import java.lang.System;
public class long_test {
		public static void main(String[] args) {
			
			
			System.out.println("Method param Test");
			longAsParamTest(2L, 3L);
			
			System.out.println("Arithmitic Tests");
			System.out.println("Addition Tests");
			LongAndLong_Const_Addition_Test();
			LongAndInt_Const_Addition_Test();
			IntAndLong_Const_Addition_Test();
			LongAndInt_Declaration_Const_Addition_Test();
			LongAndLong_Vars_Addition_Test();
			LongAndInt_Vars_Addition_Test();
			IntAndLong_Vars_Addition_Test();
			
			System.out.println("");
			System.out.println("Subtraction Tests");	
			LongAndLong_Const_Subtraction_Test();
			LongAndInt_Const_Subtraction_Test();
			IntAndLong_Const_Subtraction_Test();
			LongAndInt_Declaration_Const_Subtraction_Test();
			LongAndLong_Vars_Subtraction_Test();
			LongAndInt_Vars_Subtraction_Test();
			IntAndLong_Vars_Subtraction_Test();
			
			System.out.println("");
			System.out.println("Mult Tests");	
			LongAndLong_Const_Mult_Test();
			LongAndInt_Const_Mult_Test();
			IntAndLong_Const_Mult_Test();
			LongAndInt_Declaration_Const_Mult_Test();
			LongAndLong_Vars_Mult_Test();
			LongAndInt_Vars_Mult_Test();
			IntAndLong_Vars_Mult_Test();
			
			System.out.println("");
			System.out.println("Div Tests");	
			LongAndLong_Const_Div_Test();
			LongAndInt_Const_Div_Test();
			IntAndLong_Const_Div_Test();
			LongAndInt_Declaration_Const_Div_Test();
			LongAndLong_Vars_Div_Test();
			LongAndInt_Vars_Div_Test();
			IntAndLong_Vars_Div_Test();
			
			
			System.out.println("");
			System.out.println("Mod Tests");	
			LongAndLong_Const_Mod_Test();
			LongAndInt_Const_Mod_Test();
			IntAndLong_Const_Mod_Test();
			LongAndInt_Declaration_Const_Mod_Test();
			LongAndLong_Vars_Mod_Test();
			LongAndInt_Vars_Mod_Test();
			IntAndLong_Vars_Mod_Test();
			
			System.out.println("");
			System.out.println("Bitwise And Tests");	
			LongAndLong_Const_Bitwise_And_Test();
		    
			LongAndInt_Const_Bitwise_And_Test();
			IntAndLong_Const_Bitwise_And_Test();
			LongAndInt_Declaration_Const_Bitwise_And_Test();
			LongAndLong_Vars_Bitwise_And_Test();
			LongAndInt_Vars_Bitwise_And_Test();
			IntAndLong_Vars_Bitwise_And_Test();
			
			System.out.println("");
			System.out.println("Bitwise OR Tests");	
			LongAndLong_Const_Bitwise_OR_Test();
			LongAndInt_Const_Bitwise_OR_Test();
			IntAndLong_Const_Bitwise_OR_Test();
			LongAndInt_Declaration_Const_Bitwise_OR_Test();
			LongAndLong_Vars_Bitwise_OR_Test();
			LongAndInt_Vars_Bitwise_OR_Test();
			IntAndLong_Vars_Bitwise_OR_Test();
			
			System.out.println("");
			System.out.println("Bitwise XOR Tests");	
			LongAndLong_Const_Bitwise_XOR_Test();
			LongAndInt_Const_Bitwise_XOR_Test();
			IntAndLong_Const_Bitwise_XOR_Test();
			LongAndInt_Declaration_Const_Bitwise_XOR_Test();
			LongAndLong_Vars_Bitwise_XOR_Test();
			LongAndInt_Vars_Bitwise_XOR_Test();
			IntAndLong_Vars_Bitwise_XOR_Test();
			
		
			
		}
		
		public void longAsParamTest (long l, long j) {
            l = l + j;
            System.out.println(l);
        }
		
		public static void LongAndLong_Const_Addition_Test() {
			long l = 3L;
			l = l + 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Addition_Test() {
			long i = 2l;
			i = i + 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Addition_Test() {
			long i = 2l;
			i = 9 + i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Addition_Test() {
			int o = 9;
			long i = 2L + o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Addition_Test() {
			long l = 3L;
			long j = 2L;
			l = l + j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Addition_Test() {
			long i = 2l;
			int o = 9;
			i = i + o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Addition_Test() {
			long i = 2l;
			int o = 9;
			i = o + i;
			System.out.println(i);
		}
		
		
		
		public static void LongAndLong_Const_Subtraction_Test() {
			long l = 3L;
			l = l - 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Subtraction_Test() {
			long i = 2l;
			i = i - 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Subtraction_Test() {
			long i = 2l;
			i = 9 - i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Subtraction_Test() {
			int o = 9;
			long i = 2L - o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Subtraction_Test() {
			long l = 3L;
			long j = 2L;
			l = l - j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Subtraction_Test() {
			long i = 2l;
			int o = 9;
			i = i - o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Subtraction_Test() {
			long i = 2l;
			int o = 9;
			i = o - i;
			System.out.println(i);
		}
		
		
		
		public static void LongAndLong_Const_Mult_Test() {
			long l = 3L;
			l = l * 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Mult_Test() {
			long i = 2l;
			i = i * 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Mult_Test() {
			long i = 2l;
			i = 9 * i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Mult_Test() {
			int o = 9;
			long i = 2L * o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Mult_Test() {
			long l = 3L;
			long j = 2L;
			l = l * j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Mult_Test() {
			long i = 2l;
			int o = 9;
			i = i * o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Mult_Test() {
			long i = 2l;
			int o = 9;
			i = o * i;
			System.out.println(i);
		}
		
		
		public static void LongAndLong_Const_Div_Test() {
			long l = 3L;
			l = l / 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Div_Test() {
			long i = 2l;
			i = i / 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Div_Test() {
			long i = 2l;
			i = 9 / i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Div_Test() {
			int o = 9;
			long i = 2L / o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Div_Test() {
			long l = 3L;
			long j = 2L;
			l = l / j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Div_Test() {
			long i = 2l;
			int o = 9;
			i = i / o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Div_Test() {
			long i = 2l;
			int o = 9;
			i = o / i;
			System.out.println(i);
		}
		
		public static void LongAndLong_Const_Mod_Test() {
			long l = 3L;
			l = l % 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Mod_Test() {
			long i = 2l;
			i = i % 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Mod_Test() {
			long i = 2l;
			i = 9 % i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Mod_Test() {
			int o = 9;
			long i = 2L % o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Mod_Test() {
			long l = 3L;
			long j = 2L;
			l = l % j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Mod_Test() {
			long i = 2l;
			int o = 9;
			i = i % o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Mod_Test() {
			long i = 2l;
			int o = 9;
			i = o % i;
			System.out.println(i);
		}
		
		
		public static void LongAndLong_Const_Bitwise_And_Test() {
			long l = 3L;
			l = l & 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Bitwise_And_Test() {
			long i = 2l;
			i = i & 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Bitwise_And_Test() {
			long i = 2l;
			i = 9 & i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Bitwise_And_Test() {
			int o = 9;
			long i = 2L & o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Bitwise_And_Test() {
			long l = 3L;
			long j = 2L;
			l = l & j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Bitwise_And_Test() {
			long i = 2l;
			int o = 9;
			i = i & o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Bitwise_And_Test() {
			long i = 2l;
			int o = 9;
			i = o & i;
			System.out.println(i);
		}
		
		
		public static void LongAndLong_Const_Bitwise_OR_Test() {
			long l = 3L;
			l = l | 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Bitwise_OR_Test() {
			long i = 2l;
			i = i | 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Bitwise_OR_Test() {
			long i = 2l;
			i = 9 | i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Bitwise_OR_Test() {
			int o = 9;
			long i = 2L | o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Bitwise_OR_Test() {
			long l = 3L;
			long j = 2L;
			l = l | j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Bitwise_OR_Test() {
			long i = 2l;
			int o = 9;
			i = i | o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Bitwise_OR_Test() {
			long i = 2l;
			int o = 9;
			i = o | i;
			System.out.println(i);
		}
		
		
		
		
		public static void LongAndLong_Const_Bitwise_XOR_Test() {
			long l = 3L;
			l = l ^ 2L;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Const_Bitwise_XOR_Test() {
			long i = 2l;
			i = i ^ 9;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Const_Bitwise_XOR_Test() {
			long i = 2l;
			i = 9 ^ i;
			System.out.println(i);
		}
		
		public static void LongAndInt_Declaration_Const_Bitwise_XOR_Test() {
			int o = 9;
			long i = 2L ^ o;
			System.out.println(i);	
		}
		
		public static void LongAndLong_Vars_Bitwise_XOR_Test() {
			long l = 3L;
			long j = 2L;
			l = l ^ j;
			System.out.println(l);	
		}
		
		public static void LongAndInt_Vars_Bitwise_XOR_Test() {
			long i = 2l;
			int o = 9;
			i = i ^ o;
			System.out.println(i);	
		}
		
		public static void IntAndLong_Vars_Bitwise_XOR_Test() {
			long i = 2l;
			int o = 9;
			i = o ^ i;
			System.out.println(i);
		}
		
		
		
}