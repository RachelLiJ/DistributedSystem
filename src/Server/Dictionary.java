package Server;

import java.util.HashMap;
import java.util.Scanner;
import java.io.FileReader;

import java.io.IOException;
import java.io.FileNotFoundException;

public class Dictionary {
	private HashMap<String, String> lookup;
	Scanner input;
		//Constructor
		public Dictionary() 
		{
			lookup =new HashMap<String, String>(5);
		}
		
		public void dictionary() 
		{
			setDictionary();
		}
		
		public synchronized String search(String word)
		{
			String a=lookup.get(word);
			if(lookup.containsKey(word))
			{
				a=a;	
			}
			else
			{
				a="There is no such word";				
			}	
			return a;
		}
		
		public synchronized String remove(String word) 
		{
			String a=null;
			if(lookup.containsKey(word))
			{				
				lookup.remove(word);
				a="job done";
			}
			else
			{
				a="The word does not exist";
			}
			return a ;
		}
		
		public synchronized String add(String word, String meaning) 
		{
			String a=null;
			if(!lookup.containsKey(word))
			{
				lookup.put(word, meaning);
				a="job done";
			}			
			else
			{
				a="The word already exist";		
			}
			return a;
		}
		
		public void setDictionary()
		{			
			try 
			{
				input = new Scanner(new FileReader("./dict/dictionary.txt"));
				String line=null;
				while(input.hasNextLine())
				{
					line=input.nextLine();
					String[] a=line.split(":");
					lookup.put(a[0], a[1]);					
				}					
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}		
		}	
}
