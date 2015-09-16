package fz.math;

import static java.lang.Math.*;
import java.awt.geom.Point2D;

/**
 * ƽ���������������� ģ �� ���� �Ǹ�������������Ľ���ֵ<br/>
 * �����ǶȵĶ���Ϊ����x��������ʱ����ת�����غ�ʱΪ0����������ô��������ȡֵ��Χ��[-90,270]<br/>
 * ���еķ�����δ����˵���������ı��������<br/>
 * ���ж����ԵĲ�����δͨ�������ṩ�ķ������У��������²���ȫ����Ϊ���磺<br/>
 * 			Vector vec = new Vector(0,1);<br/>
 * 			vec.getP1().setLocation(-1,0);<br/>
 * 	�����Եĸı���δ֪��<br/>
 * @author ��������
 */
public class MyVector implements Cloneable {
	// ��x������н�
	private double angrad; // ����
	// ��������������ģ
	private double norm;
	// ���
	private Point2D p1;
	// �յ�
	private Point2D p2;

	/**
	 * ������
	 */
	public MyVector() {
		set(0, 0, 0, 0);
	}

	/**
	 * ���Ϊ(0,0)���յ�Ϊ(x,y)������
	 */
	public MyVector(double x, double y) {
		set(0, 0, x, y);
	}

	/**
	 * ���Ϊ(x1,y1)���յ�Ϊ(x2,y2)������
	 */
	public MyVector(double x1, double y1, double x2, double y2) {
		set(x1, y1, x2, y2);
	}

	/**
	 * ���Ϊstart���յ�Ϊend������
	 */
	public MyVector(Point2D start, Point2D end) {
		set(start.getX(), start.getY(), end.getX(), end.getY());
	}

	/**
	 * ���ش�������ģ
	 * 
	 * @return ������ģ
	 * @see #getLength()
	 */
	public double getNorm() {
		return norm;
	}

	/**
	 * ���ش�������ģ
	 * 
	 * @return ������ģ ��getNormû�κ�����<br/>
	 *         ��Ϊ��Ӧʹ��ϰ�߶��ṩ
	 * @see #getNorm()
	 */
	public double getLength() {
		return getNorm();
	}

	/**
	 * ���ش������û��Ȳ����ķ����
	 * 
	 * @return �û��Ȳ����ķ����
	 * @see #getAngle()
	 */
	public double getAngrad() {
		return angrad;
	}

	/**
	 * ���ش������ö��������ķ����
	 * 
	 * @return �ö��������ķ����
	 * @see #getAngrad()
	 */
	public double getAngle() {
		return toDegrees(angrad);
	}

	/**
	 * ���ش�������x���ϵ�ͶӰ������������
	 * 
	 * @return ������x���ϵ�ͶӰ������������
	 * @see #getY()
	 */
	public double getX() {
		return p2.getX() - p1.getX();
	}

	/**
	 * ���ش�������y���ϵ�ͶӰ������������
	 * 
	 * @return ������y���ϵ�ͶӰ������������
	 * @see #getX()
	 */
	public double getY() {
		return p2.getY() - p1.getY();
	}

	/**
	 * ���ش��������
	 * 
	 * @return �������
	 * @see #getP2()
	 */
	public Point2D getP1() {
		return p1;
	}

	/**
	 * ���ش������յ�
	 * 
	 * @return �����յ�
	 * @see #getP1()
	 */
	public Point2D getP2() {
		return p2;
	}

	/**
	 * �ж϶�����vec�Ƿ�ƽ��
	 * 
	 * @return ����������� vec ƽ�У��򷵻� true�����򷵻� false��
	 */
	public boolean isParallel(MyVector vec) {
		return Util.equals((getAngle() - vec.getAngle()) % 180, 0);
	}

	/**
	 * �ж϶�����vec�Ƿ���ͬ����ͬ��equals��
	 * 
	 * @return ����������� vec ��ͬһ���������򷵻� true�����򷵻� false��
	 * @see #equals(Object)
	 */
	public boolean isSame(MyVector vec) {
		return getP1().equals(vec.getP1()) && equals(vec);
	}

	/**
	 * ������ƽ�Ƶ�p�㴦
	 * 
	 * @see #moveTo(double, double)
	 */
	public void moveTo(Point2D p) {
		double x = getX();
		double y = getY();
		set(p, p2);
		setX(x);
		setY(y);
	}

	/**
	 * ������ƽ�Ƶ�(x,y)��
	 * 
	 * @see #moveTo(Point2D)
	 */
	public void moveTo(double x, double y) {
		Point2D p = new Point2D.Double(x, y);
		moveTo(p);
	}

	/**
	 * �������Ϊ(x1,y1)�� �յ�Ϊ(x2,y2)
	 * 
	 * @see #set(double ,double )
	 */
	public void set(double x1, double y1, double x2, double y2) {
		norm = hypot(x1 - x2, y1 - y2);
		if (x1 == x2 && y1 == y2)
			angrad = 0;
		else {
			angrad = atan((y2 - y1) / (x2 - x1));
			if (x2 < x1)
				angrad += PI;
		}
		p1 = new Point2D.Double(x1, y1);
		p2 = new Point2D.Double(x2, y2);
	}

	/**
	 * �������Ϊp1�� �յ�Ϊp2
	 * 
	 * @see #set(double , double )
	 */
	public void set(Point2D p1, Point2D p2) {
		set(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/**
	 * ���ö����ģΪlength���Զ��������ĽǶ�Ϊangle
	 * 
	 * @see #set(double x1, double y1, double x2, double y2)
	 */
	public void set(double length, double angle) {
		setNorm(length);
		setAngle(angle);
	}

	/**
	 * ���ö�����x���ϵ�ͶӰΪx����������
	 * 
	 * @see #setY(double)
	 */
	public void setX(double x) {
		p2.setLocation(p1.getX() + x, p2.getY());
		set(p1, p2);
	}

	/**
	 * ���ö�����y���ϵ�ͶӰΪy����������
	 * 
	 * @see #setX(double)
	 */
	public void setY(double y) {
		p2.setLocation(p2.getX(), p1.getY() + y);
		set(p1, p2);
	}

	/**
	 * ���ö������Ϊp
	 * 
	 * @see #setP2(Point2D)
	 */
	public void setP1(Point2D p) {
		set(p, p2);
	}

	/**
	 * ���ö����յ�Ϊp
	 * 
	 * @see #setP1(Point2D)
	 */
	public void setP2(Point2D p) {
		set(p1, p);
	}

	/**
	 * �ı�������ģ<br/>
	 * ��length<0ʱ��������
	 * 
	 * @see #setLength(double)
	 */
	public void setNorm(double norm) {
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = x1 + norm * cos(angrad);
		double y2 = y1 + norm * sin(angrad);
		set(x1, y1, x2, y2);
	}

	/**
	 * �ı������ĳ���<br/>
	 * ��length<0ʱ��������<br/>
	 * ��setNormû�κ�����<br/>
	 * �ǽ�Ϊ��Ӧʹ��ϰ�߶��ṩ�ķ���
	 * 
	 * @see #setNorm(double)
	 */
	public void setLength(double length) {
		setNorm(length);
	}

	/**
	 * �ı������ĽǶ�Ϊangdeg
	 * 
	 * @param angdeg
	 *            �ö��������ķ����
	 */
	public void setAngle(double angdeg) {
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = x1 + norm * cos(toRadians(angdeg));
		double y2 = y1 + norm * sin(toRadians(angdeg));
		set(x1, y1, x2, y2);
	}

	/**
	 * ���ش������ĵ�λ����
	 * 
	 * @return �������ĵ�λ����
	 */
	public MyVector unitVector() {
		MyVector vec = (MyVector) clone();
		vec.setNorm(1);
		return vec;
	}

	/**
	 * ����������������෴������
	 * 
	 * @return �����������෴������
	 */
	public MyVector reverseVector() {
		MyVector vec = (MyVector) clone();
		vec.setAngle(180 + getAngle());
		return vec;
	}

	/**
	 * ���ش�������vec��Ӻ�����������ͬ������
	 * 
	 * @param vec
	 *            �������������
	 * @return ��������vec��Ӻ�����������ͬ������
	 */
	public MyVector add(MyVector vec) {
		MyVector temp = (MyVector) clone();
		temp.setX(getX() + vec.getX());
		temp.setY(getY() + vec.getY());
		return temp;
	}

	/**
	 * ���ش�������vec���������������ͬ������
	 * 
	 * @param vec
	 *            �������������
	 * @return ��������vec���������������ͬ������
	 */
	public MyVector sub(MyVector vec) {
		return add(vec.reverseVector());
	}

	/**
	 * ���ش�������number��˺������
	 * 
	 * @return ���ش�������number��˺������
	 */
	public MyVector mul(double number) {
		setNorm(number * norm);
		return this;
	}

	/**
	 * ���ش�������vec�û��Ȳ����ļнǣ���Χ[0, PI)
	 * 
	 * @return ��������vec�û��Ȳ����ļн�
	 */
	public double diffAngrad(MyVector vec) {
		return abs(getAngrad() - vec.getAngrad()) % PI;
	}

	/**
	 * ���ش�������vec��������
	 * 
	 * @param vec
	 *            �������������
	 * @return ��������vec��������
	 */
	public double innerProduct(MyVector vec) {
		return getNorm() * vec.getNorm() * cos(diffAngrad(vec));
	}

	/**
	 * ��������vec����������ģ
	 * 
	 * @param vec
	 *            �������������
	 * @return ��������vec����������ģ
	 */
	public double vectorProduct(MyVector vec) {
		return getNorm() * vec.getNorm() * sin(diffAngrad(vec));
	}

	/**
	 * ���ش���������vec����ֽ�������<br/>
	 * DirDec��Ϊdirection decomposition
	 * 
	 * @return ����������vec����ֽ�������
	 */
	public MyVector parallelDirDec(MyVector vec) {
		MyVector temp = vec.unitVector();
		temp.setNorm(getNorm() * cos(diffAngrad(vec)));
		return temp;
	}

	/**
	 * ���ش���������vec��ֱ����ֽ��������� ��ֱ�����Ǹ���vec��ʱ����ת��ȷ��<br/>
	 * DirDec��Ϊdirection decomposition
	 * 
	 * @return ����������vec��ֱ����ֽ�������
	 */
	public MyVector verticalDirDec(MyVector vec) {
		MyVector temp = (MyVector) vec.clone();
		temp.setAngle(vec.getAngle() + 90);
		temp.setNorm(getNorm() * cos(diffAngrad(temp)));
		return temp;
	}

	/**
	 * ���ش������������õ��������� ���������䷽ʽ���з���
	 * 
	 * @param reflexVec
	 *            �޹ط���ľ�������
	 * @return �������������õ�������
	 */
	public MyVector reflexVector(MyVector reflexVec) {
		MyVector tempP = parallelDirDec(reflexVec);
		MyVector tempV = verticalDirDec(reflexVec);
		return tempV.reverseVector().add(tempP);
	}

	/**
	 * ���ҽ���������������vecģ����������ʱ,��ȳ���<br/>
	 * ����ж�ʹ�õ�Util.equals
	 * 
	 * @see #isSame(MyVector)
	 * @return ���������������vec��ȣ�����ȫ��ȣ����򷵻� true�����򷵻� false��
	 */
	public boolean equals(Object vec) {
		return Util.equals(getNorm(), ((MyVector) vec).getNorm())
				&& Util.equals((getAngle() - ((MyVector) vec).getAngle() % 360),
						0);
	}

	public String toString() {
		StringBuilder output = new StringBuilder("(length, angle)=[");
		output.append(String.format("%.2f", getNorm()));
		output.append(", ");
		output.append(String.format("%.1f", getAngle()));
		output.append("]--(");
		output.append(String.format("%.1f", p1.getX()));
		output.append(", ");
		output.append(String.format("%.1f", p1.getY()));
		output.append(")->(");
		output.append(String.format("%.1f", p2.getX()));
		output.append(", ");
		output.append(String.format("%.1f", p2.getY()));
		output.append(")");
		return output.toString();
	}

	public MyVector clone() {
		MyVector vec = new MyVector();
		vec.set(p1, p2);
		return vec;
	}
	public static void main(String[] agrs) {
		System.out.println(new MyVector(1,1));
	}
}
