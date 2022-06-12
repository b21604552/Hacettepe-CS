import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Foods {
	int food_id, calorie_count;
	String food_name;
	public static int counter = 0;

	public static Foods[] load() {
		Foods food[];
		food = new Foods[100];
		try {
			String dataset = System.getProperty("user.dir") + "/food.txt";
			FileReader inFile = new FileReader(dataset);
			BufferedReader inStream = new BufferedReader(inFile);
			String line;
			while (((line = inStream.readLine()) != null)) {
				line = line.replace("﻿", "");
				String[] splited = line.split("\\t");
				if (splited.length == 3) {
					food[counter] = new Foods();
					food[counter].food_id = Integer.parseInt(splited[0]);
					food[counter].food_name = splited[1];
					food[counter].calorie_count = Integer.parseInt(splited[2]);
					counter++;
				}
			}
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return food;
	}
	public static int search(int id, Foods[] food) {
		for (int i=0;i<=counter;i++){
			if(food[i].food_id==id){
				return i;
			}
		}
		return 0;
	}
}
