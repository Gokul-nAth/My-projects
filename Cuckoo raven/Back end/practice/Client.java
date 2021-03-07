//package practice;
import java.io.*;
import java.net.*;
import java.util.*;


/*@WebServlet("/C")//http://localhost:6700/server1/servlet/practice.Client$C
class C extends HttpServlet{
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		System.out.print(request.getParameter("fname"));
	}
}*/
class Client extends Thread{
	final static ArrayList<String> al=new ArrayList<>();
	
    public static void main(String args[]){
        try{
        	
            final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            InetAddress my_address = InetAddress.getLocalHost();
            System.out.println("Enter the IP Address of server ");

            String address = br.readLine();
            final Socket client = new Socket(address,6066);

            Thread reader_thread = new Thread(){
                public void run(){
                    while(true){
                        try{
                            InputStream from_server = client.getInputStream();
                            DataInputStream in = new DataInputStream(from_server);
                            System.out.println("Him: " + in.readUTF());
                        }
                        catch(Exception e){}
                    }
                }
            };

            Thread writer_thread = new Thread(){
                public void run(){
                	//Scanner input=new Scanner(System.in);
                	
                	
                    while(true){
                        try{
                        	for(int h=0;h<8;h++)
                        	{
                        	al.add(br.readLine());
                        	}
                            //final String message = br.readLine();
                            OutputStream to_send = client.getOutputStream();
                            //DataOutputStream out = new DataOutputStream(to_send);
                            ObjectOutputStream out=new ObjectOutputStream(to_send);
                            out.writeObject(al);
                            al.clear();
                        }
                        catch(Exception e){}
                    }
                }
            };

            reader_thread.start();
            writer_thread.start();
        }
        catch(Exception e){
            System.out.println("Houston.. we have a problem " + e);
        }
    }
}  