/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package form;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import jogovelha.JogoVelha;

/**
 *
 * @author rober
 */
public class FormGame extends javax.swing.JDialog {
    
    private String Valor;
    private Boolean MinhaVez; 
    private InputStream input;
    private OutputStream output;

    /**
     * Creates new form FormGame
     */
    public FormGame(java.awt.Frame parent, boolean modal, String valor) {
        super(parent, modal);
        initComponents();
        
        Valor = valor;
        
        JLabel label = new JLabel(new ImageIcon("resources/tabuleiro.jpg"));
        label.setLocation(0, 0);
        label.setSize(250, 250);
        add(label);
        
        lbl11.setLocation(6, 6);
        lbl11.setSize(74, 74);
        
        try {
            input = JogoVelha.socket.getInputStream();
            output = JogoVelha.socket.getOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(FormGame.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MinhaVez = false;
        
        if (valor.equals("X"))
            MinhaVez = true;
        else
            AguardarOponente();
    }
    
    private Boolean JogoAcabou(){
        String valorOponente = (Valor.equals("X") ? "O" : "X");
        
        if ((lbl11.getText().equals(Valor) && lbl12.getText().equals(Valor) && lbl13.getText().equals(Valor)) ||
           (lbl21.getText().equals(Valor) && lbl22.getText().equals(Valor) && lbl23.getText().equals(Valor)) ||
           (lbl31.getText().equals(Valor) && lbl32.getText().equals(Valor) && lbl33.getText().equals(Valor)) ||
           (lbl11.getText().equals(Valor) && lbl21.getText().equals(Valor) && lbl31.getText().equals(Valor)) ||
           (lbl12.getText().equals(Valor) && lbl22.getText().equals(Valor) && lbl32.getText().equals(Valor)) ||
           (lbl13.getText().equals(Valor) && lbl23.getText().equals(Valor) && lbl33.getText().equals(Valor)) ||
           (lbl11.getText().equals(Valor) && lbl22.getText().equals(Valor) && lbl33.getText().equals(Valor)) ||
           (lbl13.getText().equals(Valor) && lbl22.getText().equals(Valor) && lbl31.getText().equals(Valor)))
        {
            JOptionPane.showMessageDialog(null, "Você venceu!", "Parabéns", JOptionPane.INFORMATION_MESSAGE);
            return true;
            
        } else if ((lbl11.getText().equals(valorOponente) && lbl12.getText().equals(valorOponente) && lbl13.getText().equals(valorOponente)) ||
                  (lbl21.getText().equals(valorOponente) && lbl22.getText().equals(valorOponente) && lbl23.getText().equals(valorOponente)) ||
                  (lbl31.getText().equals(valorOponente) && lbl32.getText().equals(valorOponente) && lbl33.getText().equals(valorOponente)) ||
                  (lbl11.getText().equals(valorOponente) && lbl21.getText().equals(valorOponente) && lbl31.getText().equals(valorOponente)) ||
                  (lbl12.getText().equals(valorOponente) && lbl22.getText().equals(valorOponente) && lbl32.getText().equals(valorOponente)) ||
                  (lbl13.getText().equals(valorOponente) && lbl23.getText().equals(valorOponente) && lbl33.getText().equals(valorOponente)) ||
                  (lbl11.getText().equals(valorOponente) && lbl22.getText().equals(valorOponente) && lbl33.getText().equals(valorOponente)) ||
                  (lbl13.getText().equals(valorOponente) && lbl22.getText().equals(valorOponente) && lbl31.getText().equals(valorOponente)))
        {
            JOptionPane.showMessageDialog(null, "Você perdeu!", "Opsss", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } else if ((lbl11.getText() != "") && (lbl12.getText() != "") && (lbl13.getText() != "") &&
                  (lbl21.getText() != "") && (lbl22.getText() != "") && (lbl23.getText() != "") &&
                  (lbl31.getText() != "") && (lbl32.getText() != "") && (lbl33.getText() != ""))
        {
            JOptionPane.showMessageDialog(null, "Deu velha!", "", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        
        return false;
    }
    
    private void AguardarOponente(){
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            String jogada = reader.readLine();
            
            //JOptionPane.showMessageDialog(null, jogada.substring(0, 2), "", JOptionPane.INFORMATION_MESSAGE);
            //JOptionPane.showMessageDialog(null, jogada.substring(2), "", JOptionPane.INFORMATION_MESSAGE);
            
            MarcarEspaco(Integer.parseInt(jogada.substring(0, 2)), jogada.substring(2));
            
            if (!JogoAcabou())
                MinhaVez = true;
            
        } catch (IOException ex) {
            Logger.getLogger(FormGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void EnviarEspacoSelecionado(int posicao, String valor){
        if (!MinhaVez)
            return;
        
        MinhaVez = false;
        
        MarcarEspaco(posicao, valor);
        
        update(this.getGraphics());
        
        PrintStream writer = new PrintStream(output);

        writer.println(String.valueOf(posicao) + valor);
        
        if (!JogoAcabou())
            AguardarOponente();
    }
    
    private void MarcarEspaco(int posicao, String valor){
        JLabel label = null;
        
        switch (posicao){
            case 11:
                label = lbl11;
                break;
            case 12:
                label = lbl12;
                break;
            case 13:
                label = lbl13;
                break;
            case 21:
                label = lbl21;
                break;
            case 22:
                label = lbl22;
                break;
            case 23:
                label = lbl23;
                break;
            case 31:
                label = lbl31;
                break;
            case 32:
                label = lbl32;
                break;
            case 33:
                label = lbl33;
                break;
        }
        
        if (label != null){
            label.setText(valor);
            label.setEnabled(false);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl11 = new javax.swing.JLabel();
        lbl12 = new javax.swing.JLabel();
        lbl13 = new javax.swing.JLabel();
        lbl21 = new javax.swing.JLabel();
        lbl31 = new javax.swing.JLabel();
        lbl22 = new javax.swing.JLabel();
        lbl23 = new javax.swing.JLabel();
        lbl32 = new javax.swing.JLabel();
        lbl33 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setLocationByPlatform(true);
        setSize(new java.awt.Dimension(250, 250));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        lbl11.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl11MouseClicked(evt);
            }
        });

        lbl12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl12MouseClicked(evt);
            }
        });

        lbl13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl13MouseClicked(evt);
            }
        });

        lbl21.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl21MouseClicked(evt);
            }
        });

        lbl31.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl31MouseClicked(evt);
            }
        });

        lbl22.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl22MouseClicked(evt);
            }
        });

        lbl23.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl23MouseClicked(evt);
            }
        });

        lbl32.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl32MouseClicked(evt);
            }
        });

        lbl33.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl33MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lbl11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl33, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbl11, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl21, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbl31, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl12, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl13, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl22, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbl23, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(lbl32, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(172, 172, 172)
                        .addComponent(lbl33, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        lbl11.setVisible((evt.getX() >= 6) && (evt.getX() <= 80) && (evt.getY() >= 6) && (evt.getY() <= 80));
    }//GEN-LAST:event_formMouseClicked

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        setTitle(String.valueOf(evt.getX()) + ':' + String.valueOf(evt.getY()));
    }//GEN-LAST:event_formMouseMoved

    private void lbl11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl11MouseClicked
        EnviarEspacoSelecionado(11, Valor);
    }//GEN-LAST:event_lbl11MouseClicked

    private void lbl12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl12MouseClicked
        EnviarEspacoSelecionado(12, Valor);
    }//GEN-LAST:event_lbl12MouseClicked

    private void lbl13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl13MouseClicked
        EnviarEspacoSelecionado(13, Valor);
    }//GEN-LAST:event_lbl13MouseClicked

    private void lbl21MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl21MouseClicked
        EnviarEspacoSelecionado(21, Valor);
    }//GEN-LAST:event_lbl21MouseClicked

    private void lbl22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl22MouseClicked
        EnviarEspacoSelecionado(22, Valor);
    }//GEN-LAST:event_lbl22MouseClicked

    private void lbl23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl23MouseClicked
        EnviarEspacoSelecionado(23, Valor);
    }//GEN-LAST:event_lbl23MouseClicked

    private void lbl31MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl31MouseClicked
        EnviarEspacoSelecionado(31, Valor);
    }//GEN-LAST:event_lbl31MouseClicked

    private void lbl32MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl32MouseClicked
        EnviarEspacoSelecionado(32, Valor);
    }//GEN-LAST:event_lbl32MouseClicked

    private void lbl33MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl33MouseClicked
        EnviarEspacoSelecionado(33, Valor);
    }//GEN-LAST:event_lbl33MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormGame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormGame dialog = new FormGame(new javax.swing.JFrame(), true, "X");
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbl11;
    private javax.swing.JLabel lbl12;
    private javax.swing.JLabel lbl13;
    private javax.swing.JLabel lbl21;
    private javax.swing.JLabel lbl22;
    private javax.swing.JLabel lbl23;
    private javax.swing.JLabel lbl31;
    private javax.swing.JLabel lbl32;
    private javax.swing.JLabel lbl33;
    // End of variables declaration//GEN-END:variables
}
