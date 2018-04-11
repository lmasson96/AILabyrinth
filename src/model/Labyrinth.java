package model;


import java.util.ArrayList;
import java.util.List;

public class Labyrinth {

   private boolean maze[][];

    /**
     * Constructor
     */
    public Labyrinth(){
        maze = new boolean[Point.SIZE*2+1][Point.SIZE*2+1];
        for(int i=0; i<=Point.SIZE*2; ++i){
            for(int j=0; j<=Point.SIZE*2; ++j){
                maze[i][j] = true;
            }
        }

        for(Point co : Labyrinth.listOfBordersInitializer()){
            maze[co.x][co.y] = false;
        }
    }

    /**
     * Change the value of the coordinate in the labyrinth (if it is not a tile)
     * @param co
     * @return
     */
    public void changeValue(Point co){
        //if(co.x!= 0 && co.x != Point.SIZE*2 && co.y != 0 && co.y != Point.SIZE*2 && co.x%2!=co.y%2){
        boolean valueBefore = maze[co.x][co.y];
        maze[co.x][co.y]=!valueBefore;

    }

    /**
     * Get the value of the passing state
     * @param x
     * @param y
     * @return
     */
    public boolean getValue(int x, int y){
        if (new Point(x,y).isValid())
            return  maze[x][y];
        return true;
    }

    /**
     * A*
     */
    public void solve(){
        //TODO
        //return something so that to be able to display the path on the graphical interface (list of points from the path ?)
    }

    /**
     * Initialisation of borders
     * @return
     */
    public static List<Point> listOfBordersInitializer(){
        ArrayList<Point> l = new ArrayList<>();
        for(int i=0; i<= Point.SIZE*2; ++i){
            l.add(new Point(0,i)); //top border
        }
        for(int i=0; i<= Point.SIZE*2; ++i){
            l.add(new Point(Point.SIZE*2,i)); //bottom border
        }
        for(int i=2; i<= Point.SIZE*2; ++i){
            l.add(new Point(i,0)); //left border
        }
        for(int i=0; i< Point.SIZE*2-1; ++i){
            l.add(new Point(i,Point.SIZE*2)); //right border
        }
        return l;
    }
}
