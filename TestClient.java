import java.io.*;
import java.net.*;

public class TestClient{

	public void run_client() {
		try {
				Socket s = new Socket("127.0.0.1", 7878);

				InputStreamReader streamReader = new InputStreamReader(s.getInputStream());
				BufferedReader reader = new BufferedReader(streamReader);
				
				String test = reader.readLine();
				System.out.println("this was returned " + test);

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

