package jminusminus;

class JDoUntilStatement extends JStatement {
	
    /** Test expression. */
    private JExpression condition;

    /** The body. */
    private JStatement body;

	
	public JDoUntilStatement (int line, JStatement body, JExpression condition) {
        super(line);
        this.condition = condition;
        this.body = body;
	}
	
    /**
     * Analysis involves analyzing the test, checking its type and analyzing the
     * body statement.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JDoUntilStatement analyze(Context context) {
        condition = condition.analyze(context);
        condition.type().mustMatchExpected(line(), Type.BOOLEAN);
        body = (JStatement) body.analyze(context);
        return this;
    }
    
    /**
     * Generate code for the do-until loop.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        String top = output.createLabel();

        // Top, unconditional first execution of body
        output.addLabel(top);

        // Codegen body
        body.codegen(output);

        // Branch to top if condition is true, otherwise exit loop
        condition.codegen(output, top, true);
    }
	
	/**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JDoUntilStatement line=\"%d\">\n", line());
        p.indentRight();
        p.printf("<Body>\n");
        p.indentRight();
        body.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Body>\n");
        p.printf("<TestExpression>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</TestExpression>\n");
        p.indentLeft();
        p.printf("</JDoUntilStatement>\n");
    }
}