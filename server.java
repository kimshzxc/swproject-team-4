import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
public class server {
    
    static Socket client = new Socket();
    
    public static void main(String[] args) throws Exception{
        ServerSocket soc = new ServerSocket(11111);  //포트 11111로 서버를 개통합니다.
        System.out.println("Server Start");
        client = soc.accept();                       //클라이언트의 접속을 받습니다.
        System.out.println("client accept!");
        InputStream in = null;                       
        FileOutputStream out = null;
        in = client.getInputStream();                //클라이언트로 부터 바이트 단위로 입력을 받는 InputStream을 얻어와 개통합니다.
        DataInputStream din = new DataInputStream(in);  //InputStream을 이용해 데이터 단위로 입력을 받는 DataInputStream을 개통합니다.
        
        while(true){
            int data = din.readInt();           //Int형 데이터를 전송받습니다.
        String filename = din.readUTF();            //String형 데이터를 전송받아 filename(파일의 이름으로 쓰일)에 저장합니다.
        File file = new File(filename);             //입력받은 File의 이름으로 복사하여 생성합니다.
        out = new FileOutputStream(file);           //생성한 파일을 클라이언트로부터 전송받아 완성시키는 FileOutputStream을 개통합니다.
 
        int datas = data;                            //전송횟수, 용량을 측정하는 변수입니다.
        byte[] buffer = new byte[1024];        //바이트단위로 임시저장하는 버퍼를 생성합니다.
        int len;                               //전송할 데이터의 길이를 측정하는 변수입니다.
        
        
        for(;data>0;data--){                   //전송받은 data의 횟수만큼 전송받아서 FileOutputStream을 이용하여 File을 완성시킵니다.
            len = in.read(buffer);
            out.write(buffer,0,len);
        }
        
        System.out.println("약: "+datas+" kbps");
        out.flush();
        out.close();
        }
    }
 
}