package tool_dto;

import java.io.Serializable;
import java.util.ArrayList;

public class ToolsProvider implements Serializable
{
	int id;
	String name;
	long mobileno;
	ToolAddress addr;
	String password;
	
	ArrayList<Tools> tools=new ArrayList<Tools>();
	ArrayList<Manpower>manPowerList=new ArrayList<Manpower>();		
	public ToolsProvider(String name,String pass, long mobileno) {
		super();
	
		this.name = name;
		this.mobileno = mobileno;
		password=pass;
		
	}
	
	

	public ToolsProvider(int id, String name, long mobileno, ToolAddress addr,
			String password, ArrayList<Tools> tools,
			ArrayList<Manpower> manPowerList) {
		super();
		this.id = id;
		this.name = name;
		this.mobileno = mobileno;
		this.addr = addr;
		this.password = password;
		this.tools = tools;
		this.manPowerList = manPowerList;
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
	public long getMobileno() {
		return mobileno;
	}
	public void setMobileno(long mobileno) {
		this.mobileno = mobileno;
	}
	public ToolAddress getAddr() {
		return addr;
	}
	public void setAddr(ToolAddress addr) {
		this.addr = addr;
	}

	
	public void setTools(Tools tool)
	{
		tools.add(tool);
	}
	public void setListTools(ArrayList<Tools> tool)
	{
		tools=tool;
	}
	public void getTools(ArrayList<Tools> tools)
	{
		this.tools=tools;
	}
	
	
	public void setManpowers(Manpower manpower)
	{
		manPowerList.add(manpower);
	}
	
	
	public void setListManpowers(ArrayList<Manpower> manpower)
	{
		manPowerList=manpower;
	}
	public ArrayList<Manpower> getManpowers()
	{
		
		return this.manPowerList;
	}



	@Override
	public String toString() {
		return "ToolsProvider [id=" + id + ", name=" + name + ", mobileno="
				+ mobileno + ", addr=" + addr + ", password=" + password
				+ ", tools=" + tools + ", manPowerList=" + manPowerList
				+ "]";
	}
}