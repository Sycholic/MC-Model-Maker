package au.com.AidoP.Export;

import au.com.AidoP.UV.Back;
import au.com.AidoP.UV.Bottom;
import au.com.AidoP.UV.Front;
import au.com.AidoP.UV.Left;
import au.com.AidoP.UV.Right;
import au.com.AidoP.UV.TextureIndex;
import au.com.AidoP.UV.Top;
import au.com.AidoP.Voxels.X;
import au.com.AidoP.Voxels.Y;
import au.com.AidoP.Voxels.Z;
import au.com.AidoP.Other.OptionData;

public class Prepare {

	private String export;

	public String prepareWriteData(X XData, Y YData, Z ZData, Front NorthData, Back SouthData, Left WestData, Right EastData, Top TopData, Bottom BottomData, OptionData OptionData, TextureIndex Textures){

		export = "{\n";
		export += "\t\"__comment\": \"Made with MC Model Maker for Minecraft versions 14w26a+\",\n";
		export += "\t\"ambientocclusion\": " + OptionData.getAmbientOcc() + ",\n";
		/*export += "\t\"randomOffsetX\": " + OptionData.getRandOffsetX() + ",\n"; No Longer supported!
		export += "\t\"randomOffsetY\": " + OptionData.getRandOffsetY() + ",\n";
		export += "\t\"randomOffsetZ\": " + OptionData.getRandOffsetZ() + ",\n";*/
		export += "\t\"textures\": {\n";

		int index = 0;
		while(index <= 99){
			if(!Textures.getIndexNull(index)){
				export += "\t\t\"" + Textures.getFriendlyNameForIndex(index) +  "\": \"" + Textures.getTextureName(index) + "\",\n";
			}
			index++;
		}

		export += "\t\t\"particle\": \"" + OptionData.getParticleTexture() + "\"\n";
		export += "\t},\n";
		export += "\t\"elements\": [\n";

		for(int voxel=0; voxel <= 99; voxel++){
			if((XData.getOrigin(voxel) != XData.getBase(voxel) && YData.getOrigin(voxel) != YData.getBase(voxel) && ZData.getOrigin(voxel) != ZData.getBase(voxel)) || XData.getForceExport(voxel)){

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
				if(!NorthData.getExport(voxel)){ 
					export += "\t\t\t\t\"north\": { \"uv\": [ " + NorthData.getXOrigin(voxel) + ", " + NorthData.getYOrigin(voxel) + ", " + NorthData.getXBase(voxel) + ", " + NorthData.getYBase(voxel) + " ], \"texture\": \"#" + Textures.getFriendlyNameForIndex(NorthData.getTextureIndex(voxel)) + "\", \"cullface\": " + NorthData.getCulling(voxel) + " }";
					if(!SouthData.getExport(voxel) || !EastData.getExport(voxel) || !WestData.getExport(voxel) || !TopData.getExport(voxel) || !BottomData.getExport(voxel))export += ",\n";
				}


				if(!SouthData.getExport(voxel)){
					export += "\t\t\t\t\"south\": { \"uv\": [ " + SouthData.getXOrigin(voxel) + ", " + SouthData.getYOrigin(voxel) + ", " + SouthData.getXBase(voxel) + ", " + SouthData.getYBase(voxel) + " ], \"texture\": \"#" + Textures.getFriendlyNameForIndex(SouthData.getTextureIndex(voxel)) + "\", \"cullface\": " + SouthData.getCulling(voxel) + " }";
					if(!EastData.getExport(voxel) || !WestData.getExport(voxel) || !TopData.getExport(voxel) || !BottomData.getExport(voxel))export += ",\n";
				}


				if(!EastData.getExport(voxel)){
					export += "\t\t\t\t\"east\": { \"uv\": [ " + EastData.getXOrigin(voxel) + ", " + EastData.getYOrigin(voxel) + ", " + EastData.getXBase(voxel) + ", " + EastData.getYBase(voxel) + " ], \"texture\": \"#" + Textures.getFriendlyNameForIndex(EastData.getTextureIndex(voxel)) + "\", \"cullface\": " + EastData.getCulling(voxel) + " }";
					if(!WestData.getExport(voxel) || !TopData.getExport(voxel) || !BottomData.getExport(voxel))export += ",\n";
				}


				if(!WestData.getExport(voxel)){
					export += "\t\t\t\t\"west\": { \"uv\": [ " + WestData.getXOrigin(voxel) + ", " + WestData.getYOrigin(voxel) + ", " + WestData.getXBase(voxel) + ", " + WestData.getYBase(voxel) + " ], \"texture\": \"#" + Textures.getFriendlyNameForIndex(WestData.getTextureIndex(voxel)) + "\", \"cullface\": " + WestData.getCulling(voxel) + " }";
					if(!TopData.getExport(voxel) || !BottomData.getExport(voxel))export += ",\n";
				}


				if(!TopData.getExport(voxel)){
					export += "\t\t\t\t\"up\": { \"uv\": [ " + TopData.getXOrigin(voxel) + ", " + TopData.getYOrigin(voxel) + ", " + TopData.getXBase(voxel) + ", " + TopData.getYBase(voxel) + " ], \"texture\": \"#" + Textures.getFriendlyNameForIndex(TopData.getTextureIndex(voxel)) + "\", \"cullface\": " + TopData.getCulling(voxel) + " }";
					if(!BottomData.getExport(voxel)) export += ",\n";
				}


				if(!BottomData.getExport(voxel)) export += "\t\t\t\t\"down\": { \"uv\": [ " + BottomData.getXOrigin(voxel) + ", " + BottomData.getYOrigin(voxel) + ", " + BottomData.getXBase(voxel) + ", " + BottomData.getYBase(voxel) + " ], \"texture\": \"#" + Textures.getFriendlyNameForIndex(BottomData.getTextureIndex(voxel)) + "\", \"cullface\": " + BottomData.getCulling(voxel) + " }";
				export += "\n\t\t\t}\n";

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
