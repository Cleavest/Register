package me.cleavest;

import java.util.function.Function;

/**
 * @author Cleavest
 */
public enum OperationType {
    INCREASE(x -> x + 1,x -> 2, x-> x < 1000000000),
    DECREASE(x -> x - 1,x -> 2, x -> x > 0),
    DOUBLE(x -> x * 2,x -> (x/2) + 1, x -> x > 0.2*x && x <= 1000000000),
    HALF(x -> x / 2,x -> (x/4) + 1, x -> x > 0),
    SQUARE(x -> (int) Math.pow(x , 2), x -> (int) (((Math.pow(x, 2) - 2) / 4) + 1), x -> Math.pow(x, 2) <= 1000000000 ),
    ROOT(x -> (int) Math.sqrt(x), x -> (int) (((x - Math.sqrt(x)) / 4) + 1), x -> x > 1 && Math.sqrt(x) % 2 == 0);

    Function<Integer, Integer> operate;
    Function<Integer, Integer> cost;
    Function<Integer, Boolean> condition;

    OperationType(Function<Integer, Integer> operate,Function<Integer, Integer> cost,Function<Integer, Boolean> condition ) {
        this.operate = operate;
        this.cost = cost;
        this.condition = condition;
    }

    public Function<Integer, Integer> getOperate() {
        return operate;
    }

    public Function<Integer, Integer> getCost() {
        return cost;
    }

    public Function<Integer, Boolean> getCondition() {
        return condition;
    }
}
