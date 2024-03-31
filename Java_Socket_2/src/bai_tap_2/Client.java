package bai_tap_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
	 private static final String SERVER_ADDRESS = "localhost";
	    private static final int SERVER_PORT = 8888;
	    public static void main(String[] args) {
	    	  Socket socket = null;
	          BufferedReader in= null;
	          PrintWriter out = null;
	          BufferedReader consoleReader = null;
	          
	          try {
				socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
				in= new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out= new PrintWriter(socket.getOutputStream(), true);
				consoleReader= new BufferedReader(new InputStreamReader(System.in));
				
				//nhập username
				System.out.println(in.readLine());
				String username= consoleReader.readLine();
				out.println(username);
				
				final BufferedReader fReader= in;
				//đọc tin nhắn từ server
				new Thread(() ->{
					String serverMessage;
					
					 try {
		                    while((serverMessage= fReader.readLine()) != null) {
		                        System.out.println(serverMessage);
		                    }
		                } catch (Exception e) {
		                    e.printStackTrace();
		                }
		            }).start();
	            String clientMessage;
	            while((clientMessage= consoleReader.readLine()) != null) {
	            	if (!clientMessage.isEmpty()) { // Kiểm tra clientMessage có rỗng hay không
	                    out.println(clientMessage);
	                }
	            }
			} catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					 if (in != null) {
		                    in.close();
		                }
		                if (out != null) {
		                    out.close();
		                }
		                if (consoleReader != null) {
		                    consoleReader.close();
		                }
		                if (socket != null) {
		                    socket.close();
		                }
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

		}
}
