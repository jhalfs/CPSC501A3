import java.io.IOException;
import java.io.InputStream;
import java.net.*;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;

//This stuff needs to call the deserializer
public class Receiver
{
	public static void main(String args[])
	{
		int portNumber = 6666;
		try{
			ServerSocket serverSocket = new ServerSocket(portNumber);
			Socket socket = serverSocket.accept();
			
			InputStream socketIn = socket.getInputStream();

			//Turn input into Document
			SAXBuilder saxBuild = new SAXBuilder();
			
			Document doc = saxBuild.build(socketIn);		
			Deserialize ds = new Deserialize(doc);
			serverSocket.close();
		}catch(IOException | JDOMException e){
			
		}
	}
}
