
public class Throws_DoUntilTest {
	
	
	void testMethod() throws IOException, IllegalArgumentException {
		int i = 0;
		do {
			i = i + 1;
		} until (i > 10);
	}

}