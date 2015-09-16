package isManDown100.controler;

import isManDown100.model.boards.Board;
import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Info;
import isManDown100.source.WindowInf;

public class HitCheck {
	// Man��� ���ܷ�����ײ��Board �ƶ����y����
	private int mY;
	// Board�����ұ�x����ֵ
	private int bX, bX2;
	// Manվ����Board��ʱ��y����ֵ
	private int bY;
	private Board[] bs;
	private Man m;

	public HitCheck(Man m, Board[] bs) {
		this.bs = bs;
		this.m = m;
		mY = m.getY() + m.getVy() + Info.backgroundSpeed;
	}

	/**
	 * �˷�����������BoardButler.BOARDSͨ��
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
	 * ���� ������Man������ײ��Board ������
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
	 * ����Ƿ���Boards[index]������ײ
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
	 * У��Man��ײ���y����
	 */
	public void correctManLocation() {
		m.setY(bY);
	}

	/**
	 * ��Man��һ���ƶ���Խ����߽磬����true
	 */
	public boolean isLXBorder() {
		return (m.getX() + m.getVx()) <= (Img.border.getWidth(null)
				+ m.getWidth() / 2 + WindowInf.OPERATE_X0);
	}

	/**
	 * ��Man��һ���ƶ���Խ���ұ߽磬����true
	 */
	public boolean isRXBorder() {
		return (m.getX() + m.getVx()) >= (WindowInf.OPERATE_WIDTH
				- Img.border.getWidth(null) - m.getWidth() / 2 + WindowInf.OPERATE_X0);
	}

	/**
	 * �ж�Man�Ƿ񴥶�
	 */
	public boolean isTouchTop() {
		return m.getY() <= (WindowInf.OPERATE_Y0 + 16 + m.getHeight() / 2);
	}

	/**
	 * �ж�Man�Ƿ񴥵�
	 */
	public boolean isFallDeep() {
		return m.getY() >= (WindowInf.OPERATE_Y0 + WindowInf.OPERATE_HEIGHT);
	}

}
