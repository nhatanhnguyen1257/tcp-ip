/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.ui.start;

import com.client.ui.home.PanelCall;
import com.client.ui.main.Main;
import domain.Connection;
import java.awt.GridLayout;
import javax.swing.JFrame;

/**
 *
 * @author ngao
 */
public class PanelStartApp extends javax.swing.JPanel implements ISettingAppContract.View{

    private ISettingAppContract.Presenter settingPresenter;
    private Main appMain;
    
    /**
     * Creates new form PanelStartApp
     */
    public PanelStartApp() {
        initComponents();
        init();
    }

    public void setFrameMain(Main frameMain) {
        this.appMain = frameMain;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        Bt_Connection = new javax.swing.JButton();
        tb_userName = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tb_nameHost = new javax.swing.JTextField();
        tb_error = new javax.swing.JLabel();
        tb_pointHost = new javax.swing.JTextField();

        setBackground(new java.awt.Color(253, 253, 247));
        setAutoscrolls(true);
        setMinimumSize(new java.awt.Dimension(100, 300));

        jLabel1.setText("Nhập mã đăng nhập");

        Bt_Connection.setText("Kết nối tới server");
        Bt_Connection.setName("bt_login"); // NOI18N
        Bt_Connection.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Bt_ConnectionMouseClicked(evt);
            }
        });

        tb_userName.setName("tb_userName"); // NOI18N

        jLabel2.setText("Nhập địa chỉ server");

        jLabel3.setText("Nhập point server");

        tb_nameHost.setName("tb_nameHost"); // NOI18N

        tb_error.setForeground(new java.awt.Color(241, 16, 22));
        tb_error.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        tb_error.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        tb_error.setName("tb_error"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tb_error, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Bt_Connection, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(tb_userName)
                            .addComponent(tb_nameHost)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1))
                                .addGap(107, 107, 107))
                            .addComponent(tb_pointHost))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(tb_nameHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tb_pointHost, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tb_userName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Bt_Connection)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tb_error, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(185, Short.MAX_VALUE))
        );

        tb_error.getAccessibleContext().setAccessibleDescription("");
    }// </editor-fold>//GEN-END:initComponents

    private void Bt_ConnectionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Bt_ConnectionMouseClicked
        this.settingPresenter.connection(new Connection(this.tb_pointHost.getText(),
                        this.tb_nameHost.getText(),
                        this.tb_userName.getText()));
    }//GEN-LAST:event_Bt_ConnectionMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Bt_Connection;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel tb_error;
    private javax.swing.JTextField tb_nameHost;
    private javax.swing.JTextField tb_pointHost;
    private javax.swing.JTextField tb_userName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void showMeg(String message) {
       this.tb_error.setText(message == null ? "" : message );
    }

    @Override
    public void init() {
        settingPresenter = new SettingPresenter();
        settingPresenter.setView(this);
    }

    @Override
    public void changePanel() {
        this.appMain.changePanel(new PanelCall());
    }
}
