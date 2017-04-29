package presentation;

import bll.*;
import model.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;

/**
 * Clasa uneste partea de View cu partea de model (BLL)
 */
public class Controller {

    private View view;
    private CustomerBLL customerBLL;
    private OrderBLL orderBLL;
    private ProductBLL productBLL;

    /**
     * Constructorul atribuie obiectele create si ataseaza fiecarui buton din View un ActionListener
     * @param view
     * @param customerBLL
     * @param productBLL
     * @param orderBLL
     */
    public Controller(View view,CustomerBLL customerBLL,ProductBLL productBLL,OrderBLL orderBLL) {
        this.view=view;
        this.orderBLL=orderBLL;
        this.productBLL=productBLL;
        this.customerBLL=customerBLL;
        view.attachToCs(new ToCustomerListener());
        view.attachToOr(new ToOrderListener());
        view.attachToPr(new ToProductListener());
        view.attachToOrder(new OrderListener());
        view.attachToInsert(new InsertListener());
        view.attachToUpdate(new UpdateListener());
        view.attachToDelete(new DeleteListener());
        view.attachToPrint(new PrintListener());
    }

    /**
     * Clasa implemeteaza ActionListener
     */
    public class ToCustomerListener implements ActionListener{
        /**
         * Starea panoului se schimba, permisiunea tabelului customer este setata la 1(one)
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setShowPanel(1);
            view.permissionLevelOne();
        }
    }
    /**
     * Clasa implemeteaza ActionListener
     */
    public class ToProductListener implements ActionListener{
        /**
         * Starea panoului se schimba, permisiunea tabelului customer este setata la 1(one)
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setShowPanel(2);
            view.permissionLevelOne();
        }

    }
    /**
     * Clasa implemeteaza ActionListener
     */
    public class ToOrderListener implements ActionListener{
        /**
         * Starea panoului se schimba, permisiunea tabelului customer este setata la 2(Two)
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setShowPanel(3);
            view.permissionLevelTwo();
        }

    }
    /**
     * Clasa implemeteaza ActionListener
     */
    public class InsertListener implements ActionListener{
        /**
         *Metoda, in functie de tabelul selectat creaza un obiect <T> dupa valorile containarele text
         *Se incearca inserarea in baza de date si daca totul decurge cu succes atunci o noua linie in
         *  tabelul JTable aferent este inserata
        */
        @Override
        public void actionPerformed(ActionEvent e) {
            int success;
            switch (view.getShowPanel()) {
                case 1:
                Customer customer = new Customer(Integer.parseInt(view.getData(0)), view.getData(1), view.getData(2), Integer.parseInt(view.getData(3)), view.getData(4));
                success = customerBLL.insertCustomer(customer);
                if (success == 1) {
                    customerBLL.insertToTable(customer, view.getCustomerModel());
                }
                break;
                case 2:
                    Product product= new Product(Integer.parseInt(view.getData(0)), view.getData(1), Integer.parseInt(view.getData(2)),view.getData(3),Integer.parseInt( view.getData(4)));
                    success = productBLL.insertProduct(product);
                    if (success == 1) {
                        productBLL.insertToTable(product, view.getProductModel());
                    }
                    break;
                default:
            }
        }

    }
    /**
     * Clasa implemeteaza ActionListener
     */
    public class DeleteListener implements ActionListener {
        /**
         * Se executa (in functie de caz) stergerea din baza de date, iar mai apoi prin deleteFromTabel stergerea din tabel
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int toDelete;
            toDelete = Integer.parseInt(view.getData(0));
            switch (view.getShowPanel()) {
                case 1:
                    customerBLL.deleteCustomer(toDelete);
                    customerBLL.deleteFromTable(toDelete, view.getCustomerModel());
                    break;
                case 2:

                    productBLL.deleteProduct(toDelete);
                    productBLL.deleteFromTable(toDelete, view.getProductModel());
                    break;
                case 3:
                    orderBLL.deleteOrder(toDelete);
                    orderBLL.deleteFromTable(toDelete, view.getOrderModel());

                default:
            }

        }
    }  /**
     * Clasa implemeteaza ActionListener
     */
    public class UpdateListener implements ActionListener{
        /**
         * In functie de starea panoului, se preiau datele din JTextTable-uri si se executa updatul dupa id.
         * In cazul succesului din Jtable se cheama metoda de delete iar mai apoi cea de insert
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int success;
            switch (view.getShowPanel()) {
                case 1:
                    Customer customer = new Customer(Integer.parseInt(view.getData(0)), view.getData(1), view.getData(2), Integer.parseInt(view.getData(3)), view.getData(4));
                    success = customerBLL.updateCustomer(customer);
                    customerBLL.deleteFromTable(customer.getId(),view.getCustomerModel());
                    if (success == 1) {
                        customerBLL.insertToTable(customer, view.getCustomerModel());
                    }
                    break;
                case 2:
                    Product product= new Product(Integer.parseInt(view.getData(0)), view.getData(1), Integer.parseInt(view.getData(2)),view.getData(3),Integer.parseInt( view.getData(4)));
                    success = productBLL.updateProduct(product);
                    productBLL.deleteFromTable(product.getId_product(),view.getProductModel());
                    if (success == 1) {
                        productBLL.insertToTable(product, view.getProductModel());
                    }
                    break;
                case 3:
                    Order order= new Order(Integer.parseInt(view.getData(0)),Integer.parseInt(view.getData(1)), Integer.parseInt(view.getData(2)),Integer.parseInt( view.getData(3)));
                    success = orderBLL.updateOrder(order);
                    orderBLL.deleteFromTable(order.getId_order(),view.getOrderModel());
                    if (success == 1) {
                        orderBLL.insertToTable(order, view.getOrderModel());
                    }
                    break;
                default:
            }
        }

    }  /**
     * Clasa implemeteaza ActionListener
     */
    public class OrderListener implements ActionListener{
        /**
         * Asemena inserarii se plaseaza o comanda,mai intai in tabela Order iar mai apoi in JTable
         * @param e
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            int success;
            if(view.getShowPanel()==3) {
                Order order = new Order(Integer.parseInt(view.getData(0)), Integer.parseInt(view.getData(1)), Integer.parseInt(view.getData(2)), Integer.parseInt(view.getData(3)));
                success = orderBLL.placeOrder(order);
                if (success == 1) {
                    orderBLL.insertToTable(order, view.getOrderModel());
                }
            }
        }

    }
    /**
     * Clasa implemeteaza ActionListener
     */
    public class PrintListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Log logOut;
            try {
                logOut = new Log("order.txt");

            logOut.logger.setLevel(Level.INFO);
            logOut.logger.info("Order:");
            logOut.logger.info(orderBLL.toString());


            } catch (IOException e1) {
                System.out.println("Can't print");
                e1.printStackTrace();
            }
        }
    }




}