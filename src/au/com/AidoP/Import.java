package au.com.AidoP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Import {
	
	public String getValueFromFile(File FileToParse) throws IOException{

	    String s, export = new String();
		try {
		    BufferedReader in = new BufferedReader(
		    	      new FileReader(FileToParse.getPath()));
		    	    while((s = in.readLine())!= null)
		    	      export += s + "\n";
		    	    in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JsonParser json = new JsonParser();
		String export2 = json.parse(export).getAsString();
		return export2;
	}
}
