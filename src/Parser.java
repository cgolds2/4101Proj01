import java.util.ArrayList;

// Parser.java -- the implementation of class Parser
//
// Defines
//
//   class Parser;
//
// Parses the language
//
//   exp  ->  ( rest
//         |  #f
//         |  #t
//         |  ' exp
//         |  integer_constant
//         |  string_constant
//         |  identifier
//    rest -> )
//         |  exp+ [. exp] )
//
// and builds a parse tree.  Lists of the form (rest) are further
// `parsed' into regular lists and special forms in the constructor
// for the parse tree node class Cons.  See Cons.parseList() for
// more information.
//
// The parser is implemented as an LL(0) recursive descent parser.
// I.e., parseExp() expects that the first token of an exp has not
// been read yet.  If parseRest() reads the first token of an exp
// before calling parseExp(), that token must be put back so that
// it can be reread by parseExp() or an alternative version of
// parseExp() must be called.
//
// If EOF is reached (i.e., if the scanner returns a NULL) token,
// the parser returns a NULL tree.  In case of a parse error, the
// parser discards the offending token (which probably was a DOT
// or an RPAREN) and attempts to continue parsing with the next token.

class Parser {
	private Scanner scanner;
	private Token[] tokens;

	public Parser(Scanner s) {
		scanner = s;
	}
	// debug scanner
	/*
	 * Token tok = scanner.getNextToken(); while (tok != null) { int tt =
	 * tok.getType(); System.out.print(TokenName[tt]); if (tt == Token.INT)
	 * System.out.println(", intVal = " + tok.getIntVal()); else if (tt ==
	 * Token.STRING) System.out.println(", strVal = " + tok.getStrVal()); else
	 * if (tt == Token.IDENT) System.out.println(", name = " + tok.getName());
	 * else System.out.println();
	 * 
	 * tok = scanner.getNextToken();
	 */

	public Node parseExp() {
		// write code for parsing an exp
	
		Token tok = scanner.getNextToken();
		if (tok == null) {
			return null;
		}

		Node curNode;
		if (tok.getType() == TokenType.LPAREN) {
			curNode = new Node();
			curNode.setCar(parseRest());
		} else if (tok.getType() == TokenType.RPAREN) {
			return null;
		} else {
			curNode = new Node();
			Node carNode = new Node(tok);
			curNode.setCar(carNode);
		}
		return curNode;
	}

	protected Node parseRest() {
		// TODO: write code for parsing rest
		Node rootNode = new Node();
		Node curNode = rootNode;
		while(true){
			curNode.setCar(parseExp());
			if(curNode.getCar() == null){
				curNode.setCdr(null);
				break;
			}
			if(curNode.getCar().getCar().getValue().getType() == TokenType.DOT){
				curNode.setCdr(parseExp());
				if(curNode.getCdr() == null){
					System.out.println("There should have been something after the dot, shame on you");
				}
				Node shouldBeRP = parseExp();
				if(shouldBeRP != null){
					System.out.println("Too many things after the dot");
				}
				
				break;
			}
			curNode.setCdr(new Node());
			curNode = curNode.getCdr();
		}
		if(rootNode.getCar().getCar().getValue().getType() ==TokenType.DOT){
			System.out.println("There should have been something before the dot");
		}
		
		return rootNode;
	}

	private boolean isExpression(Token t) {
		if (t.getType() == TokenType.LPAREN) {
			return isRest(t);
		}
		return (t.getType() == TokenType.FALSE || t.getType() == TokenType.TRUE || t.getType() == TokenType.IDENT
				|| t.getType() == TokenType.INT || t.getType() == TokenType.QUOTE || t.getType() == TokenType.STRING);
	}

	private boolean isRest(Token t) {
		while (isExpression(t)) {

		}
		return (t.getType() == TokenType.RPAREN);
	}
};
