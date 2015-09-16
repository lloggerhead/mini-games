package isManDown100.controler;

import java.awt.event.*;

/**
 * ��Ϸ�ļ�����Ӧ������������ƶ�����Ϸ����ͣ���������˳�
 */
//KeyDirector==��������
public class KeyDirector implements KeyListener {
	private boolean leftPressed = false;
	private boolean rightPressed = false;
	private boolean gameStop = false;
	private boolean gameRestart = false;
	private boolean gameExit = false;
	private static KeyDirector m_self;
	
	private KeyDirector() {}
	
	public static KeyDirector getInstance() {
		if(m_self==null) 
			m_self = new KeyDirector();
		return m_self;
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPressed = true;
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPressed = true;
		else if (e.getKeyCode() == KeyEvent.VK_ENTER)
			if (gameStop)
				gameStop = false;
			else
				gameStop = true;
		else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
			gameExit = true;
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT)
			leftPressed = false;
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
			rightPressed = false;
	}

	public void keyTyped(KeyEvent e) {
	}

	public boolean isRestart() {
		return gameRestart;
	}

	public boolean isStop() {
		return gameStop;
	}

	public boolean isExit() {
		return gameExit;
	}

	public boolean isLeftPressed() {
		return leftPressed;
	}

	public boolean isRightPressed() {
		return rightPressed;
	}
}
