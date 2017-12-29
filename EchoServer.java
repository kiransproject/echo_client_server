import java.net.*;
import java.io.*;
import java.util.logging.*;
//import java.util.scanner.*;

public class EchoServer {
        
		protected int portNumber = 7878;
		protected boolean isStopped = false;
		protected ServerSocket serverSocket = null;
		protected Thread runningThread = null;
		private static final Logger logger = Logger.getLogger(EchoServer.class.getName()); //static as there is only one logger per class, final variable can only be assigned once


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
									logger.info("Server Stopped haha.");
									//System.out.println("Server Stopped.");
									return;
							}
							logger.log(Level.SEVERE,"Error accepting Client connection",e);
							//throw new RuntimeException("Error accepting Client connection", e);
					}
		new Thread(
			new EchoRunner(clientSocket)).start();
			logger.info("new client connected");
			}
			logger.info("Server Stopped.");
		//	System.out.println("server stopped.");
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
						logger.log(Level.SEVERE, "Error closing server", e);
						//throw new RuntimeException("Error closing server", e);
				}
		}
		
		private void openServerSocket() {
				try {
					this.serverSocket = new ServerSocket(this.portNumber);
				} catch (IOException e) {
					//throw new RuntimeException("Cannot open port 7878", e);
					logger.log(Level.SEVERE,"Cannot open port 7878",e);
				}
		}

		/*private void closeServerSocket() {
				try {
						this.serverSocket.close();
				} catch (IOException e) {
					//throw new RuntimeException("Cannot open port 7878", e);
					logger.log(Level.SEVERE,"Cannot close connection",e);
				}
		}*/

		private synchronized boolean isStopped() {
			return this.isStopped;
		}

		public static void main(String [] args) { //static only belongs to the class
				logger.info("Logging begins...."); //default logging to console
				EchoServer server = new EchoServer();
				server.go();
		}

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
								//while (true) {
								//		final String line = in.readLine();
								//		if (line == null) break;
								//while (inputLine.hasNextLine()) {
								while((inputLine = in.readLine()) != null) {
										logger.info("incoming characters");
										out.println(inputLine);
							}
							logger.info("Client Disconnected");
						} catch(IOException e) {
								logger.log(Level.SEVERE,"Exception is ",e);
								//e.printStackTrace();
						}
				}

		}


}

