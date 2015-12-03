package vendor_dto;

import java.io.Serializable;

public class VendorSeller implements Serializable
{
String productType;
String itemType;
Double price;

public VendorSeller(String productType, String itemType, Double price) {
	super();
	this.productType = productType;
	this.itemType = itemType;
	this.price = price;
	
}
public String getProductType() {
	return productType;
}
public void setProductType(String productType) {
	this.productType = productType;
}
public String getItemType() {
	return itemType;
}
public void setItemType(String itemType) {
	this.itemType = itemType;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}



@Override
public String toString() {
	return "VendorSeller [productType=" + productType + ", itemType="
			+ itemType + ", price=" + price + "]";
}


	
}
