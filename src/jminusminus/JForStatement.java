package jminusminus;

import static jminusminus.CLConstants.GOTO;

abstract class JForStatement extends JStatement {
	protected JStatement body;
	protected JStatement init;

	protected JForStatement(int line, JStatement init, JStatement body) {
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

	public JEnhancedForStatement(int line, JStatement init, JExpression collection, JStatement body) {
		super(line, init, body);
		this.collection = collection;
	}

	public JForStatement analyze(Context context) {
		init = (JStatement) init.analyze(context);
		collection = collection.analyze(context);
		body = (JStatement) body.analyze(context);
		return this;
	}

	public void codegen(CLEmitter output) {
		// Empty. . .for now!~
		// Enhanced For loop not part of Problem 4
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
	private JStatement update;
	private JExpression bool_ex;
    /**
     * The new context (built in analyze()) represented by this block.
     */
    private LocalContext context;
    
	public JStandardForStatement(int line, JStatement init, JExpression bool_ex, JStatement update, JStatement body) {
		super(line, init, body);
		this.update = update;
		this.bool_ex = bool_ex;
	}

	public JForStatement analyze(Context context) {
		// Updated for Problem 4 Exercise 5.7
		// Creates a new local context to keep the initialization
		// Statement only in the scope of the for loop
		this.context = new LocalContext(context);
		
		if (init != null) {
			init = (JStatement) init.analyze(this.context);
		}
		if (update != null) {
			update = (JStatement)update.analyze(this.context);
		}
		if (bool_ex != null) {
			bool_ex = bool_ex.analyze(this.context);
			bool_ex.type().mustMatchExpected(line(), Type.BOOLEAN);
		}
		body = (JStatement) body.analyze(this.context);
		return this;
	}

	public void codegen(CLEmitter output) {
		// Added for Problem 4 Exercise 5.7

		// init the var
		if (init != null) {
		    init.codegen(output);
		}
		// Need two labels
		String test = output.createLabel();
		String out = output.createLabel();

		// Branch out of the loop on the test condition
		// being false
		output.addLabel(test);
		if (bool_ex != null) {
		    bool_ex.codegen(output, out, false);
		}
		// Codegen body
		body.codegen(output);

		// do inc or dec
		if (update != null) {
			update.codegen(output);
		}
		// Unconditional jump back up to test
		output.addBranchInstruction(GOTO, test);

		// The label below and outside the loop
		output.addLabel(out);
	}

	public void writeToStdOut(PrettyPrinter p) {
		p.printf("<JStandardForStatement line=\"%d\">\n", line());
		p.indentRight();
		if (init != null) {
			p.printf("<InitializeExpression>\n");
			p.indentRight();
			init.writeToStdOut(p);
			p.indentLeft();
			p.printf("</InitializeExpression>\n");
		}
		if (bool_ex != null) {
			p.printf("<ConditionExpression>\n");
			p.indentRight();
			bool_ex.writeToStdOut(p);
			p.indentLeft();
			p.printf("</ConditionExpression>\n");
		}
		if (update != null) {
			p.printf("<UpdateExpression>\n");
			p.indentRight();
			update.writeToStdOut(p);
			p.indentLeft();
			p.printf("</UpdateExpression>\n");
		}
		p.printf("<Body>\n");
		p.indentRight();
		body.writeToStdOut(p);
		p.indentLeft();
		p.printf("</Body>\n");
		p.indentLeft();
		p.printf("</JStandardForStatement>\n");
	}
}
