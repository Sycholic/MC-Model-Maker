package au.com.AidoP.Voxels;

//Voxel Z Axis set
public class Z {

	private int[] Origin = new int[100];
	private int[] Base = new int[100];
	
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
}