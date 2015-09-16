package isManDown100.controler;

import isManDown100.model.boards.Board;
import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Info;
import isManDown100.source.WindowInf;

public class HitCheck {
	// Man相对 可能发生碰撞的Board 移动后的y坐标
	private int mY;
	// Board的左右边x坐标值
	private int bX, bX2;
	// Man站立于Board上时的y坐标值
	private int bY;
	private Board[] bs;
	private Man m;

	public HitCheck(Man m, Board[] bs) {
		this.bs = bs;
		this.m = m;
		mY = m.getY() + m.getVy() + Info.backgroundSpeed;
	}

	/**
	 * 此方法单方面与BoardButler.BOARDS通信
	 */
	public static void hitCheck(Man player, Board[] bs) {
		HitCheck check = new HitCheck(player, bs);
		int index = check.nextIndex();
		if (check.isHit(index)) {
			player.setStand(true);
			check.correctManLocation();
		} else if (!check.isAboveBoard())
			player.setStand(false);
		if (!player.isFallDown())
			BoardButler.BOARDS[index].action(player);
	}
	
	/**
	 * 返回 可能与Man发生碰撞的Board 的索引
	 */
	public int nextIndex() {
		int dis1 = 0, dis2 = -1000, index = 0;
		for (int i = 0; i < bs.length; i++)
			if (bs[i] != null) {
				dis1 = m.getY() + m.getHeight() / 2 - bs[i].getStandY()
						- PlayerButler.A_GRAVITY;
				if (dis1 <= 0 && dis1 > dis2) {
					dis2 = dis1;
					index = i;
				}
			}
		return index;
	}

	public boolean isAboveBoard() {
		return m.getX() >= bX && m.getX() <= bX2;
	}

	/**
	 * 检测是否与Boards[index]发生碰撞
	 */
	public boolean isHit(int index) {
		if (bs[index] == null)
			return false;
		bX = (int) bs[index].getDrawPoint().getX();
		bX2 = (int) bs[index].getDrawPoint().getX() + bs[index].getWidth();
		bY = bs[index].getStandY() - m.getHeight() / 2;
		return isAboveBoard() && (m.getY() <= bY && mY >= bY);
	}

	/**
	 * 校正Man碰撞后的y坐标
	 */
	public void correctManLocation() {
		m.setY(bY);
	}

	/**
	 * 若Man下一次移动将越过左边界，返回true
	 */
	public boolean isLXBorder() {
		return (m.getX() + m.getVx()) <= (Img.border.getWidth(null)
				+ m.getWidth() / 2 + WindowInf.OPERATE_X0);
	}

	/**
	 * 若Man下一次移动将越过右边界，返回true
	 */
	public boolean isRXBorder() {
		return (m.getX() + m.getVx()) >= (WindowInf.OPERATE_WIDTH
				- Img.border.getWidth(null) - m.getWidth() / 2 + WindowInf.OPERATE_X0);
	}

	/**
	 * 判断Man是否触顶
	 */
	public boolean isTouchTop() {
		return m.getY() <= (WindowInf.OPERATE_Y0 + 16 + m.getHeight() / 2);
	}

	/**
	 * 判断Man是否触底
	 */
	public boolean isFallDeep() {
		return m.getY() >= (WindowInf.OPERATE_Y0 + WindowInf.OPERATE_HEIGHT);
	}

}
