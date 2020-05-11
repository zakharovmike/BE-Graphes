package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.algorithm.AbstractInputData;

public class AStarAlgorithm extends DijkstraAlgorithm {
	
    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override
    protected void initCostLabels() {

    	// Initialize labels for shortest time
    	if (data.getMode() == AbstractInputData.Mode.TIME) {
    		int maxSpeed = 1;
    		
    		if (this.graph.getGraphInformation().hasMaximumSpeed()) {
    			maxSpeed = this.graph.getGraphInformation().getMaximumSpeed();
    		}
        	
        	this.labels = new LabelStar[this.graph.size()];
        	Node destination = super.getInputData().getDestination();
        	for (Node n: this.graph.getNodes()) {
            	this.labels[n.getId()] = new LabelStar(n, destination, maxSpeed);
            }
        }
    	
    	// Initialize labels for shortest distance
    	if (data.getMode() == AbstractInputData.Mode.LENGTH) {
        	this.labels = new LabelStar[this.graph.size()];
        	Node destination = super.getInputData().getDestination();
        	for (Node n: this.graph.getNodes()) {
            	this.labels[n.getId()] = new LabelStar(n, destination);
            }
        }
    }

}
