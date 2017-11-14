import java.io.*;

class Begin extends Special {

	private Node root;

	public Begin(Node t) {
		root = t;
	}

	void print(Node t, int n, boolean p) {
		Printer.printBegin(t, n, p);

	}

	public Node eval(Environment env) {
		Node expRoot = root.getCdr();
		Node evalNode = expRoot;
		while (expRoot.isPair()) {
			Node exp = expRoot.getCar();
			evalNode = exp.eval(env);
			expRoot = expRoot.getCdr();
		}
		return evalNode;
	}
}
