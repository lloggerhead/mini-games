package kyodai;

import java.awt.*;
import java.util.*;
import kyodai.*;

class Square {
	private boolean exist = true;
	private int id;
	private Image img;
	private int x;
	private int y;
	Square(int x, int y, int id) {
		setID(id);
		this.x = x;
		this.y = y;
	}
	void setExist(boolean exist) { 
		this.exist = exist; 
	}
	void setID(int id) { 
		this.id = id; 
		img = ImgFactory.images[id];
	}
	boolean isExist() { 
		return exist; 
	}
	int getID() { 
		return id; 
	}
	Image getImg() { 
		return img; 
	}
	int getX() {
		return x;
	}
	int getY() {
		return y;
	}
	public String toString() {
		return "["+id+"-"+(exist? "E":"D") +"]"+" ("+x+","+y+")";
	}
}

public class Map {
	static final int XLEN = 16;
	static final int YLEN = 10;
	static final Random SEED = new Random(); 
	private static Square[][] mapGrid;
	static boolean inXBound(int x) {
		if(x>=0&&x<XLEN)
			return true;
		return false;
	}
	static boolean inYBound(int y) {
		if(y>=0&&y<YLEN)
			return true;
		return false;
	}
	static Square[][] getMap() {
		if(mapGrid!=null)
			return mapGrid;
		mapGrid = new Square[YLEN][XLEN];
		for(int y=0;y<YLEN;y++)
			for(int x=0;x<XLEN;x++)
				if(mapGrid[y][x]==null)
				{
					int x2, y2;
					do {
						x2 = SEED.nextInt(XLEN);
						y2 = SEED.nextInt(YLEN);
					} while(mapGrid[y2][x2]!=null || (x2==x&&y2==y));
					int id = SEED.nextInt(ImgFactory.images.length);
					mapGrid[y][x] = new Square(x, y, id);
					mapGrid[y2][x2] = new Square(x2, y2, id); 
				}
		return mapGrid;
	}
	static void shuffle() {
		ArrayList<Integer> idList = new ArrayList<Integer>();
		for(int y=0;y<YLEN;y++)
			for(int x=0;x<XLEN;x++)
				if(mapGrid[y][x].isExist())
					idList.add(mapGrid[y][x].getID());
		Integer[] randID = idList.toArray(new Integer[] {});
		for(int i=0;i<randID.length;i++)
		{
			int rand = SEED.nextInt(randID.length);
			Integer temp = randID[i];
			randID[i] = randID[rand];
			randID[rand] = temp;
		}
		int i = 0;
		for(int y=0;y<YLEN;y++)
			for(int x=0;x<XLEN;x++)
				if(mapGrid[y][x].isExist())
					mapGrid[y][x].setID(randID[i++]);
	}
}
