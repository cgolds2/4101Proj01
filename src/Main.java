// Main.java -- the main program

import java.io.*;

public class Main {
    // Array of token names used for debugging the scanner
    public static final String TokenName[] = {
	"QUOTE",			// '
	"LPAREN",			// (
	"RPAREN",			// )
	"DOT",				// .
	"TRUE",				// #t
	"FALSE",			// #f
	"INT",				    // integer constant
	"STRING",			// string constant
	"IDENT"				// identifier
    };

    public static void main (String argv[]) {
	// create scanner that reads from standard input
	Scanner scanner = new Scanner(System.in);

	if (argv.length > 2) {
	    System.err.println("Usage: java Main " + "[-d]");
	    System.exit(1);
	}
	
	// if commandline option -d is provided, debug the scanner
	if (argv.length == 1 && argv[0].equals("-d")) {
	    // debug scanner
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
	}
	
	// Create parser
	Parser parser = new Parser(scanner);
	Node root;
	Node curNode;
	// Parse and pretty-print each input expression
	root = parser.parseExp();
	curNode = root;
	int spaces = 10;
	System.out.printf("%" + 10+ "s%n", "cons");

	while (curNode != null) {
		curNode.print(spaces);
		curNode.setCdr(parser.parseExp());
		curNode = curNode.getCdr();
	    spaces+=4;
	}
	System.out.printf("%" + spaces + "s%n","()");
	System.exit(0);
    }
}
