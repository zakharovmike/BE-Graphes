package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;

import org.insa.graphs.algorithm.utils.PriorityQueue;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
    	
    	// Retrieve the graph
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        
        PriorityQueue<Label> queue = new BinaryHeap<>();
        ShortestPathSolution solution = null;
        
        // Initialize array of cost labels
        Label[] labels = new Label[graph.size()];
        for (Node n: graph.getNodes()) {
        	labels[n.getId()] = new Label(n);
        }
        
        int originIndex = data.getOrigin().getId();
        int destinationIndex = data.getDestination().getId();
        
        // Initialize origin
        labels[originIndex].setCost(0);
        queue.insert(labels[originIndex]);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        // Core of algorithm
        while (!labels[destinationIndex].isMarked() && !queue.isEmpty()) {
        	
        	// Take next closest unmarked node x, and mark it
        	Label x = queue.deleteMin();
        	x.mark();
        	notifyNodeMarked(x.getNode());
        	
        	// Traverse all of x's successors
        	for (Arc successor: x.getNode().getSuccessors()) {
        		
        		// Small test to check allowed roads
                if (!data.isAllowed(successor)) {
                    continue;
                }
                
                // Update cost to given successor y, if it is unmarked
        		Label y = labels[successor.getDestination().getId()];
        		if (!y.isMarked()) {
        			double oldCost = y.getCost();
        			double newCost = x.getCost() + data.getCost(successor);
        			
        			if (newCost < oldCost) {
        				y.setCost(newCost);
        				y.setPrevious(successor);
        				
        				try {
        					queue.remove(y);
        				} catch (ElementNotFoundException e) {
        					notifyNodeReached(successor.getDestination());
        				} finally {
        					queue.insert(y);
        				}
        			}
        		}
        	}     	
        }
        
        // Destination has no predecessor, the solution is infeasible
        if (labels[destinationIndex].getPrevious() == null) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        } else {
        
        	// The destination has been found, notify the observers.
	        notifyDestinationReached(data.getDestination());
	        
	        // Create path backward from destination
	        ArrayList<Arc> path = new ArrayList<>();
	        Arc segment = labels[destinationIndex].getPrevious();
	        while (segment != null) {
	        	path.add(segment);
	        	segment = labels[segment.getOrigin().getId()].getPrevious();
	        }
	        
	        // Reverse the path
	        Collections.reverse(path);
	        
	        // Create the final solution
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, path));
        }
        
        return solution;
    }

}