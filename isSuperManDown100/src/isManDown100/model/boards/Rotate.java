package isManDown100.model.boards;

import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Sound;
import static isManDown100.model.boards.BoardSort.ROTATE;

public class Rotate extends Board {
	private boolean imgChange = false;
	private boolean actionable = true;
	private boolean played = false;

	public Rotate() {
		setImage(Img.board_d[0]);
		new Thread(this).start();
	}

	public int getStandY() {
		return getY() - Img.board_d[0].getHeight(null) / 2;
	}

	public BoardSort getSort() {
		return ROTATE;
	}

	public void action(Man player) {
		if (actionable) {
			if(!played) {
				Sound.getInstance().setID(Sound.ID.BD);
				played = true;
				imgChange = true;
				if (!player.isFullHP())
					player.setHP(player.getHP() + 1);
			}
			//存在多线程同步问题，所以就>=而不是==
			if (status >= 4)
				player.setStand(false);
			if (status >= 8)
				actionable = false;
		}
	}

	@Override
	protected void changeImg() {
		if (imgChange) {
			status++;
			//存在多线程同步问题，所以就>=而不是==
			if (status >= 8)
				imgChange = false;
			switch (status) {
			case 1:
				setImage(Img.board_d[0]);
				break;
			case 2:
				setImage(Img.board_d[1]);
				break;
			case 3:
				setImage(Img.board_d[2]);
				break;
			case 4:
				setImage(Img.board_d[3]);
				break;
			case 5:
				setImage(Img.board_d[4]);
				break;
			case 6:
				setImage(Img.board_d[5]);
				break;
			case 7:
				setImage(Img.board_d[0]);
				break;
			}
		} else
			return;
	}
}
