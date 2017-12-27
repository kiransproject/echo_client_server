//simple single threaded server that outputs a phrase

import java.io.*;
import java.net.*;

public class TestServer {
	
	String what = "hi this is kiran"; // define a string
	BufferedReader bufreader;
	String line;

	public void run() { //public function
		
		try {
			ServerSocket serverSock1 = new ServerSocket(7878);
		
				while(true) {
					Socket sock = serverSock1.accept(); // this blocks and waits for a connection

					InputStreamReader reader = new InputStreamReader(sock.getInputStream());
//					DataInputStream reader = new DataInputStream(sock.getInputStream());
					PrintWriter writer = new PrintWriter(sock.getOutputStream());
					bufreader = new BufferedReader(reader);

					//System.out.println(reader);
					String advice = bufreader.readLine();
							//line = reader.readLine();
					writer.println(advice);
					//writer.println(bufreader);
					//writer.close();
					//System.out.println(what);
				}
		} catch(IOException ex){
				ex.printStackTrace();//Stack Trace is a list of method calls from the point when the application was started to the point where the exception was thrown, most recent calls at the top
		}
	}

	public static void main(String[] args){
			TestServer server = new TestServer();
			server.run();
	}
}
			

