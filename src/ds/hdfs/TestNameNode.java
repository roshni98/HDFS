package ds.hdfs;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestNameNode{

    public static void main(String[] args){
        try{
            Registry registry = LocateRegistry.getRegistry(9090);
            INameNode nameNode = (INameNode) registry.lookup("NameNode");
            String response = nameNode.printMsg("Heartbeat Running");
            System.out.println(response);
        } catch(RemoteException | NotBoundException e){
            e.printStackTrace();
        }
    }
}