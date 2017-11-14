class Parser {
	private Scanner scanner;

	public Parser(Scanner s) {
		this.scanner = s;
	}

	public Node parseExp() {
		Token tok = this.scanner.getNextToken();
		return this.parseExp(tok);
	}

	private Node parseExp(Token tok) {
		if (tok == null) {
			return null;
		} else if (tok.getType() == 6) {
			return new IntLit(tok.getIntVal());
		} else if (tok.getType() == 7) {
			return new StrLit(tok.getStrVal());
		} else if (tok.getType() == 4) {
			return BooleanLit.getInstance(true);
		} else if (tok.getType() == 5) {
			return BooleanLit.getInstance(false);
		} else if (tok.getType() == 8) {
			return new Ident(tok.getName());
		} else if (tok.getType() == 0) {
			Node t = this.parseExp();
			return new Cons(new Ident("quote"), new Cons(t, Nil.getInstance()));
		} else if (tok.getType() == 1) {
			return this.parseRest();
		} else if (tok.getType() == 3) {
			System.out.println("Parse error: illegal dot in expression");
			return this.parseExp();
		} else {
			System.out.println("Parse error: illegal right parenthesis in expression");
			return this.parseExp();
		}
	}

	protected Node parseRest() {
		Token tok = this.scanner.getNextToken();
		return this.parseRest(tok);
	}

	private Node parseRest(Token tok) {
		if (tok == null) {
			System.err.println("Parse error: end of file in list");
			return null;
		} else if (tok.getType() == 2) {
			return Nil.getInstance();
		} else {
			Node a = this.parseExp(tok);
			tok = this.scanner.getNextToken();
			if (tok == null) {
				System.err.println("Parse error: end of file in list");
				return null;
			} else {
				Node d;
				if (tok.getType() == 3) {
					d = this.parseExp();
					if (d == null) {
						System.err.println("Parse error: end of file in list");
						return null;
					}

					tok = this.scanner.getNextToken();
					if (tok == null) {
						System.err.println("Parse error: end of file in list");
						return null;
					}

					if (tok.getType() != 2) {
						System.err.println("missing right parenthesis after expression");
						d.print(2);
						System.err.println("discarding input until matching right parenthesis");
					}

					while (tok != null && tok.getType() != 2) {
						Node dummy = this.parseExp(tok);
						if (dummy == null) {
							return null;
						}

						tok = this.scanner.getNextToken();
					}

					if (tok == null) {
						return null;
					}
				} else {
					d = this.parseRest(tok);
					if (d == null) {
						return null;
					}
				}

				return new Cons(a, d);
			}
		}
	}
}