package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class LabelStar extends Label {
	
	// LENGTH mode: Euclidean distance from this node to the destination node in meters
	// TIME mode: Time from this node to the destination node traveling at maximum speed
	private double estCostToDestination;
	
	public LabelStar(Node node, Node destination) {
		super(node);
		
		this.estCostToDestination = node.getPoint().distanceTo(destination.getPoint());
	}
	
	public LabelStar(Node node, Node destination, int maxSpeed) {
		super(node);
		
		this.estCostToDestination = node.getPoint().distanceTo(destination.getPoint()) / maxSpeed;
	}
	
	@Override
	public double getTotalCost() {
		return this.getCost() + this.estCostToDestination;
	}
}
