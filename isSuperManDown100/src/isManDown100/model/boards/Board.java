package isManDown100.model.boards;

import java.awt.*;

import fz.game.Animal;

import isManDown100.model.Man;
import isManDown100.source.Info;

/**
 * ��Ϸ�г��ֵ�Board�ĸ���
 */

public abstract class Board extends Animal implements Runnable {
	protected int status = 1;
	protected Image img;
	// �������ſ���
	protected boolean playFlag = true;

	public abstract int getStandY();

	/**
	 * ���ػ�ͼ����
	 * 
	 * @return ��ͼ����
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
	 * �������������߳�
	 */
	/**
	 * �ı�ͼƬ��ʵ�ֶ�������Ч��
	 */
	protected abstract void changeImg();

	public void destroy() {
		playFlag = false;
	}

}
