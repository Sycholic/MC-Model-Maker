package au.com.AidoP.Other;

public class OptionData {
	
	private String particleTexture = "blocks/crafting_table_front";
	
	private boolean OffX = false;
	private boolean OffY = false;
	private boolean OffZ = false;
	private boolean ambientOcc = true;
	
	public String getParticleTexture(){
		return particleTexture;
	}
	
	public void setParticleTexture(String Path){
		particleTexture = Path;
	}
	
	public boolean getRandOffsetX(){
		return OffX;
	}
	
	public boolean getRandOffsetY(){
		return OffY;
	}
	
	public boolean getRandOffsetZ(){
		return OffZ;
	}
	
	public void setRandOffsetX(boolean Value){
		OffX = Value;
	}
	
	public void setRandOffsetY(boolean Value){
		OffY = Value;
	}
	
	public void setRandOffsetZ(boolean Value){
		OffZ = Value;
	}
	
	public boolean getAmbientOcc(){
		return ambientOcc;
	}
	
	public void setAmbientOcc(boolean Value){
		ambientOcc = Value;
	}
}
