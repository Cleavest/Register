package me.cleavest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Cleavest
 */
public class Main {
    public static void main(String[] args) {
        AlgorithmMethod method;
        int start;
        int goal;
        String fileName;

        if (args.length != 4){
            sendSyntaxMessage();
            return;
        }

        if (!isMethod(args[0])){
            sendSyntaxMessage();
            return;
        }

        method = AlgorithmMethod.valueOf(args[0].toUpperCase());

        if (!isNumeric(args[1]) || !isNumeric(args[2])){
            sendSyntaxMessage();
            return;
        }

        start = Integer.parseInt(args[1]);
        goal = Integer.parseInt(args[2]);

        if (start < 0 || goal < 0) {
            System.out.println("the numbers should not be negative");
            return;
        }

        fileName = args[3];

        System.out.println(String.format("Starting solving with %s method", method.name()));

        Algo algo = new Algo(start, goal);

        List<Node> nodes = algo.findPath(method);

        if (nodes == null) {
            System.out.println("Not found solution");
            return;
        }

        long totalCost = 0;

        for (Node node : nodes) {
            totalCost += node.getCost();
        }

        System.out.println("Result found in " + algo.getTimeUtil().getTime() +" milliseconds. cost " + totalCost + " steps " + (nodes.size() - 1));

        writeResult(fileName, nodes);
    }

    /**
     * This method write in file path
     */
    private static void writeResult(String nameFile,  List<Node> nodes){
        File file = new File(nameFile);

        try {
            PrintWriter pw = new PrintWriter(file);

            int totalCost = 0;

            for (Node node : nodes) {
                totalCost += node.getCost();
            }

            pw.println(String.format("%d, %d", nodes.size() - 1, totalCost));

            nodes.stream().skip(1).forEach(node -> {
                pw.println(node.getOperation().toString() + " " + node.getNumber() + " " + node.getCost());
            });
            pw.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method display a message in case of wrong input parameters
     */
    private static void sendSyntaxMessage(){
        String[] message = {"main <method> <start> <goal> <output-file>",
                "- <method> = breadth|depth|best|astar",
                "- start is number",
                "- goal is number.",
                "- <output-file> is the file where the solution will be written."};

        for (String s : message) {
            System.out.println(s);
        }
    }


    /**
     * This method checking if str is method
     * @param str String.
     * @return true if str is method or false if not method.
     */
    private static boolean isMethod(String str){
        for (AlgorithmMethod method : AlgorithmMethod.values()) {
            if (method.toString().equalsIgnoreCase(str)) {
                return true;
            }
        }

        return false;
    }

    /**
     * method checking if str is numeric
     * @param str String.
     * @return true if str is numeric or false if not numeric.
     */
    private static boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }
}