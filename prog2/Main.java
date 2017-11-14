public class Main {
	private static final String TokenName[] = { "QUOTE", // '
			"LPAREN", "RPAREN", "DOT", "TRUE", "FALSE", "INT", "STRING", "IDENT" };

	public static void main(String argv[]) {
		Scanner scanner = new Scanner(System.in);

		if (argv.length > 1) {
			System.err.println("Usage: java Main [-d]");
			System.exit(1);
		}
		if (argv.length == 1 && argv[0].equals("-d")) {
			Token tok = scanner.getNextToken();
			while (tok != null) {
				int tt = tok.getType();
				System.out.print(TokenName[tt]);
				if (tt == Token.INT)
					System.out.println(", intVal = " + tok.getIntVal());
				else if (tt == Token.STRING)
					System.out.println(", strVal = " + tok.getStrVal());
				else if (tt == Token.IDENT)
					System.out.println(", name = " + tok.getName());
				else
					System.out.println();

				tok = scanner.getNextToken();
			}
			System.exit(0);
		}

		Parser parser = new Parser(scanner);
		Node root;

		Environment env = new Environment();

		root = parser.parseExp();
		while (root != null) {
			root.eval(env).print(0);
			root = parser.parseExp();
		}
		System.exit(0);
	}

}
