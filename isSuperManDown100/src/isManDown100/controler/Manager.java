package isManDown100.controler;

import isManDown100.source.Info;
import isManDown100.source.Sound;
import isManDown100.view.Canvas;

/**
 * ����ı�ʵ����ʱ���������������Ҫ���еĳ�ʼ��������������Ϸ����Ŀ��ƣ����磺��ͣ�����������¿�ʼ��
 */
public class Manager implements Runnable {
	private static Manager m_self;
	private BoardButler boardButler;
	private PlayerButler playerButler;
	private KeyDirector keyDirector;
	private Canvas canvas;
	// �߳̿���
	private boolean flag = true;

	private Manager() {
		initAll();
	}

	public static Manager getInstance() {
		if (m_self == null)
			m_self = new Manager();
		return m_self;
	}

	/**
	 * ������еĳ�ʼ������
	 */
	public void initAll() {
		Info.init();
		boardButler = BoardButler.getInstance();
		playerButler = PlayerButler.getInstance();
		keyDirector = KeyDirector.getInstance();
		canvas = Canvas.getInstance();
		Sound.getInstance();
	}
	/**
	 * ������Ϸ�����߳�
	 */
	public void beginGame() {
		new Thread(this).start();
	}
	
	/**
	 * ���̼߳����Ϸ���
	 */
	@Override
	public synchronized void run() {
		while (flag) {
			try {
				Thread.sleep(Info.repaintTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//������ײ���
			HitCheck.hitCheck(playerButler.player, BoardButler.BOARDS);
			boardButler.move();
			playerButler.move();
			statusTest();
			if (isGameover())
				gameover();
			canvas.repaint();
		}
	}

	/**
	 * �л���gameover���棬�ҽ��м��
	 */
	public void gameover() {
		if (isReplay())
			restart();
		else
			exit();
	}

	/**
	 * �Ƿ�������Ϸ
	 */
	//δʵ��
	public boolean isReplay() {
		return true;
	}

	/**
	 * �˳���Ϸ<br/>
	 * ������е��̹߳رն���
	 */
	public void exit() {
		playerButler.destroy();
		boardButler.destroy();
		Sound.getInstance().destroy();
		destroy();
		System.exit(0);
	}

	/**
	 * �����Ϸ��ͣ���˳�״̬
	 */
	public void statusTest() {
		do {
			if (keyDirector.isExit())
				exit();
			if (keyDirector.isStop())
				canvas.repaint();
		} while (keyDirector.isStop());
	}

	/**
	 * �ж��Ƿ�gameover
	 */
	public boolean isGameover() {
		if (playerButler.player.isDead()) {
			if (!playerButler.check.isFallDeep())
				Sound.getInstance().setID(Sound.ID.MD2);
			else
				Sound.getInstance().setID(Sound.ID.MD1);
			playerButler.player.setImage(null);
			return true;
		} else
			return false;
	}

	/**
	 * ���¿�ʼ��Ϸ
	 */
	public void restart() {
		Info.reset();
	}

	public void destroy() {
		flag = false;
	}
}
