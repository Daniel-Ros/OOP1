package ex0.algo;

import java.util.Vector;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;

/**
 * A bit better than ShabatElev2Algo: randomly spread the elevators in the init
 * stage, allocate the "closest" elevator..
 */
public class ShabatElev3Algo implements ElevatorAlgo {

    private Building building;
    private ElevatorSupreviser[] eQueue;

    public ShabatElev3Algo(Building b) {
        building = b;
        eQueue = new ElevatorSupreviser[building.numberOfElevetors()];
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            eQueue[i] = new ElevatorSupreviser();
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
     * this function allocates an elevator by calculating how much each elevator
     * would the to finish the job and picking this one
     */
    @Override
    public int allocateAnElevator(CallForElevator c) {
        // Let's start with sending an free elevator, and sending it
        double min = Integer.MAX_VALUE;
        int imin = 0;
        for (int i = 0; i < eQueue.length; i++) {
            Double time = eQueue[i].bid(building.getElevetor(i), c);
            if (time < min) {
                min = time;
                imin = i;
            }
        }
        eQueue[imin].add(c);
        return imin;
    }

    /**
     * send each elevator to its next stop and checks if given elevator can stop on
     * its way there
     */
    @Override
    public void cmdElevator(int elev) {
        Elevator e = (Elevator) building.getElevetor(elev);
        // gets the next stop and tell the elevator to go there
        if (e.getState() == Elevator.LEVEL) {
            ElevatorSupreviser queue = eQueue[elev];
            // delete completed tasks
            while (!queue.isEmpty() && queue.peek().getState() == CallForElevator.DONE)
                queue.poll();
            if (!queue.isEmpty()) {
                // goto next task
                int stop = queue.getStop();
                e.goTo(stop);
            }
        }
        // this 2 next if statment checks if we can stop on our way.
        else if (e.getState() != Elevator.ERROR) {
            ElevatorSupreviser queue = eQueue[elev];
            int stop = queue.peek().getState() == CallForElevator.GOING2SRC ? queue.peek().getSrc()
                    : queue.peek().getDest();
            Vector<Integer> stops = queue.getStops(e.getPos(), stop, e.getState());
            for (Integer s : stops) {
                e.stop(s);
            }
        }
    }

}
