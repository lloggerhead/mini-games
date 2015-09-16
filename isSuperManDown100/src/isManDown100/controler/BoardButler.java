package isManDown100.controler;

import isManDown100.model.boards.*;
import isManDown100.model.Level;
import isManDown100.source.Img;
import isManDown100.source.Info;
import isManDown100.source.WindowInf;

import java.awt.Graphics;
import java.util.*;

/**
 * 控制Board的生产、销毁、移动
 */
public class BoardButler {
	// Board图片的一般大小
	public static final int HEIGHT = 16;
	public static final int WIDTH = 96;
	//生成每个Board的间隔距离
	public static final int DISTANCE = WindowInf.OPERATE_HEIGHT / 8 + HEIGHT;
	//存放每一屏的所有存在的Board
	public static final Board[] BOARDS = new Board[8];
	private static BoardButler m_self;
	// 背景及Board循环上滚的y坐标偏移量
	public int y = 0;
	// 记录最后生成的Board的y坐标偏移量
	private int dY = 0;
	
	private BoardButler() {
		init();
	}

	public static BoardButler getInstance() {
		if (m_self == null)
			m_self = new BoardButler();
		return m_self;
	}

	private void init() {
		dY = 0;
		y = 0;
		for (int i = 0; i < BOARDS.length; i++)
			BOARDS[i] = null;
		BOARDS[0] = getBoard(BoardSort.NORMAL);
		BOARDS[0].setY(WindowInf.OPERATE_HEIGHT + WindowInf.OPERATE_Y0);
	}

	public void reset() {
		for (int i = 0; i < BOARDS.length; i++)
			if(BOARDS[i]!=null)
				BOARDS[i].destroy();
		init();
	}
	
	/**
	 * 自动生成Board
	 */
	public void produce(int index) {
		//最后生成的Board离开一定距离时，生成新Board
		if (dY >= DISTANCE) {
			dY = 0;
			BOARDS[index] = getBoard(randSort());
		}
	}

	/**
	 * 所有的Board上滚，Board的生产也在此进行<br/>
	 * 与Level进行单方面通信
	 */
	public void move() {
		for (int i = 0; i < BOARDS.length; i++)
			if (BOARDS[i] != null) {
				//Board上滚
				BOARDS[i].setY(BOARDS[i].getY() - Info.backgroundSpeed);
				//Board下边离开边界时
				if (BOARDS[i].getY() + HEIGHT/2 < WindowInf.OPERATE_Y0)
				{
					BOARDS[i].destroy();
					BOARDS[i] = null;
				}
			} 
			//一定要下一次遍历Boards时再生成Board
			else produce(i);
		if (y <= -WindowInf.OPERATE_HEIGHT) {
			y = 0;
			Level.getInstance().incScore();
		}
		y -= Info.backgroundSpeed;
		dY += Info.backgroundSpeed;
	}
	
	public void draw(Graphics gOffScreen) {
		for (int i = 0; i < BOARDS.length; i++)
			if (BOARDS[i] != null)
				BOARDS[i].draw(gOffScreen);
	}

	/**
	 * 随机生成一个合适的x坐标，使Board能随机且正常的生成
	 */
	private int randX() {
		//位于 恰好未与边界相交的dX 的随机值
		int bufX = new Random().nextInt(WindowInf.OPERATE_WIDTH - Img.border.getWidth(null)*2 - WIDTH)
				+ Img.border.getWidth(null) + WIDTH/2;
		//若与左边界距离<=半Man宽，则堵住左边界
		if (bufX <= Img.border.getWidth(null) + WIDTH/2 + 1 + PlayerButler.WIDTH / 2)
			return Img.border.getWidth(null) + WIDTH/2 + 1;
		//若与左边界距离<=Man宽，则隔开左边界一个Man宽
		else if (bufX <= Img.border.getWidth(null) + WIDTH/2 + 1 + PlayerButler.WIDTH)
			return Img.border.getWidth(null) + WIDTH/2 + 1 + PlayerButler.WIDTH;
		//若与右边界距离<=半Man宽，则堵住右边界
		else if (bufX >= WindowInf.OPERATE_WIDTH - Img.border.getWidth(null) - WIDTH/2 - 1 - PlayerButler.WIDTH / 2)
			return WindowInf.OPERATE_WIDTH - Img.border.getWidth(null) - WIDTH/2 - 1;
		//若与右边界距离<=Man宽，则隔开右边界一个Man宽
		else if (bufX >= WindowInf.OPERATE_WIDTH - Img.border.getWidth(null) - WIDTH/2 - 1 - PlayerButler.WIDTH)
			return WindowInf.OPERATE_WIDTH - Img.border.getWidth(null) - WIDTH/2 - PlayerButler.WIDTH - 1;
		else
			return bufX;
	}

	/**
	 * 根据 Level单例 随机生成BoardSort<br/>
	 * 与Level进行单方面通信
	 */
	private BoardSort randSort() {
		int score = Level.getInstance().getScore();
		// 0.0-1.0
		double probability = Math.random();
		double degree = 0;
		switch (Level.getInstance().grade()) {
		case 0:
		case 1:
			degree = score * 0.005;
			break;
		case 2:
		case 3:
			degree = 0.1 + (score - 20) * 0.002;
			break;
		case 4:
		case 5:
			degree = 0.14 + (score - 40) * 0.001;
			break;
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
			degree = 0.16 + (score - 60) * 0.0005;
			break;
		}
		if (probability <= 0.3 - degree)
			return BoardSort.NORMAL;
		else if (probability - 0.3 <= 0.1 + 0.5 * degree)
			return BoardSort.DAMAGE;
		else if (probability - 0.4 - 0.5 * degree <= 0.2 + 0.8 * degree)
			return BoardSort.JUMP;
		else if (probability - 0.6 - 1.3 * degree <= 0.2 - 0.5 * degree)
			return BoardSort.MOVE;
		else
			return BoardSort.ROTATE;
	}

	/**
	 * 产生一个s种类的Board，并设置其(x, y)=(rand, fix)
	 */
	private Board getBoard(BoardSort s) {
		Board buf;
		switch (s) {
		default:
		case NORMAL:
			buf = new Normal();
			break;
		case DAMAGE:
			buf = new Damage();
			break;
		case MOVE:
			buf = new Move();
			break;
		case JUMP:
			buf = new Jump();
			break;
		case ROTATE:
			buf = new Rotate();
			break;
		}
		buf.setX(randX() + WindowInf.OPERATE_X0);
		buf.setY(WindowInf.OPERATE_HEIGHT + WindowInf.OPERATE_Y0);
		return buf;
	}

	public void destroy() {
		for (int i = 0; i < BOARDS.length; i++)
			if (BOARDS[i] != null)
				BOARDS[i].destroy();
	}

}
