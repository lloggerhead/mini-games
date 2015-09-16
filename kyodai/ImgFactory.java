package kyodai;

import kyodai.*;
import java.awt.*;
import javax.swing.*;

public class ImgFactory {
	public static final String DIR = "images/"; 
	public static final int SUM = 39; 
	static final Image[] images = new Image[SUM];
	static final Image[] frameImages = new Image[16];
	static {
		for(int i=0;i<images.length;i++)
			images[i] = new ImageIcon(ImgFactory.class.getResource(DIR+i+".gif")).getImage();
		frameImages[0] = new ImageIcon(ImgFactory.class.getResource(DIR+"about.gif")).getImage();
		frameImages[1] = new ImageIcon(ImgFactory.class.getResource(DIR+"bomb.gif")).getImage();
		frameImages[2] = new ImageIcon(ImgFactory.class.getResource(DIR+"cursor.gif")).getImage();
		frameImages[3] = new ImageIcon(ImgFactory.class.getResource(DIR+"demo.gif")).getImage();
		frameImages[4] = new ImageIcon(ImgFactory.class.getResource(DIR+"dots.gif")).getImage();
		frameImages[5] = new ImageIcon(ImgFactory.class.getResource(DIR+"help.gif")).getImage();
		frameImages[6] = new ImageIcon(ImgFactory.class.getResource(DIR+"hint.gif")).getImage();
		frameImages[7] = new ImageIcon(ImgFactory.class.getResource(DIR+"ico.gif")).getImage();
		frameImages[8] = new ImageIcon(ImgFactory.class.getResource(DIR+"kyodai16.gif")).getImage();
		frameImages[9] = new ImageIcon(ImgFactory.class.getResource(DIR+"me.gif")).getImage();
		frameImages[10] = new ImageIcon(ImgFactory.class.getResource(DIR+"refresh.gif")).getImage();
		frameImages[11] = new ImageIcon(ImgFactory.class.getResource(DIR+"setup.gif")).getImage();
		frameImages[12] = new ImageIcon(ImgFactory.class.getResource(DIR+"sico.gif")).getImage();
		frameImages[13] = new ImageIcon(ImgFactory.class.getResource(DIR+"splash.gif")).getImage();
		frameImages[14] = new ImageIcon(ImgFactory.class.getResource(DIR+"start.gif")).getImage();
		frameImages[15] = new ImageIcon(ImgFactory.class.getResource(DIR+"topbar.gif")).getImage();
	}
}
