/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.server.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.common.Common;
import domain.*;
import com.server.rsa.RSA;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author ngao
 */
public abstract class JobServer extends RSA {

    /**
     * công viêc tương ứng với từng object
     */
    public abstract void job();

    /**
     * trả về socket tương ứng với từng user socket này sử dụng để gửi tin nhắn
     * tới user. nếu user này đang connection
     */
    public Socket findSocket(String user) {
        return Common.User.lstSocket.get(user);
    }

    /**
     * kiểm tra tồn tại của user
     */
    public boolean hasUser(String user) {
        return Common.User.lstUserName.contains(user);
    }

    /**
     * close socket
     *
     */
    public void closeSocket(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * close output stream
     *
     */
    public void closeStream(OutputStream outputStream) {
        try {
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * chuyển từ json về object
     */
    protected <T> Packages<T> jsonToObject(String json, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new Packages<T>().getClass());
    }

    /**
     * thực hiện chuyển từ object sang json
     *
     * @param packages
     * @return
     */
    protected <T> String objectToJson(Object packages) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(packages);
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
    protected <T> T objectToByte(byte []object, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, (Class<T>) t.getClass());
    }
}
