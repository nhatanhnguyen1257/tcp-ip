/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver.server;


/**
 *
 * @author ngao
 */
public interface ITCPServer extends Runnable {
    
    public void open() throws Exception;
}
