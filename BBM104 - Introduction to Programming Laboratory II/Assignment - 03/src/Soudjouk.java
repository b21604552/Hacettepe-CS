
public class Soudjouk extends ToppingDecorator{

	public Soudjouk(PizzaInterface decoratedPizza) {
		toppings = "Soudjouk " + toppings;
		cost += 3;
	}
	
	public Soudjouk(){
		toppings = "Soudjouk " + toppings;
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
