
import java.io.IOException;
import java.util.List;

public interface OrderDao {
	String createOrder(int id,int customerId);
	
	List<String> paycheck(int id) throws IOException;
	
	void removeOrder(int id);
	
	void addPizza(Pizza pizza, int id);
	
	String addDrink(int id);
	
	public List<Order> getOrders() ;

	void sort();
	
	Order getById(int id);
	
	Boolean search(int id);
}
