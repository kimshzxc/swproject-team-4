
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class udp_client {
	
	public static  byte[] intToByteArray(int value) {
		byte[] byteArray = new byte[4];
		byteArray[0] = (byte)(value >> 24);
		byteArray[1] = (byte)(value >> 16);
		byteArray[2] = (byte)(value >> 8);
		byteArray[3] = (byte)(value);
		return byteArray;
	}

	public static void main(String[] args) {
		OutputStream out;

		while (true) {
			Scanner s = new Scanner(System.in);
			String filename = s.next();
			File file = new File(filename);

			DatagramSocket ds = null;	

			try {
				// UDP������ ���� �ʿ��� ����
				ds = new DatagramSocket();
				InetAddress ia = InetAddress.getByName("127.0.0.1");

				// ������ ���� ���� ��ȣ ����
				String str = "start";				
				DatagramPacket dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 8888);
				ds.send(dp);

				// �����̸� ����
				dp = new DatagramPacket(filename.getBytes(), filename.getBytes().length, ia, 8888);
				ds.send(dp);				

				// ������ �������� ����Ʈ����!!
				// ������ ����Ʈ������ �а�, �ʹ����� �����ϴϱ��̰ɰ����� �ٽ� ���ۿ� �ִ´�
				DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));				
				
				FileInputStream fin = new FileInputStream(filename);

				System.out.printf(dis.toString());
				
				// �ѹ��� �������� �δ�Ǳ� ������ ������ũ���� ���۷� ����
				byte[] buffer = new byte[512];				
				
				
				int count = 0;
				while (true) {
					// dis �� �о� buffer �迭�� 0���� length��ŭ ����
					// ���� ���� ����.
					int x = dis.read(buffer, 0, buffer.length);
					if (x == -1)
						break;
					// ���� �������� 'x'
					dp = new DatagramPacket(buffer, x, ia, 8888); // *
					ds.send(dp);
					System.out.println(x);

					count++;
				}
				
				System.out.println(count);
				
				// ���۽�ȣ����
				str = "end";
				dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 8888);
				ds.send(dp);
				
				//�ؽ��ڵ� ����
				System.out.println((int)file.length());
				dp = new DatagramPacket(intToByteArray((int)file.length()), intToByteArray((int)file.length()).length, ia, 8888);
				ds.send(dp);

				dis.close();
				ds.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}

	}

}