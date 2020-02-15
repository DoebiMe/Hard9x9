package be.doubbel.hard9x9.backend;

public enum PlayList {
		play9x9(1),
		playHex(2),
		play25(3);
		private Integer value;

	PlayList(Integer value) {
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}
}
