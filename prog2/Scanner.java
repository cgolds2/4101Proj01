import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.Arrays;

class Scanner {

	private PushbackInputStream in;
	private int byteArraySize = 1000;
	private byte[] buf = new byte[byteArraySize];
	private boolean unreadToken;
	private Token lookAheadToken;

	private enum SkipChar {
		SPACE(32), TAB(9), SEMI(59), LF(10), CR(13), FF(12);

		private final int ascii;

		SkipChar(int ascii) {
			this.ascii = ascii;
		}

		public int ascii() {
			return ascii;
		}
	};

	private enum SpecialInitial {
		BANG('!'), DOLLAR('$'), PERCENT('%'), AMPERSAND('&'), ASTERISK('*'), FWDSLASH('/'), COLON(':'), LESSTHAN(
				'<'), EQUAL('='), GRTRTHAN('>'), QUESTION('?'), CAROT('^'), UNDERSCORE('_'), TILDA('~');

		private final char ch;

		SpecialInitial(char ch) {
			this.ch = ch;
		}

		public char ch() {
			return ch;
		}
	}

	private enum SpecialSubsequent {
		PLUS('+'), MINUS('-'), DOT('.'), AT('@');

		private final char ch;

		SpecialSubsequent(char ch) {
			this.ch = ch;
		}

		public char ch() {
			return ch;
		}
	}

	public Scanner(InputStream i) {
		in = new PushbackInputStream(i);
	}

	public Token getNextToken() {
		if (unreadToken) {
			unreadToken = false;
			return lookAheadToken;
		}
		int bite = -1;

		try {
			bite = in.read();
		} catch (IOException e) {
			System.err.println("We fail: " + e.getMessage());
		}

		while (IsSkipChar(bite)) {
			if (bite == SkipChar.SEMI.ascii()) {
				do {
					try {
						bite = in.read();
					} catch (IOException e) {
						System.err.println("We fail: " + e.getMessage());
					}
				} while (bite != SkipChar.LF.ascii());
			}
			try {
				bite = in.read();
			} catch (IOException e) {
				System.err.println("We fail: " + e.getMessage());
			}
		}

		if (bite == -1)
			return null;

		char ch = (char) bite;

		if (ch == '\'')
			return new Token(Token.QUOTE);
		else if (ch == '(')
			return new Token(Token.LPAREN);
		else if (ch == ')')
			return new Token(Token.RPAREN);
		else if (ch == '.')
			return new Token(Token.DOT);

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

		else if (ch == '"') {

			char currentChar = '-';

			try {
				currentChar = (char) in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}

			int bufIndex = 0;
			Arrays.fill(buf, (byte) 0);
			do {
				buf[bufIndex] = (byte) currentChar;
				bufIndex++;
				try {
					currentChar = (char) in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} while (currentChar != '"');

			return new StrToken((new String(buf)).substring(0, bufIndex));
		}

		else if (ch >= '0' && ch <= '9') {

			Arrays.fill(buf, (byte) 0);

			int numLength = 0;

			do {
				buf[numLength++] = (byte) ch;
				try {
					ch = (char) in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} while (ch >= '0' && ch <= '9');

			try {
				in.unread(ch);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new IntToken(Integer.parseInt((new String(buf)).trim()));
		}

		else if (IsPeculiarIdentifier(ch)) {
			return new IdentToken(String.valueOf(ch));
		} else if (IsLetter(ch) || IsSpecialInitial(ch)) {

			Arrays.fill(buf, (byte) 0);

			int identifierLength = 0;

			do {
				buf[identifierLength++] = (byte) ch;
				try {
					ch = (char) in.read();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} while (IsLetter(ch) || IsDigit(ch) || IsSpecialInitial(ch) || IsSpecialSubsequent(ch));

			try {
				in.unread(ch);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return new IdentToken((new String(buf)).trim());
		}

		else {
			System.err.println("Illegal input character '" + (char) ch + '\'');
			return getNextToken();
		}
	}

	public Token lookAhead() {
		unreadToken = false;
		lookAheadToken = getNextToken();
		return lookAheadToken;
	}

	public void putBackToken() {
		unreadToken = true;
	}

	private boolean IsSkipChar(int bite) {
		for (SkipChar c : SkipChar.values()) {
			if (c.ascii() == bite)
				return true;
		}
		return false;
	}

	private boolean IsLetter(char ch) {
		return (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z');
	}

	private boolean IsDigit(char ch) {
		return (ch >= '0' && ch <= '9');
	}

	private boolean IsSpecialInitial(char ch) {
		for (SpecialInitial c : SpecialInitial.values()) {
			if (c.ch() == ch)
				return true;
		}
		return false;
	}

	private boolean IsPeculiarIdentifier(char ch) {
		return (ch == '+' || ch == '-');
	}

	private boolean IsSpecialSubsequent(char ch) {
		for (SpecialSubsequent c : SpecialSubsequent.values()) {
			if (c.ch() == ch)
				return true;
		}
		return false;
	}
}