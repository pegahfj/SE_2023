package client.enums;

/**
 * Description:
 * 
 * Instance  is used in :
 * 
 * @see 
 * 
 */



public enum EField {
	GRASS(1) {
		public String toString() {
			return "““‘“";
		}
	},

	WATER(Integer.MAX_VALUE) {
		public String toString() {
			return "≈≈≈";
		}
	},
	MOUNTAIN(2) {
		public String toString() {
			return " ∆∆ ";
		}
	};

	private int costs;

	private EField(int costs) {
		this.costs = costs;
	}

	public int getCosts() {
		return costs;
	}
}
