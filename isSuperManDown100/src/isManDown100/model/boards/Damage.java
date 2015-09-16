package isManDown100.model.boards;

import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Sound;
import static isManDown100.model.boards.BoardSort.DAMAGE;

public class Damage extends Board {
	//������Ѫ�����action()ִ�д���
	public static int TIME = 20;
	private boolean actionable = true;
	private int time = TIME;
	
	public Damage() {
		setImage(Img.board_f);
	}

	public int getStandY() {
		return (int) getDrawPoint().getY() + 17;
	}

	public BoardSort getSort() {
		return DAMAGE;
	}

	public void action(Man player) {
		if (actionable) {
			Sound.getInstance().setID(Sound.ID.MH);
			player.setHurt(true);
			//��Ѫ���������ٶȳ�����
			player.setHP(player.getHP() - Math.abs(player.getVy())/2);
			actionable = false;
		}
		if(time--==0)
		{
			Sound.hurt.play();
			player.setHurt(true);
			player.setHP(player.getHP() - 1);
			time = TIME;
		}
	}

	@Override
	protected void changeImg() {

	}
}
