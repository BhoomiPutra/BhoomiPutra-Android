package farmer_dto;

import java.io.Serializable;
import java.util.ArrayList;

public class Farmer implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String name;
	long mobileno;
	Address addr;
	String password;
	
	double totalLand;
	ArrayList<Cattles> cattles=new ArrayList<Cattles>();
	public Farmer(String name,String pass, long mobileno2) {
		super();
	
		this.name = name;
		this.mobileno = mobileno2;
		password=pass;
		
	}
	
	public Farmer(int id, String name,long mobileno, Address addr,
			String password,ArrayList<Cattles> cattles) {
		super();
		this.id = id;
		this.name = name;
		this.mobileno = mobileno;
		this.addr = addr;
		this.password = password;
		
		this.cattles=cattles;
	}

	
	public Farmer() {
		// TODO Auto-generated constructor stub
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public double getTotalLand() {
		return totalLand;
	}

	public void setTotalLand(double totalLand) {
		this.totalLand = totalLand;
	}

	public ArrayList<Cattles> getCattles() {
		return cattles;
	}

	public void setCatles(ArrayList<Cattles> cattles) {
		this.cattles = cattles;
	}

	public long getMobileno() {
		return mobileno;
	}
	public void setMobileno(long
			
			mobileno) {
		this.mobileno = mobileno;
	}
	public Address getAddr() {
		return addr;
	}
	public void setAddr(Address addr) {
		this.addr = addr;
	}
	
	
	public void setCattle(Cattles cattle)
	{
		this.cattles.add(cattle);
	}

	@Override
	public String toString() {
		return "Farmer [id=" + id + ", name=" + name + ", mobileno=" + mobileno
				+ ", addr=" + addr + ", password=" + password + ", cattles=" + cattles + "]";
	}
	
	

}
