import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JTextArea;

class client {
public static final int DEFAULT_BUFFER_SIZE = 10000;


public void StringTrans(String serverIP,int port3,String line)
{

             try{
            	 Socket sock = new Socket(serverIP, port3);

            	 OutputStream out = sock.getOutputStream();

            	 PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));

                           pw.println(line);
                           pw.flush();

                    pw.close();
                    out.close();
                    sock.close();
             }catch(Exception e){
                    System.out.println(e);
             }
}

	public void Transmit(String serverIP, int port, String FileName)
	{

		File file = new File(FileName);
	    if (!file.exists()) {
	          System.out.println("File not Exist.");
	            System.exit(0);
	        }
	    long fileSize = file.length();
	    long totalReadBytes = 0;
	    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
	    int readBytes;
	    double startTime = 0;
	     
	    try {
	            FileInputStream fis = new FileInputStream(file);
	            Socket socket = new Socket(serverIP, port);
	        if(!socket.isConnected()){
	          System.out.println("Socket Connect Error.");
	              System.exit(0);
	        }
	         
	         
	          OutputStream os = socket.getOutputStream();
	          startTime = System.currentTimeMillis();
	        while ((readBytes = fis.read(buffer)) > 0) {
	          os.write(buffer, 0, readBytes);
	          totalReadBytes += readBytes;
	          System.out.println("In progress: " + totalReadBytes + "/"
	              + fileSize + " Byte(s) ("
	              + (totalReadBytes * 100 / fileSize) + " %)");
	        }
	         
	        System.out.println("File transfer completed.");

	        double endTime = System.currentTimeMillis();
		      double diffTime = (endTime - startTime)/ 1000;;
		      double transferSpeed = (fileSize / 1000)/ diffTime;
		       
		      System.out.println("time: " + diffTime+ " second(s)");
		      System.out.println("Average transfer speed: " + transferSpeed + " KB/s\n");
	        fis.close();
	        os.close();
	        socket.close();
	    } catch (UnknownHostException e) {
	      // TODO Auto-generated catch block
	      e.printStackTrace();
	    } catch (IOException e) {
	      // TODO Auto-generated catch block
	      //e.printStackTrace();
	    }

	}
	
	public void Activate(String serverIP, int port, int a)
	{
	try{
		
        Socket socket = new Socket(serverIP, port);
        if(!socket.isConnected()){
        System.out.println("Socket Connect Error.");
        System.exit(0);
        }

        OutputStream os = socket.getOutputStream();

        os.write(a);
      
        os.close();
        socket.close();
	} catch (UnknownHostException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	}
	
	 
	
	public void Utransmit(String serverIP, int port, String FileName)
	{
		
		      File file = new File(FileName);
		        DatagramSocket ds = null;
		        if (!file.exists()) {
		          System.out.println("File not Exist");
		            System.exit(0);
		        }
		        long fileSize = file.length();
		    long totalReadBytes = 0;
		 
		        double startTime = 0;
		        try {
		            ds = new DatagramSocket();
		            InetAddress serverAdd = InetAddress.getByName(serverIP);
		            FileInputStream fis = new FileInputStream(file);
		            byte[] buffer = new byte[65536];
		            startTime = System.currentTimeMillis();
		              int readBytes = fis.read(buffer, 0, buffer.length);
		              DatagramPacket dp = new DatagramPacket(buffer, readBytes, serverAdd, port);
		                ds.send(dp);

		          System.out.println("In progress: " + readBytes + "/"
		              + fileSize + " Byte(s) ("
		              + (readBytes * 100 / fileSize) + " %)");
		        
		            System.out.println("File transfer completed.");
		            double endTime = System.currentTimeMillis();
		          double diffTime = (endTime - startTime)/ 1000;;
		          double transferSpeed = (fileSize / 1000)/ diffTime;
		           
		          System.out.println("time: " + diffTime+ " second(s)");
		          System.out.println("Average transfer speed: " + transferSpeed + " KB/s\n");
		          
		            fis.close();
		            ds.close();
		  
		        } catch (Exception e) {
		            System.out.println(e.getMessage());
		        }

		}

	
	public void FileFunc(String serverIP,int port,int port2,int port3,String Directory, String temp)
	{
		File dirFile=new File(Directory);
		File[] fileList=dirFile.listFiles();
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].isFile()){
				long Size = fileList[i].length();
				String real=Directory+"\\"+fileList[i].getName();
				Activate(serverIP, port2, 1);
				StringTrans(serverIP, port3, temp+"\\"+fileList[i].getName());
			    if(Size<=65536)
			    {
			    Activate(serverIP, port2, 2);
			    Utransmit(serverIP, port, real);
				}
			    else
			    {
			    Activate(serverIP, port2, 1);	
			    Transmit(serverIP, port, real);
			    }
			}
			else if(fileList[i].isDirectory())
			{
				Activate(serverIP, port2, 4);
				StringTrans(serverIP, port3, temp+"\\"+fileList[i].getName()+'\\');
				String real=Directory+"\\"+fileList[i].getName();
				FileFunc(serverIP, port, port2, port3, real, temp+"\\"+fileList[i].getName());
			}
		}
	}

	
	
  public static void main(String[] args) {

	String serverIP = "127.0.0.1";
    int port = 9999, port2=9998, port3=9997; //port = 9999;

    
    int n;
    String FileName="",Directory="",str="";
    Scanner input=new Scanner(System.in);
    System.out.println("파일 단일 : 1 디렉토리 전송 : 2");
    client cli=new client();
    str=input.next();
    if(str.equals("1"))
    {
    System.out.printf("파일 이름을 입력하세요 : ");	
    Directory=input.next();
    System.out.println("");
	File file = new File(Directory);
    for(int z=Directory.length()-1;z>=0;z--)
    	if(Directory.charAt(z)=='\\')
    	{

    	for(int f=z;f<Directory.length();f++)
    	FileName+=Directory.charAt(f);
    	break;
    	}
    long Size = file.length();
    if(Size>65536)
    {
    
    cli.Activate(serverIP, port2, 1);
    cli.StringTrans(serverIP, port3, FileName);
    cli.Activate(serverIP, port2, 1);
    cli.Transmit(serverIP, port, Directory);
    cli.Activate(serverIP, port2,3);
    }
    else
    {
    cli.Activate(serverIP, port2, 1);
    cli.StringTrans(serverIP, port3, FileName);
    cli.Activate(serverIP, port2, 2);
    cli.Utransmit(serverIP, port,Directory);
    cli.Activate(serverIP, port2,3);
    }
    System.out.println("전송 완료");
    }
  else if(str.equals("2")){
	  Directory=input.next();
	  System.out.println("");
	  System.out.printf("");	
		String temp="";
		for(int i=Directory.length()-1;true;i--)
		{
			if(Directory.charAt(i)=='\\')
			{
				for(int j=i;j<Directory.length();j++)
				temp+=Directory.charAt(j);
			break;
			}
		}
	  cli.Activate(serverIP, port2, 4);
	  cli.StringTrans(serverIP, port3, temp+'\\');
	  cli.FileFunc(serverIP, port, port2, port3, Directory,temp);
	  cli.Activate(serverIP, port2, 3);
	  System.out.println("전송 완료");
	  }
   }
}