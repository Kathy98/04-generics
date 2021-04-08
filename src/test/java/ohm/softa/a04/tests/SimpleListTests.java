package ohm.softa.a04.tests;

import ohm.softa.a04.SimpleFilter;
import ohm.softa.a04.SimpleList;
import ohm.softa.a04.SimpleListImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListTests {

	private final Logger logger = LogManager.getLogger();
	private SimpleList<Integer> testList;

	@BeforeEach
	void setup(){
		testList = new SimpleListImpl<>();

		testList.add(1);
		testList.add(2);
		testList.add(3);
		testList.add(4);
		testList.add(5);
	}

	@Test
	void testAddElements(){
		logger.info("Testing if adding and iterating elements is implemented correctly");
		int counter = 0;
		for(Object o : testList){
			counter++;
		}
		assertEquals(5, counter);
	}

	@Test
	void testSize(){
		logger.info("Testing if size() method is implemented correctly");
		assertEquals(5, testList.size());
	}

	@Test
	void testFilterAnonymousClass(){
		logger.info("Testing the filter possibilities by filtering for all elements greater 2");
		SimpleList<Integer> result = testList.filter(new SimpleFilter<Integer>() {
			@Override
			public boolean include(Integer item) {
				return item > 2;
			}
		});

		for(Integer i : result){
			assertTrue(i > 2);
		}
	}

	@Test
	void testFilterLambda(){
		logger.info("Testing the filter possibilities by filtering for all elements which are dividable by 2");
		SimpleList<Integer> result = testList.filter(o -> o % 2 == 0);
		for(Integer i : result){
			assertTrue(i % 2 == 0);
		}
	}

	// Method from Task 1.4
	@Test
	void testAddDefault() throws Exception{
		logger.info("Testing to add a new empty list element");
		SimpleList<Person> inst = new SimpleListImpl<>();
		inst.addDefault(Person.class);
		assertEquals(1, inst.size());
	}

	@Test
	void testSimpleListMap(){
		logger.info("Testing default map method");
		SimpleList<Integer> mapped = testList.map(i -> i + 1);
		assertEquals(5, mapped.size()); // alle 5 Integers (2, 3, 4, 5, 6) wurden der neuen Liste hinzugefügt
		for(Integer i : mapped) {
			// Die Liste enthält nur Elemente zwischen 2 und 6 (da 1-5 jew. +1)
			assertTrue(i > 1);
			assertTrue(i < 7);
		}
	}

	@Test
	void testMap() {
		logger.info("Testing default map method by mapping every value to its square");
		SimpleList<Double> result = testList.map(i -> Math.pow(i, 2)); // Alle Werte werden Quadriert
		Iterator<Integer> origIt = testList.iterator(); // 1, 2, 3, 4, 5
		Iterator<Double> mapIt = result.iterator(); // 1, 4, 9, 16, 25
		while (origIt.hasNext() && mapIt.hasNext()) {
			assertEquals(Math.pow(origIt.next(), 2), mapIt.next(), 0.1);
			// assertEquals(1, 4, 9, 16, 25; 1, 4, 9, 16, 25)
		}
	}

	@Test
	void testSimpleListMapChangedType() {
		logger.info("Testing default map method by mapping every integer to a char value");
		SimpleList<Character> mapped = testList.map(i -> ((char) (i + 64))); // 65,..., 69 = A, B, C, D, E
		assertEquals(5, mapped.size());
	}
}