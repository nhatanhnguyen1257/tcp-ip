/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.common;

import domain.Connection;
import domain.Connection;

/**
 *
 * @author ngao
 */
public class Common {
    
    public static class Main {
        
        public static Connection connection = null;
        
    }
    
    
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
    
    public static String getMessage(int status) {
        String meg = "";
        if (status == Common.STATUS_SERVER.AUTH.ordinal()) {
            meg = "khng có quyền truy cập.";
        } else if (status == Common.STATUS_SERVER.ERROR.ordinal()) {
            meg =  "Sẩy ra lỗi.";
        } else if (status == Common.STATUS_SERVER.LOGIN_FALSE.ordinal()) {
            meg =  "Thông tin user sai.";
        } else if (status == Common.STATUS_SERVER.NOT_FOUND.ordinal()) {
            meg =  "không tồn tại";
        } else if (status == Common.STATUS_SERVER.LOCK.ordinal()) {
            meg =  "Đường dây bận";
        }
        
        return meg;
    }
    
}
