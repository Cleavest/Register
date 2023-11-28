package me.cleavest;

import java.util.*;

/**
 * @author Cleavest
 */
public class Algo {

    private final int start;
    private final int goal;

    PriorityQueue<Node> priorityQueue = new PriorityQueue<>(Comparator.comparing(Node::getF));
    LinkedList<Node> queue = new LinkedList();
    Stack<Node> stack = new Stack<>();

    Set<Integer> closed = new HashSet<>();

    TimeUtil timeUtil = new TimeUtil();

    public Algo(int start, int goal) {
        this.start = start;
        this.goal = goal;
    }


    public TimeUtil getTimeUtil() {
        return timeUtil;
    }

    public boolean checkClosed(Node newNode) {

        if (!closed.contains(newNode.getNumber())){
            closed.add(newNode.getNumber());
            return true;
        }

        return false;
    }

    public List<Node> findPath(AlgorithmMethod method){
        timeUtil.reset();
        Node root = new Node();
        root.setNumber(start);
        root.setParent(null);
        root.setOperation(null);
        root.setG(0);
        root.setH(0);
        root.setF(0);
        root.setCost(0);

        add(root, method);

        while (!isEmpty(method)) {
            if (timeUtil.hasReached(1000 * 60)) {
                System.out.println("Timeout");
                return null;
            }

            Node current_node = getNode(method);

            if (isSolution(current_node.getNumber())) {
                return getPath(current_node);
            }

            addAll(findNode(current_node, method), method);
        }

        return null;
    }

    /**
     * this method gets the node from the data structure
     * @return node
     */
    public Node getNode(AlgorithmMethod method){
        if (method == AlgorithmMethod.DEPTH) {
            return stack.pop();
        } else if (method == AlgorithmMethod.BREADTH){
            return queue.poll();
        } else {
            return priorityQueue.poll();
        }
    }

    /**
     * this method checks if the data structure uses used for a specific algorithm is empty
     * @param method specific algorithm
     */
    public boolean isEmpty(AlgorithmMethod method){
        if (method == AlgorithmMethod.DEPTH) {
            return stack.isEmpty();
        } else if (method == AlgorithmMethod.BREADTH){
            return queue.isEmpty();
        } else {
            return priorityQueue.isEmpty();
        }
    }

    /**
     * this method add node to the data structure depending on the type of the algorithm
     * @param node Node
     */
    public void add( Node node, AlgorithmMethod method){
        if (method == AlgorithmMethod.DEPTH) {
            stack.push(node);
        } else if (method == AlgorithmMethod.BREADTH){
            queue.add(node);
        } else {
            priorityQueue.add(node);
        }
    }

    /**
     * this method adds nodes to the data structure depending on the type of the algorithm
     * @param c Collection of nodes
     */
    public void addAll(Collection<? extends Node> c, AlgorithmMethod method){
        if (method == AlgorithmMethod.DEPTH) {
            stack.addAll(c);
        } else if (method == AlgorithmMethod.BREADTH){
            queue.addAll(c);
        } else {
            priorityQueue.addAll(c);
        }
    }

    /**
     * this method creates nodes if the operation conditions are met
     * @param currentNode parent node
     * @return list of nodes
     */
    public List<Node> findNode(Node currentNode, AlgorithmMethod method) {
        int x = currentNode.getNumber();
        List<Node> list = new ArrayList<>();

        for (OperationType operation : OperationType.values()) {
            if (operation.getCondition().apply(x)){
                Node child = new Node();

                child.setParent(currentNode);
                child.setOperation(operation);
                child.setG(currentNode.getG() + 1);
                child.setNumber(operation.getOperate().apply(x));
                child.setCost(operation.getCost().apply(x));

                if (checkClosed(child)){
                    child.setH(heuristic(x, operation));

                    if (method == AlgorithmMethod.BEST){
                        child.setF(child.getH());
                    } else if (method == AlgorithmMethod.ASTAR) {
                        child.setF(child.getG() + child.getH());
                    } else {
                        child.setF(0);
                    }

                    list.add(child);
                }
            }
        }

        return list;
    }

    /**
     * @param currentNode goal node
     * @return list of path at start to goal
     */
    public List<Node> getPath(Node currentNode) {
        List<Node> path = new ArrayList<Node>();
        path.add(currentNode);
        Node parent;
        while ((parent = currentNode.getParent()) != null) {
            path.add(0, parent);
            currentNode = parent;
        }
        return path;
    }

    /**
     * This method checking if a is solution
     */
    public boolean isSolution(int a) {
        return a == goal;
    }

    public int heuristic(int x, OperationType operation){
        return operation.getCost().apply(x);
    }

}
