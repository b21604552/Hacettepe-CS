
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
	
	List<Customer> customers;
	public CustomerDaoImpl() throws IOException{
		customers = new ArrayList<Customer>();
		String dataset = System.getProperty("user.dir") + "/customer.txt";
		FileReader inFile = new FileReader(dataset);
		BufferedReader inStream = new BufferedReader(inFile);
		String line;
		while (((line = inStream.readLine()) != null)) {
			line = line.replace("﻿", "");
			String[] address = line.split(":");
			String[] info = address[0].split("\\s+");
			Boolean found = this.search(Integer.parseInt(info[0]));
			if (found == false){
				/*If there is a customer with same id an customer, this control point catch it.*/
				Customer newcustomer = new Customer(Integer.parseInt(info[0]),info[1].trim(),info[2].trim(),info[3].trim(),address[1].trim());
				customers.add(newcustomer);
			}
		}
		inStream.close();
		sortByName();
	   }

	@Override
	public String deleteByID(int id) {
		int customerId=0;
		for (Customer customer : customers) {
	         if (id == customer.getID()){
	        	 customers.remove(customerId);
	        	 sortByName();
	        	 return("Customer "+customer.getID()+" "+customer.getName()+" removed");
	         }else{
	        	 customerId++;
	         }
	    }
		return null;
	}

	@Override
	public String add(String line) {
		String[] info = line.split("\\s+",4);
		String[] address = info[3].split(" ",2);
		if ((info.length<4)||(address.length<2)){
			return "There is some missing field.";
		}else{
			Customer newCustomer = new Customer(Integer.parseInt(info[0]),info[1],info[2],address[0],address[1]);
			customers.add(newCustomer);
			sortByName();
			return ("Customer " + info[0] + " " + info[1] + " added");
		}
	}

	@Override
	public Customer getById(int id) {
		for (Customer customer : customers) {
	         if (id == customer.getID()){
	        	 return customer;
	         }
		}
		return null;
	}
	
	@Override
	public List<Customer> getCustomers() {
		return customers;
	}

	@Override
	public void sortByName() {
		customers.sort(Comparator.comparing(Customer::getName));
	}

	@Override
	public void sortById() {
		customers.sort(Comparator.comparing(Customer::getID));
	}

	@Override
	public Boolean search(int id) {
		for (Customer customer : customers) {
	         if (id == customer.getID()){
	        	 return true;
	         }
		}
		return false;
	}

}
