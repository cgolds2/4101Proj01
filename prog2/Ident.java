class Ident extends Node {
	private String name;

	public Ident(String n) {
		name = n;
	}

	public void print(int n) {
		Printer.printIdent(n, name);
	}

	public boolean isSymbol() {
		return true;
	}

	public String getSymbol() {
		return name;
	}

	public String getName() {
		return name;
	}

	public Node eval(Environment env) {
		Node val = env.lookup(this);
		if (val == null)
			return new Error("Undefined variable " + name);
		return val;
	}

	public boolean equals(Object other) {
		if (!(other instanceof Ident))
			return false;
		Ident otherId = (Ident) other;
		return (this.name.equals(otherId.getName()));
	}

	public int hashCode() {
		int hash = 1;
		hash = hash * 31 + name.hashCode();
		return hash;
	}
}
