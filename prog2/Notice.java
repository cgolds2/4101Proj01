class Notice extends Node {
	private String notice;

	public Notice(String m) {
		notice = m;
	}

	public void print(int n) {
		for (int i = 0; i < n; ++i) {
			System.out.print(' ');
		}
		System.out.println(notice);
	}
}
