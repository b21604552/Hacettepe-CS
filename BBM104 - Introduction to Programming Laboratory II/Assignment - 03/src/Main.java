import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Main {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		CustomerDao customerDao = new CustomerDaoImpl();
		/*It is for make some change according to input on customer information.*/
		OrderDao orderDao = new OrderDaoImpl();
		/*It is for make some change according to input on order information.*/
		FileWriter fw = new FileWriter(System.getProperty("user.dir") + "/output.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);
		String dataset = System.getProperty("user.dir") + "/" + args[0];
		FileReader inFile = new FileReader(dataset);
		BufferedReader inStream = new BufferedReader(inFile);
		String line;
		while (((line = inStream.readLine()) != null)) {
			/*Input file will been proccesed line by line until reach end of file.*/
			String[] command = line.split("\\s+", 2);
			/*The line that is in input file split 2 part for check the mission of line.*/
			if (command[0].equals("AddCustomer") == true) {
				/*If the mission of line is adding customer, this block will proccess.*/
				String[] id = line.split("\\s+");
				Boolean found = customerDao.search(Integer.parseInt(id[1]));
				if (found == false) {
					/*If there is a customer with same id this control point catch it.*/
					if (line.length() >= 3) {
						/*If there are some missing field this control point catch it.*/
						String wline = customerDao.add(command[1]);
						out.println(wline);
					} else {
						out.println("There some missing field which are need.");
					}
				} else {
					out.println("There is a customer with same unique id therefore this customer could not added.");
				}
			} else if (command[0].equals("RemoveCustomer") == true) {
				/*If the mission of line is removing customer, this block will proccess.*/
				Boolean found = customerDao.search(Integer.parseInt(command[1]));
				if (found == true) {
					/*If there is not a customer with given customer id, this control point catch it.*/
					for (Order order : orderDao.getOrders()) {
						/*If the removing customer has a order, this part will remove it.*/
						if (Integer.parseInt(command[1]) == order.getCustomerId()) {
							orderDao.removeOrder(order.getId());
							break;
						}
					}
					String wline = customerDao.deleteByID(Integer.parseInt(command[1]));
					out.println(wline);
				} else {
					out.println("There is no customer with this id." + command[1]);
				}
			} else if (command[0].equals("CreateOrder") == true) {
				String[] Ids = command[1].split("\\s+");
				Boolean found = customerDao.search(Integer.parseInt(Ids[1]));
				Boolean orderfound = orderDao.search(Integer.parseInt(Ids[0]));
				if (found == true) {
					/*If there is not a customer with same id this control point catch it.*/
					if (orderfound == false) {
						/*If there is an order with same id this control point catch it.*/
						String wline = orderDao.createOrder(Integer.parseInt(Ids[0]), Integer.parseInt(Ids[1]));
						out.println(wline);
					} else {
						out.println("There is an order with this id." + Ids[0]);
					}
				} else {
					out.println("There is no customer with this id." + Ids[1]);
				}
			} else if (command[0].equals("RemoveOrder") == true) {
				Boolean found = orderDao.search(Integer.parseInt(command[1]));
				if (found == true) {
					/*If there is not an order with same id this control point catch it.*/
					orderDao.removeOrder(Integer.parseInt(command[1]));
				} else {
					out.println("There is no order with this id." + command[1]);
				}
			} else if (command[0].equals("AddPizza") == true) {
				Pizza new_pizza = new Pizza();
				String[] Ids = command[1].split("\\s+");
				File fk = new File(Ids[1] + ".java");
				if (fk.exists() && !fk.isDirectory()) {
					/*If there is not a base type pizza this control point catch it.*/
					PizzaInterface pizza = (PizzaInterface) Class.forName(Ids[1]).newInstance();
					int numberOfToppings = Ids.length - 2;
					Boolean topping = true;
					if (numberOfToppings != 0) {
						/*It is for check the toppings for they exist or not.*/
						for (int i = 2; i < Ids.length; i++) {
							File f = new File(Ids[i] + ".java");
							if (f.exists() && !f.isDirectory()) {
							} else {
								topping = false;
								break;
							}
						}
					}
					Boolean found = orderDao.search(Integer.parseInt(Ids[0]));
					Boolean add = false;
					if (topping == true) {
						/*If some of toppings are not exist this control point catch it.*/
						if (found == true) {
							/*If there is not a customer with same id this control point catch it.*/
							switch (numberOfToppings) {
							case 1:
								ToppingDecorator toppings = (ToppingDecorator) Class.forName(Ids[2]).newInstance();
								new_pizza = pizza.addTopping(toppings);
								add = true;
								break;
							case 2:
								Constructor<?> constructor = Class.forName(Ids[2]).getConstructor(PizzaInterface.class);
								ToppingDecorator toppings2 = (ToppingDecorator) Class.forName(Ids[3]).newInstance();
								ToppingDecorator toppings1 = (ToppingDecorator) constructor.newInstance(toppings2);
								new_pizza = pizza.addTopping(toppings1);
								add = true;
								break;
							case 3:
								ToppingDecorator toppings3 = (ToppingDecorator) Class.forName(Ids[4]).newInstance();
								Constructor<?> constructor2 = Class.forName(Ids[3]).getConstructor(PizzaInterface.class);
								constructor = Class.forName(Ids[2]).getConstructor(PizzaInterface.class);
								toppings2 = (ToppingDecorator) constructor2.newInstance(toppings3);
								toppings1 = (ToppingDecorator) constructor.newInstance(toppings2);
								new_pizza = pizza.addTopping(toppings1);
								add = true;
								break;
							case 0:
								new_pizza = pizza.addTopping();
								add = true;
								break;
							default:
								out.println("A pizza can include max 3 toppings.");
								add = false;
								break;
							}
							if (add == true) {
								/*If addpizza command not legal this control point catch it.*/
								orderDao.addPizza(new_pizza, Integer.parseInt(Ids[0]));
								out.println(Ids[1] + " pizza added to order " + Ids[0]);
							}
						} else {
							topping = true;
							out.println("There is no order with this id." + Ids[0]);
						}
					} else {
						topping = true;
						out.println("You can not add this toppings.");
					}
				} else {
					out.println("There is no type pizza.");
				}
			} else if (command[0].equals("AddDrink") == true) {
				Boolean found = orderDao.search(Integer.parseInt(command[1]));
				if (found == true) {
					/*If there is not an order with same id this control point catch it.*/
					orderDao.addDrink(Integer.parseInt(command[1]));
					out.println("Drink added to order " + command[1]);
				} else {
					out.println("There is no order with this id." + command[1]);
				}
			} else if (command[0].equals("PayCheck") == true) {
				Boolean found = orderDao.search(Integer.parseInt(command[1]));
				if (found == true) {
					/*If there is not an order with same id this control point catch it.*/
					List<String> wline = orderDao.paycheck(Integer.parseInt(command[1]));
					for (String customer : wline) {
						out.println(customer);
					}
				} else {
					out.println("There is no order with this id." + command[1]);
				}
			} else if ((command[0].equals("List") == true) || (command[0].equals("ListCustomer") == true)) {
				out.println("Customer List:");
				for (Customer customer : customerDao.getCustomers()) {
					out.println(customer.getID() + " " + customer.getName() + " " + customer.getSurname() + " "
							+ customer.getPhonenumber() + " Address: " + customer.getAddress());
				}
			}
		}
		out.close();
		inStream.close();
		/*After all things new customer informations will writing to customer.txt*/
		fw = new FileWriter(System.getProperty("user.dir") + "/customer.txt");
		bw = new BufferedWriter(fw);
		out = new PrintWriter(bw);
		customerDao.sortById();
		for (Customer customer : customerDao.getCustomers()) {
			out.println(customer.getID() + " " + customer.getName() + " " + customer.getSurname() + " "
					+ customer.getPhonenumber() + " Address: " + customer.getAddress());
		}
		out.close();
		/*After all things new order informations will writing to order.txt*/
		fw = new FileWriter(System.getProperty("user.dir") + "/order.txt");
		bw = new BufferedWriter(fw);
		out = new PrintWriter(bw);
		orderDao.sort();
		for (Order order : orderDao.getOrders()) {
			out.println("Order: " + order.getId() + " " + order.getCustomerId());
			for (Pizza pizza : order.getPizzas()) {
				out.println(pizza.getToppings());
			}
			for (int i = 0; i < order.getDrink(); i++)
				out.println("Softdrink");
		}
		out.close();
	}

}