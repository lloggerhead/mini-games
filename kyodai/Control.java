package kyodai;

import kyodai.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Control {  
	static Square lastSq = null;
	static int vanish(Square sq) {
		int minCross=Path.INFINITE;
		Graphics g = KyodaiGame.getBrush();
		if( lastSq!=null && (minCross=Path.explore(lastSq, sq)) != Path.INFINITE )
		{
			lastSq.setExist(false);
			sq.setExist(false);
			lastSq=null;
			SoundFactory.ELEC.play();
			Path.drawPath(minCross, g);
		}
		else {
			lastSq = sq;
			if(lastSq.isExist())
				SoundFactory.SEL.play();
		}
		KyodaiGame.refresh();
		return minCross;
	}	
	static void restart() {
		Square[][] mapGrid = Map.getMap();
		for(int y=0;y<Map.YLEN;y++)
			for(int x=0;x<Map.XLEN;x++)
				mapGrid[y][x].setExist(true);
		Map.shuffle();
		SoundFactory.BEGIN.play();
		KyodaiGame.refresh();
	}
	static void shuffle() {
		Map.shuffle();
		SoundFactory.SHUFFLE.play();
		KyodaiGame.refresh();
	}
	static void bomb() {
		SoundFactory.ITEMBOOM.play();
		KyodaiGame.refresh();
	}
	static boolean isWin() {
		Square[][] mapGrid = Map.getMap();
		for(int y=0;y<Map.YLEN;y++)
			for(int x=0;x<Map.XLEN;x++)
				if(mapGrid[y][x].isExist())
					return false;
		return true;
	}
	static void win() {
		SoundFactory.WIN[ new Random().nextInt(3) ].play();
	}
}
