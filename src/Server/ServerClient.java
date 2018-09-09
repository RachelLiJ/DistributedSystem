package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;


public class ServerClient implements Runnable
{
	private Socket client;
	private Dictionary dict;
	public ServerClient(Socket client,Dictionary dict)
	{
		 this.client=client;
		 this.dict=dict;		
	}	
	
	@Override
	public void run() 
	{		
		try 
	    {	
			JSONParser parser = new JSONParser();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(client.
					getInputStream(),"UTF-8"));
			BufferedWriter output = new BufferedWriter(new OutputStreamWriter(client.
					getOutputStream(),"UTF-8"));
	
			System.out.println(Thread.currentThread().getName()
					+ "Hearing from client ");			
			try 
			{
				String clientMsg=null;
			    while((clientMsg=input.readLine())!=null)
			    {
			    		//parse the command
			    		JSONObject command =(JSONObject) parser.parse(clientMsg);
			    		
			    		System.out.println(Thread.currentThread().getName() + 
			    				" Command Reveived: " + command.toJSONString());
			    		
			    		String lineToClient=null;			    		
			    		if(command.get("command").equals("search"))
			    		{			    			
			    			lineToClient=dict.search((String) command.get("word"));	
		    				output.write(lineToClient+"\n");
		    				output.flush();
		    				System.out.println("Message send");
			    		}
			    		
			    		if(command.get("command").equals("add"))
			    		{
			    			lineToClient=dict.search((String) command.get("word"));	
			    			output.write(lineToClient+"\n");
			    			output.flush();
			    			System.out.println("Message send");
			    		}		
			    		
			    		if (command.get("command").equals("remove"))
			    		{
			    			lineToClient=dict.search((String) command.get("word"));
			    			output.write(lineToClient+ "\n");
			    			output.flush();
			    			System.out.println("Message send");
			    		}			    		
			    }
			}
		    catch(SocketException | ParseException e)
		    {
		    		System.out.println("socket error");
		    		e.printStackTrace();
		    }
		    client.close();	   		
	    }		 
		catch(SocketException e)
		{
			System.out.println("close error");
			e.printStackTrace();
		}
		catch (IOException e)
		{
			System.out.println("file error");
			e.printStackTrace();
		}
	}
}
