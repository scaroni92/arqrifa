package arq.prototipo.app;

import arq.prototipo.datatypes.DTReunion;
import arq.prototipo.rest.ClienteRest;
import com.google.gson.Gson;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

public class Main extends javax.swing.JFrame {

    private LocalDevice localDevice;
    private List<DTReunion> reunionesActivas;

    public Main() {
        initComponents();
        DesktopController.initiateController();
        jPanelContent.removeAll();
        jPanelContent.repaint();
        jPanelContent.revalidate();
        jPanelContent.add(jPanelList);
        jPanelContent.repaint();
        jPanelContent.revalidate();
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
        jPanelList = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListReuniones = new javax.swing.JList<>();
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
        jLabelGen = new javax.swing.JLabel();

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

        jListReuniones.setModel(getReunionesActivas());
        jListReuniones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jListReuniones);

        javax.swing.GroupLayout jPanelListLayout = new javax.swing.GroupLayout(jPanelList);
        jPanelList.setLayout(jPanelListLayout);
        jPanelListLayout.setHorizontalGroup(
            jPanelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 486, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanelListLayout.setVerticalGroup(
            jPanelListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelListLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanelContent.add(jPanelList, "card3");

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
        jLabel6.setText("Generación:");
        jPanel7.add(jLabel6);

        jLabelGen.setText("...");
        jPanel7.add(jLabelGen);

        jPanelReunionData.add(jPanel7);

        javax.swing.GroupLayout JPanelReunionLayout = new javax.swing.GroupLayout(JPanelReunion);
        JPanelReunion.setLayout(JPanelReunionLayout);
        JPanelReunionLayout.setHorizontalGroup(
            JPanelReunionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(JPanelReunionLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addComponent(jPanelReunionData, javax.swing.GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
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
                int selectedIndex = jListReuniones.getSelectedIndex();
                if (jListReuniones.getSelectedIndex() >= 0) {
                    DTReunion selectedReuinion = reunionesActivas.get(jListReuniones.getSelectedIndex());
                    //Creo el hilo del servidor bluetooth
                    DesktopController.iniciarPuenteBluetooth(selectedReuinion);
                    btnIniciar.setText("CERRAR");
                    lblEstado.setText("ON");
                    jPanelContent.removeAll();
                    jPanelContent.repaint();
                    jPanelContent.revalidate();

                    jLabelId.setText(String.valueOf(selectedReuinion.getId()));
                    jLabelFecha.setText(selectedReuinion.getFecha().toString());
                    jLabelGen.setText(String.valueOf(selectedReuinion.getGeneracion()));

                    jPanelContent.add(JPanelReunion);
                    jPanelContent.repaint();
                    jPanelContent.revalidate();
                }

            } else {
                DesktopController.cerrarPuenteBluetooth();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    private ListModel<String> getReunionesActivas() {
        DefaultListModel<String> model = new DefaultListModel<String>();
        try {
            reunionesActivas = DesktopController.getReunionesActivas();
            model = new DefaultListModel<String>();
            DateFormat df = new SimpleDateFormat("HH:mm:ss");
            for (DTReunion val : reunionesActivas) {
                model.addElement(val.getId() + " - " + val.getFecha().toString() + " - G" + val.getGeneracion());
            }
            return model;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return model;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel JPanelReunion;
    private javax.swing.JButton btnIniciar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelFecha;
    private javax.swing.JLabel jLabelGen;
    private javax.swing.JLabel jLabelId;
    public javax.swing.JList<String> jListReuniones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelContent;
    private javax.swing.JPanel jPanelList;
    private javax.swing.JPanel jPanelReunionData;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    // End of variables declaration//GEN-END:variables
}
