package skiingRouteFinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RouteFinder {
    public static int[][] sampleData = new int[][]{{4, 8, 7, 3},{2, 5, 9, 3},{6, 3, 2, 5},{4, 4, 1, 6}};

    public static void main(String[] args){
    	SkiingMap skiingMap = new SkiingMap();
    	RouteFinder finder = new RouteFinder(skiingMap.getSkiingMap());   
        System.out.println("Best overall: " + Arrays.toString(finder.find()));
    }

    private int[][] matrix;
    private RouteFinderHelper[][] bestPathFromMemory;

    public int[][] createTwoDimensionalArray(){
    	return null;
    }
    
    public RouteFinder(int[][] aMatrix){
        bestPathFromMemory = new RouteFinderHelper[aMatrix.length][];
        matrix = new int[aMatrix.length][];

        for(int i = 0; i < aMatrix.length; i++){
            bestPathFromMemory[i] = new RouteFinderHelper[aMatrix[i].length];
            matrix[i] = new int[aMatrix[i].length];

            for(int j = 0; j < aMatrix[i].length; j++)
            {
                matrix[i][j] = aMatrix[i][j];
            }
        }
    }

    // find best path by finding the best starting cell
    public int[] find(){
        int currentBestStartingCellColumn = 0;
        int currentBestStartingCellRow = 0;
        for(int i = 0; i < matrix.length; i++)
        {
            for(int j = 0; j < matrix[i].length; j++)
            {
               if(getBestPathFromCell(i, j).compareTo(getBestPathFromCell(currentBestStartingCellColumn, currentBestStartingCellRow)) == 1){
                   currentBestStartingCellColumn = i;
                   currentBestStartingCellRow = j;
               }
            }
        }

        return unfoldBestPathFromCell(currentBestStartingCellColumn, currentBestStartingCellRow);
    }

    // find route from a starting cell
    private int[] unfoldBestPathFromCell(int colNum, int rowNum){
        RouteFinderHelper currentCellInformation = getBestPathFromCell(colNum, rowNum);
        int[] path = new int[currentCellInformation.length];
        path[0] = matrix[colNum][rowNum];
        int idx = 1;

        while(currentCellInformation.length > 1)
        {
            path[idx] = matrix[currentCellInformation.nextCellColumn][currentCellInformation.nextCellRow];
            idx++;
            currentCellInformation = getBestPathFromCell(currentCellInformation.nextCellColumn, currentCellInformation.nextCellRow);
        }

        return path;
    }

    // get the information for the best path (starting) from a cell: from memory if available or calculate otherwise
    private RouteFinderHelper getBestPathFromCell(int colNum, int rowNum){
        if(bestPathFromMemory[colNum][rowNum] == null)
        {
            bestPathFromMemory[colNum][rowNum] = calculateInformationForBestPathFromCell(colNum, rowNum);
        }
        return bestPathFromMemory[colNum][rowNum];
    }

    // calculate the information for the best path (starting) from a cell by using the information for best paths from neighboring cells
    private RouteFinderHelper calculateInformationForBestPathFromCell(int colNum, int rowNum){
        List<RouteFinderHelper> possiblePathsFromCell = new ArrayList<RouteFinderHelper>();
        if(colNum != 0 && matrix[colNum - 1][rowNum] < matrix[colNum][rowNum])
        {
            RouteFinderHelper p = getBestPathFromCell(colNum - 1, rowNum);
            possiblePathsFromCell.add(new RouteFinderHelper(p.length + 1, matrix[colNum][rowNum], p.endValue, colNum - 1, rowNum));
        }

        if(colNum != matrix.length - 1 && matrix[colNum + 1][rowNum] < matrix[colNum][rowNum])
        {
            RouteFinderHelper p = getBestPathFromCell(colNum + 1, rowNum);
            possiblePathsFromCell.add(new RouteFinderHelper(p.length + 1, matrix[colNum][rowNum], p.endValue, colNum + 1, rowNum));
        }

        if(rowNum != 0 && matrix[colNum][rowNum - 1] < matrix[colNum][rowNum])
        {
            RouteFinderHelper p = getBestPathFromCell(colNum, rowNum - 1);
            possiblePathsFromCell.add(new RouteFinderHelper(p.length + 1, matrix[colNum][rowNum], p.endValue, colNum, rowNum - 1));
        }

        if(rowNum != matrix[colNum].length -1 && matrix[colNum][rowNum + 1] < matrix[colNum][rowNum])
        {
            RouteFinderHelper p = getBestPathFromCell(colNum, rowNum + 1);
            possiblePathsFromCell.add(new RouteFinderHelper(p.length + 1, matrix[colNum][rowNum], p.endValue, colNum, rowNum + 1));
        }

        if(possiblePathsFromCell.isEmpty())
        {
            return new RouteFinderHelper(1, matrix[colNum][rowNum], matrix[colNum][rowNum], -1, -1);   
        }

        return Collections.max(possiblePathsFromCell);
    }
}