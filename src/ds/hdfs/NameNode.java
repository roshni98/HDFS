package ds.hdfs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.concurrent.TimeUnit;
import java.sql.Timestamp;

import com.google.protobuf.InvalidProtocolBufferException;


// import com.google.protobuf.InvalidProtocolBufferException;
// import com.google.protobuf.*;

// import ds.hdfs.hdfsformat.*;

public class NameNode implements INameNode{
	String name;
	String ip; 
	int port;
	protected Registry serverRegistry;
	DataNode[] dataNodes;
	long[] heartbeatTimestamp;

	public NameNode(String addr,int p, String nn) throws RemoteException
	{
		this.ip = addr;
		this.port = p;
		name = nn;
		serverRegistry = LocateRegistry.getRegistry();
		dataNodes = new DataNode[3];
		heartbeatTimestamp = new long[3];
	}

	public static class DataNode
	{
		String ip;
		int port;
		String serverName;
		public DataNode(String addr,int p,String sname)
		{
			ip = addr;
			port = p;
			serverName = sname;
		}
	}

	public static class FileInfo
	{
		String filename;
		int filehandle;
		boolean writemode;
		ArrayList<Integer> Chunks;
		public FileInfo(String name, int handle, boolean option)
		{
			filename = name;
			filehandle = handle;
			writemode = option;
			Chunks = new ArrayList<Integer>();
		}
	}
	/* Method to open a file given file name with read-write flag*/

	boolean findInFilelist(int fhandle)
	{
		return true;
	}

	public void printFilelist()
	{
	}

	public byte[] openFile(byte[] inp) throws RemoteException
	{
		try
		{
		}
		catch (Exception e)
		{
			System.err.println("Error at " + this.getClass() + e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.toByteArray();
	}

	public byte[] closeFile(byte[] inp ) throws RemoteException
	{
		try
		{
		}
		catch(Exception e)
		{
			System.err.println("Error at closefileRequest " + e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}

	public byte[] getBlockLocations(byte[] inp ) throws RemoteException
	{
		try
		{
		}
		catch(Exception e)
		{
			System.err.println("Error at getBlockLocations "+ e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}


	public byte[] assignBlock(byte[] inp ) throws RemoteException
	{
		try
		{
		}
		catch(Exception e)
		{
			System.err.println("Error at AssignBlock "+ e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}


	public byte[] list(byte[] inp ) throws RemoteException
	{
		try
		{
		}catch(Exception e)
		{
			System.err.println("Error at list "+ e.toString());
			e.printStackTrace();
			// response.setStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}

	// Datanode <-> Namenode interaction methods

	public byte[] blockReport(byte[] inp ) throws RemoteException
	{
		try
		{
		}
		catch(Exception e)
		{
			System.err.println("Error at blockReport "+ e.toString());
			e.printStackTrace();
			// response.addStatus(-1);
		}
		return null;
		// return response.build().toByteArray();
	}



	public byte[] heartBeat(byte[] inp) throws RemoteException
	{
		try{
			HeartbeatProto.Heartbeat heartbeat = HeartbeatProto.Heartbeat.parseFrom(inp);
			System.out.println(heartbeat.getName()+ " heartbeat");
			if( this.dataNodes[heartbeat.getId()] == null){
				this.dataNodes[heartbeat.getId()] = new DataNode(heartbeat.getIpAddress(), heartbeat.getPort(), heartbeat.getName());
			}
			long currentTimeMillis = System.currentTimeMillis(); 
			heartbeatTimestamp[heartbeat.getId()] = currentTimeMillis;

			for(int i=0; i<heartbeatTimestamp.length; i++){
				if(this.dataNodes[i]!=null && TimeUnit.MILLISECONDS.toMinutes(currentTimeMillis-heartbeatTimestamp[i]) > 1){
					this.dataNodes[i] = null;
					System.out.println("Looks like DataNode"+(i+1)+" is dead!");
				}
			}
		} catch(InvalidProtocolBufferException e){
			e.printStackTrace();
		}
		return null;
		// return response.build().toByteArray();
	}

	public String printMsg(String msg) throws RemoteException
	{
		System.out.println(msg);
		return msg;
	}

	public static void main(String[] args) throws InterruptedException, NumberFormatException, IOException
	{
		try{
			LocateRegistry.createRegistry(9090);
			NameNode obj = new NameNode("localhost", 9000,"NN");
			INameNode stub = (INameNode) UnicastRemoteObject.exportObject(obj, 0);
			Registry registry = LocateRegistry.getRegistry(9090);
			registry.bind("NameNode", stub);
		} catch(Exception e){
			System.out.println("Server Exception: "+ e.toString());
			e.printStackTrace();
		}
	}

}
