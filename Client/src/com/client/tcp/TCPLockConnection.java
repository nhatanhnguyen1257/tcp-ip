/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.common.Common;
import com.client.rsa.RSA;
import domain.Packages;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.client.ui.home.ICallContract;

/**
 * kiểm tra xe người được nhận cuộc gọi này đang có cuộc gọi nào không.
 *
 * @author ngao
 */
public class TCPLockConnection<T> extends TCPClientServer<T> {

    private Socket client;
    private ICallContract.IView views;
    private Packages<T> packages;
    private int status = -1;

    public TCPLockConnection(ICallContract.IView view, Packages<T> obj) {
        views = view;
        packages = obj;
    }

    @Override
    public void connect() throws Exception {
        // nếu đang có kết nối socket tới server thì không cần tạo thêm kết nối
        if (client != null && !client.isClosed()) {
            return;
        }

        try {
            client = new Socket(Common.Main.connection.getHost(),
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

        try {
            // make greeting
//            dataOutputServer = new DataOutputStream(client.getOutputStream());
//            dataOutputServer.writeUTF("Hello from " + TCPClientServer.client.getLocalSocketAddress());
            // send file
            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
//            obj.setObjectData(RSA.encrpytion(obj.getByteObject()));
            objectOutputStream.writeObject(obj);

            objectInputStream = new ObjectInputStream(client.getInputStream());
            Packages<?> dataServer = (Packages<?>) objectInputStream.readObject();
            status = dataServer.getStatus();

            views.showMeg(Common.getMessage(dataServer.getStatus()));
        } catch (Exception e) {
            Logger.getLogger(TCPClientServer.class.getName()).log(Level.SEVERE, null, e);
            views.showMeg("Một lỗi chưa xác định.");
            throw e;
        } finally {
            this.closeStream(objectOutputStream);
            this.closeStream(objectInputStream);
            this.closeSocket(client);
        }
    }

    @Override
    public void run() {
        try {
            connect();
            request(packages);
        } catch (Exception ex) {
            Logger.getLogger(TCPLockConnection.class.getName()).log(Level.SEVERE, null, ex);
            views.showMeg("một lỗi không xác định.");
        }
    }

}
