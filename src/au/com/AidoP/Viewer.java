package au.com.AidoP;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Viewer {

	public void start() {
		try {
			Display.setDisplayMode(new DisplayMode(1000,600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		
		// init OpenGL
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 1000, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		while (!Display.isCloseRequested()) {
			MyPanel s = new MyPanel();
			int VC[]=s.VC;
			int VC2[]=s.VC2;

			
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	

			
			GL11.glClearDepth(0.5f);
			GL11.glLoadIdentity(); 
			GL11.glTranslatef(1.5f, 0.0f, -7.0f);

			for(int i=0;i<=99;i++){
				if(VC[i*3]!=VC2[i*3]&&VC[i*3+1]!=VC2[i*3+1]&&VC[i*3+2]!=VC2[i*3+2]){
					GL11.glBegin(GL11.GL_QUADS);
					GL11.glColor3d(0,2.55,0);
					
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC[i*3+2]*10); //Top
					GL11.glVertex3i(VC[i*3]*10,VC[i*3+1]*10, VC[i*3+2]*10);
					GL11.glVertex3i(VC[i*3]*10,VC[i*3+1]*10, VC2[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC2[i*3+2]*10);
					
					GL11.glVertex3i(VC2[i*3]*10,VC2[i*3+1]*10, VC[i*3+2]*10); //Bottom
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC[i*3+2]*10);
					GL11.glVertex3i(VC[i*3]*10,VC2[i*3+1]*10, VC2[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC2[i*3+1]*10, VC2[i*3+2]*10);
					
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC2[i*3+2]*10); //Front
					GL11.glVertex3i(VC[i*3]*10,VC[i*3+1]*10, VC2[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC2[i*3+1]*10, VC[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC2[i*3+1]*10, VC2[i*3+2]*10);
					
					GL11.glVertex3i(VC[i*3]*10,VC[i*3+1]*10, VC[i*3+2]*10); //Back
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC2[i*3+1]*10, VC[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC2[i*3+2]*10);
					
					GL11.glVertex3i(VC[i*3]*10,VC[i*3+1]*10, VC2[i*3+2]*10); //Left
					GL11.glVertex3i(VC[i*3]*10,VC[i*3+1]*10, VC[i*3+2]*10);
					GL11.glVertex3i(VC[i*3]*10,VC2[i*3+1]*10, VC[i*3+2]*10);
					GL11.glVertex3i(VC[i*3]*10,VC2[i*3+1]*10, VC2[i*3+2]*10);
					
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC[i*3+2]*10); //Right
					GL11.glVertex3i(VC2[i*3]*10,VC[i*3+1]*10, VC2[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC2[i*3+1]*10, VC2[i*3+2]*10);
					GL11.glVertex3i(VC2[i*3]*10,VC2[i*3+1]*10, VC[i*3+2]*10);
					
					GL11.glEnd();
				}
			}
			Display.update();
		}
	}

	public static void run() {
		Viewer view = new Viewer();
		view.start();
	}
}
