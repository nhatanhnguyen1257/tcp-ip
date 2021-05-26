/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 * sử dụng chứa dữ liệu dùng để gửi đi
 * @author ngao
 */
public class Data implements Serializable {
    
    static final long serialVersionUID = -8972398L;
    
    public byte[] data;
    
    public Data(){
    }
    
    public Data(byte []datas){
        this.data = datas;
    }
}
