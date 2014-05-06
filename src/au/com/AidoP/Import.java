package au.com.AidoP;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Import {

	public boolean getAO(File FileToParse) throws IOException, ParseException{
		String result=null;
		boolean result1=false;

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		try{
			result = (String) jobject.get("useAmbientOcclusion").toString();
		}catch(NullPointerException e){
			result1=false;
		}


		if(result == "true"){
			result1=true;
		}else{
			result1=false;
		}

		return result1;
	}
	public boolean getOSX(File FileToParse) throws IOException, ParseException{
		String result=null;
		boolean result1=false;

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		try{
			result = (String) jobject.get("randomOffsetX").toString();
		}catch(NullPointerException e){
			result1=false;
		}


		if(result == "true"){
			result1=true;
		}else{
			result1=false;
		}

		return result1;
	}
	public boolean getOSY(File FileToParse) throws IOException, ParseException{
		String result=null;
		boolean result1=false;

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		try{
			result = (String) jobject.get("randomOffsetY").toString();
		}catch(NullPointerException e){
			result1=false;
		}

		if(result == "true"){
			result1=true;
		}else{
			result1=false;
		}

		return result1;
	}
	public boolean getOSZ(File FileToParse) throws IOException, ParseException{
		String result=null;
		boolean result1=false;

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		try{
			result = (String) jobject.get("randomOffsetZ").toString();
		}catch(NullPointerException e){
			result1=false;
		}

		if(result == "true"){
			result1=true;
		}else{
			result1=false;
		}

		return result1;
	}
	public boolean getKRot(File FileToParse) throws IOException, ParseException{
		String result=null;
		boolean result1=false;

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		try{
			result = (String) jobject.get("rotateVariantTextures").toString();
		}catch(NullPointerException e){
			result1=false;
		}

		if(result == "true"){
			result1=true;
		}else{
			result1=false;
		}

		return result1;
	}
	public boolean getRend3D(File FileToParse) throws IOException, ParseException{
		String result=null;
		boolean result1=false;

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		try{
			result = (String) jobject.get("inventoryRender3D").toString();
		}catch(NullPointerException e){
			result1=false;
		}

		if(result == "true"){
			result1=true;
		}else{
			result1=false;
		}

		return result1;
	}

	public int[] getVC(File FileToParse) throws IOException, ParseException{

		int[] result=new int[300];

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		JSONArray el= (JSONArray) jobject.get("elements");

		Iterator i = el.iterator();

		int x = 0;
		while (i.hasNext()&&x<=99) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONArray ar =(JSONArray) innerObj.get("from");
			String a = ar.get(0).toString();
			result[x*3]=Integer.parseInt(a);
			String a1 = ar.get(1).toString();
			result[x*3+1]=Integer.parseInt(a1);
			String a2 = ar.get(2).toString();
			result[x*3+2]=Integer.parseInt(a2);

			x++;
		}
		return result;
	}
	public int[] getVC2(File FileToParse) throws IOException, ParseException{

		int[] result=new int[300];

		FileReader reader = new FileReader(FileToParse);

		JSONParser jparser = new JSONParser();
		JSONObject jobject = (JSONObject) jparser.parse(reader);

		JSONArray el= (JSONArray) jobject.get("elements");

		Iterator i = el.iterator();

		int x = 0;
		while (i.hasNext()&&x<=99) {
			JSONObject innerObj = (JSONObject) i.next();
			JSONArray ar =(JSONArray) innerObj.get("to");
			String a = ar.get(0).toString();
			result[x*3]=Integer.parseInt(a);
			String a1 = ar.get(1).toString();
			result[x*3+1]=Integer.parseInt(a1);
			String a2 = ar.get(2).toString();
			result[x*3+2]=Integer.parseInt(a2);

			x++;
		}
		return result;
	}
}
