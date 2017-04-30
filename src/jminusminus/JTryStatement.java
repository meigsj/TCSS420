package jminusminus;

import java.util.*;

class JTryStatement extends JStatement {
	JStatement tryBlock;
	ArrayList<JFormalParameter> catchParams;
	ArrayList<JStatement> catchBlocks;
	JStatement finallyBlock;
	
	public JTryStatement(int line, JStatement tryBlock, ArrayList<JFormalParameter> catchParams,
			             ArrayList<JStatement> catchBlocks, JStatement finallyBlock) {
		super(line);
		this.tryBlock = tryBlock;
		this.catchParams = catchParams;
		this.catchBlocks = catchBlocks;
		this.finallyBlock = finallyBlock;
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
		 Iterator<JFormalParameter> paramIt =catchParams.iterator();
		 Iterator<JStatement> cblockIt = catchBlocks.iterator();
		 p.printf("<JTryStatement line=\"%d\">\n", line());
	     p.indentRight();
		 p.printf("<TryBlock>\n");
		 p.indentRight();
		 tryBlock.writeToStdOut(p);
		 p.indentLeft();
		 p.printf("</TryBlock>\n");

	     while (paramIt.hasNext() && cblockIt.hasNext()) {
		    JFormalParameter param = paramIt.next();
		     JStatement cblock = cblockIt.next();
	    	 p.printf("<CatchParameter>\n");
		     p.indentRight();
		     param.writeToStdOut(p);
		     p.indentLeft();
		     p.printf("</CatchParameter>\n");
		     p.printf("<CatchBlock>\n");
		     p.indentRight();
			 cblock.writeToStdOut(p);
			 p.indentLeft();
			 p.printf("</CatchBlock>\n");
	     }

	     if (finallyBlock != null) {
		     p.printf("<FinallyBlock>\n");
		     p.indentRight();
		     finallyBlock.writeToStdOut(p);
		     p.indentLeft();
		     p.printf("</FinallyBlock>\n");
	     }
		 p.printf("</JTryStatement>\n");	

	}

}
