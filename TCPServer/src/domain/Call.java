/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author ngao
 */
public class Call implements Serializable {

    static final long serialVersionUID = -898L;
    
    /** tài khoản gửi tin nhắn */
    public String user;
    
    /** tài khoản nhận tin nhắn */
    public String to;
    
    /** nội dung tin nhắn dạng array byte đã được mã hóa RSA*/
    public byte []data;
}
