import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		Grid grid = new Grid();
		Jewels[][] gameGrid = grid.getGameGrid();
		int x = (grid.rowCounter - 1), y = (grid.colCounter - 1);
		int totalPoint = 0;
		String inputString = null;
		do {
			System.out.print("Select coordinate or enter E to end the game: ");
			inputString = input.nextLine();
			System.out.println();
			String[] cor = inputString.split(" ");
			if ((!inputString.equals("E")) && (!inputString.equals("e"))) {
				String result = null;
				try{
					Jewels nw = gameGrid[Integer.parseInt(cor[0])][Integer.parseInt(cor[1])];
					if (nw instanceof RightDiv) {
						result = nw.diagonalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
					} else if (nw instanceof LeftDiv) {
						result = nw.diagonalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
					} else if (nw instanceof VerticalOp) {
						result = nw.verticalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x,y);
					} else if (nw instanceof Minus) {
						result = nw.horizontalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
					} else if (nw instanceof Plus) {
						result = nw.horizontalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
						if (result == null) {
							result = nw.verticalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
						}
					} else if (nw instanceof Diamond) {
						result = nw.diagonalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
					} else if (nw instanceof Square) {
						result = nw.horizontalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
					} else if (nw instanceof Triangle) {
						result = nw.verticalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
					} else if (nw instanceof WildCard) {
						result = nw.verticalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
						if (result == null) {
							result = nw.horizontalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
							if (result == null) {
								result = nw.diagonalSearch(gameGrid, Integer.parseInt(cor[0]), Integer.parseInt(cor[1]), x, y);
							}
						}
					}
					if (result == null) {
						System.out.println("There is no match.");
						System.out.println();
					} else {
						String[] results = result.split(";");
						grid.removing(gameGrid, result);
						System.out.println();
						totalPoint += Integer.parseInt(results[results.length - 1]);
						System.out.println("Score: " + results[results.length - 1] + " points.");
					}
				}catch (Exception e){
					System.out.println("Wrong input.");
					System.out.println();
				}finally{
					
				}
			}
		} while ((!inputString.equals("E")) && (!inputString.equals("e")));
		System.out.println("Total score: " + totalPoint + " points.");
		System.out.println();
		System.out.print("Enter name: ");
		inputString = input.nextLine();
		ArrayList<Players> leaderboard = playersLoad();
		leaderboard(leaderboard, inputString, totalPoint);
		System.out.println();
		System.out.println("Good bye!");
		input.close();
	}

	public static ArrayList<Players> playersLoad() throws IOException {
		ArrayList<Players> leaderboard = new ArrayList<Players>();
		String line;
		String dataset = System.getProperty("user.dir") + "/leaderboard.txt";
		FileReader inFile = new FileReader(dataset);
		BufferedReader inStream = new BufferedReader(inFile);
		while (((line = inStream.readLine()) != null)) {
			String[] commands = line.split(" ");
			leaderboard.add(new Players(commands[0], Integer.parseInt(commands[1])));
		}
		inStream.close();
		return leaderboard;
	}

	public static void leaderboard(ArrayList<Players> leaderboard, String inputString, int totalPoint) throws Exception {
		Players player = new Players(inputString, totalPoint);
		Players oldPlayer = null;
		int control = -1, index = 0;
		for (Players pla : leaderboard) {
			if (pla.name.equals(player.name)) {
				if(pla.point<player.point){
					control = 0;
					index = leaderboard.indexOf(pla);
				}else{
					control = 1;
					oldPlayer = pla;
				}
			}
		}
		if (control == 0) {
			leaderboard.set(index, player);
		} else if (control < 0) {
			leaderboard.add(player);
		}
		Collections.sort(leaderboard);
		if (control == 1){
			index = Collections.binarySearch(leaderboard, oldPlayer);
			totalPoint = oldPlayer.point;
		}else{
			index = Collections.binarySearch(leaderboard, player);
		}
		if (leaderboard.size() == 1) {
			System.out.println("Your rank is 1/1, your score is " + totalPoint + " points.");
		} else {
			if (index == 0) {
				if (totalPoint - leaderboard.get(index + 1).point == 0) {
					System.out.println("Your rank is 1/" + leaderboard.size() + ", your score is equals with "
							+ leaderboard.get(index + 1).name);
				} else {
					System.out.println("Your rank is 1/" + leaderboard.size() + ", your score is "
							+ (totalPoint - leaderboard.get(index + 1).point) + " points higher than "
							+ leaderboard.get(index + 1).name);
				}
			} else if (index == (leaderboard.size() - 1)) {
				if (leaderboard.get(index - 1).point - totalPoint == 0) {
					System.out.println("Your rank is " + leaderboard.size() + "/" + leaderboard.size()
							+ ", your score is equals with " + leaderboard.get(index - 1).name);
				} else {
					System.out.println("Your rank is " + leaderboard.size() + "/" + leaderboard.size()
							+ ", your score is " + (leaderboard.get(index - 1).point - totalPoint)
							+ " points lower than " + leaderboard.get(index - 1).name);
				}
			} else {
				if ((totalPoint - leaderboard.get(index + 1).point == 0)
						&& (totalPoint - leaderboard.get(index - 1).point != 0)) {
					System.out.println("Your rank is " + (index + 1) + "/" + leaderboard.size()
							+ ", your score is equals with " + leaderboard.get(index + 1).name + " and "
							+ (leaderboard.get(index - 1).point - totalPoint) + " points lower than "
							+ leaderboard.get(index - 1).name);
				} else if ((totalPoint - leaderboard.get(index + 1).point != 0)
						&& (totalPoint - leaderboard.get(index - 1).point == 0)) {
					System.out.println("Your rank is " + (index + 1) + "/" + leaderboard.size() + ", your score is "
							+ (totalPoint - leaderboard.get(index + 1).point) + " points higher than "
							+ leaderboard.get(index + 1).name + " and equals with " + leaderboard.get(index - 1).name);
				} else if ((totalPoint - leaderboard.get(index + 1).point != 0)
						&& (totalPoint - leaderboard.get(index - 1).point != 0)) {
					System.out.println("Your rank is " + (index + 1) + "/" + leaderboard.size() + ", your score is "
							+ (totalPoint - leaderboard.get(index + 1).point) + " points higher than "
							+ leaderboard.get(index + 1).name + " and "
							+ (leaderboard.get(index - 1).point - totalPoint) + " points lower than "
							+ leaderboard.get(index - 1).name);
				} else {
					System.out.println(
							"Your rank is " + (index + 1) + "/" + leaderboard.size() + ", your score is equals with "
									+ leaderboard.get(index + 1).name + " and " + leaderboard.get(index - 1).name);
				}
			}
		}
		FileWriter fw = new FileWriter(System.getProperty("user.dir") + "/leaderboard.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);
		for (Players pla: leaderboard){
			out.println(pla.name + " " + pla.point);
		}
		out.close();
	}
}