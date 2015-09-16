package isManDown100.view;

import isManDown100.MainApplet;
import isManDown100.controler.BoardButler;
import isManDown100.controler.KeyDirector;
import isManDown100.controler.PlayerButler;
import isManDown100.model.Level;
import isManDown100.source.Img;
import isManDown100.source.WindowInf;

import java.awt.*;
import javax.swing.*;

/**
 * ��ͼ��壬������Ϸ����Ļ��ƣ��൱��view��
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {
	//�˱�����¼�˳���������λ�ã�����MainFrame����MainApplet������
	public static boolean beginInJFrame;
	private static Canvas m_self;

	private Canvas() {
	}

	public static Canvas getInstance() {
		if (m_self == null)
			m_self = new Canvas();
		return m_self;
	}

	public void repaint() {
		if (beginInJFrame)
			super.repaint();
		else
			MainApplet.getInstance().repaint();
	}

	@Override
	public void paintComponent(Graphics g) {
		drawStaticArea(g);
		drawBackgroud(g);
		drawScore(g);
		drawOperateArea(g);
		drawHP(g);
	}

	/**
	 * ����˫���岢���ƾ�̬���򣬼�ֻ�û���һ�ε�����
	 */
	private void drawStaticArea(Graphics g) {
		g.drawImage(Img.background, 0, 0, WindowInf.GAME_WIDTH,
				WindowInf.GAME_HEIGHT, this);
		g.drawImage(Img.lifeWord, 80, 13, this);
		g.drawImage(Img.word1, 199, 17, this);
		// 120Ϊ4�����ֵĿ��֮��
		g.drawImage(Img.word2, 199 + 120 + Img.word1.getWidth(null), 17, this);
	}

	/**
	 * ������Ϸ��������
	 */
	private void drawOperateArea(Graphics g) {
		g.setClip(WindowInf.OPERATE_X0, WindowInf.OPERATE_Y0,
				WindowInf.OPERATE_WIDTH, WindowInf.OPERATE_HEIGHT);
		drawBorder(g);
		BoardButler.getInstance().draw(g);
		drawNall(g);
		PlayerButler.getInstance().player.draw(g);
		drawStop(g);
		g.setClip(0, 0, this.getWidth(), this.getHeight());
	}

	/**
	 * ���Ʊ���
	 */
	private void drawBackgroud(Graphics g) {
		g.drawImage(Img.back2, WindowInf.OPERATE_X0, WindowInf.OPERATE_Y0,
				WindowInf.OPERATE_WIDTH, WindowInf.OPERATE_HEIGHT, this);
	}

	/**
	 * ���Ʋ����Ϲ������ұ߽�ǽ
	 */
	private void drawBorder(Graphics g) {
		g.drawImage(Img.border, WindowInf.OPERATE_X0,
				(WindowInf.OPERATE_Y0 + BoardButler.getInstance().y), this);
		g.drawImage(Img.border, WindowInf.OPERATE_X0, (WindowInf.OPERATE_Y0
				+ WindowInf.OPERATE_HEIGHT + BoardButler.getInstance().y), this);
		g.drawImage(Img.border, WindowInf.OPERATE_X0 + WindowInf.OPERATE_WIDTH
				- Img.border.getWidth(null),
				(WindowInf.OPERATE_Y0 + BoardButler.getInstance().y), this);
		g.drawImage(Img.border, WindowInf.OPERATE_X0 + WindowInf.OPERATE_WIDTH
				- Img.border.getWidth(null), (WindowInf.OPERATE_Y0
				+ WindowInf.OPERATE_HEIGHT + BoardButler.getInstance().y), this);
	}

	/**
	 * ���ƶ�������
	 */
	private void drawNall(Graphics g) {
		g.drawImage(Img.board_top,
				WindowInf.OPERATE_X0 + Img.border.getWidth(null),
				WindowInf.OPERATE_Y0, this);
	}

	/**
	 * ������Ϸ��ͣʱ���ֵ�Stop��ʾ
	 */
	private void drawStop(Graphics g) {
		if (KeyDirector.getInstance().isStop())
			g.drawImage(Img.pause, WindowInf.OPERATE_X0
					+ WindowInf.OPERATE_WIDTH / 2 - Img.pause.getWidth(null)
					/ 2, WindowInf.OPERATE_Y0 + WindowInf.OPERATE_HEIGHT / 2
					- Img.pause.getHeight(null) / 2, null);
	}

	/**
	 * ���Ƶ÷֣����ڶ��ٶ��ٲ�
	 */
	private void drawScore(Graphics g) {
		Level.getInstance().draw(g);
	}

	/**
	 * ����Ѫ��
	 */
	private void drawHP(Graphics g) {
		PlayerButler.getInstance().player.drawHP(g);
	}
}
