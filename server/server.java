import java.io.*;
import java.net.*;

public class server {
  public static final int DEFAULT_BUFFER_SIZE = 10000;
  public int i=0;
		

	public void createAndWriteFile(FileEvent fileEvent,double startTime,String outputFile) {
		double diffTime;
		double endTime;

		if (!new File(fileEvent.getDestinationDirectory()).exists()) {
			new File(fileEvent.getDestinationDirectory()).mkdirs();
		}
		File dstFile = new File(outputFile);
		FileOutputStream fileOutputStream = null;
		try {
			fileOutputStream = new FileOutputStream(dstFile);
			fileOutputStream.write(fileEvent.getFileData());
			endTime = System.currentTimeMillis();
			fileOutputStream.flush();
			fileOutputStream.close();
	        diffTime = (endTime - startTime)/ 1000;;
	        System.out.println("time: " + diffTime+ " second(s)");
			System.out.println("Output file : " + outputFile + " is successfully saved ");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}


  
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
    int port = 9999, port2=9998, port3=9997;  //port =  9999;
    server ser=new server();
    int temp,s;
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
    s=ser.Activate(port2);
    folder=new File(filename);
    if(folder.exists())
    {
    	ser.i=0;
    	continue;
    }
    if(s==1)
    {
    try {
    	double startTime = System.currentTimeMillis();
      ServerSocket server = new ServerSocket(port);
        System.out.println("This server is listening... (Port: " + port  + ")");
        Socket socket = server.accept();  //새로운 연결 소켓 생성 및 accept대기
        InetSocketAddress isaClient = (InetSocketAddress) socket.getRemoteSocketAddress();
        
        System.out.println("A client("+isaClient.getAddress().getHostAddress()+
            " is connected. (Port: " +isaClient.getPort() + ")");
        
        FileOutputStream fos=new FileOutputStream(filename);
        
        InputStream is = socket.getInputStream(); 
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
    }
    else if(s==2)
    {
    	try {
			DatagramSocket socket = null;
			FileEvent fileEvent = null;
			socket = new DatagramSocket(port);
			double startTime;
			byte[] incomingData = new byte[1024 * 1000 * 50];
				DatagramPacket incomingPacket = new DatagramPacket(incomingData, incomingData.length);
				socket.receive(incomingPacket);
				byte[] data = incomingPacket.getData();
				ByteArrayInputStream in = new ByteArrayInputStream(data);
				ObjectInputStream is = new ObjectInputStream(in);
				fileEvent = (FileEvent) is.readObject();
				if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
					System.out.println("Some issue happened while packing the data @ client side");
					System.exit(0);
				}
				startTime=System.currentTimeMillis();
				ser.createAndWriteFile(fileEvent,startTime,filename);
			    is.close();
			    in.close();
			    socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }
    ser.i=0;
    }
    else if(3==temp)
    break;
    
    }
    System.out.println("전송 완료");
  }

}

