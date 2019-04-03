/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha;

import form.FormPrincipal;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rober
 */
public class JogoVelha {
    
    public static ServerSocket server;
    public static Socket socket;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        FormPrincipal frmPrincipal = new FormPrincipal(null, false);
        frmPrincipal.setVisible(true);
    }
    
}
