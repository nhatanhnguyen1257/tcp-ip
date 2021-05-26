/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import com.client.common.Common;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 *
 * @author ngao
 */
public class Connection implements Serializable {

    static final long serialVersionUID = -346898L;

    private String host;

    private String point;

    private String user;

    public Connection() {

    }

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

    /**
     * đây là method tạo dữ liệu dạng byte để gửi đi
     *
     * do mã hóa RSA có độ dài 1024.nên dữ liệu mỗi lần gửi đi chỉ có thể dài
     *  
     *  tối đa 117 byte tức 28 ký tự
     * 
     * với 1 byte đầu dùng xác định hành động là gì,
     * 1 byte tiếp theo xác định trạng thái trả lại sau khi gửi request thành công tới sever, 
     * 
     * 10 byte tiếp theo xác định thông tin người gửi
     *
     * @param status
     */
    public byte[] getDataSend(int status) {
        byte[] data = new byte[12];
        byte[] action = String.valueOf(Common.STATUS_ACTION.CONNECTION.ordinal()).getBytes();
        byte[] user = this.getUser().getBytes();

        data[0] = action[0];
        data[1] = String.valueOf(status).getBytes()[0];
        int size = user.length;
        int index = 2;
        for (int i = 0; i < size; i++) {
            data[index] = user[i];
            ++index;
        }

        return data;
    }

    /**
     * chuyển đổi dữ liệu nhận được từ server dạng byte ra đối tượng.
     *
     * dữ liệu này là dữ liệu sau khi đã được giải mã
     */
    public Packages<Connection> readDataSend(byte[] input) {
        Packages<Connection> packages = new Packages<Connection>();
        this.user = new String(Arrays.copyOfRange(input, 2, 12), StandardCharsets.UTF_8);
        byte[] status = new byte[1];
        status[0] = input[1];

        packages.setStatus(Integer.parseInt(new String(status)));
        return packages;
    }

}
