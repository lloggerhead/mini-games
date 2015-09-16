package isManDown100.model.boards;

import java.awt.*;

import fz.game.Animal;

import isManDown100.model.Man;
import isManDown100.source.Info;

/**
 * 游戏中出现的Board的父类
 */

public abstract class Board extends Animal implements Runnable {
	protected int status = 1;
	protected Image img;
	// 动画播放开关
	protected boolean playFlag = true;

	public abstract int getStandY();

	/**
	 * 返回绘图坐标
	 * 
	 * @return 绘图坐标
	 */
	public Point getDrawPoint() {
		return new Point(x - img.getWidth(null) / 2, y - img.getHeight(null)
				/ 2);
	}

	public int getWidth() {
		return img.getWidth(null);
	}

	public abstract BoardSort getSort();

	public void setImage(Image img) {
		this.img = img;
	}

	public abstract void action(Man player);

	public void draw(Graphics g) {
		if (img != null)
			g.drawImage(img, (int) getDrawPoint().getX(), (int) getDrawPoint()
					.getY(), null);
	}

	@Override
	public void run() {
		while (playFlag) {
			try {
				Thread.sleep(Info.repaintTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			changeImg();
		}
	}

	/**
	 * 结束动画播放线程
	 */
	/**
	 * 改变图片来实现动画播放效果
	 */
	protected abstract void changeImg();

	public void destroy() {
		playFlag = false;
	}

}
