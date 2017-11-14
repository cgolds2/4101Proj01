
class BuiltInFrame {
	public static Frame Create() {
		Frame f = new Frame();

		addBuiltIn(f, "read");
		addBuiltIn(f, "write");
		addBuiltIn(f, "display");
		addBuiltIn(f, "newline");
		addBuiltIn(f, "b+");
		addBuiltIn(f, "b-");
		addBuiltIn(f, "b*");
		addBuiltIn(f, "b/");
		addBuiltIn(f, "b=");
		addBuiltIn(f, "b<");
		addBuiltIn(f, "b>");
		addBuiltIn(f, "car");
		addBuiltIn(f, "cdr");
		addBuiltIn(f, "cons");
		addBuiltIn(f, "set-car!");
		addBuiltIn(f, "set-cdr!");
		addBuiltIn(f, "symbol?");
		addBuiltIn(f, "number?");
		addBuiltIn(f, "null?");
		addBuiltIn(f, "pair?");
		addBuiltIn(f, "eq?");
		addBuiltIn(f, "procedure?");
		addBuiltIn(f, "eval");
		addBuiltIn(f, "apply");
		addBuiltIn(f, "interaction-environment");

		return f;
	}

	private static void addBuiltIn(Frame f, String name) {
		Node id = new Ident(name);
		f.define(id, new BuiltIn(id));
	}
}
