//package practice; 
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.*;
import java.util.*;
import javax.sql.*;
public class Server extends Thread{

    int flag = 0;
    private ServerSocket serverSocket;
    
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static Socket server;
    public Server(int port) throws IOException{
        serverSocket = new ServerSocket(port);
    }

    public void run(){
        try{
            InetAddress my_address = InetAddress.getLocalHost();
            System.out.println(my_address);
            server = serverSocket.accept();
            System.out.println("Connected to " + server.getRemoteSocketAddress());
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public static void main(String [] args){
        int port = 6066;
        try{

            Thread connector = new Server(port);
            connector.start();
            try{connector.join();}catch(Exception e){}
            
            
            Thread reader_thread = new Thread(){
                public void run(){
                    while(true){
                        try{
                        	Connection con=DriverManager.getConnection("jdbc:mysql://localhost/serversocket","HariKrish","");
                        	
                            ObjectInputStream in = new ObjectInputStream(server.getInputStream());
                            ArrayList<String> x=new ArrayList<>((ArrayList<String>)in.readObject());
                            PreparedStatement st=con.prepareStatement("INSERT INTO `project` (`First_name`, `Last_name`, `Age`, `Contact`, `Email`, `Place`, `Training`, `Gender`) VALUES (?,?,?,?,?,?,?,?)");
                            for(int y=0;y<x.size();y++)
                            {
                            	st.setString(y+1,x.get(y));
                            }
                            st.execute();
                            System.out.print("Success");
                        }catch(Exception e){
                        	System.out.print(e);
                        }
                    }
                }
            };

            Thread writer_thread = new Thread(){
                public void run(){
                    while(true){
                        try{
                            String message = br.readLine();
                            DataOutputStream out = new DataOutputStream(server.getOutputStream());
                            out.writeUTF(message);
                        }catch(Exception e){}
                    }
                }
            };
            reader_thread.start();
            writer_thread.start();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }
}  
