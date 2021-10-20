package model;

import java.util.Base64;
import javax.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "products")
@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private int id;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "product_color", nullable = false)
    private String color;

    @Column(name = "product_price", nullable = false)
    private double price;

    @Lob
    @Column(name = "product_image", nullable = false, columnDefinition = "blob")
    private byte[] image;

    @Transient
    private String base64Image;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private Buyer buyer;

    public Product() {
    }

    public Product(String name, String color, double price, byte[] image) {
        this.name = name;
        this.color = color;
        this.price = price;
        this.image = image;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getBase64Image() {
        base64Image = Base64.getEncoder().encodeToString(this.image);
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", color=" + color + ", price=" + price + ", buyer=" + buyer + '}';
    }

}
