package au.com.AidoP;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import au.com.AidoP.Export.Prepare;
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

public class Screen{

	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}


	private static void createAndShowGUI(){
		JFrame f =new JFrame("MC Model Maker - " + getSplash((int)(Math.random()*10)));
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setResizable(false);
		f.add(new Panel());
		f.pack();
		f.setVisible(true);
	}


	private static String getSplash(int i) {
		String splash = "Yay! An Easter Egg!";
		if(i==0)splash = "Well, this is new!";
		else if(i==1)splash = "With a cherry on top!";
		else if(i==2)splash = "This. Question. Is. False!";
		else if(i==3)splash = "Don't make lemonade!";
		else if(i==4)splash = "Auf Wiedersehen!";
		else if(i==5)splash = "With the low price of $99!";
		else if(i==6)splash = "Made with love!";
		else if(i==7)splash = "10010111010010101010010101";
		else if(i==8)splash = "Now with 100% more Voxels!*";
		else if(i==9)splash = "0 is the new 1";
		else if(i==10)splash = "Now you see me, now you don't!";
		else splash = "I'm a bug! Squish me!";
		return splash;
	}
}

class Panel extends JPanel{

	//Initialise Voxels and UV Data--

	X XData = new X();
	Y YData = new Y();
	Z ZData = new Z();
	//----------------
	Front NorthData = new Front();    //North
	Back SouthData = new Back();      //South
	Left WestData = new Left();       //West
	Right EastData = new Right();     //East
	Top TopData = new Top();          //Top
	Bottom BottomData = new Bottom(); //Bottom

	OptionData OptionData = new OptionData();
	//End init of Voxels and UV Data--

	//Initialise Other Values--

	ImageIcon icon = new ImageIcon(getClass().getResource("/res/Icon.png"));

	Prepare PrepareExport = new Prepare();

	boolean DevMode = false;

	int VoxEditing = 0;
	int FaceEditing = 0;

	int mouseX = 0, mouseY = 0;

	final int VOXEL = 0;
	final int UV = 1;

	final int NORTH = 0;
	final int SOUTH = 1;
	final int EAST = 2;
	final int WEST = 3;
	final int TOP = 4;
	final int BOTTOM = 5;
	int Mode = VOXEL;

	String listOptions[] = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "72", "73", "74", "75", "76", "77", "78", "79", "80", "81", "82", "83", "84", "85", "86", "87", "88", "89", "90", "91", "92", "93", "94", "95", "96", "97", "98", "99" };

	//End init Other Values--


	//Initialise GUI Variables--

	JButton swapEditorBtn;
	JButton resetBtn;
	JButton previewBtn;
	JButton exportBtn;

	JComboBox swapVoxelBox;

	JTextArea preview;
	JOptionPane popUp;

	//End init GUI Variables--


	public Panel(){ //For GUI Initilisation.

		exportBtn = new JButton("Export");
		exportBtn.setToolTipText("Export your model to a JSON File.");
		//add(exportBtn);

		previewBtn = new JButton("Raw Preview");
		previewBtn.setToolTipText("Shows a raw JSON file view of your current model, ready to export.");
		add(previewBtn);

		swapEditorBtn = new JButton("Swap Editor");
		swapEditorBtn.setToolTipText("Swap between Voxel Editing and UV Editing");
		add(swapEditorBtn);

		resetBtn = new JButton("Reset");
		resetBtn.setToolTipText("Resets all the Voxel and UV Data of the current Voxel.");
		add(resetBtn);

		swapVoxelBox = new JComboBox(listOptions);
		swapVoxelBox.setToolTipText("Selects the Voxel set to edit");
		add(swapVoxelBox);

		//Event Handling
		EventHandler h = new EventHandler();
		swapEditorBtn.addActionListener(h);
		resetBtn.addActionListener(h);
		previewBtn.addActionListener(h);
		exportBtn.addActionListener(h);

		swapVoxelBox.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				VoxEditing = Integer.parseInt(e.getItem().toString());
			}
		});

		addMouseListener(h);
		addMouseMotionListener(h);


		//Set up basic cube.

		XData.setBase(16, 0);
		YData.setBase(16, 0);
		ZData.setBase(16, 0);

		NorthData.setXBase(16, 0);
		SouthData.setXBase(16, 0);
		EastData.setXBase(16, 0);
		WestData.setXBase(16, 0);
		TopData.setXBase(16, 0);
		BottomData.setXBase(16, 0);
		NorthData.setYBase(16, 0);
		SouthData.setYBase(16, 0);
		EastData.setYBase(16, 0);
		WestData.setYBase(16, 0);
		TopData.setYBase(16, 0);
		BottomData.setYBase(16, 0);

		NorthData.setCulling(true, 0);
		SouthData.setCulling(true, 0);
		EastData.setCulling(true, 0);
		WestData.setCulling(true, 0);
		TopData.setCulling(true, 0);
		BottomData.setCulling(true, 0);

		//Basic cube finished setting up
	}

	public Dimension getPreferredSize(){
		return new Dimension(1000, 600);
	}

	//Paint-----
	protected void paintComponent(Graphics g){
		super.paintComponent(g);

		resetBtn.setText("Reset Voxel " + VoxEditing );

		//Set up Font
		g.setFont(new Font("Arial", Font.PLAIN, 14));

		g.setColor(Color.BLACK);

		//Load and display mainGrid
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image Grid = toolkit.getImage(getClass().getResource("/res/mainGrid.png"));
		g.drawImage(Grid, 0, 0, null);

		//Display worldwide Info
		g.drawString("Editing Voxel & UV set " + VoxEditing, 5, 55);
		g.drawString("Voxel Origin { " + XData.getOrigin(VoxEditing) + ", " + YData.getOrigin(VoxEditing) + ", " + ZData.getOrigin(VoxEditing) + " }", 5, 70);
		g.drawString("Voxel Base   { " + XData.getBase(VoxEditing) + ", " + YData.getBase(VoxEditing) + ", " + ZData.getBase(VoxEditing) + " }", 5, 85);
		g.drawString("UV North   { " + NorthData.getXOrigin(VoxEditing) + ", " + NorthData.getYOrigin(VoxEditing) + ", " + NorthData.getXBase(VoxEditing) + ", " + NorthData.getYBase(VoxEditing) + " } Cull:" + NorthData.getCulling(VoxEditing), 5, 115);
		g.drawString("UV South   { " + SouthData.getXOrigin(VoxEditing) + ", " + SouthData.getYOrigin(VoxEditing) + ", " + SouthData.getXBase(VoxEditing) + ", " + SouthData.getYBase(VoxEditing) + " } Cull:" + SouthData.getCulling(VoxEditing), 5, 130);
		g.drawString("UV East     { " + EastData.getXOrigin(VoxEditing) + ", " + EastData.getYOrigin(VoxEditing) + ", " + EastData.getXBase(VoxEditing) + ", " + EastData.getYBase(VoxEditing) + " } Cull:" + EastData.getCulling(VoxEditing), 5, 145);
		g.drawString("UV West    { " + WestData.getXOrigin(VoxEditing) + ", " + WestData.getYOrigin(VoxEditing) + ", " + WestData.getXBase(VoxEditing) + ", " + WestData.getYBase(VoxEditing) + " } Cull:" + WestData.getCulling(VoxEditing), 5, 160);
		g.drawString("UV Top      { " + TopData.getXOrigin(VoxEditing) + ", " + TopData.getYOrigin(VoxEditing) + ", " + TopData.getXBase(VoxEditing) + ", " + TopData.getYBase(VoxEditing) + " } Cull:" + TopData.getCulling(VoxEditing), 5, 175);
		g.drawString("UV Bottom { " + BottomData.getXOrigin(VoxEditing) + ", " + BottomData.getYOrigin(VoxEditing) + ", " + BottomData.getXBase(VoxEditing) + ", " + BottomData.getYBase(VoxEditing) + " } Cull:" + BottomData.getCulling(VoxEditing), 5, 190);

		g.drawString("Model Options:", 5, 220);

		if(OptionData.getAmbientOcc()) g.setColor(Color.GRAY);
		else g.setColor(Color.BLACK);
		g.drawString("Ambient Occlusion", 5, 235);
		if(OptionData.getRandOffsetX()) g.setColor(Color.GRAY);
		else g.setColor(Color.BLACK);
		g.drawString("Random Offset X", 5, 250);
		if(OptionData.getRandOffsetY()) g.setColor(Color.GRAY);
		else g.setColor(Color.BLACK);
		g.drawString("Random Offset Y", 5, 265);
		if(OptionData.getRandOffsetZ()) g.setColor(Color.GRAY);
		else g.setColor(Color.BLACK);
		g.drawString("Random Offset Z", 5, 280);

		g.setColor(Color.BLACK);
		g.drawString("Change particle texture", 5, 310);

		g.drawString("For help click here or visit http://mcmodelmaker.weebly.com", 373, 555);


		if(Mode == VOXEL){
			g.setColor(Color.BLUE);
			g.drawString("Voxel Editor", 5, 20);

			Image VoxGrid = toolkit.getImage(getClass().getResource("/res/VoxelGrid.png"));
			Image VoxBGrid = toolkit.getImage(getClass().getResource("/res/VoxelBackGrid.png"));

			g.drawImage(VoxBGrid, 0, 0, null);

			//Draw cube faces

			if(VoxEditing >= 1){
				g.setColor(Color.YELLOW);
				g.fillRect(XData.getOrigin(VoxEditing-1) * 10 + 341, ZData.getOrigin(VoxEditing-1) * 10 + 75,  (XData.getBase(VoxEditing-1) * 10 + 341) - (XData.getOrigin(VoxEditing-1) * 10 + 341), (ZData.getBase(VoxEditing-1) * 10 + 341) - (ZData.getOrigin(VoxEditing-1) * 10 + 341));
				g.fillRect(XData.getOrigin(VoxEditing-1) * 10 + 341, YData.getOrigin(VoxEditing-1) * 10 + 334,  (XData.getBase(VoxEditing-1) * 10 + 341) - (XData.getOrigin(VoxEditing-1) * 10 + 341), (YData.getBase(VoxEditing-1) * 10 + 341) - (YData.getOrigin(VoxEditing-1) * 10 + 341));
				g.fillRect(ZData.getOrigin(VoxEditing-1) * 10 + 601, YData.getOrigin(VoxEditing-1) * 10 + 334,  (ZData.getBase(VoxEditing-1) * 10 + 341) - (ZData.getOrigin(VoxEditing-1) * 10 + 341), (YData.getBase(VoxEditing-1) * 10 + 601) - (YData.getOrigin(VoxEditing-1) * 10 + 601));

			}

			g.setColor(Color.GREEN);
			g.fillRect(XData.getOrigin(VoxEditing) * 10 + 341, ZData.getOrigin(VoxEditing) * 10 + 75,  (XData.getBase(VoxEditing) * 10 + 341) - (XData.getOrigin(VoxEditing) * 10 + 341), (ZData.getBase(VoxEditing) * 10 + 341) - (ZData.getOrigin(VoxEditing) * 10 + 341));
			g.fillRect(XData.getOrigin(VoxEditing) * 10 + 341, YData.getOrigin(VoxEditing) * 10 + 334,  (XData.getBase(VoxEditing) * 10 + 341) - (XData.getOrigin(VoxEditing) * 10 + 341), (YData.getBase(VoxEditing) * 10 + 341) - (YData.getOrigin(VoxEditing) * 10 + 341));
			g.fillRect(ZData.getOrigin(VoxEditing) * 10 + 601, YData.getOrigin(VoxEditing) * 10 + 334,  (ZData.getBase(VoxEditing) * 10 + 341) - (ZData.getOrigin(VoxEditing) * 10 + 341), (YData.getBase(VoxEditing) * 10 + 601) - (YData.getOrigin(VoxEditing) * 10 + 601));


			g.drawImage(VoxGrid, 0, 0, null);

			Image comment = toolkit.getImage(getClass().getResource("/res/comment.png"));
			g.drawImage(comment, 800, 35, 110, 40, null);

			g.setColor(Color.BLACK);
			g.drawString("Add Comment", 810, 95);

		}else if(Mode == UV){
			g.setColor(Color.BLUE);
			g.drawString("UV Editor", 5, 20);

			Image UVBG = toolkit.getImage("/BackgroundForMCMM.png");//Draw Background
			if(UVBG != null && UVBG.getWidth(null) == 16 && UVBG.getHeight(null) == 16) g.drawImage(UVBG, 301, 36, 320, 320, null);


			g.setColor(Color.GREEN);
			if(FaceEditing == NORTH) g.fillRect(NorthData.getXOrigin(VoxEditing) * 20 + 301, NorthData.getYOrigin(VoxEditing) * 20 + 36,  (NorthData.getXBase(VoxEditing) * 20 + 281) - (NorthData.getXOrigin(VoxEditing) * 20 + 281), (NorthData.getYBase(VoxEditing) * 20 + 281) - (NorthData.getYOrigin(VoxEditing) * 20 + 281));
			else if(FaceEditing == SOUTH) g.fillRect(SouthData.getXOrigin(VoxEditing) * 20 + 301, SouthData.getYOrigin(VoxEditing) * 20 + 36,  (SouthData.getXBase(VoxEditing) * 20 + 281) - (SouthData.getXOrigin(VoxEditing) * 20 + 281), (SouthData.getYBase(VoxEditing) * 20 + 281) - (SouthData.getYOrigin(VoxEditing) * 20 + 281));
			else if(FaceEditing == EAST) g.fillRect(EastData.getXOrigin(VoxEditing) * 20 + 301, EastData.getYOrigin(VoxEditing) * 20 + 36,  (EastData.getXBase(VoxEditing) * 20 + 281) - (EastData.getXOrigin(VoxEditing) * 20 + 281), (EastData.getYBase(VoxEditing) * 20 + 281) - (EastData.getYOrigin(VoxEditing) * 20 + 281));
			else if(FaceEditing == WEST) g.fillRect(WestData.getXOrigin(VoxEditing) * 20 + 301, WestData.getYOrigin(VoxEditing) * 20 + 36,  (WestData.getXBase(VoxEditing) * 20 + 281) - (WestData.getXOrigin(VoxEditing) * 20 + 281), (WestData.getYBase(VoxEditing) * 20 + 281) - (WestData.getYOrigin(VoxEditing) * 20 + 281));
			else if(FaceEditing == TOP) g.fillRect(TopData.getXOrigin(VoxEditing) * 20 + 301, TopData.getYOrigin(VoxEditing) * 20 + 36,  (TopData.getXBase(VoxEditing) * 20 + 281) - (TopData.getXOrigin(VoxEditing) * 20 + 281), (TopData.getYBase(VoxEditing) * 20 + 281) - (TopData.getYOrigin(VoxEditing) * 20 + 281));
			else if(FaceEditing == BOTTOM) g.fillRect(BottomData.getXOrigin(VoxEditing) * 20 + 301, BottomData.getYOrigin(VoxEditing) * 20 + 36,  (BottomData.getXBase(VoxEditing) * 20 + 281) - (BottomData.getXOrigin(VoxEditing) * 20 + 281), (BottomData.getYBase(VoxEditing) * 20 + 281) - (BottomData.getYOrigin(VoxEditing) * 20 + 281));

			Image UVGrid = toolkit.getImage(getClass().getResource("/res/UVGrid.png"));
			g.drawImage(UVGrid, 0, 0, null);

			Image changeTex = toolkit.getImage(getClass().getResource("/res/changeTex.png"));
			g.drawImage(changeTex, 801, 36, 100, 100, null);

			String FaceEdStr = "North";

			if(FaceEditing == NORTH)FaceEdStr = "North";
			if(FaceEditing == SOUTH)FaceEdStr = "South";
			if(FaceEditing == EAST)FaceEdStr = "East";
			if(FaceEditing == WEST)FaceEdStr = "West";
			if(FaceEditing == TOP)FaceEdStr = "Top";
			if(FaceEditing == BOTTOM)FaceEdStr = "Bottom";

			g.setColor(Color.BLACK);
			g.drawString("Editing " + FaceEdStr + " Face", 625, 55);

			if(FaceEditing == NORTH) g.setColor(Color.GRAY);
			else g.setColor(Color.BLACK);
			g.drawString("North", 625, 70);
			if(FaceEditing == SOUTH) g.setColor(Color.GRAY);
			else g.setColor(Color.BLACK);
			g.drawString("South", 625, 85);
			if(FaceEditing == EAST) g.setColor(Color.GRAY);
			else g.setColor(Color.BLACK);
			g.drawString("East", 625, 100);
			if(FaceEditing == WEST) g.setColor(Color.GRAY);
			else g.setColor(Color.BLACK);
			g.drawString("West", 625, 115);
			if(FaceEditing == TOP) g.setColor(Color.GRAY);
			else g.setColor(Color.BLACK);
			g.drawString("Top", 625, 130);
			if(FaceEditing == BOTTOM) g.setColor(Color.GRAY);
			else g.setColor(Color.BLACK);
			g.drawString("Bottom", 625, 145);


			Image Cullcross = toolkit.getImage(getClass().getResource("/res/cullX.png"));
			Image Culltick = toolkit.getImage(getClass().getResource("/res/cullTick.png"));
			if(FaceEditing == NORTH){
				if(!NorthData.getCulling(VoxEditing)) g.drawImage(Cullcross, 801, 146, 100, 100, null);
				else g.drawImage(Culltick, 801, 146, 100, 100, null);
			}else if(FaceEditing == SOUTH){
				if(!SouthData.getCulling(VoxEditing)) g.drawImage(Cullcross, 801, 146, 100, 100, null);
				else g.drawImage(Culltick, 801, 146, 100, 100, null);
			}else if(FaceEditing == WEST){
				if(!WestData.getCulling(VoxEditing)) g.drawImage(Cullcross, 801, 146, 100, 100, null);
				else g.drawImage(Culltick, 801, 146, 100, 100, null);
			}else if(FaceEditing == EAST){
				if(!EastData.getCulling(VoxEditing)) g.drawImage(Cullcross, 801, 146, 100, 100, null);
				else g.drawImage(Culltick, 801, 146, 100, 100, null);
			}else if(FaceEditing == TOP){
				if(!TopData.getCulling(VoxEditing)) g.drawImage(Cullcross, 801, 146, 100, 100, null);
				else g.drawImage(Culltick, 801, 146, 100, 100, null);
			}else if(FaceEditing == BOTTOM){
				if(!BottomData.getCulling(VoxEditing)) g.drawImage(Cullcross, 801, 146, 100, 100, null);
				else g.drawImage(Culltick, 801, 146, 100, 100, null);
			}

		}else{
			g.setColor(Color.RED);
			g.drawString("ERROR", 5, 20);
		}


		if(DevMode){
			g.setColor(Color.BLACK);
			g.drawString(mouseX + ", " + mouseY, mouseX, mouseY-5);
		}

		repaint();
	}

	private class EventHandler implements ActionListener, MouseListener, MouseMotionListener{

		public void mouseDragged(MouseEvent e) {}

		public void mouseMoved(MouseEvent e) {mouseX = e.getX(); mouseY = e.getY();}

		public void mouseClicked(MouseEvent e) {

			if(e.getX() >= 371 && e.getY() >= 545 && e.getX() <= 752 && e.getY() <= 560){
				try 
				{
					Desktop.getDesktop().browse(new URL("http://mcmodelmaker.weebly.com").toURI());
				}           
				catch (Exception ex) {}
			}

			if(e.getX() >= 5 && e.getY() >= 225 && e.getX() <= 122 && e.getY() <= 240) OptionData.setAmbientOcc(!OptionData.getAmbientOcc());
			if(e.getX() >= 5 && e.getY() >= 241 && e.getX() <= 114 && e.getY() <= 255) OptionData.setRandOffsetX(!OptionData.getRandOffsetX());
			if(e.getX() >= 5 && e.getY() >= 256 && e.getX() <= 114 && e.getY() <= 270) OptionData.setRandOffsetY(!OptionData.getRandOffsetY());
			if(e.getX() >= 5 && e.getY() >= 271 && e.getX() <= 114 && e.getY() <= 285) OptionData.setRandOffsetZ(!OptionData.getRandOffsetZ());

			if(e.getX() >= 5 && e.getY() >= 300 && e.getX() <= 155 && e.getY() <= 315){

				String texstr = OptionData.getParticleTexture();

				String texpath = popUp.showInputDialog(Panel.this, "Please input the path to the texture.", texstr);
				if(texpath != null) OptionData.setParticleTexture(texpath);
			}

			if(Mode == VOXEL){

				if(e.getX() >= 301 && e.getX() <= 540 && e.getY() >= 37 && e.getY() <= 276){

					if(e.getButton() == 1){ //Left Click

						double rawx = ((double)(e.getX()-341))/10;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-78))/10;
						int grdy = (int)Math.floor(rawy);

						XData.setOrigin(grdx, VoxEditing);
						ZData.setOrigin(grdy, VoxEditing);
					}

					if(e.getButton() == 3){ //Right Click

						double rawx = ((double)(e.getX()-331))/10;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-68))/10;
						int grdy = (int)Math.floor(rawy);

						XData.setBase(grdx, VoxEditing);
						ZData.setBase(grdy, VoxEditing);
					}

					if(e.getButton() == 2){ //Middle Click
						XData.setOrigin(0, VoxEditing);
						ZData.setOrigin(0, VoxEditing);
						XData.setBase(0, VoxEditing);
						ZData.setBase(0, VoxEditing);
					}

				}

				if(e.getX() >= 301 && e.getX() <= 540 && e.getY() >= 297 && e.getY() <= 536){

					if(e.getButton() == 1){ //Left Click

						double rawx = ((double)(e.getX()-341))/10;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-337))/10;
						int grdy = (int)Math.floor(rawy);

						XData.setOrigin(grdx, VoxEditing);
						YData.setOrigin(grdy, VoxEditing);
					}

					if(e.getButton() == 3){ //Right Click

						double rawx = ((double)(e.getX()-331))/10;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-327))/10;
						int grdy = (int)Math.floor(rawy);

						XData.setBase(grdx, VoxEditing);
						YData.setBase(grdy, VoxEditing);
					}

					if(e.getButton() == 2){ //Middle Click
						XData.setOrigin(0, VoxEditing);
						YData.setOrigin(0, VoxEditing);
						XData.setBase(0, VoxEditing);
						YData.setBase(0, VoxEditing);
					}

				}

				if(e.getX() >= 561 && e.getX() <= 800 && e.getY() >= 297 && e.getY() <= 536){

					if(e.getButton() == 1){ //Left Click

						double rawx = ((double)(e.getX()-601))/10;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-337))/10;
						int grdy = (int)Math.floor(rawy);

						ZData.setOrigin(grdx, VoxEditing);
						YData.setOrigin(grdy, VoxEditing);
					}

					if(e.getButton() == 3){ //Right Click

						double rawx = ((double)(e.getX()-591))/10;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-327))/10;
						int grdy = (int)Math.floor(rawy);

						ZData.setBase(grdx, VoxEditing);
						YData.setBase(grdy, VoxEditing);
					}

					if(e.getButton() == 2){ //Middle Click
						ZData.setOrigin(0, VoxEditing);
						YData.setOrigin(0, VoxEditing);
						ZData.setBase(0, VoxEditing);
						YData.setBase(0, VoxEditing);
					}

				}

				if(e.getX() >= 801 && e.getX() <= 910 && e.getY() >= 35 && e.getY() <= 97){
					String comment = popUp.showInputDialog(Panel.this, "Please input the comment to display for Voxel " + VoxEditing + ".\nLeave blank to remove the comment", XData.getComment(VoxEditing));
					if(comment != null) XData.setComment(comment, VoxEditing);
				}
			}else if(Mode == UV){

				if(e.getX() >= 301 && e.getX() <= 620 && e.getY() >= 36 && e.getY() <= 355){

					if(e.getButton() == 1){ //Left Click

						double rawx = ((double)(e.getX()-301))/20;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-36))/20;
						int grdy = (int)Math.floor(rawy);

						if(FaceEditing == NORTH){
							NorthData.setXOrigin(grdx, VoxEditing);
							NorthData.setYOrigin(grdy, VoxEditing);
						}else if(FaceEditing == SOUTH){
							SouthData.setXOrigin(grdx, VoxEditing);
							SouthData.setYOrigin(grdy, VoxEditing);
						}else if(FaceEditing == EAST){
							EastData.setXOrigin(grdx, VoxEditing);
							EastData.setYOrigin(grdy, VoxEditing);
						}else if(FaceEditing == WEST){
							WestData.setXOrigin(grdx, VoxEditing);
							WestData.setYOrigin(grdy, VoxEditing);
						}else if(FaceEditing == TOP){
							TopData.setXOrigin(grdx, VoxEditing);
							TopData.setYOrigin(grdy, VoxEditing);
						}else if(FaceEditing == BOTTOM){
							BottomData.setXOrigin(grdx, VoxEditing);
							BottomData.setYOrigin(grdy, VoxEditing);
						}
					}

					if(e.getButton() == 3){ //Right Click

						double rawx = ((double)(e.getX()-281))/20;
						int grdx = (int)Math.floor(rawx);

						double rawy = ((double)(e.getY()-16))/20;
						int grdy = (int)Math.floor(rawy);

						if(FaceEditing == NORTH){
							NorthData.setXBase(grdx, VoxEditing);
							NorthData.setYBase(grdy, VoxEditing);
						}else if(FaceEditing == SOUTH){
							SouthData.setXBase(grdx, VoxEditing);
							SouthData.setYBase(grdy, VoxEditing);
						}else if(FaceEditing == EAST){
							EastData.setXBase(grdx, VoxEditing);
							EastData.setYBase(grdy, VoxEditing);
						}else if(FaceEditing == WEST){
							WestData.setXBase(grdx, VoxEditing);
							WestData.setYBase(grdy, VoxEditing);
						}else if(FaceEditing == TOP){
							TopData.setXBase(grdx, VoxEditing);
							TopData.setYBase(grdy, VoxEditing);
						}else if(FaceEditing == BOTTOM){
							BottomData.setXBase(grdx, VoxEditing);
							BottomData.setYBase(grdy, VoxEditing);
						}
					}

					if(e.getButton() == 2){ //Middle Click
						if(FaceEditing == NORTH){
							NorthData.setXOrigin(0, VoxEditing);
							NorthData.setYOrigin(0, VoxEditing);
							NorthData.setXBase(0, VoxEditing);
							NorthData.setYBase(0, VoxEditing);
						}else if(FaceEditing == SOUTH){
							SouthData.setXOrigin(0, VoxEditing);
							SouthData.setYOrigin(0, VoxEditing);
							SouthData.setXBase(0, VoxEditing);
							SouthData.setYBase(0, VoxEditing);
						}else if(FaceEditing == EAST){
							EastData.setXOrigin(0, VoxEditing);
							EastData.setYOrigin(0, VoxEditing);
							EastData.setXBase(0, VoxEditing);
							EastData.setYBase(0, VoxEditing);
						}else if(FaceEditing == WEST){
							WestData.setXOrigin(0, VoxEditing);
							WestData.setYOrigin(0, VoxEditing);
							WestData.setXBase(0, VoxEditing);
							WestData.setYBase(0, VoxEditing);
						}else if(FaceEditing == TOP){
							TopData.setXOrigin(0, VoxEditing);
							TopData.setYOrigin(0, VoxEditing);
							TopData.setXBase(0, VoxEditing);
							TopData.setYBase(0, VoxEditing);
						}else{
							BottomData.setXOrigin(0, VoxEditing);
							BottomData.setYOrigin(0, VoxEditing);
							BottomData.setXBase(0, VoxEditing);
							BottomData.setYBase(0, VoxEditing);
						}
					}

				}

				if(e.getX() >= 801 && e.getX() <= 901 && e.getY() >= 36 && e.getY() <= 139){
					String texstr = "ERR";

					if(FaceEditing == NORTH){
						texstr = NorthData.getTexture();
					}else if(FaceEditing == SOUTH){
						texstr = SouthData.getTexture();
					}else if(FaceEditing == WEST){
						texstr = WestData.getTexture();
					}else if(FaceEditing == EAST){
						texstr = EastData.getTexture();
					}else if(FaceEditing == TOP){
						texstr = TopData.getTexture();
					}else if(FaceEditing == BOTTOM){
						texstr = BottomData.getTexture();
					}

					String texpath = popUp.showInputDialog(Panel.this, "Please input the path to the texture.", texstr);
					if(texpath != null){
						if(FaceEditing == NORTH){
							NorthData.setTexture(texpath);
						}else if(FaceEditing == SOUTH){
							SouthData.setTexture(texpath);
						}else if(FaceEditing == WEST){
							WestData.setTexture(texpath);
						}else if(FaceEditing == EAST){
							EastData.setTexture(texpath);
						}else if(FaceEditing == TOP){
							TopData.setTexture(texpath);
						}else if(FaceEditing == BOTTOM){
							BottomData.setTexture(texpath);
						}
					}
				}

				if(e.getX() >= 801 && e.getX() <= 901 && e.getY() >= 149 && e.getY() <= 249){
					if(FaceEditing == NORTH){
						NorthData.setCulling(!NorthData.getCulling(VoxEditing), VoxEditing);
					}else if(FaceEditing == SOUTH){
						SouthData.setCulling(!SouthData.getCulling(VoxEditing), VoxEditing);
					}else if(FaceEditing == WEST){
						WestData.setCulling(!WestData.getCulling(VoxEditing), VoxEditing);
					}else if(FaceEditing == EAST){
						EastData.setCulling(!EastData.getCulling(VoxEditing), VoxEditing);
					}else if(FaceEditing == TOP){
						TopData.setCulling(!TopData.getCulling(VoxEditing), VoxEditing);
					}else if(FaceEditing == BOTTOM){
						BottomData.setCulling(!BottomData.getCulling(VoxEditing), VoxEditing);
					}
				}

				if(e.getX() >= 627 && e.getX() <= 659 && e.getY() >= 62 && e.getY() <= 72) FaceEditing = NORTH;
				if(e.getX() >= 627 && e.getX() <= 661 && e.getY() >= 78 && e.getY() <= 85) FaceEditing = SOUTH;
				if(e.getX() >= 627 && e.getX() <= 653 && e.getY() >= 93 && e.getY() <= 102) FaceEditing = EAST;
				if(e.getX() >= 626 && e.getX() <= 661 && e.getY() >= 108 && e.getY() <= 117) FaceEditing = WEST;
				if(e.getX() >= 627 && e.getX() <= 650 && e.getY() >= 123 && e.getY() <= 135) FaceEditing = TOP;
				if(e.getX() >= 627 && e.getX() <= 668 && e.getY() >= 138 && e.getY() <= 147) FaceEditing = BOTTOM;
			}


		}

		public void mousePressed(MouseEvent e) {}

		public void mouseReleased(MouseEvent e) {}

		public void mouseEntered(MouseEvent e) {}

		public void mouseExited(MouseEvent e) {}

		public void actionPerformed(ActionEvent e) {

			if(swapEditorBtn == e.getSource()){
				if(Mode == VOXEL){
					Mode = UV;
				}else{
					Mode = VOXEL;
				}
			}else if(resetBtn == e.getSource()){
				if(VoxEditing == 0){
					XData.setOrigin(0, 0);
					YData.setOrigin(0, 0);
					ZData.setOrigin(0, 0);

					XData.setBase(16, 0);
					YData.setBase(16, 0);
					ZData.setBase(16, 0);

					NorthData.setXOrigin(0, 0);
					SouthData.setXOrigin(0, 0);
					EastData.setXOrigin(0, 0);
					WestData.setXOrigin(0, 0);
					TopData.setXOrigin(0, 0);
					BottomData.setXOrigin(0, 0);
					NorthData.setYOrigin(0, 0);
					SouthData.setYOrigin(0, 0);
					EastData.setYOrigin(0, 0);
					WestData.setYOrigin(0, 0);
					TopData.setYOrigin(0, 0);
					BottomData.setYOrigin(0, 0);

					NorthData.setXBase(16, 0);
					SouthData.setXBase(16, 0);
					EastData.setXBase(16, 0);
					WestData.setXBase(16, 0);
					TopData.setXBase(16, 0);
					BottomData.setXBase(16, 0);
					NorthData.setYBase(16, 0);
					SouthData.setYBase(16, 0);
					EastData.setYBase(16, 0);
					WestData.setYBase(16, 0);
					TopData.setYBase(16, 0);
					BottomData.setYBase(16, 0);

					NorthData.setCulling(true, 0);
					SouthData.setCulling(true, 0);
					EastData.setCulling(true, 0);
					WestData.setCulling(true, 0);
					TopData.setCulling(true, 0);
					BottomData.setCulling(true, 0);

				}else{
					XData.setOrigin(0, VoxEditing);
					YData.setOrigin(0, VoxEditing);
					ZData.setOrigin(0, VoxEditing);

					XData.setBase(0, VoxEditing);
					YData.setBase(0, VoxEditing);
					ZData.setBase(0, VoxEditing);

					NorthData.setXOrigin(0, VoxEditing);
					SouthData.setXOrigin(0, VoxEditing);
					EastData.setXOrigin(0, VoxEditing);
					WestData.setXOrigin(0, VoxEditing);
					TopData.setXOrigin(0, VoxEditing);
					BottomData.setXOrigin(0, VoxEditing);
					NorthData.setYOrigin(0, VoxEditing);
					SouthData.setYOrigin(0, VoxEditing);
					EastData.setYOrigin(0, VoxEditing);
					WestData.setYOrigin(0, VoxEditing);
					TopData.setYOrigin(0, VoxEditing);
					BottomData.setYOrigin(0, VoxEditing);

					NorthData.setXBase(0, VoxEditing);
					SouthData.setXBase(0, VoxEditing);
					EastData.setXBase(0, VoxEditing);
					WestData.setXBase(0, VoxEditing);
					TopData.setXBase(0, VoxEditing);
					BottomData.setXBase(0, VoxEditing);
					NorthData.setYBase(0, VoxEditing);
					SouthData.setYBase(0, VoxEditing);
					EastData.setYBase(0, VoxEditing);
					WestData.setYBase(0, VoxEditing);
					TopData.setYBase(0, VoxEditing);
					BottomData.setYBase(0, VoxEditing);

					NorthData.setCulling(false, VoxEditing);
					SouthData.setCulling(false, VoxEditing);
					EastData.setCulling(false, VoxEditing);
					WestData.setCulling(false, VoxEditing);
					TopData.setCulling(false, VoxEditing);
					BottomData.setCulling(false, VoxEditing);
				}
			}else if(previewBtn == e.getSource()){

				JFrame pFrame = new JFrame("Model Preview");
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pFrame.setResizable(false);

				preview = new JTextArea(PrepareExport.prepareWriteData(XData, YData, ZData, NorthData, SouthData, WestData, EastData, TopData, BottomData, OptionData), 35, 80);
				JScrollPane scrollpane = new JScrollPane(preview);
				preview.setEditable(false);
				preview.setTabSize(3 );
				scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

				pFrame.add(scrollpane);

				pFrame.pack();
				pFrame.setVisible(true);
			}else if(exportBtn == e.getSource()){
				int option = popUp.showConfirmDialog(Panel.this, "I shouldnt be here! How did you press the Export button?","HEY!", JOptionPane.OK_CANCEL_OPTION, 2, icon);
			}

		}
	}
}
