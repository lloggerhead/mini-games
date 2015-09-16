package fz.game;

import fz.math.MyVector;

public class Animal implements Cloneable {
	protected MyVector v;
	protected int x, y;

	protected Animal() {
		x = y = 0;
		v = new MyVector();
	}

	/**
	 * v将被clone后使用，而不会共享
	 */
	public Animal(int x, int y, MyVector v) {
		this.x = x;
		this.y = y;
		this.v = v;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public MyVector getV() {
		return v;
	}

	public int getVx() {
		return (int) v.getX();
	}

	public int getVy() {
		return (int) v.getY();
	}

	public void setV(double x, double y) {
		v.setX(x);
		v.setY(y);
	}

	public void setV(MyVector v) {
		this.v = v;
	}

	@Override
	public Object clone() {
		return new Animal(x, y, v.clone());
	}

	@Override
	public String toString() {
		StringBuilder output = new StringBuilder("Location=(");
		output.append(String.format("%d", x));
		output.append(", ");
		output.append(String.format("%d", y));
		output.append(")  V : ");
		output.append(v.toString());
		return output.toString();
	}

	public static void main(String[] args) {
		System.out.println(new Animal(3, 2, new MyVector(1, 2).clone()));
	}
}
