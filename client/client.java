import java.io.*;
import java.net.*;
import java.util.Scanner;
import javax.swing.JTextArea;

class client {
public static final int DEFAULT_BUFFER_SIZE = 10000;



public void Utransmit( String hostName, int port, String sourceFilePath)
{
		DatagramSocket socket = null;
		FileEvent event = null;
		String destinationPath = "";
		
		try {

			socket = new DatagramSocket();
			InetAddress IPAddress = InetAddress.getByName(hostName);
			byte[] incomingData = new byte[1024];
			event = getFileEvent(sourceFilePath,destinationPath);
			long fileSize = event.getFileSize();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(outputStream);
			double startTime = System.currentTimeMillis();
			os.writeObject(event);
			double endTime = System.currentTimeMillis();
			byte[] data = outputStream.toByteArray();
			DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, port);
			socket.send(sendPacket);
		      double diffTime = (endTime - startTime)/ 1000;;
		      double transferSpeed = (fileSize / 1000)/ diffTime;
		       
		    System.out.println("time: " + diffTime+ " second(s)");
		    System.out.println("Average transfer speed: " + transferSpeed + " KB/s");
			System.out.println("File sent from client");
			os.close();
			outputStream.close();
			socket.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public FileEvent getFileEvent(String sourceFilePath, String destinationPath) {
		FileEvent fileEvent = new FileEvent();
		String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("/") + 1, sourceFilePath.length());
		String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("/") + 1);
		fileEvent.setDestinationDirectory(destinationPath);
		fileEvent.setFilename(fileName);
		fileEvent.setSourceDirectory(sourceFilePath);
		File file = new File(sourceFilePath);
		if (file.isFile()) {
			try {
				DataInputStream diStream = new DataInputStream(new FileInputStream(file));
				long len = (int) file.length();
				byte[] fileBytes = new byte[(int) len];
				int read = 0;
				int numRead = 0;
				while (read < fileBytes.length
						&& (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
					read = read + numRead;
				}
				fileEvent.setFileSize(len);
				fileEvent.setFileData(fileBytes);
				fileEvent.setStatus("Success");
			} catch (Exception e) {
				e.printStackTrace();
				fileEvent.setStatus("Error");
			}
		} else {
			System.out.println("path specified is not pointing to a file");
			fileEvent.setStatus("Error");
		}
		return fileEvent;
	}

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
				long Size = fileList[i].length();
				String real=Directory+"\\"+fileList[i].getName();
				Activate(serverIP, port2, flag);
				StringTrans(serverIP, port3, temp+"\\"+fileList[i].getName());
			    if(Size>65536)
			    {
			    Activate(serverIP, port2, 1);
			    Transmit(serverIP, port, real);
				}
			    else
			    {
			    Activate(serverIP, port2, 2);	
			    Utransmit(serverIP, port, real);
			    }
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

    
    int n;
    String FileName,Directory,str;
    Scanner input=new Scanner(System.in);
    System.out.println("파일 단일 : 1 디렉토리 전송 : 2");
    client cli=new client();
    str=input.next();
    if(str.equals("1"))
    {
    System.out.printf("파일 이름을 입력하세요 : ");	
    FileName=input.next();
    
	File file = new File(FileName);
    long Size = file.length();
    if(Size>65536)
    {
    
    cli.Activate(serverIP, port2, 1);
    cli.StringTrans(serverIP, port3, FileName);
    cli.Activate(serverIP, port2, 1);
    cli.Transmit(serverIP, port, FileName);
    cli.Activate(serverIP, port2,3);
    }
    else
    {
    cli.Activate(serverIP, port2, 1);
    cli.StringTrans(serverIP, port3, FileName);
    cli.Activate(serverIP, port2, 2);
    cli.Utransmit(serverIP, port, FileName);
    cli.Activate(serverIP, port2,3);
    }
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