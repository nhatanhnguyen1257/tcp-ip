/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.common.Common;
import com.server.rsa.RSA;
import com.server.work.GroupThreadCall;
import com.server.work.ThreadConnection;
import domain.Connection;
import domain.Call;
import domain.Packages;
import domain.server.job.JobCall;
import domain.server.job.JobConnection;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ngao
 */
public final class TCPServer extends RSA implements ITCPServer {

    // create serverSocket object
    private ServerSocket serverSocket;
    private int port = 9900;

    public TCPServer() {
        open();
    }

    /**
     * open server
     *
     */
    @Override
    public void open() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("server is open on port " + port);
        } catch (IOException e) {
             Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * close socket
     *
     * @param socket
     * @throws java.io.IOException
     */
    @Override
    public void closeSocket(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
             Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * close input stream
     *
     * @param inputStream
     */
    @Override
    public void closeStream(InputStream inputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException ex) {
             Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
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

    @Override
    public void run() {
        while (true) {
            Socket server = null;
            DataInputStream dataFromClient = null;
            ObjectInputStream objectInput = null;
            ObjectOutputStream objectOutput = null;
            try {
                // accept connect from client and create Socket object
                server = serverSocket.accept();

                System.out.println("connected to " + server.getRemoteSocketAddress());
                // get greeting from client
//                dataFromClient = new DataInputStream(server.getInputStream());
//                System.out.println(dataFromClient.readUTF());
                // receive file info
                objectInput = new ObjectInputStream(server.getInputStream());
                String data = (String) objectInput.readObject();
                System.out.println(RSA.decryption(data));
                Packages<?> packageClient = jsonToObject(RSA.decryption(data), new Object());
                action(packageClient.getAction(), packageClient, server);
                
                
                if (packageClient.getAction() != Common.STATUS_ACTION.LOGIN.ordinal()) {
                    closeStream(objectInput);
                    closeStream(objectOutput);
                    closeStream(dataFromClient);
                    closeSocket(server);
                }

            } catch (IOException e) {
                 Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, e);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
            } finally {

//                closeStream(objectInput);
//                closeStream(objectOutput);
//                closeStream(dataFromClient);
//                // close session
//                closeSocket(server);
            }
        }
    }

    /**
     * chuyển từ json về object
     */
    private <T> Packages<T> jsonToObject(String json, T t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new Packages<T>().getClass());
    }
    
       /**
     * thực hiện chuyển từ object sang json
     * @param packages
     * @return 
     */
    private <T> String objectToJson(Packages<T> packages) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(packages);
    }
    
    /**
     * các hành động client mong muốn
     */
    private void action(int action, Packages<?> packages, Socket socket) 
                    throws JsonProcessingException, IOException {
        
        if (action == Common.STATUS_ACTION.LOGIN.ordinal()) {
            String json = objectToJson(packages);
            Packages<Connection> packages1 = jsonToObject(json, new Connection());
            ThreadConnection.lstConnection.add(new JobConnection(packages1, socket));
        } else if (action == Common.STATUS_ACTION.LOGOUT.ordinal()) {

        } else if (action == Common.STATUS_ACTION.CALL.ordinal()) {
            GroupThreadCall.setData(new JobCall((Packages<Call>) packages));
        } else if (action == Common.STATUS_ACTION.OFF.ordinal()) {

        } else if (action == Common.STATUS_ACTION.CONNECTION.ordinal()) {

        }
    }

}
