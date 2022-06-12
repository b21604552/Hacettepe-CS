import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

public class city {
	public static String file;
	public static boolean found = false;
	public String name;
	public int cityID;
	public LinkedList<Integer> airway;
	public LinkedList<Integer> highway;
	public LinkedList<Integer> railway;
	public city(String string, int id) {
		this.cityID = id;
		this.name = string;
		this.airway = new LinkedList<Integer>();
		this.highway = new LinkedList<Integer>();
		this.railway = new LinkedList<Integer>();
	}
	public city() {
		// TODO Auto-generated constructor stub
	}
	void Q1(city source, int destination, int number,String type, LinkedList<city> graph) throws IOException {
        boolean visited[] = new boolean[graph.size()];
        LinkedList<LinkedList<path>> paths = new LinkedList<LinkedList<path>>();
        LinkedList<path> path = new LinkedList<path>();
        Q1Util(source,source.cityID,destination,number,type,graph, visited, path, paths);
        printToFile("\n");
        if(found == false) {
        	printToFile("There is no way!");
        	printToFile("\n");
        }else
        	found = false;
    } 	
	void Q1Util(city source,int realSource, int destination,int number,String type, LinkedList<city> graph,boolean visited[],LinkedList<path> path,LinkedList<LinkedList<path>> paths) throws IOException {
	        visited[source.cityID] = true; 
	        if(source.cityID == destination) {
	        	found = true;
	        	visited[source.cityID] = false; 
	        	int counter = 0;
	        	for(path temp: path) {
	        		if(temp.type.contains(type)) {
	            		counter++;
	            	}
	        	}
	            if(counter >= number) {
	            	printToFile("\n");
	            	printToFile(graph.get(realSource).name);
	            	for(path temp: path) {
	            		printToFile( ", " + temp.type + ", " + graph.get(temp.cityID).name);
	            	}
	            }
	        }
	        LinkedList<path> pathss = new LinkedList<path>();
	        Iterator<Integer> i = source.airway.listIterator();
	        while (i.hasNext()) { 
	        	int n = i.next(); 
	        	path newpath = new path(n,"A");
	        	pathss.add(newpath);
	        }
	        i = source.railway.listIterator();
	        while (i.hasNext()) { 
	        	int n = i.next(); 
	        	path newpath = new path(n,"R");
	        	pathss.add(newpath);
	        }
	        i = source.highway.listIterator();
	        while (i.hasNext()) { 
	        	int n = i.next(); 
	        	path newpath = new path(n,"H");
	        	pathss.add(newpath);
	        }
	        Iterator<path> f = pathss.listIterator();
	        while (f.hasNext()) 
	        { 
	            path n = f.next();
	            path.add(n);
	            if (!visited[n.cityID]) {
	            	Q1Util(graph.get(n.cityID),realSource, destination,number,type,graph, visited, path,paths);
	            	visited[n.cityID]=false;
	            }
	        	path.removeLast();
	        }
		}
	void Q2(city source, int destination, int inter, LinkedList<city> graph) throws IOException {
        boolean visited[] = new boolean[graph.size()];
        LinkedList<LinkedList<path>> paths = new LinkedList<LinkedList<path>>();
        LinkedList<path> path = new LinkedList<path>();
        Q2Util(source,source.cityID,destination,inter,graph, visited, path, paths);
        printToFile("\n");
        if(found == false) {
        	printToFile("There is no way!");
        	printToFile("\n");
        }else
        	found = false;
    }
	void Q2Util(city source,int realSource, int destination,int inter, LinkedList<city> graph,boolean visited[],LinkedList<path> path,LinkedList<LinkedList<path>> paths) throws IOException {
        visited[source.cityID] = true; 
        if(source.cityID == destination) {
        	found = true;
        	visited[source.cityID] = false; 
        	boolean control = false;
        	for(path temp: path) {
        		if(temp.cityID == inter) {
            		control = true;
            	}
        	}
            if(control == true) {
            	printToFile("\n");
            	printToFile(graph.get(realSource).name);
            	for(path temp: path) {
            		printToFile(", " + temp.type + ", " + graph.get(temp.cityID).name);
            	}
            }
        }
        LinkedList<path> pathss = new LinkedList<path>();
        Iterator<Integer> i = source.airway.listIterator();
        while (i.hasNext()) { 
        	int n = i.next(); 
        	path newpath = new path(n,"A");
        	pathss.add(newpath);
        }
        i = source.railway.listIterator();
        while (i.hasNext()) { 
        	int n = i.next(); 
        	path newpath = new path(n,"R");
        	pathss.add(newpath);
        }
        i = source.highway.listIterator();
        while (i.hasNext()) { 
        	int n = i.next(); 
        	path newpath = new path(n,"H");
        	pathss.add(newpath);
        }
        Iterator<path> f = pathss.listIterator();
        while (f.hasNext()) 
        { 
            path n = f.next();
            path.add(n);
            if (!visited[n.cityID]) {
            	Q2Util(graph.get(n.cityID),realSource, destination,inter,graph, visited, path,paths);
            	visited[n.cityID]=false;
            }
        	path.removeLast();
        }
	} 
	void Q3(city source, int destination, LinkedList<city> graph, int type) throws IOException{ 
		boolean visited[] = new boolean[graph.size()]; 
	    LinkedList<String> path = new LinkedList<String>();
	    Q3Util(source,destination,graph, visited, path, type); 
	    printToFile("\n");
	    if(found == false) {
	      	printToFile("There is no way!");
	       	printToFile("\n");
	    }else
	       	found = false;
	}
	void Q3Util(city source, int destination, LinkedList<city> graph,boolean visited[],LinkedList<String> path, int type) throws IOException { 
        visited[source.cityID] = true; 
        path.add(source.name);
        if(source.cityID == destination) {
        	found = true;
        	printToFile("\n");
        	Iterator<String> f = path.listIterator();
        	while (f.hasNext()) 
	        { 
	            String n = f.next(); 
	            printToFile(n);
	            if(f.hasNext()) {
		            switch(type) {
			        case 0:
			        	printToFile(", A, ");
			        	break;
			        case 1:
			        	printToFile(", R, ");
			        	break;
			        case 2:
			        	printToFile(", H, ");
			        	break;
			        }    
	            }
	        } 
        }
        Iterator<Integer> i = source.airway.listIterator();
        switch(type) {
        case 0:
        	i = source.airway.listIterator();
        	break;
        case 1:
        	i = source.railway.listIterator();
        	break;
        case 2:
        	i = source.highway.listIterator();
        	break;
        }         
        while (i.hasNext()) 
        { 
            int n = i.next(); 
            if (!visited[n]) 
            	Q3Util(graph.get(n), destination,graph, visited, path,type);
            path.removeLast();
            visited[n]=false;
        }
    }
	void Q4(city source, int destination, int air,int rail,int high, LinkedList<city> graph) throws IOException {
        boolean visited[] = new boolean[graph.size()];
        LinkedList<LinkedList<path>> paths = new LinkedList<LinkedList<path>>();
        LinkedList<path> path = new LinkedList<path>();
        Q4Util(source,source.cityID,destination,air,rail,high,graph, visited, path, paths);
        printToFile("\n");
        if(found == false) {
        	printToFile("There is no way!");
        	printToFile("\n");
        }else
        	found = false;
    } 
	void Q4Util(city source,int realSource, int destination,int air,int rail,int high, LinkedList<city> graph,boolean visited[],LinkedList<path> path,LinkedList<LinkedList<path>> paths) throws IOException {
        visited[source.cityID] = true; 
        if(source.cityID == destination) {
        	found = true;
        	visited[source.cityID] = false; 
        	int airway = 0;
        	int railway = 0;
        	int highway = 0;
        	for(path temp: path) {
        		if(temp.type.contains("A")) {
            		airway++;
            	}else if(temp.type.contains("R")) {
            		railway++;
            	}else if(temp.type.contains("H")) {
            		highway++;
            	}
        	}
            if((airway == air) && (railway == rail) && (highway == high)) {
            	printToFile("\n");
            	printToFile(graph.get(realSource).name);
            	for(path temp: path) {
            		printToFile(", " + temp.type + ", " + graph.get(temp.cityID).name);
            	}
            }
        }
        LinkedList<path> pathss = new LinkedList<path>();
        Iterator<Integer> i = source.airway.listIterator();
        while (i.hasNext()) { 
        	int n = i.next(); 
        	path newpath = new path(n,"A");
        	pathss.add(newpath);
        }
        i = source.railway.listIterator();
        while (i.hasNext()) { 
        	int n = i.next(); 
        	path newpath = new path(n,"R");
        	pathss.add(newpath);
        }
        i = source.highway.listIterator();
        while (i.hasNext()) { 
        	int n = i.next(); 
        	path newpath = new path(n,"H");
        	pathss.add(newpath);
        }
        Iterator<path> f = pathss.listIterator();
        while (f.hasNext()) 
        { 
            path n = f.next();
            path.add(n);
            if (!visited[n.cityID]) {
            	Q4Util(graph.get(n.cityID),realSource, destination,air,rail,high,graph, visited, path,paths);
            	visited[n.cityID]=false;
            }
        	path.removeLast();
        }
	} 
	public static void printToFile(String line) throws IOException {
		FileWriter fw = new FileWriter(file, true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    out.print(line);
	    out.close();
	}
}
