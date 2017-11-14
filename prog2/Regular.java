import java.io.*;

class Regular extends Special {

	private Node root;
	private static boolean applyNextProc = true;
	private static boolean innerProcEvaluated = false;

	public Regular(Node t) {
		root = t;
	}

	void print(Node t, int n, boolean p) {
		Printer.printRegular(t, n, p);
	}

	public Node eval(Environment env) {

		boolean localApplyNextProc = applyNextProc;

		Node n1 = root.getCar();
		Node n2 = root.getCdr();

		boolean n1_quoted = n1.isPair() && (((Cons) n1).getForm() instanceof Quote);
		boolean nestedList = n1.isPair() && !n1_quoted;
		applyNextProc = nestedList;
		innerProcEvaluated = false;

		Node n1Eval = n1.eval(env);

		if (n1Eval instanceof Error) {
			ResetFlags();
			return n1Eval;
		}

		if (n1Eval.isNull() && !n1_quoted && !n1.isSymbol()) {
			ResetFlags();
			return new Error("Invalid expression ()");

		}

		if (localApplyNextProc && !n1Eval.isProcedure() && (nestedList || !innerProcEvaluated)) {
			ResetFlags();
			return new Error("Attempt to call non-operator");
		}

		applyNextProc = false;
		Node n2Eval = n2.eval(env);
		if (n2Eval instanceof Error) {
			ResetFlags();
			return n2Eval;
		}

		applyNextProc = localApplyNextProc;
		innerProcEvaluated = false;
		if (applyNextProc && n1Eval.isProcedure()) {
			innerProcEvaluated = true;
			return n1Eval.apply(n2Eval);
		} else
			return new Cons(n1Eval, n2Eval);

	}

	private void ResetFlags() {
		applyNextProc = true;
		innerProcEvaluated = false;
	}
}
