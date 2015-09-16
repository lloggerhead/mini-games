package kyodai;

import static kyodai.DrawBoard.GAP;
import static kyodai.DrawBoard.H;
import static kyodai.DrawBoard.W;
import static kyodai.DrawBoard.X0;
import static kyodai.DrawBoard.Y0;
import kyodai.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

class MouseProcess extends MouseAdapter {
	@Override
	public void mousePressed(MouseEvent e) {
		Point p = e.getPoint();
		int dX = (int)p.getX()-DrawBoard.X0; 
		int dY = (int)p.getY()-DrawBoard.Y0; 
		if( !(dX<0 || dY<0 
				|| dX>Map.XLEN*(DrawBoard.W+DrawBoard.GAP)+DrawBoard.GAP  
				|| dY>Map.YLEN*(DrawBoard.H+DrawBoard.GAP)+DrawBoard.GAP) )
		{
			int x = dX/(DrawBoard.W+DrawBoard.GAP);
			int y = dY/(DrawBoard.H+DrawBoard.GAP);
			Path.minCross = Control.vanish(Map.getMap()[y][x]);  
		}
		
		if(!Path.isExist())
			Control.shuffle();
		if(Control.isWin())
			Control.win();
	}
}

interface MenuProcess {
	ActionListener help = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "完成时间：2012/8/13", "关于", JOptionPane.INFORMATION_MESSAGE);
		}
	};
	ActionListener bomb = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Control.bomb();
		}
	};
	ActionListener start = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Control.restart();
		}
	};
	ActionListener shuffle = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Control.shuffle();
		}
	};
	ActionListener exit = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	};
}

class DrawBoard extends JPanel {
	static final int W = ImgFactory.images[0].getWidth(null);
	static final int H = ImgFactory.images[0].getHeight(null);
	static final int GAP = 2;
	static int X0 ;
	static int Y0 ;
	DrawBoard() {
		addMouseListener(new MouseProcess());
	}
	void drawMap(Graphics g) {
		Square[][] mapGrid = Map.getMap();
		for(int y=0;y<Map.YLEN;y++)
			for(int x=0;x<Map.XLEN;x++)
				if(mapGrid[y][x].isExist())
					g.drawImage(mapGrid[y][x].getImg(), X0+GAP+x*(W+GAP), Y0+GAP+y*(H+GAP), null);
	}
	void drawGridline(Square sq, Graphics g) {
		if(sq==null || !sq.isExist())
		{
			KyodaiGame.refresh();
			return;
		}
		int x1 = X0+1+sq.getX()*(W+GAP);
		int y1 = Y0+1+sq.getY()*(H+GAP);
		int x2 = X0+sq.getX()*(W+GAP);
		int y2 = Y0+sq.getY()*(H+GAP);
		g.setColor(Color.RED);
		g.drawLine(x1, y1, x1, y1+H+GAP);
		g.drawLine(x1, y1, x1+W+GAP, y1);
		g.drawLine(x1+W+GAP, y1, x1+W+GAP, y1+H+GAP);
		g.drawLine(x1, y1+H+GAP, x1+W+GAP, y1+H+GAP);
		g.drawLine(x2, y2, x2, y2+H+GAP*2);
		g.drawLine(x2, y2, x2+W+GAP*2, y2);
		g.drawLine(x2+W+GAP*2, y2, x2+W+GAP*2, y2+H+GAP*2);
		g.drawLine(x2, y2+H+GAP*2, x2+W+GAP*2, y2+H+GAP*2);
	}
	@Override
	public void paintComponent(Graphics g){
		X0 = ( getWidth()-Map.XLEN*(W+GAP)-GAP )/2;
		Y0 = ( getHeight()-Map.YLEN*(H+GAP)-GAP )/2;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		drawMap(g); 
		drawGridline(Control.lastSq, g);
	}
}

public class KyodaiGame extends JFrame{
	static final int WIDTH = 650;
	static final int HEIGHT = 520;
	private static DrawBoard gameBoard = new DrawBoard();
	private JMenu[] menus = {
			new JMenu("游戏(G)"), new JMenu("帮助(H)")
	};
	private JMenuItem[] items = {
			new JMenuItem("开始", KeyEvent.VK_F1), new JMenuItem("随机", KeyEvent.VK_F5), new JMenuItem("炸弹", KeyEvent.VK_F4),
			new JMenuItem("退出", KeyEvent.VK_ESCAPE), new JMenuItem("关于")
	};
	KyodaiGame() {
		items[0].addActionListener(MenuProcess.start);
		items[1].addActionListener(MenuProcess.shuffle);
		items[2].addActionListener(MenuProcess.bomb);
		items[3].addActionListener(MenuProcess.exit);
		items[4].addActionListener(MenuProcess.help);
		items[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		items[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
		items[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0));
		items[3].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0));
		for(int i=0;i<items.length-1;i++)
			menus[0].add(items[i]);
		menus[1].add(items[4]);
		menus[0].setMnemonic(KeyEvent.VK_G);
		menus[1].setMnemonic(KeyEvent.VK_H);
		JMenuBar mb = new JMenuBar();
		for(int i=0;i<menus.length;i++)
			mb.add(menus[i]);
		setJMenuBar(mb);
		setSize(WIDTH, HEIGHT);
		add(gameBoard, BorderLayout.CENTER);
		setResizable(false);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("连连看—简版");
	}
	static Graphics getBrush() {
		return gameBoard.getGraphics();
	}
	static void refresh() {
		gameBoard.repaint();
	}
	public static void main(String[] args) {
		new KyodaiGame();
	}
}
