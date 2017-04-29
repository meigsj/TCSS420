package jminusminus;
import static jminusminus.CLConstants.*;

/**
 * The AST node for a ternary expression.
 */

class JTernaryExpression extends JExpression {
	protected int line;
	protected JExpression condition;
	protected JExpression true_val;
	protected JExpression false_val;
	
	
	public JTernaryExpression(int line, JExpression condition, JExpression true_val, JExpression false_val) {
		super(line);
		this.condition=condition;
		this.true_val=true_val;
		this.false_val=false_val;
	}

	/**
     * The analysis of any JExpression returns a JExpression. That's all this
     * (re-)declaration of analyze() says.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

	@Override
    public JExpression analyze(Context context) {
    	condition = (JExpression) condition.analyze(context);
    	true_val = (JExpression) true_val.analyze(context);
    	false_val = (JExpression) false_val.analyze(context);
    	
    	condition.type().mustMatchExpected(line(), Type.BOOLEAN);
    	if (true_val.type() == false_val.type()) {
    		type = true_val.type();
    	} else {
    		type = Type.ANY;
    		JAST.compilationUnit.reportSemanticError(line(),
                    "Types must match for ternary expression true and false values");
    	}
    	
    	return this;	
    }
	
    @Override
    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JTernaryExpression line=\"%d\" type=\"%s\" "
                + "\n", line(), ((type == null) ? "" : type
                .toString()));
        p.indentRight();
        p.printf("<Condition>\n");
        p.indentRight();
        condition.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Condition>\n");
        p.printf("<True_Val>\n");
        p.indentRight();
        true_val.writeToStdOut(p);
        p.indentLeft();
        p.printf("</True_Val>\n");
        p.printf("<False_Val>\n");
        p.indentRight();
        false_val.writeToStdOut(p);
        p.indentLeft();
        p.printf("</False_Val>\n");
        p.indentLeft();
        p.printf("</JTernaryExpression>\n");
    }
    
    @Override
    public void codegen(CLEmitter output) {
        // We should never reach here, i.e., all boolean
        // (including
        // identifier) expressions must override this method.
        System.err.println("Error in code generation");
    }

}
