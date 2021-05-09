/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.server.job;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.server.common.Common;
import com.server.rsa.RSA;
import domain.Connection;
import domain.Call;
import domain.Packages;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * đang thực hiện cuộc gọi
 *
 * @author ngao
 */
public class JobCall extends JobServer {

    private Call objCall;

    public JobCall(Packages<Call> packages)
            throws JsonProcessingException, IOException {

        byte[] json = objectToJson(packages.getData()).getBytes();
        this.objCall = objectToByte(json, new Call());
    }

    @Override
    public void job() {
        if (this.objCall != null) {
            Socket socket = Common.User.lstSocket.get(this.objCall.to);
            if (socket != null && !socket.isClosed()) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Packages<Call> obj = new Packages<>();
                    Call call = new Call();
                    call.to = objCall.user;
                    call.user = objCall.to;
                    call.data = objCall.data;
                    obj.setData(call);
                    obj.setStatus(Common.STATUS_SERVER.OK.ordinal());
                    obj.setAction(Common.STATUS_ACTION.CALL.ordinal());
                    
                    oos.writeObject(RSA.encrpytion(objectToJson(obj)));
                    
                } catch (IOException ex) {
                    Logger.getLogger(JobCall.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(JobCall.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    /**
     * kiểm tra xem user gửi resquest có tồn tại hay không
     */
    private boolean isUser() {
        return Common.User.lstUserName.contains(this.objCall.user);
    }

    /**
     * kiểm tra xem user nhận tin nhắn có tồn tại hay không
     */
    private boolean isToUser() {
        return Common.User.lstUserName.contains(this.objCall.to);
    }

    /**
     * kiểm tra xem user nhận tin nhắn có đang trực tuyến hay không nếu đang
     * trực tuyến thì trả về true nếu không trả về false.
     */
    private boolean isUserOnline() {
        return Common.User.lstSocket.get(this.objCall.to) != null ? true : false;
    }

}
