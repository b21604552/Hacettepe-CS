
public interface PizzaInterface {
	
	Pizza addTopping(ToppingDecorator object);

	Pizza addTopping();
	
	String printToppings();
	
	int cost();
	
}
