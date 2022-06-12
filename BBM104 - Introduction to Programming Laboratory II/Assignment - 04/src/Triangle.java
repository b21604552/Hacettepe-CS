
public class Triangle extends ShapeJewels {

	@Override
	public String diagonalSearch(Jewels[][] gameGrid, int row, int col,int gridRow, int gridCol) {
		return null;
	}

	@Override
	public String verticalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		String text = null;
		if ((row-2>=0) && (row-1>=0)){
			if((gameGrid[row-1][col] instanceof Triangle) && (gameGrid[row-2][col] instanceof Triangle)){
				return text = (row-2) + ";" + (col) + ";" + (row-1) + ";" + (col) + ";" + (row) + ";" + (col) + ";" + 45;
			}
		}
		if ((row+2<=gridRow) && (row+1<=gridRow)){
			if((gameGrid[row+1][col] instanceof Triangle) && (gameGrid[row+2][col] instanceof Triangle)){
				return text = (row) + ";" + (col) + ";" + (row+1) + ";" + (col) + ";" + (row+2) + ";" + (col) + ";" + 45;
			}
		}
		return text;
	}

	@Override
	public String horizontalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		// Do nothing.
		return null;
	}

}
