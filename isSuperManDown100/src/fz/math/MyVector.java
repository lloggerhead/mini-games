package fz.math;

import static java.lang.Math.*;
import java.awt.geom.Point2D;

/**
 * 平面向量，即向量的 模 与 弧度 是根据坐标算出来的近似值<br/>
 * 向量角度的定义为：与x正半轴逆时针旋转增大，重合时为0，且无论怎么操作，其取值范围是[-90,270]<br/>
 * 所有的方法若未另外说明，均不改变向量起点<br/>
 * 所有对属性的操作若未通过此类提供的方法进行，都将导致不安全的行为，如：<br/>
 * 			Vector vec = new Vector(0,1);<br/>
 * 			vec.getP1().setLocation(-1,0);<br/>
 * 	其属性的改变是未知的<br/>
 * @author 方正制造
 */
public class MyVector implements Cloneable {
	// 与x正半轴夹角
	private double angrad; // 弧度
	// 范数，即向量的模
	private double norm;
	// 起点
	private Point2D p1;
	// 终点
	private Point2D p2;

	/**
	 * 零向量
	 */
	public MyVector() {
		set(0, 0, 0, 0);
	}

	/**
	 * 起点为(0,0)，终点为(x,y)的向量
	 */
	public MyVector(double x, double y) {
		set(0, 0, x, y);
	}

	/**
	 * 起点为(x1,y1)，终点为(x2,y2)的向量
	 */
	public MyVector(double x1, double y1, double x2, double y2) {
		set(x1, y1, x2, y2);
	}

	/**
	 * 起点为start，终点为end的向量
	 */
	public MyVector(Point2D start, Point2D end) {
		set(start.getX(), start.getY(), end.getX(), end.getY());
	}

	/**
	 * 返回此向量的模
	 * 
	 * @return 向量的模
	 * @see #getLength()
	 */
	public double getNorm() {
		return norm;
	}

	/**
	 * 返回此向量的模
	 * 
	 * @return 向量的模 和getNorm没任何区别<br/>
	 *         仅为适应使用习惯而提供
	 * @see #getNorm()
	 */
	public double getLength() {
		return getNorm();
	}

	/**
	 * 返回此向量用弧度测量的方向角
	 * 
	 * @return 用弧度测量的方向角
	 * @see #getAngle()
	 */
	public double getAngrad() {
		return angrad;
	}

	/**
	 * 返回此向量用度数测量的方向角
	 * 
	 * @return 用度数测量的方向角
	 * @see #getAngrad()
	 */
	public double getAngle() {
		return toDegrees(angrad);
	}

	/**
	 * 返回此向量在x轴上的投影（区分正负）
	 * 
	 * @return 向量在x轴上的投影（区分正负）
	 * @see #getY()
	 */
	public double getX() {
		return p2.getX() - p1.getX();
	}

	/**
	 * 返回此向量在y轴上的投影（区分正负）
	 * 
	 * @return 向量在y轴上的投影（区分正负）
	 * @see #getX()
	 */
	public double getY() {
		return p2.getY() - p1.getY();
	}

	/**
	 * 返回此向量起点
	 * 
	 * @return 向量起点
	 * @see #getP2()
	 */
	public Point2D getP1() {
		return p1;
	}

	/**
	 * 返回此向量终点
	 * 
	 * @return 向量终点
	 * @see #getP1()
	 */
	public Point2D getP2() {
		return p2;
	}

	/**
	 * 判断对象与vec是否平行
	 * 
	 * @return 如果此向量与 vec 平行，则返回 true；否则返回 false。
	 */
	public boolean isParallel(MyVector vec) {
		return Util.equals((getAngle() - vec.getAngle()) % 180, 0);
	}

	/**
	 * 判断对象与vec是否相同（不同于equals）
	 * 
	 * @return 如果此向量与 vec 是同一个向量，则返回 true；否则返回 false。
	 * @see #equals(Object)
	 */
	public boolean isSame(MyVector vec) {
		return getP1().equals(vec.getP1()) && equals(vec);
	}

	/**
	 * 将对象平移到p点处
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
	 * 将对象平移到(x,y)处
	 * 
	 * @see #moveTo(Point2D)
	 */
	public void moveTo(double x, double y) {
		Point2D p = new Point2D.Double(x, y);
		moveTo(p);
	}

	/**
	 * 设置起点为(x1,y1)， 终点为(x2,y2)
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
	 * 设置起点为p1， 终点为p2
	 * 
	 * @see #set(double , double )
	 */
	public void set(Point2D p1, Point2D p2) {
		set(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	/**
	 * 设置对象的模为length，以度数测量的角度为angle
	 * 
	 * @see #set(double x1, double y1, double x2, double y2)
	 */
	public void set(double length, double angle) {
		setNorm(length);
		setAngle(angle);
	}

	/**
	 * 设置对象在x轴上的投影为x（有正负）
	 * 
	 * @see #setY(double)
	 */
	public void setX(double x) {
		p2.setLocation(p1.getX() + x, p2.getY());
		set(p1, p2);
	}

	/**
	 * 设置对象在y轴上的投影为y（有正负）
	 * 
	 * @see #setX(double)
	 */
	public void setY(double y) {
		p2.setLocation(p2.getX(), p1.getY() + y);
		set(p1, p2);
	}

	/**
	 * 设置对象起点为p
	 * 
	 * @see #setP2(Point2D)
	 */
	public void setP1(Point2D p) {
		set(p, p2);
	}

	/**
	 * 设置对象终点为p
	 * 
	 * @see #setP1(Point2D)
	 */
	public void setP2(Point2D p) {
		set(p1, p);
	}

	/**
	 * 改变向量的模<br/>
	 * 当length<0时，对象反向
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
	 * 改变向量的长度<br/>
	 * 当length<0时，对象反向<br/>
	 * 和setNorm没任何区别<br/>
	 * 是仅为适应使用习惯而提供的方法
	 * 
	 * @see #setNorm(double)
	 */
	public void setLength(double length) {
		setNorm(length);
	}

	/**
	 * 改变向量的角度为angdeg
	 * 
	 * @param angdeg
	 *            用度数测量的方向角
	 */
	public void setAngle(double angdeg) {
		double x1 = p1.getX();
		double y1 = p1.getY();
		double x2 = x1 + norm * cos(toRadians(angdeg));
		double y2 = y1 + norm * sin(toRadians(angdeg));
		set(x1, y1, x2, y2);
	}

	/**
	 * 返回此向量的单位向量
	 * 
	 * @return 此向量的单位向量
	 */
	public MyVector unitVector() {
		MyVector vec = (MyVector) clone();
		vec.setNorm(1);
		return vec;
	}

	/**
	 * 返回与此向量方向相反的向量
	 * 
	 * @return 与向量方向相反的向量
	 */
	public MyVector reverseVector() {
		MyVector vec = (MyVector) clone();
		vec.setAngle(180 + getAngle());
		return vec;
	}

	/**
	 * 返回此向量与vec相加后的向量，起点同此向量
	 * 
	 * @param vec
	 *            参与运算的向量
	 * @return 此向量与vec相加后的向量，起点同此向量
	 */
	public MyVector add(MyVector vec) {
		MyVector temp = (MyVector) clone();
		temp.setX(getX() + vec.getX());
		temp.setY(getY() + vec.getY());
		return temp;
	}

	/**
	 * 返回此向量与vec相减后的向量，起点同此向量
	 * 
	 * @param vec
	 *            参与运算的向量
	 * @return 此向量与vec相减后的向量，起点同此向量
	 */
	public MyVector sub(MyVector vec) {
		return add(vec.reverseVector());
	}

	/**
	 * 返回此向量与number相乘后的向量
	 * 
	 * @return 返回此向量与number相乘后的向量
	 */
	public MyVector mul(double number) {
		setNorm(number * norm);
		return this;
	}

	/**
	 * 返回此向量与vec用弧度测量的夹角，范围[0, PI)
	 * 
	 * @return 此向量与vec用弧度测量的夹角
	 */
	public double diffAngrad(MyVector vec) {
		return abs(getAngrad() - vec.getAngrad()) % PI;
	}

	/**
	 * 返回此向量与vec的数量积
	 * 
	 * @param vec
	 *            参与运算的向量
	 * @return 此向量与vec的数量积
	 */
	public double innerProduct(MyVector vec) {
		return getNorm() * vec.getNorm() * cos(diffAngrad(vec));
	}

	/**
	 * 此向量与vec的向量积的模
	 * 
	 * @param vec
	 *            参与运算的向量
	 * @return 此向量与vec的向量积的模
	 */
	public double vectorProduct(MyVector vec) {
		return getNorm() * vec.getNorm() * sin(diffAngrad(vec));
	}

	/**
	 * 返回此向量沿着vec方向分解后的向量<br/>
	 * DirDec意为direction decomposition
	 * 
	 * @return 此向量沿着vec方向分解后的向量
	 */
	public MyVector parallelDirDec(MyVector vec) {
		MyVector temp = vec.unitVector();
		temp.setNorm(getNorm() * cos(diffAngrad(vec)));
		return temp;
	}

	/**
	 * 返回此向量沿着vec垂直方向分解后的向量， 垂直方向是根据vec逆时针旋转所确定<br/>
	 * DirDec意为direction decomposition
	 * 
	 * @return 此向量沿着vec垂直方向分解后的向量
	 */
	public MyVector verticalDirDec(MyVector vec) {
		MyVector temp = (MyVector) vec.clone();
		temp.setAngle(vec.getAngle() + 90);
		temp.setNorm(getNorm() * cos(diffAngrad(temp)));
		return temp;
	}

	/**
	 * 返回此向量经反射后得到的向量， 对象以入射方式进行反射
	 * 
	 * @param reflexVec
	 *            无关方向的镜面向量
	 * @return 此向量经反射后得到的向量
	 */
	public MyVector reflexVector(MyVector reflexVec) {
		MyVector tempP = parallelDirDec(reflexVec);
		MyVector tempV = verticalDirDec(reflexVec);
		return tempV.reverseVector().add(tempP);
	}

	/**
	 * 当且仅当此向量与向量vec模、方向角相等时,相等成立<br/>
	 * 相等判断使用的Util.equals
	 * 
	 * @see #isSame(MyVector)
	 * @return 如果此向量与向量vec相等（不完全相等），则返回 true；否则返回 false。
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
