/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.client.rsa.RSA;
import com.client.tcp.TCPConnection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author ngao
 */
public class SocketBase extends RSA {

    /**
     * socket giữ kết nối tcp/ip server và client
     */
    private Socket socket;

    /**
     * sử dụng để đẩy dữ liệu từ server về cho client
     */
    private ObjectOutputStream outSocket;

    private ObjectInputStream inputStream;

    public SocketBase(String host, int point) throws IOException {
        socket = new Socket(host, point);
        this.outSocket = new ObjectOutputStream(socket.getOutputStream());
        this.inputStream = new ObjectInputStream(socket.getInputStream());
    }

    public SocketBase(Socket socket) throws IOException {
        this.socket = socket;
        if (socket != null) {
            this.outSocket = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
        }
    }

    /**
     * sử dụng để gửi dữ liệu trao đổi server và client
     *
     * @param data
     * @throws java.io.IOException
     */
    public void sendData(Data data) throws IOException {

        if (this.outSocket != null) {
            this.outSocket.writeObject(data);
//             this.outSocket.writeObject(RSA.encrpytion(objectToJson(data)));
        }
    }

    /**
     * sử dụng để gửi dữ liệu trao đổi server và client
     *
     * @param data
     * @return 
     * @throws java.io.IOException
     * @throws java.lang.ClassNotFoundException
     */
    public Data readData() throws IOException, ClassNotFoundException, Exception {
        if (this.inputStream != null ) {
            Data data =  (Data) this.inputStream.readObject();
            data.data = decryption(data.data);
            return data;
        } 
        throw new Exception();
    }

    public Socket getSocket() {
        return socket;
    }

    public void getSocket(Socket sockets) {
        socket = sockets;
    }
    
    public void close() throws IOException {
        if (this.outSocket != null) {
            this.outSocket.close();
        }
        
        if (this.inputStream != null) {
            this.inputStream.close();
        }
        
        if (this.socket != null) {
            this.socket.close();
        }
    }

}
