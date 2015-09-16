package isManDown100.source;

import isManDown100.controler.BoardButler;
import isManDown100.controler.KeyDirector;
import isManDown100.controler.PlayerButler;
import isManDown100.model.Level;


/**
 * 进行游戏基本信息的初始化和重置，比如：当前关卡的难度、重绘时间、背景滚动速度等
 */
public class Info {
	// 背景上滚每阶段的速度
	public static final int SPEED0 = 3;
	public static final int SPEED1 = 4;
	public static final int SPEED2 = 5;
	public static final int SPEED3 = 6;
	// 重绘间隔(ms)
	public static int repaintTime = 35;
	// 背景上滚速度(像素)
	public static int backgroundSpeed = SPEED0;

	/**
	 * 完成游戏状态的初始化
	 */
	public static void init() {
		KeyDirector.getInstance();
		reset();
	}

	/**
	 * 重置GameInf，重新开始游戏前需调用此方法
	 */
	public static void reset() {
		backgroundSpeed = SPEED0;
		Level.getInstance().reset();
		//PlayerButler调用reset之前必须初始化BoardButler
		BoardButler.getInstance().reset();
		PlayerButler.getInstance().reset();
	}

}
