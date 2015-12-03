package tool_dto;

import java.io.Serializable;
import java.util.ArrayList;

public class ToolsProviderAssets implements Serializable
{

	int  manpowerCategry;
	Tools toolsName;
	public ToolsProviderAssets(Manpower manpowerCategory, Tools toolsName) {
		super();
		this.manpowerCategry = manpowerCategry;
		this.toolsName = toolsName;
	}
	
	
	
	public int getManpowerCategry() {
		return manpowerCategry;
	}



	public void setManpowerCategry(int manpowerCategry) {
		this.manpowerCategry = manpowerCategry;
	}



	public Tools getToolsName() {
		return toolsName;
	}



	public void setToolsName(Tools toolsName) {
		this.toolsName = toolsName;
	}



	@Override
	public String toString() {
		return "ToolsProviderAssets [manpowerCategry=" + manpowerCategry
				+ ", toolsName=" + toolsName + "]";
	}




	
	
	
	
	
}
