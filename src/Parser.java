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
		// TODO: write code for parsing an exp
		// STEPS
		// GET THE LIST
		ArrayList<Token> listOfTokens = new ArrayList<>();
		Token tok = scanner.getNextToken();
		// creates the list of tokens
		while (tok != null) {
			if (isExpression(tok)) {
				listOfTokens.add(tok);
			}
			tok = scanner.getNextToken();
		}

		Node ret = parseRest(listOfTokens);

		return ret;
	}

	protected Node parseRest(ArrayList<Token> listOfTokens) {
		// TODO: write code for parsing rest
		return null;
	}

	private boolean isExpression(Token t) {
		if(t.getType() == TokenType.LPAREN){
			return isRest(t);
		}
		return (t.getType() == TokenType.FALSE || t.getType() == TokenType.TRUE || t.getType() == TokenType.IDENT
				|| t.getType() == TokenType.INT  || t.getType() == TokenType.QUOTE
				|| t.getType() == TokenType.STRING);
	}
	
	private boolean isRest(Token t){
		while(isExpression(t)){
			
		}
		return (t.getType() == TokenType.RPAREN );
	}
};
