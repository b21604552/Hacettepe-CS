
public class Customer {
	private int id;
	private String name,surname,phoneNumber,address;
	Customer(int id,String name,String surname,String phoneNumber,String address){
	      this.id = id;
	      this.name=name;
	      this.surname=surname;
	      this.phoneNumber=phoneNumber;
	      this.address=address;
	}
	public int getID() {
	      return this.id;
	   }
	public String getName() {
	      return this.name;
	   }
	public String getSurname() {
		return this.surname;
	}
	public String getPhonenumber() {
		return this.phoneNumber;
	}
	public String getAddress() {
		return this.address;
	}
}
