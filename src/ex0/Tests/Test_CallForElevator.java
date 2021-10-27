package ex0.Tests;

import ex0.CallForElevator;

public class Test_CallForElevator implements CallForElevator {
    int src,dest,state;

    public Test_CallForElevator (int floor, int destination){
        this.src =floor;
        this.dest=destination;
        state = 1;
    }

    @Override
    public int getState() {
        return state;
    }
    @Override
    public double getTime(int state) {
        return 0;
    }

    @Override
    public int getSrc() {
        return src;
    }

    @Override
    public int getDest() {
        return dest;
    }

    @Override
    public int getType() {
        if (dest< src)
            return DOWN;
        else if(dest> src)
            return UP;
        else return DONE; /** NOT SURE **/
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int allocatedTo() {
        return 0;
    }
}
