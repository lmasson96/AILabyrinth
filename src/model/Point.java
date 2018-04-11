package model;

public class Point {
    public static final int SIZE = 5;

    public int x;
    public int y;


    /**
     * Constructor
     * @param _x row
     * @param _y column
     */
    public Point(int _x, int _y){
        this.x=_x;
        this.y=_y;
    }

    /**
     * Test if coordinate is valid
     * @return true if coordinate is in the labyrinth
     */
    public boolean isValid(){
        return (this.x >= 0 && this.y >= 0 && this.x < 2*SIZE+1 && this.y < 2*SIZE+1);
    }

    /**
     * Test type of coordinate
     * @return true if coordinate is tile, false if possible border
     */
    public boolean isTile(){
        return (this.x % 2 == 1 && this.y % 2 == 1);
    }

    /**
     * Compares parameter object with current coordinate
     * @param o
     * @return true is x and y attributes match
     */
    public boolean equals(Object o){
        if(o instanceof Point){
            return (this.x==((Point)o).x && this.y==((Point)o).y);
        }
        return false;
    }
}
