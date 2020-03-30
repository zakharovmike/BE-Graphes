package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {
	
	private final Node node;
	private boolean marked;
	private double cost;
	private Arc fromPrevious;
	
	public Label(Node node) {
		this.node = node;
		this.marked = false;
		this.cost = Double.POSITIVE_INFINITY;
		this.fromPrevious = null;
	}
	
	public Node getNode() {
		return this.node;
	}
	
	public boolean isMarked() {
		return marked;
	}
	
	public void mark() {
		this.marked = true;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(double newCost) {
		this.cost = newCost;
	}
	
	public Arc getPrevious() {
		return this.fromPrevious;
	}
	
	public void setPrevious(Arc fromPrevious) {
		this.fromPrevious = fromPrevious;
	}
	
	public int compareTo(Label other) {
		return Double.compare(this.getCost(), other.getCost());
	}

}