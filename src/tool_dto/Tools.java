package tool_dto;

import java.io.Serializable;

public class Tools implements Serializable
{
	String name;
	double toolrate;
	int count;
	public Tools(String name, double toolrate, int count) {
		super();
		this.name = name;
		this.toolrate = toolrate;
		this.count = count;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getToolrate() {
		return toolrate;
	}
	public void setToolrate(double toolrate) {
		this.toolrate = toolrate;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "Tools [name=" + name + ", toolrate=" + toolrate + ", count="
				+ count + "]";
	}
 
	
	
	
	
}
