import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class udp_server {

	public static  int byteArrayToInt(byte bytes[]) {
		return ((((int)bytes[0] & 0xff) << 24) |
				(((int)bytes[1] & 0xff) << 16) |
				(((int)bytes[2] & 0xff) << 8) |
				(((int)bytes[3] & 0xff)));
	} 

	public static void main(String[] args) {

		while (true) {
			try {
				DatagramSocket ds = new DatagramSocket(8888);

				System.out.println("�����.....");
				File file = null;
				// ������ ���� ��ü ����
				// File file = null;
				// ���Ͽ� �����и� ����� ��ü ����
				DataOutputStream dos = null;
				
				int check_client_f;
				int check_server_f = 0;

				// file�� ȥ�ڼ� ���, �б� ����, ������ ���Ͽ����� ������ �ִ� Ŭ����,
				// �����������ϱ� ���ؼ��� in,out put stream �ʿ�

				while (true) {
					// Constructs a DatagramPacket for receiving packets of
					// length
					// length.
					DatagramPacket dp = new DatagramPacket(new byte[512], 512);
					ds.receive(dp);
					// ���� ������ ���ڿ��� ��ȯ
					String filename = new String(dp.getData()).trim();
					int data;

					if (filename.equals("start")) {
						dp = new DatagramPacket(new byte[512], 512);
						ds.receive(dp);
						filename = new String(dp.getData()).trim();
						// �޽��� ������ ���� �� �ִ� ���� ��ü ����
						file = new File(filename);
						check_server_f = (int)file.length();
						// ���Ͽ� ����� �� �ִ� ��ü ����
						dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
						// DataOutputStream�� ��� Ÿ�� �ڷ��� ���� �� ����
						
					} else if (filename.equals("end")) {
						System.out.println("���ۿϷ�");
						
						//�ؽ��ڵ� ����
						dp = new DatagramPacket(new byte[512], 512);
						ds.receive(dp);					
						
						check_client_f = byteArrayToInt(dp.getData());
											
						if(check_server_f == check_client_f){
							System.out.println("���۵� ������ �����ϴ�!!");
						}else{
							System.out.println("���۵� ������ ���� �ʽ��ϴ�!!");
						}
						
						System.out.println(check_server_f);
						
						dos.close();
						ds.close();
						break;
					} else {						
						dos.write(filename.getBytes(), 0, filename.getBytes().length);
					}
				}
			} catch (Exception e) {

			}
		}

	}

}