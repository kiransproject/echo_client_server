import java.io.*;
import java.net.*;

public class TestClient{

	String test1 = "test";

	public void run_client() {
		try {
				Socket s = new Socket("127.0.0.1", 7878);
				
				PrintWriter writer = new PrintWriter(s.getOutputStream());
				InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
				BufferedReader reader = new BufferedReader(streamReader);
				
				writer.println(test1);
				writer.flush();

				String test = reader.readLine();
				System.out.println("this was returned " + test);
				writer.close();
				reader.close();
		}
		catch (IOException ex) {
				ex.printStackTrace();
		}
	}

	public static void main(String[] args) {
			TestClient test = new TestClient();
			test.run_client();
	}
}

