package isManDown100.source;

import java.awt.*;
import javax.swing.*;
/*
 *  使用规则：
 *  Img.文件名
 *  e.g：Img.background
 */
public final class Img {
	public static final String DIR = "images/";
	public static final String BOARD_DIR = DIR+"board/";
	public static final String LIFE_DIR = DIR+"life/";
	public static final String MAN_DIR = DIR+"man/";
	public static final String NUM_DIR = DIR+"num/";
	
	public static final Image back2 = new ImageIcon(Img.class.getResource(DIR+"back2.gif")).getImage();
	public static final Image background = new ImageIcon(Img.class.getResource(DIR+"backgroud.gif")).getImage();
	public static final Image border = new ImageIcon(Img.class.getResource(DIR+"border.gif")).getImage();
	public static final Image gameover = new ImageIcon(Img.class.getResource(DIR+"gameover.gif")).getImage();
	public static final Image pause = new ImageIcon(Img.class.getResource(DIR+"pause.gif")).getImage();
	public static final Image word1 = new ImageIcon(Img.class.getResource(DIR+"word1.gif")).getImage();
	public static final Image word2 = new ImageIcon(Img.class.getResource(DIR+"word2.gif")).getImage();

	public static final Image board_a = new ImageIcon(Img.class.getResource(BOARD_DIR+"board_a.gif")).getImage();
	public static final Image[] board_b = {
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_b1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_b2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_b3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_b4.gif")).getImage()
	};
	public static final Image[] board_c = {
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_c1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_c2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_c3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_c4.gif")).getImage()
	};
	public static final Image[] board_d = {
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_d1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_d2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_d3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_d4.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_d5.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_d6.gif")).getImage()
	};
	public static final Image[] board_e = {
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_e1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_e2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_e3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_e4.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_e5.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_e6.gif")).getImage(),
		new ImageIcon(Img.class.getResource(BOARD_DIR+"board_e7.gif")).getImage()
	};
	public static final Image board_f = new ImageIcon(Img.class.getResource(BOARD_DIR+"board_f.gif")).getImage();
	public static final Image  board_top= new ImageIcon(Img.class.getResource(BOARD_DIR+"board_top.gif")).getImage();
	
	public static final Image[] life = {
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_00.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_01.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_02.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_03.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_04.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_05.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_06.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_07.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_08.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_09.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_10.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_11.gif")).getImage(),
		new ImageIcon(Img.class.getResource(LIFE_DIR+"life_12.gif")).getImage()
	};
	public static final Image lifeWord = new ImageIcon(Img.class.getResource(LIFE_DIR+"lifeword.gif")).getImage();

	public static final Image[] javaman_c = {
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_c1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_c1_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_c2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_c2_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_c3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_c3_b.gif")).getImage()
	};
	public static final Image[] javaman_l = {
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_l1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_l1_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_l2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_l2_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_l3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_l3_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_ld1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_ld1_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_ld2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_ld2_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_ld3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_ld3_b.gif")).getImage(),
	};
	public static final Image[] javaman_r = {
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_r1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_r1_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_r2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_r2_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_r3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_r3_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_rd1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_rd1_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_rd2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_rd2_b.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_rd3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_rd3_b.gif")).getImage()
	};
	public static final Image[] javaman_s = {
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_s.gif")).getImage(),
		new ImageIcon(Img.class.getResource(MAN_DIR+"javaman_s_b.gif")).getImage()
	};
	
	public static final Image[] number= {
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_0.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_1.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_2.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_3.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_4.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_5.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_6.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_7.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_8.gif")).getImage(),
		new ImageIcon(Img.class.getResource(NUM_DIR+"number_9.gif")).getImage(),
	};
	private Img(){}
	//调试图片是否正常加载
	/*
	public static void main(String args[]){
		JFrame frame = new JFrame();
		frame.setLayout(null);
		frame.add(new Img().new Show(life));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000,1000);
	}
	class Show extends JPanel{
		private Image[] imgs;
		Show(Image img){
			imgs = new Image[]{img};
			setBounds(0,0,imgs.length*imgs[0].getWidth(null),imgs.length*imgs[0].getHeight(null));
		}
		Show(Image[] img){
			imgs = img;
			setBounds(0,0,imgs.length*imgs[0].getWidth(null),imgs.length*imgs[0].getHeight(null));
		}
		@Override
		public void paintComponent(Graphics g){
			for(int i=0,j=0;i<imgs.length;i++)
			{
				if(j==4) j = 0;
				g.drawImage(imgs[i],j*(imgs[0].getWidth(null)+5),(i/4)*(imgs[0].getHeight(null)+5),imgs[0].getWidth(null),imgs[0].getHeight(null),this);
				j++;
			}
		}
	}
	*/
}

