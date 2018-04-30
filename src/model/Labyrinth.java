package model;


import org.omg.CORBA.INTERNAL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Point> solve(){
        Map<Integer,Node> openList = new HashMap<>();
        Map<Integer,Node> closedList = new HashMap<>();

        Node nodeEntry = new Node(-1,0,0);

        openList.put(0, nodeEntry);
        boolean notFound = true;
        int exitIndex = Point.SIZE*Point.SIZE -1;


        while(notFound && !openList.isEmpty()){
            //take best candidate
            int bestValue = Integer.MAX_VALUE;
            int bestIndex = -1;
            for(int n : openList.keySet()){
                if(openList.get(n).getFValue() < bestValue){
                    bestValue = openList.get(n).getFValue();
                    bestIndex = n;
                }
            }
            Node bestNode = openList.remove(bestIndex);
            closedList.put(bestIndex, bestNode);

            //check neighbours
            for(Integer neighbour : getNeighbours(bestIndex)){
                //in open list
                if(openList.containsKey(neighbour)){
                    Node nodeOfNeighbour = openList.get(neighbour);
                    if(neighbour == exitIndex){
                        closedList.put(neighbour, nodeOfNeighbour);
                        notFound = false;
                        break;
                    }
                    if(nodeOfNeighbour.getFValue() > bestNode.getFValue() +1){
                        nodeOfNeighbour.updateParent(bestIndex,bestNode.gValue +1);
                    }
                }
                else if(!closedList.containsKey(neighbour)){  //not in open list yet
                    Node nodeOfNeighbour =new Node (bestIndex,bestNode.gValue+1, neighbour);
                    if(neighbour == exitIndex){
                        closedList.put(neighbour, nodeOfNeighbour);
                        notFound = false;
                        break;
                    }
                    openList.put(neighbour, nodeOfNeighbour);
                }
            }
        }

        if(!closedList.containsKey(exitIndex)){
            //no possible path
            return null;
        }

        List<Point> path = new ArrayList<>();
        path.add(new Point(2*(exitIndex/Point.SIZE)+1, 2*(exitIndex%Point.SIZE)+1));
        int i = exitIndex;
        while(i!=0){
            i=closedList.get(i).parentIndex;
            int row = i/Point.SIZE;
            int column = i % Point.SIZE;
            path.add(new Point(2*row+1, 2*column+1));
        }

        return path;
    }


    private boolean canGoTo(int indexFrom, int indexTo) {
        int x1 =2*(indexFrom/Point.SIZE)+1;
        int y1 = 2*(indexFrom%Point.SIZE)+1;
        int x2 = 2*(indexTo/Point.SIZE)+1;
        int y2 = 2*(indexTo%Point.SIZE)+1;

        boolean result = maze[(x1+x2)/2][(y1+y2)/2];
        return result;
    }


    private List<Integer> getNeighbours(int currentIndex){
        int row = currentIndex/Point.SIZE;
        int column = currentIndex % Point.SIZE;

        List<Integer> result = new ArrayList();
        int neighbour = currentIndex - Point.SIZE;  //top
        if(row>0 && canGoTo(currentIndex, neighbour))
            result.add(neighbour);
        neighbour = currentIndex + Point.SIZE;      //down
        if(row<Point.SIZE-1 && canGoTo(currentIndex, neighbour))
            result.add(neighbour);
        neighbour = currentIndex -1;                //left
        if(column > 0 && canGoTo(currentIndex, neighbour))
            result.add(neighbour);
        neighbour = currentIndex +1;                //right
        if(column<Point.SIZE-1 && canGoTo(currentIndex, neighbour))
            result.add(neighbour);

        return result;

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
