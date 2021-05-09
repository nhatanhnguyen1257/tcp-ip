/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.tcp;

import com.client.rsa.RSA;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    /**
     * thực hiện chuyển từ object sang json
     * @param packages
     * @return 
     */
    protected String objectToJson(Packages<T> packages) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(packages);
    }
    
    
        /**
     * thực hiện chuyển từ json sang Object
     * @param packages
     * @return 
     */
    protected Packages<T> jsonToObject(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Packages.class);
    }

    
    /**
     * chuyển từ object về byte
     */
    protected byte[] objectToByte(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
    
        /**
     * chuyển từ byte về object
     */
    protected <T> T byetToObject(byte []object, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, (Class<T>) t.getClass());
    }
}
