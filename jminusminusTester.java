
public class StandardForTest {
	
	
	void switchTestMethod() {
		int i  = 0;
		switch(i) {
			case 0: {int p = 2;}
			case 1: {int p = 2;}
		}
	}

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
>>>>>>> 739932829248be00901cd1cc5808488c268152fa
		}
	}
}