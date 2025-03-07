package app.phone.dto;

public class MobileDto {
	private int mobileId;
	private String brand;
	private String model;
	private Double price;
	private int stock;
	
	public MobileDto() {}
	
	public MobileDto(int mobileId, String brand, String model, Double price, int stock) {
		super();
		this.mobileId = mobileId;
		this.brand = brand;
		this.model = model;
		this.price = price;
		this.stock = stock;
	}
	
	public int getMobileId() {
		return mobileId;
	}
	public void setMobileId(int mobileId) {
		this.mobileId = mobileId;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	@Override
	public String toString() {
		return "MobileDto [mobileId=" + mobileId + ", brand=" + brand + ", model=" + model + ", price=" + price
				+ ", stock=" + stock + "]";
	}
	
	
}
