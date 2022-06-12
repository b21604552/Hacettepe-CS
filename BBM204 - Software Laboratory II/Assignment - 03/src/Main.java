import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Main {
	@SuppressWarnings({ "resource", "static-access" })
	public static void main(String[] args) throws IOException {
		BufferedReader reader;
		reader = new BufferedReader(new FileReader(args[0]));
		FileOutputStream out = new FileOutputStream(args[2]);
        out.close();
		city.file=args[2];
		String line = reader.readLine();
		LinkedList<city> digraph = new LinkedList<city>();
		int counter = 0;
		String type = "Type";
		while (line != null) {
			String[] tokens = line.split(" ");
			if(((tokens[0].contains("Airway")) == true) || ((tokens[0].contains("Highway")) == true) || ((tokens[0].contains("Railway")) == true)) {
				type = tokens[0];
				counter = 0;
			}
			if((type.contains("Airway")) == true) {
				if ((line.length() != 0) && ((line.contains("Airway") != true))) {
					city city = new city(tokens[0],counter);
					char[] chars = tokens[1].toCharArray();
					int i = 0;
					for(char ch: chars) {
						if(ch == '1')
							city.airway.add(i);
						i++;
					}
					digraph.add(city);
					counter++;
				}
			}
			if((type.contains("Railway")) == true) {
				if ((line.length() != 0) && ((line.contains("Railway") != true))) {
					city city = digraph.get(counter);
					char[] chars = tokens[1].toCharArray();
					int i = 0;
					for(char ch: chars) {
						if(ch == '1')
							city.railway.add(i);
						i++;
					}
					counter++;
				}
			}
			if((type.contains("Highway")) == true) {
				if ((line.length() != 0) && ((line.contains("Highway") != true))) {
					city city = digraph.get(counter);
					char[] chars = tokens[1].toCharArray();
					int i = 0;
					for(char ch: chars) {
						if(ch == '1')
							city.highway.add(i);
						i++;
					}
					counter++;
				}
			}
			line = reader.readLine();
		}
		reader.close();
		reader = new BufferedReader(new FileReader(args[1]));
		line = reader.readLine();
		while (line != null) {
			String[] tokens = line.split(" ");
			for(int i = 0; i<tokens.length;i++) {
				if(i+1 != tokens.length)
					city.printToFile(tokens[i]+", ");
				else
					city.printToFile(tokens[i]);
			}
			if(tokens[0].equals("Q1")){
				city source = new city();
				int destination = 0;
				int number = Integer.parseInt(tokens[3]);
				for(city ch: digraph) {
					if(ch.name.contains(tokens[1]))
						source = ch;
					if(ch.name.contains(tokens[2]))
						destination = ch.cityID;
				}
				source.Q1(source,destination,number,tokens[4], digraph);
			}else if(tokens[0].equals("Q2")){
				city source = new city();
				int destination = 0;
				int inter = 0;
				for(city ch: digraph) {
					if(ch.name.contains(tokens[1]))
						source = ch;
					if(ch.name.contains(tokens[2]))
						destination = ch.cityID;
					if(ch.name.contains(tokens[3]))
						inter = ch.cityID;
				}
				source.Q2(source,destination,inter, digraph);
			}else if(tokens[0].equals("Q3")){
				int type1 = 0;
				if(tokens[3].contains("A")) {
					type1 = 0;
				}else if(tokens[3].contains("R")) {
					type1 = 1;
				}else if(tokens[3].contains("H")) {
					type1 = 2;
				}
				city source = new city();
				int destination = 0;
				for(city ch: digraph) {
					if(ch.name.contains(tokens[1]))
						source = ch;
					if(ch.name.contains(tokens[2]))
						destination = ch.cityID;
				}
				source.Q3(source, destination, digraph,type1);
			}else if(tokens[0].equals("Q4")){
				city source = new city();
				int destination = 0;
				for(city ch: digraph) {
					if(ch.name.contains(tokens[1]))
						source = ch;
					if(ch.name.contains(tokens[2]))
						destination = ch.cityID;
				}
				char[] chars = tokens[3].toCharArray();
				int air = Character.getNumericValue(chars[1]);
				chars = tokens[4].toCharArray();
				int high = Character.getNumericValue(chars[1]);
				chars = tokens[5].toCharArray();
				int rail = Character.getNumericValue(chars[1]);
				source.Q4(source,destination,air,rail,high, digraph);
			}else{
				city.printToFile("\n");
				for(city city: digraph){
					LinkedList<Integer> citys = new LinkedList<Integer>();
					for(int temp: city.airway) {
						if(!(citys.contains(temp))) {
							citys.add(temp);
						}
					}
					for(int temp: city.railway) {
						if(!(citys.contains(temp))) {
							citys.add(temp);
						}
					}
					for(int temp: city.highway) {
						if(!(citys.contains(temp))) {
							citys.add(temp);
						}
					}
					city.printToFile(city.name+" --> ");
					for(int temp: citys) {
						city.printToFile(digraph.get(temp).name+" ");
					}
					city.printToFile("\n");
				}
			}
			line = reader.readLine();
		}
	}
}
