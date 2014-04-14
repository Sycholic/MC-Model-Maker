package au.com.AidoP;

import java.io.File;
import java.util.Formatter;

public class Writer {
	Formatter f;
	
	private void createFile(File file){
		try{
			f = new Formatter(file.getPath());
		}catch(Exception e){
			System.out.println("ERROR CREATING FILE :O"+e);
		}
	}
	
	private void writeFile(String exportString){
		f.format(exportString);
	}
	
	public void Write(File file, String toWrite){
		String filename=file.getPath();
		if(getExtension(file)!=null){
			System.out.println(getExtension(file));
			int extPos = file.getPath().lastIndexOf(".");
			  if(extPos == -1) {
			    filename = file.getPath();
			  }
			  else {
			    filename = file.getPath().substring(0, extPos);
			  }
		}
		file.renameTo(file=new File(filename+".json"));
		
		createFile(file);
		writeFile(toWrite);
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
}
