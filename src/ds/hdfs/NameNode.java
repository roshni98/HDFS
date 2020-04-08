package ds.hdfs;

import com.google.protobuf.BlockReportProto;
import com.google.protobuf.HeartbeatProto;
import com.google.protobuf.ListProto;
import com.google.protobuf.PutProto;
import com.google.protobuf.getRequestProto;
import com.google.protobuf.getResponseProto;
import com.google.protobuf.ReadRequestProto;
import com.google.protobuf.ReadResponseProto;
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
		try {// read
			getRequestProto.getRequest gRequest = getRequestProto.getRequest.parseFrom(inp);
			String filename = gRequest.getFilename(); // hp.txt
			int dataNodeIndex = 0;
			List<Integer> blocks = null;
			// find which dataNode has the file
			for (String key : blockMaps.keySet()) {
				// map of filename->blocks
				if (blockMaps.get(key).getFilesMap().containsKey(filename)) {
					blocks = blockMaps.get(key).getFilesMap().get(filename).getBlockNumberList();
					if (key.equals("DataNode1")) {
						dataNodeIndex = 0;
					} else if (key.equals("DataNode2")) {
						dataNodeIndex = 1;
					} else {
						dataNodeIndex = 2;
					}
					break;
				}
			}
			if (blocks != null) {
				DataNode dn = dataNodes.get(dataNodeIndex);
				Registry registry = LocateRegistry.getRegistry(dn.ip, dn.port);
				IDataNode dnStub = (IDataNode) registry.lookup(dn.serverName);
				ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
				for (Integer i : blocks) {
					ReadRequestProto.ReadRequest.Builder readRequest = ReadRequestProto.ReadRequest.newBuilder();
					readRequest.setFilename(filename);
					readRequest.setBlockNumber(i);
					ReadResponseProto.ReadResponse readResponse = ReadResponseProto.ReadResponse
							.parseFrom(dnStub.readBlock(readRequest.build().toByteArray()));
					if (readResponse != null) {
						outputStream.write(readResponse.getData().toByteArray());
					}
				}
				getResponseProto.getResponse.Builder gResponse = getResponseProto.getResponse.newBuilder();
				gResponse.setData(ByteString.copyFrom(outputStream.toByteArray()));
				gResponse.setFilename(filename);
				return gResponse.build().toByteArray();
			} else {
				throw new Exception("No Blocks found!");
			}
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
			System.out.println("File Handle: " + assignBlockRequest.getFileHandle());

			// randomly picks <replicationFactor> number of DataNodes from set of active
			// DataNodes
			ArrayList<Integer> dataNodeIndexes = new ArrayList<Integer>();
			Random random = new Random();
			System.out.println(replicationFactor);
			for (int i = 0; i < replicationFactor; i++) {
				int nextDataNodeIndex = random.nextInt(dataNodes.size());
				while (dataNodeIndexes.contains(nextDataNodeIndex)) {
					nextDataNodeIndex = random.nextInt(dataNodes.size());
				}
				dataNodeIndexes.add(nextDataNodeIndex);
			}
			System.out.println("DataNode Indexes: " + dataNodeIndexes);
			// int dataNode1Index = random.nextInt(dataNodes.length);
			// int dataNode2Index = random.nextInt(dataNodes.length);
			// while (dataNode1Index == dataNode2Index) {
			// dataNode2Index = random.nextInt(dataNodes.length);
			// }

			// create list of new DataNode objects for the Client to write to
			ArrayList<DataNode> dataNodesAssigned = new ArrayList<DataNode>();
			// DataNode dataNode1 = dataNodes[dataNode1Index];
			// DataNode dataNode2 = dataNodes[dataNode2Index];
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
		try {
			HeartbeatProto.Heartbeat heartbeat = HeartbeatProto.Heartbeat.parseFrom(inp);
			System.out.println(heartbeat.getName() + " heartbeat");
			try {
				DataNode dn = dataNodes.get(heartbeat.getId());
				if (dn == null) {
					System.out.println("Recovering dead " + heartbeat.getName());
					this.dataNodes.set(heartbeat.getId(),
							new DataNode(heartbeat.getIpAddress(), heartbeat.getPort(), heartbeat.getName()));
				}

			} catch (Exception e) {
				this.dataNodes.add(new DataNode(heartbeat.getIpAddress(), heartbeat.getPort(), heartbeat.getName()));
			}
			// if (this.dataNodes[heartbeat.getId()] == null) {
			// this.dataNodes[heartbeat.getId()] = new DataNode(heartbeat.getIpAddress(),
			// heartbeat.getPort(),
			// heartbeat.getName());
			// }
			long currentTimeMillis = System.currentTimeMillis();
			try {
				this.heartbeatTimestamp.set(heartbeat.getId(), currentTimeMillis);
			} catch (Exception e) {
				this.heartbeatTimestamp.add(currentTimeMillis);
			}

			for (int i = 0; i < this.heartbeatTimestamp.size(); i++) {
				if (this.dataNodes.get(i) == null) {
					continue;
				}
				long timeDifference = currentTimeMillis - heartbeatTimestamp.get(i);
				if (this.dataNodes.get(i) != null && timeDifference > (dataNodeTimeout * 1000)) {
					this.dataNodes.set(i, null);
					this.heartbeatTimestamp.set(i, (long) 0);
					System.out.println("Looks like DataNode" + (i + 1) + " is dead!");
				}
			}
		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		return null;
		// return response.build().toByteArray();
	}

	public String printMsg(String msg) throws RemoteException {

		System.out.println(msg);
		return msg;
	}

	public static void main(String[] args) throws InterruptedException, NumberFormatException, IOException {
		try {
			// Read NameNode properties from the NameNode config file
			// System.out.println(System.getProperty("user.dir"));
			File nameNodeConfig = new File("src/nn_config.txt");
			BufferedReader br = new BufferedReader(new FileReader(nameNodeConfig));
			String currLine = br.readLine();
			currLine = br.readLine();
			String[] nameNodeProperties = currLine.split(";");
			String nameNodeName = nameNodeProperties[0];
			String nameNodeIP = nameNodeProperties[1];
			int nameNodePort = Integer.parseInt(nameNodeProperties[2]);

			// read replicationFactor and DataNode timeout time from the config file
			File config = new File("src/config.txt");
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
