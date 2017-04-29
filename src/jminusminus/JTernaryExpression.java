package jminusminus;

public class JTernaryExpression extends JExpression {
	


	/**
     * The analysis of any JExpression returns a JExpression. That's all this
     * (re-)declaration of analyze() says.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public abstract JExpression analyze(Context context);
	

}
