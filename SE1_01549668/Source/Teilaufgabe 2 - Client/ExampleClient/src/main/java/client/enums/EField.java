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
	GRASS(10) {
		public String toString() {
			return "““‘“";
		}
	},

	WATER(Integer.MAX_VALUE) {
		public String toString() {
			return "≈≈≈";
		}
	},
	MOUNTAIN(30) {
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
