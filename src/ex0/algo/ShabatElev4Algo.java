package ex0.algo;

import ex0.Building;
import ex0.CallForElevator;
import ex0.Elevator;
import ex0.algo.ElevSup.STATES;

public class ShabatElev4Algo implements ElevatorAlgo {

    private Building building;
    private ElevSup[] eQueue;

    public ShabatElev4Algo(Building b) {
        building = b;
        eQueue = new ElevSup[b.numberOfElevetors()];
        for (int i = 0; i < eQueue.length; i++) {
            eQueue[i] = new ElevSup();
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
        var retElevator = findFreeElevator(c.getSrc(), c.getDest());
        if (retElevator == Integer.MAX_VALUE)
            retElevator = 0;

        eQueue[retElevator].addTrip(c.getSrc(), c.getDest());
        return retElevator;
    }

    @Override
    public void cmdElevator(int elev) {
        ElevSup es = eQueue[elev];
        Elevator e = (Elevator) building.getElevetor(elev);

        es.setPos(e.getPos());

        var stop = es.nextStop();
        if (stop == Integer.MAX_VALUE)
            return;

        e.goTo(stop);
    }

    /**
     * This function returns the best free elevator given this params: - distance to
     * the src floor - if found 2 or more equal elvators go by dest
     * 
     * @param src  floor
     * @param dest dest floor
     * @return the best free elevator
     */
    private int findFreeElevator(int src, int dest) {
        int min = Integer.MAX_VALUE, ret = Integer.MAX_VALUE;
        // step 1: look for free elevator and chose the closest
        for (int i = 0; i < eQueue.length; i++) {
            if (eQueue[i].getState() == STATES.IDLE)
                if (findTimeToFloor(i, src) < min) {
                    min = Math.abs(src - building.getElevetor(i).getPos());
                    ret = i;
                }
        }
        return ret;
    }

    /**
     * Calculates the time required to (pickup / drop ) (from / to) floor using this
     * formula closeTime + startTime + speed*numberOfFloors + stopTime + openTime
     * 
     * @param id          of elevator
     * @param destenation floor
     * @return time Time of operation
     */
    private double findTimeToFloor(int elev, int dest) {
        Elevator e = building.getElevetor(elev);
        return e.getTimeForClose() + e.getStartTime()
                + (e.getSpeed() * Math.abs((int) (e.getPos() - dest))) * e.getStopTime() + e.getTimeForOpen();
    }
}
