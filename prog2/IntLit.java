import java.io.*;

class IntLit extends Node {
    private int intVal;

    public IntLit(int i) {
        intVal = i;
    }

    public void print(int n) {
        Printer.printIntLit(n, intVal);
    }

    public boolean isNumber() {
        return true;
    }

    public int getIntVal() {
        return intVal;
    }

    public Node eval(Environment env) {
        // If an expression is a constant (not a symbol and not a pair), return
        // it
        return this;
    }

}
