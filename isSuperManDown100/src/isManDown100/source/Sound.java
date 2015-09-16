package isManDown100.source;

import java.applet.*;

public final class Sound implements Runnable{
	/**
	 * None, Man dead1/2, Board_a/b/d/e, Man hurt, Game start, Game end 
	 */
	public enum ID {
		N, MD1, MD2, BA, BB, BD, BE, MH, GS, GE
	}
	public static final String DIR = "sounds/";
	public static final AudioClip dead1 = Applet.newAudioClip(Sound.class.getResource(DIR+"dead1.wav"));
	public static final AudioClip dead2 = Applet.newAudioClip(Sound.class.getResource(DIR+"dead2.wav"));
	public static final AudioClip board_a = Applet.newAudioClip(Sound.class.getResource(DIR+"board_a.wav"));
	public static final AudioClip board_b = Applet.newAudioClip(Sound.class.getResource(DIR+"board_b.wav"));
	public static final AudioClip board_d = Applet.newAudioClip(Sound.class.getResource(DIR+"board_d.wav"));
	public static final AudioClip board_e = Applet.newAudioClip(Sound.class.getResource(DIR+"board_e.wav"));
	public static final AudioClip hurt = Applet.newAudioClip(Sound.class.getResource(DIR+"hurt.wav"));
	public static final AudioClip start = Applet.newAudioClip(Sound.class.getResource(DIR+"start.wav"));
	public static final AudioClip end = Applet.newAudioClip(Sound.class.getResource(DIR+"end.wav"));
	
	private boolean flag = true;
	private ID id = ID.N;
	private static Sound m_self;
	
	private Sound() {
		new Thread(this).start();
	}
	
	public static Sound getInstance() {
		if(m_self==null)
			m_self = new Sound();
		return m_self;
	}
	
	public synchronized void setID(ID id1) {
		id = id1;
	}
	
	/**
	 * 播放游戏音效
	 */
	private synchronized void play() {
		switch(id) {
		default:
			break;
		case MD1:
			dead1.play();
			break;
		case MD2:
			dead2.play();
			break;
		case BA:
			board_a.play();
			break;
		case BB:
			board_b.play();
			break;
		case BD:
			board_d.play();
			break;
		case BE:
			board_e.play();
			break;
		case MH:
			hurt.play();
			break;
		case GS:
			start.play();
			break;
		case GE:
			end.play();
			break;
		}
		id = ID.N;
	}
	
	//此线程单独播放音乐
	@Override
	public void run() {
		while(flag)
			play();
	}
	
	//结束此线程
	public void destroy() {
		flag = false;
	}
	
}
