
// Scanner.java -- the implementation of class Scanner

import java.io.*;

class Scanner {
	private PushbackInputStream in;
	private byte[] buf = new byte[1000];

	public Scanner(InputStream i) {
		in = new PushbackInputStream(i);
	}

	public Token getNextToken() {
		int bite = -1;
		
	

		// It would be more efficient if we'd maintain our own input buffer
		// and read characters out of that buffer, but reading individual
		// characters from the input stream is easier.
		try {
			bite = in.read();
	

		// skip white space and comments
		while (isWhiteSpace((char) bite)) {
			bite = in.read();
		}

		if (((char) bite) == ';') {
			// while the bite isn't the newline char, ignore it
			while (((char) bite) != 12) {
				bite = in.read();
			}
			// finally, take care of the newline char
			bite = in.read();
		}

		if (bite == -1)
			return null;

		char ch = (char) bite;

		// Special characters
		if (ch == '\'')
			return new Token(Token.QUOTE);
		else if (ch == '(')
			return new Token(Token.LPAREN);
		else if (ch == ')')
			return new Token(Token.RPAREN);
		else if (ch == '.')
			// We ignore the special identifier `...'.
			return new Token(Token.DOT);

		// Boolean constants
		else if (ch == '#') {
			try {
				bite = in.read();
			} catch (IOException e) {
				System.err.println("We fail: " + e.getMessage());
			}

			if (bite == -1) {
				System.err.println("Unexpected EOF following #");
				return null;
			}
			ch = (char) bite;
			if (ch == 't')
				return new Token(Token.TRUE);
			else if (ch == 'f')
				return new Token(Token.FALSE);
			else {
				System.err.println("Illegal character '" + (char) ch + "' following #");
				return getNextToken();
			}
		}

		// String constants
		else if (ch == '"') {
			// scan a string into the buffer variable buf
			for (int i = 0; i < 1000; i++) {
				ch = (char) in.read();
				if (ch == '"') {
					break;
				}
				buf[i] = (byte) ch;
			}
			return new StrToken(buf.toString());
		}

		// Integer constants
		else if (ch >= '0' && ch <= '9') {
			int i = ch - '0';
			while (true) {
				
				ch = (char) in.read();
				while (isWhiteSpace(ch)){
					ch = (char) in.read();
				}
				// not a number
				if (!(ch >= '0' && ch <= '9')) {
					in.unread(ch);
					break;
				}
				// it is a number, left shift standing number and add
				i *= 10;
				i += (ch - '0');
			}

			// scan the number and convert it to an integer

			// put the character after the integer back into the input
			// in->putback(ch);
			return new IntToken(i);
		}

		// Identifiers
		/*
		String peculiar = "+ | - ";
		String specSub = "+ | - | . | @";
		String digit = "0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9";
		String letter = "[A-Z | a-z]";
		String specInitial = "! | $ | % | & | * | / | : | < | = | > | ? | ^ | _ | ~";
		String initial = letter + "|" + specInitial;
		String subsequent = initial + "|" + digit + "|" + specSub;
		String identifierStartPattern = initial + "|" + peculiar;
		 */
		else if (ch >= 'A' && ch <= 'Z' ||
					 ch >= 'a' && ch <= 'z'  ||
					 ch == '!'   || 
					 ch == '$'  || 
					 ch == '%' || 
					 ch == '&'  || 
					 ch == '*'   || 
					 ch == '/'   || 
					 ch == ':'   || 
					 ch == '<'  || 
					 ch == '='  || 
					 ch== '>'   || 
					 ch == '?'  || 
					 ch == '^'   || 
					 ch == '_'  || 
					 ch == '~'  ||
					 ch == '+'  ||
					 ch == '-'   ) {
			// scan an identifier into the buffer
			if(ch == '+' || ch == '-'){
				//return just ch
				buf[0] = (byte) ch;
				return new IdentToken(buf.toString());
			}
			buf[0] = (byte) ch;
			while(true){
				
			}
			
			
			// put the character after the identifier back into the input
			// in->putback(ch);
			return new IdentToken(buf.toString());
		}

		// Illegal character
		else {
			System.err.println("Illegal input character '" + (char) ch + '\'');
			return getNextToken();
		}
		
		//because we are terrible people
		} catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
			return getNextToken();
		}
		
	};



	private boolean isWhiteSpace(char ch) {
		return (
		// Space
		(ch == 32) ||
		// tab, newline, carriage-return, and form-feed characters
				(ch >= 9 && ch <= 15));

	}
}
