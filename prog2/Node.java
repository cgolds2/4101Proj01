import java.io.*;

class Node {
	public void print(int n) {
	}

	public void print(int n, boolean p) {
		print(n);
	}

	public boolean isBoolean() {
		return false;
	}

	public boolean isNumber() {
		return false;
	}

	public boolean isString() {
		return false;
	}

	public boolean isSymbol() {
		return false;
	}

	public boolean isNull() {
		return false;
	}

	public boolean isPair() {
		return false;
	}

	public boolean isProcedure() {
		return false;
	}

	public boolean isEnvironment() {
		return false;
	}

	// TODO: Report an error in these default methods and implement them
	// in class Cons. After setCar, a Cons cell needs to be `parsed' again
	// using parseList.

	public static void print(Node t, int n, boolean p) {
		t.print(n, p);
	}

	public static Node getCar(Node t) {
		return t.getCar();
	}

	public static Node getCdr(Node t) {
		return t.getCdr();
	}

	public static boolean isNull(Node t) {
		return t.isNull();
	}

	public static boolean isPair(Node t) {
		return t.isPair();
	}

	public Node getCar() {
		return null;
	}

	public Node getCdr() {
		return null;
	}

	public void setCar(Node a) {
	}

	public void setCdr(Node d) {
	}

	public String getName() {
		return "";
	}

	// TODO: Implement eval for subclasses
	// Find an alternative to returning null here
	public Node eval(Environment env) {
		return null;
	}

	public String getStrVal() {
		return "";
	}

	public boolean getBoolean() {
		return false;
	}

	public int getIntVal() {
		return 0;
	}

	public Node apply(Node args) {
		return new Error("attempt to call a non-procedure");
	}
}