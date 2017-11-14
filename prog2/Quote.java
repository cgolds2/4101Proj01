class Quote extends Special {

	private Node root;

	public Quote(Node t) {
		root = t;
	}

	void print(Node t, int n, boolean p) {
		Printer.printQuote(t, n, p);
	}

	public Node eval(Environment env) {
		return root.getCdr().getCar();
	}

}
