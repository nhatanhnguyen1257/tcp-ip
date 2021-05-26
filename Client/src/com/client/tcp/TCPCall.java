/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.audio.Audio;
import com.client.common.Common;
import domain.Call;
import domain.Packages;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.client.ui.home.ICallContract;
import domain.Data;
import domain.SocketBase;

/**
 *
 * @author ngao
 */
public class TCPCall extends TCPClientServer<Call> {

    private SocketBase client;
    private ICallContract.IView views;
    private Packages<Call> packages;
    
    public TCPCall(ICallContract.IView views, Packages<Call> obj) {
        this.views = views;
        this.packages = obj;
    }

    @Override
    public void connect() throws Exception {

        try {
            client = new SocketBase(Common.Main.connection.getHost(),
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
    public void request(Packages<Call> obj) throws Exception {

        try {

//            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
//            objectOutputStream.write(createDataSend(obj));
            client.sendData(new Data(createDataSend(obj)));
//            objectOutputStream.writeObject(RSA.encrpytion(this.objectToJson(obj)));
            System.out.println("send ok");

//             new ObjectInputStream(client.getSocket().getInputStream()).readObject();
            //Packages<?> dataServer = (Packages<?>) objectInputStream.readObject();
            //views.showMeg(Common.getMessage(dataServer.getStatus()));
        } catch (Exception e) {
            Logger.getLogger(TCPClientServer.class.getName()).log(Level.SEVERE, null, e);
            views.showMeg("Một lỗi chưa xác định.");
//            throw e;
        } 
    }

    @Override
    public void run() {
        try {
            int size = 93;
            Audio audio = new Audio();
            byte[] dataAudio = audio.getDataFileAudio(views.getUrlAudio());
            for (int i = 0; i < dataAudio.length / size + 1; ++i) {
                connect();
                if ((i + 1) * size > dataAudio.length || i * size > dataAudio.length) {
                    packages.getData().setData(Arrays.copyOfRange(dataAudio, (i - 1) * size, dataAudio.length - 1));
                } else {
                    packages.getData().setData(Arrays.copyOfRange(dataAudio, i * size, (i + 1) * size));
                }
                packages.setAction(Common.STATUS_ACTION.CALL.ordinal());
                request(packages);
                Thread.sleep(10);
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPCall.class.getName()).log(Level.SEVERE, null, ex);
            views.showMeg("Một lỗi không xác định.");
        } catch (Exception ex) {
            Logger.getLogger(TCPCall.class.getName()).log(Level.SEVERE, null, ex);
            views.showMeg("Một lỗi không xác định.");
        }

    }

    /**
     * tạo dữ liệu được gửi đi là 1 byte đã được mã hóa RSA
     *
     * @param obj
     * @return
     */
    public byte[] createDataSend(Packages<Call> obj) throws Exception {
        return this.encrpytion(obj.getData().getDataSend(0));
    }

    /**
     * sử dụng method này để giải mã dữ liệu nhận được từ server và chuyển nó về
     * đối tượng mong muốn
     */
    @Override
    public Packages<Call> readByteResponse(byte[] data) throws Exception {
        return new Call().readDataSend(this.decryption(data));
    }

}
