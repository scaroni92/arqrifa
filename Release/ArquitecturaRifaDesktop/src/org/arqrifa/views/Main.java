package org.arqrifa.views;

import java.awt.Frame;
import java.text.SimpleDateFormat;
import org.arqrifa.app.DesktopController;
import org.arqrifa.app.MessagePane;
import org.arqrifa.datatypes.DTReunion;

public class Main extends javax.swing.JFrame {

    int mouseX;
    int mouseY;

    public Main() {
        initComponents();
        setLocationRelativeTo(null);
        cargarDatos();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlLeft = new javax.swing.JPanel();
        lblReload = new javax.swing.JLabel();
        lblHelp = new javax.swing.JLabel();
        pnlTop = new javax.swing.JPanel();
        lblDrag = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        pnlOffline = new javax.swing.JPanel();
        lblOfflineIcon = new javax.swing.JLabel();
        lblOffline = new javax.swing.JLabel();
        pnlOnline = new javax.swing.JPanel();
        lblOnlineIcon = new javax.swing.JLabel();
        lblOnline = new javax.swing.JLabel();
        lblClose = new javax.swing.JLabel();
        pnlMiddle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblLogo = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        pnlBottom = new javax.swing.JPanel();
        pnlReunionDetalles = new javax.swing.JPanel();
        lblReunionFechaHora = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        lblReunionTitulo = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        lblReunionEstado = new javax.swing.JLabel();
        lblAsistenciasRegistradas = new javax.swing.JLabel();
        lbl = new javax.swing.JLabel();
        pnlActivar = new javax.swing.JPanel();
        lblActivar = new javax.swing.JLabel();
        lblMessage = new javax.swing.JLabel();
        lblLog = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLeft.setBackground(new java.awt.Color(37, 40, 56));
        pnlLeft.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblReload.setBackground(new java.awt.Color(255, 255, 255));
        lblReload.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblReload.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Refresh.png"))); // NOI18N
        lblReload.setToolTipText("Ayuda");
        lblReload.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblReload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblReloadMouseClicked(evt);
            }
        });
        pnlLeft.add(lblReload, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 50, -1));

        lblHelp.setBackground(new java.awt.Color(255, 255, 255));
        lblHelp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblHelp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Help.png"))); // NOI18N
        lblHelp.setToolTipText("Ayuda");
        lblHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblHelp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHelpMouseClicked(evt);
            }
        });
        pnlLeft.add(lblHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 50, -1));

        getContentPane().add(pnlLeft, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 50, 500));

        pnlTop.setBackground(new java.awt.Color(255, 255, 255));
        pnlTop.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        pnlTop.add(lblDrag, new org.netbeans.lib.awtextra.AbsoluteConstraints(-50, 0, 980, 30));

        lblMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Minimize.png"))); // NOI18N
        lblMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMinimizeMouseClicked(evt);
            }
        });
        pnlTop.add(lblMinimize, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 10, -1, 20));

        pnlOffline.setBackground(pnlTop.getBackground());
        pnlOffline.setPreferredSize(new java.awt.Dimension(81, 30));

        lblOfflineIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Offline.png"))); // NOI18N

        lblOffline.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblOffline.setForeground(new java.awt.Color(204, 204, 204));
        lblOffline.setText("Offline");

        javax.swing.GroupLayout pnlOfflineLayout = new javax.swing.GroupLayout(pnlOffline);
        pnlOffline.setLayout(pnlOfflineLayout);
        pnlOfflineLayout.setHorizontalGroup(
            pnlOfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOfflineLayout.createSequentialGroup()
                .addComponent(lblOfflineIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOffline)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlOfflineLayout.setVerticalGroup(
            pnlOfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOfflineLayout.createSequentialGroup()
                .addGroup(pnlOfflineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOfflineIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlOfflineLayout.createSequentialGroup()
                        .addComponent(lblOffline, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pnlTop.add(pnlOffline, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, 80, -1));

        pnlOnline.setBackground(pnlTop.getBackground());

        lblOnlineIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/Online.png"))); // NOI18N

        lblOnline.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblOnline.setForeground(new java.awt.Color(84, 219, 181));
        lblOnline.setText("Online");

        javax.swing.GroupLayout pnlOnlineLayout = new javax.swing.GroupLayout(pnlOnline);
        pnlOnline.setLayout(pnlOnlineLayout);
        pnlOnlineLayout.setHorizontalGroup(
            pnlOnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOnlineLayout.createSequentialGroup()
                .addComponent(lblOnlineIcon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblOnline)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlOnlineLayout.setVerticalGroup(
            pnlOnlineLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblOnline, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblOnlineIcon, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
        );

        pnlTop.add(pnlOnline, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 30));

        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/icons/CloseRounded.png"))); // NOI18N
        lblClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblClose.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCloseMouseClicked(evt);
            }
        });
        pnlTop.add(lblClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 0, -1, 30));

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 990, 30));

        pnlMiddle.setBackground(new java.awt.Color(240, 240, 241));
        pnlMiddle.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTitle.setFont(new java.awt.Font("Segoe UI Light", 0, 48)); // NOI18N
        lblTitle.setText("Puente Bluetooth V1.0");
        pnlMiddle.add(lblTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/arqrifa/resources/img/logo.png"))); // NOI18N
        pnlMiddle.add(lblLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, 180, 160));

        lblSubtitle.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblSubtitle.setText("Adaptador Bluetooth - Arquitectura Rifa");
        pnlMiddle.add(lblSubtitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, -1, -1));

        getContentPane().add(pnlMiddle, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 990, 210));

        pnlBottom.setBackground(new java.awt.Color(58, 58, 78));
        pnlBottom.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlReunionDetalles.setBackground(new java.awt.Color(85, 85, 119));
        pnlReunionDetalles.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblReunionFechaHora.setBackground(new java.awt.Color(255, 255, 255));
        lblReunionFechaHora.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblReunionFechaHora.setForeground(new java.awt.Color(255, 255, 255));
        lblReunionFechaHora.setText("Hora de inicio: 00:00 AM");
        pnlReunionDetalles.add(lblReunionFechaHora, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 6, -1, 20));
        pnlReunionDetalles.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 440, -1));

        lblReunionTitulo.setBackground(new java.awt.Color(255, 255, 255));
        lblReunionTitulo.setFont(new java.awt.Font("Segoe UI Light", 1, 12)); // NOI18N
        lblReunionTitulo.setForeground(new java.awt.Color(255, 255, 255));
        lblReunionTitulo.setText("Título de la reunión");
        pnlReunionDetalles.add(lblReunionTitulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        lblDescripcion.setBackground(new java.awt.Color(255, 255, 255));
        lblDescripcion.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblDescripcion.setForeground(new java.awt.Color(255, 255, 255));
        lblDescripcion.setText("Otra info...");
        pnlReunionDetalles.add(lblDescripcion, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        lblReunionEstado.setBackground(new java.awt.Color(255, 255, 255));
        lblReunionEstado.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblReunionEstado.setForeground(new java.awt.Color(255, 255, 255));
        lblReunionEstado.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblReunionEstado.setText("Estado: INICIADA");
        pnlReunionDetalles.add(lblReunionEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 110, -1));

        lblAsistenciasRegistradas.setBackground(new java.awt.Color(255, 255, 255));
        lblAsistenciasRegistradas.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblAsistenciasRegistradas.setForeground(new java.awt.Color(255, 255, 255));
        lblAsistenciasRegistradas.setText("Asistencias registradas: 0");
        pnlReunionDetalles.add(lblAsistenciasRegistradas, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        lbl.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lbl.setForeground(new java.awt.Color(255, 255, 255));
        lbl.setText("Reunión activa");
        pnlReunionDetalles.add(lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 30));

        pnlBottom.add(pnlReunionDetalles, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 460, 180));

        pnlActivar.setBackground(new java.awt.Color(94, 187, 191));
        pnlActivar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lblActivar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblActivar.setForeground(new java.awt.Color(255, 255, 255));
        lblActivar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblActivar.setText("ACTIVAR");
        lblActivar.setAlignmentX(0.5F);
        lblActivar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblActivarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout pnlActivarLayout = new javax.swing.GroupLayout(pnlActivar);
        pnlActivar.setLayout(pnlActivarLayout);
        pnlActivarLayout.setHorizontalGroup(
            pnlActivarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlActivarLayout.createSequentialGroup()
                .addComponent(lblActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlActivarLayout.setVerticalGroup(
            pnlActivarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlActivarLayout.createSequentialGroup()
                .addComponent(lblActivar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlBottom.add(pnlActivar, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 110, 180, 50));

        lblMessage.setFont(new java.awt.Font("Segoe UI Light", 0, 14)); // NOI18N
        lblMessage.setForeground(new java.awt.Color(255, 255, 255));
        lblMessage.setText("No se encontro ninguna reunión para la fecha actual");
        pnlBottom.add(lblMessage, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        lblLog.setBackground(new java.awt.Color(255, 255, 255));
        lblLog.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        lblLog.setForeground(new java.awt.Color(255, 255, 255));
        lblLog.setText("Sistema: marcando asistencia para el estudiante 55555555...");
        pnlBottom.add(lblLog, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 230, -1, -1));

        getContentPane().add(pnlBottom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 240, 990, 260));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblDragMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDragMousePressed
        mouseX = evt.getX();
        mouseY = evt.getY();
    }//GEN-LAST:event_lblDragMousePressed

    private void lblDragMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblDragMouseDragged
        int x = evt.getXOnScreen() - mouseX;
        int y = evt.getYOnScreen() - mouseY;

        this.setLocation(x, y);
    }//GEN-LAST:event_lblDragMouseDragged

    private void lblMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMinimizeMouseClicked
        this.setState(Frame.ICONIFIED);
    }//GEN-LAST:event_lblMinimizeMouseClicked

    private void lblActivarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblActivarMouseClicked

        try {
            if (lblActivar.getText().equals("ACTIVAR")) {
                DesktopController.iniciarPuenteBluetooth();
                lblActivar.setText("DESACTIVAR");
                pnlOnline.setVisible(true);
                pnlOffline.setVisible(false);
                new MessagePane().displayPane("Éxito", "Adaptador Bluetooth activado exitosamente", MessagePane.OK);
            } else {
                //DesktopController.finalizarPuenteBluetooth();
                lblActivar.setText("ACTIVAR");
                pnlOnline.setVisible(false);
                pnlOffline.setVisible(true);
                new MessagePane().displayPane("Éxito", "Adaptador Bluetooth desactivado exitosamente", MessagePane.OK);
            }
        } catch (Exception e) {
            new MessagePane().displayPane("Info", e.getMessage(), MessagePane.OK);
        }


    }//GEN-LAST:event_lblActivarMouseClicked

    private void lblCloseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCloseMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lblCloseMouseClicked

    private void lblReloadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblReloadMouseClicked
        cargarDatos();
    }//GEN-LAST:event_lblReloadMouseClicked

    private void lblHelpMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHelpMouseClicked
        Help.getInstance().setVisible(true);
    }//GEN-LAST:event_lblHelpMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lbl;
    private javax.swing.JLabel lblActivar;
    private javax.swing.JLabel lblAsistenciasRegistradas;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblDrag;
    private javax.swing.JLabel lblHelp;
    private javax.swing.JLabel lblLog;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblMessage;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JLabel lblOffline;
    private javax.swing.JLabel lblOfflineIcon;
    private javax.swing.JLabel lblOnline;
    private javax.swing.JLabel lblOnlineIcon;
    private javax.swing.JLabel lblReload;
    private javax.swing.JLabel lblReunionEstado;
    private javax.swing.JLabel lblReunionFechaHora;
    private javax.swing.JLabel lblReunionTitulo;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel pnlActivar;
    private javax.swing.JPanel pnlBottom;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlMiddle;
    private javax.swing.JPanel pnlOffline;
    private javax.swing.JPanel pnlOnline;
    private javax.swing.JPanel pnlReunionDetalles;
    private javax.swing.JPanel pnlTop;
    // End of variables declaration//GEN-END:variables

    private void cargarDatos() {
        DTReunion reunion = DesktopController.getReunion();
        if (reunion != null) {
            lblReunionFechaHora.setText(new SimpleDateFormat("'Hora de inico: 'hh:mm a").format(reunion.getFecha()));
            lblReunionTitulo.setText(reunion.getTitulo());
            lblDescripcion.setText(reunion.getDescripcion());
            lblReunionEstado.setText("Estado: " + reunion.getEstado().toUpperCase());
            lblMessage.setVisible(false);
        } else {
            lblMessage.setVisible(true);
            pnlReunionDetalles.setVisible(false);
            pnlActivar.setVisible(false);
        }
        lblLog.setText("");
    }
}
