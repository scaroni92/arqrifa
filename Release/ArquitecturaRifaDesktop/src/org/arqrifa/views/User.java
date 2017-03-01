package org.arqrifa.views;

import org.arqrifa.app.DesktopController;
import org.arqrifa.datatypes.DTUsuario;

public class User extends javax.swing.JFrame {

    private static User instance = null;

    int mouseX;
    int mouseY;

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private User() {
        initComponents();
        this.setLocationRelativeTo(null);
        cargarDatos();
    }

    private void cargarDatos() {
        DTUsuario usuario = DesktopController.getUsuario();
        lblName.setText(usuario.getNombre() + " " + usuario.getApellido());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTop = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        pnlMiddle = new javax.swing.JPanel();
        lblUserIcon = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        pnlBottom = new javax.swing.JPanel();
        lblName = new javax.swing.JLabel();
        lblDrag = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTop.setBackground(new java.awt.Color(37, 40, 56));
        pnlTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Close.png"))); // NOI18N
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });
        pnlTop.add(lblClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, -1, 30));

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 30));

        pnlMiddle.setBackground(new java.awt.Color(37, 40, 56));
        pnlMiddle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblUserIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/User-Filled.png"))); // NOI18N
        pnlMiddle.add(lblUserIcon, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, -1));

        lblRol.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lblRol.setForeground(new java.awt.Color(94, 187, 191));
        lblRol.setText("Encargado");
        pnlMiddle.add(lblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 130, -1, -1));

        getContentPane().add(pnlMiddle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 280, 160));

        pnlBottom.setBackground(new java.awt.Color(37, 40, 56));

        lblName.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("Juan García");
        pnlBottom.add(lblName);

        getContentPane().add(pnlBottom, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 280, 110));

        lblDrag.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        lblDrag.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                lblDragMouseDragged(evt);
            }
        });
        lblDrag.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblDragMousePressed(evt);
            }
        });
        getContentPane().add(lblDrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblDragMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDragMouseDragged
        int x = evt.getXOnScreen() - mouseX;
        int y = evt.getYOnScreen() - mouseY;

        this.setLocation(x, y);
    }//GEN-LAST:event_lblDragMouseDragged

    private void lblDragMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDragMousePressed
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_lblDragMousePressed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDrag;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlMiddle;
    private javax.swing.JPanel pnlTop;
    // End of variables declaration//GEN-END:variables
}
