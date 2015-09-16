package isManDown100;

import isManDown100.controler.KeyDirector;
import isManDown100.controler.Manager;
import isManDown100.source.WindowInf;
import isManDown100.view.Canvas;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class MainApplet extends JApplet{
	private static ArrayList<MainApplet> m_self = new ArrayList<MainApplet>();
	
	public MainApplet() {
		m_self.add(this);
		setSize(WindowInf.WINDOW_WIDTH, WindowInf.WINDOW_HEIGHT);
		setContentPane(Canvas.getInstance());
		setFocusable(true);
	}
	
	public static MainApplet getInstance() {
		return m_self.get(0);
	}
	
	@Override
	public void init() {
		Manager.getInstance();
	}
	
	@Override
	public void start() {
		Manager.getInstance().beginGame();
	}
	
	@Override
	public void stop() {
		Manager.getInstance().exit();
	}
	
	@Override
	public void repaint() {
		super.repaint();
	}
	
	@Override
	public void update(Graphics g) {
		Canvas.getInstance().paintComponent(g);
	}
	
	@Override
	public void processKeyEvent(KeyEvent e) {
        KeyListener listener = KeyDirector.getInstance();
        if (listener != null) {
            int id = e.getID();
            switch(id) {
              case KeyEvent.KEY_TYPED:
                  listener.keyTyped(e);
                  break;
              case KeyEvent.KEY_PRESSED:
                  listener.keyPressed(e);
                  break;
              case KeyEvent.KEY_RELEASED:
                  listener.keyReleased(e);
                  break;
            }
        }
	}
}
