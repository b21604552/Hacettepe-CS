
public class Minus extends MathJewels {

	@Override
	public String diagonalSearch(Jewels[][] gameGrid, int row, int col,int gridRow, int gridCol) {
		// Do nothing.
		return null;	
	}

	@Override
	public String verticalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		// Do nothing.
		return null;
	}

	@Override
	public String horizontalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		String text = null;
		if ((col-2>=0) && (col-1>=0)){
			if((gameGrid[row][col-2] instanceof MathJewels) && (gameGrid[row][col-1] instanceof MathJewels)){
				return text = (row) + ";" + (col-2) + ";" + (row) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 60;
			}
		}
		if ((col+2<=gridCol) && (col+1<=gridCol)){
			if((gameGrid[row][col+2] instanceof MathJewels) && (gameGrid[row][col+1] instanceof MathJewels)){
				return text = (row) + ";" + (col) + ";" + (row) + ";" + (col+1) + ";" + (row) + ";" + (col+2) + ";" + 60;
			}
		}
		return text;
	}

}
