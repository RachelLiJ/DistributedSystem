/*
 * 
 */
package Client;

//import java.awt.geom.Area;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.util.Scanner;

import java.net.Socket;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.simple.JSONObject;

public class Client {
	
	// IP and port
	private static String ip = "localhost";
	private static int port = 4444;
	
	public static void main(String[] args) 
	{
		Socket socket = null;
		try
		{
			socket = new Socket(ip, port);
			System.out.println("Connection established");
			BufferedReader input=new BufferedReader(new InputStreamReader(socket.
					getInputStream(),"UTF-8"));
			
		    BufferedWriter output = new BufferedWriter(new OutputStreamWriter(socket.
		    		getOutputStream(),"UTF-8"));
		    
		    JSONObject newCommand = new JSONObject();
		    
		    Scanner scanner = new Scanner(System.in);
			String inputStr = "Start";//Initial a random word
			
			while(!(inputStr=scanner.nextLine()).equals("exit"))
			{
				
				boolean avalibleToSent=true;
				while(avalibleToSent)
				{
					String[] commandLine=inputStr.split(" ");
					if(commandLine[0].equals("search") || commandLine[0].equals("remove") )
					{
						newCommand.put("command", commandLine[0]);
						newCommand.put("word", commandLine[1]);
						System.out.println(newCommand.toJSONString());
						avalibleToSent=false;
					}
					
					else if(commandLine[0].equals("add"))
					{
						newCommand.put("command", commandLine[0]);
						newCommand.put("word", commandLine[1]);
						newCommand.put("meaning",commandLine[2]);
						System.out.println(newCommand.toJSONString());	
						avalibleToSent=false;
					}				
					else
					{					
						//if user enter a wrong command, they will get a notice and chance to try again
						System.out.println("Uesless command, please try again.");
						System.out.println("Correct format: command_name_word_(meaning is optional)");
						inputStr=scanner.nextLine();
						avalibleToSent=true;
					}					
				}		
				// Send the input string to the server by writing to the socket output stream in JSON format	
				output.write(newCommand.toJSONString() + "\n");
				output.flush();
				System.out.println("Message sent");
				
				//receive response form server
				String received=input.readLine();
				System.out.println(received + " Response received");				
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

