import java.io.*;

class Cond extends Special {

	private static Node syntaxError = new Error("Syntax-violation - use does not match definition of [cond]");

	private Node root;

	public Cond(Node t) {
		root = t;
	}

	void print(Node t, int n, boolean p) {
		Printer.printCond(t, n, p);
	}

	public Node eval(Environment env) {

		Node clauseRoot = root.getCdr();
		do {
			if (!clauseRoot.isPair())
				return syntaxError;
			Node clause = clauseRoot.getCar();
			if (!clause.isPair())
				return syntaxError;
			Node test = clause.getCar();
			Node expressions = clause.getCdr();
			if (test.isSymbol() && ((Ident) test).getName().equals("else")) {
				return evalExps(expressions, env);
			}

			Node testVal = test.eval(env);
			if (testVal instanceof Error)
				return testVal;

			Node evalC = evalClause(testVal, expressions, env);

			if (!(evalC instanceof Empty))
				return evalC;

			clauseRoot = clauseRoot.getCdr();

		} while (!clauseRoot.isNull());

		return new Notice(";no conditions evaluated to true");
	}

	private Node evalClause(Node testVal, Node expressions, Environment env) {
		if (testVal.isBoolean() && ((BooleanLit) testVal).getVal()) {
			if (expressions.isNull()) {
				return testVal;
			}

			if (expressions.isPair() && expressions.getCar().isSymbol()
					&& ((Ident) expressions.getCar()).getName().equals("=>")) {
				Node exp = expressions.getCdr();
				if (!exp.isPair())
					return syntaxError;
				Node evalExp = exp.getCar().eval(env);
				if (evalExp instanceof Error)
					return evalExp;
				return evalExp.apply(testVal);
			}

			return evalExps(expressions, env);
		}

		return new Empty();
	}

	private Node evalExps(Node expressions, Environment env) {

		Node expVal;
		do {
			if (!expressions.isPair())
				return syntaxError;
			Node expression = expressions.getCar();
			expVal = expression.eval(env);
			if (expVal instanceof Error)
				return expVal;
			expressions = expressions.getCdr();
		} while (!expressions.isNull());
		return expVal;
	}
}
