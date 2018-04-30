package model;

public class Node {
    int parentIndex;
    int gValue;
    int hValue;

    public Node(int parentIndex, int gValue, int currentIndex) {
        this.parentIndex = parentIndex;
        this.gValue = gValue;
        computeHValue(currentIndex);
    }

    private void computeHValue(int currentIndex) {
        int row = currentIndex/Point.SIZE;
        int column = currentIndex % Point.SIZE;
        hValue =  (Point.SIZE-1-row) + (Point.SIZE-1-column);
    }

    int getFValue() {
        return gValue + hValue;
    }

    void updateParent(int newParentIndex, int newgValue) {
        this.parentIndex = newParentIndex;
        this.gValue = newgValue;
    }
}
