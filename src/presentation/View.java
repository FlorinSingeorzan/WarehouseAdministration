package presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private static CardLayout container;               //obiectele ce urmeaza a fi folosite in fereastra grafica
    private static JPanel winPanel ;
    private static JPanel menuPanel;
    private static JPanel operationPanel;
    private static JPanel blankPanel;
    private JScrollPane panelCustomer;
    private JScrollPane panelOrder;
    private JScrollPane panelProduct;
    private int showPanel=0;
    private JButton toCustomer;
    private JButton toProduct;
    private DefaultTableModel customerModel;
    private DefaultTableModel productModel;
    private DefaultTableModel orderModel;
    private JButton toOrder;
    private JTable tabOrder;
    private JTable tabCustomer;
    private JTable tabProduct;
    private String [] headCustomers={"Id","Name","Address","Phone","Email"};
    private String [] headProduct={"Id","Name","Price","Description","Quantity"};
    private String[] headOrder={"Id Command","Id Client","Id Product","Quantity"};
    private JButton insertButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton orderButton;
    private JButton printOrder;
    private JTextField[] data;
    private JLabel[] info;


    /**
     * Toate containarele sunt initializate
     */
    private void init(){
        initButtons();
        initPanelsContent();
        initDataAndInfo();
    }

    /**
     * Sunt create obictele JButton si este setat un text cu atributia lui fiecarui buton
     */
    private void initButtons(){
        toCustomer=new JButton("Customer Table");
        toOrder=new JButton("Order Table");
        toProduct=new JButton("Product Table");
        orderButton=new JButton("Order");
        insertButton=new JButton("Insert");
        deleteButton=new JButton("Delete");
        updateButton=new JButton("Update");
        printOrder=new JButton("Print Order");
    }

    /**
     * Sunt create obiecte pentru cele doua tabloruri si sunt setate etichete aferente fiecarui container text
     */
    private void initDataAndInfo(){
        data=new JTextField[6];
        info=new JLabel[6];
        info[0]=new JLabel("Id:");
        info[1]=new JLabel("Field 2:");
        info[2]=new JLabel("Field 3:");
        info[3]=new JLabel("Field 4:");
        info[4]=new JLabel("Field 5:");
        info[5]=new JLabel("Id:");
        data[0]=new JTextField();
        data[1]=new JTextField();
        data[2]=new JTextField();
        data[3]=new JTextField();
        data[4]=new JTextField();
        data[5]=new JTextField();

    }

    /**
     * Se creaza noi obiecte pentru fiecare panou.
     * Tabelele sunt setate pe un JScrollText si le sunt atribuite cate un model fiecaruia
     * Metoda isCellEditable returneaza false si transmite faptul ca in tabel datele nu pot fi introduse direct in celulr
     */
    private void initPanelsContent(){
        container=new CardLayout();
        winPanel=new JPanel();
        menuPanel=new JPanel();
        operationPanel=new JPanel();
        blankPanel=new JPanel();
        tabCustomer=new JTable()
        {
            public boolean isCellEditable(int row,int columns){return false;}
        };
        tabProduct=new JTable()
        {
            public boolean isCellEditable(int row,int columns){return false;}
        };
        tabOrder=new JTable()
        {
            public boolean isCellEditable(int row,int columns) {return false;}
        };
        panelCustomer=new JScrollPane(tabCustomer);
        panelOrder=new JScrollPane(tabOrder);
        panelProduct=new JScrollPane(tabProduct);
        customerModel=new DefaultTableModel();
        productModel=new DefaultTableModel();
        orderModel=new DefaultTableModel();
        tabCustomer.setModel(customerModel);
        customerModel.setColumnIdentifiers(headCustomers);
        tabProduct.setModel(productModel);
        productModel.setColumnIdentifiers(headProduct);
        tabOrder.setModel(orderModel);
        orderModel.setColumnIdentifiers(headOrder);
    }

    /**
     * Constuctorul apeleaza cele doua metode private
     */
    public View() {
        init();
        setFrame();
    }

    /**
     * Rolul metodei este acela de a seta proprietatile ferestrei
     * Panourile sunt adaugate in fereastra si sunt apelate alte metode private din interiorul clasei
     */

    private void setFrame() {           //seteaza caracteristicele ferestrei
        this.setSize(900, 700);
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle("WareHouse Management");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(winPanel);
        this.add(menuPanel);
        this.add(operationPanel);
        constructPanels();
        linkPanels();
        addButtons();
        tables();
        addDataAndInfo();
        setPermissions();

    }

    /**
     * Layout pentru panourile menuPanel si operationPanel este setat la null, culoarea la WHITE si dimensiunile la
     * coordonatele alese
     */
    private void constructPanels(){
        menuPanel.setLayout(null);
        operationPanel.setLayout(null);
        menuPanel.setBackground(Color.WHITE);
        operationPanel.setBackground(Color.WHITE);
        blankPanel.setBackground(Color.WHITE);
        menuPanel.setBounds(0,0,900,150);
        operationPanel.setBounds(600,150,300,550);
        blankPanel.setBounds(0,150,900,550);
    }

    /**
     * Fiecarui panou (continut de winPanel) ii este setat o constrangere de nume
     * blankPanel este panoul ce este afisat initial la rulare
     */
    private void linkPanels(){
        winPanel.setLayout(container);
        winPanel.setBounds(0,150,600,550);
        winPanel.add(blankPanel,"4");
        winPanel.add(panelCustomer,"1");
        winPanel.add(panelProduct,"2");
        winPanel.add(panelOrder,"3");
        container.show(winPanel,"0");

    }

    /**
     * Fiecarui buton ii sunt setate dimensiunile spatiale si este adaugat panoului din care face parte
     */
    private void addButtons(){
        menuPanel.setLayout(null);
        toCustomer.setBounds(160,85,150,30);
        toProduct.setBounds(360,85,150,30);
        toOrder.setBounds(560,85,150,30);
        insertButton.setBounds(20,400,80,25);
        deleteButton.setBounds(110,400,80,25);
        updateButton.setBounds(200,400,80,25);
        orderButton.setBounds(110,450,80,25);
        printOrder.setBounds(20,485,120,20);
        menuPanel.add(toCustomer);
        menuPanel.add(toOrder);
        menuPanel.add(toProduct);
        operationPanel.add(insertButton);
        operationPanel.add(deleteButton);
        operationPanel.add(updateButton);
        operationPanel.add(orderButton);
        operationPanel.add(printOrder);
    }

    /**
     * Se seteaza dimensiunile spatiale pentru tablourilor de etichete si text
     */
    private void addDataAndInfo(){
        int i,xPos=140,yPos=30;

        for(i=0;i<5;i++){
            data[i].setBounds(xPos,yPos,150,25);
            yPos+=50;
        }
        xPos=10;
        yPos=30;
        for(i=0;i<5;i++){
            info[i].setBounds(xPos,yPos,150,25);
            yPos+=50;
        }

        info[5].setBounds(170,485,20,25);
        data[5].setBounds(200,485,40,25);
        for(i=0;i<6;i++){
            operationPanel.add(data[i]);
            operationPanel.add(info[i]);
        }
    }

    /**
     * Pentru fiecare tablou inaltimea unei coloane este setata la 25 si culoarea de fundal la alb
     */
    private void tables(){
        tabCustomer.setRowHeight(25);
        tabOrder.setRowHeight(25);
        tabProduct.setRowHeight(25);
        tabOrder.setBackground(Color.WHITE);
        tabProduct.setBackground(Color.WHITE);
        tabCustomer.setBackground(Color.WHITE);
    }

    /**
     * Seteaza toate containarele la disable
     * Este folosita pentru panoul blank
     */
    private void setPermissions(){
         int i;
         if(showPanel==0){
             orderButton.setEnabled(false);
             insertButton.setEnabled(false);
             deleteButton.setEnabled(false);
             updateButton.setEnabled(false);
             printOrder.setEnabled(false);
             for(i=0;i<6;i++){
                 data[i].setEnabled(false);
             }
         }
    }

    /**
     * Folosit pentru panoul de Customer si cel de Product
     * Butonul de Order si printOrder sunt setate drept inaccesibile pentru intereactionare
     */
    void permissionLevelOne(){
        int i;
        orderButton.setEnabled(false);
        insertButton.setEnabled(true);
        deleteButton.setEnabled(true);
        updateButton.setEnabled(true);
        printOrder.setEnabled(false);
        for(i=0;i<5;i++){
            data[i].setEnabled(true);
        }
        data[5].setEnabled(false);
    }

    /**
     * Folosit de tabelul Order
     */
    void permissionLevelTwo(){
        int i;
        orderButton.setEnabled(true);
        insertButton.setEnabled(false);
        deleteButton.setEnabled(true);
        updateButton.setEnabled(true);
        printOrder.setEnabled(true);
        for(i=0;i<4;i++){
            data[i].setEnabled(true);
        }
        data[4].setEnabled(false);
        data[5].setEnabled(true);
    }
    int getShowPanel() {
        return showPanel;
    }

    /**
     * Se modifica tabloul in functie de cel dorit pentru vizualizare
     * @param showPanel panoul de afisat
     */
    void setShowPanel(int showPanel) {
        this.showPanel = showPanel;
        container.show(winPanel,""+showPanel);
    }
     String getData(int i){

        return data[i].getText();
    }


    DefaultTableModel getCustomerModel() {
        return customerModel;
    }
     DefaultTableModel getOrderModel() {
        return orderModel;
    }

     DefaultTableModel getProductModel() {
        return productModel;
    }

    void attachToCs(ActionListener actionListener){
        toCustomer.addActionListener(actionListener);
    }
    void attachToPr(ActionListener actionListener){
        toProduct.addActionListener(actionListener);
    }
    void attachToOr(ActionListener actionListener){
        toOrder.addActionListener(actionListener);
    }
    void attachToInsert(ActionListener actionListener){
        insertButton.addActionListener(actionListener);
    }
    void attachToDelete(ActionListener actionListener){
        deleteButton.addActionListener(actionListener);
    }
    void attachToOrder(ActionListener actionListener){
        orderButton.addActionListener(actionListener);
    }
    void attachToUpdate(ActionListener actionListener){
        updateButton.addActionListener(actionListener);
    }
    void attachToPrint(ActionListener actionListener){printOrder.addActionListener(actionListener);}

}