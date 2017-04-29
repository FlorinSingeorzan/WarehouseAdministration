package model;


public class Order {
    private int id_order;
    private int id_client;
    private int id_product;
    private int quantity;


    public Order(int id_order,int id_client,int id_product,int quantity){
        this.id_order=id_order;
        this.id_client=id_client;
        this.id_product=id_product;
        this.quantity=quantity;
    }

    public int getId_product() {
        return id_product;
    }


    public int getId_client() {
        return id_client;
    }


    public int getId_order() {
        return id_order;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Comanda cu id-ul "+id_order+" plasata de "+id_client+" pentru produsul "+id_product;
    }
}
