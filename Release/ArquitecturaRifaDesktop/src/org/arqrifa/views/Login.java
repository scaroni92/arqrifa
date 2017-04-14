package org.arqrifa.views;

import org.arqrifa.app.DesktopController;
import org.arqrifa.app.MessagePane;

public class Login extends javax.swing.JFrame {

    int mouseX;
    int mouseY;

    public Login() {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtCi = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        pnlLogin = new javax.swing.JPanel();
        lblLogin = new javax.swing.JLabel();
        txtPass = new javax.swing.JPasswordField();
        lblMessage = new javax.swing.JLabel();
        jLabelClose = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        jLabelDrag = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanelMain.setBackground(new java.awt.Color(37, 40, 56));
        jPanelMain.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanelMain.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 112, 170, 0));
        jPanelMain.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 180, 10));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Contraseña");
        jPanelMain.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Cédula");
        jPanelMain.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        txtCi.setBackground(jPanelMain.getBackground());
        txtCi.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtCi.setForeground(new java.awt.Color(255, 255, 255));
        txtCi.setBorder(null);
        txtCi.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanelMain.add(txtCi, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 180, -1));
        jPanelMain.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 180, 10));

        pnlLogin.setBackground(jPanelMain.getBackground());
        pnlLogin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(94, 187, 191)));
        pnlLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlLoginMouseClicked(evt);
            }
        });

        lblLogin.setBackground(new java.awt.Color(255, 255, 255));
        lblLogin.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblLogin.setForeground(new java.awt.Color(94, 187, 191));
        lblLogin.setText("Login");
        pnlLogin.add(lblLogin);

        jPanelMain.add(pnlLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 180, 30));

        txtPass.setBackground(jPanelMain.getBackground());
        txtPass.setForeground(new java.awt.Color(255, 255, 255));
        txtPass.setBorder(null);
        txtPass.setCaretColor(new java.awt.Color(255, 255, 255));
        jPanelMain.add(txtPass, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 190, 180, -1));

        lblMessage.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(255, 255, 255));
        lblMessage.setText("Ingrese sus credenciales");
        jPanelMain.add(lblMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, -1));

        jLabelClose.setFont(new java.awt.Font("Segoe UI Light", 0, 36)); // NOI18N
        jLabelClose.setForeground(new java.awt.Color(255, 255, 255));
        jLabelClose.setText("×");
        jLabelClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabelClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                close(evt);
            }
        });
        jPanelMain.add(jLabelClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 0, -1, 30));

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Login");
        jPanelMain.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabelDrag.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        jLabelDrag.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                jLabelDragMouseDragged(evt);
            }
        });
        jLabelDrag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jLabelDragMousePressed(evt);
            }
        });
        jPanelMain.add(jLabelDrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 230, 20));

        getContentPane().add(jPanelMain, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 260, 400));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void close(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_close
        System.exit(0);
    }//GEN-LAST:event_close

    private void jLabelDragMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDragMousePressed
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_jLabelDragMousePressed

    private void jLabelDragMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDragMouseDragged
        int x = evt.getXOnScreen() - mouseX;
        int y = evt.getYOnScreen() - mouseY;

        this.setLocation(x, y);
    }//GEN-LAST:event_jLabelDragMouseDragged

    private void pnlLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLoginMouseClicked
        try {
            int ci = Integer.parseInt(txtCi.getText());
            String pass = String.valueOf(txtPass.getPassword());

            DesktopController.ingresar(ci, pass);
            
            this.setVisible(false);
        } catch (NumberFormatException e) {
            new MessagePane().displayPane("Info", "Ingrese una cédula válida", MessagePane.ALERT);
        } catch (Exception e) {
            new MessagePane().displayPane("Info", e.getMessage(), MessagePane.ALERT);
        }
    }//GEN-LAST:event_pnlLoginMouseClicked

    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelClose;
    private javax.swing.JLabel jLabelDrag;
    private javax.swing.JPanel jPanelMain;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JLabel lblLogin;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlLogin;
    private javax.swing.JTextField txtCi;
    private javax.swing.JPasswordField txtPass;
    // End of variables declaration//GEN-END:variables
}
