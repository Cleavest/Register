package me.cleavest;

/**
 * @author Cleavest
 */
public class Node {

    private int number;
    private OperationType operation;        // the last operation
    private double cost;                    // cost of operation
    private int h;              // the value of the heuristic function for this node
    private int g;              // the depth of this node wrt the root of the search tree
    private int f;              // f=0 or f=h or f=h+g, depending on the search algorithm used.
    private Node parent;        //the parent node (NULL for the root).

    public Node() {

    }

    public int getNumber() {
        return number;
    }

    public OperationType getOperation() {
        return operation;
    }

    public int getH() {
        return h;
    }

    public int getG() {
        return g;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getF() {
        return f;
    }

    public Node getParent() {
        return parent;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setG(int g) {
        this.g = g;
    }

    public void setF(int f) {
        this.f = f;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }
}
