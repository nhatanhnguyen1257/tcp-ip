/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.rsa.RSA;
import domain.Packages;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author ngao
 */
public abstract class TCPClientServer<T> extends RSA implements Runnable {

    public abstract void connect() throws Exception;

    public abstract void request(Packages<T> obj) throws Exception;
    
    public abstract byte[] createDataSend(Packages<T> obj) throws Exception;
    
    public abstract Packages<T> readByteResponse(byte[] data) throws Exception;

    /**
     * close socket
     *
     */
    protected void closeSocket(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * close input stream
     *
     */
    protected void closeStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * close output stream
     *
     */
    protected void closeStream(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }    
}
