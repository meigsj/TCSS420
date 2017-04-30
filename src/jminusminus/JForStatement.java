package jminusminus;

abstract class JForStatement extends JStatement {
	protected JStatement body;
	protected JExpression init;
	
	protected JForStatement(int line, JExpression init, JStatement body) {
		super(line);
		this.init = init;
		this.body = body;
	}
	
	public abstract JForStatement analyze(Context context);
	
	public abstract void codegen(CLEmitter output);
	
	public abstract void writeToStdOut(PrettyPrinter p);
}
	
class JEnhancedForStatement extends JForStatement {
	private JExpression collection;
		
	public JEnhancedForStatement(int line, JExpression init, JExpression collection, JStatement body) {
		super(line, init, body);
		this.collection = collection;
	}
		
	// TODO Check correctness
	public JForStatement analyze(Context context) {
	    init = init.analyze(context);
	    collection = collection.analyze(context);
	    body = (JStatement) body.analyze(context);
	    return this;
	}
		
	public void codegen(CLEmitter output) {
			
	}
		
	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JEnhancedForStatement line=\"%d\">\n", line());
		p.indentRight();
		p.printf("<InitializeExpression>\n");
		p.indentRight();
		init.writeToStdOut(p);
		p.indentLeft();
		p.printf("</InitializeExpression>\n");
		p.printf("<CollectionExpression>\n");
	    p.indentRight();    
		collection.writeToStdOut(p);
		p.indentLeft();
		p.printf("</CollectionExpression>\n");
		p.printf("<Body>\n");
		p.indentRight();
		body.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Body>\n");
		p.indentLeft();
		p.printf("</JEnhancedForStatement>\n");          
	}
}
	
class JStandardForStatement extends JForStatement {
	private JExpression update;
	private JExpression bool_ex;
	public JStandardForStatement(int line, JExpression init, JExpression bool_ex, JExpression update, JStatement body){
		super(line, init, body);
		this.update = update;
		this.bool_ex = bool_ex;
	}
		
	// TODO Check correctness
	public JForStatement analyze(Context context) {
		init = init.analyze(context);
		update = update.analyze(context);
		bool_ex = bool_ex.analyze(context);
        bool_ex.type().mustMatchExpected(line(), Type.BOOLEAN);
        body = (JStatement) body.analyze(context);
        return this;
	}
		
	public void codegen(CLEmitter output) {
			
	}
		
	public void writeToStdOut(PrettyPrinter p) {
		 p.printf("<JStandarForStatement line=\"%d\">\n", line());
	     p.indentRight();
	     p.printf("<InitializeExpression>\n");
	     p.indentRight();
	     init.writeToStdOut(p);
	     p.indentLeft();
	     p.printf("</InitializeExpression>\n");
	     p.printf("<ConditionExpression>\n");
	     p.indentRight();
	     bool_ex.writeToStdOut(p);
	     p.indentLeft();
	     p.printf("</ConditionExpression>\n");
	     p.printf("<UpdateExpression>\n");
	     p.indentRight();
	     update.writeToStdOut(p);
	     p.indentLeft();
	     p.printf("</UpdateExpression>\n");
	     p.printf("<Body>\n");
	     p.indentRight();
	     body.writeToStdOut(p);
	     p.indentLeft();
		 p.printf("</Body>\n");
		 p.indentLeft();
		 p.printf("</JStandarForStatement>\n");			
	}
}

