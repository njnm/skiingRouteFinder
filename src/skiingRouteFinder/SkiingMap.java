package skiingRouteFinder;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SkiingMap {

	public final String mapLocation = "c:/map.txt";
	
	public static void main(String[] arStrings) {
		
	}
	
	public int[][] getSkiingMap(){
		BufferedReader br = null;
		String[][] twoDimensionalArray = new String[1000][1000];
		int[][] twoDArray = new int[1000][1000];
		int z=0;
		
		try {
			String sCurrentLine;
			//reading the map into a two dimensional string array
			br = new BufferedReader(new FileReader(mapLocation));
			while ((sCurrentLine = br.readLine()) != null) {
				twoDimensionalArray[z]= sCurrentLine.split("\\s+");
				z++;
			}
			
			//converting the string array to int array
			for(int i=0; i<twoDimensionalArray.length; i++){
				for(int j=0; j<twoDimensionalArray[i].length; j++){
					twoDArray[i][j] = Integer.parseInt(twoDimensionalArray[i][j]);
				}
			}
						
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return twoDArray;
	}
	
}
