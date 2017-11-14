class Define extends Special {

	private Node root;

	public Define(Node t) {
		root = t;
	}

	public Node eval(Environment env) {
		if (root.getCdr().getCar().isPair()) {
			Node var = root.getCdr().getCar().getCar();
			if (var.isSymbol() && var.getName() != "quote") {
				if (!root.getCdr().getCar().getCdr().isNull()) {
					root.getCdr().setCar(root.getCdr().getCar().getCdr());
				} else {
					root.getCdr().setCar(Nil.getInstance());
				}
				Node val = new Lambda(root).eval(env);
				if (val instanceof Error)
					return val;
				env.define(var, val);
			} else {
				return new Error("sytax-violation:" + "ill-formed definition [syntax-rules]");
			}
		} else if (!root.getCdr().getCar().isSymbol()) {
			return new Error("assertion-violation:" + "argument of wrong type [symbol->string]");
		} else if (root.getCdr().getCdr().getCar().isPair()
				&& root.getCdr().getCdr().getCar().getCar().getName() != "quote") {
			Node val = root.getCdr().getCdr().getCar().eval(env);
			if (val instanceof Error)
				return val;
			Node var = root.getCdr().getCar();
			env.define(var, val);
		} else {
			Node val = root.getCdr().getCdr().getCar().eval(env);
			if (val instanceof Error)
				return val;
			env.define(root.getCdr().getCar(), val);
		}
		return new Notice("; no values returned");
	}

	void print(Node t, int n, boolean p) {
		Printer.printDefine(t, n, p);
	}
}
