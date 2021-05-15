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
import domain.Packages;
import domain.SocketBase;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngao
 */
public class JobConnection extends JobServer {

    private Connection connection;
    private Socket sockets;

    public JobConnection(Packages<Connection> packages, Socket socket)
            throws JsonProcessingException, IOException {

        byte[] json = objectToJson(packages.getData()).getBytes();
        connection = objectToByte(json, new Connection());
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
                }
            } else {
                messageError();
            }
        }
    }

    private void mesageOK() {
        // confirm that file is received
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(this.sockets.getOutputStream());
            Packages<?> obj = new Packages<Object>();
            obj.setStatus(Common.STATUS_SERVER.OK.ordinal());
            obj.setAction(Common.STATUS_ACTION.LOGIN.ordinal());
            oos.writeObject(objectToJson(obj));
//            oos.writeObject(RSA.encrpytion(objectToJson(obj)));
//            oos.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
            this.closeStream(oos);
            this.closeSocket(sockets);
        } catch (Exception ex) {
            Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addSocket() throws IOException {
        SocketBase socket = Common.User.lstSocket.get(connection.getUser());
        if (socket == null) {
            Common.User.lstSocket.put(connection.getUser(), new SocketBase(this.sockets));
        } else if (!socket.getSocket().isClosed()) {
            try {
                socket.getSocket().close();
                Common.User.lstSocket.put(connection.getUser(), new SocketBase(this.sockets));
            } catch (IOException ex) {
                Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void messageError() {
// confirm that file is received
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(this.sockets.getOutputStream());
            Packages<?> obj = new Packages<Object>();
            obj.setStatus(Common.STATUS_SERVER.LOGIN_FALSE.ordinal());
            obj.setAction(Common.STATUS_ACTION.LOGIN.ordinal());
            oos.writeObject(objectToJson(obj));
//            oos.writeObject(RSA.encrpytion(objectToJson(obj)));
//            oos.writeObject(obj);
        } catch (IOException ex) {
            Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JobConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            this.closeStream(oos);
            this.closeSocket(sockets);
        }
    }

}
