
public class Salami extends ToppingDecorator{
	
	public Salami(PizzaInterface decoratedPizza) {
		toppings = "Salami " + toppings;
		cost += 3;
	}
	
	public Salami(){
		toppings = "Salami " + toppings;
		cost += 3;
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
