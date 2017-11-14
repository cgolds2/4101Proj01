import java.io.*;

abstract class Special {
	abstract void print(Node t, int n, boolean p);

	public Node eval(Environment env) {
		return Nil.getInstance();
	}

}
