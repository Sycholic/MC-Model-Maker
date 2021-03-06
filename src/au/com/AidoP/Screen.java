package au.com.AidoP;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.json.simple.parser.ParseException;

import au.com.AidoP.Display.Window;
import au.com.AidoP.Export.Prepare;
import au.com.AidoP.Export.Writer;
import au.com.AidoP.Import.Importer;
import au.com.AidoP.UV.Back;
import au.com.AidoP.UV.Bottom;
import au.com.AidoP.UV.Front;
import au.com.AidoP.UV.Left;
import au.com.AidoP.UV.Right;
import au.com.AidoP.UV.Texture;
import au.com.AidoP.UV.TextureIndex;
import au.com.AidoP.UV.Top;
import au.com.AidoP.Voxels.X;
import au.com.AidoP.Voxels.Y;
import au.com.AidoP.Voxels.Z;
import au.com.AidoP.Other.OptionData;

import com.apple.eawt.*;

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
		else if(i==1)splash = "Shaken, not stired";
		else if(i==2)splash = "This. Sentence. Is. False!";
		else if(i==3)splash = "Don't make lemonade!";
		else if(i==4)splash = "Auf Wiedersehen!";
		else if(i==5)splash = "With the low price of $99!";
		else if(i==6)splash = "Made with love!";
		else if(i==7)splash = "10010111010010101010010101";
		else if(i==8)splash = "Now with 100% more Voxels!*";
		else if(i==9)splash = "0 is the new 1";
		else if(i==10)splash = "Now you see me, now you don't!";
		else splash = "I'm a bug! Squish me!";
		
		//Special splashes:
		Date date = new Date();
		SimpleDateFormat justDays = new SimpleDateFormat("MM/dd");
		SimpleDateFormat timeHours = new SimpleDateFormat("HH");
		if(timeHours.format(date).equals("00")) splash = "Don't sleep!";
		
		if(justDays.format(date).equals("12/25")) splash = "Happy Christmas!";
		else if(justDays.format(date).equals("00/00")) splash = "Happy new year!";
		
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
	TextureIndex Textures = new TextureIndex(); 

	//End init of Voxels and UV Data--

	//Initialise Other Values--
	int redValueTop;
	int greenValueTop;
	int blueValueTop;
	int alphaValueTop;

	int redValueBottom;
	int greenValueBottom;
	int blueValueBottom;
	int alphaValueBottom;
	
	String Framerate; 

	long lastLoopTime = System.nanoTime();
	final int TARGET_FPS = 60;
	final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;   
	int fps;
	int lastFpsTime;

	Prepare PrepareExport = new Prepare();
	Writer writer = new Writer();

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
	String framerateOptions[] = { "Unlimited", "Limited" };


	//End init Other Values--


	//Initialise GUI Variables--

	JButton swapEditorBtn;
	JButton resetBtn;
	JButton previewBtn;
	JButton exportBtn;
	JButton importBtn;
	JButton View3DBtn;

	JComboBox swapVoxelBox;
	JComboBox framerateOptionsBox;

	JSlider redSliderTop;
	JSlider greenSliderTop;
	JSlider blueSliderTop;
	JSlider alphaSliderTop;
	JSlider redSliderBottom;
	JSlider greenSliderBottom;
	JSlider blueSliderBottom;
	JSlider alphaSliderBottom;
	JButton saveSettings;

	JFileChooser FileChooser;

	JTextArea preview;
	JOptionPane popUp;

	FileNameExtensionFilter JSONFilter = new FileNameExtensionFilter("Json Files (*.json)", "json");
	FileNameExtensionFilter PNGFilter = new FileNameExtensionFilter("PNG Image Files (*.png)", "png");
	FileNameExtensionFilter RESPACKFilter = new FileNameExtensionFilter("Minecraft Resource Pack", "as");

	//End init GUI Variables--


	public Panel(){ //For GUI Initilisation.

		FileChooser = new JFileChooser();
		FileChooser.setAcceptAllFileFilterUsed(false);

		importBtn = new JButton("Import");
		importBtn.setToolTipText("Import an existing JSON File");
		add(importBtn);

		exportBtn = new JButton("Export");
		exportBtn.setToolTipText("Export your model to a JSON File.");
		add(exportBtn);

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

		View3DBtn = new JButton("3D Preview");
		View3DBtn.setToolTipText("Open the 3D Viewer");
		add(View3DBtn);

		//Event Handling
		EventHandler h = new EventHandler();
		swapEditorBtn.addActionListener(h);
		resetBtn.addActionListener(h);
		previewBtn.addActionListener(h);
		exportBtn.addActionListener(h);
		importBtn.addActionListener(h);
		View3DBtn.addActionListener(h);

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

		//Textures
		Textures.setTextureAtIndex(new Texture("blocks/crafting_table_front", getClass().getResource("/res/crafting_table_front.png").getPath() ), 0);
		Textures.setFriendlyNameForIndex("TableFront", 0);
		Textures.setTextureAtIndex(new Texture("blocks/crafting_table_side", getClass().getResource("/res/crafting_table_side.png").getPath()), 1);
		Textures.setFriendlyNameForIndex("TableSide", 1);
		Textures.setTextureAtIndex(new Texture("blocks/crafting_table_top", getClass().getResource("/res/crafting_table_top.png").getPath()), 2);
		Textures.setFriendlyNameForIndex("TableTop", 2);
		Textures.setTextureAtIndex(new Texture("blocks/planks_oak", getClass().getResource("/res/planks_oak.png").getPath()), 3);
		Textures.setFriendlyNameForIndex("TableBottom", 3);

		//Basic cube finished setting up

		//Get settings
		Importer importer = new Importer();
		try {
			redValueTop = importer.getRedEditing();
			greenValueTop = importer.getGreenEditing();
			blueValueTop = importer.getBlueEditing();
			alphaValueTop = importer.getAlphaEditing();
			redValueBottom = importer.getRedBack();
			greenValueBottom = importer.getGreenBack();
			blueValueBottom = importer.getBlueBack();
			alphaValueBottom = importer.getAlphaBack();
			Framerate = importer.getFramerate();
		} catch (Exception e) {
			redValueTop = 0;
			greenValueTop = 255;
			blueValueTop = 0;
			alphaValueTop = 255;
			redValueBottom = 255;
			greenValueBottom = 255;
			blueValueBottom = 0;
			alphaValueBottom = 255;
			Framerate = "Unlimited";
			e.printStackTrace();
		}
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
		g.drawString("*Random Offset X", 5, 250);
		if(OptionData.getRandOffsetY()) g.setColor(Color.GRAY);
		else g.setColor(Color.BLACK);
		g.drawString("*Random Offset Y", 5, 265);
		if(OptionData.getRandOffsetZ()) g.setColor(Color.GRAY);
		else g.setColor(Color.BLACK);
		g.drawString("*Random Offset Z", 5, 280);

		g.setColor(Color.BLACK);
		g.drawString("Change particle texture", 5, 310);

		g.drawString("For help click here or visit http://mcmodelmaker.weebly.com", 373, 555);
		g.drawString("Click here to change your settings.", 445, 575);

		if(Mode == VOXEL){
			g.setColor(Color.BLUE);
			g.drawString("Voxel Editor", 5, 20);

			Image VoxGrid = toolkit.getImage(getClass().getResource("/res/VoxelGrid.png"));
			Image VoxBGrid = toolkit.getImage(getClass().getResource("/res/VoxelBackGrid.png"));

			g.drawImage(VoxBGrid, 0, 0, null);

			//Draw cube faces

			if(VoxEditing >= 1){
				g.setColor(new Color(redValueBottom, greenValueBottom, blueValueBottom, alphaValueBottom));
				g.fillRect(XData.getOrigin(VoxEditing-1) * 10 + 341, ZData.getOrigin(VoxEditing-1) * 10 + 75,  (XData.getBase(VoxEditing-1) * 10 + 341) - (XData.getOrigin(VoxEditing-1) * 10 + 341), (ZData.getBase(VoxEditing-1) * 10 + 341) - (ZData.getOrigin(VoxEditing-1) * 10 + 341));
				g.fillRect(XData.getOrigin(VoxEditing-1) * 10 + 341, YData.getOrigin(VoxEditing-1) * 10 + 334,  (XData.getBase(VoxEditing-1) * 10 + 341) - (XData.getOrigin(VoxEditing-1) * 10 + 341), (YData.getBase(VoxEditing-1) * 10 + 341) - (YData.getOrigin(VoxEditing-1) * 10 + 341));
				g.fillRect(ZData.getOrigin(VoxEditing-1) * 10 + 601, YData.getOrigin(VoxEditing-1) * 10 + 334,  (ZData.getBase(VoxEditing-1) * 10 + 341) - (ZData.getOrigin(VoxEditing-1) * 10 + 341), (YData.getBase(VoxEditing-1) * 10 + 601) - (YData.getOrigin(VoxEditing-1) * 10 + 601));

			}

			g.setColor(new Color(redValueTop, greenValueTop, blueValueTop, alphaValueTop));
			g.fillRect(XData.getOrigin(VoxEditing) * 10 + 341, ZData.getOrigin(VoxEditing) * 10 + 75,  (XData.getBase(VoxEditing) * 10 + 341) - (XData.getOrigin(VoxEditing) * 10 + 341), (ZData.getBase(VoxEditing) * 10 + 341) - (ZData.getOrigin(VoxEditing) * 10 + 341));
			g.fillRect(XData.getOrigin(VoxEditing) * 10 + 341, YData.getOrigin(VoxEditing) * 10 + 334,  (XData.getBase(VoxEditing) * 10 + 341) - (XData.getOrigin(VoxEditing) * 10 + 341), (YData.getBase(VoxEditing) * 10 + 341) - (YData.getOrigin(VoxEditing) * 10 + 341));
			g.fillRect(ZData.getOrigin(VoxEditing) * 10 + 601, YData.getOrigin(VoxEditing) * 10 + 334,  (ZData.getBase(VoxEditing) * 10 + 341) - (ZData.getOrigin(VoxEditing) * 10 + 341), (YData.getBase(VoxEditing) * 10 + 601) - (YData.getOrigin(VoxEditing) * 10 + 601));


			g.drawImage(VoxGrid, 0, 0, null);

			Image comment = toolkit.getImage(getClass().getResource("/res/comment.png"));
			g.drawImage(comment, 800, 35, 110, 40, null);

			g.setColor(Color.BLACK);
			g.drawString("Add Comment", 810, 95);

			if(XData.getForceExport(VoxEditing)) g.setColor(Color.GRAY);
			else g.setColor(Color.BLACK);
			g.drawString("Force-Voxel", 810, 125);

		}else if(Mode == UV){
			g.setColor(Color.BLUE);
			g.drawString("UV Editor", 5, 20);

			Image UVBG = null;
			try{
				if(Textures.getTexturePath(NorthData.getTextureIndex(VoxEditing)) != null) if(FaceEditing == NORTH) UVBG = toolkit.getImage(Textures.getTexturePath(NorthData.getTextureIndex(VoxEditing)));
				if(Textures.getTexturePath(SouthData.getTextureIndex(VoxEditing)) != null)  if(FaceEditing == SOUTH) UVBG = toolkit.getImage(Textures.getTexturePath(SouthData.getTextureIndex(VoxEditing)));
				if(Textures.getTexturePath(EastData.getTextureIndex(VoxEditing)) != null)  if(FaceEditing == EAST) UVBG = toolkit.getImage(Textures.getTexturePath(EastData.getTextureIndex(VoxEditing)));
				if(Textures.getTexturePath(WestData.getTextureIndex(VoxEditing)) != null)  if(FaceEditing == WEST) UVBG = toolkit.getImage(Textures.getTexturePath(WestData.getTextureIndex(VoxEditing)));
				if(Textures.getTexturePath(TopData.getTextureIndex(VoxEditing)) != null)  if(FaceEditing == TOP) UVBG = toolkit.getImage(Textures.getTexturePath(TopData.getTextureIndex(VoxEditing)));
				if(Textures.getTexturePath(BottomData.getTextureIndex(VoxEditing)) != null)  if(FaceEditing == BOTTOM) UVBG = toolkit.getImage(Textures.getTexturePath(BottomData.getTextureIndex(VoxEditing)));
			}catch(NullPointerException e){

			}


			if(UVBG != null && UVBG.getWidth(null) == 16 && UVBG.getHeight(null) == 16) g.drawImage(UVBG, 301, 36, 320, 320, null);


			g.setColor(new Color(redValueTop, greenValueTop, blueValueTop, alphaValueTop));
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
			Image ArrowUp = toolkit.getImage(getClass().getResource("/res/arrowUp.png"));
			g.drawImage(ArrowUp, 902, 36, 50, 50, null);
			Image ArrowDown = toolkit.getImage(getClass().getResource("/res/arrowDown.png"));
			g.drawImage(ArrowDown, 902, 86, 50, 50, null);

			g.setColor(Color.BLACK);
			String indexStr = "0";
			if(FaceEditing == NORTH)indexStr = "" + NorthData.getTextureIndex(VoxEditing);
			if(FaceEditing == SOUTH)indexStr = "" + SouthData.getTextureIndex(VoxEditing);
			if(FaceEditing == EAST)indexStr = "" + EastData.getTextureIndex(VoxEditing);
			if(FaceEditing == WEST)indexStr = "" + WestData.getTextureIndex(VoxEditing);
			if(FaceEditing == TOP)indexStr = "" + TopData.getTextureIndex(VoxEditing);
			if(FaceEditing == BOTTOM)indexStr = "" + BottomData.getTextureIndex(VoxEditing);

			g.drawString(indexStr, 922, 92);

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

			Image North = null, South = null, East = null, West = null, Top = null, Down = null;
			if(!NorthData.getExport(VoxEditing)) North = toolkit.getImage(getClass().getResource("/res/smallTick.png"));
			else North = toolkit.getImage(getClass().getResource("/res/smallCross.png"));
			g.drawImage(North, 680, 55, null);
			if(!SouthData.getExport(VoxEditing)) South = toolkit.getImage(getClass().getResource("/res/smallTick.png"));
			else South = toolkit.getImage(getClass().getResource("/res/smallCross.png"));
			g.drawImage(South, 680, 70, null);
			if(!EastData.getExport(VoxEditing)) East = toolkit.getImage(getClass().getResource("/res/smallTick.png"));
			else East = toolkit.getImage(getClass().getResource("/res/smallCross.png"));
			g.drawImage(East, 680, 85, null);
			if(!WestData.getExport(VoxEditing)) West = toolkit.getImage(getClass().getResource("/res/smallTick.png"));
			else West = toolkit.getImage(getClass().getResource("/res/smallCross.png"));
			g.drawImage(West, 680, 100, null);
			if(!TopData.getExport(VoxEditing)) Top = toolkit.getImage(getClass().getResource("/res/smallTick.png"));
			else Top = toolkit.getImage(getClass().getResource("/res/smallCross.png"));
			g.drawImage(Top, 680, 115, null);
			if(!BottomData.getExport(VoxEditing)) Down = toolkit.getImage(getClass().getResource("/res/smallTick.png"));
			else Down = toolkit.getImage(getClass().getResource("/res/smallCross.png"));
			g.drawImage(Down, 680, 130, null);


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

		if(Framerate.trim().equals("Limited")){

			try{Thread.sleep(1000/60);}
			catch(Exception e){}

			long now = System.nanoTime();
			long updateLength = now - lastLoopTime;
			lastLoopTime = now;
			lastFpsTime += updateLength;
			fps++;
			if (lastFpsTime >= 1000000000)
			{
				System.out.println("(FPS: "+fps+")");
				lastFpsTime = 0;
				fps = 0;
			}
		}

		repaint();
	}

	private class EventHandler implements ActionListener, MouseListener, MouseMotionListener{

		public void mouseDragged(MouseEvent e) {}

		public void mouseMoved(MouseEvent e) {mouseX = e.getX(); mouseY = e.getY();}

		public void mousePressed(MouseEvent e) {

			if(e.getX() >= 371 && e.getY() >= 545 && e.getX() <= 752 && e.getY() <= 560){
				try 
				{
					Desktop.getDesktop().browse(new URL("http://mcmodelmaker.weebly.com").toURI());
				}           
				catch (Exception ex) {}
			}
			if(e.getX() >= 445 && e.getY() >= 565 && e.getX() <= 665 && e.getY() <= 580){
				final JFrame opFrame = new JFrame("MC Model Maker - Settings");
				opFrame.setAlwaysOnTop(true);
				opFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				opFrame.setVisible(true);

				opFrame.setLayout(new GridLayout(0,2));

				JLabel framerateLabel = new JLabel("Framerate:");
				opFrame.add(framerateLabel);
				framerateOptionsBox = new JComboBox(framerateOptions);
				framerateOptionsBox.setToolTipText("The framerate you want MC Model Maker to run at.");
				if(Framerate.trim().equals("Limited"))framerateOptionsBox.setSelectedIndex(1);
				else framerateOptionsBox.setSelectedIndex(0);
				framerateOptionsBox.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						Framerate = framerateOptionsBox.getSelectedItem().toString();

					}
				});
				opFrame.add(framerateOptionsBox);

				JLabel blankLabel = new JLabel("");
				opFrame.add(blankLabel);
				JLabel coloursLabel = new JLabel("Colours:");
				opFrame.add(coloursLabel);


				JLabel topRedLabel = new JLabel("Red Value of Editing Colour:");
				opFrame.add(topRedLabel);

				redSliderTop = new JSlider(0, 255, redValueTop);
				redSliderTop.setToolTipText("Red value of the editing coulour.");
				redSliderTop.setPreferredSize(new Dimension(500, 45));
				redSliderTop.setMajorTickSpacing(15);
				redSliderTop.setMinorTickSpacing(5);
				redSliderTop.setPaintTicks(true);
				redSliderTop.setPaintLabels(true);
				redSliderTop.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						redValueTop = redSliderTop.getValue();
					}
				});
				opFrame.add(redSliderTop);

				JLabel topGreenLabel = new JLabel("Green Value of Editing Colour:");
				opFrame.add(topGreenLabel);

				greenSliderTop = new JSlider(0, 255, greenValueTop);
				greenSliderTop.setToolTipText("Green value of the editing coulour.");
				greenSliderTop.setPreferredSize(new Dimension(500, 45));
				greenSliderTop.setMajorTickSpacing(15);
				greenSliderTop.setMinorTickSpacing(5);
				greenSliderTop.setPaintTicks(true);
				greenSliderTop.setPaintLabels(true);
				greenSliderTop.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						greenValueTop = greenSliderTop.getValue();
					}
				});
				opFrame.add(greenSliderTop);

				JLabel topBlueLabel = new JLabel("Blue Value of Editing Colour:");
				opFrame.add(topBlueLabel);

				blueSliderTop = new JSlider(0, 255, blueValueTop);
				blueSliderTop.setToolTipText("Blue value of the editing coulour.");
				blueSliderTop.setPreferredSize(new Dimension(500, 45));
				blueSliderTop.setMajorTickSpacing(15);
				blueSliderTop.setMinorTickSpacing(5);
				blueSliderTop.setPaintTicks(true);
				blueSliderTop.setPaintLabels(true);
				blueSliderTop.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						blueValueTop = blueSliderTop.getValue();
					}
				});
				opFrame.add(blueSliderTop);
				
				JLabel topAlphaLabel = new JLabel("Alpha Value of Editing Colour:");
				opFrame.add(topAlphaLabel);

				alphaSliderTop = new JSlider(0, 255, alphaValueTop);
				alphaSliderTop.setToolTipText("Alpha value of the editing coulour.");
				alphaSliderTop.setPreferredSize(new Dimension(500, 45));
				alphaSliderTop.setMajorTickSpacing(15);
				alphaSliderTop.setMinorTickSpacing(5);
				alphaSliderTop.setPaintTicks(true);
				alphaSliderTop.setPaintLabels(true);
				alphaSliderTop.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						alphaValueTop = alphaSliderTop.getValue();
					}
				});
				opFrame.add(alphaSliderTop);

				JLabel botRedLabel = new JLabel("Red Value of Background Voxel Colour:");
				opFrame.add(botRedLabel);

				redSliderBottom = new JSlider(0, 255, redValueBottom);
				redSliderBottom.setToolTipText("Red value of the background voxel coulour.");
				redSliderBottom.setPreferredSize(new Dimension(500, 45));
				redSliderBottom.setMajorTickSpacing(15);
				redSliderBottom.setMinorTickSpacing(5);
				redSliderBottom.setPaintTicks(true);
				redSliderBottom.setPaintLabels(true);
				redSliderBottom.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						redValueBottom = redSliderBottom.getValue();
					}
				});
				opFrame.add(redSliderBottom);

				JLabel botGreenLabel = new JLabel("Green Value of Background Voxel Colour:");
				opFrame.add(botGreenLabel);

				greenSliderBottom = new JSlider(0, 255, greenValueBottom);
				greenSliderBottom.setToolTipText("Green value of the background voxel coulour.");
				greenSliderBottom.setPreferredSize(new Dimension(500, 45));
				greenSliderBottom.setMajorTickSpacing(15);
				greenSliderBottom.setMinorTickSpacing(5);
				greenSliderBottom.setPaintTicks(true);
				greenSliderBottom.setPaintLabels(true);
				greenSliderBottom.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						greenValueBottom = greenSliderBottom.getValue();
					}
				});
				opFrame.add(greenSliderBottom);

				JLabel botBlueLabel = new JLabel("Blue Value of Background Voxel Colour:");
				opFrame.add(botBlueLabel);

				blueSliderBottom = new JSlider(0, 255, blueValueBottom);
				blueSliderBottom.setToolTipText("Blue value of the background voxel coulour.");
				blueSliderBottom.setPreferredSize(new Dimension(500, 45));
				blueSliderBottom.setMajorTickSpacing(15);
				blueSliderBottom.setMinorTickSpacing(5);
				blueSliderBottom.setPaintTicks(true);
				blueSliderBottom.setPaintLabels(true);
				blueSliderBottom.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						blueValueBottom = blueSliderBottom.getValue();
					}
				});
				opFrame.add(blueSliderBottom);

				JLabel botAlphaLabel = new JLabel("Alpha Value of Background Voxel Colour:");
				opFrame.add(botAlphaLabel);

				alphaSliderBottom = new JSlider(0, 255, alphaValueBottom);
				alphaSliderBottom.setToolTipText("Alpha value of the background voxel coulour.");
				alphaSliderBottom.setPreferredSize(new Dimension(500, 45));
				alphaSliderBottom.setMajorTickSpacing(15);
				alphaSliderBottom.setMinorTickSpacing(5);
				alphaSliderBottom.setPaintTicks(true);
				alphaSliderBottom.setPaintLabels(true);
				alphaSliderBottom.addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e) {
						alphaValueBottom = alphaSliderBottom.getValue();
					}
				});
				opFrame.add(alphaSliderBottom);

				saveSettings = new JButton("Apply Settings");
				saveSettings.setToolTipText("Save the settings so that they are the default on launch.");
				saveSettings.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent event) {
						File f = new File(".");
						File dir = f.getAbsoluteFile().getParentFile();
						String path = dir.toString() + "/Settings.json";
						try {
							Formatter writer = new Formatter(new File(path));

							writer.format("{\n\t" + 
									"\"EditingColourRed\": " + redValueTop + ",\n\t" +
									"\"EditingColourGreen\": " + greenValueTop + ",\n\t" +
									"\"EditingColourBlue\": " + blueValueTop + ",\n\t" +
									"\"EditingAlpha\": " + alphaValueTop + ",\n\t" +
									"\"ColourRed\": " + redValueBottom + ",\n\t" +
									"\"ColourGreen\": " + greenValueBottom + ",\n\t" +
									"\"ColourBlue\": " + blueValueBottom + ",\n\t" +
									"\"Alpha\": " + alphaValueBottom + ",\n\t" +
									"\"Framerate\": \"" + Framerate + "\",\n}");

							writer.close();
						} catch (FileNotFoundException e) {
							popUp.showMessageDialog(Panel.this, "Error saving settings!");
							e.printStackTrace();
						}
						
						opFrame.dispose();
					}
				});
				opFrame.add(saveSettings);

				opFrame.pack();
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

				if(e.getX() >= 812 && e.getX() <= 886 && e.getY() >= 117 && e.getY() <= 126){
					XData.setForceExport(!XData.getForceExport(VoxEditing), VoxEditing);
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
					String texstr = " ";

					String indexStr = (String) popUp.showInputDialog(Panel.this, "What texture index do you want to edit?", "Attention!", JOptionPane.INFORMATION_MESSAGE, null, listOptions, listOptions[0]);;
					int index = Integer.parseInt(indexStr);

					if(!Textures.getIndexNull(index)){
						texstr = Textures.getTextureName(index);
					}
					String friendlyName = popUp.showInputDialog(Panel.this, "Input the name of the texture", "Texture" + index);
					Textures.setFriendlyNameForIndex(friendlyName, index);

					String texpath = popUp.showInputDialog(Panel.this, "Please input the path to the texture.", texstr);

					FileChooser.addChoosableFileFilter(PNGFilter);
					FileChooser.showOpenDialog(Panel.this);

					if(texpath != null && texpath != ""){
						Textures.setTextureAtIndex(new Texture(texpath, FileChooser.getSelectedFile().getPath()), index);
					}else{
						Textures.setTextureAtIndex(new Texture(texstr, FileChooser.getSelectedFile().getPath()), index);
					}

					FileChooser.removeChoosableFileFilter(PNGFilter);

					if(FaceEditing == NORTH){
						NorthData.setTextureIndex(index, VoxEditing);
					}else if(FaceEditing == SOUTH){
						SouthData.setTextureIndex(index, VoxEditing);
					}else if(FaceEditing == EAST){
						EastData.setTextureIndex(index, VoxEditing);
					}else if(FaceEditing == WEST){
						WestData.setTextureIndex(index, VoxEditing);
					}else if(FaceEditing == TOP){
						TopData.setTextureIndex(index, VoxEditing);
					}else if(FaceEditing == BOTTOM){
						BottomData.setTextureIndex(index, VoxEditing);
					}

				}else if(e.getX() >= 902 && e.getX() <= 952 && e.getY() >= 36 && e.getY() <= 85){
					if(FaceEditing == NORTH){
						if(NorthData.getTextureIndex(VoxEditing) < 99) NorthData.setTextureIndex(NorthData.getTextureIndex(VoxEditing) + 1, VoxEditing);
					}else if(FaceEditing == SOUTH){
						if(SouthData.getTextureIndex(VoxEditing) < 99) SouthData.setTextureIndex(SouthData.getTextureIndex(VoxEditing) + 1, VoxEditing);
					}else if(FaceEditing == EAST){
						if(EastData.getTextureIndex(VoxEditing) < 99) EastData.setTextureIndex(EastData.getTextureIndex(VoxEditing) + 1, VoxEditing);
					}else if(FaceEditing == WEST){
						if(WestData.getTextureIndex(VoxEditing) < 99) WestData.setTextureIndex(WestData.getTextureIndex(VoxEditing) + 1, VoxEditing);
					}else if(FaceEditing == TOP){
						if(TopData.getTextureIndex(VoxEditing) < 99) TopData.setTextureIndex(TopData.getTextureIndex(VoxEditing) + 1, VoxEditing);
					}else if(FaceEditing == BOTTOM){
						if(BottomData.getTextureIndex(VoxEditing) < 99) BottomData.setTextureIndex(BottomData.getTextureIndex(VoxEditing) + 1, VoxEditing);
					}
				}else if(e.getX() >= 902 && e.getX() <= 952 && e.getY() >= 86 && e.getY() <= 136){
					if(FaceEditing == NORTH){
						if(NorthData.getTextureIndex(VoxEditing) > 0) NorthData.setTextureIndex(NorthData.getTextureIndex(VoxEditing) - 1, VoxEditing);
					}else if(FaceEditing == SOUTH){
						if(SouthData.getTextureIndex(VoxEditing) > 0) SouthData.setTextureIndex(SouthData.getTextureIndex(VoxEditing) - 1, VoxEditing);
					}else if(FaceEditing == EAST){
						if(EastData.getTextureIndex(VoxEditing) > 0) EastData.setTextureIndex(EastData.getTextureIndex(VoxEditing) - 1, VoxEditing);
					}else if(FaceEditing == WEST){
						if(WestData.getTextureIndex(VoxEditing) > 0) WestData.setTextureIndex(WestData.getTextureIndex(VoxEditing) - 1, VoxEditing);
					}else if(FaceEditing == TOP){
						if(TopData.getTextureIndex(VoxEditing) > 0) TopData.setTextureIndex(TopData.getTextureIndex(VoxEditing) - 1, VoxEditing);
					}else if(FaceEditing == BOTTOM){
						if(BottomData.getTextureIndex(VoxEditing) > 0) BottomData.setTextureIndex(BottomData.getTextureIndex(VoxEditing) - 1, VoxEditing);
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

				if(e.getX() >= 680 && e.getX() <= 698 && e.getY() >= 55 && e.getY() <= 71) NorthData.setExport(!NorthData.getExport(VoxEditing), VoxEditing);
				if(e.getX() >= 680 && e.getX() <= 698 && e.getY() >= 72 && e.getY() <= 87) SouthData.setExport(!SouthData.getExport(VoxEditing), VoxEditing);
				if(e.getX() >= 680 && e.getX() <= 698 && e.getY() >= 88 && e.getY() <= 102) EastData.setExport(!EastData.getExport(VoxEditing), VoxEditing);
				if(e.getX() >= 680 && e.getX() <= 698 && e.getY() >= 103 && e.getY() <= 118) WestData.setExport(!WestData.getExport(VoxEditing), VoxEditing);
				if(e.getX() >= 680 && e.getX() <= 698 && e.getY() >= 119 && e.getY() <= 132) TopData.setExport(!TopData.getExport(VoxEditing), VoxEditing);
				if(e.getX() >= 680 && e.getX() <= 698 && e.getY() >= 133 && e.getY() <= 147) BottomData.setExport(!BottomData.getExport(VoxEditing), VoxEditing);
			}


		}

		public void mouseClicked(MouseEvent e) {}

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
			}else if(View3DBtn == e.getSource()){

				String[] exportOptions = {"Large", "Small"};

				int zoomSize = popUp.showOptionDialog(Panel.this, "How big is your model?", "Attention!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, exportOptions, "Large");

				float zoom = 0.0f;

				switch(zoomSize){
				case 0: zoom = 10.0f;
				break;
				case 1: zoom = 1.0f;
				break;
				}

				int sure = 0;
				Properties props = System.getProperties();
				props.list(System.out);
				if(System.getProperty("java.specification.version").equals("1.7")) sure = popUp.showConfirmDialog(Panel.this, "The 3D Viewer is known to crash on Java 7, causing lost data. It is recomended that you downgrade to Java 6!\nWould you still like to continue?");
				if(sure == 0){
					Window viewer = new Window();
					viewer.run(XData, YData, ZData, zoom);
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

					NorthData.setExport(false, 0);
					SouthData.setExport(false, 0);
					EastData.setExport(false, 0);
					WestData.setExport(false, 0);
					TopData.setExport(false, 0);
					BottomData.setExport(false, 0);

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

					NorthData.setExport(false, VoxEditing);
					SouthData.setExport(false, VoxEditing);
					EastData.setExport(false, VoxEditing);
					WestData.setExport(false, VoxEditing);
					TopData.setExport(false, VoxEditing);
					BottomData.setExport(false, VoxEditing);
				}
			}else if(previewBtn == e.getSource()){

				JFrame pFrame = new JFrame("Model Preview");
				pFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				pFrame.setResizable(true);

				preview = new JTextArea(PrepareExport.prepareWriteData(XData, YData, ZData, NorthData, SouthData, WestData, EastData, TopData, BottomData, OptionData, Textures), 35, 80);
				JScrollPane scrollpane = new JScrollPane(preview);
				preview.setEditable(false);
				preview.setTabSize(3);
				scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

				pFrame.add(scrollpane);

				pFrame.pack();
				pFrame.setVisible(true);
			}else if(importBtn == e.getSource()){

				if(popUp.showConfirmDialog(Panel.this, "Are you sure you want to import a model. This will delete your current progress if it isn't saved!") == 0){
					FileChooser.addChoosableFileFilter(JSONFilter);
					int suc = FileChooser.showOpenDialog(Panel.this);

					if(suc == FileChooser.APPROVE_OPTION){

						Importer importer = new Importer(); 

						try {
							importer.startImport(XData, YData, ZData, NorthData, SouthData, WestData, EastData, TopData, BottomData, OptionData, FileChooser.getSelectedFile(), Textures);
						} catch (IOException ex) {
							popUp.showMessageDialog(Panel.this, "Import failed!\nIOException Stacktrace:\n" + ex, "Error!", JOptionPane.ERROR_MESSAGE);
						} catch (ParseException ex) {
							popUp.showMessageDialog(Panel.this, "Import failed!\nParseException (Invalid JSON File!) Stacktrace:\n" + ex, "Error!", JOptionPane.ERROR_MESSAGE);
						} catch (IndexOutOfBoundsException ex){} //Stub to remove of "Meaningless" errors  - I know I shouldn't but...
					}

					FileChooser.removeChoosableFileFilter(JSONFilter);
				}
			}

			else if(exportBtn == e.getSource()){

				String[] exportOptions = {"JSON File", "Resource Pack - Item", "Resource Pack - Block"};

				int exportType = popUp.showOptionDialog(Panel.this, "What would you like to export your model as?", "Attention!", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, exportOptions, "Resource Pack - Block");

				if(exportType == 1){ //Respack - Item

					FileChooser.setAcceptAllFileFilterUsed(true);
					int suc = FileChooser.showSaveDialog(Panel.this);
					FileChooser.setAcceptAllFileFilterUsed(false);

					if(suc == 0)
						if(writer.getExtension(FileChooser.getSelectedFile()) == null){
							writer.createResPackDir(FileChooser.getSelectedFile());

							String packDescription = (String)popUp.showInputDialog(Panel.this, "Please input the description of the resource pack!", "Sorry to disturb you!", JOptionPane.QUESTION_MESSAGE, null, null, OptionData.getPackDescription());
							String mcmetaData = "{\n\t\"pack\": {\n\t\t\"pack_format\": 1,\n\t\t\"description\": \""+ packDescription + "\"\n\t}\n}";
							File mcmetaFile = new File(FileChooser.getSelectedFile().getPath() + "/" + "pack.mcmeta");
							writer.createFile(mcmetaFile);
							writer.writeFile(mcmetaData, mcmetaFile);

							OptionData.setPackDescription(packDescription);

							String getNameSuc = (String)popUp.showInputDialog(Panel.this, "Please input the name of the item that your model is changing!", "One last thing!", JOptionPane.QUESTION_MESSAGE, null, null, OptionData.getBlockEditing());
							OptionData.setBlockEditing(getNameSuc);

							if(getNameSuc != null && getNameSuc != ""){

								File blockFile = new File(FileChooser.getSelectedFile().getPath() + "/assets/minecraft/models/item/" + getNameSuc);
								if(writer.getExtension(blockFile) == null || !writer.getExtension(blockFile).equalsIgnoreCase("json")) blockFile = new File(FileChooser.getSelectedFile().getPath() + "/assets/minecraft/models/item//" + getNameSuc + ".json");

								writer.createFile(blockFile);
								writer.writeFile(PrepareExport.prepareWriteData(XData, YData, ZData, NorthData, SouthData, WestData, EastData, TopData, BottomData, OptionData, Textures), blockFile);
							}
						}else{
							popUp.showMessageDialog(Panel.this, "Invalid extension for a Resource Pack!\nResource Packs do not need an extension!", "Attention!", 0);
						}
				}

				else if(exportType == 2){//Respack - Block

					FileChooser.setAcceptAllFileFilterUsed(true);
					int suc = FileChooser.showSaveDialog(Panel.this);
					FileChooser.setAcceptAllFileFilterUsed(false);

					if(suc == 0)
						if(writer.getExtension(FileChooser.getSelectedFile()) == null){
							writer.createResPackDir(FileChooser.getSelectedFile());

							String packDescription = (String)popUp.showInputDialog(Panel.this, "Please input the description of the resource pack!", "Sorry to disturb you!", JOptionPane.QUESTION_MESSAGE, null, null, OptionData.getPackDescription());
							String mcmetaData = "{\n\t\"pack\": {\n\t\t\"pack_format\": 1,\n\t\t\"description\": \""+ packDescription + "\"\n\t}\n}";
							File mcmetaFile = new File(FileChooser.getSelectedFile().getPath() + "/" + "pack.mcmeta");
							writer.createFile(mcmetaFile);
							writer.writeFile(mcmetaData, mcmetaFile);

							OptionData.setPackDescription(packDescription);

							String getNameSuc = (String)popUp.showInputDialog(Panel.this, "Please input the name of the block that your model is changing!", "One last thing!", JOptionPane.QUESTION_MESSAGE, null, null, OptionData.getBlockEditing());
							OptionData.setBlockEditing(getNameSuc);
							if(getNameSuc != null && getNameSuc != ""){

								File blockFile = new File(FileChooser.getSelectedFile().getPath() + "/assets/minecraft/models/block/" + getNameSuc);
								if(writer.getExtension(blockFile) == null || !writer.getExtension(blockFile).equalsIgnoreCase("json")) blockFile = new File(FileChooser.getSelectedFile().getPath() + "/assets/minecraft/models/block/" + getNameSuc + ".json");

								writer.createFile(blockFile);
								writer.writeFile(PrepareExport.prepareWriteData(XData, YData, ZData, NorthData, SouthData, WestData, EastData, TopData, BottomData, OptionData, Textures), blockFile);
							}
						}else{
							popUp.showMessageDialog(Panel.this, "Invalid extension for a Resource Pack!\nResource Packs do not need an extension!", "Attention!", 0);
						}
				}

				else if(exportType == 0){//JSON Export

					FileChooser.setAcceptAllFileFilterUsed(true);
					int suc = FileChooser.showSaveDialog(Panel.this);
					FileChooser.setAcceptAllFileFilterUsed(false);

					if(suc == 0){
						File blockFile = new File(FileChooser.getSelectedFile().getPath());
						if(writer.getExtension(blockFile) == null || !writer.getExtension(blockFile).equalsIgnoreCase("json")) blockFile = new File(FileChooser.getSelectedFile().getPath() + ".json");

						writer.createFile(blockFile);
						writer.writeFile(PrepareExport.prepareWriteData(XData, YData, ZData, NorthData, SouthData, WestData, EastData, TopData, BottomData, OptionData, Textures), blockFile);
					}
				}
			}
		}
	}
}
