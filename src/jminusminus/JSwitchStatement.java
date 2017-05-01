package jminusminus;

import java.util.*;

class JSwitchStatement extends JStatement {

	JExpression mySwitchExpression;
	ArrayList<JExpression> caseLiterial;
	ArrayList<JBlock> caseStatements;
	JBlock myDefaultStatements;
	
	public JSwitchStatement(int line, JExpression theSwitchExpression, ArrayList<JExpression> theCaseLiterials,
			ArrayList<JBlock> theCaseStatements, JBlock theDefaultStatements) {
		super(line);
		mySwitchExpression = theSwitchExpression;
		this.caseLiterial = theCaseLiterials;
		this.caseStatements = theCaseStatements;
		this.myDefaultStatements = theDefaultStatements;
	}

	@Override
	public JAST analyze(Context arg0) { 
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void codegen(CLEmitter arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
		 Iterator<JExpression> caseLiterialIter = caseLiterial.iterator();
		 Iterator<JBlock> caseBlockListIterator = caseStatements.iterator();
		 //Iterator<JStatement> defaultStatements = myDefaultStatements.iterator();
		 p.printf("<JSwitchStatement line=\"%d\">\n", line());
	     p.indentRight();
		 p.printf("<Switch_Statement>\n");
		 p.indentRight();
		 mySwitchExpression.writeToStdOut(p);
 	 
		 if(caseLiterial != null) {
			 while(caseLiterialIter.hasNext()) {
				 p.indentRight();
				 p.printf("<Case Literial>\n");
				 caseLiterialIter.next().writeToStdOut(p);
				 p.indentRight();			 
				 if(caseStatements != null) {				
					 while(caseBlockListIterator.hasNext()) {
						 p.printf("<Case Block>\n");
						 p.indentRight();
						 JBlock block = caseBlockListIterator.next();
						 block.writeToStdOut(p);
						 p.indentLeft();
						 p.printf("</Case Block>\n");
					 }
				 }
				 p.indentLeft();
				 p.printf("</Case Literial>\n");
				 p.indentLeft();
			 } 
		 }
		 if(myDefaultStatements != null) { 
			 p.printf("<DefaultStatement>\n");
			 p.indentRight();
			 myDefaultStatements.writeToStdOut(p);
			 p.indentLeft();
			 p.printf("</DefaultStatement>\n");					
		 }
		 
		 p.indentLeft();
		 p.printf("</Switch_Statement>\n");

	     

	}
	
	
}
