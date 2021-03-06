// Copyright 2013 Bill Campbell, Swami Iyer and Bahar Akbal-Delibas

package jminusminus;

import static jminusminus.CLConstants.*;

/**
 * The AST node for a binary expression. A binary expression has an operator and
 * two operands: a lhs and a rhs.
 */

abstract class JBinaryExpression extends JExpression {

    /** The binary operator. */
    protected String operator;

    /** The lhs operand. */
    protected JExpression lhs;

    /** The rhs operand. */
    protected JExpression rhs;

    /**
     * Construct an AST node for a binary expression given its line number, the
     * binary operator, and lhs and rhs operands.
     * 
     * @param line
     *            line in which the binary expression occurs in the source file.
     * @param operator
     *            the binary operator.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    protected JBinaryExpression(int line, String operator, JExpression lhs,
            JExpression rhs) {
        super(line);
        this.operator = operator;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * @inheritDoc
     */

    public void writeToStdOut(PrettyPrinter p) {
        p.printf("<JBinaryExpression line=\"%d\" type=\"%s\" "
                + "operator=\"%s\">\n", line(), ((type == null) ? "" : type
                .toString()), Util.escapeSpecialXMLChars(operator));
        p.indentRight();
        p.printf("<Lhs>\n");
        p.indentRight();
        lhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Lhs>\n");
        p.printf("<Rhs>\n");
        p.indentRight();
        rhs.writeToStdOut(p);
        p.indentLeft();
        p.printf("</Rhs>\n");
        p.indentLeft();
        p.printf("</JBinaryExpression>\n");
    }

}

/**
 * The AST node for a plus (+) expression. In j--, as in Java, + is overloaded
 * to denote addition for numbers and concatenation for Strings.
 */

class JPlusOp extends JBinaryExpression {

    /**
     * Construct an AST node for an addition expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the addition expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JPlusOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "+", lhs, rhs);
    }

    /**
     * Analysis involves first analyzing the operands. If this is a string
     * concatenation, we rewrite the subtree to make that explicit (and analyze
     * that). Otherwise we check the types of the addition operands and compute
     * the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.STRING || rhs.type() == Type.STRING) {
            return (new JStringConcatenationOp(line, lhs, rhs))
                    .analyze(context);
        } else if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
        		||lhs.type() == Type.INT && rhs.type() == Type.LONG
        		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for +");
        }
        return this;
    }

    /**
     * Any string concatenation has been rewritten as a JStringConcatenationOp
     * (in analyze()), so code generation here involves simply generating code
     * for loading the operands onto the stack and then generating the
     * appropriate add instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IADD);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LADD);
        }
    }

}

/**
 * The AST node for a subtraction (-) expression.
 */

class JSubtractOp extends JBinaryExpression {

    /**
     * Construct an AST node for a subtraction expression given its line number,
     * and lhs and rhs operands.
     * 
     * @param line
     *            line in which the subtraction expression occurs in the source
     *            file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JSubtractOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "-", lhs, rhs);
    }

    /**
     * Analyzing the - operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for -");
        }     

        return this;
    }

    /**
     * Generating code for the - operation involves generating code for the two
     * operands, and then the subtraction instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISUB);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LSUB);
        }
    }

}

/**
 * The AST node for a multiplication (*) expression.
 */

class JMultiplyOp extends JBinaryExpression {

    /**
     * Construct an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the multiplication expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JMultiplyOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "*", lhs, rhs);
    }

    /**
     * Analyzing the * operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for *");
        }     

        return this;
    }

    /**
     * Generating code for the * operation involves generating code for the two
     * operands, and then the multiplication instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IMUL);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LMUL);
        }
    }
}

/**
 * The AST node for a Division (/) expression.
 */

class JDivOp extends JBinaryExpression {

    /**
     * Construct an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the Division expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JDivOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "/", lhs, rhs);
    }

    /**
     * Analyzing the * operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for /");
        }     

        return this;
    }

    /**
     * Generating code for the / operation involves generating code for the two
     * operands, and then the Divesision instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IDIV);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LDIV);
        }
    }
}

/**
 * The AST node for a Modulo (%) expression.
 */

class JMOdOp extends JBinaryExpression {

    /**
     * Construct an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the Division expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JMOdOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "%", lhs, rhs);
    }

    /**
     * Analyzing the % operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for %");
        }     

        return this;
    }

    /**
     * Generating code for the % operation involves generating code for the two
     * operands, and then the Modulo instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IREM);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LREM);
        }
    }
}

/**
 * The AST node for a Bitwise And (&) expression.
 */

class JBitwiseAndOp extends JBinaryExpression {

    /**
     * Construct an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the Division expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitwiseAndOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "&", lhs, rhs);
    }

    /**
     * Analyzing the % operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for &");
        }     

        return this;
    }

    /**
     * Generating code for the & operation involves generating code for the two
     * operands, and then the multiplication instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IAND);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LAND);
        }
    }
}

/**
 * The AST node for a Bitwise Or (|) expression.
 */

class JBitwiseOROp extends JBinaryExpression {

    /**
     * Construct an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the Division expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitwiseOROp(int line, JExpression lhs, JExpression rhs) {
        super(line, "|", lhs, rhs);
    }

    /**
     * Analyzing the | operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for |");
        }     

        return this;
    }

    /**
     * Generating code for the % operation involves generating code for the two
     * operands, and then the multiplication instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IOR);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LOR);
        }
    }
}

/**
 * The AST node for a Bitwise XOR (^) expression.
 */

class JBitwiseXOROp extends JBinaryExpression {

    /**
     * Construct an AST for a multiplication expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the Division expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitwiseXOROp(int line, JExpression lhs, JExpression rhs) {
        super(line, "^", lhs, rhs);
    }

    /**
     * Analyzing the ^ operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for ^");
        }     

        return this;
    }

    /**
     * Generating code for the ^ operation involves generating code for the two
     * operands, and then the multiplication instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(IXOR);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LXOR);
        }
    }
}
 
/**
 * The AST node for a BitShiftLeft (<<) expression.
 */

class JBitShiftLeftOp extends JBinaryExpression {

    /**
     * Construct an AST for a BitShiftLeft expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the BitShiftLeft expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitShiftLeftOp(int line, JExpression lhs, JExpression rhs) {
        super(line, "<<", lhs, rhs);
    }

    /**
     * Analyzing the << operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for <<");
        }     

        return this;
    }

    /**
     * Generating code for the << operation involves generating code for the two
     * operands, and then the multiplication instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
        if (type == Type.INT) {
            lhs.codegen(output);
            rhs.codegen(output);
            output.addNoArgInstruction(ISHL);
        } else if(type == Type.LONG) {
        	if(rhs.type() == Type.INT) {
        		lhs.codegen(output);
        		rhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        	} else if(lhs.type() == Type.INT) {
        		lhs.codegen(output);
        		output.addNoArgInstruction(I2L);
        		rhs.codegen(output);
        	} else {
        		lhs.codegen(output);
        		rhs.codegen(output);
        	}  	
            output.addNoArgInstruction(LSHL);
        }
    }
}

/**
 * The AST node for a BitShiftRight (>>) expression.
 */

class JBitShiftRightOp extends JBinaryExpression {

    /**
     * Construct an AST for a BitShiftRight expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the BitShiftRight expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitShiftRightOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>", lhs, rhs);
    }

    /**
     * Analyzing the >> operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for >>");
        }     

        return this;
    }

    /**
     * Generating code for the >> operation involves generating code for the two
     * operands, and then the multiplication instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
    	 if (type == Type.INT) {
             lhs.codegen(output);
             rhs.codegen(output);
             output.addNoArgInstruction(ISHR);
         } else if(type == Type.LONG) {
         	if(rhs.type() == Type.INT) {
         		lhs.codegen(output);
         		rhs.codegen(output);
         		output.addNoArgInstruction(I2L);
         	} else if(lhs.type() == Type.INT) {
         		lhs.codegen(output);
         		output.addNoArgInstruction(I2L);
         		rhs.codegen(output);
         	} else {
         		lhs.codegen(output);
         		rhs.codegen(output);
         	}  	
             output.addNoArgInstruction(LSHR);
         }
    }
}

/**
 * The AST node for a BitShiftRightUnsigned (>>>) expression.
 */

class JBitShiftRightUnsignedOp extends JBinaryExpression {

    /**
     * Construct an AST for a BitShiftRightUnsigned expression given its line number,
     * and the lhs and rhs operands.
     * 
     * @param line
     *            line in which the BitShiftRightUnsigned expression occurs in the
     *            source file.
     * @param lhs
     *            the lhs operand.
     * @param rhs
     *            the rhs operand.
     */

    public JBitShiftRightUnsignedOp(int line, JExpression lhs, JExpression rhs) {
        super(line, ">>>", lhs, rhs);
    }

    /**
     * Analyzing the >>> operation involves analyzing its operands, checking
     * types, and determining the result type.
     * 
     * @param context
     *            context in which names are resolved.
     * @return the analyzed (and possibly rewritten) AST subtree.
     */

    public JExpression analyze(Context context) {
        lhs = (JExpression) lhs.analyze(context);
        rhs = (JExpression) rhs.analyze(context);
        if (lhs.type() == Type.INT && rhs.type() == Type.INT) {
            type = Type.INT;
        } else if(lhs.type() == Type.LONG && rhs.type() == Type.INT 
    		||lhs.type() == Type.INT && rhs.type() == Type.LONG
    		||lhs.type() == Type.LONG && rhs.type() == Type.LONG) {
        	type = Type.LONG;
    	}else {
            type = Type.ANY;
            JAST.compilationUnit.reportSemanticError(line(),
                    "Invalid operand types for >>>");
        }     

        return this;
    }

    /**
     * Generating code for the % operation involves generating code for the two
     * operands, and then the multiplication instruction.
     * 
     * @param output
     *            the code emitter (basically an abstraction for producing the
     *            .class file).
     */

    public void codegen(CLEmitter output) {
   	 if (type == Type.INT) {
         lhs.codegen(output);
         rhs.codegen(output);
         output.addNoArgInstruction(IUSHR);
     } else if(type == Type.LONG) {
     	if(rhs.type() == Type.INT) {
     		lhs.codegen(output);
     		rhs.codegen(output);
     		output.addNoArgInstruction(I2L);
     	} else if(lhs.type() == Type.INT) {
     		lhs.codegen(output);
     		output.addNoArgInstruction(I2L);
     		rhs.codegen(output);
     	} else {
     		lhs.codegen(output);
     		rhs.codegen(output);
     	}  	
         output.addNoArgInstruction(LUSHR);
     }
    }
}