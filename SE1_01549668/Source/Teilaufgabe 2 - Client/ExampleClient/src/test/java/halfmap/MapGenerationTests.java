package halfmap;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.MatcherAssert.assertThat;

import client.enums.EField;
import client.model.HalfMapModel;
import client.model.HalfMapService;
import client.model.Node;
import client.model.NodeCoords;

/*
 * 
 * 
 * In the following you see: 
 * 1) The method annotated with @BeforeEach runs before each test
 * 2) A method annotated with @Test defines a test method
 * 3) @DisplayName can be used to define the name of the test which is displayed to the user
 * 4) This is an assert statement which validates that expected and actual value is the same, 
 * 	  if not the message at the end of the method is shown
 * 5) @RepeatedTest defines that this test method will be executed multiple times, in this example 5 times
 * 
 * 
 * The @Disabled or @Disabled("Why disabled") annotation marks a test to be disabled. 
 * This is useful when the underlying code has been changed and the test case has not yet been adapted 
 * 
 * 
 * Testing that certain exceptions are thrown are be done with the
 * org.junit.jupiter.api.Assertions.expectThrows() assert statement.
 * 
 * In case you want to ensure that all asserts are checked you can assertAll.
 * assertAll("address name",
        () -> assertEquals("John", address.getFirstName()),
        () -> assertEquals("User", address.getLastName())
    );
    
 * 
 * Dynamic test 
 * 
 * methods are annotated with @TestFactory and allow you to create multiple tests of type DynamicTest with your code. 
 * They can return:
 * an Iterable a Collection a Stream
 * 
 * 
 * You can use the @TestMethodOrder on the class to control the execution order of the tests
 * @TestMethodOrder(OrderAnnotation.class)
 *  use the @Order(int)
 *  
 *  
 *  
 *  
 *  
 */

class MapGenerationTests {
	
	HalfMapService hGen;
	HalfMapModel hmap;
	

	@BeforeEach
	void setUp() throws Exception {
		this.hGen = new HalfMapService();
		this.hmap = this.hGen.generateHalfMap();
	}

	

	@Test
	@DisplayName("Ensure all correct coordinates are created (0<= x <10, 0<= y <5)")
	void testBaseOfHalfMap() {
		
		//arrange		
		List<Node> mapNodes = this.hmap.getMapNodes();
		int minY=0;
		int minX=0;
		int maxY=0;
		int maxX=0;
		
		//act
		for(Node n : mapNodes) {
			if(n.getCoordinates().getY() < minY) {
				
				minY = n.getCoordinates().getY();
			}
			
			if(n.getCoordinates().getX() < minX) {
				
				minX = n.getCoordinates().getX();
			}
			
			if(n.getCoordinates().getY() > maxY) {

				maxY = n.getCoordinates().getY();
				
			}

			if(n.getCoordinates().getX() > maxX) {

				maxX = n.getCoordinates().getX();
				
			}
				
		}
		
		//assert
		assertEquals(0, minY, "Min of y should be zero");
		assertEquals(0, minX, "Min of x should be zero");
		assertEquals(4, maxY, "Max of y should be 4");
		assertEquals(9, maxX, "Min of y should be 9");
		
	}

	
	@Test
	@DisplayName("There should be min of 7 water fields ")
	void testWaterFieldCount() {
		//arrange
		int waterCount = (int) this.hmap.getMapNodes().stream().filter(n -> n.getFieldType().equals(EField.WATER)).count();
		
		//act
		//assert
		assertThat(waterCount, is(greaterThanOrEqualTo(7)));
	}
	
	@Test
	@DisplayName("There should be less than 2 water count in width-borders")
	void testBorderWaterCount() {
		//arrange
		List<Node> wBorder = this.hmap.getMapNodes().stream().filter(n -> ( n.getCoordinates().getX()==0 || n.getCoordinates().getX()==9 )).collect(Collectors.toList());
		
		//act
		int waterCount = (int) wBorder.stream().filter(n -> n.getFieldType().equals(EField.WATER)).count();
		
		//assert
		assertThat(waterCount, is(lessThanOrEqualTo(3)));
	}
	
	@Test
	@DisplayName("There should be min of 5 mountain fields")
	void testMountainFieldCount() {
		//arrange
		int waterCount = (int) this.hmap.getMapNodes().stream().filter(n -> n.getFieldType().equals(EField.WATER)).count();

		//act
		
		//assert
		assertThat(waterCount, is(greaterThanOrEqualTo(5)));

	}

	
//	@Test
//	@DisplayName("")
//	void test() {
//		//arrange
//		
//		//act
//		
//		//assert
//	}

}
