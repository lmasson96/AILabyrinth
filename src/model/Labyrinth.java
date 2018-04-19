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
            for(int j=0; j<=Point.SIZE*2; ++j)
                maze[i][j] = true;
        }

        for(Point co : Labyrinth.listOfBordersInitializer()){
            maze[co.x][co.y] = false;
        }
    }

    /**
     * Change the value of the coordinate in the labyrinth
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
    public List<Point> solve(List<Point> pathSoFar, int distanceSoFar){
        Point currentPoint = pathSoFar.get(pathSoFar.size()-1);
        Point bestPoint = null;
        int minValue = Integer.MAX_VALUE;
        for(Point candidate : neighbours(currentPoint)) {
            if(!pathSoFar.contains(candidate) && canGoTo(currentPoint, candidate)){
                int h = heuristic(candidate);
                if (h==0){  //We arrived at the exit
                    pathSoFar.add(candidate);
                    return pathSoFar;
                }
                if(h < minValue){
                    minValue = h;
                    bestPoint = candidate;
                }
            }
        }
        if(bestPoint!=null){
            pathSoFar.add(bestPoint);
            return solve(pathSoFar, distanceSoFar+1);
        } else {    //no solution
            return null;
        }
    }

    /**
     * Say wether the border between currentPoint and candidate is off or on. currentPoint and candidate must be 2 tiles next to each other
     * @param currentPoint
     * @param candidate
     * @return true if there is no border between the 2 points
     */
    private boolean canGoTo(Point currentPoint, Point candidate) {
        boolean result = maze[(currentPoint.x+candidate.x)/2][(currentPoint.y+candidate.y)/2];
        System.out.println("can go from ("+currentPoint.x+", "+currentPoint.y+") to ("+candidate.x+", "+candidate.y+") = "+result);
        return result;
    }

    /**
     * Heuristic function
     * @param currentPoint
     * @return estimated cost remaining to go from currentpoint to exit
     */
    public int heuristic(Point currentPoint){
        //Point exitPoint = new Point(Point.SIZE*2-1, Point.SIZE*2-1);
        return ((Point.SIZE*2-1-currentPoint.y) + (Point.SIZE*2-1-currentPoint.x))/2;
    }

    public List<Point> neighbours(Point p){
        List<Point> list = new ArrayList<>();
        Point left = new Point(p.x, p.y-2);
        if(left.isTile()) list.add(left);
        Point right = new Point(p.x, p.y+2);
        if(right.isTile()) list.add(right);
        Point up = new Point(p.x-2, p.y);
        if(up.isTile()) list.add(up);
        Point down = new Point(p.x+2, p.y);
        if(down.isTile()) list.add(down);

        return list;
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
