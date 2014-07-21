package au.com.AidoP.Import;

import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import au.com.AidoP.Other.OptionData;
import au.com.AidoP.Voxels.*;
import au.com.AidoP.UV.*;

public class Importer {

	private JSONParser Parser = new JSONParser();

	public void startImport(X XData, Y YData, Z ZData, Front NorthData, Back SouthData, Left WestData, Right EastData, Top TopData, Bottom BottomData, OptionData OptionData, File fileToParse, TextureIndex Textures )throws IndexOutOfBoundsException, ParseException, IOException{
		//Delete everything 
		for(int VoxEditing = 0; VoxEditing <= 99; VoxEditing++){
			XData.setOrigin(0, VoxEditing);
			YData.setOrigin(0, VoxEditing);
			ZData.setOrigin(0, VoxEditing);
			XData.setBase(0, VoxEditing);
			YData.setBase(0, VoxEditing);
			ZData.setBase(0, VoxEditing);

			NorthData.setXOrigin(0, VoxEditing);
			SouthData.setXOrigin(0, VoxEditing);
			EastData.setXOrigin(0, VoxEditing);
			WestData.setXOrigin(0, VoxEditing);
			TopData.setXOrigin(0, VoxEditing);
			BottomData.setXOrigin(0, VoxEditing);
			NorthData.setYOrigin(0, VoxEditing);
			SouthData.setYOrigin(0, VoxEditing);
			EastData.setYOrigin(0, VoxEditing);
			WestData.setYOrigin(0, VoxEditing);
			TopData.setYOrigin(0, VoxEditing);
			BottomData.setYOrigin(0, VoxEditing);

			NorthData.setXBase(0, VoxEditing);
			SouthData.setXBase(0, VoxEditing);
			EastData.setXBase(0, VoxEditing);
			WestData.setXBase(0, VoxEditing);
			TopData.setXBase(0, VoxEditing);
			BottomData.setXBase(0, VoxEditing);
			NorthData.setYBase(0, VoxEditing);
			SouthData.setYBase(0, VoxEditing);
			EastData.setYBase(0, VoxEditing);
			WestData.setYBase(0, VoxEditing);
			TopData.setYBase(0, VoxEditing);
			BottomData.setYBase(0, VoxEditing);

			NorthData.setCulling(false, VoxEditing);
			SouthData.setCulling(false, VoxEditing);
			EastData.setCulling(false, VoxEditing);
			WestData.setCulling(false, VoxEditing);
			TopData.setCulling(false, VoxEditing);
			BottomData.setCulling(false, VoxEditing);	

			NorthData.setExport(false, VoxEditing);
			SouthData.setExport(false, VoxEditing);
			EastData.setExport(false, VoxEditing);
			WestData.setExport(false, VoxEditing);
			TopData.setExport(false, VoxEditing);
			BottomData.setExport(false, VoxEditing);
		}

		//Set up the main Object
		FileReader read = new FileReader(fileToParse);

		JSONObject mainObject = (JSONObject) Parser.parse(read);

		//get Ambient Occlusion
		if((mainObject.get("ambientocclusion") != null)) OptionData.setAmbientOcc(Boolean.parseBoolean(mainObject.get("ambientocclusion").toString()));
		else OptionData.setAmbientOcc(false);
		//Get textures next
		JSONObject textureObj = (JSONObject) mainObject.get("textures");
		//~~~~WIP

		//get elements object
		JSONArray elementsObj = (JSONArray) mainObject.get("elements");

		for(int voxel = 0; voxel <= 99; voxel++){
			JSONObject voxSet = (JSONObject) elementsObj.get(voxel);

			//get from
			JSONArray from = (JSONArray) voxSet.get("from");
			XData.setOrigin((int)Long.parseLong(from.get(0).toString()), voxel);
			YData.setOrigin((int)Long.parseLong(from.get(1).toString()), voxel);
			ZData.setOrigin((int)Float.parseFloat(from.get(2).toString()), voxel);

			//get to
			JSONArray to = (JSONArray) voxSet.get("to");
			XData.setBase((int)Long.parseLong(to.get(0).toString()), voxel);
			YData.setBase((int)Long.parseLong(to.get(1).toString()), voxel);
			ZData.setBase((int)Float.parseFloat(to.get(2).toString()), voxel);

			//Get faces Object
			JSONObject faces = (JSONObject) voxSet.get("faces");

			//Assign UV Values

			if(faces.get("north") != null){ //North face
				JSONObject face = (JSONObject) faces.get("north");
				JSONArray uv = (JSONArray) face.get("uv");
				NorthData.setXOrigin(Integer.parseInt(uv.get(0).toString()), voxel);
				NorthData.setYOrigin(Integer.parseInt(uv.get(1).toString()), voxel);
				NorthData.setXBase(Integer.parseInt(uv.get(2).toString()), voxel);
				NorthData.setYBase(Integer.parseInt(uv.get(3).toString()), voxel);
				if(face.get("cullface") != null) NorthData.setCulling(Boolean.parseBoolean(face.get("cullface").toString()), voxel);
				else NorthData.setCulling(false, voxel);
			}else NorthData.setExport(true, voxel);

			if(faces.get("south") != null){ //South Face
				JSONObject face = (JSONObject) faces.get("south");
				JSONArray uv = (JSONArray) face.get("uv");
				SouthData.setXOrigin(Integer.parseInt(uv.get(0).toString()), voxel);
				SouthData.setYOrigin(Integer.parseInt(uv.get(1).toString()), voxel);
				SouthData.setXBase(Integer.parseInt(uv.get(2).toString()), voxel);
				SouthData.setYBase(Integer.parseInt(uv.get(3).toString()), voxel);
				if(face.get("cullface") != null) SouthData.setCulling(Boolean.parseBoolean(face.get("cullface").toString()), voxel);
				else SouthData.setCulling(false, voxel);
			}else SouthData.setExport(true, voxel);

			if(faces.get("east") != null){ //East Face
				JSONObject face = (JSONObject) faces.get("east");
				JSONArray uv = (JSONArray) face.get("uv");
				EastData.setXOrigin(Integer.parseInt(uv.get(0).toString()), voxel);
				EastData.setYOrigin(Integer.parseInt(uv.get(1).toString()), voxel);
				EastData.setXBase(Integer.parseInt(uv.get(2).toString()), voxel);
				EastData.setYBase(Integer.parseInt(uv.get(3).toString()), voxel);
				if(face.get("cullface") != null) EastData.setCulling(Boolean.parseBoolean(face.get("cullface").toString()), voxel);
				else EastData.setCulling(false, voxel);
			}else EastData.setExport(true, voxel);

			if(faces.get("west") != null){ //West Face
				JSONObject face = (JSONObject) faces.get("west");
				JSONArray uv = (JSONArray) face.get("uv");
				WestData.setXOrigin(Integer.parseInt(uv.get(0).toString()), voxel);
				WestData.setYOrigin(Integer.parseInt(uv.get(1).toString()), voxel);
				WestData.setXBase(Integer.parseInt(uv.get(2).toString()), voxel);
				WestData.setYBase(Integer.parseInt(uv.get(3).toString()), voxel);
				if(face.get("cullface") != null) WestData.setCulling(Boolean.parseBoolean(face.get("cullface").toString()), voxel);
				else WestData.setCulling(false, voxel);
			}else WestData.setExport(true, voxel);

			if(faces.get("up") != null){ //Top Face
				JSONObject face = (JSONObject) faces.get("up");
				JSONArray uv = (JSONArray) face.get("uv");
				TopData.setXOrigin(Integer.parseInt(uv.get(0).toString()), voxel);
				TopData.setYOrigin(Integer.parseInt(uv.get(1).toString()), voxel);
				TopData.setXBase(Integer.parseInt(uv.get(2).toString()), voxel);
				TopData.setYBase(Integer.parseInt(uv.get(3).toString()), voxel);
				if(face.get("cullface") != null) TopData.setCulling(Boolean.parseBoolean(face.get("cullface").toString()), voxel);
				else TopData.setCulling(false, voxel);
			}else TopData.setExport(true, voxel);

			if(faces.get("down") != null){ //Bottom Face
				JSONObject face = (JSONObject) faces.get("down");
				JSONArray uv = (JSONArray) face.get("uv");
				BottomData.setXOrigin(Integer.parseInt(uv.get(0).toString()), voxel);
				BottomData.setYOrigin(Integer.parseInt(uv.get(1).toString()), voxel);
				BottomData.setXBase(Integer.parseInt(uv.get(2).toString()), voxel);
				BottomData.setYBase(Integer.parseInt(uv.get(3).toString()), voxel);
				if(face.get("cullface") != null) BottomData.setCulling(Boolean.parseBoolean(face.get("cullface").toString()), voxel);
				else BottomData.setCulling(false, voxel);
			}else BottomData.setExport(true, voxel);
			
			//Reverse Y Values because minecraft values are upside down
			int OYDatao = YData.getOrigin(voxel);
			int OYDatab = YData.getBase(voxel);
			YData.setOrigin(OYDatab, voxel);
			YData.setBase(OYDatao, voxel);
			YData.setOrigin(YData.getOrigin(voxel) * -1 + 16, voxel);
			YData.setBase(YData.getBase(voxel) * -1 + 16, voxel);
		}

	}
	
	
	//The rest are for the settings
	
	public int getRedEditing() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("EditingColourRed").toString());
	}
	
	public int getGreenEditing() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("EditingColourGreen").toString());
	}
	
	public int getBlueEditing() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("EditingColourBlue").toString());
	}
	
	public int getAlphaEditing() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("EditingAlpha").toString());
	}
	
	public int getRedBack() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("ColourRed").toString());
	}
	
	public int getGreenBack() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("ColourGreen").toString());
	}
	
	public int getBlueBack() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("ColourBlue").toString());
	}
	
	public int getAlphaBack() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return Integer.parseInt(main.get("Alpha").toString());
	}
	
	public String getFramerate() throws IOException, ParseException{
		File f = new File(".");
		File dir = f.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		
		FileReader read = new FileReader(new File(path + "/Settings.json"));

		JSONObject main = (JSONObject) Parser.parse(read);
		
		return main.get("Framerate").toString();
	}
}
