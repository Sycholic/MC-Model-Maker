package au.com.AidoP.UV;

public class Texture {
	
	private String TextureName;
	private String TexturePath;
	
	public Texture(String TextureName, String TexturePath){
		this.TextureName = TextureName;
		this.TexturePath = TexturePath;
	}
	
	public String getTextureName(){
		return TextureName;
	}
	
	public String getTexturePath(){
		return TexturePath;
	}
	
	public void setTextureName(String Value){
		TextureName = Value;
	}
	
	public void setTexturePath(String Value){
		TexturePath = Value;
	}
}
