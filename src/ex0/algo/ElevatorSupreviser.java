package ex0.algo;

import java.util.LinkedList;

import ex0.CallForElevator;

public class ElevatorSupreviser {
    LinkedList<CallForElevator> stops;

    ElevatorSupreviser() {
        stops = new LinkedList<CallForElevator>();
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

    public int getGumberOfCalls() {
        return stops.size();
    }

    public CallForElevator getCall(int i) {
        return stops.get(i);
    }

    public LinkedList<CallForElevator> getQueue() {
        return stops;
    }
}
