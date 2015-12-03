package tool_dto;

import java.io.Serializable;

public class Manpower implements Serializable
{

	String manpowercategory;
	double manpowerRate;
	int count;
	public Manpower(String manpowercategory, double manpowerRate, int count) {
		super();
		this.manpowercategory = manpowercategory;
		this.manpowerRate = manpowerRate;
		this.count = count;
	}
	public String getManpowercategory() {
		return manpowercategory;
	}
	public void setManpowercategory(String manpowercategory) {
		this.manpowercategory = manpowercategory;
	}
	public double getManpowerRate() {
		return manpowerRate;
	}
	public void setManpowerRate(double manpowerRate) {
		this.manpowerRate = manpowerRate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Manpower [manpowercategory=" + manpowercategory
				+ ", manpowerRate=" + manpowerRate + ", count=" + count + "]";
	}
	
	
	
}
