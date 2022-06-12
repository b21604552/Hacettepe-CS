
public interface JewelsInterface {

	String diagonalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol);
	
	String verticalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol);
	
	String horizontalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol);
}
