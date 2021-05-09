/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.ui.main;

import com.client.common.IViewBase;
import javax.swing.JPanel;

/**
 *
 * @author ngao
 */
public interface IMainContract {
    
    public interface IView extends IViewBase{
        
        void initView();
   
        void showError();
        
        void changePanel(JPanel newPanel);
        
    }
    
    public interface IPresenter {
        
        
        
    }
    
}
