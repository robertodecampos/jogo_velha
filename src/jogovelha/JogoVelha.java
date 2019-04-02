/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogovelha;

import form.FormPrincipal;
import java.net.UnknownHostException;

/**
 *
 * @author rober
 */
public class JogoVelha {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try{
            FormPrincipal frmPrincipal = new FormPrincipal(null, false);
            frmPrincipal.setVisible(true);
            System.out.println("exibido");
        } catch (UnknownHostException erro){
            System.out.println(erro.getMessage());
        }
    }
    
}
