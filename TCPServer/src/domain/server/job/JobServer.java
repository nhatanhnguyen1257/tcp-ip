/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.server.job;

import com.server.common.Common;
import domain.*;
import com.server.rsa.RSA;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Iterator;

/**
 *
 * @author ngao
 */
public abstract class JobServer<T> extends RSA {

    /**
     * công viêc tương ứng với từng object
     */
    public abstract void job();

    public abstract byte[] createDataSend(Packages<T> obj) throws Exception;

    public abstract Packages<T> readByteResponse(byte[] data) throws Exception;

    /**
     * trả về socket tương ứng với từng user socket này sử dụng để gửi tin nhắn
     * tới user. nếu user này đang connection
     */
    public SocketBase findSocket(String user) {
        return Common.User.lstSocket.get(user);
    }

    /**
     * kiểm tra tồn tại của user
     * @param user
     * @return 
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

}
