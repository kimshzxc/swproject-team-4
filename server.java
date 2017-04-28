import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.InputStream; 
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
  	        InetSocketAddress isaClient = (InetSocketAddress) socket.getRemoteSocketAddress();

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
  
  public static void main(String[] args) {
    int port = 9999, port2=9998;  //port =  9999;
    int temp;
    String path="";
    server ser=new server();
    System.out.println("client를 기다리는 중...");
    File folder;
    for(int i=0,j=0;true;)
    {
    temp=ser.Activate(port2);
    if(1==temp||2==temp)
    {
    
    if(temp==2)
    {
    folder=new File(path+"folder"+j);
    	if(!folder.exists())
    	{
    		folder.mkdirs();
    		path=path+"folder"+j+++"\\";
    	}
    }
    String filename = path+"test"+i+++".jpeg";

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

