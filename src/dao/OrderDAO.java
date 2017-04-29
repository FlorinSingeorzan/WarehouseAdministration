package dao;


import model.Order;
/**
 * Clasa extinde AbstractDao contine statementuri de modificare si interogare a tabelelei Order
 * Contine insert, delete update si select
 */
 public class OrderDAO extends AbstractDAO<Order> {
    private static final String updateStatement= "INSERT INTO `order` (id_order,id_client,id_product,quantity)"
            + " VALUES (?,?,?,?)";
    private final static String findStatement = "SELECT * FROM `order` where id_order = ?";
    private final static String deleteString = "DELETE FROM `order` where id_order = ?";
    private final static String updateString = "UPDATE `order` set id_client=?,id_product=?,quantity=? where id_order = ?";

     public OrderDAO() throws ClassNotFoundException {
     }


     public  int place(Order order){
        return super.insert(order,updateStatement);
    }


    public Order findById(int id){
        return super.findById(findStatement,id);
    }

    public int delete(int id) { return super.delete(deleteString,id);
    }

    public int update(Order order){
       return super.update(order,updateString);
    }

}
