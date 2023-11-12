package client.enums;

import java.util.Arrays;
import java.util.List;

public enum EDirection {
    /**
     *
     *   [[-1, 0], [1, 0], [0, -1], [0, 1]]
     */
    LEFT(Arrays.asList(-1,0) ) {
	},

    RIGHT(Arrays.asList(1,0) ) {
    },

    DOWN(Arrays.asList(0,1) ) {
    },
    
    UP(Arrays.asList(0,-1) ) {
    };

    private List<Integer> direction;

	private EDirection (List<Integer> d) {
		this.direction = d;
	}

	public List<Integer> get_value() {
		return this.direction;
	}

   
}
