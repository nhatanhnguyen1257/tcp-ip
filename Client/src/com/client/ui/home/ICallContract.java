/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.ui.home;

import com.client.common.IPresenterBase;
import com.client.common.IViewBase;

/**
 *
 * @author ngao
 */
public interface ICallContract {
    
    
    public interface IView extends IViewBase{
        
        void showMeg(String meg);
        
        String getUrlAudio();
        
        String getToUser();
    }
    
    public interface IPresenter extends IPresenterBase<ICallContract.IView>{
        
        void call();
        
        
    }
}
