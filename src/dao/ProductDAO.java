package dao;


import model.Product;
/**
 * Clasa extinde AbstractDao contine statementuri de modificare si interogare a tabelelei Product
 * Contine insert, delete update si select
 */
public class ProductDAO extends AbstractDAO<Product> {
    
    private static final String insertStatement = "INSERT INTO product (id_product,name,price,description,quantity)"
            + " VALUES (?,?,?,?,?)";
    private final static String findStatement = "SELECT * FROM product where id_product = ?";
    private final static String deleteString = "DELETE FROM product where id_product = ?";
    private final static String updateString = "UPDATE product set name=?,price=?,description=?,quantity=? where id_product = ?";




    public ProductDAO() throws ClassNotFoundException {
    }


    public  int insert(Product product){
        return super.insert(product,insertStatement);
    }

    public Product findById(int id){
        return super.findById(findStatement,id);
    }

    public int delete(int id) { return super.delete(deleteString,id);
    }
    public int update(Product product){
        return super.update(product,updateString);
    }

}
