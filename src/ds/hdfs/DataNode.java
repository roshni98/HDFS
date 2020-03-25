//Written By Shaleen Garg
package ds.hdfs;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

// import com.google.protobuf.ByteString;
// import com.google.protobuf.InvalidProtocolBufferException;

import java.io.*;
import java.nio.charset.Charset;

// import ds.hdfs.hdfsformat.*;
// import ds.hdfs.IDataNode.*;

public class DataNode implements IDataNode {
    protected String MyChunksFile;
    protected INameNode NNStub;
    protected String MyIP;
    protected int MyPort;
    protected String MyName;
    protected int MyID;

    /* Creates a DataNode Object of the fields listed above */
    public DataNode() {
        // Constructor
    }

    /* IGNORE THIS METHOD */
    public static void appendtoFile(String Filename, String Line) {
        BufferedWriter bw = null;

        // try {
        //     //append
        // }
        // catch (IOException ioe)
        // {
        //     ioe.printStackTrace();
        // }
        // finally
        // {                       // always close the file
        //     if (bw != null) try {
        //         bw.close();
        //     } catch (IOException ioe2) {
        //     }
        // }

    }

    public byte[] readBlock(byte[] Inp)
    {
      // unserialize the byte array to proto object
        try
        {
          //
        }
        catch(Exception e)
        {

            System.out.println("Error at readBlock");
            // response.setStatus(-1);
        }
        return null;
        // return response.build().toByteArray();
    }

    public byte[] writeBlock(byte[] Inp)
    {
      // unserialize the byte array to proto object
        try
        {
          //
        }
        catch(Exception e)
        {

            System.out.println("Error at writeBlock ");
            // response.setStatus(-1);
        }
        return null;
        // return response.build().toByteArray();
    }

    public void BlockReport() throws IOException {
    }

    public void BindServer(String Name, String IP, int Port) {
        try {
            IDataNode stub = (IDataNode) UnicastRemoteObject.exportObject(this, 0);
            System.setProperty("java.rmi.server.hostname", IP);
            Registry registry = LocateRegistry.getRegistry(Port);
            registry.rebind(Name, stub);
            System.out.println("\nDataNode connected to RMIregistry\n");
        } catch (Exception e) {
            System.err.println("Server Exception: " + e.toString());
            e.printStackTrace();
        }
    }

    public INameNode GetNNStub(String Name, String IP, int Port) {
        while (true) {
            try {
                Registry registry = LocateRegistry.getRegistry(IP, Port);
                INameNode stub = (INameNode) registry.lookup(Name);
                System.out.println("NameNode Found!");
                return stub;
            } catch (Exception e) {
                System.out.println("NameNode still not Found");
                continue;
            }
        }
    }

    // InvalidProtocolBufferException, 
    public static void main(String args[]) throws IOException
    {   

        String dataNodeName = "DataNode2";
        String dataNodeIp = "localhost";
        int portNum = 9092; 
        int id = 1;

        //Define a Datanode Me
        DataNode Me = new DataNode();
        LocateRegistry.createRegistry(portNum);
        Me.BindServer(dataNodeName, dataNodeIp, portNum);
       
        INameNode nameNode = Me.GetNNStub("NameNode", null, 9090);
        HeartbeatProto.Heartbeat.Builder protoMsg = HeartbeatProto.Heartbeat.newBuilder();
        protoMsg.setIpAddress(dataNodeIp);
        protoMsg.setPort(portNum);
        protoMsg.setName(dataNodeName);
        protoMsg.setId(id);

        while(true){
            try{
                // nameNode.printMsg(dataNodeName+" heartbeat");
                nameNode.heartBeat(protoMsg.build().toByteArray());
                Thread.sleep(10*1000); // sleeps for every 10 seconds
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }


    }
}
