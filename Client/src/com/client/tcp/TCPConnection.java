/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.common.Common;
import com.client.rsa.RSA;
import com.client.ui.start.ISettingAppContract;
import domain.Call;
import domain.Packages;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TCP này dùng để tạo connection dữ client và server .
 *
 * TCP này sẽ được lưu lại để nhận dữ liệu từ server gửi cho client
 *
 * @author ngao
 */
public class TCPConnection<T> extends TCPClientServer<T> {

    // socket này sẽ được lưu lại nếu kết nối thành công
    public static Socket client;
    private ISettingAppContract.View views;
    private Packages<T> packages;

    public TCPConnection(ISettingAppContract.View views, Packages<T> obj) {
        this.views = views;
        packages = obj;
    }

    @Override
    public void connect() throws Exception {
        // nếu đang có kết nối socket tới server thì không cần tạo thêm kết nối
        if (TCPConnection.client != null && !TCPConnection.client.isClosed()) {
            return;
        }

        try {
            TCPConnection.client = new Socket(Common.Main.connection.getHost(),
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
    public void request(Packages<T> obj) throws Exception {
//        DataOutputStream dataOutputServer = null;
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;

        int status = -1;

        try {
            // make greeting
//            dataOutputServer = new DataOutputStream(client.getOutputStream());
//            dataOutputServer.writeUTF("Hello from " + TCPClientServer.client.getLocalSocketAddress());
            // send file
            objectOutputStream = new ObjectOutputStream(TCPConnection.client.getOutputStream());
//            obj.setObjectData(RSA.encrpytion(obj.getByteObject()));
            objectOutputStream.writeObject(this.objectToJson(obj));
//            objectOutputStream.writeObject(RSA.encrpytion(this.objectToJson(obj)));

            objectInputStream = new ObjectInputStream(TCPConnection.client.getInputStream());
//            (Packages<Call>)jsonToObject(RSA.decryption(data));
//            Packages<?> dataServer = (Packages<?>) jsonToObject(RSA.decryption((String) objectInputStream.readObject()));
            Packages<?> dataServer = (Packages<?>) jsonToObject((String) objectInputStream.readObject());
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
                this.closeStream(objectOutputStream);
                this.closeStream(objectInputStream);
                this.closeSocket(TCPConnection.client);
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

}
