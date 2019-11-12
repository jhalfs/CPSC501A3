import java.io.*;


//This stuff needs to go in the JDOM class to send over the network
public class Sender
{
	
	public static void main(String[] args)
	{
		Document doc;
		String hostname = "127.0.0.1";
		int portNumber = 6666;
	

		//Create objects
		
		//Serialize objects
		Serializer serializer = new Serializer();
		doc = serializer.serialize(objects);
		
		//Open a socket and send the data
		try
		{
			//Open a socket
			Socket socket = new Socket(hostname, portNumber);
			OutputStream socketOut = socket.getOutputStream();
			
			//Transmit the data
			XMLOutputter xmlOut = new XMLOutputter();
			
			xmlOut.output(doc, socketOut);

			//Close the socket
			socket.close();			
		}
		catch(IOException e)
		{
			System.out.println(e);
		}
	}
}

