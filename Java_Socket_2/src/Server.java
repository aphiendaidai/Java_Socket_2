import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		ServerSocket serverSocket= null;
		Socket clientSocket= null;
		try {
			 serverSocket= new ServerSocket(8888);
		  System.out.println("Server đang chạy...");
			
		  while(true) {
			  clientSocket= serverSocket.accept();
			  System.out.println("Clinet đã kết nối: "+ clientSocket);
			  
			  // luong xu ly clinet
			  ClientHandler clientHandler = new ClientHandler(clientSocket);
			  new Thread(clientHandler).start();
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
				e2.printStackTrace();
			}
		}
	
	}
static class ClientHandler implements Runnable{
	private Socket clientSocket;
	public ClientHandler(Socket socket) {
		this.clientSocket=socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			PrintWriter out= new PrintWriter(clientSocket.getOutputStream(), true);
			for(int i=1; i<=1000; i++) {
				out.println(i);
				Thread.sleep(1000);
			}
			out.close();
			clientSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	}
	
	
	}
