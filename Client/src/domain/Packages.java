/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 *
 * @author ngao
 */
public class Packages<T> implements Serializable {

    static final long serialVersionUID = -82739L;

    /**
     * mã hành động
     */
    private int action;

    /**
     * trạng thái
     */
    private int status;

    /**
     * dữ liệu từ client gửi nên server và dữ liệu này sẽ được mã hóa
     */
    private T data;

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
