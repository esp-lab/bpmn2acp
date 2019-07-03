package main;

import java.math.BigInteger;

public class aaaa {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int A = 1234000;
		int B = 0001;
		int lA = (A==0)?0:(int) Math.log10(A);
		int lB = (B==0)?0:(int) Math.log10(B);
		long C =0;
		int p = 0;
		int lC = lA + lB + 1;
		while (lA >= 0 && lB >= 0) {
			switch (p) {
			case 0:
				p = 1;
				int delta = (A / (int) Math.pow(10, lA));
				int bigDelta = delta*(int) Math.pow(10, lC);
				C+=bigDelta;
				A -= (A / (int) Math.pow(10, lA)) * (int) Math.pow(10, lA);
				lC--;
				lA--;
				break;

			default:
				p = 0;
				int delta1 = (B / (int) Math.pow(10, lB));
				int bigDelta1 = delta1*(int) Math.pow(10, lC);
				C+=bigDelta1;
				B -= (B / (int) Math.pow(10, lB)) * (int) Math.pow(10, lB);
				lC--;
				lB--;
				break;
			}
		}
		if (lA >= 0) {
			C+=A;
		}
		if (lB >= 0) {
			C+=B;
		}
		System.out.println(C);
	}

}
