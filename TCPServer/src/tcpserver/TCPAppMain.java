/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import com.server.common.Common;
import com.server.work.GroupThreadCall;
import com.server.work.ThreadConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import tcpserver.server.TCPServer;
import tcpserver.server.ITCPServer;

/**
 *
 * @author ngao
 */
public class TCPAppMain {

    public static void main(String[] args) {
        Common.User.createUser();
        Common.User.createListSocket();

        GroupThreadCall.initThreadWork();
        new Thread(new ThreadConnection()).start();

        ITCPServer serverTCP;
        try {
            serverTCP = new TCPServer();
            Thread thread = new Thread(serverTCP);
            thread.start();
        } catch (Exception ex) {
            Logger.getLogger(TCPAppMain.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(0);
        }

    }
}
