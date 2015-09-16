package fz.math;

import static java.lang.Math.*;

/**
 * @author ��������
 */
public class Util {
	/**
	 * �޽��ʶ������ֵ==Double.MAX_VALUE
	 */
	public static final double NO_Solution = java.lang.Double.NaN;
	/**
	 * �����������㣬��ֵ==10e-3
	 */
	public static final double ZERO = 10e-3;

	/**
	 * �ж�d1��d2��...��dn�Ƿ����ȫ��ȣ�
	 * ����жϾ���ΪZERO
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
	 * �ж�i1��i2��...��in�Ƿ�ȫ���
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
	 * �ж�b1��b2��...��bn�Ƿ�ȫ���
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
	 * �ж�d1��d2��...��dn���Ƿ���ڽ�����ȵ�����double��
	 * ����жϾ���ΪZERO
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
	 * �ж�i1��i2��...��in���Ƿ������ȵ�����int
	 */
	public static boolean hasEqual(int i1, int... in) {
		for (int i = -1; i < in.length - 1; i++)
			for (int j = i + 1; j < in.length; j++)
				if ((i == -1 ? i1 : in[i]) == in[j])
					return true;
		return false;
	}
	/**
	 * �ж�b1��b2��...��bn���Ƿ������ȵ�����boolean
	 */
	public static boolean hasEqual(boolean b1, boolean... bn) {
		for (int i = -1; i < bn.length - 1; i++)
			for (int j = i + 1; j < bn.length; j++)
				if ((i == -1 ? b1 : bn[i]) == bn[j])
					return true;
		return false;
	}

	/**
	 * �ж�x�Ƿ���������[x1,x2]��[x2,x1]
	 * @return ���x��������[x1,x2]��[x2,x1]���򷵻� true�����򷵻� false��
	 */
	public static boolean isMiddle(double x, double x1, double x2) {
		return (x >= x1 && x <= x2) || (x >= x2 && x <= x1);
	}

	/**
	 * ���ַ��ҷ��̽⣬���洢��solutionNum��
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
	 * ����f1(x)=f2(x)����Ϊprecision�Ľ��ƽ�<br/>
	 * ʹ�ö��ַ��ҳ���һ�����ʵĽ��ƽ�<br/>
	 * ��precision==0ʱ��precision=10e-6����ˣ������޷��ҵ���ȷ��<br/>
	 * ���ԣ���f1(x)-f2(x)>=0��f1(x)-f2(x)<=0ʱ���������Ҳ���<br/>
	 * precision/(x2-x1)�㹻Сʱ�����ܷ���ջ���<br/>
	 * 
	 * @param e1
	 *            �ṩ����f1(x)�Ķ���
	 * @param e2
	 *            �ṩ����f2(x)�Ķ���
	 * @param x1
	 *            ���ƽ�����˵�x1
	 * @param x2
	 *            ���ƽ�����˵�x2
	 * @param precision
	 *            ���ƽ⾫��<br/>
	 * @return f1(x)-f2(x)=0�Ľ��ƽ�<br/>
	 *         �����ڶ�������return���x1�Ľ�
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