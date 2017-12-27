//simple single threaded server that outputs a phrase

import java.io.*;
import java.net.*;

public class TestServer {
	
	String what = "hi this is kiran"; // define a string

	public void run() { //public function
		
		try {
			ServerSocket serverSock1 = new ServerSocket(7878);
		
				while(true) {
					Socket sock = serverSock1.accept(); // this blocks and waits for a connection

					PrintWriter writer = new PrintWriter(sock.getOutputStream());
					writer.println(what);
					writer.close();
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
			

