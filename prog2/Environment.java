class Environment extends Node {

	private class NodeLookup {
		public Node node;
		public Frame container;

		public NodeLookup(Node n, Frame f) {
			node = n;
			container = f;
		}
	}

	private Frame scope;

	public static Environment topLevel;

	public Environment() {
		Frame builtIn = BuiltInFrame.Create();
		scope = new Frame(builtIn);
		topLevel = this;
	}

	public Environment(Frame s) {
		scope = s;
	}

	public Frame getScope() {
		return scope;
	}

	public boolean isEnvironment() {
		return true;
	}

	public void print(int n) {
		for (int i = 0; i < n; i++) {
			System.out.print(' ');
		}
		System.out.println("#{Environment");
		Frame cScope = scope;
		while (cScope != null) {
			cScope.print(n + 3);
			cScope = cScope.getParent();
		}
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.println('}');
	}

	private NodeLookup lookupNode(Node id) {
		Frame container = scope;

		Node val = scope.find(id);
		if (val != null) {
			return new NodeLookup(val, container);
		}

		do {
			container = container.getParent();
			if (container != null)
				val = container.find(id);
		} while (val == null && container != null);

		return new NodeLookup(val, container);
	}

	public Node lookup(Node id) {
		NodeLookup n = lookupNode(id);
		return n.node;
	}

	public void define(Node id, Node val) {
		NodeLookup n = lookupNode(id);
		if (n.node == null) {
			scope.define(id, val);

		} else {
			n.container.define(id, val);
		}
	}

}
