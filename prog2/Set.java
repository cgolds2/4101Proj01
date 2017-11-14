import java.io.*;

class Set extends Special {

	private static Node syntaxError = new Error("syntax-violation - invalid expression [set!]");
	private Node root;

	public Set(Node t) {
		root = t;
	}

	void print(Node t, int n, boolean p) {
		Printer.printSet(t, n, p);
	}

	public Node eval(Environment env) {
		if (!twoArgs())
			return syntaxError;

		Node var = root.getCdr().getCar();
		if (!var.isSymbol())
			return syntaxError;
		if (env.lookup(var) == null)
			return new Error("Undefined variable");

		Node exp = root.getCdr().getCdr().getCar();
		Node evalExp = exp.eval(env);

		env.define(var, evalExp);
		return new Notice("Unspecific");
	}

	private boolean twoArgs() {
		return (root.getCdr().isPair() && root.getCdr().getCdr().isPair() && root.getCdr().getCdr().getCdr().isNull());
	}
}
