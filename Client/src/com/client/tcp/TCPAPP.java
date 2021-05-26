/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.audio.Audio;
import domain.Call;
import domain.Data;
import domain.Packages;
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
        Audio audio = new Audio();
        while (true) {
            if (TCPConnection.client != null && !TCPConnection.client.getSocket().isClosed()) {
                try {
                    Thread.sleep(10);
                    Data data = TCPConnection.client.readData();
                    
                    audio.playAudio(new Call().readDataSend(data.data).getData().data);
                }catch (Exception ex) {
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

    @Override
    public byte[] createDataSend(Packages obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Packages readByteResponse(byte[] data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
