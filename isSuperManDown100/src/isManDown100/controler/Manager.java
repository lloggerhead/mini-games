package isManDown100.controler;

import isManDown100.source.Info;
import isManDown100.source.Sound;
import isManDown100.view.Canvas;

/**
 * 此类的被实例化时会完成所有类所需要进行的初始化动作；进行游戏整体的控制，比如：暂停、继续、重新开始等
 */
public class Manager implements Runnable {
	private static Manager m_self;
	private BoardButler boardButler;
	private PlayerButler playerButler;
	private KeyDirector keyDirector;
	private Canvas canvas;
	// 线程开关
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
	 * 完成所有的初始化动作
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
	 * 启动游戏控制线程
	 */
	public void beginGame() {
		new Thread(this).start();
	}
	
	/**
	 * 此线程监控游戏情况
	 */
	@Override
	public synchronized void run() {
		while (flag) {
			try {
				Thread.sleep(Info.repaintTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//进行碰撞检测
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
	 * 切换到gameover界面，且进行检测
	 */
	public void gameover() {
		if (isReplay())
			restart();
		else
			exit();
	}

	/**
	 * 是否重新游戏
	 */
	//未实现
	public boolean isReplay() {
		return true;
	}

	/**
	 * 退出游戏<br/>
	 * 完成所有的线程关闭动作
	 */
	public void exit() {
		playerButler.destroy();
		boardButler.destroy();
		Sound.getInstance().destroy();
		destroy();
		System.exit(0);
	}

	/**
	 * 检测游戏暂停、退出状态
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
	 * 判断是否gameover
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
	 * 重新开始游戏
	 */
	public void restart() {
		Info.reset();
	}

	public void destroy() {
		flag = false;
	}
}
