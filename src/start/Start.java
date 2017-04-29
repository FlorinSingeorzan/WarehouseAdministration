package start;


import bll.*;
import presentation.Controller;
import presentation.View;

/**
 * Clasa implementeaza metoda main
 */
public class Start {
/**
 *   Metoda main creaza un obiect View si obiecte de tipul modelului (BLL)
 *   care sunt trimise trimise ca parametri unei noi instante de Controller
 */
    public static void main(String[] args) {
        View actualView=new View();

        try {
            CustomerBLL customerBLL=new CustomerBLL();
            OrderBLL orderBLL=new OrderBLL();
            ProductBLL productBLL=new ProductBLL();
             new Controller(actualView,customerBLL,productBLL,orderBLL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
