package au.com.AidoP.Display;

import javax.media.j3d.Shape3D;
import javax.vecmath.Point3f;

import au.com.AidoP.Voxels.X;
import au.com.AidoP.Voxels.Y;
import au.com.AidoP.Voxels.Z;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;

public class Cubes extends Shape3D {
	private Point3f[] verts = new Point3f[48];
	
	public Cubes(int Voxel, X XData, Y YData, Z ZData){

		Point3f A = new Point3f(((float)XData.getOrigin(Voxel)) / 10, ((float)YData.getBase(Voxel)) / 10, ((float)ZData.getBase(Voxel)) / 10);
		Point3f B = new Point3f(((float)XData.getOrigin(Voxel)) / 10, ((float)YData.getOrigin(Voxel)) / 10, ((float)ZData.getBase(Voxel)) / 10);
		Point3f C = new Point3f(((float)XData.getBase(Voxel)) / 10, ((float)YData.getOrigin(Voxel)) / 10, ((float)ZData.getBase(Voxel)) / 10);
		Point3f D = new Point3f(((float)XData.getBase(Voxel)) / 10, ((float)YData.getBase(Voxel)) / 10, ((float)ZData.getBase(Voxel)) / 10);
		Point3f E = new Point3f(((float)XData.getOrigin(Voxel)) / 10, ((float)YData.getBase(Voxel)) / 10, ((float)ZData.getOrigin(Voxel)) / 10);
		Point3f F = new Point3f(((float)XData.getOrigin(Voxel)) / 10, ((float)YData.getOrigin(Voxel)) / 10, ((float)ZData.getOrigin(Voxel)) / 10);
		Point3f G = new Point3f(((float)XData.getBase(Voxel)) / 10, ((float)YData.getOrigin(Voxel)) / 10, ((float)ZData.getOrigin(Voxel)) / 10);
		Point3f H = new Point3f(((float)XData.getBase(Voxel)) / 10, ((float)YData.getBase(Voxel)) / 10, ((float)ZData.getOrigin(Voxel)) / 10);
		
		
		//Front - North
		verts[0] = C;
		verts[1] = D;
		verts[2] = A;
		verts[3] = B;
		//Front (BackFace)
		verts[4] = C;
		verts[5] = B;
		verts[6] = A;
		verts[7] = D;
		
		//Back - South
		verts[24] = G;
		verts[25] = F;
		verts[26] = E;
		verts[27] = H;
		//Back
		verts[28] = F;
		verts[29] = G;
		verts[30] = H;
		verts[31] = E;
		
		//Left - East
		verts[8] = B;
		verts[9] = A;
		verts[10] = E;
		verts[11] = F;
		//Left
		verts[12] = B;
		verts[13] = F;
		verts[14] = E;
		verts[15] = A;
		
		//Right - West
		verts[16] = C;
		verts[17] = D;
		verts[18] = H;
		verts[19] = G;
		//Right
		verts[20] = C;
		verts[21] = G;
		verts[22] = H;
		verts[23] = D;
		
		//Top
		verts[32] = B;
		verts[33] = C;
		verts[34] = G;
		verts[35] = F;
		//Top
		verts[36] = C;
		verts[37] = B;
		verts[38] = F;
		verts[39] = G;
		
		//Bottom
		verts[40] = E;
		verts[41] = H;
		verts[42] = D;
		verts[43] = A;
		//Bottom
		verts[44] = H;
		verts[45] = E;
		verts[46] = A;
		verts[47] = D;
		
		int[] stripCounts = new int[12];
		stripCounts[0] = 4;
		stripCounts[1] = 4;
		stripCounts[2] = 4;
		stripCounts[3] = 4;
		stripCounts[4] = 4;
		stripCounts[5] = 4;
		stripCounts[6] = 4;
		stripCounts[7] = 4;
		stripCounts[8] = 4;
		stripCounts[9] = 4;
		stripCounts[10] = 4;
		stripCounts[11] = 4;

		int[] contourCount = new int[12];
		contourCount[0] = 1;
		contourCount[1] = 1;
		contourCount[2] = 1;
		contourCount[3] = 1;
		contourCount[4] = 1;
		contourCount[5] = 1;
		contourCount[6] = 1;
		contourCount[7] = 1;
		contourCount[8] = 1;
		contourCount[9] = 1;
		contourCount[10] = 1;
		contourCount[11] = 1;
		
		GeometryInfo gInf = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
		
		gInf.setCoordinates(verts);
		gInf.setStripCounts(stripCounts);
		gInf.setContourCounts(contourCount);

		NormalGenerator ng = new NormalGenerator();
		ng.setCreaseAngle ((float) Math.toRadians(360));
		ng.generateNormals(gInf);
		
		this.setGeometry(gInf.getGeometryArray());
	}
}
