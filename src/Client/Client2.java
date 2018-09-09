
package Client;
import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

public class Client2 {
	
	// IP and portPp
	private static String ip = "localhost";
	private static int port = 4444;
	
	public static void main(String[] args) {
		
		//JSONML command =new JSONML();
		Socket socket = null;
		try
		{
			socket = new Socket(ip, port);
			System.out.println("Connection established");
			BufferedReader input=new BufferedReader(new InputStreamReader(socket.
					getInputStream(),"UTF-8"));
			
		    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.
		    		getOutputStream(),"UTF-8"));
		 
		    Scanner scanner = new Scanner(System.in);
			String inputStr = "Start";
			//
			while(!(inputStr=scanner.nextLine()).equals("exit"))
			{
				// Send the input string to the server by writing to the socket output stream
				//System.out.println("Please enter your choice: 1. Add 2. Remove 3. Searcch");
				
				output.write(inputStr + "\n");
				output.flush();
				System.out.println("Message sent");		
				String received=input.readLine();
				System.out.println(received);				
			}			
			scanner.close();	
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
			System.out.println("what happened?");
		} 
		catch(SocketException e)
		{
			System.out.println("may be this can be right");
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			System.out.println("i don't know");
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("something defindly happened");
		}
		finally
		{
			if(socket!=null)
			{
				try
				{
					socket.close();
				}
				catch(IOException e)
				{
					System.out.println("ask somebody else");
					e.printStackTrace();	
				}
			}
		}
	}

}

