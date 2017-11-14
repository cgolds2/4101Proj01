class Let extends Special {

	Node root;
	Environment localEnv;

	public Let(Node t) {
		root = t;
	}

	public Node eval(Environment env) {
		if (root.getCdr().isPair() && root.getCdr().getCar().isPair()) {
			Node def = localDefine(env, root.getCdr().getCar());
			if (def instanceof Error) {
				return def;
			}
			return root.getCdr().getCdr().getCar().eval(localEnv);
		} else if (root.getCdr().isPair() && root.getCdr().getCar().isSymbol()) {
			Node func = root.getCdr().getCar();
			return new Cons(func, evalHelper(env)).eval(env);
		}
		return new Error("syntax-violation - invalid expression [let]");
	}

	public Node evalHelper(Environment env) {
		root = root.getCdr();
		return eval(env);
	}

	private Node localDefine(Environment env, Node head) {
		Frame localScope = new Frame(env.getScope());
		localEnv = new Environment(localScope);

		do {
			if (!head.getCar().isPair() || !head.getCar().getCdr().isPair())
				return new Error("Invalid binding expression");
			Node var = head.getCar().getCar();
			Node lookUp = localScope.find(var);
			if (lookUp != null)
				return new Error("Variable defined more than once in [let] bindings.");
			Node value = head.getCar().getCdr().getCar();
			value = value.eval(env);
			localEnv.define(var, value);
			head = head.getCdr();
		} while (!head.isNull());

		return Empty.getInstance();
	}

	void print(Node t, int n, boolean p) {
		Printer.printLet(t, n, p);
	}
}
