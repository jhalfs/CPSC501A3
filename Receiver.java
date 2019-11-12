import java.io.InputStream;
import java.net.*;

import org.jdom2.*;

//This stuff needs to call the deserializer
public class Receiver
{
	public static void main(String args[])
	{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket socket = serverSocket.accept();
			
			InputStream socketIn = socket.getInputStream();
			
			//Turn input into Document
			SAXBuilder saxBuild = new SAXBuilder();
			
			doc = saxBuild.build(socketIn);			
	}
}
