import java.io.*;

class Node {
	private Node _carNode;
	private Node _cdrNode;
	private Token _value;
	/*
	 * N / \ car cdr
	 */

	public Node() {
	}

	public Node(Token parentValue) {
		_value=(parentValue);
	}

	// The argument of print(int) is the number of characters to indent.
	// Every subclass of Node must implement print(int).
	void print(int n) {
		

		if (_carNode != null) {

			if (_carNode.getValue() != null) {
				String s = "";
				if (_carNode.getValue().getType() == Token.INT)
				  s= ""+ _carNode.getValue().getIntVal();
				else if (_carNode.getValue().getType() == Token.STRING)
				   s=  _carNode.getValue().getStrVal();
				else if (_carNode.getValue().getType() == Token.IDENT)
				  s=  _carNode.getValue().getName();
				System.out.printf("%" + (n - 4) + "s" + "    %s%n", s, "cons");

			} 
		}
		if (_cdrNode != null) {
			_cdrNode.print(4 + n);
			// System.out.printf("%" + (n-4) + "s"+"%" + (n-4) + "s",
			// _carNode._value, "cons");
		}

	}

	// The first argument of print(int, boolean) is the number of characters
	// to indent. It is interpreted the same as for print(int).
	// The second argument is only useful for lists (nodes of classes
	// Cons or Nil). For all other subclasses of Node, the booleanean
	// argument is ignored. Therefore, print(n,p) defaults to print(n)
	// for all classes other than Cons and Nil.
	// For classes Cons and Nil, print(n,TRUE) means that the open
	// parenthesis was printed already by the caller.
	// Only classes Cons and Nil override print(int,boolean).
	// For correctly indenting special forms, you might need to pass
	// additional information to print. What additional information
	// you pass is up to you. If you only need one more bit, you can
	// encode that in the sign bit of n. If you need additional parameters,
	// make sure that you define the method print in all the appropriate
	// subclasses of Node as well.
	void print(int n, boolean p) {

		print(n);
	}

	// For parsing Cons nodes, for printing trees, and later for
	// evaluating them, we need some helper functions that test
	// the type of a node and that extract some information.

	// TODO: implement these in the appropriate subclasses to return TRUE.
	public boolean isBoolean() {
		return false;
	} // BooleanLit

	public boolean isNumber() {
		return false;
	} // IntLit

	public boolean isString() {
		return false;
	} // StringLit

	public boolean isSymbol() {
		return false;
	} // Ident

	public boolean isNull() {
		return false;
	} // nil

	public boolean isPair() {
		return false;
	} // Cons

	// TODO: Report an error in these default methods and implement them
	// in class Cons. After setCar, a Cons cell needs to be `parsed' again
	// using parseList.
	public Node getCar() {
		return _carNode;
	}

	public Node getCdr() {
		return _cdrNode;
	}

	public void setCar(Node a) {
		_carNode = a;
	}

	public void setCdr(Node d) {
		_cdrNode = d;
	}

	public Node getBottom() {
		Node n = _cdrNode;
		while (n != null) {
			if (n.getCdr() == null) {
				break;
			}
			n = n.getCdr();
		}
		return n;
	}

	public Token getValue() {
		return _value;
	}

}
