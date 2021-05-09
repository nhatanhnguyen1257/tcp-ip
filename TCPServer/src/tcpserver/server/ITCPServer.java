/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 *
 * @author ngao
 */
public interface ITCPServer extends Runnable {
    
    public void open();
    public void closeSocket(Socket socket);
    public void closeStream(InputStream inputStream);
    public void closeStream(OutputStream outputStream);
}
