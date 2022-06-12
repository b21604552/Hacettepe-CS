
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

	private List<Order> orders;
	private int orderId;

	public OrderDaoImpl() throws InstantiationException, IllegalAccessException, ClassNotFoundException,
			NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, IOException {
		orders = new ArrayList<Order>();
		String dataset = System.getProperty("user.dir") + "/order.txt";
		FileReader inFile = new FileReader(dataset);
		BufferedReader inStream = new BufferedReader(inFile);
		String line;
		Boolean order=false;
		while (((line = inStream.readLine()) != null)) {
			String[] command = line.split("\\s", 2);
			if (command[0].equals("Order:") == true) {
				String[] Ids = command[1].split("\\s+");
				order = this.search(Integer.parseInt(Ids[0]));
				if (order == false){
					this.createOrder(Integer.parseInt(Ids[0]), Integer.parseInt(Ids[1]));
					this.setOrderId(Integer.parseInt(Ids[0]));
				}else{
					order = true;
				}
			} else if ((command[0].equals("Softdrink") == true) && (order == false)) {
				this.addDrink(this.getOrderId());
			} else if (order == false){
				Pizza new_pizza = new Pizza();
				String[] Ids = command[1].split("\\s+");
				File fk = new File(command[0] + ".java");
				if (fk.exists() && !fk.isDirectory()) {
					PizzaInterface pizza = (PizzaInterface) Class.forName(command[0]).newInstance();
					int numberOfToppings = Ids.length;
					Boolean topping = true;
					if (command[1].equals("")) {
						numberOfToppings = 0;
					}
					if (numberOfToppings != 0) {
						for (int i = 0; i < Ids.length; i++) {
							File f = new File(Ids[i] + ".java");
							if (f.exists() && !f.isDirectory()) {
								continue;
							} else {
								topping = false;
								break;
							}
						}
					}
					Boolean add = false;
					if (topping == true) {
						switch (numberOfToppings) {
						case 1:
							ToppingDecorator toppings = (ToppingDecorator) Class.forName(Ids[0]).newInstance();
							new_pizza = pizza.addTopping(toppings);
							add = true;
							break;
						case 2:
							Constructor<?> constructor = Class.forName(Ids[0]).getConstructor(PizzaInterface.class);
							ToppingDecorator toppings2 = (ToppingDecorator) Class.forName(Ids[1]).newInstance();
							ToppingDecorator toppings1 = (ToppingDecorator) constructor.newInstance(toppings2);
							new_pizza = pizza.addTopping(toppings1);
							add = true;
							break;
						case 3:
							ToppingDecorator toppings3 = (ToppingDecorator) Class.forName(Ids[2]).newInstance();
							Constructor<?> constructor2 = Class.forName(Ids[1]).getConstructor(PizzaInterface.class);
							constructor = Class.forName(Ids[0]).getConstructor(PizzaInterface.class);
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
							add = false;
							break;
						}
						if (add == true) {
							this.addPizza(new_pizza, this.getOrderId());
						}
					} else {
						topping = true;
					}
				}
			}
		}
		inStream.close();
		sort();
	}

	@Override
	public String createOrder(int id, int customerId) {
		Order newOrder = new Order(id);
		orders.add(newOrder);
		for (Order order : orders) {
			if (id == order.getId()) {
				order.setCustomerId(customerId);
			}
		}
		sort();
		return ("Order " + newOrder.getId() + " created");
	}

	@Override
	public List<String> paycheck(int id) throws IOException {
		List<String> wline = new ArrayList<String>();
		wline.add("PayCheck for order " + id);
		for (Order order : orders) {
			if (id == order.getId()) {
				ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
				pizzas = order.getPizzas();
				for (Pizza pizza : pizzas) {
					wline.add("\t" + pizza.getToppings() + pizza.cost() + "$");
				}
				for (int i = 0; i < order.getDrink(); i++) {
					wline.add("\tSoftDrink 1$");
				}
				int cost = order.getCost();
				wline.add("\tTotal: " + cost + "$");

				break;
			}
		}
		return wline;
	}

	@Override
	public void removeOrder(int id) {
		int orderId = 0;
		for (Order order : orders) {
			if (id == order.getId()) {
				orders.remove(orderId);
				sort();
				break;
			} else {
				orderId++;
			}
		}

	}

	@Override
	public void addPizza(Pizza pizza, int id) {
		for (Order order : orders) {
			if (id == order.getId()) {
				order.setPizza(pizza);
			}
		}
	}

	@Override
	public String addDrink(int id) {
		for (Order order : orders) {
			if (id == order.getId()) {
				order.setDrink();
				return ("Drink added to order " + order.getId());
			}
		}
		return null;
	}

	@Override
	public Order getById(int id) {
		for (Order order : orders) {
			if (id == order.getId()) {
				return order;
			}
		}
		return null;
	}

	@Override
	public List<Order> getOrders() {
		return orders;
	}

	@Override
	public void sort() {
		this.orders.sort(Comparator.comparing(Order::getId));
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	@Override
	public Boolean search(int id) {
		for (Order order : orders) {
			if (id == order.getId()) {
				return true;
			}
		}
		return false;
	}
}
