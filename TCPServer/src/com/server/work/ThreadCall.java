/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.work;

import com.server.rsa.RSA;
import domain.server.job.JobCall;
import java.util.Deque;

/**
 * sử dụng để thực hiện cuộc gọi
 *
 * @author ngao
 */
public class ThreadCall extends RSA implements Runnable {

    private Deque<JobCall> lstJobCalls;

    public void setListData(Deque<JobCall> lstJobCall) {
        this.lstJobCalls = lstJobCall;
    }

    public void addDataToListData(JobCall jobCall) {
        if (jobCall == null) {
            return;
        }
        lstJobCalls.add(jobCall);
    }

    @Override
    public void run() {
        while (true) {
            JobCall jobCall = lstJobCalls.poll();
            if (jobCall != null) {
                jobCall.job();
            }
        }
    }

}
