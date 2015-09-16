package isManDown100.model.boards;

import isManDown100.model.Man;
import isManDown100.source.Img;
import isManDown100.source.Sound;
import static isManDown100.model.boards.BoardSort.MOVE;

import java.util.Random;

public class Move extends Board {
	private final int V = Man.MAX_V-Man.AV*2;
	private boolean direction;
	private boolean played = false;

	public Move() {
		if (direction = new Random().nextBoolean())
			setImage(Img.board_b[0]);
		else
			setImage(Img.board_c[0]);
		new Thread(this).start();
	}

	public BoardSort getSort() {
		return MOVE;
	}

	public void action(Man player) {
		if(!played) {
			Sound.getInstance().setID(Sound.ID.BB);
			played = true;
		}
		if(Math.abs(player.getVx())<Man.MAX_V*1.3)
			player.setDv(direction?-V : V, 0);
	}

	@Override
	public int getStandY() {
		return (int) getDrawPoint().getY();
	}

	@Override
	protected void changeImg() {
		status++;
		if (status >= 5)
			status = 1;
		if (direction == true)
			switch (status) {
			case 1:
				setImage(Img.board_b[0]);
				break;
			case 2:
				setImage(Img.board_b[1]);
				break;
			case 3:
				setImage(Img.board_b[2]);
				break;
			case 4:
				setImage(Img.board_b[3]);
				break;
			}
		else
			switch (status) {
			case 1:
				setImage(Img.board_c[0]);
				break;
			case 2:
				setImage(Img.board_c[1]);
				break;
			case 3:
				setImage(Img.board_c[2]);
				break;
			case 4:
				setImage(Img.board_c[3]);
				break;
			}
	}
	
}
