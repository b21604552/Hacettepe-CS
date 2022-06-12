
public class WildCard extends ShapeJewels {

	@Override
	public String diagonalSearch(Jewels[][] gameGrid, int row, int col,int gridRow, int gridCol) {
		String text = null;
		if ((row-2>=0) && (row-1>=0) && (col-2>=0) && (col-1>=0)){
			if ((gameGrid[row-2][col-2] instanceof Diamond) && (gameGrid[row-1][col-1] instanceof Diamond)){
				return text = (row-2) + ";" + (col-2) + ";" + (row-1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 70;
			}
			if ((gameGrid[row-2][col-2] instanceof MathJewels) && (gameGrid[row-1][col-1] instanceof LeftDiv)){
				return text = (row-2) + ";" + (col-2) + ";" + (row-1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 50;
			}
			if (((gameGrid[row-2][col-2] instanceof Jewels) && !(gameGrid[row-2][col-2] instanceof Empty)) && (gameGrid[row-1][col-1] instanceof WildCard)){
				String point = "";
				if (gameGrid[row-2][col-2] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row-2][col-2] instanceof Square) || (gameGrid[row-2][col-2] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row-2][col-2] instanceof Diamond){
					point = "50";
				}
				return text = (row-2) + ";" + (col-2) + ";" + (row-1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + point;
			}
			if ((gameGrid[row-2][col-2] instanceof WildCard) && (gameGrid[row-1][col-1] instanceof WildCard)){
				return text = (row-2) + ";" + (col-2) + ";" + (row-1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 30;
			}
		}
		if ((row+2<=gridRow) && (row+1<=gridRow) && (col+2<=gridCol) && (col+1<=gridCol)){
			if ((gameGrid[row+2][col+2] instanceof Diamond) && (gameGrid[row+1][col+1] instanceof Diamond)){
				return text = (row+2) + ";" + (col+2) + ";" + (row+1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 70;
			}
			if ((gameGrid[row+2][col+2] instanceof MathJewels) && (gameGrid[row+1][col+1] instanceof LeftDiv)){
				return text = (row+2) + ";" + (col+2) + ";" + (row+1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 50;
			}
			if (((gameGrid[row+2][col+2] instanceof Jewels) && !(gameGrid[row+2][col+2] instanceof Empty)) && (gameGrid[row+1][col+1] instanceof WildCard)){
				String point = "";
				if (gameGrid[row+2][col+2] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row+2][col+2] instanceof Square) || (gameGrid[row+2][col+2] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row+2][col+2] instanceof Diamond){
					point = "50";
				}
				return text = (row+2) + ";" + (col+2) + ";" + (row+1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + point;
			}
			if ((gameGrid[row+2][col+2] instanceof WildCard) && (gameGrid[row+1][col+1] instanceof WildCard)){
				return text = (row+2) + ";" + (col+2) + ";" + (row+1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 30;
			}
		}
		if ((row-2>=0) && (row-1>=0) && (col+2<=gridCol) && (col+1<=gridCol)){
			if ((gameGrid[row-2][col+2] instanceof Diamond) && (gameGrid[row-1][col+1] instanceof Diamond)){
				return text = (row-2) + ";" + (col+2) + ";" + (row-1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 70;
			}
			if ((gameGrid[row-2][col+2] instanceof MathJewels) && (gameGrid[row-1][col+1] instanceof RightDiv)){
				return text = (row-2) + ";" + (col+2) + ";" + (row-1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 50;
			}
			if (((gameGrid[row-2][col+2] instanceof Jewels) && !(gameGrid[row-2][col+2] instanceof Empty)) && (gameGrid[row-1][col+1] instanceof WildCard)){
				String point = "";
				if (gameGrid[row-2][col+2] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row-2][col+2] instanceof Square) || (gameGrid[row-2][col+2] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row-2][col+2] instanceof Diamond){
					point = "50";
				}
				return text = (row-2) + ";" + (col+2) + ";" + (row-1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + point;
			}
			if ((gameGrid[row-2][col+2] instanceof WildCard) && (gameGrid[row-1][col+1] instanceof WildCard)){
				return text = (row-2) + ";" + (col+2) + ";" + (row-1) + ";" + (col+1) + ";" + (row) + ";" + (col) + ";" + 30;
			}
		}
		if ((row+2<=gridRow) && (row+1<=gridRow) && (col-2>=0) && (col-1>=0)){
			if ((gameGrid[row+2][col-2] instanceof Diamond) && (gameGrid[row+1][col-1] instanceof Diamond)){
				return text = (row+2) + ";" + (col-2) + ";" + (row+1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 70;
			}
			if ((gameGrid[row+2][col-2] instanceof MathJewels) && (gameGrid[row+1][col-1] instanceof RightDiv)){
				return text = (row+2) + ";" + (col-2) + ";" + (row+1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 50;
			}
			if (((gameGrid[row+2][col-2] instanceof Jewels) && !(gameGrid[row+2][col-2] instanceof Empty)) && (gameGrid[row+1][col-1] instanceof WildCard)){
				String point = "";
				if (gameGrid[row+2][col-2] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row+2][col-2] instanceof Square) || (gameGrid[row+2][col-2] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row+2][col-2] instanceof Diamond){
					point = "50";
				}
				return text = (row+2) + ";" + (col-2) + ";" + (row+1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + point;
			}
			if ((gameGrid[row+2][col-2] instanceof WildCard) && (gameGrid[row+1][col-1] instanceof WildCard)){
				return text = (row+2) + ";" + (col-2) + ";" + (row+1) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 30;
			}
		}
		return text;
	}

	@Override
	public String verticalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		String text = null;
		if ((row-2>=0) && (row-1>=0)){
			if((gameGrid[row-1][col] instanceof Triangle) && (gameGrid[row-2][col] instanceof Triangle)){
				return text = (row-2) + ";" + (col) + ";" + (row-1) + ";" + (col) + ";" + (row) + ";" + (col) + ";" + 40;
			}
			if((gameGrid[row-1][col] instanceof Plus) && (gameGrid[row-2][col] instanceof MathJewels)){
				return text = (row-2) + ";" + (col) + ";" + (row-1) + ";" + (col) + ";" + (row) + ";" + (col) + ";" + 50;
			}
			if((gameGrid[row-1][col] instanceof VerticalOp) && (gameGrid[row-2][col] instanceof MathJewels)){
				return text = (row-2) + ";" + (col) + ";" + (row-1) + ";" + (col) + ";" + (row) + ";" + (col) + ";" + 50;
			}
			if((gameGrid[row-1][col] instanceof WildCard) && ((gameGrid[row-2][col] instanceof Jewels) && !(gameGrid[row-2][col] instanceof Empty))){
				String point = "";
				if (gameGrid[row-2][col] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row-2][col] instanceof Square) || (gameGrid[row-2][col] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row-2][col] instanceof Diamond){
					point = "50";
				}
				return text = (row-2) + ";" + (col) + ";" + (row-1) + ";" + (col) + ";" + (row) + ";" + (col) + ";" + point;
			}
			if((gameGrid[row-1][col] instanceof WildCard) && (gameGrid[row-2][col] instanceof WildCard)){
				return text = (row-2) + ";" + (col) + ";" + (row-1) + ";" + (col) + ";" + (row) + ";" + (col) + ";" + 30;
			}
		}
		if ((row+2<=gridRow) && (row+1<=gridRow)){
			if((gameGrid[row+1][col] instanceof Triangle) && (gameGrid[row+2][col] instanceof Triangle)){
				return text = (row) + ";" + (col) + ";" + (row+1) + ";" + (col) + ";" + (row+2) + ";" + (col) + ";" + 40;
			}
			if((gameGrid[row+1][col] instanceof Plus) && (gameGrid[row+2][col] instanceof MathJewels)){
				return text = (row) + ";" + (col) + ";" + (row+1) + ";" + (col) + ";" + (row+2) + ";" + (col) + ";" + 50;
			}
			if((gameGrid[row+1][col] instanceof VerticalOp) && (gameGrid[row+2][col] instanceof MathJewels)){
				return text = (row) + ";" + (col) + ";" + (row+1) + ";" + (col) + ";" + (row+2) + ";" + (col) + ";" + 50;
			}
			if((gameGrid[row+1][col] instanceof WildCard) &&  ((gameGrid[row+2][col] instanceof Jewels) && !(gameGrid[row+2][col] instanceof Empty))){
				String point = "";
				if (gameGrid[row+2][col] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row+2][col] instanceof Square) || (gameGrid[row+2][col] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row+2][col] instanceof Diamond){
					point = "50";
				}
				return text = (row) + ";" + (col) + ";" + (row+1) + ";" + (col) + ";" + (row+2) + ";" + (col) + ";" + point;
			}
			if((gameGrid[row+1][col] instanceof WildCard) && (gameGrid[row+2][col] instanceof WildCard)){
				return text = (row) + ";" + (col) + ";" + (row+1) + ";" + (col) + ";" + (row+2) + ";" + (col) + ";" + 30;
			}
		}
		return text;
	}

	@Override
	public String horizontalSearch(Jewels[][] gameGrid,int row,int col,int gridRow, int gridCol) {
		String text = null;
		if ((col-2>=0) && (col-1>=0)){
			if((gameGrid[row][col-2] instanceof Square) && (gameGrid[row][col-1] instanceof Square)){
				return text = (row) + ";" + (col-2) + ";" + (row) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 40;
			}
			if((gameGrid[row][col-2] instanceof MathJewels) && (gameGrid[row][col-1] instanceof Minus)){
				return text = (row) + ";" + (col-2) + ";" + (row) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 50;
			}
			if(((gameGrid[row][col-2] instanceof Jewels) && !(gameGrid[row][col-1] instanceof Empty)) && (gameGrid[row][col-1] instanceof WildCard)){
				String point = "";
				if (gameGrid[row][col-1] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row][col-1] instanceof Square) || (gameGrid[row][col-1] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row][col-1] instanceof Diamond){
					point = "50";
				}
				return text = (row) + ";" + (col-2) + ";" + (row) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + point;
			}
			if((gameGrid[row][col-2] instanceof WildCard) && (gameGrid[row][col-1] instanceof WildCard)){
				return text = (row) + ";" + (col-2) + ";" + (row) + ";" + (col-1) + ";" + (row) + ";" + (col) + ";" + 30;
			}
		}
		if ((col+2<=gridCol) && (col+1<=gridCol)){
			if((gameGrid[row][col+2] instanceof Square) && (gameGrid[row][col+1] instanceof Square)){
				return text = (row) + ";" + (col) + ";" + (row) + ";" + (col+1) + ";" + (row) + ";" + (col+2) + ";" + 40;
			}
			if((gameGrid[row][col+2] instanceof MathJewels) && (gameGrid[row][col+1] instanceof Minus)){
				return text = (row) + ";" + (col) + ";" + (row) + ";" + (col+1) + ";" + (row) + ";" + (col+2) + ";" + 50;
			}
			if(((gameGrid[row][col+2] instanceof Jewels) && !(gameGrid[row][col+1] instanceof Empty)) && (gameGrid[row][col+1] instanceof WildCard)){
				String point = "";
				if (gameGrid[row][col+2] instanceof MathJewels){
					point = "40";
				}else if ((gameGrid[row][col+2] instanceof Square) || (gameGrid[row][col+2] instanceof Triangle)){
					point = "35";
				}else if (gameGrid[row][col+2] instanceof Diamond){
					point = "50";
				}
				return text = (row) + ";" + (col) + ";" + (row) + ";" + (col+1) + ";" + (row) + ";" + (col+2) + ";" + point;
			}
			if((gameGrid[row][col+2] instanceof WildCard) && (gameGrid[row][col+1] instanceof WildCard)){
				return text = (row) + ";" + (col) + ";" + (row) + ";" + (col+1) + ";" + (row) + ";" + (col+2) + ";" + 30;
			}
		}
		return text;
	}

}
