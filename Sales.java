/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package GM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;


/**
 *
 * @author LENOVO
 */
public class Sales extends javax.swing.JFrame {
    
      public void print() throws PrinterException {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(new SalesPrintable());
        if (job.printDialog()) {
            job.print();
        }
      }
      
      public class SalesPrintable implements Printable {
    @Override
    public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        
       
        g.drawString("Sales Report", 100, 100);
        
        // More complex drawing logic can be added here.
        
        return PAGE_EXISTS;
    }
}
    /**
     * Creates new form Sales
     */
    public Sales() {
        
        this.setResizable(false);
        initComponents();
         addTable();
         TableClick();
         fillTable();
         filTable();
       filledTable();
        listTable();
       addTableClickListener();
       
    }
    
    
    String prize = "";
    
 
    private void addTableClickListener() {
        jTable2.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = jTable2.getSelectedRow();
                    if (selectedRow != -1) {
                        String Order = jTable2.getValueAt(selectedRow, 0).toString();
                        String  Proid = jTable2.getValueAt(selectedRow, 1).toString();
                        String Custid = jTable2.getValueAt(selectedRow, 2).toString();
                        String Proquan = jTable2.getValueAt(selectedRow, 3).toString();
                        String Unit  = jTable2.getValueAt(selectedRow, 4).toString();
                        String  Amount  = jTable2.getValueAt(selectedRow, 5).toString(); 


                        orderid.setText(Order);
                        proid.setText(Proid);
                        cusID.setText(Custid);
                        proquan.setText(Proquan);
                        unit.setText(Unit);
                        amount.setText(Amount);

                    }
                }
            }
        });
    }
    
     private void filTable() {

          DefaultTableModel model1 = (DefaultTableModel) jTable1.getModel();
          model1.setRowCount(0); // Clear existing rows
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gms", "root", "root");
             PreparedStatement pstmt = conn.prepareStatement("SELECT Productid, Productname, Quantity, Category, productprize FROM product");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String Proid = rs.getString("Productid");
                String Proname = rs.getString("Productname");
                String Proquan = rs.getString("Quantity");
                String Procate = rs.getString("Category");
                prize = rs.getString("productprize");

                model1.addRow(new Object[]{Proid, Proname, Proquan, Procate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
 
     private void TableClick() {
        jTable3.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = jTable3.getSelectedRow();
                    if (selectedRow != -1) {
                        String Custid = jTable3.getValueAt(selectedRow, 1).toString();
                        

                        // Set values to text fields
                      
                        cusID.setText(Custid);
                       
                    }
                }
            }
        });
    }
     
     
     private void listTable() {
    jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        public void valueChanged(ListSelectionEvent event) {
            if (!event.getValueIsAdjusting()) {
                int selectedRow = jTable1.getSelectedRow();
                if (selectedRow != -1) {
                    String prize2 = prize;
                    
                    try {
                        // Convert string to double
                        double prizeValue = Double.parseDouble(prize2);
                        // Calculate new prize value
                        double prize3 = (prizeValue * 0.1) + prizeValue;
                        // Set the calculated value to the text field as a string
                        unit.setText(String.valueOf(prize3));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        // Handle the exception, perhaps set unit to some error message
                        unit.setText("Invalid number format");
                    }
                }
            }
        }
    });
}
   
    
    
     private void addTable() {
        jTable1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
          
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    int selectedRow = jTable1.getSelectedRow();
                    if (selectedRow != -1) {
                        String Proid = jTable1.getValueAt(selectedRow, 0).toString();
                       

                        // Set values to text fields
                        proid.setText(Proid);
                       
                    }
                }
            }
        });
    }
   
      private void fillTable() {
        DefaultTableModel model1 = (DefaultTableModel) jTable3.getModel();
        model1.setRowCount(0); // Clear existing rows

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gms", "root", "root");
             PreparedStatement pstmt = conn.prepareStatement("SELECT Customername, Customerid, Customerphone FROM customer");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String Custname = rs.getString("Customername");
                String Custid = rs.getString("Customerid");
                String Custphone = rs.getString("Customerphone");
                model1.addRow(new Object[]{Custname, Custid, Custphone});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     private void filledTable() {
        DefaultTableModel model1 = (DefaultTableModel) jTable2.getModel();
        model1.setRowCount(0); // Clear existing rows

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gms", "root", "root");
             PreparedStatement pstmt = conn.prepareStatement("SELECT OrderID, Customerid, Productid, Quantity, Unit, Amount, created_at FROM sales");
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String Order = rs.getString("OrderID");
                String Custid = rs.getString("Customerid");
                String Proid = rs.getString("Productid");
                String Proquan = rs.getString("Quantity");
                String Unit = rs.getString("Unit");
                String Amount = rs.getString("Amount");
                String datevalue = rs.getString("created_at");

                model1.addRow(new Object[]{Order, Custid, Proid, Proquan, Unit, Amount, datevalue});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
     }
     
       /**
       * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        print = new javax.swing.JButton();
        vieworder = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cusID = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        proid = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        proquan = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        unit = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        Addcart = new javax.swing.JButton();
        back = new javax.swing.JButton();
        jdelet = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        orderid = new javax.swing.JTextField();
        date = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Showcard Gothic", 1, 48)); // NOI18N
        jLabel1.setText("Sales");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(844, 545));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ProID", "ProName", "Quantity", "Category"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Order ID", "Product ID", "Customer Id", "Quantity", "Unit Price", "Total", "Date and Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable2);
        if (jTable2.getColumnModel().getColumnCount() > 0) {
            jTable2.getColumnModel().getColumn(0).setResizable(false);
            jTable2.getColumnModel().getColumn(1).setResizable(false);
            jTable2.getColumnModel().getColumn(2).setResizable(false);
            jTable2.getColumnModel().getColumn(3).setResizable(false);
            jTable2.getColumnModel().getColumn(4).setResizable(false);
            jTable2.getColumnModel().getColumn(5).setResizable(false);
            jTable2.getColumnModel().getColumn(6).setResizable(false);
            jTable2.getColumnModel().getColumn(6).setPreferredWidth(120);
        }

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Customer ID", "Customer Name", "Phone No#"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable3.getTableHeader().setReorderingAllowed(false);
        jScrollPane3.setViewportView(jTable3);

        print.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        print.setText("Print order");
        print.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printActionPerformed(evt);
            }
        });

        vieworder.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        vieworder.setText("View Order");
        vieworder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vieworderActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel3.setText("Customer Id");

        jLabel4.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel4.setText("Product Id");

        jLabel5.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel5.setText("Quantity");

        jLabel6.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel6.setText("Unit Price");

        jLabel7.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        jLabel7.setText("Amount");

        Addcart.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        Addcart.setText("Add to cart");
        Addcart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddcartActionPerformed(evt);
            }
        });

        back.setFont(new java.awt.Font("Showcard Gothic", 0, 12)); // NOI18N
        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        jdelet.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jdelet.setText("Delete from Cart");
        jdelet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jdeletActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Showcard Gothic", 0, 14)); // NOI18N
        jLabel8.setText("Order ID");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(35, 35, 35)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(Addcart)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(0, 1, Short.MAX_VALUE)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(unit, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(proquan, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cusID, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(proid, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(orderid, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(47, 47, 47))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel8)))))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(back)
                        .addGap(45, 45, 45))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addComponent(jdelet)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(vieworder)
                            .addGap(18, 18, 18)
                            .addComponent(print)
                            .addGap(66, 66, 66))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 600, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(18, 18, 18)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(back)
                    .addComponent(date))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(orderid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cusID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(proid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(proquan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(unit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(amount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(Addcart)
                        .addGap(108, 108, 108))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(print)
                            .addComponent(vieworder)
                            .addComponent(jdelet))
                        .addGap(79, 79, 79))))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(331, 331, 331)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(369, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 854, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 509, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        this.toBack();
        Dashboard dashboard = new Dashboard();
        dashboard.setVisible(true);
        dashboard.toFront();
    }//GEN-LAST:event_backActionPerformed
        
    
       
    
    
    private void AddcartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddcartActionPerformed
        // TODO add your handling code here:
   
        
    String Order = orderid.getText();         
    String Unit = unit.getText();
    String Proquan = proquan.getText();
    String Proid = proid.getText();
    String Custid = cusID.getText();


        Connection conn = null;
        PreparedStatement pstmt = null;
    
        double proquanValue = Double.parseDouble(Proquan);
        double unitValue = Double.parseDouble(Unit);
        double amountValue = proquanValue * unitValue;
        amount.setText(String.valueOf(amountValue));
        String Amount = amount.getText();
        
            double availableQuantity = 0.0;
    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gms", "root", "root");
        String queryAvailability = "SELECT Quantity FROM product WHERE Productid = ?";
        pstmt = conn.prepareStatement(queryAvailability);
        pstmt.setString(1, Proid);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            availableQuantity = rs.getDouble("Quantity");
        }
        if (proquanValue > availableQuantity) {
            JOptionPane.showMessageDialog(this, "Invalid quantity. Available quantity: " + availableQuantity);
            return;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    
    if ( Unit.isEmpty()||Proquan.isEmpty() || Proid.isEmpty() || Custid.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill out all fields");
        return;
    }

          try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gms", "root", "root");
        String query = "INSERT INTO Sales (OrderID, Customerid, Productid, Quantity, Unit, Amount) VALUES (?, ?, ?, ?, ?, ?)";
        pstmt = conn.prepareStatement(query);
        
        pstmt.setString(1, Order);
        pstmt.setString(2, Custid);
        pstmt.setString(3, Proid);
        pstmt.setString(4, Proquan);
        pstmt.setString(5, Unit);
        pstmt.setDouble(6, amountValue);


        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            DefaultTableModel model1 = (DefaultTableModel) jTable2.getModel();
            model1.addRow(new Object[]{Order, Custid, Proid, Proquan, Unit, Amount});
            filledTable();

            // Update the available quantity in the product table
            String updateQuery = "UPDATE product SET Quantity = Quantity - ? WHERE Productid = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setString(1, Proquan);
            pstmt.setString(2, Proid);
            pstmt.executeUpdate();

            orderid.setText("");
            proid.setText("");
            cusID.setText("");
            proquan.setText("");
            unit.setText("");
            amount.setText("");

            filTable();
        } else {
            JOptionPane.showMessageDialog(null, "Fill all the data");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_AddcartActionPerformed

    private void vieworderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vieworderActionPerformed
        // TODO add your handling code here:
        dispose();
        View view = new View();
        view.setVisible(true);
    }//GEN-LAST:event_vieworderActionPerformed

    
    private void jdeletActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jdeletActionPerformed
        // TODO add your handling code here:
    String Order = orderid.getText();         
    String Unit = unit.getText();
    String Proquan = proquan.getText();
    String Proid = proid.getText();
    String Custid = cusID.getText();
    
   
        double proquanValue = Double.parseDouble(Proquan);
        double unitValue = Double.parseDouble(Unit);
        double amountValue = proquanValue * unitValue;
        amount.setText(String.valueOf(amountValue));
        
        
        String Amount = amount.getText();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
          // Check for empty fields
           if ( Unit.isEmpty()||Proquan.isEmpty() || Proid.isEmpty() || Custid.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill out all fields");
        return;}
                
          conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/gms", "root", "root");
          String query = "DELETE FROM sales WHERE OrderID = ?";
          pstmt = conn.prepareStatement(query);
          pstmt.setString(1, Order);

            orderid.setText("");
            proid.setText("");
            cusID.setText("");
            proquan.setText("");
            unit.setText("");
            amount.setText("");
           
            
          int rowsUpdated = pstmt.executeUpdate();
          if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Delete  Successfully");
            filledTable();
          } else {
            JOptionPane.showMessageDialog(this, "Delete failed. ");
          }
        }
        catch (SQLException ex) {
          ex.printStackTrace();
          JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jdeletActionPerformed
    
    

    private void printActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printActionPerformed
        // TODO add your handling code here:
        Sales sales = new Sales();
    try {
        sales.print();
    } catch (PrinterException e) {
        e.printStackTrace();
      JOptionPane.showMessageDialog(null, "Printing failed: " + e.getMessage(), "Print Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_printActionPerformed

     
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton Addcart;
    javax.swing.JTextField amount;
    javax.swing.JButton back;
    javax.swing.JTextField cusID;
    javax.swing.JLabel date;
    javax.swing.JLabel jLabel1;
    javax.swing.JLabel jLabel2;
    javax.swing.JLabel jLabel3;
    javax.swing.JLabel jLabel4;
    javax.swing.JLabel jLabel5;
    javax.swing.JLabel jLabel6;
    javax.swing.JLabel jLabel7;
    javax.swing.JLabel jLabel8;
    javax.swing.JPanel jPanel1;
    javax.swing.JPanel jPanel2;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JScrollPane jScrollPane2;
    javax.swing.JScrollPane jScrollPane3;
    javax.swing.JTable jTable1;
    javax.swing.JTable jTable2;
    javax.swing.JTable jTable3;
    javax.swing.JButton jdelet;
    javax.swing.JTextField orderid;
    javax.swing.JButton print;
    javax.swing.JTextField proid;
    javax.swing.JTextField proquan;
    javax.swing.JTextField unit;
    javax.swing.JButton vieworder;
    // End of variables declaration//GEN-END:variables
}
