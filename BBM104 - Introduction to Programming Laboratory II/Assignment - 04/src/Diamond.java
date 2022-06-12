
public class Diamond extends ShapeJewels {

	@Override
	public String diagonalSearch(Jewels[][] gameGrid, int row, int col,int gridRow, int gridCol) {
		String text = null;
		if ((row-2>=0) && (row-1>=0) && (col-2>=0) && (col-1>=0)){
			if ((gameGrid[row-2][col-2] instanceof Diamond) && (gameGrid[row-1][col-1] instanceof Diamond)){
				return text = (row-2) + ";" + (col-2) + ";" + (row-1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 90;
			}
		}
		if ((row+2<=gridRow) && (row+1<=gridRow) && (col+2<=gridCol) && (col+1<=gridCol)){
			if ((gameGrid[row+2][col+2] instanceof Diamond) && (gameGrid[row+1][col+1] instanceof Diamond)){
				return text = (row+2) + ";" + (col+2) + ";" + (row+1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 90;
			}
		}
		if ((row-2>=0) && (row-1>=0) && (col+2<=gridCol) && (col+1<=gridCol)){
			if ((gameGrid[row-2][col+2] instanceof Diamond) && (gameGrid[row-1][col+1] instanceof Diamond)){
				return text = (row-2) + ";" + (col+2) + ";" + (row-1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 90;
			}
		}
		if ((row+2<=gridRow) && (row+1<=gridRow) && (col-2>=0) && (col-1>=0)){
			if ((gameGrid[row+2][col-2] instanceof Diamond) && (gameGrid[row+1][col-1] instanceof Diamond)){
				return text = (row+2) + ";" + (col-2) + ";" + (row+1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 90;
			}
		}
		return text;	
	}

	@Override
	public String verticalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		// Do nothing.
		return null;
	}

	@Override
	public String horizontalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		// Do nothing.
		return null;
	}

}
