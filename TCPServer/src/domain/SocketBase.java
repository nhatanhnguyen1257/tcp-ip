/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.rsa.RSA;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ngao
 */
public class SocketBase { // extends RSA {
    
    /** socket giữ kết nối tcp/ip server và client */
    private Socket socket;
    
    /** sử dụng để đẩy dữ liệu từ server về cho client*/
    private ObjectOutputStream outSocket;

    public SocketBase(Socket socket) throws IOException {
        this.socket = socket;
        if (socket != null) {
            this.outSocket = new ObjectOutputStream(socket.getOutputStream());
        }
    }
    
    /** sử dụng để gửi dữ liệu trao đổi server và client */
    public void sendData(Packages<?> data ) 
            throws JsonProcessingException, IOException, Exception {
        
        if (this.outSocket != null) {
             this.outSocket.writeObject(objectToJson(data));
//             this.outSocket.writeObject(RSA.encrpytion(objectToJson(data)));
        }
    }
    
     /**
     * chuyển từ json về object
     */
    public <T> Packages<T> jsonToObject(String json, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new Packages<T>().getClass());
    }

    /**
     * thực hiện chuyển từ object sang json
     *
     * @param packages
     * @return
     */
    public <T> String objectToJson(Object packages) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(packages);
    }
    
    /**
     * chuyển từ object về byte
     */
    public byte[] objectToByte(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsBytes(object);
    }
    
        /**
     * chuyển từ byte về object
     */
    public <T> T objectToByte(byte []object, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(object, (Class<T>) t.getClass());
    }

    public Socket getSocket() {
        return socket;
    }
    
    
}
