package com.ironsrc.matrixfinder;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;



public class MatrixFinder {

    public List<Pair<Integer,Integer>> findPathToTarget(int[][] arr, Pair startPoint, Integer targetValue){
        List<Pair<Integer,Integer>> solution=new ArrayList<>();
        Pair<Integer,Integer> targetPoint=findTargetPoint(arr,0,0,targetValue);
        solution=findPathByDirections(arr,startPoint,targetPoint,solution);
        return solution;
    }

    private List<Pair<Integer,Integer>> findPathByDirections(int[][] arr, Pair currentPoint, Pair<Integer, Integer> targetPoint, List<Pair<Integer,Integer>> solution) {
        solution.add(currentPoint);
        if(currentPoint.equals(targetPoint)){
            return solution;
        }
        Pair<Integer,Integer> nextPair=findNextPair(arr,currentPoint,targetPoint,solution);
        if(nextPair==null){
            System.out.println("TARGET PAIR NOT EXISTS");
            return null;
        }
        return findPathByDirections(arr,nextPair,targetPoint,solution);
    }


    private Pair<Integer, Integer> findNextPair(int[][] arr, Pair<Integer, Integer> currentPoint, Pair<Integer, Integer> targetPoint, List<Pair<Integer,Integer>> solution) {
        Pair<Integer,Integer> above=new Pair<>(currentPoint.getKey()-1,currentPoint.getValue());
        Pair<Integer,Integer> below=new Pair<>(currentPoint.getKey()+1,currentPoint.getValue());
        Pair<Integer,Integer> right=new Pair<>(currentPoint.getKey(),currentPoint.getValue()+1);
        Pair<Integer,Integer> left=new Pair<>(currentPoint.getKey(),currentPoint.getValue()-1);
        List<Pair<Integer,Integer>> options=addPairsToList(arr,solution,above,below,right,left);
        Integer horizontalDirection=findHorizontalDirection(arr,currentPoint,targetPoint);
        Integer vertialDirection=findVerticalDirection(currentPoint,targetPoint);
        Pair nextPair=findByDirections(arr,solution,options,horizontalDirection,vertialDirection,currentPoint);
        return nextPair;
    }

    private Pair findByDirections(int[][] arr, List<Pair<Integer,Integer>> solution, List<Pair<Integer, Integer>> options, Integer horizontalDirection, Integer vertialDirection, Pair<Integer, Integer> currentPoint) {
        for(Pair<Integer,Integer> targetPair:options){
            if(horizontalDirection==1 && targetPair.getValue()>currentPoint.getValue()){
                return targetPair;
            }else if(horizontalDirection==-1 && targetPair.getValue()<currentPoint.getValue()){
                return targetPair;
            }else if(vertialDirection==1 && targetPair.getKey()>currentPoint.getKey()){
                return targetPair;
            }else if(vertialDirection==-1 && targetPair.getKey()<currentPoint.getKey()){
                return targetPair;
            }
        }
        return options.get(0);
    }

    private List<Pair<Integer, Integer>> addPairsToList(int[][] arr, List<Pair<Integer,Integer>> solution, Pair<Integer, Integer> above, Pair<Integer, Integer> below, Pair<Integer, Integer> right, Pair<Integer, Integer> left) {
        List<Pair<Integer, Integer>> options=new ArrayList<>();
        if(isValidPair(arr,above) && !isVisited(above,solution) && getValue(arr,above)>0){
            options.add(above);
        }
        if(isValidPair(arr,below) && !isVisited(below,solution) && getValue(arr,below)>0){
            options.add(below);
        }
        if(isValidPair(arr,right) && !isVisited(right,solution) && getValue(arr,right)>0){
            options.add(right);
        }
        if(isValidPair(arr,left) && !isVisited(left,solution) && getValue(arr,left)>0){
            options.add(left);
        }
        return options;
    }


    Boolean isValidPair(int[][]arr,Pair<Integer,Integer> pair){
        Integer x=pair.getKey();
        Integer y=pair.getValue();
        if(x<0 || x>=arr.length || y<0 || y>=arr[x].length){
            return false;
        }
        return true;
    }

    Boolean isVisited(Pair<Integer,Integer> targetPair,List<Pair<Integer,Integer>> solution){
        for(Pair current:solution){
            if(current.equals(targetPair)){
                return true;
            }
        }
        return false;
    }
    private Integer findHorizontalDirection(int[][] arr, Pair<Integer,Integer> startPoint,Pair<Integer,Integer> targetPoint) {
        if(targetPoint.getValue()>startPoint.getValue()){
            return 1;
        }
        if(targetPoint.getValue()<startPoint.getValue()){
            return -1;
        }
        return 0;
    }

    private Integer findVerticalDirection(Pair<Integer,Integer> startPoint,Pair<Integer,Integer> targetPoint) {
        if(targetPoint.getKey()>startPoint.getKey()){
            return 1;
        }
        if(targetPoint.getKey()<startPoint.getKey()){
            return -1;
        }
        return 0;
    }

    public void printTwoDimetionArray(int[][] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            for(int j = 0; j < arr[i].length; j++)
            {
                System.out.printf("%5d ", arr[i][j]);
            }
            System.out.println();
        }
    }


    private Pair<Integer, Integer> findTargetPoint(int[][] arr,Integer x,Integer y,Integer targetValue) {
        if(arr[x][y]==targetValue){
            System.out.println("TARGET=["+x+","+y+"]\n");
            return new Pair<Integer,Integer>(x,y);
        }
        if(x==arr.length-1 && y==arr[x].length){ // we are in the last point and did not found the target
            return null;
        }
        if(x==arr.length-1 || y<arr[x].length-1){ // we are in the last line OR we can still go right
            return findTargetPoint(arr,x,y+1,targetValue);
        }
        return findTargetPoint(arr,x+1,0,targetValue); // we must go down to next line
    }

    private Integer getValue(int[][]arr, Pair<Integer,Integer> point){
        return arr[point.getKey()][point.getValue()];
    }


    public static void main(String[] args) {
        MatrixFinder s=new MatrixFinder();
        int[][] arr= {
                {1,1,0,0},
                {0,1,0,0},
                {0,1,1,2},
                {0,1,0,0}
        };
        s.printTwoDimetionArray(arr);
        Pair<Integer,Integer> startPoint = new Pair<>(0,0);
        System.out.println("START POINT=["+startPoint.getKey()+","+startPoint.getValue()+"]");
        List<Pair<Integer,Integer>> solution=s.findPathToTarget(arr,startPoint,2);
        System.out.println(solution);
    }

}
