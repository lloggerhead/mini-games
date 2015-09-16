package isManDown100.model.boards;

import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Sound;
import static isManDown100.model.boards.BoardSort.NORMAL;

public class Normal extends Board {
	private boolean actionable = true;

	public Normal() {
		setImage(Img.board_a);
	}

	public BoardSort getSort() {
		return NORMAL;
	}

	public void action(Man player) {
		if (actionable) {
			Sound.getInstance().setID(Sound.ID.BA);
			if (!player.isFullHP())
				player.setHP(player.getHP() + 1);
			actionable = false;
		}
	}

	@Override
	public int getStandY() {
		return (int) getDrawPoint().getY();
	}

	@Override
	protected void changeImg() {

	}
}
