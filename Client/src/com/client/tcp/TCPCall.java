/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.audio.Audio;
import com.client.common.Common;
import com.client.rsa.RSA;
import domain.Call;
import domain.Packages;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.client.ui.home.ICallContract;

/**
 *
 * @author ngao
 */
public class TCPCall extends TCPClientServer {

    private Socket client;
    private ICallContract.IView views;
    private Packages<Call> packages;

    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;

    public TCPCall(ICallContract.IView views, Packages<Call> obj) {
        this.views = views;
        this.packages = obj;
    }

    @Override
    public void connect() throws Exception {

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
    public void request(Packages obj) throws Exception {

        try {

            objectOutputStream = new ObjectOutputStream(client.getOutputStream());
            objectOutputStream.writeObject(this.objectToJson(obj));

//            objectOutputStream.writeObject(RSA.encrpytion(this.objectToJson(obj)));
            System.out.println("send ok");
            
            //objectInputStream = new ObjectInputStream(client.getInputStream());
            //Packages<?> dataServer = (Packages<?>) objectInputStream.readObject();

            //views.showMeg(Common.getMessage(dataServer.getStatus()));
        } catch (Exception e) {
            Logger.getLogger(TCPClientServer.class.getName()).log(Level.SEVERE, null, e);
            views.showMeg("Một lỗi chưa xác định.");
//            throw e;
        } finally {

            this.closeStream(objectInputStream);
            this.closeStream(objectOutputStream);
            this.closeSocket(client);
        }
    }

    @Override
    public void run() {
        try {
            int size = 30;
            Audio audio = new Audio();
            byte[] dataAudio = audio.getDataFileAudio(views.getUrlAudio());
            for (int i = 0; i < dataAudio.length / size + 1; ++i) {
                connect();
                if ((i + 1) * 30 > dataAudio.length || i * 30 > dataAudio.length) {
                    packages.getData().setData(Arrays.copyOfRange(dataAudio, (i - 1) * size, dataAudio.length - 1));
                } else {
                    packages.getData().setData(Arrays.copyOfRange(dataAudio, i * size, (i + 1) * size));
                }
                request(packages);
            }
        } catch (IOException ex) {
            Logger.getLogger(TCPCall.class.getName()).log(Level.SEVERE, null, ex);
            views.showMeg("Một lỗi không xác định.");
        } catch (Exception ex) {
            Logger.getLogger(TCPCall.class.getName()).log(Level.SEVERE, null, ex);
            views.showMeg("Một lỗi không xác định.");
        } 

    }

}
