import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Grid {

	Jewels[][] gameGrid;
	int rowCounter=0,colCounter=0;
	public Grid() throws IOException{
		String line;
		String dataset = System.getProperty("user.dir") + "/gameGrid.txt";
		FileReader countRowColFile = new FileReader(dataset);
		BufferedReader countRowCol = new BufferedReader(countRowColFile);
		while (((line = countRowCol.readLine()) != null)) {
			String[] row = line.split(" ");
			colCounter=0;
			for (int i=0;i<row.length;i++){
				colCounter++;
			}
			rowCounter++;
		}
		countRowCol.close();
		gameGrid = new Jewels[rowCounter][colCounter];
		rowCounter=0;
		colCounter=0;
		FileReader inFile = new FileReader(dataset);
		BufferedReader inStream = new BufferedReader(inFile);
		while (((line = inStream.readLine()) != null)) {
			String[] row = line.split(" ");
			colCounter=0;
			for (String type : row){
				Jewels kind = null;
				if (type.equals("D")){
					kind = new Diamond();
				}else if (type.equals("S")){
					kind = new Square();
				}else if (type.equals("T")){
					kind = new Triangle();
				}else if (type.equals("W")){
					kind = new WildCard();
				}else if (type.equals("\\")){
					kind = new LeftDiv();
				}else if (type.equals("/")){
					kind = new RightDiv();
				}else if (type.equals("+")){
					kind = new Plus();
				}else if (type.equals("-")){
					kind = new Minus();
				}else if (type.equals("|")){
					kind = new VerticalOp();
				}
				gameGrid[rowCounter][colCounter] = kind;
				colCounter++;
			}
			rowCounter++;
		}
		inStream.close();
		System.out.println("Game grid:");
		System.out.println();
		this.printGrid();
		System.out.println();
	}
	public void printGrid(){
		for (int r=0;r<rowCounter;r++){
			for (int c=0;c<colCounter;c++){
				if(gameGrid[r][c] instanceof Empty){
					System.out.print("  ");
				}else if(gameGrid[r][c] instanceof Plus){
					System.out.print("+ ");
				}else if(gameGrid[r][c] instanceof Minus){
					System.out.print("- ");
				}else if(gameGrid[r][c] instanceof VerticalOp){
					System.out.print("| ");
				}else if(gameGrid[r][c] instanceof LeftDiv){
					System.out.print("\\ ");
				}else if(gameGrid[r][c] instanceof RightDiv){
					System.out.print("/ ");
				}else{
					System.out.print(gameGrid[r][c].toString().substring(0,1));
					System.out.print(" ");
				}
			}
			System.out.println();
		}
	}
	public Jewels[][] getGameGrid(){
		return gameGrid;
	}
	public void removing(Jewels[][] gameGrid, String info){
		String[] cor = info.split(";");
		int row1 = Integer.parseInt(cor[0]), col1 = Integer.parseInt(cor[1]), row2 = Integer.parseInt(cor[2]), col2 = Integer.parseInt(cor[3]), row3 = Integer.parseInt(cor[4])
				, col3 = Integer.parseInt(cor[5]);
		ShapeJewels nw1 = new Empty();
		gameGrid[row1][col1] = nw1;
		this.shift(gameGrid, row1, col1);
		ShapeJewels nw2 = new Empty();
		gameGrid[row2][col2] = nw2;
		this.shift(gameGrid, row2, col2);
		ShapeJewels nw3 = new Empty();
		gameGrid[row3][col3] = nw3;
		this.shift(gameGrid, row3, col3);
		this.printGrid();
	}
	public void shift(Jewels[][] gameGrid,int row,int col){
		
		for (int i=row;i>0;i--){
			Jewels nw = gameGrid[i][col];
			gameGrid[i][col] = gameGrid[i-1][col];
			gameGrid[i-1][col] = nw ;
		}
	}
}
