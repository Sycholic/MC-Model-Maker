package au.com.AidoP.Display;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;

import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import au.com.AidoP.Voxels.X;
import au.com.AidoP.Voxels.Y;
import au.com.AidoP.Voxels.Z;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

public class Window extends JFrame{

	BoundingSphere bounds;
	ViewingPlatform view;
	OrbitBehavior behavior;
	KeyNavigatorBehavior KeyBeh;

	X XData;
	Y YData;
	Z ZData;

	public void run(X XData, Y YData, Z ZData, float ZoomSize){
		this.XData = XData;
		this.YData = YData;
		this.ZData = ZData;
		
		JFrame frame = new JFrame("3D Viewer");
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setVisible(true);
		
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas = new Canvas3D(config);
		canvas.setPreferredSize(new Dimension(1000, 600));
		frame.add(canvas);
		
		SimpleUniverse universe = new SimpleUniverse(canvas);
		frame.pack();
		
		view = universe.getViewingPlatform();
		
		BranchGroup scene = createRootBranch();
		universe.addBranchGraph(scene);
		
		view.setNominalViewingTransform();
		
		TransformGroup viewTrans = new TransformGroup();
		Transform3D zoomOut = new Transform3D();
		zoomOut.setTranslation(new Vector3f(0.0f, 0.0f, ZoomSize));
		viewTrans.setTransform(zoomOut);
		
		view.getViewPlatformTransform().setTransform(zoomOut);
	}

	private BranchGroup createRootBranch(){

		BranchGroup rootGroup = new BranchGroup();
		BranchGroup cubeGroup = new BranchGroup();
		TransformGroup transGroup = new TransformGroup();

        transGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		int Voxel = 0;
		while(Voxel <= 99){
			if((XData.getOrigin(Voxel) != XData.getBase(Voxel) && YData.getOrigin(Voxel) != YData.getBase(Voxel) && ZData.getOrigin(Voxel) != ZData.getBase(Voxel)) || XData.getForceExport(Voxel)){
				Cubes cube = new Cubes(Voxel, XData, YData, ZData);

				Material mat = new Material();
				mat.setAmbientColor(0.3f, 0.3f, 0.3f);
				mat.setDiffuseColor(0.7f, 0.7f, 0.7f);
				mat.setSpecularColor(0.7f, 0.7f, 0.7f);
				
				Appearance app = new Appearance();
				ColoringAttributes colourApp = new ColoringAttributes();
				colourApp.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
				app.setColoringAttributes(colourApp);
				app.setMaterial(mat);
				cube.setAppearance(app);

				cubeGroup.addChild(cube);
			}
			Voxel++;
		}


		bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0); 
		Color3f alColour = new Color3f(0.6f, 0.6f, 0.7f);
		AmbientLight aLgt = new AmbientLight(alColour);
		aLgt.setInfluencingBounds(bounds);

		Color3f LightColour = new Color3f(1.0f, 1.0f, 1.0f);
		Vector3f LightDirection = new Vector3f(1.0f, 10.0f, 1.0f);

		DirectionalLight DirLight = new DirectionalLight(LightColour, LightDirection);
		DirLight.setInfluencingBounds(bounds);

		rootGroup.addChild(DirLight);
		rootGroup.addChild(aLgt);
		rootGroup.addChild(cubeGroup);

		TransformGroup tg = new TransformGroup();
		
		behavior = new OrbitBehavior();
		behavior.setSchedulingBounds(bounds);
		
		KeyBeh = new KeyNavigatorBehavior(transGroup);
		KeyBeh.setSchedulingBounds(new BoundingSphere(new Point3d(), 1000.0));

		transGroup.addChild(KeyBeh);
		
		view.setViewPlatformBehavior(behavior);
		
		Background bg = new Background();
		bg.setColor(new Color3f(0.0f, 1.0f, 0.0f));
		rootGroup.addChild(bg);
		
		rootGroup.addChild(transGroup);
		rootGroup.compile();
		return rootGroup;

	}

}
