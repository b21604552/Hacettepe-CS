
public class HotPepper extends ToppingDecorator {

	public HotPepper(PizzaInterface decoratedPizza) {
		toppings = "HotPepper " + toppings;
		cost += 1;
	}

	public HotPepper(){
		toppings = "HotPepper " + toppings;
		cost += 1;
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
