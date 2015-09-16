package isManDown100.model;

import isManDown100.source.Img;
import static isManDown100.source.Info.*;

import java.awt.Graphics;
/**
 * 此类封装了游戏得分与难度信息
 */
public class Level {
	//数字绘制的参照点
	private static final int x0 = 199 + Img.word1.getWidth(null);
	private static final int y0 = 21;
	// 得分，即地下score层
	private int score;
	private int[] lastScoreBit;
	private int[] y;
	private static Level m_self;
	
	private Level() {
		score = 0;
		lastScoreBit = new int[4];
		y = new int[4];
	}
	
	public static Level getInstance() {
		if(m_self==null)
			m_self = new Level();
		return m_self;
	}
	
	public void reset() {
		score = 0;
		for(int i=0;i<lastScoreBit.length;i++)
			lastScoreBit[i] = y[i] = 0;
	}
	
	public int getScore() {
		return score;
	}
	/**
	 * 得分加1(Increase Socre)
	 */
	public void incScore() {
		score++;
	}
	/**
	 * 游戏难度
	 */
	public int grade() {
		if (score % 10 == 0)
			switch (score / 10) {
			default:
				break;
			case 1:
				backgroundSpeed = SPEED1;
				break;
			case 3:
				backgroundSpeed = SPEED2;
				break;
			case 7:
				backgroundSpeed = SPEED3;
				break;
			}
		return score / 10;
	}

	public void draw(Graphics gOffScreen) {
		int[] scoreBit = { score % 10, score % 100 / 10, score % 1000 / 100,
				score / 1000 };
		gOffScreen.setClip(x0, y0, 120, 32);
		for (int i = 0; i < scoreBit.length; i++) {
			if (lastScoreBit[i] == scoreBit[i])
				gOffScreen.drawImage(Img.number[scoreBit[i]], x0 + 90 - 30 * i,
						y0, null);
			else {
				gOffScreen.drawImage(Img.number[lastScoreBit[i]], x0 + 90 - 30
						* i, y0 - y[i], null);
				gOffScreen.drawImage(Img.number[scoreBit[i]], x0 + 90 - 30 * i,
						y0 + 32 - y[i], null);
				// score进位时图片差时滚动的处理
				if ((i == 0 || (y[i - 1] > y0 / 2 || y[i] > y0 / 2))
						&& 32 == y[i]++) {
					lastScoreBit[i] = scoreBit[i];
					y[i] = 0;
				}
			}
		}
	}

}
