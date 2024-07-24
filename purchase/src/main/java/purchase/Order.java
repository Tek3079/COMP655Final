package purchase;
public class Order {
    public String id;
    public String customerId;
    public String productId;
    public double amount;
    public long time;

    public void setId(String string) {
        id = string;
    }

    public void setCustomerId(String id2) {
        customerId = id2;
    }

    public void setProductId(String id2) {
        productId = id2;
    }

    public void setAmount(double product) {
        amount = product;
    }

    public void setTimeToNow() {
        time = System.currentTimeMillis();
    }

}
