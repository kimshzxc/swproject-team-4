import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class tcp_server {
	static Socket client = new Socket();

	public static void main(String[] args) throws Exception {
		ServerSocket soc = new ServerSocket(11111); // 포트 11111로 서버를 개통합니다.
		System.out.println("Server Start");
		client = soc.accept(); // 클라이언트의 접속을 받습니다.
		System.out.println("client accept!");
		InputStream in = null;
		FileOutputStream out = null;
		in = client.getInputStream(); // 클라이언트로 부터 바이트 단위로 입력을 받는 InputStream을
										// 얻어와 개통합니다.
		DataInputStream din = new DataInputStream(in); // InputStream을 이용해 데이터
														
		while (true) {
			int data = din.readInt(); // Int형 데이터를 전송받습니다.
			String filename = din.readUTF();
			long check_client_F = din.readLong();
			long check_server_F;			
			File file = new File(filename); // 입력받은 File의 이름으로 복사하여 생성합니다.
			out = new FileOutputStream(file); // 생성한 파일을 클라이언트로부터 전송받아 완성시키는
			
			
			
			int datas = data; // 전송횟수, 용량을 측정하는 변수입니다.
			byte[] buffer = new byte[1024]; // 바이트단위로 임시저장하는 버퍼를 생성합니다.
			int len; // 전송할 데이터의 길이를 측정하는 변수입니다.

			for (; data > 0; data--) { // 전송받은 data의 횟수만큼 전송받아서										
				len = in.read(buffer);
				out.write(buffer, 0, len);
			}
			
			check_server_F = file.length();
			System.out.println(check_server_F);

			System.out.println("약: " + datas + " kbps");
			if(check_server_F == check_client_F){
				System.out.println(filename + ": 파일에 이상이 없음!!");
			}else{
				System.out.println(filename + ": 파일에 이상이 있음!!");
			}
			
			out.flush();
			out.close();
		}
	}

}