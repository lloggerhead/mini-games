package isManDown100;

import isManDown100.controler.KeyDirector;
import isManDown100.controler.Manager;
import isManDown100.source.WindowInf;
import isManDown100.view.Canvas;

import javax.swing.JFrame;
/**
 * ��ΪJava app���� 
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{
	
	public MainFrame() {
		Canvas.beginInJFrame = true;
		Manager.getInstance();
		setSize(WindowInf.WINDOW_WIDTH, WindowInf.WINDOW_HEIGHT);
		setContentPane(Canvas.getInstance());
		addKeyListener(KeyDirector.getInstance());
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("�ǳ��˾���һ�ٲ�");
		getGraphics();
	}
	
	public static void main(String[] args) {
		new MainFrame();
		Manager.getInstance().beginGame();
	}
	
}
