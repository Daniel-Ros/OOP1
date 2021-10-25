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

    //
    private Boolean[] freeElevators;
    private ElevatorSupreviser[] eQueue;

    public ShabatElev3Algo(Building b) {
        building = b;
        freeElevators = new Boolean[building.numberOfElevetors()];
        eQueue = new ElevatorSupreviser[building.numberOfElevetors()];
        for (int i = 0; i < building.numberOfElevetors(); i++) {
            freeElevators[i] = true;
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
     * The basic logic here is to determine if there is an available elevator to
     * send or is it simply better to send oneimport ex0.algo.ShabatElev3AlgoCopy;
     * import ex0.algo.ShabatElev4Algo; that is on the way
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

    @Override
    public void cmdElevator(int elev) {
        Elevator e = (Elevator) building.getElevetor(elev);
        if (e.getState() == Elevator.LEVEL) {
            ElevatorSupreviser queue = eQueue[elev];
            // delete completed tasks
            while (!queue.isEmpty() && queue.peek().getState() == CallForElevator.DONE)
                queue.poll();
            if (!queue.isEmpty()) {
                // goto next task
                int stop = queue.peek().getState() == CallForElevator.GOING2SRC ? queue.peek().getSrc()
                        : queue.peek().getDest();
                e.goTo(stop);
            }
        } else if (e.getState() == Elevator.UP) {
            ElevatorSupreviser queue = eQueue[elev];
            int stop = queue.peek().getState() == CallForElevator.GOING2SRC ? queue.peek().getSrc()
                    : queue.peek().getDest();
            Vector<Integer> stops = queue.getStops(e.getPos(), stop, Elevator.UP);
            for (Integer s : stops) {
                e.stop(s);
            }
        } else if (e.getState() == Elevator.DOWN) {
            ElevatorSupreviser queue = eQueue[elev];
            int stop = queue.peek().getState() == CallForElevator.GOING2SRC ? queue.peek().getSrc()
                    : queue.peek().getDest();
            Vector<Integer> stops = queue.getStops(e.getPos(), stop, Elevator.DOWN);
            for (Integer s : stops) {
                e.stop(s);
            }
        }
    }

}
