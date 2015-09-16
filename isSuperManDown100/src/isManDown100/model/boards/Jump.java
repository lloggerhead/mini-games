package isManDown100.model.boards;

import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Info;
import isManDown100.source.Sound;
import static isManDown100.model.boards.BoardSort.JUMP;

public class Jump extends Board {
	private boolean imgChange = false;

	public Jump() {
		setImage(Img.board_e[3]);
		new Thread(this).start();
	}

	public int getStandY() {
		return getY() - Img.board_e[3].getHeight(null) / 2;
	}

	public BoardSort getSort() {
		return JUMP;
	}

	public void action(Man player) {
		Sound.getInstance().setID(Sound.ID.BE);
		player.setVy(-8-Info.backgroundSpeed);
		player.setStand(false);
		imgChange = true;
	}

	@Override
	protected void changeImg() {
		if (imgChange) {
			status++;
			if (status >= 13) {
				status = 0;
				imgChange = false;
			}
			switch (status) {
			case 1:
				setImage(Img.board_e[4]);
				break;
			case 2:
				setImage(Img.board_e[5]);
				break;
			case 3:
				setImage(Img.board_e[6]);
				break;
			case 4:
				setImage(Img.board_e[5]);
				break;
			case 5:
				setImage(Img.board_e[4]);
				break;
			case 6:
				setImage(Img.board_e[3]);
				break;
			case 7:
				setImage(Img.board_e[2]);
				break;
			case 8:
				setImage(Img.board_e[1]);
				break;
			case 9:
				setImage(Img.board_e[0]);
				break;
			case 10:
				setImage(Img.board_e[1]);
				break;
			case 11:
				setImage(Img.board_e[2]);
				break;
			case 12:
				setImage(Img.board_e[3]);
				break;
			}
		} else
			return;
	}
}
