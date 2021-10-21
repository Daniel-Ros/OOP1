package ex0.algo;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class ElevSup {

    enum STATES {
        UP, DOWN, IDLE, ERROR, WATINGTOSTART
    }

    private int dest;
    private int pos;
    private int currentTrip;
    private boolean pickedup;
    private HashMap<Integer, LinkedList<Integer>> stops;
    private STATES state;

    ElevSup() {
        state = STATES.IDLE;
        dest = Integer.MAX_VALUE;
        pos = 0;
        currentTrip = 0;
        stops = new HashMap<Integer, LinkedList<Integer>>();
    }

    public void addTrip(int src, int dest) {
        stops.putIfAbsent(src, new LinkedList<>());
        stops.get(src).add(dest);
    }

    public void setPos(int pos) {
        if (stops.get(currentTrip) != null)
            stops.get(currentTrip).remove((Integer) pos);
        if (pos == currentTrip) {
            pickedup = true;
            if (stops.get(currentTrip) != null)
                if (stops.get(currentTrip).peek() > currentTrip)
                    stops.get(currentTrip).sort(Comparator.naturalOrder());
                else
                    stops.get(currentTrip).sort(Comparator.reverseOrder());
        }
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
        if (stops.size() == 0)
            return Integer.MAX_VALUE;
        if (stops.get(currentTrip) == null) {
            currentTrip = findNextPassenger();
        }
        if (stops.get(currentTrip).size() == 0) {
            stops.remove(currentTrip);
            currentTrip = findNextPassenger();
        }
        return pickedup ? stops.get(currentTrip).peek() : currentTrip;
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

    private int findNextPassenger() {
        pickedup = false;
        int ret = 0;
        int minDistance = Integer.MAX_VALUE;
        for (int i : stops.keySet()) {
            if (Math.abs(pos - i) < minDistance) {
                minDistance = Math.abs(pos - i);
                ret = i;
            }
        }
        return ret;
    }

}
