package jminusminus;

public class JThrowStatement extends JStatement {
    private JExpression expr;
	public JThrowStatement(int line, JExpression expr) {
		super(line);
		this.expr = expr;
	}

	@Override
	public JThrowStatement analyze(Context context) {
		expr = expr.analyze(context); //TODO check if throwable?
		return this;
	}

	@Override
	public void codegen(CLEmitter arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JThrowStatement line=\"%d\">\n", line());
        p.indentRight();
        expr.writeToStdOut(p);
        p.indentLeft();
        p.printf("</JThrowStatement>\n");

	}

}
