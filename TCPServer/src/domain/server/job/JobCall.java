/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.server.job;

import com.server.common.Common;
import domain.Call;
import domain.Data;
import domain.Packages;
import domain.SocketBase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * đang thực hiện cuộc gọi
 *
 * @author ngao
 */
public class JobCall<Connection> extends JobServer {

    private Call objCall;

    public JobCall(byte[] data) {
        this.objCall = new Call().readDataSend(data).getData();
    }

    @Override
    public void job() {
        if (this.objCall != null) {
//            System.out.println(String.valueOf("1")); 
            SocketBase socket = Common.User.lstSocket.get(this.objCall.to);
            if (socket != null && socket.getSocket() != null && !socket.getSocket().isClosed()) {
                try {

                    Packages<Call> obj = new Packages<>();
                    Call call = new Call();
                    call.to = objCall.user;
                    call.user = objCall.to;
                    call.data = objCall.data;
                    obj.setData(call);
                    obj.setStatus(Common.STATUS_SERVER.OK.ordinal());
                    obj.setAction(Common.STATUS_ACTION.CALL.ordinal());

                    Data data = new Data();
                    data.data = encrpytion(obj.getData().getDataSend(Common.STATUS_SERVER.OK.ordinal()));
                    socket.sendData(data);

                    obj = null;
                    data = null;
                } catch (IOException ex) {
                    Logger.getLogger(JobCall.class.getName()).log(Level.SEVERE, null, ex);
//                    try {
//                        Data data = new Data();
//                        data.data = new Call().getDataSend(Common.STATUS_SERVER.ERROR.ordinal());
//                        socket.sendData(data);
//                        data = null;
//                    } catch (Exception ex1) {
//                        Logger.getLogger(JobCall.class.getName()).log(Level.SEVERE, null, ex1);
//                    }
                } catch (Exception ex) {
                    Logger.getLogger(JobCall.class.getName()).log(Level.SEVERE, null, ex);
//                    try {
//                        Data data = new Data();
//                        data.data = new Call().getDataSend(Common.STATUS_SERVER.ERROR.ordinal());
//                        socket.sendData(data);
//                        data = null;
//                    } catch (Exception ex1) {
//                        Logger.getLogger(JobCall.class.getName()).log(Level.SEVERE, null, ex1);
//                    }
                } finally{
                    try {
                        if (socket != null)
                            socket.close();
                    } catch (IOException ex) {
                        Logger.getLogger(JobCall.class.getName()).log(Level.SEVERE, null, ex);
                    }
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

    @Override
    public byte[] createDataSend(Packages obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Packages readByteResponse(byte[] data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
