package model;


public class Product {
    private int id_product;
    private String name;
    private int price;
    private String description;
    private int quantity;

    public Product(int id_product,String name,int price,String description,int quantity){
        this.id_product=id_product;
        this.name=name;
        this.description=description;
        this.quantity=quantity;
        this.price=price;
    }

    public String getName() {
        return name;
    }

    public int getId_product() {
        return id_product;
    }

    public String getDescription() {
        return description;
    }


    public int getQuantity() {
        return quantity;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
