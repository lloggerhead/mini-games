package kyodai;

import static kyodai.DrawBoard.*;
import kyodai.*;
import java.awt.*;
import java.util.*;

public class Path extends ArrayList<Point>{
	static final int INFINITE = 999;
	static int minCross = INFINITE;
	private static Path[] paths = new Path[] {
		new Path(), new Path(), new Path()
	};
	//checkP2决定是否对(x2,y2)进行检查
	private static boolean xCourse(int x1, int y1, int x2, int y2, Path path, boolean checkP2) {
		path.clear();
		Square[][] mapGrid = Map.getMap();
		if( y1!=y2 || x1==x2 
				|| (checkP2 && Map.inXBound(x2) && Map.inYBound(y2) && mapGrid[y2][x2].isExist()) )
			return false;
		int direction = x1<x2? 1 : -1;
		for(int x=x1+direction;x!=x2; x+=direction)
			if(Map.inYBound(y1) && mapGrid[y1][x].isExist())
				return false;
			else 
				path.add(new Point(x,y1));
		if(checkP2)
			path.add(new Point(x2,y2));
		return true;
	}
	private static boolean yCourse(int x1, int y1, int x2, int y2, Path path, boolean checkP2) {
		path.clear();
		Square[][] mapGrid = Map.getMap();
		if( x1!=x2||y1==y2 
				|| (checkP2 && Map.inXBound(x2) && Map.inYBound(y2) && mapGrid[y2][x2].isExist()) )
			return false;
		int direction = y1<y2? 1 : -1;
		for(int y=y1+direction; y!=y2; y+=direction)
			if(Map.inXBound(x1) && mapGrid[y][x1].isExist()) 
				return false;
			else 
				path.add(new Point(x1,y));
		if(checkP2)
			path.add(new Point(x2,y2));
		return true;
	}
	static int explore(Square start, Square end) {
		Square[][] mapGrid = Map.getMap();
		int x1 = start.getX();
		int y1 = start.getY();
		int x2 = end.getX();
		int y2 = end.getY();
		if( start.getID()==end.getID() && start.isExist()&&end.isExist() ) {
			if(xCourse(x1, y1, x2, y2, paths[0], false) || yCourse(x1, y1, x2, y2, paths[0], false))
				return 0;
			if( ( xCourse(x1, y1, x2, y1, paths[0], true) && yCourse(x2, y1, x2, y2, paths[1], false) )   
					|| ( yCourse(x1, y1, x1, y2, paths[0], true) && xCourse(x1, y2, x2, y2, paths[1], false) ) )
				return 1;
			//vertical
			for(int y=-1;y<=Map.YLEN;y++)
				if( (y!=y1&&y!=y2) 
						&& yCourse(x1, y1, x1, y, paths[0], true) 
						&& xCourse(x1, y, x2, y, paths[1], true) 
						&& yCourse(x2, y, x2, y2, paths[2], false) )
					return 2;
			//horizontal
			for(int x=-1;x<=Map.XLEN;x++)
				if( (x!=x1&&x!=x2) 
						&& xCourse(x1, y1, x, y1, paths[0], true) 
						&& yCourse(x, y1, x, y2, paths[1], true) 
						&& xCourse(x, y2, x2, y2, paths[2], false) )
					return 2;
		}
		return INFINITE;
	}
	static boolean isExist() {
		Square[][] mapGrid = Map.getMap();
		for(int y=0;y<Map.YLEN;y++)
			for(int x=0;x<Map.XLEN;x++)
				for(int i=y;i<Map.YLEN;i++)
					for(int j=x+1;j<Map.XLEN;j++)
						if(explore(mapGrid[y][x], mapGrid[i][j])!=INFINITE )
							return true;
		return false;
	}
	static Path[] getPaths() {
		return paths;
	}
	static void drawPath(int minCross, Graphics g) {
		for(int i=0;i<=minCross;i++)
		{
			Path path = Path.getPaths()[i]; 
			for(int j=0;j<path.size();j++)
			{
				Point p = path.get(j);
				int x = (int)p.getX();
				int y = (int)p.getY();
				Image img = ImgFactory.frameImages[4];
				int drawX = X0+GAP+x*(W+GAP)+(W-img.getWidth(null))/2;
				int drawY = Y0+GAP+y*(H+GAP)+(H-img.getHeight(null))/2;
				g.drawImage(img, drawX, drawY, null);
			}
		}
		Path.minCross = Path.INFINITE;
		try{
			Thread.sleep(300);
		}catch(Exception e) {}
		KyodaiGame.refresh();
	}
}
