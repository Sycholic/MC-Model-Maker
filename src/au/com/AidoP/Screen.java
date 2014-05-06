package au.com.AidoP;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileView;
import javax.swing.text.html.ImageView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.ParseException;
import org.lwjgl.opengl.GL11;

public class Screen {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI(); 
			}
		});
	}

	private static void createAndShowGUI() {
		JFrame f = new JFrame("MC Model Maker - By AidoP");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.add(new MyPanel());
		f.pack();
		f.setVisible(true);


	} 
}

class MyPanel extends JPanel {


	public JButton TM;
	public JButton Save;
	public JButton Open;
	public JComboBox SN;
	public JButton Reset;

	public JOptionPane er;

	public int sn;
	public int m;
	public int edUV=1;

	public boolean ao,OSX,OSY,OSZ,KRot,Rend3D;

	public final JFileChooser fc = new JFileChooser();

	private static String Opt[] = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60","61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80","81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99"};

	public int[] VC=new int[300];
	public int[] VC2=new int[300];
	public int[] UVC0=new int[1200];
	public int[] UVC02=new int[1200];
	public int[] UVC1=new int[1200];
	public int[] UVC12=new int[1200];
	public int[] UVC2=new int[1200];
	public int[] UVC22=new int[1200];
	public int[] UVC3=new int[1200];
	public int[] UVC32=new int[1200];
	public int[] UVC4=new int[1200];
	public int[] UVC42=new int[1200];
	public int[] UVC5=new int[1200];
	public int[] UVC52=new int[1200];


	public MyPanel() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));


		fc.setAcceptAllFileFilterUsed(false);
		fc.addChoosableFileFilter(new FileNameExtensionFilter("Json Files (*.json)", "json"));
		//fc.addChoosableFileFilter(new FileNameExtensionFilter("STL Files (*.stl)", "stl"));
		//fc.setAcceptAllFileFilterUsed(true);

		Open = new JButton("Open");
		Open.setToolTipText("Open an existing .json file.");
		add(Open);

		Save=new JButton("Export/Save");
		Save.setToolTipText("Save and Export your current model.");
		add(Save);

		TM = new JButton("Toggle Drawing Mode");
		TM.setToolTipText("Toggle between Voxel and UV editing modes.");
		add(TM);

		Reset = new JButton("Reset Voxel");
		Reset.setToolTipText("Resets all the values to the default for the current Voxel");
		add(Reset);

		SN = new JComboBox(Opt);
		SN.setToolTipText("The Voxel/UV Set you are editing");

		SN.addItemListener(
				new ItemListener(){
					public void itemStateChanged(ItemEvent event) {
						sn=Integer.parseInt(event.getItem().toString());	
					}
				});

		add(SN);

		Handler h = new Handler();
		Reset.addActionListener(h);
		TM.addActionListener(h);
		Save.addActionListener(h);
		Open.addActionListener(h);

		addMouseListener(h);
		addMouseMotionListener(h);
	}

	public Dimension getPreferredSize() {
		return new Dimension(1000,600);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int x,y,z,x2,y2,z2;
		x=VC[sn*3];
		y=VC[sn*3+1];
		z=VC[sn*3+2];
		x2=VC2[sn*3];
		y2=VC2[sn*3+1];
		z2=VC2[sn*3+2];

		int lx=0,ly=0,lz=0,lx2=0,ly2=0,lz2=0;
		if(sn>=1){
			lx=VC[(sn-1)*3];
			ly=VC[(sn-1)*3+1];
			lz=VC[(sn-1)*3+2];
			lx2=VC2[(sn-1)*3];
			ly2=VC2[(sn-1)*3+1];
			lz2=VC2[(sn-1)*3+2];
		}

		int ux1,uy1,ux12,uy12;
		ux1=UVC0[sn*2];
		uy1=UVC0[sn*2+1];
		ux12=UVC02[sn*2];
		uy12=UVC02[sn*2+1];

		int ux2,uy2,ux22,uy22;
		ux2=UVC1[sn*2];
		uy2=UVC1[sn*2+1];
		ux22=UVC12[sn*2];
		uy22=UVC12[sn*2+1];

		int ux3,uy3,ux32,uy32;
		ux3=UVC2[sn*2];
		uy3=UVC2[sn*2+1];
		ux32=UVC22[sn*2];
		uy32=UVC22[sn*2+1];

		int ux4,uy4,ux42,uy42;
		ux4=UVC3[sn*2];
		uy4=UVC3[sn*2+1];
		ux42=UVC32[sn*2];
		uy42=UVC32[sn*2+1];

		int ux5,uy5,ux52,uy52;
		ux5=UVC4[sn*2];
		uy5=UVC4[sn*2+1];
		ux52=UVC42[sn*2];
		uy52=UVC42[sn*2+1];

		int ux6,uy6,ux62,uy62;
		ux6=UVC5[sn*2];
		uy6=UVC5[sn*2+1];
		ux62=UVC52[sn*2];
		uy62=UVC52[sn*2+1];

		g.setColor(Color.BLACK);

		g.drawString("Info for Voxel and UV set:" + sn,10,50);
		g.drawString("From[X:"+x+",Y:"+y+",Z:"+z+"]", 10, 65);
		g.drawString("To[X:"+x2+",Y:"+y2+",Z:"+z2+"]", 10, 80);
		g.drawString("UV:", 10, 110);
		g.drawString("Top:[ "+ux1+", "+uy1+", "+ux12+", "+uy12+" ]", 10, 125);
		g.drawString("Bottom:[ "+ux2+", "+uy2+", "+ux22+", "+uy22+" ]", 10, 140);
		g.drawString("Front:[ "+ux3+", "+uy3+", "+ux32+", "+uy32+" ]", 10, 155);
		g.drawString("Back:[ "+ux4+", "+uy4+", "+ux42+", "+uy42+" ]", 10, 170);
		g.drawString("Left:[ "+ux5+", "+uy5+", "+ux52+", "+uy52+" ]", 10, 185);
		g.drawString("Right:[ "+ux6+", "+uy6+", "+ux62+", "+uy62+" ]", 10, 200);

		g.drawString("Model Options", 65, 250);
		if(ao==true){
			g.setColor(Color.GREEN);
		}
		if(ao==false){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(8, 252, 100, 15,5,5);
		g.setColor(Color.BLACK);
		g.drawString("Anti-Occlusion", 10, 265);
		g.drawRoundRect(8, 252, 100, 15,5,5);

		g.setColor(Color.BLACK);
		g.drawString("Random Offset:", 10, 280);

		if(OSX==true){
			g.setColor(Color.GREEN);
		}
		if(OSX==false){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(23, 282, 40, 15,5,5);
		g.setColor(Color.BLACK);
		g.drawString("    X", 25, 295);
		g.drawRoundRect(23, 282, 40, 15,5,5);

		if(OSY==true){
			g.setColor(Color.GREEN);
		}
		if(OSY==false){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(23, 297, 40, 15,5,5);
		g.setColor(Color.BLACK);
		g.drawString("    Y", 25, 310);
		g.drawRoundRect(23, 297, 40, 15,5,5);

		if(OSZ==true){
			g.setColor(Color.GREEN);
		}
		if(OSZ==false){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(23, 312, 40, 15,5,5);
		g.setColor(Color.BLACK);
		g.drawString("    Z", 25, 325);
		g.drawRoundRect(23, 312, 40, 15,5,5);

		if(KRot==true){
			g.setColor(Color.GREEN);
		}
		if(KRot==false){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(8, 327, 150, 15,5,5);
		g.setColor(Color.BLACK);
		g.drawString("Rotate Variant Textures", 10, 340);
		g.drawRoundRect(8, 327, 150, 15,5,5);

		if(Rend3D==true){
			g.setColor(Color.GREEN);
		}
		if(Rend3D==false){
			g.setColor(Color.RED);
		}
		g.fillRoundRect(8, 342, 150, 15,5,5);
		g.setColor(Color.BLACK);
		g.drawString("Render 3D in inventory", 10, 355);
		g.drawRoundRect(8, 342, 150, 15,5,5);

		if(m==1){ // Check if in UV Editor
			g.setColor(Color.GREEN);

			if(edUV==1)g.fillRect(ux1*20+305,uy1*20+35,(ux12-ux1+1)*20,(uy12-uy1+1)*20);
			else if(edUV==2)g.fillRect(ux2*20+305,uy2*20+35,(ux22-ux2+1)*20,(uy22-uy2+1)*20);
			else if(edUV==3)g.fillRect(ux3*20+305,uy3*20+35,(ux32-ux3+1)*20,(uy32-uy3+1)*20);
			else if(edUV==4)g.fillRect(ux4*20+305,uy4*20+35,(ux42-ux4+1)*20,(uy42-uy4+1)*20);
			else if(edUV==5)g.fillRect(ux5*20+305,uy5*20+35,(ux52-ux5+1)*20,(uy52-uy5+1)*20);
			else if(edUV==6)g.fillRect(ux6*20+305,uy6*20+35,(ux62-ux6+1)*20,(uy62-uy6+1)*20);

			g.setColor(Color.GRAY);
			for(int i=1; i<=16;i++){
				g.drawLine(i*20+305, 35, i*20+305, 16*20+55);
			}
			for(int i=1; i<=16;i++){
				g.drawLine(305, i*20+35, 16*20+325, i*20+35);
			}

			g.setColor(Color.BLUE);
			g.drawString("UV Editor",10,20);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(5, 35, 990, 560);
			g.drawRect(5, 35, 300, 560);
			g.drawLine(795, 35, 795, 595);
			g.drawLine(16*20+325, 35, 16*20+325, 16*20+55);
			g.drawLine(305, 16*20+55, 16*20+325, 16*20+55);

			g.setColor(Color.BLACK);
			g.drawString("Editing face:",800,55);

			if(edUV==1)g.setColor(Color.GRAY);
			g.drawString("Top",805,70); //edUV 1

			g.setColor(Color.BLACK);
			if(edUV==2)g.setColor(Color.GRAY);
			g.drawString("Bottom",805,85);//edUV 2

			g.setColor(Color.BLACK);
			if(edUV==3)g.setColor(Color.GRAY);
			g.drawString("Front           (Facing North)",805,100);//edUV 3

			g.setColor(Color.BLACK);
			if(edUV==4)g.setColor(Color.GRAY);
			g.drawString("Back            (Facing South)",805,115);//edUV 4

			g.setColor(Color.BLACK);
			if(edUV==5)g.setColor(Color.GRAY);
			g.drawString("Left             (Facing East)",805,130);//edUV 5

			g.setColor(Color.BLACK);
			if(edUV==6)g.setColor(Color.GRAY);
			g.drawString("Right           (Facing West)",805,145);//edUV 6


		}


		else if(m==0){ //Check if in Voxel Editor
			g.setColor(Color.BLUE);
			g.drawString("Voxel Editor",10,20);
			g.setColor(Color.DARK_GRAY);
			g.drawRect(5, 35, 990, 560);
			g.drawRect(5, 35, 300, 560);

			g.drawRect(305, 35, 240, 240);
			g.drawRect(555, 285, 240, 240);
			g.drawRect(305, 285, 240, 240);
			g.drawString("Do not redistribute!",490,585);

			g.drawString("Top View, X and Z", 550 , 50);
			g.drawString("Side View, Z and Y", 620 , 540);
			g.drawString("Front View, X and Y", 370 , 540);

			g.setColor(Color.LIGHT_GRAY);//Y-Square Grid
			g.drawLine(315, 35+1, 315, 274);
			g.drawLine(325, 35+1, 325, 274);
			g.drawLine(335, 35+1, 335, 274);
			g.drawLine(345, 35+1, 345, 274);
			g.drawLine(355, 35+1, 355, 274);
			g.drawLine(365, 35+1, 365, 274);
			g.drawLine(375, 35+1, 375, 274);
			g.drawLine(385, 35+1, 385, 274);
			g.drawLine(395, 35+1, 395, 274);
			g.drawLine(405, 35+1, 405, 274);
			g.drawLine(415, 35+1, 415, 274);
			g.drawLine(425, 35+1, 425, 274);
			g.drawLine(435, 35+1, 435, 274);
			g.drawLine(445, 35+1, 445, 274);
			g.drawLine(455, 35+1, 455, 274);
			g.drawLine(465, 35+1, 465, 274);
			g.drawLine(475, 35+1, 475, 274);
			g.drawLine(485, 35+1, 485, 274);
			g.drawLine(495, 35+1, 495, 274);
			g.drawLine(505, 35+1, 505, 274);
			g.drawLine(515, 35+1, 515, 274);
			g.drawLine(525, 35+1, 525, 274);
			g.drawLine(535, 35+1, 535, 274);

			g.drawLine(315, 35+251, 315, 274+250);
			g.drawLine(325, 35+251, 325, 274+250);
			g.drawLine(335, 35+251, 335, 274+250);
			g.drawLine(345, 35+251, 345, 274+250);
			g.drawLine(355, 35+251, 355, 274+250);
			g.drawLine(365, 35+251, 365, 274+250);
			g.drawLine(375, 35+251, 375, 274+250);
			g.drawLine(385, 35+251, 385, 274+250);
			g.drawLine(395, 35+251, 395, 274+250);
			g.drawLine(405, 35+251, 405, 274+250);
			g.drawLine(415, 35+251, 415, 274+250);
			g.drawLine(425, 35+251, 425, 274+250);
			g.drawLine(435, 35+251, 435, 274+250);
			g.drawLine(445, 35+251, 445, 274+250);
			g.drawLine(455, 35+251, 455, 274+250);
			g.drawLine(465, 35+251, 465, 274+250);
			g.drawLine(475, 35+251, 475, 274+250);
			g.drawLine(485, 35+251, 485, 274+250);
			g.drawLine(495, 35+251, 495, 274+250);
			g.drawLine(505, 35+251, 505, 274+250);
			g.drawLine(515, 35+251, 515, 274+250);
			g.drawLine(525, 35+251, 525, 274+250);
			g.drawLine(535, 35+251, 535, 274+250);

			g.drawLine(315+250, 35+251, 315+250, 274+250);
			g.drawLine(325+250, 35+251, 325+250, 274+250);
			g.drawLine(335+250, 35+251, 335+250, 274+250);
			g.drawLine(345+250, 35+251, 345+250, 274+250);
			g.drawLine(355+250, 35+251, 355+250, 274+250);
			g.drawLine(365+250, 35+251, 365+250, 274+250);
			g.drawLine(375+250, 35+251, 375+250, 274+250);
			g.drawLine(385+250, 35+251, 385+250, 274+250);
			g.drawLine(395+250, 35+251, 395+250, 274+250);
			g.drawLine(405+250, 35+251, 405+250, 274+250);
			g.drawLine(415+250, 35+251, 415+250, 274+250);
			g.drawLine(425+250, 35+251, 425+250, 274+250);
			g.drawLine(435+250, 35+251, 435+250, 274+250);
			g.drawLine(445+250, 35+251, 445+250, 274+250);
			g.drawLine(455+250, 35+251, 455+250, 274+250);
			g.drawLine(465+250, 35+251, 465+250, 274+250);
			g.drawLine(475+250, 35+251, 475+250, 274+250);
			g.drawLine(485+250, 35+251, 485+250, 274+250);
			g.drawLine(495+250, 35+251, 495+250, 274+250);
			g.drawLine(505+250, 35+251, 505+250, 274+250);
			g.drawLine(515+250, 35+251, 515+250, 274+250);
			g.drawLine(525+250, 35+251, 525+250, 274+250);
			g.drawLine(535+250, 35+251, 535+250, 274+250);

			g.drawLine(306, 46, 544, 46);
			g.drawLine(306, 56, 544, 56);
			g.drawLine(306, 66, 544, 66);
			g.drawLine(306, 76, 544, 76);
			g.drawLine(306, 86, 544, 86);
			g.drawLine(306, 96, 544, 96);
			g.drawLine(306, 106, 544, 106);
			g.drawLine(306, 116, 544, 116);
			g.drawLine(306, 126, 544, 126);
			g.drawLine(306, 136, 544, 136);
			g.drawLine(306, 146, 544, 146);
			g.drawLine(306, 156, 544, 156);
			g.drawLine(306, 166, 544, 166);
			g.drawLine(306, 176, 544, 176);
			g.drawLine(306, 186, 544, 186);
			g.drawLine(306, 196, 544, 196);
			g.drawLine(306, 206, 544, 206);
			g.drawLine(306, 216, 544, 216);
			g.drawLine(306, 226, 544, 226);
			g.drawLine(306, 236, 544, 236);
			g.drawLine(306, 246, 544, 246);
			g.drawLine(306, 256, 544, 256);
			g.drawLine(306, 266, 544, 266);

			g.drawLine(306, 46+250, 544, 46+250);
			g.drawLine(306, 56+250, 544, 56+250);
			g.drawLine(306, 66+250, 544, 66+250);
			g.drawLine(306, 76+250, 544, 76+250);
			g.drawLine(306, 86+250, 544, 86+250);
			g.drawLine(306, 96+250, 544, 96+250);
			g.drawLine(306, 106+250, 544, 106+250);
			g.drawLine(306, 116+250, 544, 116+250);
			g.drawLine(306, 126+250, 544, 126+250);
			g.drawLine(306, 136+250, 544, 136+250);
			g.drawLine(306, 146+250, 544, 146+250);
			g.drawLine(306, 156+250, 544, 156+250);
			g.drawLine(306, 166+250, 544, 166+250);
			g.drawLine(306, 176+250, 544, 176+250);
			g.drawLine(306, 186+250, 544, 186+250);
			g.drawLine(306, 196+250, 544, 196+250);
			g.drawLine(306, 206+250, 544, 206+250);
			g.drawLine(306, 216+250, 544, 216+250);
			g.drawLine(306, 226+250, 544, 226+250);
			g.drawLine(306, 236+250, 544, 236+250);
			g.drawLine(306, 246+250, 544, 246+250);
			g.drawLine(306, 256+250, 544, 256+250);
			g.drawLine(306, 266+250, 544, 266+250);

			g.drawLine(306+250, 46+250, 544+250, 46+250);
			g.drawLine(306+250, 56+250, 544+250, 56+250);
			g.drawLine(306+250, 66+250, 544+250, 66+250);
			g.drawLine(306+250, 76+250, 544+250, 76+250);
			g.drawLine(306+250, 86+250, 544+250, 86+250);
			g.drawLine(306+250, 96+250, 544+250, 96+250);
			g.drawLine(306+250, 106+250, 544+250, 106+250);
			g.drawLine(306+250, 116+250, 544+250, 116+250);
			g.drawLine(306+250, 126+250, 544+250, 126+250);
			g.drawLine(306+250, 136+250, 544+250, 136+250);
			g.drawLine(306+250, 146+250, 544+250, 146+250);
			g.drawLine(306+250, 156+250, 544+250, 156+250);
			g.drawLine(306+250, 166+250, 544+250, 166+250);
			g.drawLine(306+250, 176+250, 544+250, 176+250);
			g.drawLine(306+250, 186+250, 544+250, 186+250);
			g.drawLine(306+250, 196+250, 544+250, 196+250);
			g.drawLine(306+250, 206+250, 544+250, 206+250);
			g.drawLine(306+250, 216+250, 544+250, 216+250);
			g.drawLine(306+250, 226+250, 544+250, 226+250);
			g.drawLine(306+250, 236+250, 544+250, 236+250);
			g.drawLine(306+250, 246+250, 544+250, 246+250);
			g.drawLine(306+250, 256+250, 544+250, 256+250);
			g.drawLine(306+250, 266+250, 544+250, 266+250);

			g.setColor(Color.RED);
			g.fillRect(346, 77, 9, 9);
			g.fillRect(346, 327, 9, 9);
			g.fillRect(596, 327, 9, 9);
			g.fillRect(496, 77, 9, 9);
			g.fillRect(496, 327, 9, 9);
			g.fillRect(746, 327, 9, 9);
			g.fillRect(346, 227, 9, 9);
			g.fillRect(346, 477, 9, 9);
			g.fillRect(596, 477, 9, 9);
			g.fillRect(496, 227, 9, 9);
			g.fillRect(496, 477, 9, 9);
			g.fillRect(746, 477, 9, 9);

			g.setColor(Color.DARK_GRAY);
			g.drawRect(795, 395, 200, 200);
			g.drawString("3D viewer in progress!",825,450);
			g.drawLine(795, 395, 795, 35);

			if(sn>=1){
				g.setColor(Color.YELLOW);
				g.fillRect(lx*10+346,lz*10+77,(lx2-lx)*10-1,(lz2-lz)*10-1);
				g.fillRect(lx*10+346,ly*10+327,(lx2-lx)*10-1,(ly2-ly)*10-1);
				g.fillRect(lz*10+596,ly*10+327,(lz2-lz)*10-1,(ly2-ly)*10-1);
			}
			g.setColor(Color.GREEN);
			g.fillRect(x*10+346,z*10+77,(x2-x)*10-1,(z2-z)*10-1);
			g.fillRect(x*10+346,y*10+327,(x2-x)*10-1,(y2-y)*10-1);
			g.fillRect(z*10+596,y*10+327,(z2-z)*10-1,(y2-y)*10-1);


		}

		repaint();
	} 

	public void setXVal(int val){
		VC[sn*3]=val;
	}
	public void setYVal(int val){
		VC[sn*3+1]=val;
	}
	public void setZVal(int val){
		VC[sn*3+2]=val;
	}

	public void setX2Val(int val){
		VC2[sn*3]=val;
	}
	public void setY2Val(int val){
		VC2[sn*3+1]=val;
	}
	public void setZ2Val(int val){
		VC2[sn*3+2]=val;
	}


	private class Handler implements ActionListener,MouseListener,MouseMotionListener{

		public void actionPerformed(ActionEvent event) {
			if(TM==event.getSource()){
				if(m==1){
					m=0;
					repaint();
				}else{
					m=1;
					repaint();
				}
			}

			if(Save==event.getSource()){

				Writer w = new Writer();
				Export ex = new Export();

				ex.export(ao,VC, VC2,UVC0,UVC02,UVC1,UVC12,UVC2,UVC22,UVC3,UVC32,UVC4,UVC42,UVC5,UVC52,OSX,OSY,OSZ,KRot,Rend3D);
				String contents = ex.export;
				int acFc = fc.showSaveDialog(MyPanel.this);
				if(acFc == fc.APPROVE_OPTION){

					File otherFile = new File(fc.getSelectedFile().getPath()+".json");

					File file = fc.getSelectedFile();
					String filename=file.getPath();
					if(w.getExtension(file)!=null){
						System.out.println(w.getExtension(file));
						int extPos = file.getPath().lastIndexOf(".");
						if(extPos == -1) {
							filename = file.getPath();
						}
						else {
							filename = file.getPath().substring(0, extPos);
						}
					}
					File anotherFile=new File(filename+".json");

					if(fc.getSelectedFile().exists()||otherFile.exists()||anotherFile.exists()){

						int opt = er.showConfirmDialog(MyPanel.this, "This file already exists. Saving it will overwrite it.\nAre you sure you want to save it?","Warning!",JOptionPane.WARNING_MESSAGE);

						if(opt==0){
							w.Write(fc.getSelectedFile(), contents);
							er.showMessageDialog(MyPanel.this, "Succesfully saved!","Success",JOptionPane.INFORMATION_MESSAGE);
						}
					}else{
						w.Write(fc.getSelectedFile(), contents);
						er.showMessageDialog(MyPanel.this, "Succesfully saved!","Success",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}

			if(Open==event.getSource()){
				Writer w = new Writer();

				fc.setAcceptAllFileFilterUsed(false);

				int acFc = fc.showOpenDialog(MyPanel.this);
				if(acFc == fc.APPROVE_OPTION){
					System.out.println(fc.getSelectedFile());
					Import i = new Import();
					int opt = er.showConfirmDialog(MyPanel.this, "Are you sure you want to open this file?\nYour current progress will be lost","Warning!",JOptionPane.WARNING_MESSAGE);
					if(opt==0){
						try {

							ao=i.getAO(fc.getSelectedFile());
							OSX=i.getOSX(fc.getSelectedFile());
							OSY=i.getOSY(fc.getSelectedFile());
							OSZ=i.getOSZ(fc.getSelectedFile());
							KRot=i.getKRot(fc.getSelectedFile());
							Rend3D=i.getRend3D(fc.getSelectedFile());

							VC=i.getVC(fc.getSelectedFile());
							VC2=i.getVC2(fc.getSelectedFile());

							er.showMessageDialog(MyPanel.this, "This file seems to have opened correctly!","Success",JOptionPane.INFORMATION_MESSAGE);

						} catch (IOException e) {
							e.printStackTrace();
							er.showMessageDialog(MyPanel.this, "Load corrupted\nIOException:"+e,"Error",JOptionPane.ERROR_MESSAGE);
						} catch (ParseException e) {
							e.printStackTrace();
							er.showMessageDialog(MyPanel.this, "Load corrupted\nParseException:"+e,"Error",JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}

			if(Reset==event.getSource()){
				VC[sn*3]=0;
				VC[sn*3+1]=0;
				VC[sn*3+2]=0;
				VC2[sn*3]=0;
				VC2[sn*3+1]=0;
				VC2[sn*3+2]=0;
			}
		}

		public void mouseClicked(MouseEvent event) {
			System.out.println(event.getX() + ":"+event.getY());

			if(event.getX()>=10 && event.getX()<= 110 && event.getY()>=255 && event.getY() <= 265){
				if(ao==true){
					ao=false;
				}
				else if(ao==false){
					ao=true;
				}
			}

			if(event.getX()>=20 && event.getX()<= 65 && event.getY()>=285 && event.getY() <= 295){
				if(OSX==true){
					OSX=false;
				}
				else if(OSX==false){
					OSX=true;
				}
			}
			if(event.getX()>=20 && event.getX()<= 65 && event.getY()>=300 && event.getY() <= 315){
				if(OSY==true){
					OSY=false;
				}
				else if(OSY==false){
					OSY=true;
				}
			}
			if(event.getX()>=20 && event.getX()<= 65 && event.getY()>=315 && event.getY() <= 330){
				if(OSZ==true){
					OSZ=false;
				}
				else if(OSZ==false){
					OSZ=true;
				}
			}
			if(event.getX()>=10 && event.getX()<= 175 && event.getY()>=330 && event.getY() <= 344){
				if(KRot==true){
					KRot=false;
				}
				else if(KRot==false){
					KRot=true;
				}
			}
			if(event.getX()>=10 && event.getX()<= 170 && event.getY()>=345 && event.getY() <= 355){
				if(Rend3D==true){
					Rend3D=false;
				}
				else if(Rend3D==false){
					Rend3D=true;
				}
			}
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}

		public void mousePressed(MouseEvent event) {
			if(m==0){
				if(event.getX()>=306&&event.getX()<=544&&event.getY()>=36&&event.getY()<=274){
					VC[sn*3]=(event.getX()-355)/10;
					VC[sn*3+2]=(event.getY()-85)/10;
				}
				if(event.getX()>=306&&event.getX()<=545&&event.getY()>=286 && event.getY()<=528){
					VC[sn*3]=(event.getX()-355)/10;
					VC[sn*3+1]=(event.getY()-330)/10;
				}
				if(event.getX()>=556 && event.getX()<=795 && event.getY()>=286 && event.getY()<=528){
					VC[sn*3+2]=(event.getX()-605)/10;
					VC[sn*3+1]=(event.getY()-330)/10;
				}
			}else{
				if(event.getX()>=306 && event.getX()<=304+16*21 && event.getY()>=36 && event.getY()<=34+16*21){
					if(edUV==1){
						UVC0[sn*2]=(event.getX()-305)/20;
						UVC0[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==2){
						UVC1[sn*2]=(event.getX()-305)/20;
						UVC1[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==3){
						UVC2[sn*2]=(event.getX()-305)/20;
						UVC2[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==4){
						UVC3[sn*2]=(event.getX()-305)/20;
						UVC3[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==5){
						UVC4[sn*2]=(event.getX()-305)/20;
						UVC4[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==6){
						UVC5[sn*2]=(event.getX()-305)/20;
						UVC5[sn*2+1]=(event.getY()-35)/20;
					}
				}

				if(event.getX()>=800 && event.getX()<=975 && event.getY()>=60 && event.getY()<=75){
					edUV=1;
				}else if(event.getX()>=800 && event.getX()<=975 && event.getY()>=75 && event.getY()<=90){
					edUV=2;
				}else if(event.getX()>=800 && event.getX()<=975 && event.getY()>=90 && event.getY()<=105){
					edUV=3;
				}else if(event.getX()>=800 && event.getX()<=975 && event.getY()>=105 && event.getY()<=120){
					edUV=4;
				}else if(event.getX()>=800 && event.getX()<=975 && event.getY()>=120 && event.getY()<=135){
					edUV=5;
				}else if(event.getX()>=800 && event.getX()<=975 && event.getY()>=135 && event.getY()<=150){
					edUV=6;
				}
			}
		}

		public void mouseReleased(MouseEvent event) {
		}

		public void mouseDragged(MouseEvent event) {	
			if(m==0){
				if(event.getX()>=305&&event.getX()<=544&&event.getY()>=35&&event.getY()<=274){
					VC2[sn*3]=(event.getX()-335)/10;
					VC2[sn*3+2]=(event.getY()-65)/10;
				}

				if(event.getX()>=305 && event.getX()<=544 && event.getY()>=288 && event.getY()<=528){
					VC2[sn*3]=(event.getX()-335)/10;
					VC2[sn*3+1]=(event.getY()-315)/10;
				}
				if(event.getX()>=555 && event.getX()<=795 && event.getY()>=288 && event.getY()<=528){
					VC2[sn*3+2]=(event.getX()-585)/10;
					VC2[sn*3+1]=(event.getY()-325)/10;
				}
			}else{
				if(event.getX()>=306 && event.getX()<=304+16*21 && event.getY()>=36 && event.getY()<=34+16*21){
					if(edUV==1){
						UVC02[sn*2]=(event.getX()-305)/20;
						UVC02[sn*2+1]=(event.getY()-35)/20;}
					else if(edUV==2){
						UVC12[sn*2]=(event.getX()-305)/20;
						UVC12[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==3){
						UVC22[sn*2]=(event.getX()-305)/20;
						UVC22[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==4){
						UVC32[sn*2]=(event.getX()-305)/20;
						UVC32[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==5){
						UVC42[sn*2]=(event.getX()-305)/20;
						UVC42[sn*2+1]=(event.getY()-35)/20;
					}else if(edUV==6){
						UVC52[sn*2]=(event.getX()-305)/20;
						UVC52[sn*2+1]=(event.getY()-35)/20;
					}
				}
			}
		}

		public void mouseMoved(MouseEvent event) {
		}
	}
}
