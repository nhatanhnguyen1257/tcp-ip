/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.audio.Audio;
import com.client.rsa.RSA;
import domain.Call;
import domain.Packages;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * sử dụng để nhận data từ server gửi xuống.
 *
 * @author ngao
 */
public class TCPAPP extends TCPClientServer implements Runnable {

    @Override
    public void run() {
        ObjectInputStream ois = null;
        Audio audio = new Audio();
        while (true) {
            Packages<Call> packages = null;
            if (TCPConnection.client != null && !TCPConnection.client.isClosed()) {
                try {
                    ois = new ObjectInputStream(TCPConnection.client.getInputStream());
                    String data = (String) ois.readObject();
                    packages = (Packages<Call>) jsonToObject(RSA.decryption(data));

                    byte[] json = objectToByte(packages.getData());
                    Call call = (Call) byetToObject(json, new Call());

                    audio.playAudio(call.data);
                } catch (IOException ex) {
                    Logger.getLogger(TCPAPP.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TCPAPP.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(TCPAPP.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    public void connect() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void request(Packages obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
