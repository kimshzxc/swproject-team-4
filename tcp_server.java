import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class tcp_server {
	static Socket client = new Socket();

	public static void main(String[] args) throws Exception {
		ServerSocket soc = new ServerSocket(11111); // ��Ʈ 11111�� ������ �����մϴ�.
		System.out.println("Server Start");
		client = soc.accept(); // Ŭ���̾�Ʈ�� ������ �޽��ϴ�.
		System.out.println("client accept!");
		InputStream in = null;
		FileOutputStream out = null;
		in = client.getInputStream(); // Ŭ���̾�Ʈ�� ���� ����Ʈ ������ �Է��� �޴� InputStream��
										// ���� �����մϴ�.
		DataInputStream din = new DataInputStream(in); // InputStream�� �̿��� ������
														
		while (true) {
			int data = din.readInt(); // Int�� �����͸� ���۹޽��ϴ�.
			String filename = din.readUTF();
			long check_client_F = din.readLong();
			long check_server_F;			
			File file = new File(filename); // �Է¹��� File�� �̸����� �����Ͽ� �����մϴ�.
			out = new FileOutputStream(file); // ������ ������ Ŭ���̾�Ʈ�κ��� ���۹޾� �ϼ���Ű��
			
			
			
			int datas = data; // ����Ƚ��, �뷮�� �����ϴ� �����Դϴ�.
			byte[] buffer = new byte[1024]; // ����Ʈ������ �ӽ������ϴ� ���۸� �����մϴ�.
			int len; // ������ �������� ���̸� �����ϴ� �����Դϴ�.

			for (; data > 0; data--) { // ���۹��� data�� Ƚ����ŭ ���۹޾Ƽ�										
				len = in.read(buffer);
				out.write(buffer, 0, len);
			}
			
			check_server_F = file.length();
			System.out.println(check_server_F);

			System.out.println("��: " + datas + " kbps");
			if(check_server_F == check_client_F){
				System.out.println(filename + ": ���Ͽ� �̻��� ����!!");
			}else{
				System.out.println(filename + ": ���Ͽ� �̻��� ����!!");
			}
			
			out.flush();
			out.close();
		}
	}

}