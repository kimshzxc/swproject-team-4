import java.io.File;
import java.io.PrintWriter;
import java.io.OutputStreamWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
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
	         
	        startTime = System.currentTimeMillis(); 
	          OutputStream os = socket.getOutputStream();
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
		      System.out.println("Average transfer speed: " + transferSpeed + " KB/s");
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
	
	public void FileFunc(String serverIP,int port,int port2,int port3,String Directory, String temp)
	{
		File dirFile=new File(Directory);
		File[] fileList=dirFile.listFiles();
		int flag=0;
		for(int i=0;i<fileList.length;i++){
			if(fileList[i].isFile()){
				if(i==0)
				flag=2;
				else
				flag=1;
				String real=Directory+"\\"+fileList[i].getName();
				Activate(serverIP, port2, flag);
				StringTrans(serverIP, port3, temp+"\\"+fileList[i].getName());
			  	Transmit(serverIP, port, real);
			}
			else if(fileList[i].isDirectory())
			{
				String real=Directory+"\\"+fileList[i].getName();
				FileFunc(serverIP, port, port2, port3, real, temp+"\\"+fileList[i].getName());
			}
		}
	}

	
	
  public static void main(String[] args) {

	String serverIP = "127.0.0.1";//"114.70.193.139";
    int port = 9999, port2=9998, port3=9997; //port = 9999;
    System.out.println("UDP전송 : 1  TCP전송 : 2");
    Scanner s =new Scanner(System.in);
    int protocol = s.nextInt();
    if(protocol == 1){
    	UClient client = new UClient(serverIP,9876);
		Scanner input=new Scanner(System.in);
		System.out.printf("파일 이름을 입력하세요 : ");
		String str=input.next();
		client.setsd(str);
		client.createConnection();
    	
    }
    
    
    else{
    client cli=new client();
    int n;
    String FileName,Directory,str;
    Scanner input=new Scanner(System.in);
    System.out.println("파일 단일 : 1 디렉토리 전송 : 2");
    str=input.next();
    if(str.equals("1"))
    {
    System.out.printf("파일 이름을 입력하세요 : ");	
    FileName=input.next();
    cli.Activate(serverIP, port2, 1);
    cli.StringTrans(serverIP, port3, FileName);
    cli.Transmit(serverIP, port, FileName);
    cli.Activate(serverIP, port2,3);
    }
  else if(str.equals("2")){
	  Directory=input.next();
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
	  cli.FileFunc(serverIP, port, port2, port3, Directory,temp);
	  cli.Activate(serverIP, port2, 3);
	  System.out.println("전송 완료");
	  }
    }
   }
}