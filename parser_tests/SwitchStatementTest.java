
public class SwitchStatementTest {
	void SwitchStatementTestMethod() {
		int i  = 0;
		switch(i) {
			case 0:  
				{i = i + 1;}
			case 1:
				i = i - 1;
			default: 
				i = i + 3;
		}
	}		
}
