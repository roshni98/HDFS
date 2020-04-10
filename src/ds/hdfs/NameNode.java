package ds.hdfs;

import com.google.protobuf.BlockReportProto;
import com.google.protobuf.HeartbeatProto;
import com.google.protobuf.ListProto;
import com.google.protobuf.PutProto;
import com.google.protobuf.getRequestProto;
import com.google.protobuf.getResponseProto;
import com.google.protobuf.BlockReportProto.BlockReport;
import com.google.protobuf.ReadRequestProto;
import com.google.protobuf.ReadResponseProto;
import com.google.protobuf.RecoverDataNodeProto;
import java.io.ByteArrayOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;
import java.util.function.*;
import java.util.stream.*;
import java.util.Iterator;

import com.google.protobuf.InvalidProtocolBufferException;

import com.google.protobuf.ByteString;

// import com.google.protobuf.InvalidProtocolBufferException;
// import com.google.protobuf.*;

// import ds.hdfs.hdfsformat.*;

public class NameNode implements INameNode {
	int fileHandle = 1;
	static int replicationFactor;
	static int dataNodeTimeout;
	String name;
	String ip;
	int port;
	static int aliveDataNodes = 0;
	protected Registry serverRegistry;
	ArrayList<DataNode> dataNodes;
	ArrayList<Long> heartbeatTimestamp;
	ArrayList<FileInfo> files;
	HashMap<Integer, FileInfo> fileMap;
	HashMap<String, BlockReportProto.BlockReport> blockMaps;

	public NameNode(String addr, int p, String nn) throws RemoteException {
		this.ip = addr;
		this.port = p;
		name = nn;
		serverRegistry = LocateRegistry.getRegistry();
		dataNodes = new ArrayList<DataNode>();
		heartbeatTimestamp = new ArrayList<Long>();
		files = new ArrayList<FileInfo>();
		fileMap = new HashMap<Integer, FileInfo>();
		blockMaps = new HashMap<>();

	}

	/* Class that represents information about DataNode object for Hearbeat */
	public static class DataNode {
		String ip;
		int port;
		String serverName;

		public DataNode(String addr, int p, String sname) {
			ip = addr;
			port = p;
			serverName = sname;
		}
	}

	/*
	 * Class that represents information about File stored on a particular DataNode
	 * object
	 */
	public static class FileInfo {
		// name of the file
		String filename;
		// file descriptor to give to client
		int filehandle;
		// true for a put call, false otherwise
		boolean writemode;

		ArrayList<Integer> Chunks;

		public FileInfo(String name, int handle, boolean option) {
			filename = name;
			filehandle = handle;
			writemode = option;
			Chunks = new ArrayList<Integer>();
		}
	}
	/* Method to open a file given file name with read-write flag */

	boolean findInFilelist(int fhandle) {
		return true;

	}

	public void printFilelist() {
	}

	public byte[] openFile(byte[] inp) throws RemoteException {
		PutProto.OpenFileNameNodeAck.Builder openFileAck;
		try {
			// receive an open file request from the client
			PutProto.OpenFileClientRequest openFileRequest = PutProto.OpenFileClientRequest.parseFrom(inp);
			System.out.println(openFileRequest.getFileName());
			System.out.println(openFileRequest.getWritemode());

			// create a new FileInfo object for this new file
			FileInfo file = new FileInfo(openFileRequest.getFileName(), fileHandle, openFileRequest.getWritemode());

			// store this FileInfo object in an HashMap that represents files across all
			// DataNodes given a fileHandle
			files.add(file);
			fileMap.put(fileHandle, file);

			// respond with the fileHandle for the opened file
			openFileAck = PutProto.OpenFileNameNodeAck.newBuilder();
			openFileAck.setFileHandle(fileHandle);

			// increment the fileHandle to keep them unique for each file
			fileHandle++;

			// success -> return the fileHandle to indicate success to client
		} catch (Exception e) {
			System.err.println("Error at " + this.getClass() + e.toString());
			e.printStackTrace();
			// error while trying to open a file -> return a bad file handle to indicate
			// failure to client
			openFileAck = PutProto.OpenFileNameNodeAck.newBuilder();
			openFileAck.setFileHandle(-1);
		}
		return openFileAck.build().toByteArray();
	}

	public byte[] closeFile(byte[] inp) throws RemoteException {
		try {
		} catch (Exception e) {
			System.err.println("Error at closefileRequest " + e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}

	public byte[] getBlockLocations(byte[] inp) throws RemoteException {
		try {

			// retriever the details about the file to read form the client
			getRequestProto.getRequest gRequest = getRequestProto.getRequest.parseFrom(inp);
			String filename = gRequest.getFilename(); // hp.txt

			// setup to create a list of all available DataNodes that contain this file
			int dataNodeIndex = 0;
			List<Integer> blocks = null;
			List<Integer> dataNodesToRead = new ArrayList<Integer>();

			// find which DataNodes contain the file and add it to the list
			for (String key : blockMaps.keySet()) {
				if (blockMaps.get(key).getFilesMap().containsKey(filename)) {
					if (blocks == null) {
						blocks = blockMaps.get(key).getFilesMap().get(filename).getBlockNumberList();
					}
					if (key.equals("DataNode1") && dataNodes.get(0) != null) {
						dataNodesToRead.add(0);
					} else if (key.equals("DataNode2") && dataNodes.get(1) != null) {
						dataNodesToRead.add(1);
					} else if (key.equals("DataNode3") && dataNodes.get(2) != null) {
						dataNodesToRead.add(2);
					}
				}
			}

			boolean success = false;
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			// if the file exists, read all the blocks, merge them, and return to the client
			if (blocks != null) {
				// remove any DataNodes that are down and are at the beginning of the list of
				// DataNodes with that file
				while (dataNodesToRead.size() != 0 && dataNodes.get(dataNodesToRead.get(0)) == null) {
					dataNodesToRead.remove(0);
				}

				// if any available DataNodes are left, read from the file
				if (dataNodesToRead.size() > 0) {
					// create a DataNode stub for the alive DataNode
					DataNode dn = dataNodes.get(dataNodesToRead.get(0));
					Registry registry = LocateRegistry.getRegistry(dn.ip, dn.port);
					IDataNode dnStub = (IDataNode) registry.lookup(dn.serverName);

					// loop to read all blocks from that DataNode
					for (Integer i : blocks) {
						// try to read a block one at a time from an alive DataNode
						try {
							ReadRequestProto.ReadRequest.Builder readRequest = ReadRequestProto.ReadRequest
									.newBuilder();
							readRequest.setFilename(filename);
							readRequest.setBlockNumber(i);
							ReadResponseProto.ReadResponse readResponse = ReadResponseProto.ReadResponse
									.parseFrom(dnStub.readBlock(readRequest.build().toByteArray()));
							if (readResponse != null) {
								outputStream.write(readResponse.getData().toByteArray());
							}
						}
						// get the next available DataNode with the same file
						catch (Exception e) {
							// remove the current DataNode from the list of DataNodes with the file
							dn = null;
							dataNodesToRead.remove(0);
							// Continue removing dead DataNodes with the file from the list
							while (dataNodesToRead.size() != 0 && dataNodes.get(dataNodesToRead.get(0)) == null) {
								dataNodesToRead.remove(0);
							}
							// check to see if there are any available nodes to read from in general
							// if not, print error and return failure to read to the client
							if (dataNodesToRead.size() == 0) {
								success = false;
								break;
							}
							// if there is an available node, then create a new stub and continue reading
							// the file
							else {
								dn = dataNodes.get(dataNodesToRead.get(0));
								registry = LocateRegistry.getRegistry(dn.ip, dn.port);
								dnStub = (IDataNode) registry.lookup(dn.serverName);
							}
						}
					}
					success = true;
				}
			}
			getResponseProto.getResponse.Builder gResponse = getResponseProto.getResponse.newBuilder();
			// file does not exist on any of the DataNodes
			if (blocks == null) {
				System.out.println("File does not exist.");
				gResponse.setStatus(-2);
			}
			// No available DataNodes for this file
			else if (!success) {
				System.out.println("All DataNodes with this file are unavailable");
				gResponse.setStatus(-1);
			}
			// File read successfully
			else {
				System.out.println("File read was successful");
				gResponse.setData(ByteString.copyFrom(outputStream.toByteArray()));
				gResponse.setFilename(filename);
				gResponse.setStatus(1);
			}
			return gResponse.build().toByteArray();

		} catch (Exception e) {
			System.err.println("Error at getBlockLocations " + e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
			// return null;
		}
		return null;
		// return response.build().toByteArray();
	}

	public byte[] assignBlock(byte[] inp) throws RemoteException {
		try {
			// retrieve the fileHandle from the client request
			PutProto.AssignBlockClientRequest assignBlockRequest = PutProto.AssignBlockClientRequest.parseFrom(inp);

			// randomly picks <replicationFactor> number of DataNodes from set of active
			// DataNodes
			ArrayList<Integer> dataNodeIndexes = new ArrayList<Integer>();
			Random random = new Random();

			// check to see if there are enough DataNodes to meet the replicationFactor.
			// if not, add all the available nodes to the DataNodes list
			if (aliveDataNodes < replicationFactor) {
				System.out.println(
						"There are less DataNodes alive than the replication factor. Will write to all alive DataNodes");
				for (int i = 0; i < dataNodes.size(); i++) {
					if (dataNodes.get(i) != null) {
						dataNodeIndexes.add(i);
					}
				}
			}
			// there are enough Datanodes to meet the replication factor.
			else {
				for (int i = 0; i < replicationFactor; i++) {
					int nextDataNodeIndex = random.nextInt(dataNodes.size());
					while (dataNodeIndexes.contains(nextDataNodeIndex) || dataNodes.get(nextDataNodeIndex) == null) {
						nextDataNodeIndex = random.nextInt(dataNodes.size());
					}
					dataNodeIndexes.add(nextDataNodeIndex);
				}
			}

			// create list of new DataNode objects for the client to write to
			ArrayList<DataNode> dataNodesAssigned = new ArrayList<DataNode>();
			for (int index : dataNodeIndexes) {
				dataNodesAssigned.add(dataNodes.get(index));
			}
			// package data for each DataNode and send it back to the client
			PutProto.AssignBlockNameNodeResponse.Builder blockNameResponse = PutProto.AssignBlockNameNodeResponse
					.newBuilder();
			for (DataNode dn : dataNodesAssigned) {
				blockNameResponse.addDataNodes(PutProto.AssignBlockNameNodeResponse.DataNode.newBuilder()
						.setName(dn.serverName).setIp(dn.ip).setPort(dn.port));
			}
			return blockNameResponse.build().toByteArray();

		} catch (Exception e) {
			System.err.println("Error at AssignBlock " + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public byte[] list(byte[] inp) throws RemoteException {
		try {
			// traverse the list of files and return the available filenames to client
			ListProto.ListFilesResponse.Builder listFilesResponse = ListProto.ListFilesResponse.newBuilder();
			for (FileInfo file : files) {
				listFilesResponse.addFiles(ListProto.ListFilesResponse.File.newBuilder().setFileName(file.filename));
			}
			return listFilesResponse.build().toByteArray();

		} catch (Exception e) {
			System.err.println("Error at list " + e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}

	// Datanode <-> Namenode interaction methods
	public byte[] blockReport(byte[] inp) throws RemoteException {
		try {
			// store blockReports for a specific DataNode in the blockMaps hashmap
			// blockReports contain a list of files and associated blocks for each DataNode
			BlockReportProto.BlockReport blockRep = BlockReportProto.BlockReport.parseFrom(inp);
			if (blockRep != null && blockRep.getDataNodename() != null) {
				blockMaps.put(blockRep.getDataNodename(), blockRep);
				System.out.println("BlockReport received from: " + blockRep.getDataNodename());
			}
		} catch (Exception e) {
			System.err.println("Error at blockReport " + e.toString());
			e.printStackTrace();
			// response.addStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}

	public byte[] heartBeat(byte[] inp) throws RemoteException {
		// in the event of a recovery, send all files to the DataNode for persistence
		RecoverDataNodeProto.RecoverDataNode.Builder recoverResponse = RecoverDataNodeProto.RecoverDataNode
				.newBuilder();
		try {
			// Heartbeat initiated by a DataNode
			HeartbeatProto.Heartbeat heartbeat = HeartbeatProto.Heartbeat.parseFrom(inp);
			System.out.println(heartbeat.getName() + " heartbeat");

			try {
				DataNode dn = dataNodes.get(heartbeat.getId());
				System.out.println(dn);

				// If the DataNode was previously dead
				if (dn == null) {
					System.out.println("Recovering dead " + heartbeat.getName());
					this.dataNodes.set(heartbeat.getId(),
							new DataNode(heartbeat.getIpAddress(), heartbeat.getPort(), heartbeat.getName()));

					aliveDataNodes++;

					// Send DataNode the list of blocks from previous state to the DataNode
					BlockReportProto.BlockReport dnReport = blockMaps.get(heartbeat.getName());
					Map<String, BlockReportProto.BlockReport.ListBlocks> dnFiles = dnReport.getFilesMap();
					Iterator<String> it = dnFiles.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next();
						RecoverDataNodeProto.RecoverDataNode.ListBlocks.Builder listBlocks = RecoverDataNodeProto.RecoverDataNode.ListBlocks
								.newBuilder();
						listBlocks.addAllBlockNumber(dnFiles.get(key).getBlockNumberList());
						recoverResponse.putFiles(key, listBlocks.build());
					}

					// indicate to the DataNode that a previous blockReport is being received from
					// the NameNode and set the time of the DataNode
					recoverResponse.setStatus(1);
					this.heartbeatTimestamp.set(heartbeat.getId(), System.currentTimeMillis());
					return recoverResponse.build().toByteArray();
				}
			} catch (Exception e) {
				e.printStackTrace();

				// DataNode is new, add it
				this.dataNodes.add(new DataNode(heartbeat.getIpAddress(), heartbeat.getPort(), heartbeat.getName()));
			}

			// set the time of the DataNode to the current time
			long currentTimeMillis = System.currentTimeMillis();
			try {
				this.heartbeatTimestamp.set(heartbeat.getId(), currentTimeMillis);
			} catch (Exception e) {
				this.heartbeatTimestamp.add(currentTimeMillis);
			}

			// Check all DataNodes to see if any of them are down
			for (int i = 0; i < this.heartbeatTimestamp.size(); i++) {
				// DataNode died, skip it
				if (this.dataNodes.get(i) == null) {
					continue;
				}
				long timeDifference = currentTimeMillis - heartbeatTimestamp.get(i);
				// DataNode death detected, set it to null
				if (this.dataNodes.get(i) != null && timeDifference > (dataNodeTimeout * 1000)) {
					this.dataNodes.set(i, null);
					this.heartbeatTimestamp.set(i, (long) 0);
					System.out.println("Looks like DataNode" + (i + 1) + " is dead!");
					aliveDataNodes--;
				}
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		recoverResponse.setStatus(-1);
		return recoverResponse.build().toByteArray();
	}

	public String printMsg(String msg) throws RemoteException {

		System.out.println(msg);
		return msg;
	}

	public static void main(String[] args) throws InterruptedException, NumberFormatException, IOException {
		try {
			// Read NameNode properties from the NameNode config file
			File nameNodeConfig = new File("nn_config.txt");
			BufferedReader br = new BufferedReader(new FileReader(nameNodeConfig));
			String currLine = br.readLine();
			currLine = br.readLine();
			String[] nameNodeProperties = currLine.split(";");

			// for Docker
			String nameNodeName = System.getenv(nameNodeProperties[0]);
			String nameNodeIP = System.getenv(nameNodeProperties[1]);
			int nameNodePort = Integer.parseInt(System.getenv(nameNodeProperties[2]));

			// for localhost
			// String nameNodeName = nameNodeProperties[0];
			// String nameNodeIP = nameNodeProperties[1];
			// int nameNodePort = Integer.parseInt(nameNodeProperties[2]);
			System.out.println(nameNodeName + ", " + nameNodeIP + ", " + nameNodePort);

			// read replicationFactor and DataNode timeout time from the config file
			File config = new File("config.txt");
			br = new BufferedReader(new FileReader(config));
			currLine = br.readLine();
			currLine = br.readLine();
			String[] replicationFactorProperty = currLine.split("=");
			replicationFactor = Integer.parseInt(replicationFactorProperty[1]);
			currLine = br.readLine();
			currLine = br.readLine();
			currLine = br.readLine();
			currLine = br.readLine();
			String[] dataNodeTimeoutProperty = currLine.split("=");
			dataNodeTimeout = Integer.parseInt(dataNodeTimeoutProperty[1]);

			// set number of aliveDataNodes to 0 and increment as new DataNodes are added
			aliveDataNodes = 0;

			// create a NameNode stub and bind it to the Java RMI registry
			LocateRegistry.createRegistry(nameNodePort);
			NameNode obj = new NameNode(nameNodeIP, nameNodePort, nameNodeName);
			INameNode stub = (INameNode) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry(nameNodePort);
			registry.bind(nameNodeName, stub);

		} catch (Exception e) {
			System.out.println("Server Exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
