package au.com.AidoP.UV;

//Eastern Face
public class Right {

	private int[] Origin = new int[200];
	private int[] Base = new int[200];
	private boolean[] cull = new boolean[100];
	private boolean[] export = new boolean[100];
	private int[] TextureIndex = new int[100];
	
	public int getXOrigin(int Voxel){
		return Origin[Voxel * 2];
	}
	
	public int getXBase(int Voxel){
		return Base[Voxel * 2];
	}
	
	public int getYOrigin(int Voxel){
		return Origin[Voxel * 2 + 1];
	}
	
	public int getYBase(int Voxel){
		return Base[Voxel * 2 + 1];
	}
	
	public void setXOrigin(int Value, int Voxel){
		Origin[Voxel * 2] = Value;
	}
	
	public void setXBase(int Value, int Voxel){
		Base[Voxel * 2] = Value;
	}
	
	public void setYOrigin(int Value, int Voxel){
		Origin[Voxel * 2 + 1] = Value;
	}
	
	public void setYBase(int Value, int Voxel){
		Base[Voxel * 2 + 1] = Value;
	}
	
	public void setCulling(boolean Value, int Voxel){
		cull[Voxel] = Value;
	}
	
	public boolean getCulling(int Voxel){
		return cull[Voxel];
	}
	
	public void setExport(boolean Value, int Voxel){
		export[Voxel] = Value;
	}
	
	public boolean getExport(int Voxel){
		return export[Voxel];
	}
	
	public int getTextureIndex(int Voxel){
		return TextureIndex[Voxel];
	}
	
	public void setTextureIndex(int Value, int Voxel){
		TextureIndex[Voxel] = Value;
	}
}
