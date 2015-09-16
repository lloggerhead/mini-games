package fz.math;

import static java.lang.Math.*;

/**
 * @author 方正制造
 */
public class Util {
	/**
	 * 无解标识符，其值==Double.MAX_VALUE
	 */
	public static final double NO_Solution = java.lang.Double.NaN;
	/**
	 * 浮点数近似零，其值==10e-3
	 */
	public static final double ZERO = 10e-3;

	/**
	 * 判断d1，d2，...，dn是否近似全相等，
	 * 相等判断精度为ZERO
	 * @return d1==d2==d3==...==dn
	 * @see #equals(int, int...)
	 * @see #ZERO
	 */
	public static boolean equals(double d1, double... dn) {
		for (double d : dn)
			if (abs(d1 - d) > ZERO)
				return false;
		return true;
	}

	/**
	 * 判断i1，i2，...，in是否全相等
	 * @return i1==i2==i3==...==in
	 * @see #equals(double, double...)
	 */
	public static boolean equals(int i1, int... in) {
		for (int i : in)
			if (i1 != i)
				return false;
		return true;
	}
	/**
	 * 判断b1，b2，...，bn是否全相等
	 * @return b1==b2==b3==...==bn
	 * @see #equals(int, int...)
	 */
	public static boolean equals(boolean b1, boolean... bn) {
		for (boolean b : bn)
			if (b1 != b)
				return false;
		return true;
	}

	/**
	 * 判断d1，d2，...，dn中是否存在近似相等的两个double，
	 * 相等判断精度为ZERO
	 * @see #equals(double, double...)
	 * @see #ZERO
	 */
	public static boolean hasEqual(double d1, double... dn) {
		for (int i = -1; i < dn.length - 1; i++)
			for (int j = i + 1; j < dn.length; j++)
				if (abs((i == -1 ? d1 : dn[i]) - dn[j]) < ZERO)
					return true;
		return false;
	}

	/**
	 * 判断i1，i2，...，in中是否存在相等的两个int
	 */
	public static boolean hasEqual(int i1, int... in) {
		for (int i = -1; i < in.length - 1; i++)
			for (int j = i + 1; j < in.length; j++)
				if ((i == -1 ? i1 : in[i]) == in[j])
					return true;
		return false;
	}
	/**
	 * 判断b1，b2，...，bn中是否存在相等的两个boolean
	 */
	public static boolean hasEqual(boolean b1, boolean... bn) {
		for (int i = -1; i < bn.length - 1; i++)
			for (int j = i + 1; j < bn.length; j++)
				if ((i == -1 ? b1 : bn[i]) == bn[j])
					return true;
		return false;
	}

	/**
	 * 判断x是否属于区间[x1,x2]或[x2,x1]
	 * @return 如果x属于区间[x1,x2]或[x2,x1]，则返回 true；否则返回 false。
	 */
	public static boolean isMiddle(double x, double x1, double x2) {
		return (x >= x1 && x <= x2) || (x >= x2 && x <= x1);
	}

	/**
	 * 二分法找方程解，并存储在solutionNum内
	 */
	private static void probeSolution(double[] solutionNum, Equation e1,
			Equation e2, double x1, double x2, double precision) {
		double y1 = e2.f(x1) - e1.f(x1);
		double y2 = e2.f(x2) - e1.f(x2);
		double x = (x1 + x2) / 2;
		boolean hasSolution = y1 * y2 <= 0;
		boolean preArrived = abs(x2 - x1) < (precision == 0 ? 10e-6
				: abs(precision));
		if (hasSolution) {
			if (y1 == 0)
				solutionNum[0] = x1;
			else if (y2 == 0)
				solutionNum[0] = x2;
			else if (preArrived)
				solutionNum[0] = x;
		}
		if (!Double.isNaN(solutionNum[0]))
			return;
		if (!preArrived) {
			probeSolution(solutionNum, e1, e2, x1, x, precision);
			probeSolution(solutionNum, e1, e2, x, x2, precision);
		}
	}

	/**
	 * 返回f1(x)=f2(x)精度为precision的近似解<br/>
	 * 使用二分法找出第一个合适的近似解<br/>
	 * 当precision==0时，precision=10e-6。因此，总是无法找到精确解<br/>
	 * 所以，当f1(x)-f2(x)>=0或f1(x)-f2(x)<=0时，解总是找不到<br/>
	 * precision/(x2-x1)足够小时，可能发生栈溢出<br/>
	 * 
	 * @param e1
	 *            提供方程f1(x)的对象
	 * @param e2
	 *            提供方程f2(x)的对象
	 * @param x1
	 *            近似解区间端点x1
	 * @param x2
	 *            近似解区间端点x2
	 * @param precision
	 *            近似解精度<br/>
	 * @return f1(x)-f2(x)=0的近似解<br/>
	 *         若存在多解情况，return最靠近x1的解
	 */
	public static double solution(Equation e1, Equation e2, double x1,
			double x2, double precision) {
		double[] solutionNum = { NO_Solution };
		probeSolution(solutionNum, e1, e2, x1, x2, precision);
		return solutionNum[0];
	}
	public static void main(String[] agrs) {
		System.out.println(new MyVector(2,2));
	}
}