class Error extends Node {
	private String errorMessage;
	private Node extraInfo;

	public Error(String e) {
		errorMessage = e;
	}

	public Error(String e, Node extra) {
		errorMessage = e;
		extraInfo = extra;
	}

	public void print(int n) {
		if (n >= 0) {
			System.out.println("ERROR: " + errorMessage);
			if (extraInfo != null)
				extraInfo.print(n);
		} else {
			n = -1 * n;
			System.out.print("ERROR: " + errorMessage);
			if (extraInfo != null)
				extraInfo.print(n);
		}
	}
}
