/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.work;

import com.server.rsa.RSA;
import com.sun.org.apache.xml.internal.utils.ThreadControllerWrapper;
import domain.server.job.JobCall;
import java.net.SocketAddress;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * sử dụng để thực hiện cuộc gọi
 *
 * @author ngao
 */
public class ThreadCall extends RSA implements Runnable {

    private int numbers;
    
    public ThreadCall(int number) {
        numbers = number;
    }
    
    
    
    @Override
    public void run() {
        while (true) {
            if (!GroupThreadCall.lstJobCall[numbers].isEmpty()) {
                JobCall jobCall = GroupThreadCall.lstJobCall[numbers].poll();
                if (jobCall != null) {
                    jobCall.job();
                }
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
