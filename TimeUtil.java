package me.cleavest;

/**
 * @author Cleavest
 */
public class TimeUtil {

    long l;
    public TimeUtil() {
        reset();
    }

    public boolean hasReached(long time){
        return System.currentTimeMillis() >= l + time ;
    }

    public void reset() {
        this.l = System.currentTimeMillis();
    }

    public long getTime() {
        return System.currentTimeMillis() - l;
    }
}
