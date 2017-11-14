import java.util.Hashtable;

class Frame {
	private Hashtable<Node, Node> scope;
	private Frame parent;

	public Frame() {
		parent = null;
		scope = new Hashtable<Node, Node>();
	}

	public Frame(Frame p) {
		parent = p;
		scope = new Hashtable<Node, Node>();
	}

	public Frame getParent() {
		return parent;
	}

	public void define(Node id, Node val) {
		scope.put(id, val);
	}

	public Node find(Node id) {
		return scope.get(id);
	}

	public void print(int n) {
		for (Node key : scope.keySet()) {
			scope.get(key).print(n);
		}
	}
}
