import java.net.*;
import java.io.*;

public class EchoServer {
        
		protected int portNumber = 7878;
		protected boolean isStopped = false;
		protected ServerSocket serverSocket = null;
		protected Thread runningThread = null;


		public void go () {
				synchronized(this){
						this.runningThread = Thread.currentThread();
				}
			openServerSocket();
			while (! isStopped()){
					Socket clientSocket = null;
					try {
							clientSocket = this.serverSocket.accept();
					} catch ( IOException e) {
							if (isStopped()){
									System.out.println("Server Stopped.");
									return;
							}
							throw new RuntimeException("Error accepting Client connection", e);
					}
		new Thread(
			new EchoRunner(clientSocket)).start();
			}
			System.out.println("server stopped.");
		}
		
        /*catch (IOException e) {
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
       } */
    

		public synchronized void stop(){
				this.isStopped = true;
				try {
					this.serverSocket.close();
				} catch (IOException e) {
						throw new RuntimeException("Error closing server", e);
				}
		}
		
		private void openServerSocket() {
				try {
					this.serverSocket = new ServerSocket(this.portNumber);
				} catch (IOException e) {
					throw new RuntimeException("Cannot open port 7878", e);
				}
		}

		private synchronized boolean isStopped() {
			return this.isStopped;
		}

		public static void main(String [] args) {
			EchoServer server = new EchoServer();
			server.go();
		}

}

