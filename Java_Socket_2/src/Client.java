import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		Socket socket= null;
		BufferedReader in= null;
		try {
			socket= new Socket("localhost", 8888);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			String number;
			while((number= in.readLine()) != null) {
				System.out.println(number);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(in != null) {
					in.close();
				}
				if(socket != null) {
					socket.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}
	}

}
