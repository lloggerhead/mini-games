package fz.game;

import fz.math.*;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/**
 * ����һ�����ڱ�ʾ������ࡣʵ���˲��־������ԣ��磺λ�á��ٶȡ�ͼƬ������<br/>
 * getLocation()���ص���spiritͼƬ�����ĵ㣬Ҳ�������ƶ������ƵĲ���ԭ��<br/>
 * 
 * @author ��������
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
	 * ��ͼƬ����Ϊimg
	 */
	public void setImage(Image img) {
		m_img = img;
	}

	/**
	 * ����ͼƬ
	 * 
	 * @return ͼƬ
	 */
	public Image getImage() {
		return m_img;
	}

	/**
	 * ����spiritͼƬ�Ŀ�δ�Կ�ָ��������п���
	 */
	public int getWidth() {
		return m_img.getWidth(null);
	}

	/**
	 * ����spiritͼƬ�ĸߣ�δ�Կ�ָ��������п���
	 */
	public int getHeight() {
		return m_img.getHeight(null);
	}

	/**
	 * ���ش�spirit�Ļ�ͼ����
	 * 
	 * @return ��spirit�Ļ�ͼ����
	 */
	public Point getDrawPoint() {
		return new Point(getX() - m_img.getWidth(null) / 2, getY()
				- m_img.getHeight(null)/2);
	}

	/**
	 * �����ٶȵ�x����
	 */
	public void setVx(double x) {
		v.setX(x);
	}

	/**
	 * �����ٶȵ�y����
	 */
	public void setVy(double y) {
		v.setY(y);
	}

	/**
	 * ʹ��(dX, dY)��������������ı��ٶȣ��磺����ǰV=(4, 1) ����deltaV(-1, 1)��V=(3, 2)
	 */
	public void deltaV(double dX, double dY) {
		setV(v.getX() + dX, v.getY() + dY);
	}
	/**
	 * ʹ������vec���ı��ٶȣ��磺����ǰV=(4, 1) ����deltaV(new MyVector(-1, 1))��V=(3, 2)
	 */
	public void deltaV(MyVector vec) {
		setV(v.getX() + vec.getX(), v.getY() + vec.getY());
	}

	/**
	 * ���ش�spirit�ļ��ٶ�
	 * 
	 * @return ��spirit�ļ��ٶ�
	 */
	public MyVector getAv() {
		return aV;
	}

	/**
	 * ����spirit�ļ��ٶ�����Ϊָ����double��ʾ
	 */
	public void setAv(double x, double y) {
		aV.setX(x);
		aV.setY(y);
	}

	/**
	 * ����spirit�ļ��ٶ�����Ϊָ����Vector
	 */
	public void setAv(MyVector vec) {
		aV = vec;
	}

	/**
	 * ��getDrawPoint()�����صĵ�Ϊ���Ͻǻ��ƴ�spirit<br/>
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
