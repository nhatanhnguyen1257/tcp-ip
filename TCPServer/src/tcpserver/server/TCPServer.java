/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver.server;


import com.server.common.Common;
import com.server.work.GroupThreadCall;
import com.server.work.ThreadConnection;
import domain.Data;
import domain.SocketBase;
import domain.server.job.JobCall;
import domain.server.job.JobConnection;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngao
 */
public final class TCPServer implements ITCPServer {

    // create serverSocket object
    private ServerSocket serverSocket;
    private final int port = 9900;

    public TCPServer() throws Exception {
        open();
    }

    /**
     * open server
     *
     */
    @Override
    public void open() throws Exception {
        serverSocket = new ServerSocket(port);
        System.out.println("server is open on port " + port);
    }

    @Override
    public void run() {
        while (true) {
            SocketBase server = null;
            try {
                // accept connect from client and create Socket object
                server = new SocketBase( serverSocket.accept());

                Data d = server.readData();

                action(Integer.parseInt(new String(new byte[]{d.data[0]})), d.data, server);
                if (Integer.parseInt(new String(new byte[]{d.data[0]})) != Common.STATUS_ACTION.LOGIN.ordinal()) {
                    server.getSocket().close();
                } 

            } catch (IOException e) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, e);
            } 
            catch (Exception ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }


    /**
     * các hành động client mong muốn
     */
    private void action(int action, byte[] data, SocketBase socket)
            throws IOException {

        if (action == Common.STATUS_ACTION.LOGIN.ordinal()) {
            ThreadConnection.lstConnection.add(new JobConnection(data, socket));
        } else if (action == Common.STATUS_ACTION.LOGOUT.ordinal()) {

        } else if (action == Common.STATUS_ACTION.CALL.ordinal()) {
            GroupThreadCall.setData(new JobCall(data));
        } else if (action == Common.STATUS_ACTION.OFF.ordinal()) {

        } else if (action == Common.STATUS_ACTION.CONNECTION.ordinal()) {

        }
    }

}
