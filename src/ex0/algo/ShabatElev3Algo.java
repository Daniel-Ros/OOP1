package ex0.algo;

import java.util.LinkedList;
import java.util.Queue;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

/**
 * A bit better than ShabatElev2Algo: randomly spread the elevators in the init
 * stage, allocate the "closest" elevator..
 */
public class ShabatElev3Algo implements ElevatorAlgo {

    private Building building;

    //
    private Boolean[] freeElevators;
    private LinkedList<CallForElevator>[] eQueue;

    @SuppressWarnings("unchecked")
    public ShabatElev3Algo(Building b) {
        building = b;
        freeElevators = new Boolean[building.numberOfElevetors()];
        eQueue = new LinkedList[building.numberOfElevetors()];
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            freeElevators[i] = true;
            eQueue[i] = new LinkedList<CallForElevator>();
        }
    }

    @Override
    public Building getBuilding() {
        return building;
    }

    @Override
    public String algoName() {
        return "D^2 algo";
    }

    /**
     * The basic logic here is to determine if there is an available elevator to
     * send or is it simply better to send one that is on the way
     */
    @Override
    public int allocateAnElevator(CallForElevator c) {
        // Let's start with sending an free elevator, and sending it
        double min = Integer.MAX_VALUE;
        int imin = 0;
        for (int i = 0; i < eQueue.length; i++) {
            int stops = 0, floors = 0;
            for (int j = 0; j < eQueue[i].size(); j++) {
                CallForElevator call = eQueue[i].get(j);
                if (call.getState() == CallForElevator.GOING2SRC) {
                    floors += Math.abs(building.getElevetor(i).getPos() - call.getSrc());
                    floors += Math.abs(call.getSrc() - call.getDest());
                    stops += 2;
                }
                if (call.getState() == CallForElevator.GOIND2DEST) {
                    floors += Math.abs(building.getElevetor(i).getPos() - call.getDest());
                    stops++;
                }

            }
            Double time = findTimeToFloor(floors, stops, i);
            if (time < min) {
                min = time;
                imin = i;
            }

        }
        eQueue[imin].add(c);
        return imin;
    }

    @Override
    public void cmdElevator(int elev) {
        Elevator e = (Elevator) building.getElevetor(elev);
        if (e.getState() == Elevator.LEVEL) {
            Queue<CallForElevator> queue = eQueue[elev];
            // delete completed tasks
            if (!queue.isEmpty() && queue.peek().getState() == CallForElevator.DONE)
                queue.poll();
            if (!queue.isEmpty()) {
                // goto next task
                try {
                    e.goTo(queue.peek().getState() == CallForElevator.GOIND2DEST ? queue.poll().getDest()
                            : queue.peek().getSrc());
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    /**
     * Calculates the time required to (pickup / drop ) (from / to) floor using this
     * formula closeTime + startTime + speed*numberOfFloors + stopTime + openTime
     * 
     * @param id          of elevator
     * @param destenation floor
     * @return time Time of operation
     */
    private double findTimeToFloor(int floors, int stops, int elev) {
        Elevator e = building.getElevetor(elev);
        return ((e.getTimeForClose() + e.getStartTime()) * stops) + (e.getSpeed() * floors)
                + ((e.getStopTime() + e.getTimeForOpen()) * stops);
    }
}
