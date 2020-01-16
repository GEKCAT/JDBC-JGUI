package idv.peter.publishers;

public class Publisher {
	private String publisher_id;
	private String publisher_name;
	private String phone;
	private String contact;
	
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Publisher() {
		// TODO Auto-generated constructor stub
	}

	public Publisher(String publisher_id, String publisher_name, String phone, String contact) {
		super();
		this.publisher_id = publisher_id;
		this.publisher_name = publisher_name;
		this.phone = phone;
		this.contact = contact;
	}

	@Override
	public String toString() {
		String all = String.format("%s, %s, %s ", this.publisher_id, this.publisher_name, this.phone);
		return all;
	}
	
	
	public String getPublisher_id() {
		return publisher_id;
	}

	public void setPublisher_id(String publisher_id) {
		this.publisher_id = publisher_id;
	}

	public String getPublisher_name() {
		return publisher_name;
	}

	public void setPublisher_name(String publisher_name) {
		this.publisher_name = publisher_name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
	
}
