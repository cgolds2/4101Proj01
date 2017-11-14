import java.io.*;

class If extends Special {

	private Node root;

	public If(Node t) {
		root = t;
	}

	void print(Node t, int n, boolean p) {
		Printer.printIf(t, n, p);
	}

	public Node eval(Environment env) {
		Node conditionAction = root.getCdr();
		if (!twoArguments(conditionAction) || moreThanThreeArguments(conditionAction))
			return new Error("Syntax violation: invalid expression [if]");

		Node cond = conditionAction.getCar();
		Node consequent = conditionAction.getCdr().getCar();
		Node alternate = (threeArguments(conditionAction)) ? conditionAction.getCdr().getCdr().getCar() : null;

		cond = cond.eval(env);
		if (cond instanceof Error)
			return cond;
		if (!(cond instanceof BooleanLit))
			return new Error("Invalid condition");

		boolean condVal = ((BooleanLit) cond).getVal();
		if (condVal)
			return consequent.eval(env);
		else if (alternate != null)
			return alternate.eval(env);
		else
			return new Notice("Unspecific");
	}

	private boolean twoArguments(Node args) {
		return (args.isPair() && args.getCdr().isPair());
	}

	private boolean threeArguments(Node args) {
		return (args.isPair() && args.getCdr().isPair() && args.getCdr().getCdr().isPair());
	}

	private boolean moreThanThreeArguments(Node args) {
		return (threeArguments(args) && !args.getCdr().getCdr().getCdr().isNull());
	}
}
