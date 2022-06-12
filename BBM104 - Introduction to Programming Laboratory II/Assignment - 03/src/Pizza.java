
public class Pizza {
	
	private String toppings;
	private int cost;
	
	public String getToppings() {
		return toppings;
	}
	
	public void setToppings(String toppings) {
		this.toppings = toppings;
	}

	public int cost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}
	public String printToppings(){
		String[] toppings = this.toppings.split(" ", 2);
		return toppings[1];
	}
}
