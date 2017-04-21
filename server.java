import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
 
public class server {
    
    static Socket client = new Socket();
    
    public static void main(String[] args) throws Exception{
        ServerSocket soc = new ServerSocket(11111);  //��Ʈ 11111�� ������ �����մϴ�.
        System.out.println("Server Start");
        client = soc.accept();                       //Ŭ���̾�Ʈ�� ������ �޽��ϴ�.
        System.out.println("client accept!");
        InputStream in = null;                       
        FileOutputStream out = null;
        in = client.getInputStream();                //Ŭ���̾�Ʈ�� ���� ����Ʈ ������ �Է��� �޴� InputStream�� ���� �����մϴ�.
        DataInputStream din = new DataInputStream(in);  //InputStream�� �̿��� ������ ������ �Է��� �޴� DataInputStream�� �����մϴ�.
        
        while(true){
            int data = din.readInt();           //Int�� �����͸� ���۹޽��ϴ�.
        String filename = din.readUTF();            //String�� �����͸� ���۹޾� filename(������ �̸����� ����)�� �����մϴ�.
        File file = new File(filename);             //�Է¹��� File�� �̸����� �����Ͽ� �����մϴ�.
        out = new FileOutputStream(file);           //������ ������ Ŭ���̾�Ʈ�κ��� ���۹޾� �ϼ���Ű�� FileOutputStream�� �����մϴ�.
 
        int datas = data;                            //����Ƚ��, �뷮�� �����ϴ� �����Դϴ�.
        byte[] buffer = new byte[1024];        //����Ʈ������ �ӽ������ϴ� ���۸� �����մϴ�.
        int len;                               //������ �������� ���̸� �����ϴ� �����Դϴ�.
        
        
        for(;data>0;data--){                   //���۹��� data�� Ƚ����ŭ ���۹޾Ƽ� FileOutputStream�� �̿��Ͽ� File�� �ϼ���ŵ�ϴ�.
            len = in.read(buffer);
            out.write(buffer,0,len);
        }
        
        System.out.println("��: "+datas+" kbps");
        out.flush();
        out.close();
        }
    }
 
}