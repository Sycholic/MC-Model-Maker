package au.com.AidoP.Voxels;

//Voxel X Axis set
public class X {

	private int[] Origin = new int[100];
	private int[] Base = new int[100];
	
	private String[] Comment = new String[100];
	
	private boolean[] ForceExport = new boolean[100];
	
	public int getOrigin(int Voxel){
		return Origin[Voxel];
	}
	
	public int getBase(int Voxel){
		return Base[Voxel];
	}
	
	public void setOrigin(int Value, int Voxel){
		Origin[Voxel] = Value;
	}
	
	public void setBase(int Value, int Voxel){
		Base[Voxel] = Value;
	}
	
	public String getComment(int Voxel){
		return Comment[Voxel];
	}
	
	public void setComment(String Value, int Voxel){
		Comment[Voxel] = Value;
	}

	public boolean getForceExport(int Voxel) {
		return ForceExport[Voxel];
	}
	
	public void setForceExport(boolean Value, int Voxel) {
		ForceExport[Voxel] = Value;
	}
}
