
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
				// UDP전송을 위해 필요한 소켓
				ds = new DatagramSocket();
				InetAddress ia = InetAddress.getByName("127.0.0.1");

				// 데이터 전송 시작 신호 전송
				String str = "start";				
				DatagramPacket dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 8888);
				ds.send(dp);

				// 파일이름 전송
				dp = new DatagramPacket(filename.getBytes(), filename.getBytes().length, ia, 8888);
				ds.send(dp);				

				// 파일을 읽을때는 바이트단위!!
				// 파일을 바이트단위로 읽고, 너무많이 접근하니까이걸가지고 다시 버퍼에 넣는다
				DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filename)));				
				
				FileInputStream fin = new FileInputStream(filename);

				System.out.printf(dis.toString());
				
				// 한번에 다읽으면 부담되기 때문에 적절한크기의 버퍼로 나눔
				byte[] buffer = new byte[512];				
				
				
				int count = 0;
				while (true) {
					// dis 를 읽어 buffer 배열에 0부터 length만큼 저장
					// 읽은 갯수 리턴.
					int x = dis.read(buffer, 0, buffer.length);
					if (x == -1)
						break;
					// 읽은 갯수까지 'x'
					dp = new DatagramPacket(buffer, x, ia, 8888); // *
					ds.send(dp);
					System.out.println(x);

					count++;
				}
				
				System.out.println(count);
				
				// 전송신호전송
				str = "end";
				dp = new DatagramPacket(str.getBytes(), str.getBytes().length, ia, 8888);
				ds.send(dp);
				
				//해시코드 전송
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