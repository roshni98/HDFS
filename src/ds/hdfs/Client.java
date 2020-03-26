package ds.hdfs;

import java.net.UnknownHostException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.util.*;
import java.io.*;

// import ds.hdfs.hdfsformat.*;
import com.google.protobuf.ByteString;

//import ds.hdfs.INameNode;

public class Client {
    // Variables Required
    public INameNode NNStub; // Name Node stub
    public IDataNode DNStub; // Data Node stub

    public Client() {
        // this.DNStub = GetDNStub("")
        this.NNStub = GetNNStub("NameNode", null, 9090);

    }

    public IDataNode GetDNStub(String Name, String IP, int Port) {
        while (true) {
            try {
                Registry registry = LocateRegistry.getRegistry(IP, Port);
                IDataNode stub = (IDataNode) registry.lookup(Name);
                return stub;
            } catch (Exception e) {
                continue;
            }
        }
    }

    public INameNode GetNNStub(String Name, String IP, int Port) {
        while (true) {
            try {
                Registry registry = LocateRegistry.getRegistry(IP, Port);
                INameNode stub = (INameNode) registry.lookup(Name);
                System.out.println("NameNode Found");
                System.out.println(stub);
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
        BufferedInputStream bis;
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

            // bis = new BufferedInputStream(new FileInputStream(File));
        } catch (Exception e) {
            System.out.println("File not found !!!");
            return;
        }
    }

    public void GetFile(String FileName) {
    }

    public void List() {
    }

    public static void main(String[] args) throws RemoteException, UnknownHostException {
        // To read config file and Connect to NameNode
        // Intitalize the Client
        System.out.println("Started a Client");
        Client Me = new Client();
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
