package idv.peter.jframe.add_customer;

public class Customer {
	private String customer_id;
	private String customer_name;
	private String phone;
	private String address;
	
	//Source -> Constructor using Fields 建構子
	//Source -> Getters and Setters
	public Customer() {

	}
	
	public Customer(String customer_id, String customer_name, String phone, String address) {
		setCustomer_id(customer_id);
		setCustomer_name(customer_name);
		setPhone(phone);
		setAddress(address);
	}
	
	
	public void setCustomer_id(String customer_id){
		this.customer_id = customer_id;
	}
	
	
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	
	public String getCustomer_id() {
		return customer_id;
	}
	
	
	public String getCustomer_name() {
		return customer_name;
	}
	
	
	public String getPhone() {
		return phone;
	}
	
	
	public String getAddress() {
		return address;
	}
	
	
	@Override
	public String toString() {
		return "Customer_ID:" + customer_name +
				"\nCustomer_Name:" + customer_name +
				"\nPhone:" + phone +
				"\nAdress:" + address;
	}
	
}
