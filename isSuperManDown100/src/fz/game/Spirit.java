package fz.game;

import fz.math.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * 这是一个用于表示精灵的类。实现了部分精灵属性，如：位置、速度、图片、绘制<br/>
 * getLocation()返回的是spirit图片的中心点，也即精灵移动、绘制的参照原点<br/>
 * 
 * @author 方正制造
 */
public class Spirit extends Animal implements Cloneable {
	private MyVector aV;
	private Image m_img;

	protected Spirit() {
		aV = new MyVector();
	}

	protected Spirit(Image img) {
		this();
		m_img = img;
	}

	public Spirit(int x, int y, MyVector v, Image img) {
		super(x, y, v);
		aV = new MyVector();
		m_img = img;
	}

	/**
	 * 将图片设置为img
	 */
	public void setImage(Image img) {
		m_img = img;
	}

	/**
	 * 返回图片
	 * 
	 * @return 图片
	 */
	public Image getImage() {
		return m_img;
	}

	/**
	 * 返回spirit图片的宽，未对空指针情况进行考虑
	 */
	public int getWidth() {
		return m_img.getWidth(null);
	}

	/**
	 * 返回spirit图片的高，未对空指针情况进行考虑
	 */
	public int getHeight() {
		return m_img.getHeight(null);
	}

	/**
	 * 返回此spirit的绘图坐标
	 * 
	 * @return 此spirit的绘图坐标
	 */
	public Point getDrawPoint() {
		return new Point(getX() - m_img.getWidth(null) / 2, getY()
				- m_img.getHeight(null)/2);
	}

	/**
	 * 设置速度的x分量
	 */
	public void setVx(double x) {
		v.setX(x);
	}

	/**
	 * 设置速度的y分量
	 */
	public void setVy(double y) {
		v.setY(y);
	}

	/**
	 * 使用(dX, dY)所代表的增量来改变速度，如：调用前V=(4, 1) 调用deltaV(-1, 1)后V=(3, 2)
	 */
	public void deltaV(double dX, double dY) {
		setV(v.getX() + dX, v.getY() + dY);
	}
	/**
	 * 使用向量vec来改变速度，如：调用前V=(4, 1) 调用deltaV(new MyVector(-1, 1))后V=(3, 2)
	 */
	public void deltaV(MyVector vec) {
		setV(v.getX() + vec.getX(), v.getY() + vec.getY());
	}

	/**
	 * 返回此spirit的加速度
	 * 
	 * @return 此spirit的加速度
	 */
	public MyVector getAv() {
		return aV;
	}

	/**
	 * 将此spirit的加速度设置为指定的double表示
	 */
	public void setAv(double x, double y) {
		aV.setX(x);
		aV.setY(y);
	}

	/**
	 * 将此spirit的加速度设置为指定的Vector
	 */
	public void setAv(MyVector vec) {
		aV = vec;
	}

	/**
	 * 以getDrawPoint()所返回的点为左上角绘制此spirit<br/>
	 */
	public void draw(Graphics g) {
		g.drawImage(m_img, (int) getDrawPoint().getX(),	(int) getDrawPoint().getY(), null);
	}

	@Override
	public Object clone() {
		Spirit buf = new Spirit(x, y, v, m_img);
		buf.setAv((MyVector) getAv().clone());
		return buf;
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder(super.toString());
		output.append("   A : ");
		output.append(aV.toString());
		return output.toString();
	}

	public static void main(String[] args) {
		Spirit s = new Spirit();
		System.out.println(s);
	}
}
