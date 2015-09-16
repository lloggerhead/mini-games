package isManDown100.source;

public final class WindowInf {
	//绘图区的大小
	public static final int GAME_WIDTH = 465;   // 465==Img.background.getWidth(null);
	public static final int GAME_HEIGHT = 436;
	//游戏区左上角相对绘图区坐标
	public static final int OPERATE_X0 = 23;
	public static final int OPERATE_Y0 = 63;
	//游戏区的大小
	public static final int OPERATE_WIDTH = GAME_WIDTH-OPERATE_X0*2; 
	public static final int OPERATE_HEIGHT = GAME_HEIGHT-OPERATE_Y0-19;
	//整个窗口的大小
	public static final int WINDOW_WIDTH = GAME_WIDTH+5;
	public static final int WINDOW_HEIGHT = GAME_HEIGHT+26;
	
	private WindowInf(){}
}
