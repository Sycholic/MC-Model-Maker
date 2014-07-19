package au.com.AidoP.Export;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;

public class Writer {
	private Formatter f;

	public void createFile(File file){
		try{
			f = new Formatter(file.getPath());
		}catch(Exception e){
			System.out.println( "File creation failed! " + e );
		}
	}
	
	public void createDir(File file){
		try{
			file.mkdir();
		}catch(Exception e){
			System.out.println( "File creation failed!" + e );
		}
	}

	public void writeFile(String exportString, File file){
		f.format(exportString);
		f.close();
	}

	public String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 &&  i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}

	
	public void createResPackDir(File file){
		if(getExtension(file) == null){
			
			if(!file.exists())createDir(file);//Create resourcepack folder
			createDir(new File(file.getPath() + "/assets"));//create assets folder
			if(!new File(file.getPath() + "/assets/minecraft").exists())createDir(new File(file.getPath() + "/assets/minecraft"));
			if(!new File(file.getPath() + "/assets/minecraft/models").exists())createDir(new File(file.getPath() + "/assets/minecraft/models"));//create models folder
			if(!new File(file.getPath() + "/assets/minecraft/models/block").exists())createDir(new File(file.getPath() + "/assets/minecraft/models/block"));
			if(!new File(file.getPath() + "/assets/minecraft/models/item").exists())createDir(new File(file.getPath() + "/assets/minecraft/models/item"));
			if(!new File(file.getPath() + "/assets/minecraft/textures").exists())createDir(new File(file.getPath() + "/assets/minecraft/textures"));//create textures folder
			if(!new File(file.getPath() + "/assets/minecraft/textures/blocks").exists())createDir(new File(file.getPath() + "/assets/minecraft/textures/blocks"));
			if(!new File(file.getPath() + "/assets/minecraft/textures/items").exists())createDir(new File(file.getPath() + "/assets/minecraft/textures/items"));
			
			
		}else{
			System.out.println("Error creating resource pack! ");
		}
	}

}
