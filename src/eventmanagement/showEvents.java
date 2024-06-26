/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package eventmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.PreparedStatement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author PRATEEK PARWANI
 */
public class showEvents extends javax.swing.JFrame {
    private int participantid;private int adminID;
    /**
     * Creates new form showEvents
     */
    public showEvents(int participantid,int adminID) {
        this.participantid=participantid;
        this.adminID=adminID;
        initComponents();
        loadTable();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        nameSearch = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "EventID", "Name", "Venue", "Club", "Day", "Hour", "Minute", "Coordinator"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(7).setMaxWidth(200);
        }

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(298, 97, 647, 330));

        jLabel2.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Name");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 40, -1, -1));

        nameSearch.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        nameSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameSearchActionPerformed(evt);
            }
        });
        nameSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameSearchKeyReleased(evt);
            }
        });
        getContentPane().add(nameSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 40, 90, -1));

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setFont(new java.awt.Font("Yu Gothic UI", 1, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 40, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/space1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1280, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nameSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameSearchKeyReleased
        // TODO add your handling code here:
        Statement stmt;
        String search=nameSearch.getText();
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagement", "root", "Babaji@10");
            stmt = conn.createStatement();
            DefaultTableModel dft=(DefaultTableModel) jTable1.getModel();
            dft.setRowCount(0);
            PreparedStatement pstmt = conn.prepareStatement("Select event.EventID,event.Name,event.Venue,club.Club,event.Day,event.Hour,event.Minute,event.Coordinator from event inner join club on event.Venue = club.Venue  where event.Name like ? order by event.Day,event.Hour,event.Minute");
            pstmt.setString(1, search + "%");
            ResultSet rs = pstmt.executeQuery();
            
            while(rs.next()){
                Vector V =new Vector();
                V.add(rs.getString(1));
                V.add(rs.getString(2));
                V.add(rs.getString(3));
                V.add(rs.getString(4));
                V.add(rs.getString(5));
                V.add(rs.getString(6));
                V.add(rs.getString(7));
                dft.addRow(V);
            }
            centerAlignTableColumns(jTable1);
    }
        catch (SQLException ex) {
            Logger.getLogger(Wallet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_nameSearchKeyReleased

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        if(participantid==-1){
            new Admin(adminID).setVisible(true);
        }
        else if(adminID==-1)
            new Participant(participantid).setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void nameSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nameSearchActionPerformed

    /**
     * @param args the command line arguments
     */
    
    private void loadTable(){
        
        Statement stmt;
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/eventmanagement", "root", "Babaji@10");
            stmt = conn.createStatement();
            DefaultTableModel dft=(DefaultTableModel) jTable1.getModel();
            dft.setRowCount(0);
            ResultSet rs=stmt.executeQuery("Select event.EventID,event.Name,event.Venue,club.Club,event.Day,event.Hour,event.Minute,event.Coordinator from event inner join club on event.Venue = club.Venue order by event.Day,event.Hour,event.Minute");
            while(rs.next()){
                Vector V =new Vector();
                V.add(rs.getString(1));
                V.add(rs.getString(2));
                V.add(rs.getString(3));
                V.add(rs.getString(4));
                V.add(rs.getString(5));
                V.add(rs.getString(6));
                V.add(rs.getString(7));
                V.add(rs.getString(8));
                dft.addRow(V);
            }
            centerAlignTableColumns(jTable1);
    }
        catch (SQLException ex) {
            Logger.getLogger(Wallet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void centerAlignTableColumns(JTable table) {
    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
    centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
    TableColumnModel tableColumnModel = table.getColumnModel();
    for (int columnIndex = 0; columnIndex < tableColumnModel.getColumnCount(); columnIndex++) {
        tableColumnModel.getColumn(columnIndex).setCellRenderer(centerRenderer);
    }
}
    
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
            java.util.logging.Logger.getLogger(showEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(showEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(showEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(showEvents.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new showEvents(1,-1).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField nameSearch;
    // End of variables declaration//GEN-END:variables
}
