
import java.util.ArrayList;

public class Order {
	private int Id,customerId,cost,drink;
	private ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
	
	public Order(int Id){
		this.Id = Id;
		this.cost = 0;
		this.drink = 0;
	}
	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public int getCost() {
		this.cost=this.drink;
		for (Pizza pizza : pizzas) {
			this.cost += pizza.cost();
		}
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getDrink() {
		return drink;
	}

	public void setDrink() {
		this.drink++;
	}

	public ArrayList<Pizza> getPizzas() {
		return pizzas;
	}

	public void setPizza(Pizza pizza) {
		this.pizzas.add(pizza);
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}
