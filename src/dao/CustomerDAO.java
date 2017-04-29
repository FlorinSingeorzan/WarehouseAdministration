package dao;


import model.Customer;

/**
 * Clasa extinde AbstractDao contine statementuri de modificare si interogare a tabelelei Customer
 * Contine insert, delete update si select
 */
public class CustomerDAO extends AbstractDAO<Customer> {



    private static final String insertStatementString = "INSERT INTO customer (id,name,address,phone,email)"
            + " VALUES (?,?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM customer where id = ?";
    private final static String deleteString = "DELETE FROM customer where id = ?";
    private final static String updateString = "UPDATE customer set name=?,address=?,phone=?,email=? where id = ?";

    public CustomerDAO() throws ClassNotFoundException {
    }

    public  int insert(Customer customer){
       return super.insert(customer,insertStatementString);
    }
    public Customer findById(int id){
        return super.findById(findStatementString,id);
    }
    public int delete(int id) { return super.delete(deleteString,id);}
    public int update(Customer customer){return super.update(customer,updateString);}

}
