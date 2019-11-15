import org.jdom2.*;
import org.jdom2.output.XMLOutputter;

import mypackage.Movie;

import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class JDOM {

	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		String hostname = "127.0.0.1";
		int portNumber = 6666;
		
		//The Object builder goes here, so we can pass the proper object(s) to the serializer
		Serialize serializer = new Serialize();

		
		Element root = new Element("serialized");
		System.out.println(args[0]);
		if(args[0].equals("1")){
			serializer.serialize(SimpleObject(), root);
		}else if(args[0].equals("2")){
			serializer.serialize(References(), root);
		}else if(args[0].equals("3")){
			serializer.serialize(SimpleArray(), root);
		}else if(args[0].equals("4")){
			serializer.serialize(ReferencesArray(), root);
		}else if(args[0].equals("5")){
			serializer.serialize(ReferencesCollection(), root);
		}else{
			System.out.println("Usage: java JDOM (1-5)");
			System.exit(1);
		}
		
		
		DocType type = new DocType("Objects");
		Document doc = new Document(root, type);
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
	
	public static Object SimpleObject(){
		System.out.println("Please enter a string");
		Scanner in = new Scanner(System.in);
		String title = in.nextLine();
		System.out.println("Please enter a number");
		int num = Integer.parseInt(in.nextLine());
		System.out.println("Please enter another number");
		int numTwo = Integer.parseInt(in.nextLine());
		in.close();
		SimpleObjectClass soc = new SimpleObjectClass(title, num, numTwo);
		return soc;
	}
	public static Object References(){
		System.out.println("Please enter a string");
		Scanner in = new Scanner(System.in);
		String title = in.nextLine();
		System.out.println("Please enter a number");
		int num = Integer.parseInt(in.nextLine());
		System.out.println("Please enter another number");
		int numTwo = Integer.parseInt(in.nextLine());
		in.close();
		ReferencesClass rc = new ReferencesClass(title, num, numTwo);
		return rc;
	}
	public static Object SimpleArray(){
		System.out.println("Please enter number");
		Scanner in = new Scanner(System.in);
		int num = Integer.parseInt(in.nextLine());
		System.out.println("Please enter another number");
		int numTwo = Integer.parseInt(in.nextLine());
		System.out.println("Please enter the final number");
		int numThree = Integer.parseInt(in.nextLine());
		in.close();
		SimpleArrayClass sac = new SimpleArrayClass(num, numTwo, numThree);
		return sac;
	}
	public static Object ReferencesArray(){
		System.out.println("Please enter a string");
		Scanner in = new Scanner(System.in);
		String title = in.nextLine();
		System.out.println("Please enter a number");
		int num = Integer.parseInt(in.nextLine());
		System.out.println("Please enter another number");
		int numTwo = Integer.parseInt(in.nextLine());
		in.close();
		SimpleObjectClass soc1 = new SimpleObjectClass(title, num, numTwo);
		SimpleObjectClass soc2 = new SimpleObjectClass(title, num, numTwo);
		SimpleObjectClass soc3 = new SimpleObjectClass(title, num, numTwo);
		ReferencesArrayClass rac = new ReferencesArrayClass(soc1, soc2, soc3);
		return rac;
	}
	public static Object ReferencesCollection(){
		System.out.println("Please enter a string");
		Scanner in = new Scanner(System.in);
		String title = in.nextLine();
		System.out.println("Please enter a number");
		int num = Integer.parseInt(in.nextLine());
		System.out.println("Please enter another number");
		int numTwo = Integer.parseInt(in.nextLine());
		in.close();
		SimpleObjectClass soc1 = new SimpleObjectClass(title, num, numTwo);
		SimpleObjectClass soc2 = new SimpleObjectClass(title, num, numTwo);
		SimpleObjectClass soc3 = new SimpleObjectClass(title, num, numTwo);
		ReferencesCollectionClass rcc = new ReferencesCollectionClass(soc1, soc2, soc3);
		return rcc;
	}

}