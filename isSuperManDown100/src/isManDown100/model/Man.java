package isManDown100.model;

import fz.game.Spirit;
import isManDown100.controler.KeyDirector;
import isManDown100.source.Img;
import isManDown100.source.Info;

import java.awt.*;

public class Man extends Spirit implements Runnable {
	// 加速度--右，下为正方向
	public static final int AV = 2;
	public static final int MAX_V = 8;
	public static final int MAX_HP = 12;
	/**
	 * @see #move()
	 */
	private int dVx = 0;
	/**
	 * @see #move()
	 */
	private int dVy = 0;
	// 动画的帧索引
	private int leftMoveStatus = 1;
	private int rightMoveStatus = 1;
	private int fallDownStatus = 1;
	private int hurtStatus = 0;
	private int HP = MAX_HP;
	// 线程开关
	private boolean flag = true;
	// 状态变量
	private boolean fall = false;
	private boolean beHurt = false;

	public Man(Image img) {
		super(img);
		new Thread(this).start();
	}

	/**
	 * 返回图片宽
	 */
	public int getWidth() {
		if (getImage() == null)
			return Img.javaman_s[0].getWidth(null);
		return super.getWidth();
	}

	/**
	 * 返回图片长
	 */
	public int getHeight() {
		if (getImage() == null)
			return Img.javaman_s[0].getHeight(null);
		return super.getHeight();
	}

	/**
	 * 判断是否到达最大速
	 */
	public boolean isArrMaxV() {
		return Math.abs(getVx()) >= Man.MAX_V;
	}

	public int getHP() {
		return HP;
	}

	public void setHP(int HP) {
		if (HP <= 0)
			this.HP = 0;
		else
			this.HP = HP;
	}

	public boolean isFullHP() {
		return getHP() >= MAX_HP;
	}

	public boolean isDead() {
		return HP <= 0;
	}

	public boolean isFallDown() {
		return fall;
	}

	public void setStand(boolean canStand) {
		this.fall = canStand ? false : true;
	}

	public boolean isHurt() {
		if (hurtStatus == 12) {
			hurtStatus = 0;
			beHurt = false;
		}
		return beHurt;
	}

	public void setHurt(boolean hurt) {
		beHurt = hurt;
	}
	/**
	 * 给作用一个常量速度，但并不改变速度 
	 */
	public void setDv(int x, int y) {
		dVx = x;
		dVy = y;
	}
	
	public void move() {
		setX(getX()+getVx()+dVx);
		setY(getY()+getVy()+dVy);
		deltaV(getAv());
		dVx = 0;
		dVy = 0;
	}
	
	public void drawHP(Graphics g) {
		if (HP > 0 && HP<13)
			g.drawImage(Img.life[HP], 52, 31, null);
		else
			g.drawImage(Img.life[0], 52, 31, null);
	}

	@Override
	public void run() {
		while (flag) {
			try {
				Thread.sleep(Info.repaintTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			changeImg();
		}
	}

	private void changeImg() {
		if (isHurt())
			hurtStatus++;
		int i = hurtStatus % 2 == 0 ? 0 : 1;
		if (isFallDown()) {
			switch (fallDownStatus++) {
			case 1:
				setImage(Img.javaman_c[0 + i]);
				break;
			case 2:
				setImage(Img.javaman_c[2 + i]);
				break;
			case 3:
				setImage(Img.javaman_c[4 + i]);
				break;
			}
			if (fallDownStatus == 4)
				fallDownStatus = 1;
		} else if (KeyDirector.getInstance().isLeftPressed()) {
			switch (leftMoveStatus++) {
			case 1:
				setImage(Img.javaman_l[0 + i]);
				break;
			case 2:
				setImage(Img.javaman_l[2 + i]);
				break;
			case 3:
				setImage(Img.javaman_l[4 + i]);
				break;
			}
			if (leftMoveStatus == 4)
				leftMoveStatus = 1;
		} else if (KeyDirector.getInstance().isRightPressed()) {
			switch (rightMoveStatus++) {
			case 1:
				setImage(Img.javaman_r[0 + i]);
				break;
			case 2:
				setImage(Img.javaman_r[2 + i]);
				break;
			case 3:
				setImage(Img.javaman_r[4 + i]);
				break;
			}
			if (rightMoveStatus == 4)
				rightMoveStatus = 1;
		} else
			setImage(Img.javaman_s[0 + i]);
	}

	/**
	 * 关闭动画线程
	 */
	public void destroy() {
		flag = false;
	}
}
