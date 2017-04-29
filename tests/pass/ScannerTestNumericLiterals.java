
public class ScannerTestNumericLiterals {
	public static void main(String args[]) {
		// Test Double
		double dbl = 1.33;
	    dbl = 1.33d;
	    dbl = 1.33.;
          dbl = 1.1e11;
	    
          // Test Double Error
          double dbl_fail = 1.;
          dbl_fail = 1.e;
          dbl_fail = 1.1e;

	    // Test Float
	    float fl = 1.33f;
	    fl = 1.33F;
          fl = 1.3e3f;

          // Test Float Error
          double float_fail = 1.f;
          float_fail = 1.ef;
          float_fail = 1.1ef;

          	    
	    // Test Long
	    long lg = 1234L;
	    lg = 1234l;
	    
	    // Test Integers - Valid
		int integer = 1;
	    int octal = 0123;
	    int binary = 0B10110;
	    binary = 0b10110;
	    int hex = 0X1A4Ff;
	    hex = 0x1A4Ff;
	    
	    // Invalid Octal
	    int fail = 0832;
	    fail = 0999;
	    
	    // Invalid Binary
	    fail = 0b2222;
	    fail = 0B1234;
	    
	    // Invalid Hex
	    fail = 0xgbcdefg;

	}   
}
