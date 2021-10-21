package ex0.algo;

import java.lang.Thread.State;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ElevSup {

    enum STATES {
        UP, DOWN, IDLE, ERROR, WATINGTOSTART
    }

    private int dest;
    private int pos;
    private PriorityQueue<Job> stops;
    private STATES state;

    ElevSup() {
        state = STATES.IDLE;
        dest = Integer.MAX_VALUE;
        pos = 0;
        stops = new PriorityQueue<Job>();
    }

    public void addDest(int dest) {
        var job = new Job(dest, 1);
        stops.add(job);
    }

    public void addSrc(int dest) {
        var job = new Job(dest, 0);
        stops.add(job);
    }

    public void setPos(int pos) {
        stops.remove(new Job(pos, 0));
        stops.remove(new Job(pos, 1));
        this.pos = pos;
    }

    public void setState(int state) {
        switch (state) {
        case 1:
            this.state = STATES.UP;
            break;
        case 0:
            this.state = STATES.IDLE;
            break;
        case -1:
            this.state = STATES.DOWN;
            break;
        case -2:
            this.state = STATES.ERROR;
            break;
        default:
            break;
        }
    }

    public int nextStop() {
        if (stops.isEmpty())
            return Integer.MAX_VALUE;
        return stops.peek().getFloor();
    }

    public int getDest() {
        return dest;
    }

    public int getPos() {
        return pos;
    }

    public STATES getState() {
        return state;
    }

}
