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
 * 绘图面板，进行游戏界面的绘制，相当于view层
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {
	//此变量记录了程序启动的位置，即从MainFrame还是MainApplet启动的
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
	 * 启用双缓冲并绘制静态区域，即只用绘制一次的区域
	 */
	private void drawStaticArea(Graphics g) {
		g.drawImage(Img.background, 0, 0, WindowInf.GAME_WIDTH,
				WindowInf.GAME_HEIGHT, this);
		g.drawImage(Img.lifeWord, 80, 13, this);
		g.drawImage(Img.word1, 199, 17, this);
		// 120为4个数字的宽度之和
		g.drawImage(Img.word2, 199 + 120 + Img.word1.getWidth(null), 17, this);
	}

	/**
	 * 绘制游戏操作区域
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
	 * 绘制背景
	 */
	private void drawBackgroud(Graphics g) {
		g.drawImage(Img.back2, WindowInf.OPERATE_X0, WindowInf.OPERATE_Y0,
				WindowInf.OPERATE_WIDTH, WindowInf.OPERATE_HEIGHT, this);
	}

	/**
	 * 绘制不断上滚的左右边界墙
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
	 * 绘制顶部钉子
	 */
	private void drawNall(Graphics g) {
		g.drawImage(Img.board_top,
				WindowInf.OPERATE_X0 + Img.border.getWidth(null),
				WindowInf.OPERATE_Y0, this);
	}

	/**
	 * 绘制游戏暂停时出现的Stop提示
	 */
	private void drawStop(Graphics g) {
		if (KeyDirector.getInstance().isStop())
			g.drawImage(Img.pause, WindowInf.OPERATE_X0
					+ WindowInf.OPERATE_WIDTH / 2 - Img.pause.getWidth(null)
					/ 2, WindowInf.OPERATE_Y0 + WindowInf.OPERATE_HEIGHT / 2
					- Img.pause.getHeight(null) / 2, null);
	}

	/**
	 * 绘制得分，即第多少多少层
	 */
	private void drawScore(Graphics g) {
		Level.getInstance().draw(g);
	}

	/**
	 * 绘制血槽
	 */
	private void drawHP(Graphics g) {
		PlayerButler.getInstance().player.drawHP(g);
	}
}
