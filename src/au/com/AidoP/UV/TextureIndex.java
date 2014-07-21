package au.com.AidoP.UV;

public class TextureIndex {


	private String[] TextureFriendlyNames = new String[100];
	private Texture[] Textures = new Texture[100];

	public void setFriendlyNameForIndex(String Value, int Index){
		TextureFriendlyNames[Index] = Value;
	}

	public String getFriendlyNameForIndex(int Index){
		return TextureFriendlyNames[Index];
	}

	public String getTextureName(int Index){
		return Textures[Index].getTextureName();
	}

	public String getTexturePath(int Index){
		return Textures[Index].getTexturePath();
	}

	public void setTextureName(String Value, int Index){
		Textures[Index].setTextureName(Value);
	}

	public void setTexturePath(String Value, int Index){
		Textures[Index].setTexturePath(Value);
	}

	public boolean getIndexNull(int Index){
		if(Textures[Index] == null){
			return true;
		}else{
			return false;
		}
	}
	
	public void setTextureAtIndex(Texture Value, int Index){
		Textures[Index] = Value;
	}
}
