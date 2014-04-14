package au.com.AidoP;

public class Export {
	public String export;
	private int Ovc2,Ovc;
	public void export(boolean AmbientOc,int[] VC, int[] VC2,int[] UVC0,int[] UVC02,int[] UVC1,int[] UVC12,int[] UVC2,int[] UVC22,int[] UVC3,int[] UVC32,int[] UVC4,int[] UVC42,int[] UVC5,int[] UVC52){
		export="{\n\"__comment\": \"Made using MC Model Maker by AidoP!\",\n\"useAmbientOcclusion\": "+AmbientOc+",\n\"elements\": [\n";
		for(int i=0;i<=99;i++){
			if(VC[i*3]!=VC2[i*3]&&VC[i*3+1]!=VC2[i*3+1]&&VC[i*3+2]!=VC2[i*3+2]){
				VC[i*3+1]=VC[i*3+1]*-1 +16;
				VC2[i*3+1]=VC2[i*3+1]*-1 +16;
				Ovc=VC[i*3+1];
				Ovc2 = VC2[i*3+1];
				VC2[i*3+1] = Ovc;
				VC[i*3+1] = Ovc2;
				if(i!=0)export=export+",\n";
				export=export+"{   \n\"type\": \"cube\",\n\"from\": [ "+VC[i*3]+", "+VC[i*3+1]+", "+VC[i*3+2]+" ],\n\"to\": [ "+VC2[i*3]+", "+VC2[i*3+1]+", "+VC2[i*3+2]+" ],\n\"faceData\": {\n\"down\": {\n\"uv\": [ "+ UVC1[2*i*2] +", "+ UVC1[2*i*2+1] +", "+ UVC12[2*i*2] +", "+ UVC12[2*i*2+1] +" ],\n\"textureFacing\": \"down\"\n},\n\"up\": {\n\"uv\": [ "+ UVC0[1*i*2] +", "+ UVC0[1*i*2+1] +", "+ UVC02[1*i*2] +", "+ UVC02[1*i*2+1] +" ],\n\"textureFacing\": \"up\"\n},\n\"north\": {\n\"uv\": [ "+ UVC2[3*i*2] +", "+ UVC2[3*i*2+1] +", "+ UVC22[3*i*2] +", "+ UVC22[3*i*2+1] +" ],\n\"textureFacing\": \"north\"\n},\n\"south\": {\n\"uv\": [ "+ UVC3[4*i*2] +", "+ UVC3[4*i*2+1] +", "+ UVC32[4*i*2] +", "+ UVC32[4*i*2+1] +" ],\n\"textureFacing\": \"south\"\n},\n\"west\": {\n\"uv\": [ "+ UVC4[5*i*2] +", "+ UVC4[5*i*2+1] +", "+ UVC42[5*i*2] +", "+ UVC42[5*i*2+1] +" ],\n\"textureFacing\": \"west\"\n},\n\"east\": {\n\"uv\": [ "+ UVC5[6*i*2] +", "+ UVC5[6*i*2+1] +", "+ UVC52[6*i*2] +", "+ UVC52[6*i*2+1] +" ],\n\"textureFacing\": \"east\" \n}\n}\n}";
				VC2[i*3+1] = Ovc2;
				VC[i*3+1] = Ovc;
				VC[i*3+1]=VC[i*3+1]*-1 +16;
				VC2[i*3+1]=VC2[i*3+1]*-1 +16;
			}
		}
		export = export+"]\n}";
	}
}
