/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.server.job;

import domain.Cancel;
import com.server.common.Common;
import domain.Packages;

/**
 *
 * @author ngao
 */
public class JobOutConnection extends JobServer {

    private Cancel cancels;

    public JobOutConnection(Packages<Cancel> packages) {
        this.cancels = packages.getData();
    }
    
    
    @Override
    public void job() {
        removeSocket();
    }
    
    private void removeSocket(){
        if (cancels != null && cancels.user != null)
            this.closeSocket(Common.User.lstSocket.get(cancels.user).getSocket());
    }

    @Override
    public byte[] createDataSend(Packages obj) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Packages readByteResponse(byte[] data) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
