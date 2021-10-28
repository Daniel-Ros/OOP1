package ex0.Tests;

import ex0.Elevator;

public class Test_Elevator implements Elevator {
    int minFloor,maxFloor,ID,state,position;
    double timeForOpen,timeForClose,stopTime,startTime,speed;
    int lastGoto;



    public Test_Elevator (int pos, double speed, double openCloseSpeed, double startStopSpeed){
        this.minFloor=-20;
        this.maxFloor=100;
        this.ID=0;
        this.timeForOpen=openCloseSpeed;
        this.timeForClose=openCloseSpeed;
        this.speed=speed;
        this.startTime=startStopSpeed;
        this.stopTime=startStopSpeed;
        this.state=LEVEL;
        this.position = pos;
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
        lastGoto = floor;
        return false;
    }
    public int getLastGoto() {
        return lastGoto;
    }

    @Override
    public boolean stop(int floor) {
        lastGoto = floor;
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

    public void setState(int state) {
        this.state = state;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
