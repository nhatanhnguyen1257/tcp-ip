/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.server.job;

import com.server.common.Common;
import domain.Connection;
import domain.Data;
import domain.Packages;
import domain.SocketBase;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngao
 */
public class JobConnection extends JobServer<Connection> {

    private Connection connection;
    private SocketBase sockets;

    public JobConnection(byte[] data, SocketBase socket) {
        connection = new Connection().readDataSend(data).getData();
        sockets = socket;
    }

    @Override
    public void job() {
        if (this.connection != null && this.sockets != null) {
            if (this.hasUser(this.connection.getUser())) {
                try {
                    mesageOK();
                    addSocket();
                } catch (IOException ex) {
                    Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                try {
                    messageError();
                } catch (Exception ex) {
                    Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void mesageOK() throws Exception {
        Packages<Connection> obj = new Packages<Connection>();
        obj.setData(connection);
        obj.setStatus(Common.STATUS_SERVER.OK.ordinal());
        obj.setAction(Common.STATUS_ACTION.LOGIN.ordinal());
        Data data = new Data();
        try {
            data.data = this.encrpytion(obj.getData().getDataSend(obj.getStatus()));
            this.sockets.sendData(data);
            return;
            //oos.write(this.encrpytion(obj.getData().getDataSend(Common.STATUS_SERVER.OK.ordinal())));
        } catch (Exception ex) {
            Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        data.data = this.encrpytion(obj.getData().getDataSend(obj.getStatus()));
        this.sockets.sendData(data);
        this.sockets.close();
    }

    private void addSocket() throws IOException {
        SocketBase socket = Common.User.lstSocket.get(connection.getUser());
        if (socket == null) {
            Common.User.lstSocket.put(connection.getUser(), this.sockets);
        } else if (!socket.getSocket().isClosed()) {
            try {
                socket.getSocket().close();
                Common.User.lstSocket.put(connection.getUser(), this.sockets);
            } catch (IOException ex) {
                Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void messageError() throws Exception {

        Packages<Connection> obj = new Packages<>();
        obj.setData(connection);
        obj.setStatus(Common.STATUS_SERVER.LOGIN_FALSE.ordinal());
        obj.setAction(Common.STATUS_ACTION.LOGIN.ordinal());
        Data data = new Data();
        try {
            data.data = this.encrpytion(obj.getData().getDataSend(obj.getStatus()));
            this.sockets.sendData(data);

        } catch (IOException ex) {
            Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
            data.data = this.encrpytion(obj.getData().getDataSend(obj.getStatus()));
            this.sockets.sendData(data);
        } catch (Exception ex) {
            Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
            data.data = this.encrpytion(obj.getData().getDataSend(obj.getStatus()));
            this.sockets.sendData(data);
        } finally {
            this.sockets.close();
        }
    }

    @Override
    public byte[] createDataSend(Packages<Connection> obj) throws Exception {
        return this.encrpytion(obj.getData().getDataSend(obj.getStatus()));
    }

    @Override
    public Packages<Connection> readByteResponse(byte[] data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
