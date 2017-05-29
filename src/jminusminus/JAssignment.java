// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for an assignment statement. This is an abtract class into which
 * we factor behavior common to all assignment operations.
 */

abstract class JAssignment extends JBinaryExpression {

    /**
     * Construct an AST node for an assignment operation.
     * 
     * @param line
     *            line in which the assignment operation occurs in the source
     *            file.
     * @param operator
     *            the actual assignment operator.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JAssignment(int line, String operator, JExpression lhs,
            JExpression rhs) {
        super(line, operator, lhs, rhs);
    }

}

/**
 * The AST node for an assignment (=) expression. The = operator has two
 * operands: a lhs and a rhs.
 */

class JAssignOp extends JAssignment {

    /**
     * Construct the AST node for an assignment (=) expression given the lhs and
     * rhs operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            lhs operand.
     * @param rhs
     *            rhs operand.
     */

    public JAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "=", lhs, rhs);
    }

    /**
     * Analyze the lhs and rhs, checking that types match, and set the result
     * type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        rhs.type().mustMatchExpected(line(), lhs.type());
        type = rhs.type();
        if (lhs instanceof JVariable) {
            IDefn defn = ((JVariable) lhs).iDefn();
            if (defn != null) {
                // Local variable; consider it to be initialized now.
                ((LocalVariableDefn) defn).initialize();
            }
        }
        return this;
    }

    /**
     * Code generation for an assignment involves, generating code for loading
     * any necessary Lvalue onto the stack, for loading the Rvalue, for (unless
     * a statement) copying the Rvalue to its proper place on the stack, and for
     * doing the store.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        rhs.codegen(output);
        if (!isStatementExpression) {
            // Generate code to leave the Rvalue atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }

}

/**
 * The AST node for a += expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JPlusAssignOp extends JAssignment {

    /**
     * Construct the AST node for a += expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JPlusAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+=", lhs, rhs);
    }

    /**
     * Analyze the lhs and rhs, rewrite rhs as lhs + rhs (string concatenation)
     * if lhs is a String, and set the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if (lhs.type().equals(Type.STRING)) {
            rhs = (new JStringConcatenationOp(line, lhs, rhs)).analyze(context);
            type = Type.STRING;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for +=: " + lhs.type());
        }
        return this;
    }

    /**
     * Code generation for += involves, generating code for loading any
     * necessary l-value onto the stack, for (unless a string concatenation)
     * loading the r-value, for (unless a statement) copying the r-value to its
     * proper place on the stack, and for doing the store.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if (lhs.type().equals(Type.STRING)) {
            rhs.codegen(output);
        } else if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IADD);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LADD);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
     }       
}


/**
 * The AST node for a -= expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JMinusAssignOp extends JAssignment {

    /**
     * Construct the AST node for a -= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JMinusAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {

        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISUB);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LSUB);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
    
    public JExpression analyze(Context context) {

    	if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for -=: " + lhs.type());
        }
        return this;
    }   
}

/**
 * The AST node for a *= expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JMultAssignOp extends JAssignment {

    /**
     * Construct the AST node for a *= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JMultAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IMUL);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LMUL);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
    public JExpression analyze(Context context) {
    	if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for *=: " + lhs.type());
        }
        return this;
    }   
}

/**
 * The AST node for a /= expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JDivAssignOp extends JAssignment {

    /**
     * Construct the AST node for a /= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JDivAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IDIV);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LDIV);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
    public JExpression analyze(Context context) {
    	if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for /=: " + lhs.type());
        }
        return this;
    }   
}

/**
 * The AST node for a %= expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JModAssignOp extends JAssignment {

    /**
     * Construct the AST node for a %= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JModAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IREM);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LREM);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
    public JExpression analyze(Context context) {
    	if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for %=: " + lhs.type());
        }
        return this;
    }   
}

/**
 * The AST node for a &= expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JAndAssignOp extends JAssignment {

    /**
     * Construct the AST node for a &= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JAndAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IAND);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LAND);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
    public JExpression analyze(Context context) {
    	if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for &=: " + lhs.type());
        }
        return this;
    }   
}

/**
 * The AST node for a ^= expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JXORAssignOp extends JAssignment {

    /**
     * Construct the AST node for a ^= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JXORAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IXOR);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LXOR);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
    public JExpression analyze(Context context) {
    	if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for ^=: " + lhs.type());
        }
        return this;
    }   
}

/**
 * The AST node for a |= expression. A += expression has two operands: a lhs and
 * a rhs.
 */

class JOrAssignOp extends JAssignment {

    /**
     * Construct the AST node for a |= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JOrAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {
        ((JLhs) lhs).codegenLoadLhsLvalue(output);
        if(lhs.type().equals(Type.INT))  {
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IOR);
        } else  { // Added for 5.21 //lhs == LONG
            ((JLhs) lhs).codegenLoadLhsRvalue(output);
        	if(rhs.type() == Type.INT) {
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else {
        		rhs.codegen(output);
        	}       
            output.addNoArgInstruction(LOR);
        }
        if (!isStatementExpression) {
            // Generate code to leave the r-value atop stack
            ((JLhs) lhs).codegenDuplicateRvalue(output);
        }
        ((JLhs) lhs).codegenStore(output);
    }
    public JExpression analyze(Context context) {
    	if (!(lhs instanceof JLhs)) {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Illegal lhs for assignment");
	    return this;
        } else {
            lhs = (JExpression) ((JLhs) lhs).analyzeLhs(context);
        }
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type().equals(Type.INT)) {
            rhs.type().mustMatchExpected(line(), Type.INT);
            type = Type.INT;
        } else if(lhs.type().equals(Type.LONG)) {
        	if(rhs.type().equals(Type.LONG)) {
            	rhs.type().mustMatchExpected(line(), Type.LONG);
        	} else if(rhs.type().equals(Type.INT)) {
            	rhs.type().mustMatchExpected(line(), Type.INT);
        	} else {
        		rhs.type().mustMatchExpected(line(), Type.STRING);//BREAKS IF NOT INT OR LONG
        	}
        	type = Type.LONG;
        } else {
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid lhs type for ^=: " + lhs.type());
        }
        return this;
    }   
}

/**
 * The AST node for a <<= expression. A <<= expression has two operands: a lhs and
 * a rhs.
 */

class JBitShiftLeftAssignOp extends JAssignment {

    /**
     * Construct the AST node for a <<= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitShiftLeftAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {

    }
    public JExpression analyze(Context context) {

            return this;
    }   
}

/**
 * The AST node for a >>= expression. A >>= expression has two operands: a lhs and
 * a rhs.
 */

class JBitShiftRightAssignOp extends JAssignment {

    /**
     * Construct the AST node for a >>= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitShiftRightAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {

    }
    public JExpression analyze(Context context) {

            return this;
    }   
}

/**
 * The AST node for a >>>= expression. A >>>= expression has two operands: a lhs and
 * a rhs.
 */

class JBitShiftUnsignedRightAssignOp extends JAssignment {

    /**
     * Construct the AST node for a >>>= expression given its lhs and rhs
     * operands.
     * 
     * @param line
     *            line in which the assignment expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitShiftUnsignedRightAssignOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>=", lhs, rhs);
    }

    public void codegen(CLEmitter output) {

    }
    public JExpression analyze(Context context) {

            return this;
    }   
}
