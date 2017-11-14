class Lambda extends Special {

	Node func;

	public Lambda(Node t) {
		func = (t.getCdr());
	}

	public Node eval(Environment env) {
		return new Closure(func, env);
	}

	void print(Node t, int n, boolean p) {
		Printer.printLambda(t, n, true);
	}

}
