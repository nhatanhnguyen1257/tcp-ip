/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.ui.start;

import com.client.common.Common;
import domain.Connection;
import com.client.tcp.TCPConnection;
import domain.Packages;

/**
 *
 * @author ngao
 */
public class StartPresenter implements IStartAppContract.Presenter {

    private IStartAppContract.View views;

    public StartPresenter() {
        
    }

    @Override
    public void connection(Connection connection) {
        try {
            checkDataSetting(connection);
//            tCPClientServer.connect();
            Packages<Connection> packages = new Packages<Connection>();
            packages.setData(connection);
            packages.setAction(Common.STATUS_ACTION.LOGIN.ordinal());
//            tCPClientServer.request(tcp);
//            tCPClientServer = new TCPConnection(views, tcp);
            Common.Main.connection = connection;
            new Thread(new TCPConnection(views, packages)).start();
        } catch (Exception e) {
            e.printStackTrace();
            this.views.showMeg(e.getMessage());
        }
    }

    @Override
    public void setView(IStartAppContract.View view) {
        this.views = view;
    }

    private void checkDataSetting(Connection setting) throws Exception {

        try {
            Integer.parseInt(setting.getPoint());
        } catch (Exception e) {
            throw new Exception("Cổng kết nối nhập số ");
        }

        if (setting.getHost() == null || setting.getHost().equals("")) {
            throw new Exception("Bạn cần nhập địa chỉ server.");
        } else if (Integer.parseInt(setting.getPoint()) < 1024) {
            throw new Exception("Công kết nối với server không đúng.");
        } else if (setting.getUser()== null || setting.getUser().equals("")) {
            throw new Exception("Tên đăng nhập không đúng.");
        }
    }

}
