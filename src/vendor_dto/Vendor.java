package vendor_dto;

import java.io.Serializable;
import java.util.ArrayList;

import tool_dto.Manpower;
import tool_dto.Tools;

public class Vendor implements Serializable
{
	int id;
	String name;
	long mobileno;
	VendorAddress addr;
	String password;

	
	ArrayList<VendorSeller> listSeller=new ArrayList<VendorSeller>();
	ArrayList<VendorBuyer>listBuyer=new ArrayList<VendorBuyer>();
	public Vendor(int id, String name, long mobileno, VendorAddress addr,
			String password, VendorBuyer buyer, VendorSeller seller,
			ArrayList<VendorSeller> listSeller, ArrayList<VendorBuyer> listBuyer) {
		super();
		this.id = id;
		this.name = name;
		this.mobileno = mobileno;
		this.addr = addr;
		this.password = password;
		
		this.listSeller = listSeller;
		this.listBuyer = listBuyer;
	}
	public Vendor(String name2, String password2, long mobile) {
		
		name=name2;
		password=password2;
		mobileno=mobile;
		
		// TODO Auto-generated constructor stub
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
	public long getMobileno() {
		return mobileno;
	}
	public void setMobileno(long mobileno) {
		this.mobileno = mobileno;
	}
	public VendorAddress getAddr() {
		return addr;
	}
	public void setAddr(VendorAddress addr) {
		this.addr = addr;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setBuyer(VendorBuyer buyer) {
		listBuyer.add(buyer);
	}

	public void setSeller(VendorSeller seller) {
		listSeller.add(seller);
	}
	public ArrayList<VendorSeller> getListSeller() {
		return listSeller;
	}
	public void setListSeller(ArrayList<VendorSeller> listSeller) {
		this.listSeller = listSeller;
	}
	public ArrayList<VendorBuyer> getListBuyer() {
		return listBuyer;
	}
	public void setListBuyer(ArrayList<VendorBuyer> listBuyer) {
		this.listBuyer = listBuyer;
	}
	@Override
	public String toString() {
		return "Vendor [id=" + id + ", name=" + name + ", mobileno=" + mobileno
				+ ", addr=" + addr + ", password=" + password + ", listSeller="
				+ listSeller + ", listBuyer=" + listBuyer + "]";
	}
	
	
	
	
	
}
