/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.work;

import domain.Call;
import domain.server.job.JobServer;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * sử dụng cho việc thực hiện connection và hủy kết nối tới server
 * 
 * @author ngao
 */
public class ThreadConnection implements Runnable {

    public static Deque<JobServer> lstConnection = new ArrayDeque<JobServer>();

    @Override
    public void run() {
        while (true) {            
            if(!lstConnection.isEmpty()) {
                lstConnection.poll().job();
            }
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(ThreadConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
