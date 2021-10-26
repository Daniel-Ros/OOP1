package Tests;

import ex0.Elevator;

public class Test_Elevator implements Elevator {
    int minFloor,maxFloor,ID,state,position;
    double timeForOpen,timeForClose,stopTime,startTime,speed;

    public Test_Elevator (int min,int max,int ID,double openTime,double closeTime,double speed,double startTime,double stopTime,int state){
        this.minFloor=min;
        this.maxFloor=max;
        this.ID=ID;
        this.timeForOpen=openTime;
        this.timeForClose=closeTime;
        this.speed=speed;
        this.startTime=startTime;
        this.stopTime=stopTime;
        this.state=state;
    }

    @Override
    public int getMinFloor() {
        return this.minFloor;
    }

    @Override
    public int getMaxFloor() {
        return this.maxFloor;
    }

    @Override
    public double getTimeForOpen() {
        return this.timeForOpen;
    }

    @Override
    public double getTimeForClose() {
        return this.timeForClose;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public int getPos() {
        return this.position;
    }

    @Override
    public boolean goTo(int floor) {
        return false;
    }

    @Override
    public boolean stop(int floor) {
        return false;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public double getStartTime() {
        return this.startTime;
    }

    @Override
    public double getStopTime() {
        return this.stopTime;
    }

    @Override
    public int getID() {
        return this.ID;
    }
}
