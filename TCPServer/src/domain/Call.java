/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.server.common.Common;
import java.io.Serializable;
import java.util.Arrays;

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
    
    
    /**
     * đây là method tạo dữ liệu dạng byte để gửi đi
     *
     * do mã hóa RSA có độ dài 1024, nên dữ liệu mỗi lần gửi đi chỉ có thể dài
     *
     * tối đa 128 byte tức 28 ký tự
     *
     * với 1 byte đầu dùng xác định hành động là gì,
     * 1 byte tiếp theo xác định trạng thái trả lại sau khi gửi request thành công tới sever, 
     * 10 byte tiếp theo xác định thông tin người gửi,
     * 10 byte tiếp theo xác định thông tin người nhận được
     * cuộc gọi số byte còn lại là xác định dữ liệu muốn gửi đi
     *
     */
    public byte[] getDataSend(int status) {
        byte[] data = new byte[128];
        byte[] action = String.valueOf(Common.STATUS_ACTION.CALL.ordinal()).getBytes();
        byte[] user = this.user.getBytes();
        byte[] to = this.to.getBytes();
        
        data[0] = action[0];
        data[1] = String.valueOf(status).getBytes()[0];
        int size = user.length;
        int index = 2;
        for (int i = 0; i < size; i++) {
            data[index] = user[i];
            ++index;
        }

        size = to.length;
        index = 12;
        for (int i = 0; i < size; i++) {
            data[index] = to[i];
            ++index;
        }

        size = this.data.length;
        index = 23;
        for (int i = 0; i < size; i++) {
            data[index] = this.data[i];
            ++index;
        }

        return data;
    }

    
    /**
     * chuyển đổi dữ liệu nhận được từ server dạng byte ra đối tượng.
     * 
     * dữ liệu này là dữ liệu sau khi đã được giải mã
     */
    public Packages<Call> readDataSend(byte[] input) {
        Packages<Call> packages = new Packages<Call>();
        packages.setData(this);
        this.user = new String(Arrays.copyOfRange(input, 2, 12));
        this.to = new String(Arrays.copyOfRange(input, 12, 23));
        this.data = Arrays.copyOf(input, 22);
        
        byte []status = new byte[1];
        status[0] = input[1];
        
        packages.setStatus(Integer.parseInt(new String(status)));
        return packages;
    }
}
