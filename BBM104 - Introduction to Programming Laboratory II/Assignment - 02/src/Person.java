import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class Person {
	public int person_id, person_weight, person_height, person_age, calorie_need, taken_calorie, burned_calorie;
	public String person_name, person_gender;
	public static int counter = 0;

	public static Person[] load() {
		Person person[];
		person = new Person[50];
		try {
			String dataset = System.getProperty("user.dir") + "/people.txt";
			FileReader inFile = new FileReader(dataset);
			BufferedReader inStream = new BufferedReader(inFile);
			String line;
			while ((line = inStream.readLine()) != null) {
				line = line.replace("﻿", "");
				String[] splited = line.split("\\t");
				if (splited.length == 6) {
					person[counter] = new Person();
					person[counter].person_id = Integer.parseInt(splited[0]);
					person[counter].person_name = splited[1];
					person[counter].person_gender = splited[2];
					person[counter].person_weight = Integer.parseInt(splited[3]);
					person[counter].person_height = Integer.parseInt(splited[4]);
					person[counter].person_age = (2018 - Integer.parseInt(splited[5]));
					person[counter].taken_calorie = 0;
					person[counter].burned_calorie = 0;
					if (splited[2].equals("male")) {
						person[counter].calorie_need = (int) Math.round(66 + (13.75 * person[counter].person_weight)
								+ (5 * person[counter].person_height) - (6.8 * person[counter].person_age));
					} else {
						person[counter].calorie_need = (int) Math.round(665 + (9.6 * Integer.parseInt(splited[3]))
								+ (1.7 * Integer.parseInt(splited[4])) - (4.7 * person[counter].person_age));
					}
					counter++;
				}
			}
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return person;
	}
	public static int search(int id, Person[] person) {
		for (int i=0;i<=counter;i++){
			if(person[i].person_id==id){
				return i;
			}
		}
		return 0;
	}
}
