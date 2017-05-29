import java.lang.System;
public class long_assignment_operators_test {
	public static void main(String[] args) {
		System.out.println("Plus Equal Tests");
		plusEqualsLong_With_Var();
		plusEqualsInt_With_Var();
		plusEqualsLong_Without_Var();
		plusEqualsInt_Without_Var();
		
		System.out.println("");
		System.out.println("Minus Equal Tests");	
		minusEqualsLong_With_Var();
		minusEqualsInt_With_Var();
		minusEqualsLong_Without_Var();
		minusEqualsInt_Without_Var();
		
		System.out.println("");
		System.out.println("Mult Equal Tests");	
		multEqualsLong_With_Var();
		multEqualsInt_With_Var();
		multEqualsLong_Without_Var();
		multEqualsInt_Without_Var();
		
		System.out.println("");
		System.out.println("div Equal Tests");	
		divEqualsLong_With_Var();
		divEqualsInt_With_Var();
		divEqualsLong_Without_Var();
		divEqualsInt_Without_Var();
		
		System.out.println("");
		System.out.println("mod Equal Tests");	
		modEqualsLong_With_Var();
		modEqualsInt_With_Var();
		modEqualsLong_Without_Var();
		modEqualsInt_Without_Var();
		
		System.out.println("");
		System.out.println("bitwise And Equal Tests");	
		bitwise_AndEqualsLong_With_Var();
		bitwise_AndEqualsInt_With_Var();
		bitwise_AndEqualsLong_Without_Var();
		bitwise_AndEqualsInt_Without_Var();
		
		System.out.println("");
		System.out.println("bitwise OR Equal Tests");	
		bitwise_OREqualsLong_With_Var();
		bitwise_OREqualsInt_With_Var();
		bitwise_OREqualsLong_Without_Var();
		bitwise_OREqualsInt_Without_Var();
		
		System.out.println("");
		System.out.println("bitwise XOR Equal Tests");	
		bitwise_XOREqualsLong_With_Var();
		bitwise_XOREqualsInt_With_Var();
		bitwise_XOREqualsLong_Without_Var();
		bitwise_XOREqualsInt_Without_Var();
		
	}
	
	public static void plusEqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l += other;
		System.out.println(l);
	}
	
	public static void plusEqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l += other;
		System.out.println(l);
	}
	
	public static void plusEqualsLong_Without_Var() {
		long l = 5l;
		l += 10l;
		System.out.println(l);
	}
	
	public static void plusEqualsInt_Without_Var() {
		long l = 5l;
		l += 10;
		System.out.println(l);
	}
	
	
	public static void minusEqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l -= other;
		System.out.println(l);
	}
	
	public static void minusEqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l -= other;
		System.out.println(l);
	}
	
	public static void minusEqualsLong_Without_Var() {
		long l = 5l;
		l -= 10l;
		System.out.println(l);
	}
	
	public static void minusEqualsInt_Without_Var() {
		long l = 5l;
		l -= 10;
		System.out.println(l);
	}
	
	
	public static void multEqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l *= other;
		System.out.println(l);
	}
	
	public static void multEqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l *= other;
		System.out.println(l);
	}
	
	public static void multEqualsLong_Without_Var() {
		long l = 5l;
		l *= 10l;
		System.out.println(l);
	}
	
	public static void multEqualsInt_Without_Var() {
		long l = 5l;
		l *= 10;
		System.out.println(l);
	}
	
	
	public static void divEqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l /= other;
		System.out.println(l);
	}
	
	public static void divEqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l /= other;
		System.out.println(l);
	}
	
	public static void divEqualsLong_Without_Var() {
		long l = 5l;
		l /= 10l;
		System.out.println(l);
	}
	
	public static void divEqualsInt_Without_Var() {
		long l = 5l;
		l /= 10;
		System.out.println(l);
	}
	
	
	public static void modEqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l %= other;
		System.out.println(l);
	}
	
	public static void modEqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l %= other;
		System.out.println(l);
	}
	
	public static void modEqualsLong_Without_Var() {
		long l = 5l;
		l %= 10l;
		System.out.println(l);
	}
	
	public static void modEqualsInt_Without_Var() {
		long l = 5l;
		l %= 10;
		System.out.println(l);
	}
	
	
	public static void bitwise_AndEqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l &= other;
		System.out.println(l);
	}
	
	public static void bitwise_AndEqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l &= other;
		System.out.println(l);
	}
	
	public static void bitwise_AndEqualsLong_Without_Var() {
		long l = 5l;
		l &= 10l;
		System.out.println(l);
	}
	
	public static void bitwise_AndEqualsInt_Without_Var() {
		long l = 5l;
		l &= 10;
		System.out.println(l);
	}
	
	public static void bitwise_OREqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l |= other;
		System.out.println(l);
	}
	
	public static void bitwise_OREqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l |= other;
		System.out.println(l);
	}
	
	public static void bitwise_OREqualsLong_Without_Var() {
		long l = 5l;
		l |= 10l;
		System.out.println(l);
	}
	
	public static void bitwise_OREqualsInt_Without_Var() {
		long l = 5l;
		l |= 10;
		System.out.println(l);
	}
	
	public static void bitwise_XOREqualsLong_With_Var() {
		long l = 5l;
		long other = 10l;
		l ^= other;
		System.out.println(l);
	}
	
	public static void bitwise_XOREqualsInt_With_Var() {
		long l = 5l;
		int other = 10;
		l ^= other;
		System.out.println(l);
	}
	
	public static void bitwise_XOREqualsLong_Without_Var() {
		long l = 5l;
		l ^= 10l;
		System.out.println(l);
	}
	
	public static void bitwise_XOREqualsInt_Without_Var() {
		long l = 5l;
		l ^= 10;
		System.out.println(l);
	}
}
