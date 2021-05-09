/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.client.ui.home;

import com.client.audio.Audio;
import com.client.common.Common;
import com.client.tcp.TCPCall;
import domain.Call;
import domain.Packages;

/**
 *
 * @author ngao
 */
public class CallPresenter implements ICallContract.IPresenter {

    private ICallContract.IView views;

    @Override
    public void call() {
        if (views.getUrlAudio().length() <= 0) {
            views.showMeg("bạn cần nhập vào file âm thanh");
        } else if (views.getToUser().length() == 0) {
            views.showMeg("Bạn cần nhập vào người nhận.");
        } else {
            Packages<Call> packages = new Packages<Call>();
            Call call = new Call();
            call.setUser(Common.Main.connection.getUser());
            call.setTo(views.getToUser());
            packages.setData(call);
            packages.setAction(Common.STATUS_ACTION.CALL.ordinal());
            new Thread(new TCPCall(views, packages)).start();
        }
    }

    @Override
    public void setView(ICallContract.IView view) {
        views = view;
    }

}
