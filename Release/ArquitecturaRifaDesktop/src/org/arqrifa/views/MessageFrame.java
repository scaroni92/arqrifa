package org.arqrifa.views;

public class MessageFrame extends javax.swing.JFrame {

    public static final int OK = 1;
    public static final int ALERT = 2;
    public static final int CONFLICT = 3;

    int mouseX;
    int mouseY;

    public MessageFrame(String title, String message, int type) {
        initComponents();
        this.setLocationRelativeTo(null);
        lblTitle.setText(title);
        lblMessage.setText(message);
        lblOk.setVisible(false);
        lblInfo.setVisible(false);
        lblConflict.setVisible(false);
        switch (type) {
            case OK:
                lblOk.setVisible(true);
                break;
            case ALERT:
                lblInfo.setVisible(true);
                break;
            case CONFLICT:
                lblConflict.setVisible(true);
                break;
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlTop = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblDrag = new javax.swing.JLabel();
        lblInfo = new javax.swing.JLabel();
        lblOk = new javax.swing.JLabel();
        lblConflict = new javax.swing.JLabel();
        pnlContainer = new javax.swing.JPanel();
        pnlMessage = new javax.swing.JPanel();
        lblMessage = new javax.swing.JLabel();
        pnlConfirm = new javax.swing.JPanel();
        lblConfirm = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlTop.setBackground(new java.awt.Color(37, 40, 56));
        pnlTop.setCursor(new java.awt.Cursor(java.awt.Cursor.MOVE_CURSOR));
        pnlTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Título");
        pnlTop.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, 100, 30));

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
        pnlTop.add(lblDrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 30));

        lblInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Info.png"))); // NOI18N
        pnlTop.add(lblInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        lblOk.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Checked.png"))); // NOI18N
        pnlTop.add(lblOk, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        lblConflict.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConflict.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Error.png"))); // NOI18N
        pnlTop.add(lblConflict, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 30));

        pnlContainer.setBackground(new java.awt.Color(37, 40, 56));
        pnlContainer.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMessage.setBackground(pnlContainer.getBackground());

        lblMessage.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(255, 255, 255));
        lblMessage.setText("Nombre de usuario o contraseña incorrectos");
        pnlMessage.add(lblMessage);

        pnlContainer.add(pnlMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 40));

        pnlConfirm.setBackground(pnlContainer.getBackground());
        pnlConfirm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(94, 187, 191)));
        pnlConfirm.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        pnlConfirm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlConfirmMouseClicked(evt);
            }
        });

        lblConfirm.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblConfirm.setForeground(new java.awt.Color(94, 187, 191));
        lblConfirm.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblConfirm.setText("Confirmar");

        javax.swing.GroupLayout pnlConfirmLayout = new javax.swing.GroupLayout(pnlConfirm);
        pnlConfirm.setLayout(pnlConfirmLayout);
        pnlConfirmLayout.setHorizontalGroup(
            pnlConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlConfirmLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlConfirmLayout.setVerticalGroup(
            pnlConfirmLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblConfirm, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
        );

        pnlContainer.add(pnlConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 50, 90, 30));

        getContentPane().add(pnlContainer, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 570, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void pnlConfirmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlConfirmMouseClicked
        this.dispose();
    }//GEN-LAST:event_pnlConfirmMouseClicked

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
    private javax.swing.JLabel lblConfirm;
    private javax.swing.JLabel lblConflict;
    private javax.swing.JLabel lblDrag;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblOk;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlConfirm;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JPanel pnlMessage;
    private javax.swing.JPanel pnlTop;
    // End of variables declaration//GEN-END:variables
}
