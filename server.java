import java.io.*;
import java.net.*;
import java.sql.*;
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
    int port = 8888, port2=8887, port3=8886;  //port =  9999;
    server ser=new server();
    int temp=0,s=0,fl=2,n=0;
    String path="",var="",filename="",id="",name="";
    System.out.println("client를 기다리는 중...\n");
    File folder;
    for(;true;)
    {
    temp=ser.Activate(port2);
    if(1==temp)
    {
    fl=1;
    path=ser.StringTrans(port3);
    name="";
    filename="C://apache-tomcat-8.5.12//webapps//soft//img//";
    for(int k=0;k<path.length();k++)
    if(path.charAt(k)=='\\')
    {
       for(int z=k+1;z<path.length();z++)
       filename+=path.charAt(z);
       break;
    }
    
    for(int k=path.length()-1;k>=0;k--)
    if(path.charAt(k)=='\\')
    {
       for(int z=k+1;z<path.length();z++)
       name+=path.charAt(z);
       break;
    }
    s=ser.Activate(port2);
    folder=new File(filename);

    if(folder.exists())
    fl=0;

    if(s==1)
    {
    try {
       
      ServerSocket server = new ServerSocket(port);
        Socket socket = server.accept();  //새로운 연결 소켓 생성 및 accept대기
        InputStream is = socket.getInputStream();
        byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
        if(fl==1)
        {
        System.out.println("This server is listening... (Port: " + port  + ")");
        int readBytes;
        int totalreadBytes=0;
        double startTime = System.currentTimeMillis();
        FileOutputStream fos=new FileOutputStream(filename);
        while ((readBytes = is.read(buffer)) != -1) {
           totalreadBytes+=readBytes;
          fos.write(buffer, 0, readBytes);
        }  
        double endTime = System.currentTimeMillis();
        double diffTime = (endTime - startTime)/ 1000;;
        double transferSpeed = (totalreadBytes / 1000)/ diffTime;
        System.out.println("time: " + diffTime+ " second(s)");
        System.out.println("Average transfer speed: " + transferSpeed + " KB/s");
        System.out.println("File transfer completed.\n");
        System.out.println("전송 완료\n");
        fos.close();

        }
        else if(fl==0)
        while (is.read(buffer)!= -1);
        is.close();
        socket.close();
        server.close();
        Connection con = null;
        Statement stmt = null;
        String driver = "com.mysql.jdbc.Driver";
        String dbURL = "jdbc:mysql://localhost:3306/test";
        
        
      } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    else if(s==2)
    {
          try {
            int nReadSize = 0;
            byte[] buffer = new byte[65536];
            DatagramSocket ds = new DatagramSocket(port);
            DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
              if(fl==1)
              {
            FileOutputStream fos = new FileOutputStream(filename);
              System.out.println("This server is listening... (Port: " + port  + ")");
                  double startTime = System.currentTimeMillis(); 
                    ds.receive(dp);
                    nReadSize = dp.getLength();
                      fos.write(dp.getData(), 0, nReadSize);

                double endTime = System.currentTimeMillis();
                double diffTime = (endTime - startTime)/ 1000;
                double transferSpeed = (nReadSize / 1000)/ diffTime;
                 
                System.out.println("time: " + diffTime+ " second(s)");
                System.out.println("Average transfer speed: " + transferSpeed + " KB/s");
                  System.out.println("File transfer completed.\n");
                  System.out.println("전송 완료\n");
                  fos.close();
              }
              else if(fl==0)
              ds.receive(dp);
              ds.close();
              Connection con = null;
              Statement stmt = null;
              
              
          } catch (Exception e) {}
    }
    }
    else if(3==temp)
    {
    s=0;
    fl=2;
    n=0;
    id="";
    name="";
    path="";
    var="";
    filename="";
    System.out.println("client를 기다리는 중...\n");
    }
    else if(4==temp)
    {
       id=ser.StringTrans(port3);
        var="C://apache-tomcat-8.5.12//webapps//soft//img//"+id;
        folder=new File(var);
        if(!folder.exists())
           folder.mkdirs();
    }
    ser.i=0;
    }
  }

}