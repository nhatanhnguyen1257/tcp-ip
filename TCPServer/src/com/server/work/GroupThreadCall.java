/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.work;

import domain.server.job.JobCall;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Tạo ra nhiều thread để thực hiện cuộc gọi
 * 
 * @author ngao
 */
public class GroupThreadCall {

    private static int indexDeque = 0;
    private static int indexDequeMax;
    private static final int numberCore = 2;
    public static Deque<JobCall>[] lstJobCall;

    public static void initThreadWork() {
        lstJobCall = new ArrayDeque[numberCore * 2 - 2];
        indexDequeMax = numberCore * 2 - 2;
        for (int i = 0; i < numberCore * 2 - 2; ++i) {
            lstJobCall[i] = new ArrayDeque<>();
            new Thread(new ThreadCall(i)).start();
        }
    }
    
    public synchronized static void setData(JobCall jobCall) {
        if (jobCall != null){
            GroupThreadCall.lstJobCall[indexDeque].add(jobCall);
            indexDeque += 1 ;
        }
//        System.err.println(indexDeque);
        if (indexDeque >= indexDequeMax) {
            indexDeque = 0;
        }
    }
    
}
