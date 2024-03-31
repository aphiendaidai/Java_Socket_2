package bai_tap_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class Server {
	private static final int PORT= 888;
	private static final Map<Socket, String> clinets= new HashMap<Socket, String>();
	public static void main(String[] args) {
		ServerSocket serverSocket= null;
		try {
			serverSocket= new ServerSocket(PORT);
			System.out.println("Server đang chạy....");
			while(true) {
				Socket clinetSocket= serverSocket.accept();
				System.out.println("Client đã kết nối: "+ clinetSocket);
				
				ClientHandler clientHandler= new ClientHandler(clinetSocket);
				new Thread(clientHandler);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally {
			try {
				if(serverSocket != null) {
					serverSocket.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}
	static class ClientHandler implements Runnable{
		private Socket clientSoket ;
		private BufferedReader in;
		private PrintWriter out;
		private String username;
		public ClientHandler(Socket socket) {
			this.clientSoket= socket;
			
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				in = new BufferedReader(new InputStreamReader(clientSoket.getInputStream()));
				out= new PrintWriter(clientSoket.getOutputStream(), true);
				
// nhập username
				out.println("Nhập tên bạn: ");
				username= in.readLine();
				clinets.put(clientSoket, username);
				broadcastMessage(username + "Gia nhập vào nhóm chat.");
				String clientMessage;
				while((clientMessage= in.readLine()) != null) {
                    broadcastMessage(username + ": " + clientMessage);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}finally {
				try {
					if(in !=null) {
						in.close();
					}
					if(out != null) {
						in.close();
					}
					clientSoket.close();
					clinets.remove(clientSoket);
					broadcastMessage(username + "đã rời khỏi cuộc trò chuyện.");
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
			
		}
		private void broadcastMessage(String message) {
			// TODO Auto-generated method stub
			for(Socket socket : clinets.keySet()) {
				try {
					PrintWriter socketout= new PrintWriter(socket.getOutputStream(), true);
					socketout.println(message);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
		}
	}
}
