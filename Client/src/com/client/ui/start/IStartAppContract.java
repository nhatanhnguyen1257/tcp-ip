/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.ui.start;

import domain.Connection;
import com.client.common.IViewBase;
import com.client.common.IPresenterBase;

/**
 *
 * @author ngao
 */
public interface IStartAppContract {
    
    public interface View extends IViewBase {
        
        void showMeg(String message);
        
        void changePanel();
    }
    
    
    public interface Presenter extends IPresenterBase<IStartAppContract.View> {
        
        public void connection(Connection setting);
    }
    
}
