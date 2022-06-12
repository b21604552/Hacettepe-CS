
public class Onion extends ToppingDecorator{
	
	public Onion(PizzaInterface decoratedPizza) {
		toppings = "Onion " + toppings;
		cost += 2;
	}
	
	public Onion(){
		toppings = "Onion " + toppings;
		cost += 2;
	}

	@Override
	public Pizza addTopping(ToppingDecorator object) {
		return null;
	}

	@Override
	public Pizza addTopping() {
		return null;
	}

	@Override
	public String printToppings() {
		return null;
	}

	@Override
	public int cost() {
		return 0;
	}
}
