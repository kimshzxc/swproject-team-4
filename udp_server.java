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

				System.out.println("대기중.....");
				File file = null;
				// 저장할 파일 객체 변수
				// File file = null;
				// 파일에 데이털르 기록할 객체 변수
				DataOutputStream dos = null;
				
				int check_client_f;
				int check_server_f = 0;

				// file은 혼자서 기록, 읽기 못함, 파일은 파일에대한 정보를 주는 클래스,
				// 썻다지웠다하기 위해서는 in,out put stream 필요

				while (true) {
					// Constructs a DatagramPacket for receiving packets of
					// length
					// length.
					DatagramPacket dp = new DatagramPacket(new byte[512], 512);
					ds.receive(dp);
					// 받은 내용을 문자열로 변환
					String filename = new String(dp.getData()).trim();
					int data;

					if (filename.equals("start")) {
						dp = new DatagramPacket(new byte[512], 512);
						ds.receive(dp);
						filename = new String(dp.getData()).trim();
						// 메시지 파일을 받을 수 있는 파일 객체 생성
						file = new File(filename);
						check_server_f = (int)file.length();
						// 파일에 기록할 수 있는 객체 생성
						dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
						// DataOutputStream은 모든 타입 자료형 받을 수 있음
						
					} else if (filename.equals("end")) {
						System.out.println("전송완료");
						
						//해시코드 받음
						dp = new DatagramPacket(new byte[512], 512);
						ds.receive(dp);					
						
						check_client_f = byteArrayToInt(dp.getData());
											
						if(check_server_f == check_client_f){
							System.out.println("전송된 파일이 같습니다!!");
						}else{
							System.out.println("전송된 파일이 같지 않습니다!!");
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