package Server;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

import javax.net.ServerSocketFactory;

public class Server {
	
	// Declare the port number
	private static int port = 4444;
	
	// Identifies the user number connected
	private static int counter = 0;
	private static ServerSocket listeningSocket=null;
	private static Dictionary dict;
	public static void main(String[] args) 
	{
		dict=new Dictionary();
		try
		{
			dict.dictionary();
			listeningSocket=new ServerSocket(port);
			System.out.println("Waiting for client connection..");			
			// Wait for connections.
			while(true)
			{
				System.out.println(Thread.currentThread().getName()+" - Client "+counter+": Applying for connection!");				
				Socket client = listeningSocket.accept();
				counter++;
				
				//show all the information about client
				System.out.println("Client conection number " + counter + " accepted:");
				System.out.println("Remote Port: " + client.getPort());
				System.out.println("Remote Hostname: " + client.getInetAddress().getHostName());
				System.out.println("Local Port: " + client.getLocalPort());
				
				// Start a new thread for a connection
				ServerClient i =new ServerClient(client,dict);
				Thread clientConnection=new Thread(i);				
				clientConnection.setName("Thread " + counter);
				clientConnection.start();										
			}       
		}
		catch(SocketException ex)
		{
			ex.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(listeningSocket!=null)
			{
				try 
				{
					listeningSocket.close();
				}
				catch(IOException e)
				{
					System.out.println("I/O error occurs");
					e.printStackTrace();
				}
			}
		}		
	}	
}
