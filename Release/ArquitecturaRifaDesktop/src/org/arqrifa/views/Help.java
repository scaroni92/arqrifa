package org.arqrifa.views;


public class Help extends javax.swing.JFrame {
    private static Help instance = null;
    
    public static Help getInstance(){
        if (instance == null) {
            instance = new Help();
        }
        return instance;
    }
    
    private Help() {
        initComponents();
        this.setLocationRelativeTo(null);
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContainer = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblDescription = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlContainer.setBackground(new java.awt.Color(37, 40, 56));
        pnlContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(94, 187, 191));
        lblTitle.setText("Ayuda");
        pnlContainer.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 10, -1, -1));

        lblDescription.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDescription.setForeground(new java.awt.Color(255, 255, 255));
        lblDescription.setText("Lorem impsum..");
        pnlContainer.add(lblDescription, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Close.png"))); // NOI18N
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });
        pnlContainer.add(lblClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, -1, 30));

        getContentPane().add(pnlContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 280, 300));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblCloseMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDescription;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlContainer;
    // End of variables declaration//GEN-END:variables
}
