package main.java.kalender.model;

public enum WiederholungType {
	OHNE("ohne Wiederholung") {
		@Override
		public int inTagen() {
			return 0;
		}
		
	},
	TAEGLICH("t�glich") {
		@Override
		public int inTagen() {
			return 1;
		}
	},
	WOECHENTLICH("w�chentlich") {
		@Override
		public int inTagen() {
			return 7;
		}
	};

	private String name;

	private WiederholungType(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	public abstract int inTagen();
};
