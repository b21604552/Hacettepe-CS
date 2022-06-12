import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
	public static int counter = 0, used_counter = 0;

	public static void main(String[] args) throws NumberFormatException, IOException {
		Person[] person = Person.load();
		Foods[] food = Foods.load();
		Sport[] sport = Sport.load();
		String dataset = System.getProperty("user.dir") + "/" + args[0];
		int[] used = new int[100];
		String found = "False";
		FileReader inFile = new FileReader(dataset);
		BufferedReader inStream = new BufferedReader(inFile);
		FileWriter fileWriter = new FileWriter(System.getProperty("user.dir") + "/monitoring.txt");
		PrintWriter printWriter = new PrintWriter(fileWriter);
		String line;
		int counter = 0;
		while ((line = inStream.readLine()) != null) {
			line = line.replace("﻿", "");
			String[] splited = line.split("\\t");
			if (counter > 0) {
				printWriter.println("***************");
			}
			counter = 1;
			if (splited.length != 1) {
				if ((Integer.parseInt(splited[1]) < 2000) && (Integer.parseInt(splited[1]) > 1000)) {
					int person_id = Person.search(Integer.parseInt(splited[0]), person);
					int food_id = Foods.search(Integer.parseInt(splited[1]), food);
					person[person_id].taken_calorie += (food[food_id].calorie_count * Integer.parseInt(splited[2]));
					printWriter.println(person[person_id].person_id + "\thas\ttaken\t"
							+ (food[food_id].calorie_count * Integer.parseInt(splited[2])) + "kcal\tfrom\t"
							+ food[food_id].food_name);
					for (int i = 0; i <= used_counter; i++) {
						if (used[i] == person[person_id].person_id) {
							found = "True";
							break;
						}
					}
					if (found.equals("False")) {
						used[used_counter] = person[person_id].person_id;
						used_counter++;
					} else {
						found = "False";
					}

				} else {
					int person_id = Person.search(Integer.parseInt(splited[0]), person);
					int sport_id = Sport.search(Integer.parseInt(splited[1]), sport);
					person[person_id].burned_calorie += (sport[sport_id].calorie_burned * Integer.parseInt(splited[2])
							/ 60);
					printWriter.println(person[person_id].person_id + "\thas\tburned\t"
							+ (int) Math.round(sport[sport_id].calorie_burned * Float.parseFloat(splited[2]) / 60)
							+ "kcal\tthanks\tto\t" + sport[sport_id].sport_name);
					for (int i = 0; i <= used_counter; i++) {
						if (used[i] == person[person_id].person_id) {
							found = "True";
							break;
						}
					}
					if (found.equals("False")) {
						used[used_counter] = person[person_id].person_id;
						used_counter++;
					} else {
						found = "False";
					}
				}
			} else if (line.equals("printList")) {
				for (int i = 0; i < used_counter; i++) {
					int object_id = Person.search(used[i], person);
					if ((person[object_id].taken_calorie != 0) || (person[object_id].burned_calorie != 0)) {
						int result = person[object_id].taken_calorie
								- (person[object_id].calorie_need + person[object_id].burned_calorie);
						printWriter.print(person[object_id].person_name + "\t" + person[object_id].person_age + "\t"
								+ person[object_id].calorie_need + "kcal\t" + person[object_id].taken_calorie + "kcal\t"
								+ person[object_id].burned_calorie + "kcal\t");
						printWriter.println(result > 0 ? "+" + result + "kcal" : result + "kcal");
					}
				}
			} else {
				int person_id = Integer.parseInt(line.substring(6, 11));
				int object_id = Person.search(person_id, person);
				int result = person[object_id].taken_calorie
						- (person[object_id].calorie_need + person[object_id].burned_calorie);
				printWriter.print(person[object_id].person_name + "\t" + person[object_id].person_age + "\t"
						+ person[object_id].calorie_need + "kcal\t" + person[object_id].taken_calorie + "kcal\t"
						+ person[object_id].burned_calorie + "kcal\t");
				printWriter.println(result > 0 ? "+" + result + "kcal" : result + "kcal");
			}
		}
		printWriter.close();
		inStream.close();
	}
}
