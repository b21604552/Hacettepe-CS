import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Sport {
	int sport_id, calorie_burned;
	String sport_name;
	public static int counter = 0;

	public static Sport[] load() {
		Sport sports[];
		sports = new Sport[100];
		try {
			String dataset = System.getProperty("user.dir") + "/sport.txt";
			FileReader inFile = new FileReader(dataset);
			BufferedReader inStream = new BufferedReader(inFile);
			String line;
			while ((line = inStream.readLine()) != null) {
				line = line.replace("﻿", "");
				String[] splited = line.split("\\t");
				if (splited.length == 3) {
					sports[counter] = new Sport();
					sports[counter].sport_id = Integer.parseInt(splited[0]);
					sports[counter].sport_name = splited[1];
					sports[counter].calorie_burned = Integer.parseInt(splited[2]);
					counter++;
				}
			}
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sports;
	}
	public static int search(int id, Sport[] sport) {
		for (int i=0;i<=counter;i++){
			if(sport[i].sport_id==id){
				return i;
			}
		}
		return 0;
	}
}
