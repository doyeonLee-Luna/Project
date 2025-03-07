package app.phone.dto;

public class SaleDto {
    private int saleId;
    private int custId;
    private int mobileId;
    private int quantity;
    private double totalPrice;
    private String saleDate;

    // 기본 생성자
    public SaleDto() {}

    // Insert 시 사용 (saleId 없이)
    public SaleDto(int custId, int mobileId, int quantity, double totalPrice) {
        this.custId = custId;
        this.mobileId = mobileId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    // 상세 조회 및 수정 시 사용 (saleId 포함)
    public SaleDto(int saleId, int custId, int mobileId, int quantity, double totalPrice, String saleDate) {
        this.saleId = saleId;
        this.custId = custId;
        this.mobileId = mobileId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
    }

    // Getter & Setter
    public int getSaleId() { return saleId; }
    public void setSaleId(int saleId) { this.saleId = saleId; }

    public int getCustId() { return custId; }
    public void setCustId(int custId) { this.custId = custId; }

    public int getMobileId() { return mobileId; }
    public void setMobileId(int mobileId) { this.mobileId = mobileId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public String getSaleDate() { return saleDate; }
    public void setSaleDate(String saleDate) { this.saleDate = saleDate; }
}
