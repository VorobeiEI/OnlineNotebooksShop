package entity.order;


import java.sql.Timestamp;
import java.util.LinkedList;

/**
 * Created by jacksparrow on 12.09.17.
 */
public class Order {

    private int id;
    private double sum;
    private LinkedList<Integer> prodacts = new LinkedList<>();
    private Status status;
    private int userId;
    private Timestamp date;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public LinkedList<Integer> getProdacts() {
        return prodacts;
    }

    public void addProducts(Integer idProdacts) {
                prodacts.add(idProdacts);
    }

    public void removeProducts(Integer idProdacts) {
        prodacts.remove(idProdacts);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", sum=" + sum +
                ", prodacts=" + prodacts +
                ", status=" + status +
                ", userId=" + userId +
                ", date=" + date +
                '}';
    }
}
