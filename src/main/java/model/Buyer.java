package model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "buyers")
@DynamicUpdate
public class Buyer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "buyer_id", nullable = false)
    private int id;

    @Column(name = "buyer_name", nullable = false)
    private String name;

    @Column(name = "balance", nullable = false)
    private int balance;

    @OneToMany(mappedBy = "buyer")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Set<Product> products;

    public Buyer() {
    }

    public Buyer(int id, String name, int balance) {
        this.id = id;
        this.name = name;
        this.balance = balance;
    }

    public Buyer(int id, String name, int balance, Set<Product> products) {
        this.id = id;
        this.name = name;
        this.balance = balance;
        this.products = products;
    }
    
    

    public Buyer(String name, int balance) {
        this.name = name;
        this.balance = balance;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Buyer{" + "id=" + id + ", name=" + name + ", balance=" + balance + ", products=" + products + '}';
    }

}
