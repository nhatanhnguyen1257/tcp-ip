/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.auth;

import com.server.common.Common;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author ngao
 */
public class ServerAuth implements IServerAuth{

    
    @Override
    public boolean checkUser(String user) {
        return Common.User.lstUserName.contains(user);
    }
    
}
