package purchase;
import java.time.LocalDateTime;

public class Order {
    public String id;
    public long customerId;
    public Long productId;
    public double amount;
    public LocalDateTime time;

    public void setId(String string) {
        id = string;
    }

    public void setCustomerId(long id2) {
        customerId = id2;
    }

    public void setProductId(long id2) {
        productId = id2;
    }

    public void setAmount(double product) {
        amount = product;
    }

    public void setTimeToNow() {
        this.time = LocalDateTime.now();
    }

}
