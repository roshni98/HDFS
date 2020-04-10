//Written By Shaleen Garg
package ds.hdfs;

import com.google.protobuf.BlockReportProto;
import com.google.protobuf.HeartbeatProto;
import com.google.protobuf.ListProto;
import com.google.protobuf.PutProto;
import com.google.protobuf.getRequestProto;
import com.google.protobuf.getResponseProto;
import com.google.protobuf.ReadRequestProto;
import com.google.protobuf.ReadResponseProto;
import com.google.protobuf.RecoverDataNodeProto;
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

import javax.sound.sampled.Port;

import com.google.protobuf.ByteString;

// import jdk.vm.ci.aarch64.AArch64;

import java.io.*;
import java.nio.charset.Charset;
import java.lang.Thread;

// import ds.hdfs.hdfsformat.*;
// import ds.hdfs.IDataNode.*;

public class DataNode extends Thread implements IDataNode {
    protected String MyChunksFile;
    protected INameNode NNStub;
    protected String MyIP;
    protected int MyPort;
    protected String MyName;
    protected int MyID;
    public static int heartbeatInterval = 3;
    public static int blockReportInterval = 5;

    public static HashMap<String, ArrayList<Integer>> allFiles;

    /* Creates a DataNode Object of the fields listed above */
    public DataNode() {
        // Constructor
        allFiles = new HashMap<>();
    }

    // /* IGNORE THIS METHOD */
    // public static void appendtoFile(String Filename, String Line) {
    // BufferedWriter bw = null;

    // }

    public byte[] readBlock(byte[] Inp) {
        ReadResponseProto.ReadResponse.Builder readResponse = ReadResponseProto.ReadResponse.newBuilder();
        try {
            // process the read request from the client to get a certain block
            ReadRequestProto.ReadRequest readRequest = ReadRequestProto.ReadRequest.parseFrom(Inp);
            String filename = this.MyName + "/" + readRequest.getBlockNumber() + " - " + readRequest.getFilename();
            File file = new File(filename);
            System.out.println("Requesting Block: " + filename);

            // convert the file contents to a byte array
            byte fileContent[] = new byte[(int) file.length()];
            FileInputStream fs = new FileInputStream(file);
            fs.read(fileContent);

            // send data back to the NameNode as bytes
            readResponse.setData(ByteString.copyFrom(fileContent));
            readResponse.setFilename(readRequest.getFilename());
            return readResponse.build().toByteArray();
        } catch (Exception e) {

            System.out.println("Error at readBlock");
            e.printStackTrace();
            // response.setStatus(-1);
        }
        return null;
        // return response.build().toByteArray();
    }

    public byte[] writeBlock(byte[] Inp) {
        // unserialize the byte array to proto object
        try {

            // receive block number, filename, and data to write in this block from the
            // client
            PutProto.WriteBlockClientRequest writeBlockRequest = PutProto.WriteBlockClientRequest.parseFrom(Inp);
            String data = writeBlockRequest.getData().toStringUtf8();
            int blockNumber = writeBlockRequest.getBlockNumber();
            String fileName = writeBlockRequest.getFileName();

            // create a new file in the DataNode to represent the block
            // Use hashmap to keep track
            if (!this.allFiles.containsKey(fileName)) {
                this.allFiles.put(fileName, new ArrayList<Integer>());
            }
            this.allFiles.get(fileName).add(Integer.valueOf(blockNumber));

            // create a new block file in the DataNode folder
            String path = this.MyName + "/" + blockNumber + " - " + fileName;
            File file = new File(path);
            file.getParentFile().mkdirs();
            file.createNewFile();

            // setup a filewriter and bufferedwriter to write block to DataNode
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(data);
            bw.close();
            fw.close();

            // respond to client that write was successful
            System.out.println("Wrote Block Successfully");
            PutProto.WriteBlockDataNodeResponse.Builder writeBlockResponse = PutProto.WriteBlockDataNodeResponse
                    .newBuilder();
            writeBlockResponse.setIsSuccessful(true);
            System.out.println(writeBlockResponse);
            return writeBlockResponse.build().toByteArray();

        } catch (Exception e) {
            System.out.println("Error at writeBlock ");
            e.printStackTrace();
            // respond to client that write was unsucessful
            PutProto.WriteBlockDataNodeResponse.Builder writeBlockResponse = PutProto.WriteBlockDataNodeResponse
                    .newBuilder();
            writeBlockResponse.setIsSuccessful(false);
            return writeBlockResponse.build().toByteArray();
        }
    }

    public BlockReportProto.BlockReport BlockReport() throws IOException {
        BlockReportProto.BlockReport.Builder blockReport = BlockReportProto.BlockReport.newBuilder();
        Iterator<String> it = this.allFiles.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            BlockReportProto.BlockReport.ListBlocks.Builder listBlocks = BlockReportProto.BlockReport.ListBlocks
                    .newBuilder();
            listBlocks.addAllBlockNumber(this.allFiles.get(key));
            blockReport.putFiles(key, listBlocks.build());
        }
        blockReport.setDataNodename(this.MyName);
        return blockReport.build();
    }

    public void BindServer(String Name, String IP, int Port) {
        try {
            IDataNode stub = (IDataNode) UnicastRemoteObject.exportObject(this, Port);
            System.setProperty("java.rmi.server.hostname", "localhost");
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
                e.printStackTrace();
                System.out.println("NameNode still not Found");
                // return null;
                continue;
            }
        }
    }

    // InvalidProtocolBufferException,
    public static void main(String args[]) throws IOException {

        File dataNodeConfig = new File("dn_config.txt");
        BufferedReader br = new BufferedReader(new FileReader(dataNodeConfig));

        String currLine = br.readLine();
        currLine = br.readLine();
        String[] dataNodeProperties = currLine.split(";");
        System.out.println(dataNodeProperties[0]);
        int id = Integer.parseInt(System.getenv(dataNodeProperties[0]));
        String dataNodeIp = System.getenv(dataNodeProperties[1]) == null ? "localhost" : System.getenv(dataNodeProperties[1]);
        int portNum = Integer.parseInt(System.getenv(dataNodeProperties[2]));
        // int id = Integer.parseInt(dataNodeProperties[0]);
        // String dataNodeIp = dataNodeProperties[1];
        // int portNum = Integer.parseInt(dataNodeProperties[2]);
        // int id = Integer.parseInt(System.getenv(dataNodeProperties[0]));
        // String dataNodeIp = System.getenv(dataNodeProperties[1]);
        // int portNum = Integer.parseInt(System.getenv(dataNodeProperties[2]));
        String dataNodeName = "DataNode" + (id + 1);

        // Define a new DataNode object called Me
        DataNode Me = new DataNode();
        LocateRegistry.createRegistry(portNum);
        Me.BindServer(dataNodeName, dataNodeIp, portNum);
        Me.MyName = dataNodeName;
        Me.MyID = id;
        Me.MyPort = portNum;
        Me.MyIP = dataNodeIp;

        // Read NameNode properties from the NameNode config file
        File nameNodeConfig = new File("nn_config.txt");
        br = new BufferedReader(new FileReader(nameNodeConfig));
        currLine = br.readLine();
        currLine = br.readLine();
        String[] nameNodeProperties = currLine.split(";");
        String nameNodeName = System.getenv(nameNodeProperties[0]);
        String nameNodeIP = System.getenv(nameNodeProperties[1]);
        int nameNodePort = Integer.parseInt(System.getenv(nameNodeProperties[2]));
        // String nameNodeName = nameNodeProperties[0];
        // String nameNodeIP = nameNodeProperties[1];
        // int nameNodePort = Integer.parseInt(nameNodeProperties[2]);

        // Create the NameNode stub
        INameNode nameNode = Me.GetNNStub(nameNodeName, nameNodeIP, nameNodePort);

        // Send a hearbeat to the NameNode to let it know that this DataNode is alive
        HeartbeatProto.Heartbeat.Builder protoMsg = HeartbeatProto.Heartbeat.newBuilder();
        protoMsg.setIpAddress(dataNodeIp);
        protoMsg.setPort(portNum);
        protoMsg.setName(dataNodeName);
        protoMsg.setId(id);

        // Get heartbeat and blockreport intervals from config
        File config = new File("config.txt");
        br = new BufferedReader(new FileReader(config));
        currLine = br.readLine();
        currLine = br.readLine();
        currLine = br.readLine();
        currLine = br.readLine();
        String[] heartBeatProperty = currLine.split("=");
        heartbeatInterval = Integer.parseInt(heartBeatProperty[1]);
        currLine = br.readLine();
        String[] blockReportProperty = currLine.split("=");
        blockReportInterval = Integer.parseInt(blockReportProperty[1]);

        // thread to handle sending heartbeats to the server every <heartbeatInterval>
        // seconds
        Thread heartBeatThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        RecoverDataNodeProto.RecoverDataNode recoverResponse = RecoverDataNodeProto.RecoverDataNode
                                .parseFrom(nameNode.heartBeat(protoMsg.build().toByteArray()));
                        int status = recoverResponse.getStatus();
                        if (status == 1) {
                            Map<String, RecoverDataNodeProto.RecoverDataNode.ListBlocks> files = recoverResponse
                                    .getFilesMap();
                            Iterator<String> it = files.keySet().iterator();
                            while (it.hasNext()) {
                                String key = it.next();
                                ArrayList<Integer> blocks = new ArrayList<Integer>(files.get(key).getBlockNumberList());
                                allFiles.put(key, blocks);
                            }
                            System.out.println(allFiles.toString());
                        }
                        Thread.sleep(heartbeatInterval * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        // thread to handle sending the blockReport to the server every
        // <blockReportInterval> seconds
        Thread blockReportThread = new Thread() {
            public void run() {
                while (true) {
                    try {
                        nameNode.blockReport(Me.BlockReport().toByteArray());
                        Thread.sleep(blockReportInterval * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        heartBeatThread.start();
        blockReportThread.start();
    }
}
