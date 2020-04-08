package ds.hdfs;

import com.google.protobuf.BlockReportProto;
import com.google.protobuf.HeartbeatProto;
import com.google.protobuf.ListProto;
import com.google.protobuf.PutProto;
import com.google.protobuf.getRequestProto;
import com.google.protobuf.getResponseProto;
import com.google.protobuf.ReadRequestProto;
import com.google.protobuf.ReadResponseProto;

import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.*;

import javax.sound.sampled.Port;

import java.io.*;

// import ds.hdfs.hdfsformat.*;
import com.google.protobuf.ByteString;

//import ds.hdfs.INameNode;

public class Client {
    // Variables Required
    public INameNode NNStub; // Name Node stub
    public IDataNode DNStub; // Data Node stub
    public static int BUFFERSIZE = 64000;

    public Client(String name, String IP, int port) {

        this.NNStub = GetNNStub(name, null, port);

    }

    public IDataNode GetDNStub(String Name, String IP, int Port) {
        while (true) {
            try {
                System.out.println(Name);
                System.out.println(Port);
                Registry registry = LocateRegistry.getRegistry(IP, Port);
                System.out.println(registry);
                IDataNode stub = (IDataNode) registry.lookup(IP);
                return stub;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public INameNode GetNNStub(String Name, String IP, int Port) {
        while (true) {
            System.out.println("trying to connect to NameNode");
            try {
                System.out.println("1");
                Registry registry = LocateRegistry.getRegistry(IP, Port);
                System.out.println("2");
                INameNode stub = (INameNode) registry.lookup(IP);
                System.out.println("NameNode Found");
                return stub;
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public void PutFile(String Filename) // Put File
    {

        System.out.println("Going to put file " + Filename);
        try {

            // make a request to the NameNode to open a new File with write permissions
            PutProto.OpenFileClientRequest.Builder openFileRequest = PutProto.OpenFileClientRequest.newBuilder();
            openFileRequest.setFileName(Filename);
            openFileRequest.setWritemode(true);
            byte[] openFileAckBytes = NNStub.openFile(openFileRequest.build().toByteArray());

            // receive a fileHandle response from the NameNode
            PutProto.OpenFileNameNodeAck openFileAck = PutProto.OpenFileNameNodeAck.parseFrom(openFileAckBytes);

            int fileHandle = openFileAck.getFileHandle();
            // if -1, then failure
            if (fileHandle == -1) {
                System.out.println("NameNode: openFile - failure");
                return;
            }
            System.out.println("NameNode: openFile - success");

            // make a call to assignBlock to retrieve the DataNodes to write the file to
            PutProto.AssignBlockClientRequest.Builder assignBlockRequest = PutProto.AssignBlockClientRequest
                    .newBuilder();
            assignBlockRequest.setFileHandle(fileHandle);
            byte[] assignBlockResponseBytes = NNStub.assignBlock(assignBlockRequest.build().toByteArray());

            // receive DataNodes to write to from the NameNode
            PutProto.AssignBlockNameNodeResponse assignBlockResponse = PutProto.AssignBlockNameNodeResponse
                    .parseFrom(assignBlockResponseBytes);
            System.out.println(assignBlockResponse);

            // create a list of DataNode stubs for the DataNodes to write to
            ArrayList<IDataNode> dataNodes = new ArrayList<IDataNode>();
            int numDataNodes = assignBlockResponse.getDataNodesCount();
            for (int i = 0; i < numDataNodes; i++) {
                PutProto.AssignBlockNameNodeResponse.DataNode dn = assignBlockResponse.getDataNodes(i);
                dataNodes.add(GetDNStub(dn.getName(), dn.getIp(), dn.getPort()));
                System.out.println(dataNodes.get(i));
            }

            // setup input stream to read from the file you want to write to
            File file = new File(Filename);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[BUFFERSIZE];
            int bytesRead = 0;
            int blockNumber = 1;

            // read bufferSize (default = 64000 bytes) from the file each time until no
            // bytes are read
            while ((bytesRead = bis.read(buffer)) >= 0) {
                // make a request to write a block to each DataNode
                for (int i = 0; i < dataNodes.size(); i++) {
                    IDataNode dn = dataNodes.get(i);
                    PutProto.WriteBlockClientRequest.Builder writeBlockRequest = PutProto.WriteBlockClientRequest
                            .newBuilder();
                    writeBlockRequest.setBlockNumber(blockNumber);
                    writeBlockRequest.setData(ByteString.copyFrom(buffer, 0, bytesRead));
                    writeBlockRequest.setFileName(Filename);

                    // receive response from DataNode if write to block was successful or not
                    PutProto.WriteBlockDataNodeResponse writeBlockResponse = PutProto.WriteBlockDataNodeResponse
                            .parseFrom(dn.writeBlock(writeBlockRequest.build().toByteArray()));
                    System.out.println(writeBlockResponse);
                    boolean success = writeBlockResponse.getIsSuccessful();
                    if (success) {
                        System.out.println(
                                "Block successfully written to " + assignBlockResponse.getDataNodes(i).getName());
                    } else {
                        System.out.println(
                                "Warning, block was not written to " + assignBlockResponse.getDataNodes(i).getName());
                    }

                }
                blockNumber++;

            }
        } catch (Exception e) {
            e.printStackTrace();
            // System.out.println("File not found !!!");
            return;
        }
    }

    public void GetFile(String FileName) {
        FileOutputStream fileOutputStream = null;
        try {
            File file = new File("output/" + FileName);
            file.getParentFile().mkdirs();
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            // System.out.println("here1");
            getRequestProto.getRequest.Builder gRequest = getRequestProto.getRequest.newBuilder();
            // System.out.println("here2");
            gRequest.setFilename(FileName);
            // System.out.println("here3");
            getResponseProto.getResponse gResponse = getResponseProto.getResponse
                    .parseFrom(this.NNStub.getBlockLocations(gRequest.build().toByteArray()));
            // System.out.println("here4");
            fileOutputStream.write(gResponse.getData().toByteArray());
            // System.out.println("here5");
        } catch (Exception e) {
            System.out.println("Error getting file:" + e);
        } finally {
            try {
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException ioe) {
                System.out.println("Error while closing stream: " + ioe);
            }
        }
    }

    public void List() {
        System.out.println("Going to get list of files in HDFS");
        try {
            byte[] listFilesBytes = NNStub.list(null);
            ListProto.ListFilesResponse listFilesResponse = ListProto.ListFilesResponse.parseFrom(listFilesBytes);
            List<ListProto.ListFilesResponse.File> files = listFilesResponse.getFilesList();
            for (ListProto.ListFilesResponse.File file : files) {
                System.out.println(file.getFileName());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws RemoteException, UnknownHostException, IOException {
        // To read config file and Connect to NameNode
        // Intitalize the Client
        System.out.println("Started a Client");
        // Read NameNode properties from the NameNode config file
        File nameNodeConfig = new File("src/nn_config.txt");
        BufferedReader br = new BufferedReader(new FileReader(nameNodeConfig));
        String currLine = br.readLine();
        currLine = br.readLine();
        String[] nameNodeProperties = currLine.split(";");
        String nameNodeName = nameNodeProperties[0];
        String nameNodeIP = nameNodeProperties[1];
        int nameNodePort = Integer.parseInt(nameNodeProperties[2]);
        System.out.println(nameNodeName + ", " + nameNodeIP + ", " + nameNodePort);
        Client Me = new Client(nameNodeName, nameNodeIP, nameNodePort);

        File config = new File("src/config.txt");
        br = new BufferedReader(new FileReader(config));
        currLine = br.readLine();
        currLine = br.readLine();
        currLine = br.readLine();
        String[] bufferSizeProperty = currLine.split("=");
        BUFFERSIZE = Integer.parseInt(bufferSizeProperty[1]);

        System.out.println("Welcome to HDFS!!");
        Scanner Scan = new Scanner(System.in);
        while (true) {
            // Scanner, prompt and then call the functions according to the command
            System.out.print("$> "); // Prompt
            String Command = Scan.nextLine();
            String[] Split_Commands = Command.split(" ");

            if (Split_Commands[0].equals("help")) {
                System.out.println("The following are the Supported Commands");
                System.out.println("1. put filename ## To put a file in HDFS");
                System.out.println("2. get filename ## To get a file in HDFS");
                System.out.println("3. list ## To get the list of files in HDFS");
            } else if (Split_Commands[0].equals("put")) // put Filename
            {
                // Put file into HDFS
                String Filename;
                try {
                    Filename = Split_Commands[1];
                    Me.PutFile(Filename);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please type 'help' for instructions");
                    continue;
                }
            } else if (Split_Commands[0].equals("get")) {
                // Get file from HDFS
                String Filename;
                try {
                    Filename = Split_Commands[1];
                    Me.GetFile(Filename);
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Please type 'help' for instructions");
                    continue;
                }
            } else if (Split_Commands[0].equals("list")) {
                System.out.println("List request");
                // Get list of files in HDFS
                Me.List();
            } else {
                System.out.println("Please type 'help' for instructions");
            }
        }
    }
}
