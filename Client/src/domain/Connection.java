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
public class Connection implements Serializable{

    static final long serialVersionUID = -346898L;
    
    private String host;

    private String point;

    private String user;

    public Connection(String point, String host, String userName) {
        this.point = point;
        this.host = host;
        this.user = userName;
    }
    
    public String getPoint() {
        return point;
    }

    public String getHost() {
        return host;
    }

    public String getUser() {
        return user;
    }

    
    
}
