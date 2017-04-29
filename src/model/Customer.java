package model;



public class Customer {
    private int id;
    private String name;
    private String address;
    private int phone;
    private String email;

    public Customer(int id, String name, String address, int phone, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public int getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }


    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

}
