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
    private Queue<CallForElevator>[] eQueue;

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
        int min = Integer.MAX_VALUE, imin = 0;
        for (int i = 0; i < eQueue.length; i++) {
            if (eQueue[i].size() < min) {
                min = eQueue[i].size();
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
            if (!queue.isEmpty()) {
                // delete completed tasks
                if (queue.peek().getState() == CallForElevator.DONE)
                    queue.poll();
                // goto next task
                e.goTo(queue.peek().getState() == CallForElevator.GOIND2DEST ? queue.poll().getDest()
                        : queue.peek().getSrc());
            }
        }
    }
}
