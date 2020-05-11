package org.insa.graphs.algorithm.shortestpath;

import java.io.InputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

import org.insa.graphs.algorithm.shortestpath.AlgorithmComparisonWithOracle.Mode;

public class AlgorithmTestHelper {

	String mapPath;
	InputStream is;
	BinaryGraphReader bgr;
	Graph graph;
	Node originNode;
	Node destinationNode;
	List<ArcInspector> arcInspectors = ArcInspectorFactory.getAllFilters();
	ShortestPathAlgorithm algorithm;
	ShortestPathData algorithmInput;
	ShortestPathSolution algorithmOutput;
	
	public AlgorithmTestHelper(String mapPath, int origin, int destination, String algorithm, Mode mode) {
		this.mapPath = mapPath;
		
		// Retrieve map file
		try {
			this.is = new FileInputStream(this.mapPath);
		} catch (FileNotFoundException fnfe) {
			System.out.println(fnfe);
			this.is = null;
		}
		this.bgr = new BinaryGraphReader(new DataInputStream(this.is));
		
		// Load graph from map file
		try {
			this.graph = bgr.read();
		} catch (IOException ioe) {
			System.out.println(ioe);
			this.graph = null;
		}
		
		// Manually create impossible nodes when needed
		if (origin > this.graph.size() - 1) {
			this.originNode = new Node(origin, new Point(999f, 333f));
		} else {
			this.originNode = this.graph.get(origin);
		}
		if (destination > this.graph.size() - 1) {
			this.destinationNode = new Node(destination, new Point(333f, 999f));
		} else {
			this.destinationNode = this.graph.get(destination);
		}
		
		switch (mode) {
        case ALL_LENGTH:  this.algorithmInput = new ShortestPathData(this.graph, this.originNode, this.destinationNode, this.arcInspectors.get(0));
                 break;
        case CARS_ONLY_LENGTH:  this.algorithmInput = new ShortestPathData(this.graph, this.originNode, this.destinationNode, this.arcInspectors.get(1));
                 break;
        case ALL_TIME:  this.algorithmInput = new ShortestPathData(this.graph, this.originNode, this.destinationNode, this.arcInspectors.get(2));
                 break;
        case CARS_ONLY_TIME:  this.algorithmInput = new ShortestPathData(this.graph, this.originNode, this.destinationNode, this.arcInspectors.get(3));
                 break;
        case PEDESTRIAN_TIME:  this.algorithmInput = new ShortestPathData(this.graph, this.originNode, this.destinationNode, this.arcInspectors.get(4));
                 break;
        default: this.algorithmInput = new ShortestPathData(this.graph, this.originNode, this.destinationNode, this.arcInspectors.get(0));
                 break;
    }
		
		// Create the algorithm specified in constructor
		String fqn = "org.insa.graphs.algorithm.shortestpath." + algorithm + "Algorithm"; 
		try {
			this.algorithm = (ShortestPathAlgorithm) Class.forName(fqn).getDeclaredConstructor(ShortestPathData.class).newInstance(this.algorithmInput);	
		} catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			System.out.println(e);
		}
		
		this.algorithmOutput = this.algorithm.doRun();
	}

}
