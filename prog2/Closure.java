class Closure extends Node {
	private Node func, result;
	private Environment env;

	private Node params, body, args;
	Frame fScope;

	public Closure(Node f, Environment e) {
		func = f;
		env = e;
		params = func.getCar();
		body = func.getCdr().getCar();
	}

	public Node getFun() {
		return func;
	}

	public Environment getEnv() {
		return env;
	}

	public boolean isProcedure() {
		return true;
	}

	public void print(int n) {
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.println("#{Procedure");
		func.print(n + 3);
		for (int i = 0; i < n; i++)
			System.out.print(' ');
		System.out.print('}');
	}

	public Node apply(Node args) {
		fScope = new Frame(env.getScope());
		Environment f1 = new Environment(fScope);
		this.args = args;
		Node evalArgs = args;
		if (evalArgs.isNull() && !params.isNull()) {
			return result = new Cons(new Error("assertion-violation: wrong number of arguments [tail-call]"),
					this.args);
		} else if (!evalArgs.isNull() && params.isNull()) {
			return result = new Cons(new Error("assertion-violation: wrong number of arguments [tail-call]"),
					this.args);
		} else if (evalArgs.isNull() && params.isNull()) {
			result = body.eval(f1);
		} else if (!evalArgs.isNull() && !params.isNull()) {
			Node lParams = params;
			while (!lParams.getCdr().isNull() && !args.getCdr().isNull()) {
				fScope.define(lParams.getCar(), args.getCar());
				lParams = lParams.getCdr();
				args = args.getCdr();
			}
			if (lParams.getCdr().isNull() != args.getCdr().isNull()) {
				return result = new Cons(new Error("assertion-violation: wrong number of arguments [tail-call]"),
						this.args);
			}
			fScope.define(lParams.getCar(), args.getCar());
			result = body.eval(f1);

		} else {
			result = body.eval(f1);
		}

		if (result instanceof Error || !result.isPair())
			return result;
		else
			return result.getCar();
	}

}
