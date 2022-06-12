
public class AmericanPan implements PizzaInterface{
	
	public AmericanPan(){
		ToppingDecorator.toppings = "";
		ToppingDecorator.cost = 0;
	}

	@Override
	public Pizza addTopping(ToppingDecorator object) {
		Pizza new_pizza = new Pizza();
		ToppingDecorator.toppings ="AmericanPan " + ToppingDecorator.toppings;
		new_pizza.setToppings(ToppingDecorator.toppings);
		ToppingDecorator.cost += 5;
		new_pizza.setCost(ToppingDecorator.cost);
		return new_pizza;
	}
	
	@Override
	public Pizza addTopping() {
		Pizza new_pizza = new Pizza();
		ToppingDecorator.toppings ="AmericanPan ";
		new_pizza.setToppings(ToppingDecorator.toppings);
		ToppingDecorator.cost += 5;
		new_pizza.setCost(ToppingDecorator.cost);
		return new_pizza;
	}

	@Override
	public String printToppings() {
		String[] toppings = ToppingDecorator.toppings.split(" ", 2);
		return toppings[1];
	}

	@Override
	public int cost() {
		return ToppingDecorator.cost;
	}
}
