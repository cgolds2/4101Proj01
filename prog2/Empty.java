
class Empty extends Node{
    private static Empty instance;
    public static Empty getInstance() {
        if (instance == null)
            instance = new Empty();
        return instance;
    }
    public void print(int n) {
        return;
    }
}
