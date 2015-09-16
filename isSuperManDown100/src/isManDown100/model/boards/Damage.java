package isManDown100.model.boards;

import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Sound;
import static isManDown100.model.boards.BoardSort.DAMAGE;

public class Damage extends Board {
	//持续掉血所需的action()执行次数
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
			//掉血量与下落速度成正比
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
