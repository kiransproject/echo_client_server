import java.net.*;
import java.io.*;

public class EchoServer {
        
        int portNumber = 7878;

		public void go () {
			try {
				ServerSocket serverSocket = new ServerSocket(portNumber); //listening in on port 7878
			}
				
				Socket clientSocket = serverSocket.accept();//waits for incoming connections and then returns a socket for communicating with the client     
			
		new Thread(
			new EchoRunner(clientSocket)).start();
        /*catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        } */
    

	}

    public static void main(String [] args) {
			EchoServer server = new EchoServer();
			server.go();
	}

}

