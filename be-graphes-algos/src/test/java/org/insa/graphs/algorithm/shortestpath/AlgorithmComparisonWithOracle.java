package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.*;
import org.junit.Test;

import org.insa.graphs.algorithm.AbstractSolution.Status;

public class AlgorithmComparisonWithOracle {

	// Maps to test on
	String carre = "../maps/carre.mapgr";
	String fractal = "../maps/fractal.mapgr";
	String hauteGaronne = "../maps/europe/france/haute-garonne.mapgr";
	String belgium = "../maps/europe/belgium/belgium.mapgr";
	String greece = "../maps/europe/greece/greece.mapgr";
	String newZealand = "../maps/oceania/new-zealand.mapgr";
	String ecuador = "../maps/south-america/ecuador.mapgr";
	String california = "../maps/us/california.mapgr";
	
	double dDelta = 1; // in meters
	double tDelta = 1; // in seconds
	
	enum Mode {
		ALL_LENGTH,
		CARS_ONLY_LENGTH,
		ALL_TIME,
		CARS_ONLY_TIME,
		PEDESTRIAN_TIME
	}
	
	/*
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 *                         <Edge Cases> 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 */
	
	// Impossible destination node on abstract map
	@Test
	public void impossibleNodeOnCarre() {
		assertEquals(Status.INFEASIBLE, new AlgorithmTestHelper(carre, 5, 99, "Dijkstra", Mode.ALL_LENGTH).algorithmOutput.getStatus());
		assertEquals(Status.INFEASIBLE, new AlgorithmTestHelper(carre, 5, 99, "AStar", Mode.ALL_LENGTH).algorithmOutput.getStatus());
	}
	
	// Impossible destination node on real map
	@Test
	public void impossibleNodeonHauteGaronne() {
		assertEquals(Status.INFEASIBLE, new AlgorithmTestHelper(hauteGaronne, 128620, 999999, "Dijkstra", Mode.ALL_LENGTH).algorithmOutput.getStatus());
		assertEquals(Status.INFEASIBLE, new AlgorithmTestHelper(hauteGaronne, 128620, 999999, "AStar", Mode.ALL_LENGTH).algorithmOutput.getStatus());
	}
	
	// Null length on abstract map
	@Test
	public void nullLengthOnCarre() {
		assertEquals(0, new AlgorithmTestHelper(carre, 5, 5, "Dijkstra", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
		assertEquals(0, new AlgorithmTestHelper(carre, 5, 5, "AStar", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
	}
	
	// Disjointed nodes on real map
	@Test
	public void disjointedNodesOnGreece() {
//		Status bfReference = new AlgorithmTestHelper(greece, 277781, 736472, "BellmanFord", Mode.ALL_TIME).algorithmOutput.getStatus();
		Status bfReference = Status.INFEASIBLE; // Precalculated
		assertEquals(bfReference, new AlgorithmTestHelper(greece, 277781, 736472, "Dijkstra", Mode.ALL_TIME).algorithmOutput.getStatus());
		assertEquals(bfReference, new AlgorithmTestHelper(greece, 277781, 736472, "AStar", Mode.ALL_TIME).algorithmOutput.getStatus());
		assertEquals(Status.INFEASIBLE, bfReference);
	}
	
	@Test
	public void disjointedNodesOnNewZealand() {
//		Status bfReference = new AlgorithmTestHelper(newZealand, 318158, 288451, "BellmanFord", Mode.CARS_ONLY_LENGTH).algorithmOutput.getStatus();
		Status bfReference = Status.INFEASIBLE; // Precalculated
		assertEquals(bfReference, new AlgorithmTestHelper(newZealand, 318158, 288451, "Dijkstra", Mode.CARS_ONLY_LENGTH).algorithmOutput.getStatus());
		assertEquals(bfReference, new AlgorithmTestHelper(newZealand, 318158, 288451, "AStar", Mode.CARS_ONLY_LENGTH).algorithmOutput.getStatus());
		assertEquals(Status.INFEASIBLE, bfReference);
	}
	
	// No pedestrian path on real map
	@Test
	public void noPedestrianPathOnBelgium() {
		Status bfReference = new AlgorithmTestHelper(belgium, 676740, 768308, "BellmanFord", Mode.PEDESTRIAN_TIME).algorithmOutput.getStatus();
		assertEquals(bfReference, new AlgorithmTestHelper(belgium, 676740, 768308, "Dijkstra", Mode.PEDESTRIAN_TIME).algorithmOutput.getStatus());
		assertEquals(bfReference, new AlgorithmTestHelper(belgium, 676740, 768308, "AStar", Mode.PEDESTRIAN_TIME).algorithmOutput.getStatus());
		assertEquals(Status.INFEASIBLE, bfReference);
	}
	
	@Test
	public void noPedestrianPathVerificationOnBelgium() {
		Status bfReference = new AlgorithmTestHelper(belgium, 676740, 768308, "BellmanFord", Mode.ALL_TIME).algorithmOutput.getStatus();
		assertEquals(bfReference, new AlgorithmTestHelper(belgium, 676740, 768308, "Dijkstra", Mode.ALL_TIME).algorithmOutput.getStatus());
		assertEquals(bfReference, new AlgorithmTestHelper(belgium, 676740, 768308, "AStar", Mode.ALL_TIME).algorithmOutput.getStatus());
		assertEquals(Status.OPTIMAL, bfReference);
	}
	
	/*
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 *                         </Edge Cases> 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 */
	
	/*
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 *                         <General Cases> 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 */
	
	// Abstract map (distance)
	@Test
	public void distanceOnCarre() {
		double bfReference = new AlgorithmTestHelper(carre, 5, 13, "BellmanFord", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength();
		assertEquals(bfReference, new AlgorithmTestHelper(carre, 5, 13, "Dijkstra", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
		assertEquals(bfReference, new AlgorithmTestHelper(carre, 5, 13, "AStar", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
	}
	
	// Real map (distance)
	@Test
	public void distanceOnHauteGaronne() {
		double bfReference = new AlgorithmTestHelper(hauteGaronne, 128620, 116028, "BellmanFord", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength();
		assertEquals(bfReference, new AlgorithmTestHelper(hauteGaronne, 128620, 116028, "Dijkstra", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
		assertEquals(bfReference, new AlgorithmTestHelper(hauteGaronne, 128620, 116028, "AStar", Mode.ALL_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
	}
	
	// Real map (pedestrian time)
	@Test
	public void pedestrianTimeOnEcuador() {
//		double bfReference = new AlgorithmTestHelper(ecuador, 259175, 214335, "BellmanFord", Mode.PEDESTRIAN_TIME).algorithmOutput.getPath().getMinimumTravelTime();
		double bfReference = 57700; // Precalculated
		assertEquals(bfReference, new AlgorithmTestHelper(ecuador, 259175, 214335, "Dijkstra", Mode.PEDESTRIAN_TIME).algorithmOutput.getPath().getMinimumTravelTime(), tDelta);
		assertEquals(bfReference, new AlgorithmTestHelper(ecuador, 259175, 214335, "AStar", Mode.PEDESTRIAN_TIME).algorithmOutput.getPath().getMinimumTravelTime(), tDelta);
	}
	
	// Real map (time)
	@Test
	public void timeOnEcuador() {
//		double bfReference = new AlgorithmTestHelper(ecuador, 259175, 214335, "BellmanFord", Mode.ALL_TIME).algorithmOutput.getPath().getMinimumTravelTime();
		double bfReference = 35331; // Precalculated
		assertEquals(bfReference, new AlgorithmTestHelper(ecuador, 259175, 214335, "Dijkstra", Mode.ALL_TIME).algorithmOutput.getPath().getMinimumTravelTime(), tDelta);
		assertEquals(bfReference, new AlgorithmTestHelper(ecuador, 259175, 214335, "AStar", Mode.ALL_TIME).algorithmOutput.getPath().getMinimumTravelTime(), tDelta);
	}
	
	// Real map (car time)
	@Test
	public void carTimeOnBelgium() {
//		double bfReference = new AlgorithmTestHelper(belgium, 81840, 895846, "BellmanFord", Mode.CARS_ONLY_TIME).algorithmOutput.getPath().getMinimumTravelTime();
		double bfReference = 8984; // Precalculated
		assertEquals(bfReference, new AlgorithmTestHelper(belgium, 81840, 895846, "Dijkstra", Mode.CARS_ONLY_TIME).algorithmOutput.getPath().getMinimumTravelTime(), tDelta);
		assertEquals(bfReference, new AlgorithmTestHelper(belgium, 81840, 895846, "AStar", Mode.CARS_ONLY_TIME).algorithmOutput.getPath().getMinimumTravelTime(), tDelta);
	}
	
	// Really big map (car distance)
	@Test
	public void carDistanceOnCalifornia() {
//		double bfReference = new AlgorithmTestHelper(california, 73904, 372680, "BellmanFord", Mode.CARS_ONLY_LENGTH).algorithmOutput.getPath().getLength();
		double bfReference = 1135948; // Precalculated
		assertEquals(bfReference, new AlgorithmTestHelper(california, 73904, 372680, "Dijkstra", Mode.CARS_ONLY_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
		assertEquals(bfReference, new AlgorithmTestHelper(california, 73904, 372680, "AStar", Mode.CARS_ONLY_LENGTH).algorithmOutput.getPath().getLength(), dDelta);
	}
	
	/*
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 *                         </General Cases> 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 */
	
	/*
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 *                         <Path Validity Tests> 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 */
	
	@Test
	public void testPathValidityOnCarre() {
		assertTrue(new AlgorithmTestHelper(carre, 17, 9, "BellmanFord", Mode.ALL_LENGTH).algorithmOutput.getPath().isValid());
		assertTrue(new AlgorithmTestHelper(carre, 17, 9, "Dijkstra", Mode.ALL_LENGTH).algorithmOutput.getPath().isValid());
		assertTrue(new AlgorithmTestHelper(carre, 17, 9, "AStar", Mode.ALL_LENGTH).algorithmOutput.getPath().isValid());
	}
	
	@Test
	public void testPathValidityOnHauteGaronne() {
		assertTrue(new AlgorithmTestHelper(hauteGaronne, 67393, 134644, "BellmanFord", Mode.CARS_ONLY_LENGTH).algorithmOutput.getPath().isValid());
		assertTrue(new AlgorithmTestHelper(hauteGaronne, 67393, 134644, "Dijkstra", Mode.CARS_ONLY_LENGTH).algorithmOutput.getPath().isValid());
		assertTrue(new AlgorithmTestHelper(hauteGaronne, 67393, 134644, "AStar", Mode.CARS_ONLY_LENGTH).algorithmOutput.getPath().isValid());
	}
	
	@Test
	public void testPathValidityOnNewZealand() {
		assertTrue(new AlgorithmTestHelper(newZealand, 161800, 216263, "BellmanFord", Mode.CARS_ONLY_TIME).algorithmOutput.getPath().isValid());
		assertTrue(new AlgorithmTestHelper(newZealand, 161800, 216263, "Dijkstra", Mode.CARS_ONLY_TIME).algorithmOutput.getPath().isValid());
		assertTrue(new AlgorithmTestHelper(newZealand, 161800, 216263, "AStar", Mode.CARS_ONLY_TIME).algorithmOutput.getPath().isValid());
	}
	
	/*
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 *                         </Path Validity Tests> 
	 * =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=
	 */
}
