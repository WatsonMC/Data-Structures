package undirectedWeightedGraph;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ConcreteVerticesUndirectedWeightedGraphTest extends UndirectedWeightedGraphInstanceTest {

	@Override
	public UndirectedWeightedGraph<String> emptyInstance() {
		// TODO Auto-generated method stub
		//return new ConcreteVerticesUndirectedWeightedGraph<String>();
		return (UndirectedWeightedGraph<String>) UndirectedWeightedGraph.instanceOf(String.class);
	}
	
	@Test
	public void testAll() {
		testAdd();
		testNeighbours();
		testRemove();
		testSet();
		testVertices();
	}
	
	@Test 
	public void TestVertices() {
		/**
		 * Test get for neighbours
		 * 
		 * Test set for neighbours, tests deleteEdge and getLabel implicitly
		 * 1. new neighbour, add ->T1
		 * 2. new neighbour  delete ->T2
		 * 3. neighbour already add ->T3
		 * 4. neighbour already  delete
		*/

		Vertex<String> v1 = new Vertex<>("v1");
		Vertex<String> v2 = new Vertex<>("v2");
		Vertex<String> v3 = new Vertex<>("v3");
		
		//T1
		assertTrue(v1.setNeighbour(v2.getLabel(), 1) == 0);
		Map<String,Integer> T1Map = new HashMap<>();
		T1Map.put("v2", 1);
		assertTrue(v1.getNeighbours().equals(T1Map));
		//T2
		assertTrue(v1.setNeighbour(v3.getLabel(), 0) == 0); //T2
		assertTrue(v3.getNeighbours().equals(new HashMap<>()));
		//T3
		assertTrue(v1.setNeighbour(v2.getLabel(), 2) == 1);
		T1Map.put("v2", 2);
		assertTrue(v1.getNeighbours().equals(T1Map));
		//T4
		assertTrue(v1.setNeighbour(v2.getLabel(), 0) == 2);
		T1Map.clear();
		assertTrue(v1.getNeighbours().equals(T1Map));
		
	}

}
