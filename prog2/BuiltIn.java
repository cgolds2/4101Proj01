class BuiltIn extends Node {
	private Node symbol;

	public BuiltIn(Node s) {

		symbol = s;
	}

	public Node getSymbol() {
		return symbol;
	}

	public boolean isProcedure() {
		return true;
	}

	public void print(int n) {
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.println("#{Built-in Procedure");
		symbol.print(n + 3);
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.println('}');
	}

	public Node apply(Node args) {
		String name = ((Ident) symbol).getName();
		if (name.equals("b+") || name.equals("b-") || name.equals("b*") || name.equals("b/") || name.equals("b<")
				|| name.equals("b>") || name.equals("b=")) {
			return binaryArithmetic(name, args);
		}

		else if (name.equals("number?")) {
			if (!oneArgument(args))
				System.out.println("Invalid number of arguments");
			return BooleanLit.getInstance(args.getCar().isNumber());
		} else if (name.equals("symbol?")) {
			if (!oneArgument(args))
				System.out.println("Invalid number of arguments");
			Node arg = args.getCar();
			return BooleanLit.getInstance(arg.isSymbol());
		} else if (name.equals("procedure?")) {
			if (!oneArgument(args))
				System.out.println("Invalid number of arguments");
			return BooleanLit.getInstance(args.getCar().isProcedure());
		} else if (name.equals("null?")) {
			if (!oneArgument(args))
				System.out.println("Invalid number of arguments");
			return BooleanLit.getInstance(args.getCar().isNull());
		} else if (name.equals("pair?")) {
			if (!oneArgument(args))
				System.out.println("Invalid number of arguments");
			return BooleanLit.getInstance(args.getCar().isPair());
		} else if (name.equals("eq?")) {
			if (!twoArguments(args))
				System.out.println("Invalid number of arguments");
			return BooleanLit.getInstance(args.getCar() == args.getCdr().getCar());
		} else if (name.equals("car")) {
			if (!listArgument(args))
				System.out.println("Argument must be a list");
			return args.getCar().getCar();
		} else if (name.equals("cdr")) {
			if (!listArgument(args))
				System.out.println("Argument must be a list");
			return args.getCar().getCdr();
		} else if (name.equals("cons")) {
			if (!twoArguments(args))
				System.out.println("Invalid number of arguments");
			return new Cons(args.getCar(), args.getCdr().getCar());
		} else if (name.equals("set-car!")) {
			if (!twoArguments(args))
				System.out.println("Invalid number of arguments");
			Node list = args.getCar();
			if (!list.isPair())
				System.out.println("First argument must be a list");
			list.setCar(args.getCdr().getCar());
			return list;
		} else if (name.equals("set-cdr!")) {
			if (!twoArguments(args))
				System.out.println("Invalid number of arguments");
			Node list = args.getCar();
			if (!list.isPair())
				System.out.println("First argument must be a list");
			list.setCdr(args.getCdr().getCar());
			return list;
		}

		// IO operations
		else if (name.equals("display")) {
			if (args.isNull())
				return this;
			else if (!oneArgument(args))
				System.out.println("Invalid number of arguments");
			Node a = args.getCar().isPair() ? args.getCar().getCar() : args.getCar();
			a.print(0);
			return Empty.getInstance();
		} else if (name.equals("write")) {
			if (args.isNull())
				return this;
			else if (!oneArgument(args))
				System.out.println("Invalid number of arguments");
			return args.getCar();
		} else if (name.equals("read")) {
			Scanner scanner = new Scanner(System.in);
			Parser parser = new Parser(scanner);
			return parser.parseExp();
		} else if (name.equals("newline")) {
			return new Empty();
		} else if (name.equals("interaction-environment")) {
			return Environment.topLevel;
		} else if (name.equals("eval")) {
			if (!twoArguments(args))
				System.out.println("Invalid number of arguments");
			Node env = args.getCdr().getCar();
			if (!env.isEnvironment())
				System.out.println("Second argument must specify an environment");
			Node exp = args.getCar();
			Node expCheck = exp.isPair() ? exp.getCar() : exp;
			if (!expCheck.isSymbol())
				System.out.println("Invalid Scheme expression as argument");
			return exp.eval((Environment) env);
		} else if (name.equals("apply")) {
			if (!twoArguments(args))
				System.out.println("Invalid number of arguments");
			Node func = args.getCar();
			Node arg_list = args.getCdr();
			if (!func.isProcedure())
				System.out.println("First argument invalid, type should be procedure");
			if (!arg_list.isPair() || !arg_list.getCar().isPair())
				System.out.println("Second argument invalid, type should be a list");
			return func.apply(arg_list.getCar());
		} else
			System.out.println("Built-in method not found");

		return null;
	}

	private Node binaryArithmetic(String name, Node args) {
		if (!twoArguments(args)) {
			System.out.println("Binary arithmetic requires two arguments");
		}
		Node arg1 = args.getCar();
		Node arg2 = args.getCdr().getCar();
		if (!arg1.isNumber() || !arg2.isNumber()) {
			System.out.println(String.format("Invalid argument types for binary arithmetic [%s]", name));
		}
		int val1 = ((IntLit) arg1).getIntVal();
		int val2 = ((IntLit) arg2).getIntVal();

		if (name.equals("b+"))
			return new IntLit(val1 + val2);
		else if (name.equals("b-"))
			return new IntLit(val1 - val2);
		else if (name.equals("b*"))
			return new IntLit(val1 * val2);
		else if (name.equals("b/"))
			return new IntLit(val1 / val2);
		else if (name.equals("b="))
			return BooleanLit.getInstance(val1 == val2);
		else if (name.equals("b<"))
			return BooleanLit.getInstance(val1 < val2);
		else if (name.equals("b>"))
			return BooleanLit.getInstance(val1 > val2);
		else
			System.out.println("Not a valid arithmetic operator.");
		return null;
	}

	private boolean oneArgument(Node args) {
		return (args.isPair() && args.getCdr().isNull());
	}

	private boolean twoArguments(Node args) {
		return (args.isPair() && args.getCdr().isPair() && args.getCdr().getCdr().isNull());
	}

	private boolean listArgument(Node args) {
		return (oneArgument(args) && args.getCar().isPair());
	}

}
