package au.com.AidoP.Export;

import au.com.AidoP.UV.Back;
import au.com.AidoP.UV.Bottom;
import au.com.AidoP.UV.Front;
import au.com.AidoP.UV.Left;
import au.com.AidoP.UV.Right;
import au.com.AidoP.UV.Top;
import au.com.AidoP.Voxels.X;
import au.com.AidoP.Voxels.Y;
import au.com.AidoP.Voxels.Z;
import au.com.AidoP.Other.OptionData;

public class Prepare {

	private String export;

	public String prepareWriteData(X XData, Y YData, Z ZData, Front NorthData, Back SouthData, Left WestData, Right EastData, Top TopData, Bottom BottomData, OptionData OptionData){
		
		export = "{\n";
		export += "\t\"__comment\": \"Made with MC Model Maker for Minecraft versions 14w26a+\",\n";
		export += "\t\"ambientocclusion\": " + OptionData.getAmbientOcc() + ",\n";
		export += "\t\"randomOffsetX\": " + OptionData.getRandOffsetX() + ",\n";
		export += "\t\"randomOffsetY\": " + OptionData.getRandOffsetY() + ",\n";
		export += "\t\"randomOffsetZ\": " + OptionData.getRandOffsetZ() + ",\n";
		export += "\t\"textures\": {\n";
		export += "\t\t\"north\": \"" + NorthData.getTexture() + "\",\n";
		export += "\t\t\"south\": \"" + SouthData.getTexture() + "\",\n";
		export += "\t\t\"east\": \"" + EastData.getTexture() + "\",\n";
		export += "\t\t\"west\": \"" + WestData.getTexture() + "\",\n";
		export += "\t\t\"up\": \"" + TopData.getTexture() + "\",\n";
		export += "\t\t\"down\": \"" + BottomData.getTexture() + "\",\n";
		export += "\t\t\"particle\": \"" + OptionData.getParticleTexture() + "\"\n";
		export += "\t},\n";
		export += "\t\"elements\": [\n";

		for(int voxel=0; voxel <= 99; voxel++){
			if(XData.getOrigin(voxel) != XData.getBase(voxel) && YData.getOrigin(voxel) != YData.getBase(voxel) && ZData.getOrigin(voxel) != ZData.getBase(voxel)){

				YData.setOrigin(YData.getOrigin(voxel) * -1 + 16, voxel);
				YData.setBase(YData.getBase(voxel) * -1 + 16, voxel);
				int OYDato = YData.getOrigin(voxel);
				int OYDatb = YData.getBase(voxel);
				YData.setOrigin(OYDatb, voxel);
				YData.setBase(OYDato, voxel);
				
				
				if(voxel >= 1)export += "\t\t},\n";
				export += "\t\t{\n";
				export += "\t\t\t\"from\": [ " + XData.getOrigin(voxel) + ", " + YData.getOrigin(voxel) + ", " + ZData.getOrigin(voxel) + " ],\n";
				export += "\t\t\t\"to\": [ " + XData.getBase(voxel) + ", " + YData.getBase(voxel) + ", " + ZData.getBase(voxel) + " ],\n";
				if(XData.getComment(voxel) != null && XData.getComment(voxel).length() >= 1) export += "\t\t\t\"__comment\": \"" + XData.getComment(voxel) + "\",\n";
				export += "\t\t\t\"faces\": {\n";
				export += "\t\t\t\t\"north\": { \"uv\": [ " + NorthData.getXOrigin(voxel) + ", " + NorthData.getYOrigin(voxel) + ", " + NorthData.getXBase(voxel) + ", " + NorthData.getYBase(voxel) + " ], \"texture\": \"#north\", \"cull\": " + NorthData.getCulling(voxel) + " },\n";
				export += "\t\t\t\t\"south\": { \"uv\": [ " + SouthData.getXOrigin(voxel) + ", " + SouthData.getYOrigin(voxel) + ", " + SouthData.getXBase(voxel) + ", " + SouthData.getYBase(voxel) + " ], \"texture\": \"#south\", \"cull\": " + SouthData.getCulling(voxel) + " },\n";
				export += "\t\t\t\t\"east\": { \"uv\": [ " + EastData.getXOrigin(voxel) + ", " + EastData.getYOrigin(voxel) + ", " + EastData.getXBase(voxel) + ", " + EastData.getYBase(voxel) + " ], \"texture\": \"#east\", \"cull\": " + EastData.getCulling(voxel) + " },\n";
				export += "\t\t\t\t\"west\": { \"uv\": [ " + WestData.getXOrigin(voxel) + ", " + WestData.getYOrigin(voxel) + ", " + WestData.getXBase(voxel) + ", " + WestData.getYBase(voxel) + " ], \"texture\": \"#west\", \"cull\": " + WestData.getCulling(voxel) + " },\n";
				export += "\t\t\t\t\"up\": { \"uv\": [ " + TopData.getXOrigin(voxel) + ", " + TopData.getYOrigin(voxel) + ", " + TopData.getXBase(voxel) + ", " + TopData.getYBase(voxel) + " ], \"texture\": \"#up\", \"cull\": " + TopData.getCulling(voxel) + " },\n";
				export += "\t\t\t\t\"down\": { \"uv\": [ " + BottomData.getXOrigin(voxel) + ", " + BottomData.getYOrigin(voxel) + ", " + BottomData.getXBase(voxel) + ", " + BottomData.getYBase(voxel) + " ], \"texture\": \"#down\", \"cull\": " + BottomData.getCulling(voxel) + " }\n";
				export += "\t\t\t}\n";
				
				YData.setOrigin(OYDato, voxel);
				YData.setBase(OYDatb, voxel);
				YData.setOrigin(YData.getOrigin(voxel) * -1 + 16, voxel);
				YData.setBase(YData.getBase(voxel) * -1 + 16, voxel);
			}
		}
		export += "\t\t}\n";
		export += "\t]\n";
		export += "}";
		return export;
	}
}
