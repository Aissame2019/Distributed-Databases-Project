package application.models;

public class Client {
	
	private int id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String address;
	private int code_postal;
	private String ville;
	private String email;
	private String phone; 
	private int orderid;
	
	
	
	public Client(){
		
	}

	public Client(String pu,String pp,String pf,String pl,String pa,String pv,String pe,String pph,int pc){
		this.setUsername(pu);
		this.setPassword(pp);
		this.setFirstname(pf);
		this.setLastname(pl);
		this.setAddress(pa);
		this.setCode_postal(pc);
		this.setVille(pv);
		this.setEmail(pe);
		this.setPhone(pph);
	}
	public Client(int id,String pf,String pl,String pa,String pv,String pe,String pph,int pc){
		this.setId(id);
		this.setFirstname(pf);
		this.setLastname(pl);
		this.setAddress(pa);
		this.setCode_postal(pc);
		this.setVille(pv);
		this.setEmail(pe);
		this.setPhone(pph);
	}
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(int code_postal) {
		this.code_postal = code_postal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
}
