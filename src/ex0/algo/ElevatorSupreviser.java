package ex0.algo;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

import ex0.CallForElevator;
import ex0.Elevator;

public class ElevatorSupreviser {
    LinkedList<CallForElevator> stops;
    int lastState;

    ElevatorSupreviser() {
        stops = new LinkedList<CallForElevator>();
    }

    public double bid(Elevator e, CallForElevator c) {
        HashSet<Integer> picked = new HashSet<Integer>();
        if (stops.size() == 0) {
            return findTimeToFloor(e.getPos(), c.getSrc(), e);
        }
        int acc = 0;
        acc += findTimeToFloor(e.getPos(), stops.peek().getSrc(), e);
        acc += findTimeToFloor(stops.peek().getSrc(), stops.peek().getDest(), e);
        picked.add(stops.peek().getSrc());
        picked.add(stops.peek().getDest());
        int lastFloor = stops.peek().getDest();
        for (int i = 1; i < stops.size(); i++) {

            if (!picked.contains(stops.get(i).getSrc())) {
                acc += findTimeToFloor(lastFloor, stops.get(i).getSrc(), e);
                lastFloor = stops.peek().getSrc();
                if (c.getSrc() == lastFloor) {
                    return acc;
                }
            }
            acc += findTimeToFloor(lastFloor, stops.get(i).getDest(), e);
            lastFloor = stops.peek().getDest();
            if (c.getSrc() == lastFloor) {
                return acc;
            }
        }
        return acc;
    }

    public void add(CallForElevator c) {
        for (int i = 0; i < stops.size(); i++) {
            // Same source and direction so let's pack it up!
            // If it up call, put is above, if its down call so put it below
            if (c.getSrc() == stops.get(i).getSrc() && c.getType() == stops.get(i).getType()) {
                if (c.getType() == CallForElevator.UP) { // UP
                    if (c.getDest() > stops.get(i).getDest()) {
                        while (i < stops.size() && stops.get(i).getSrc() == c.getSrc())
                            i++;
                        stops.add(i, c);
                    } else
                        stops.add(i, c);
                } else { // DOWN
                    if (c.getDest() > stops.get(i).getDest())
                        stops.add(i, c);
                    else {
                        while (i < stops.size() && stops.get(i).getSrc() == c.getSrc())
                            i++;
                        stops.add(i, c);
                    }
                }
                break;
            }
        }
        stops.add(c);
    }

    public Vector<Integer> getStops(int pos, int dest, int type) {
        Vector<Integer> ret = new Vector<Integer>();
        if (type == CallForElevator.UP) {
            for (CallForElevator c : stops) {
                if (c.getState() == CallForElevator.GOING2SRC && c.getSrc() > pos && c.getSrc() != dest) {
                    ret.add(c.getSrc());
                }
                if (c.getState() == CallForElevator.GOIND2DEST && c.getDest() < dest) {
                    ret.add(c.getDest());
                }
            }
        } else {
            for (CallForElevator c : stops) {
                if (c.getState() == CallForElevator.GOING2SRC && c.getSrc() < pos && c.getSrc() != dest) {
                    ret.add(c.getSrc());
                }
                if (c.getState() == CallForElevator.GOIND2DEST && c.getDest() > dest) {
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

    public LinkedList<CallForElevator> getQueue() {

        return stops;
    }

    public boolean isEmpty() {
        return stops.isEmpty();
    }

    public CallForElevator peek() {
        return stops.peek();
    }

    public CallForElevator poll() {
        lastState = stops.peek().getType();
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
