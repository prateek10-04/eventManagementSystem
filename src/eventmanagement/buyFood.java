/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eventmanagement;

import java.awt.Component;
import javax.swing.JCheckBox;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 *
 * @author PRATEEK PARWANI
 */
public class buyFood extends javax.swing.JFrame {
    private int participantid;
    private int stallID;
    /**
     * Creates new form buyFood
     */
    public buyFood(int participantid,int stallID) {
        initComponents();
        this.participantid=participantid;
        this.stallID=stallID;
        displayFoodItemsFromDatabase();
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
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 420, 700));

        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton1.setText("Add Items");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 320, -1, -1));

        jButton2.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton2.setText("Back");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 370, 100, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/space1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int tot=0;
        Component[] components = jPanel1.getComponents();
        for (Component component : components) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                JLabel priceLabel = findPriceLabel(checkBox); // Find corresponding price label
                if (checkBox.isSelected()) {
                  //  selectedItems.put(checkBox.getText(), Double.parseDouble(priceLabel.getText().substring(1))); // Remove "$" and convert price to double
                  String priceText = priceLabel.getText().replace("INR ", ""); // Remove "$" from price label text
                double price = Double.parseDouble(priceText);
                    System.out.println(price);
                    tot+=price;
                }
            }
        }
        
        
        Statement stmt;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagement", "root", "Babaji@10");
            stmt = conn.createStatement();
            ResultSet rs=null;
            int rowsAffected=stmt.executeUpdate("UPDATE participant SET Wallet = Wallet - " +tot+" WHERE ParticipantID = "+participantid);

            //int rowsAffected = stmt.executeUpdate("UPDATE participant SET Wallet = Wallet - " + tot + " WHERE ParticipantID = " + participantid);

if (rowsAffected > 0) {
    // Participant's wallet updated successfully
    // Insert orders into the database
    for (Component component : components) {
        if (component instanceof JCheckBox) {
            JCheckBox checkBox = (JCheckBox) component;
            if (checkBox.isSelected()) {
                // Extract stall name and item name from the checkbox text
                String[] parts = checkBox.getText().split(" INR ");
                String itemName = parts[0];
                String stallName = "YourStallName"; // Set the stall name here or retrieve it from somewhere else in your application
                try {
            
            rs = stmt.executeQuery("SELECT * FROM stall where StallID = "+stallID);

            
            // Add checkboxes for each food item
            while (rs.next()) {
                stallName = rs.getString("StallName");
            }
            //stmt = conn.prepareStatement("UPDATE participant SET Wallet = Wallet - " +tot+" WHERE ParticipantID = "+participantid);

// Execute the update query
            

        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database errors
        }
                
                // Insert order into the database
                PreparedStatement orderStmt;
                try {
                    orderStmt = conn.prepareStatement("INSERT INTO orders(StallName, ItemName,Pending) VALUES(?, ?,1)");
                    orderStmt.setString(1, stallName);
                orderStmt.setString(2, itemName);
                orderStmt.executeUpdate();
                
                } catch (SQLException ex) {
                    Logger.getLogger(buyFood.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    }
    int moneychange=stmt.executeUpdate("UPDATE participant SET Money_spent = Money_spent + " +tot+" WHERE ParticipantID = "+participantid);
    if(moneychange>0){
    rs = stmt.executeQuery("SELECT * FROM participant where ParticipantID = "+participantid);
    int vara=-1,varb=-1,varc=-1,money=0;
            
            // Add checkboxes for each food item
            while (rs.next()) {
                 money = rs.getInt("Money_spent");
                 vara = rs.getInt("a");
                 varb = rs.getInt("b");
                 varc = rs.getInt("c");
                
            }
            if(money>=5000 && money<7000 && vara==1){
                moneychange=stmt.executeUpdate("UPDATE participant SET Wallet = Wallet + " +500+" WHERE ParticipantID = "+participantid);
                moneychange=stmt.executeUpdate("UPDATE participant SET a =  "+0+" WHERE ParticipantID = "+participantid);
                moneychange=stmt.executeUpdate("UPDATE participant SET Rewardpoints = Rewardpoints + " +500+" WHERE ParticipantID = "+participantid);

            } else if(money>=7000 && money<=8000 && varb==1){
                moneychange=stmt.executeUpdate("UPDATE participant SET Wallet = Wallet + " +250+" WHERE ParticipantID = "+participantid);
                moneychange=stmt.executeUpdate("UPDATE participant SET b =  " +0+" WHERE ParticipantID = "+participantid);
                                moneychange=stmt.executeUpdate("UPDATE participant SET Rewardpoints = Rewardpoints + " +250+" WHERE ParticipantID = "+participantid);

                
            } else if(money>=9000 && varc==1){
            moneychange=stmt.executeUpdate("UPDATE participant SET Wallet = Wallet + " +250+" WHERE ParticipantID = "+participantid);
            moneychange=stmt.executeUpdate("UPDATE participant SET c =  " +0+" WHERE ParticipantID = "+participantid);
                            moneychange=stmt.executeUpdate("UPDATE participant SET Rewardpoints = Rewardpoints + " +250+" WHERE ParticipantID = "+participantid);

    }
    
    
    
} 
               JOptionPane.showMessageDialog(this, "Items bought!");
            new Participant(participantid).setVisible(true);
}
else {
    // Participant's wallet balance is insufficient
    JOptionPane.showMessageDialog(this, "Insufficient Balance");
}
        
    }//GEN-LAST:event_jButton1ActionPerformed
    catch (SQLException ex) {
            Logger.getLogger(buyFood.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        new Participant(participantid).setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private JLabel findPriceLabel(JCheckBox checkBox) {
        Component[] components = jPanel1.getComponents();
        for (Component component : components) {
            if (component instanceof JLabel) {
                JLabel label = (JLabel) component;
                if (label.getBounds().y == checkBox.getBounds().y) {
                    return label;
                }
            }
        }
        return null;
    }
    
    private void displayFoodItemsFromDatabase() {
        // Clear existing components
        jPanel1.removeAll();
        
        // Retrieve food items from the database
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagement", "root", "Babaji@10");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT Name,Price FROM item where StallID = "+stallID);
            
            // Add checkboxes for each food item
            int y = 10; // Initial y-coordinate for checkboxes
            
            // Add checkboxes for each food item
            while (rs.next()) {
                String name = rs.getString("name");
                int price=rs.getInt("price");
                JCheckBox checkBox = new JCheckBox(name);
                checkBox.setBounds(10, y, 200, 20); // Adjust width to accommodate text
            jPanel1.add(checkBox);
            
            JLabel priceLabel = new JLabel("INR " + price);
            priceLabel.setBounds(220, y, 50, 20); // Adjust position accordingly
            jPanel1.add(priceLabel);
                
                y += 30; // Increment y-coordinate for next checkbox
            }
            //stmt = conn.prepareStatement("UPDATE participant SET Wallet = Wallet - " +tot+" WHERE ParticipantID = "+participantid);

// Execute the update query
            
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            // Handle database errors
        }
        
        // Revalidate and repaint the panel to reflect changes
        jPanel1.revalidate();
        jPanel1.repaint();
    }
    
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
            java.util.logging.Logger.getLogger(buyFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(buyFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(buyFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(buyFood.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new buyFood(1,1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
