
import java.util.List;

public interface CustomerDao {

			Customer getById(int id);
	
			String deleteByID(int id);

            String add(String line);

            List<Customer> getCustomers();
            
            void sortByName();

            void sortById();
            
            Boolean search(int id);
}
