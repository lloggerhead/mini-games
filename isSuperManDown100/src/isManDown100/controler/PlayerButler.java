package isManDown100.controler;

import isManDown100.model.Man;
import isManDown100.source.*;

/**
 * 控制Man的移动、碰撞、销毁、重置
 */
public class PlayerButler {
	// 重力加速度
	public static final int A_GRAVITY = 1;
	// Man被顶部钉板刺中后的下落速度
	public static final int FALL_SPEED = 3;
	// Man图片的一般长宽
	public static final int HEIGHT = 32;
	public static final int WIDTH = 32;
	private static PlayerButler m_self;
	public Man player;
	public HitCheck check;

	private PlayerButler() {
		init();
	}

	public static PlayerButler getInstance() {
		if (m_self == null)
			m_self = new PlayerButler();
		return m_self;
	}

	private void init() {
		player = new Man(Img.javaman_s[0]);
		check = new HitCheck(player, BoardButler.BOARDS);
	}

	/**
	 * 此方法单方面与BoardButler.BOARDS通信
	 */
	public void reset() {
		if (player != null)
			player.destroy();
		init();
		player.setX(BoardButler.BOARDS[0].getX());
		player.setY(BoardButler.BOARDS[0].getStandY() - player.getHeight()/2);
	}
	
	public void move() {
		xMove();
		yMove();
		player.move();
	}

	/**
	 * 此方法单方面与BoardButler.BOARDS、HitCheck通信
	 */
	private void xMove() {
		class Constant {
			//摩擦系数
			static final double RATIO = 0.8;
		}
		if (KeyDirector.getInstance().isLeftPressed())
			if (!player.isArrMaxV() || player.getVx() > 0)
				player.getAv().setX(-Man.AV);
			else 
				player.getAv().setX(0);
		else if (KeyDirector.getInstance().isRightPressed())
			if (!player.isArrMaxV() || player.getVx() < 0)
				player.getAv().setX(Man.AV);
			else 
				player.getAv().setX(0);
		// 摩擦力
		else if (!player.isFallDown())
			if (player.getVx() > Man.AV * Constant.RATIO)
				player.getAv().setX(-Man.AV * Constant.RATIO);
			else if (player.getVx() < -Man.AV * Constant.RATIO)
				player.getAv().setX(Man.AV * Constant.RATIO);
			else
				player.getAv().setX(-player.getVx() * Constant.RATIO);
		else 
			player.getAv().setX(0);
		if (check.isLXBorder())
			player.setVx(Img.border.getWidth(null) + player.getWidth() / 2
					- player.getX() + WindowInf.OPERATE_X0);
		if (check.isRXBorder())
			player.setVx(WindowInf.OPERATE_WIDTH - Img.border.getWidth(null)
					- player.getWidth() / 2 - player.getX()
					+ WindowInf.OPERATE_X0);
	}

	/**
	 * 此方法单方面与HitCheck通信
	 */
	private void yMove() {
		if (player.isFallDown())
			player.getAv().setY(A_GRAVITY);
		else
		{
			player.getAv().setY(0);
			player.setVy(-Info.backgroundSpeed);
		}
		if (check.isTouchTop()) {
			Sound.getInstance().setID(Sound.ID.MH);
			player.setStand(false);
			player.setHurt(true);
			player.setHP(player.getHP() - 4);
			player.setVy(FALL_SPEED);
		} else if (check.isFallDeep()) {
			player.setHP(0);
			return;
		}
	}

	public void destroy() {
		player.destroy();
	}

}
