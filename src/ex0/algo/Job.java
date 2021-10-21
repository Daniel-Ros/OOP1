package ex0.algo;

public class Job implements Comparable<Job> {
    private int floor;
    private int priority;

    public Job(int floor, int priority) {
        this.floor = floor;
        this.priority = priority;
    }

    public int getFloor() {
        return floor;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Job o) {
        if (priority == o.priority)
            return floor - o.floor;
        return priority - o.priority;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Job) {
            var other = (Job) obj;
            return (floor == other.floor) && (priority == other.priority);
        }
        return false;
    }
}
