import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;

public class server {
  public static final int DEFAULT_BUFFER_SIZE = 10000;
  public int i=0;

  public int Activate(int port)
  {		
  	
  	  try {
  	      ServerSocket server = new ServerSocket(port);
  	        Socket socket = server.accept();  //새로운 연결 소켓 생성 및 accept대기
  	        InputStream is = socket.getInputStream();

  	        i=is.read();
  			is.close();
  	        socket.close();
  	        server.close();
  	      } catch (IOException e) {
  	        // TODO Auto-generated catch block
  	        e.printStackTrace();
  	      }
  	  return i;
}
  

public String StringTrans(int port3)
{
	
	String line=null;
             try{
            	 ServerSocket server = new ServerSocket(port3);
            	 Socket sock = server.accept();

            	 InputStream in = sock.getInputStream();

            	 BufferedReader br = new BufferedReader(new InputStreamReader(in));

                    line = br.readLine();
                    in.close();
                    br.close();
                    sock.close();
                    server.close();
             } catch(Exception e){

                    System.out.println(e);

             }
             
             return line;
}
  
  

  
  public static void main(String[] args) {
    int port = 9999, port2=9998,port3=9997;  //port =  9999;
    server ser=new server();
    int temp;
    String path,var,filename;
    System.out.println("client를 기다리는 중...");
    File folder;
    for(int i=0,j=0;true;)
    {
    temp=ser.Activate(port2);
    if(1==temp||2==temp)
    {
    path=ser.StringTrans(port3);
    if(temp==2)
    {
    var="";
    for(int k=0;k<path.length();k++)
    if(path.charAt(k)=='\\')
    {
    	for(int z=path.length()-1;z>=0;z--)
    	if(path.charAt(z)=='\\')
    	{

    	for(int f=k+1;f<z;f++)
    	var+=path.charAt(f);
    	
    	break;
    	}
    	break;
    }
    System.out.println(var);
    folder=new File(var);
    if(!folder.exists())
    	folder.mkdirs();
    }
    filename="";
    for(int k=0;k<path.length();k++)
    if(path.charAt(k)=='\\')
    {
    	for(int z=k+1;z<path.length();z++)
    	filename+=path.charAt(z);
    	break;
    }
    
    try {
      ServerSocket server = new ServerSocket(port);
        System.out.println("This server is listening... (Port: " + port  + ")");
        Socket socket = server.accept();  //새로운 연결 소켓 생성 및 accept대기
        InetSocketAddress isaClient = (InetSocketAddress) socket.getRemoteSocketAddress();
         
        System.out.println("A client("+isaClient.getAddress().getHostAddress()+
            " is connected. (Port: " +isaClient.getPort() + ")");
         
        FileOutputStream fos = new FileOutputStream(filename);
        InputStream is = socket.getInputStream();
         
        double startTime = System.currentTimeMillis(); 
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        int readBytes;
        while ((readBytes = is.read(buffer)) != -1) {
          fos.write(buffer, 0, readBytes);
 
        }  
        double endTime = System.currentTimeMillis();
        double diffTime = (endTime - startTime)/ 1000;;
 
        System.out.println("time: " + diffTime+ " second(s)");
         
        is.close();
        fos.close();
        socket.close();
        server.close();
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    ser.i=0;
    }
    else if(3==temp)
    break;
    
    }
    System.out.println("전송 완료");
  }
  
} 

