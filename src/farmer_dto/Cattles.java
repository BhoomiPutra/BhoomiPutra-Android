package farmer_dto;

import java.io.Serializable;

public class Cattles implements Serializable

{

	String name;	// TODO Auto-generated constructor stub
	 int count;
	public Cattles(String name, int count) {
		super();
		this.name = name;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Animals [name=" + name + ", count=" + count + "]";
	}
	 
}
