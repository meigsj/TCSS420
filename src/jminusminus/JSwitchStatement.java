package jminusminus;

import java.util.*;

class JSwitchStatement extends JStatement {

	JExpression mySwitchExpression;
	ArrayList<JExpression> caseLiterial;
	ArrayList<ArrayList<JStatement>> caseStatements;
	ArrayList<JStatement> myDefaultStatements;
	
	public JSwitchStatement(int line, JExpression theSwitchExpression, ArrayList<JExpression> theCaseLiterials,
			ArrayList<ArrayList<JStatement>> theCaseStatements, ArrayList<JStatement> theDefaultStatements) {
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
		 Iterator<ArrayList<JStatement>> caseStatementListIterator = caseStatements.iterator();
		 Iterator<JStatement> defaultStatements = myDefaultStatements.iterator();
		 p.printf("<JSwitchStatement line=\"%d\">\n", line());
	     p.indentRight();
		 p.printf("<Switch_Statement>\n");
		 p.indentRight();
		 mySwitchExpression.writeToStdOut(p);
 
		 
		 if(caseLiterial != null) {
			 while(caseLiterialIter.hasNext()) {	
				
				 p.printf("<Case Literial>\n");
				 caseLiterialIter.next().writeToStdOut(p);
				 p.indentRight();			 
				 if(caseStatements != null) {				
					 while(caseStatementListIterator.hasNext()) {
						 p.printf("<Case Statement>\n");
						 ArrayList<JStatement> statementList = caseStatementListIterator.next();				 
						 Iterator<JStatement> caseStatementIterator = statementList.iterator();
						 while(caseStatementIterator.hasNext()) {
							 p.indentRight();
							 caseStatementIterator.next().writeToStdOut(p);
							 p.indentLeft();
						 }
						 p.printf("</Case Statement>\n");
					 }
				 }
				 p.printf("</Case Literial>\n");
			 } 
		 }
		 if(myDefaultStatements != null) { 
			 while(defaultStatements.hasNext()) {
				 JStatement defaultStatement =  defaultStatements.next();
			     p.printf("<DefaultStatement>\n");
			     p.indentRight();
			     defaultStatement.writeToStdOut(p);
				 p.indentLeft();
				 p.printf("</DefaultStatement>\n");
			 }
		 }
		 
		 p.indentLeft();
		 p.printf("</Switch_Statement>\n");

	     

	}
	
	
}
