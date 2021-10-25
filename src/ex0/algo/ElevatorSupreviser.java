package ex0.algo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import ex0.CallForElevator;
import ex0.Elevator;

public class ElevatorSupreviser {
    LinkedList<CallForElevator> stops;

    ElevatorSupreviser() {
        stops = new LinkedList<CallForElevator>();
    }

    public double bid(Elevator e, CallForElevator c) {
        stops.add(c);
        HashSet<Integer> picked = new HashSet<Integer>();
        HashSet<Integer> dropped = new HashSet<Integer>();
        if (stops.size() == 0) {
            stops.remove(c);
            return findTimeToFloor(e.getPos(), c.getSrc(), e);
        }
        int acc = 0;
        if (stops.peek().getState() == CallForElevator.GOING2SRC)
            acc += findTimeToFloor(e.getPos(), stops.peek().getSrc(), e);
        if (stops.peek().getState() != CallForElevator.DONE)
            acc += findTimeToFloor(stops.peek().getSrc(), stops.peek().getDest(), e);
        picked.add(stops.peek().getSrc());
        picked.add(stops.peek().getDest());
        dropped.add(stops.peek().getDest());
        int lastFloor = stops.peek().getDest();
        for (int i = 1; i < stops.size(); i++) {
            if (stops.get(i).getState() == CallForElevator.DONE) {
                continue;
            }

            // sources
            if (!picked.contains(stops.get(i).getSrc())) {
                if (stops.get(i).getState() == CallForElevator.GOIND2DEST) {
                    continue;
                }
                Vector<Integer> middleStops = getStops(stops.get(i).getSrc(), lastFloor,
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
            if (!dropped.contains(stops.get(i).getDest())) {
                Vector<Integer> middleStops = getStops(stops.get(i).getSrc(), lastFloor,
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

                // TODO check is improves
                if (picked.contains(c.getSrc()) && dropped.contains(c.getDest())) {
                    stops.remove(c);
                    // return acc;
                }
            }
        }

        stops.remove(c);
        return acc;
    }

    public void add(CallForElevator c) {
        stops.add(c);
    }

    public Vector<Integer> getStops(int pos, int dest, int type) {
        Vector<Integer> ret = new Vector<Integer>();
        if (type == CallForElevator.UP) {
            for (CallForElevator c : stops) {
                if (c.getState() == CallForElevator.GOING2SRC && pos < c.getSrc() && c.getSrc() < dest) {
                    ret.add(c.getSrc());
                }
                if (c.getState() == CallForElevator.GOIND2DEST && pos < c.getDest() && c.getDest() < dest) {
                    ret.add(c.getDest());
                }
            }
        } else {
            for (CallForElevator c : stops) {
                if (c.getState() == CallForElevator.GOING2SRC && dest < c.getSrc() && c.getSrc() < pos) {
                    ret.add(c.getSrc());
                }
                if (c.getState() == CallForElevator.GOIND2DEST && dest < c.getDest() && c.getDest() < pos) {
                    ret.add(c.getDest());
                }
            }
        }
        return ret;
    }

    public int getGumberOfCalls() {
        return stops.size();
    }

    public CallForElevator getCall(int i) {
        return stops.get(i);
    }

    public boolean isEmpty() {
        return stops.isEmpty();
    }

    public CallForElevator peek() {
        return stops.peek();
    }

    public CallForElevator poll() {
        return stops.poll();
    }

    /**
     * Calculates the time required to (pickup / drop ) (from / to) floor using this
     * formula closeTime + startTime + speed*numberOfFloors + stopTime + openTime
     * 
     * @param id          of elevator
     * @param destenation floor
     * @return time Time of operation
     */
    private double findTimeToFloor(int pos, int dest, Elevator e) {
        return e.getTimeForClose() + e.getStartTime() + (e.getSpeed() * Math.abs((pos - dest))) + e.getStopTime()
                + e.getTimeForOpen();
    }
}
