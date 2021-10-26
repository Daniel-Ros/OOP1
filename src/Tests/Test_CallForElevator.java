package Tests;

import ex0.CallForElevator;
import ex0.Elevator;

public class Test_CallForElevator implements CallForElevator {
    int floor,dest;

    public Test_CallForElevator (int floor,int destination){
        this.floor=floor;
        this.dest=destination;
    }
    @Override
    public int getState() {
        return 0;
    }

    @Override
    public double getTime(int state) {
        return 0;
    }

    @Override
    public int getSrc() {
        return floor;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public int getType() {
        if (dest<floor)
            return DOWN;
        else if(dest>floor)
            return UP;
        else return DONE; /** NOT SURE **/
    }

    @Override
    public int allocatedTo() {
        return 0;
    }
}
