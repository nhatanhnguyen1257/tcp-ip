/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.common.Common;
import domain.Connection;
import domain.Data;
import domain.Packages;
import domain.SocketBase;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.client.ui.start.IStartAppContract;

/**
 * TCP này dùng để tạo connection dữ client và server .
 *
 * TCP này sẽ được lưu lại để nhận dữ liệu từ server gửi cho client
 *
 * @author ngao
 */
public class TCPConnection extends TCPClientServer<Connection> {

    // socket này sẽ được lưu lại nếu kết nối thành công
    public static SocketBase client;
    private IStartAppContract.View views;
    private Packages<Connection> packages;

    public TCPConnection(IStartAppContract.View views, Packages<Connection> packag) {
        this.views = views;
        packages = packag;
    }

    @Override
    public void connect() throws Exception {
        // nếu đang có kết nối socket tới server thì không cần tạo thêm kết nối
        if (TCPConnection.client != null && !TCPConnection.client.getSocket().isClosed()) {
            return;
        }

        try {
            TCPConnection.client = new SocketBase(Common.Main.connection.getHost(),
                    Integer.parseInt(Common.Main.connection.getPoint()));

            System.out.println("connected to server");
        } catch (UnknownHostException e) {
            Logger.getLogger(TCPClientServer.class.getName()).log(Level.SEVERE, null, e);
            throw new Exception("Thông tin server hoặc cổng sai, không thể liên kết với server");
        } catch (IOException e) {
            Logger.getLogger(TCPClientServer.class.getName()).log(Level.SEVERE, null, e);
            throw new Exception("Thông tin server hoặc cổng sai, không thể liên kết với server");
        }
    }

    @Override
    public void request(Packages<Connection> obj) throws Exception {

        int status = -1;

        try {

            // tạo dữ liệu gửi lên server
            Data data = new Data();
            data.data = createDataSend(obj);
            TCPConnection.client.sendData(data);

            // đọc dữ liệu từ sever gửi lại cho client
            data = TCPConnection.client.readData();
            
            Packages<Connection> dataServer  = this.readByteResponse(data.data);

            status = dataServer.getStatus();
            if (status == Common.STATUS_SERVER.OK.ordinal()) {
                views.changePanel();
                
                /* connection thành công tới server khởi tạo 1 luồng
                 * lắng nghe tại client để nhận dữ liệu từ sever*/
                new Thread(new TCPAPP()).start();
            }
            views.showMeg(Common.getMessage(dataServer.getStatus()));
        } catch (Exception e) {
            Logger.getLogger(TCPClientServer.class.getName()).log(Level.SEVERE, null, e);
            views.showMeg("Một lỗi chưa xác định.");
            throw e;
        } finally {

            // nếu kết nối phát sinh nỗi thì sẽ close
            if (status != Common.STATUS_SERVER.OK.ordinal()) {
               TCPConnection.client.close();
            }
        }
    }

    @Override
    public void run() {
        try {
            connect();
            request(packages);
        } catch (Exception ex) {
            Logger.getLogger(TCPConnection.class.getName()).log(Level.SEVERE, null, ex);
            views.showMeg("một lỗi không xác định.");
        }
    }

    @Override
    public byte[] createDataSend(Packages<Connection> obj) throws Exception {
        return this.encrpytion(obj.getData().getDataSend(0));
    }

    
    @Override
    public Packages<Connection> readByteResponse(byte[] data) throws Exception {
        return new Connection().readDataSend(data);
    }

}
