package client;

import java.awt.TextArea;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

import javax.swing.JTextArea;
 
public class client {
  public static final int DEFAULT_BUFFER_SIZE = 10000;
  public static void main(JTextArea text) {
    String serverIP = "127.0.0.1";
    int port = 9999; //port = 9999;
    String FileName;        //String FileName = "test.mp4";
    Scanner s = new Scanner(System.in); 
    FileName = s.nextLine();
    
    
    File file = new File(FileName);
    System.out.println(file.hashCode());
    if (!file.exists()) {
          text.append("File not Exist.\n");
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
          text.append("Socket Connect Error.\n");
              System.exit(0);
        }
         
        startTime = System.currentTimeMillis(); 
          OutputStream os = socket.getOutputStream();
        while ((readBytes = fis.read(buffer)) > 0) {
          os.write(buffer, 0, readBytes);
          totalReadBytes += readBytes;
          text.append("In progress: " + totalReadBytes + "/"
              + fileSize + " Byte(s) ("
              + (totalReadBytes * 100 / fileSize) + " %)\n");
        }
         
        text.append("File transfer completed.\n");
        fis.close();
        os.close();
        socket.close();
    } catch (UnknownHostException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
       
      double endTime = System.currentTimeMillis();
      double diffTime = (endTime - startTime)/ 1000;;
      double transferSpeed = (fileSize / 1000)/ diffTime;
       
      text.append("time: " + diffTime+ " second(s)\n");
      text.append("Average transfer speed: " + transferSpeed + " KB/s\n");
  	} 
}