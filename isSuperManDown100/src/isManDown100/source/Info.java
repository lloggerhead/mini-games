package isManDown100.source;

import isManDown100.controler.BoardButler;
import isManDown100.controler.KeyDirector;
import isManDown100.controler.PlayerButler;
import isManDown100.model.Level;


/**
 * ������Ϸ������Ϣ�ĳ�ʼ�������ã����磺��ǰ�ؿ����Ѷȡ��ػ�ʱ�䡢���������ٶȵ�
 */
public class Info {
	// �����Ϲ�ÿ�׶ε��ٶ�
	public static final int SPEED0 = 3;
	public static final int SPEED1 = 4;
	public static final int SPEED2 = 5;
	public static final int SPEED3 = 6;
	// �ػ���(ms)
	public static int repaintTime = 35;
	// �����Ϲ��ٶ�(����)
	public static int backgroundSpeed = SPEED0;

	/**
	 * �����Ϸ״̬�ĳ�ʼ��
	 */
	public static void init() {
		KeyDirector.getInstance();
		reset();
	}

	/**
	 * ����GameInf�����¿�ʼ��Ϸǰ����ô˷���
	 */
	public static void reset() {
		backgroundSpeed = SPEED0;
		Level.getInstance().reset();
		//PlayerButler����reset֮ǰ�����ʼ��BoardButler
		BoardButler.getInstance().reset();
		PlayerButler.getInstance().reset();
	}

}
