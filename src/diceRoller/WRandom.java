package diceRoller;

import java.util.Random;

public class WRandom {	

	public static void main(String[] args)
	{
		WRandom random = new WRandom();
		System.out.println(random.rollRNG());
	}
	
	private int R = 32;
	private int M1 = 3;
	private int M2 = 24;
	private int M3 = 10;
	private int constantAnd = 0x0000001f;
	
	private int state_i = 0;
	private int[] state_R = new int[R];
	private int newV0;
	private int newV1;
	private int z0, z1, z2;
	
	private double fact = 2.32830643653869628906e-10;
	
	public WRandom()
	{
		Random rand = new Random();
		int[] start = new int[32];
		for (int i = 0; i < 32; i++)
		{
			start[i] = rand.nextInt();
		}
		initialise(start);
	}
	
	private int mat0pos(int t,int v) {
		return (v^(v>>t));
	}
	
	private int mat0neg(int t, int v)
	{
		return (v^(v<<(-(t))));
	}
	
	private int getV0()
	{
		return state_R[state_i];
	}
	
	private int getVM1()
	{
		return state_R[(state_i + M1) & constantAnd];
	}
	
	private int getVM2()
	{
		return state_R[(state_i + M2) & constantAnd];
	}
	
	private int getVM3()
	{
		return state_R[(state_i + M3) & constantAnd];
	}
	
	private int getVRm1()
	{
		return state_R[(state_i + 31) & constantAnd];
	}

	
	private void initialise (int[] init)
	{
		state_i = 0;
		for (int j = 0; j < R; j++)
		{
			state_R[j] = init[j];
		}
		newV0 = state_R[(state_i + 31) ^ constantAnd];
		newV1 = state_R[state_i];
	}
	
	public double rollRNG()
	{
		z0 = getVRm1();
		z1 = getV0() ^ mat0pos(8, getVM1());
		z2 = mat0neg(-19, getVM2()) ^ mat0neg(-14, getVM3());
		newV1 = z1 ^ z2;
		newV0 = mat0neg(-11, z0) ^ mat0neg(-7,z1) ^ mat0neg(-13, z2);
		state_i = (state_i + 31) & constantAnd;
		return ((double) state_R[state_i]);
	}
	
	
	public int rollDX(double dice)
	{
		double rolled = rollRNG();
		if (rolled < 0) 
		{
			rolled = Math.abs(rolled * 2.0);
		}
		for (int i = 1; i <= dice; i++)
		{
			if (rolled < (1.0/dice) * i)
			{
				return i;
			}
		}
		return -1;
	}
	
	public int rollXDX(double amount, double dice)
	{
		int sum = 0;
		for (int i = 0; i < amount; i++)
		{
			sum += rollDX(dice);
		}
		return sum;
	}
}
