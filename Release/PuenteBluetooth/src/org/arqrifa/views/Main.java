package org.arqrifa.views;

import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import org.arqrifa.app.*;
import org.arqrifa.datatypes.DTReunion;

public class Main extends javax.swing.JFrame {

    private final DTReunion reunionActiva;

    public Main() {
        initComponents();
        DesktopController.initiateController();
        reunionActiva = DesktopController.getReunionActiva();
        cargarDatos();
    }

    private void cargarDatos() {        
        jLabelId.setText(String.valueOf(reunionActiva.getId()));
        jLabelFecha.setText(new SimpleDateFormat("dd 'de' MMMM 'de' yyyy, hh:mm 'hrs'").format(reunionActiva.getFecha()));
        jLabelEstado.setText(reunionActiva.getEstado());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        btnIniciar = new javax.swing.JButton();
        jPanelContent = new javax.swing.JPanel();
        JPanelReunion = new javax.swing.JPanel();
        jPanelReunionData = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabelFecha = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabelEstado = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(500, 500));
        setName("Puente Bluetooth"); // NOI18N
        setResizable(false);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel1.setFont(new java.awt.Font("Andale Mono", 1, 15)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ARQUITECTURA RIFA");
        jPanel2.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Puente Bluetooth");
        jPanel2.add(jLabel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel3.setText("Estado:");
        jPanel1.add(jLabel3);

        lblEstado.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        lblEstado.setText("OFF");
        jPanel1.add(lblEstado);

        jPanel3.add(jPanel1, java.awt.BorderLayout.PAGE_END);

        btnIniciar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        btnIniciar.setText("INICIAR");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        jPanel4.add(btnIniciar);

        jPanel3.add(jPanel4, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);

        jPanelContent.setMaximumSize(new java.awt.Dimension(0, 0));
        jPanelContent.setLayout(new java.awt.CardLayout());

        jPanelReunionData.setLayout(new javax.swing.BoxLayout(jPanelReunionData, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel4.setText("Id:");
        jPanel5.add(jLabel4);

        jLabelId.setText("...");
        jPanel5.add(jLabelId);

        jPanelReunionData.add(jPanel5);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel5.setText("Fecha:");
        jPanel6.add(jLabel5);

        jLabelFecha.setText("...");
        jPanel6.add(jLabelFecha);

        jPanelReunionData.add(jPanel6);

        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel6.setText("Estado:");
        jPanel7.add(jLabel6);

        jLabelEstado.setText("...");
        jPanel7.add(jLabelEstado);

        jPanelReunionData.add(jPanel7);

        javax.swing.GroupLayout JPanelReunionLayout = new javax.swing.GroupLayout(JPanelReunion);
        JPanelReunion.setLayout(JPanelReunionLayout);
        JPanelReunionLayout.setHorizontalGroup(
            JPanelReunionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelReunionLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jPanelReunionData, javax.swing.GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                .addContainerGap())
        );
        JPanelReunionLayout.setVerticalGroup(
            JPanelReunionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelReunionLayout.createSequentialGroup()
                .addGap(148, 148, 148)
                .addComponent(jPanelReunionData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(169, Short.MAX_VALUE))
        );

        jPanelContent.add(JPanelReunion, "card4");

        getContentPane().add(jPanelContent, java.awt.BorderLayout.LINE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        try {
            if (btnIniciar.getText().equals("INICIAR")) {
                //Creo el hilo del servidor bluetooth
                DesktopController.iniciarPuenteBluetooth();
                btnIniciar.setText("CERRAR");
                lblEstado.setText("ON");

            } else {
                DesktopController.cerrarPuenteBluetooth();
            }
        } catch (IOException | InterruptedException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }//GEN-LAST:event_btnIniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelReunion;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelEstado;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JPanel jPanelReunionData;
    private javax.swing.JLabel lblEstado;
    // End of variables declaration//GEN-END:variables

}
