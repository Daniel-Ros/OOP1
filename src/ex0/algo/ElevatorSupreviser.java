package ex0.algo;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import ex0.CallForElevator;
import ex0.Elevator;

public class ElevatorSupreviser {
    LinkedList<CallForElevator> stops;
    HashSet<Integer> mStops;

    ElevatorSupreviser() {
        stops = new LinkedList<CallForElevator>();
        mStops = new HashSet<Integer>();
    }

    /**
     * This function gives an estimation to how long it would take to complete all
     * of it tasks given {@code c} was in is
     * 
     * @param e The given elevator
     * @param c The call to complete
     * @return
     */
    public double bid(Elevator e, CallForElevator c) {
        // adds the call temporary
        stops.add(c);

        // stores which floors we visted in case we have the same floor twice
        HashSet<Integer> picked = new HashSet<Integer>();
        HashSet<Integer> dropped = new HashSet<Integer>();
        // if no jobs just calculate the time it will
        // take the elevator to complete the job
        if (stops.size() == 0) {
            stops.remove(c);
            return findTimeToFloor(e.getPos(), c.getSrc(), e) + findTimeToFloor(c.getSrc(), c.getDest(), e);
        }
        int acc = 0;
        int lastFloor = e.getPos();
        // if we need to go the source
        if (stops.peek().getState() < CallForElevator.GOIND2DEST) {
            acc += findTimeToFloor(e.getPos(), stops.peek().getSrc(), e);
            picked.add(stops.peek().getSrc());
        }
        // if we need to go the destenation
        if (stops.peek().getState() < CallForElevator.DONE) {
            acc += findTimeToFloor(stops.peek().getSrc(), stops.peek().getDest(), e);
            picked.add(stops.peek().getDest());
            dropped.add(stops.peek().getDest());
            lastFloor = stops.peek().getDest();
        }
        // simulate the route
        for (int i = 1; i < stops.size(); i++) {
            // if finished job, skip it
            if (stops.get(i).getState() == CallForElevator.DONE) {
                continue;
            }

            // sources
            if (!picked.contains(stops.get(i).getSrc()) && stops.get(i).getState() < CallForElevator.GOIND2DEST) {
                // calculate the stops along a path using the original method so we get
                // consistent resualts
                Vector<Integer> middleStops = getStops(lastFloor, stops.get(i).getSrc(),
                        lastFloor > stops.get(i).getSrc() ? CallForElevator.UP : CallForElevator.DOWN);
                for (Integer s : middleStops) {
                    picked.add(s);
                    dropped.add(s);
                    acc += findTimeToFloor(lastFloor, s, e);
                    lastFloor = s;
                }
                picked.add(stops.get(i).getSrc());
                acc += findTimeToFloor(lastFloor, stops.get(i).getSrc(), e);
                lastFloor = stops.get(i).getSrc();
            }

            // destnetions
            if (!dropped.contains(stops.get(i).getDest()) && stops.get(i).getState() < CallForElevator.DONE) {
                Vector<Integer> middleStops = getStops(lastFloor, stops.get(i).getDest(),
                        lastFloor > stops.get(i).getDest() ? CallForElevator.UP : CallForElevator.DOWN);
                for (Integer s : middleStops) {
                    picked.add(s);
                    dropped.add(s);
                    acc += findTimeToFloor(lastFloor, s, e);
                    lastFloor = s;
                }

                picked.add(stops.get(i).getDest());
                dropped.add(stops.get(i).getDest());
                acc += findTimeToFloor(lastFloor, stops.get(i).getDest(), e);
                lastFloor = stops.get(i).getDest();
            }
        }

        // removes the call
        stops.remove(c);
        return acc;
    }

    /**
     * adds {@code c} to queue
     * 
     * @param c The call to add
     */
    public void add(CallForElevator c) {
        stops.add(c);
    }

    public int getStop() {
        mStops = new HashSet<Integer>();
        return stops.peek().getState() == CallForElevator.GOING2SRC ? stops.peek().getSrc() : stops.peek().getDest();
    }

    /**
     * calculate the stop the elevator can take in the current trip
     * 
     * @param pos  the current position of the elevator
     * @param dest the current next stop fo the elevator
     * @param type if the current job is up or down
     * @return vector of all the floors the elevator can stop in
     */
    public Vector<Integer> getStops(int pos, int dest, int type) {
        Vector<Integer> ret = new Vector<Integer>();
        if (type == CallForElevator.UP) {
            for (CallForElevator c : stops) {
                if (c.getState() == CallForElevator.GOING2SRC && pos < c.getSrc() && c.getSrc() < dest) {
                    if (!mStops.contains(c.getSrc()))
                        ret.add(c.getSrc());
                }
                if (c.getState() == CallForElevator.GOIND2DEST && pos < c.getDest() && c.getDest() < dest) {
                    if (!mStops.contains(c.getDest()))
                        ret.add(c.getDest());
                }
                ret.sort(Comparator.naturalOrder());
            }
        } else {
            for (CallForElevator c : stops) {
                if (c.getState() == CallForElevator.GOING2SRC && dest < c.getSrc() && c.getSrc() < pos) {
                    if (!mStops.contains(c.getSrc()))
                        ret.add(c.getSrc());
                }
                if (c.getState() == CallForElevator.GOIND2DEST && dest < c.getDest() && c.getDest() < pos) {
                    if (!mStops.contains(c.getDest()))
                        ret.add(c.getDest());
                }
            }
            ret.sort(Comparator.reverseOrder());
        }
        mStops.addAll(ret);
        return ret;
    }

    /***
     * get the number of calls
     * 
     * @return number of calls
     */
    public int getGumberOfCalls() {
        return stops.size();
    }

    /**
     * This implementation returns {@code queue.size() == 0}.
     * 
     * @return whether the queue is empty
     */
    public boolean isEmpty() {
        return stops.isEmpty();
    }

    /**
     * Retrieves, but does not remove, the head (first element) of this queue.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    public CallForElevator peek() {
        return stops.peek();
    }

    /**
     * Retrieves and removes the head (first element) of this list.
     *
     * @return the head of this queue, or {@code null} if this queue is empty
     */
    public CallForElevator poll() {
        return stops.poll();
    }

    /**
     * Calculates the time required to (pickup / drop ) (from / to) floor using this
     * formula closeTime + startTime + numberOfFloors / speed + stopTime + openTime
     * 
     * @param id          of elevator
     * @param destenation floor
     * @return time Time of operation
     */
    private double findTimeToFloor(int pos, int dest, Elevator e) {
        return e.getTimeForClose() + e.getStartTime() + (Math.abs((pos - dest)) / e.getSpeed()) + e.getStopTime()
                + e.getTimeForOpen();
    }
}
