/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.common;

import domain.SocketBase;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ngao
 */
public class Common{

    public static enum STATUS_ACTION {
        LOGIN, // khi tạo connection tới server
        LOGOUT, // tắt app đi 
        CONNECTION, // tạo kết nối lần đầu khi thực hiện gọi
        CALL, // khi đang thực hiện gọi 
        OFF // khi ấn kết thúc cuộc gọi
        
    }

    public static enum STATUS_SERVER {
        OK, // trạng thái thành công
        ERROR, // khi có lỗi
        LOGIN_FALSE, // kết nối tới server nhưng user sai
        AUTH, // không có quyền/
        NOT_FOUND, // không tin thấy  user
        LOCK // USER đang bận
    }

    
    /** giữ liệu giả lập */
    public static class User {

        /** chứa danh sách socket lần đầu được kết nối thành công */
        public static Set<String> lstUserName = new HashSet<String>();

        /** chứa danh sách kết nối được tạo khi người sử dụng thực hiện kết nối*/
        public static HashMap<String, SocketBase> lstSocket = new HashMap<>();

        public static void createListSocket() {
            for (String user : lstUserName) {
                lstSocket.put(user, null);
            }
        }
        
        /** 
         * khi user thực hiện 1 cuộc gọi cho 1 user khác thì nó sẽ cần khóa lại chuyển trạng thái bận
         * 
         * khi này sẽ tương tự userA gọi cho userB và userB gọi cho userA.
         * 
         */
        public static HashMap<String, String> lockConnection = new HashMap<>();

        public static void createUser() {
            lstUserName.add("1");
            lstUserName.add("2");
            lstUserName.add("3");
            lstUserName.add("4");
            lstUserName.add("5");
            lstUserName.add("6");
        }
    }

}
