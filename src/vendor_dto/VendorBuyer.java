package vendor_dto;

import java.io.Serializable;

public class VendorBuyer implements Serializable{

	
	String cropType;
	
	String cropSubtype;
	double cropPrice;
	public VendorBuyer(String cropType, String cropSubtype,
			double cropPrice) {
		super();
		this.cropType = cropType;
		this.cropSubtype = cropSubtype;
		this.cropPrice = cropPrice;
	}
	public String getCropType() {
		return cropType;
	}
	public void setCropType(String cropType) {
		this.cropType = cropType;
	}
	
	
	public String getCropSubtype() {
		return cropSubtype;
	}
	public void setCropSubtype(String cropSubtype) {
		this.cropSubtype = cropSubtype;
	}
	public double getCropPrice() {
		return cropPrice;
	}
	public void setCropPrice(double cropPrice) {
		this.cropPrice = cropPrice;
	}
	@Override
	public String toString() {
		return "VendorBuyer [cropType=" + cropType + ", cropSubtype=" + cropSubtype + ", cropPrice=" + cropPrice
				+ "]";
	}
	
	
	

	
	
	
}