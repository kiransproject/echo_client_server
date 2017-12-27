import java.io.*;
import java.net.*;

public class EchoRunner implements Runnable{
		protected Socket clientSocket = null;

//		BufferedReader in;
//		PrintWriter out = new PrinterWriter();
	 	String inputLine;

		public EchoRunner(Socket clientSocket){
				this.clientSocket = clientSocket;
		}

		public void run() {
				try {
					PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);                   
					BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					while ((inputLine = in.readLine()) != null) {
						out.println(inputLine);
					}
				} catch(IOException e) {
						e.printStackTrace();
				}
		}
}


