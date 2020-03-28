package ds.hdfs;

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

import com.google.protobuf.InvalidProtocolBufferException;

// import com.google.protobuf.InvalidProtocolBufferException;
// import com.google.protobuf.*;

// import ds.hdfs.hdfsformat.*;

public class NameNode implements INameNode {
	int fileHandle = 1;
	String name;
	String ip;
	int port;
	protected Registry serverRegistry;
	DataNode[] dataNodes;
	long[] heartbeatTimestamp;
	HashMap<Integer, FileInfo> files;
	HashMap<String, BlockReportProto.BlockReport> blockMaps;

	public NameNode(String addr, int p, String nn) throws RemoteException {
		this.ip = addr;
		this.port = p;
		name = nn;
		serverRegistry = LocateRegistry.getRegistry();
		dataNodes = new DataNode[3];
		heartbeatTimestamp = new long[3];
		files = new HashMap<Integer, FileInfo>();
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
			files.put(fileHandle, file);

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
		} catch (Exception e) {
			System.err.println("Error at getBlockLocations " + e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}

	public byte[] assignBlock(byte[] inp) throws RemoteException {
		try {
			// retrieve the fileHandle from the client request
			PutProto.AssignBlockClientRequest assignBlockRequest = PutProto.AssignBlockClientRequest.parseFrom(inp);
			System.out.println("File Handle: " + assignBlockRequest.getFileHandle());

			// randomly pick 2 DataNodes from set of active DataNodes
			Random random = new Random();
			int dataNode1Index = random.nextInt(dataNodes.length);
			int dataNode2Index = random.nextInt(dataNodes.length);
			while (dataNode1Index == dataNode2Index) {
				dataNode2Index = random.nextInt(dataNodes.length);
			}
			System.out.println(dataNodes[0]);
			System.out.println(dataNodes[1]);
			System.out.println(dataNodes[2]);

			DataNode dataNode1 = dataNodes[dataNode1Index];
			DataNode dataNode2 = dataNodes[dataNode2Index];
			// package data for each DataNode and send it back to the client
			PutProto.AssignBlockNameNodeResponse.Builder blockNameResponse = PutProto.AssignBlockNameNodeResponse
					.newBuilder();
			blockNameResponse.addDataNodes(PutProto.AssignBlockNameNodeResponse.DataNode.newBuilder()
					.setName(dataNode1.serverName).setIp(dataNode1.ip).setPort(dataNode1.port));
			blockNameResponse.addDataNodes(PutProto.AssignBlockNameNodeResponse.DataNode.newBuilder()
					.setName(dataNode2.serverName).setIp(dataNode2.ip).setPort(dataNode2.port));
			return blockNameResponse.build().toByteArray();

		} catch (Exception e) {
			System.err.println("Error at AssignBlock " + e.toString());
			e.printStackTrace();
		}
		return null;
	}

	public byte[] list(byte[] inp) throws RemoteException {
		try {
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
			if(blockRep != null && blockRep.getDataNodename() != null){
				blockMaps.put(blockRep.getDataNodename(), blockRep);
				System.out.println("BlockReport received from :"+blockRep.getDataNodename());
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
			if (this.dataNodes[heartbeat.getId()] == null) {
				this.dataNodes[heartbeat.getId()] = new DataNode(heartbeat.getIpAddress(), heartbeat.getPort(),
						heartbeat.getName());
			}
			long currentTimeMillis = System.currentTimeMillis();
			heartbeatTimestamp[heartbeat.getId()] = currentTimeMillis;

			for (int i = 0; i < heartbeatTimestamp.length; i++) {
				if (this.dataNodes[i] != null
						&& TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis - heartbeatTimestamp[i]) > 1) {
					this.dataNodes[i] = null;
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
			LocateRegistry.createRegistry(9090);
			NameNode obj = new NameNode("localhost", 9000, "NN");
			INameNode stub = (INameNode) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry(9090);
			registry.bind("NameNode", stub);
		} catch (Exception e) {
			System.out.println("Server Exception: " + e.toString());
			e.printStackTrace();
		}
	}

}
