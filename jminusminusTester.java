
public class StandardForTest {
	
	
	void standardFor() throws IOException, IllegalArgumentException {
		for(int i = 0; i < 10; i = i + 1) {
			System.out.println(i);
		}
	}
	
	
	void standardForNoInit() throws IOException, IllegalArgumentException {
		int i = 0;
		for(; i < 10; i = i + 1) {
			System.out.println(i);
		}
	}

	void standardForNoCondition() throws IOException, IllegalArgumentException {
		int i = 0;
		for(; ;i = i + 1) {
			System.out.println(i);
		}
	}
	
	void standardForNoIncrement() throws IOException, IllegalArgumentException {
		int i = 0;
		for(; ;) {
			System.out.println(i);
		}
	}
	
	void standardForOnlyCond() throws IOException, IllegalArgumentException {
		int i = 0;
		for(;i < 10 ;) {
			System.out.println(i);
		}
	}
	
	void standardForOnlyInit() throws IOException, IllegalArgumentException {
		for(int i = 0;;) {
			System.out.println(i);
		}
	}
}