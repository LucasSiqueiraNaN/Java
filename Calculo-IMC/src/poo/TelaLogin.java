import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaLogin extends JFrame{
    public TelaLogin(String nome){
        Container JANELA = getContentPane();
        JANELA.setLayout(null);

        setSize(600,400);
        setVisible(true);
        setResizable(false);

        JTextField email = new JTextField(20);
        JPasswordField senha = new JPasswordField(20);
        JButton btnEnviar = new JButton("Enviar");
        JButton btnCadastrar = new JButton("Cadastre-se");
        JLabel lblemail = new JLabel("Email");
        JLabel lblsenha = new JLabel("Senha");
        JLabel titulo = new JLabel("Login");

        Font fonte_label = new Font( "Arial", Font.PLAIN, 14);
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 22);
        lblemail.setFont(fonte_label);
        lblsenha.setFont(fonte_label);
        titulo.setFont(fonte_titulo);

        JANELA.add(titulo);
        JANELA.add(lblemail);
        JANELA.add(email);
        JANELA.add(lblsenha);
        JANELA.add(senha);
        JANELA.add(btnEnviar);
        JANELA.add(btnCadastrar);

        titulo.setBounds(270, 5, 120, 25);
        lblemail.setBounds(280, 40, 120, 25);
        lblsenha.setBounds(280, 100, 120, 25);
        email.setBounds(180, 60, 240, 25);
        senha.setBounds(180, 120, 240, 25);
        btnEnviar.setBounds(225, 180, 150, 25);
        btnCadastrar.setBounds(225, 220, 150, 30);

        Altera banco = new Altera();

        btnEnviar.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                System.out.println("NOME DO USUARIO "+nome);
                String email_paciente = email.getText();
                String senha_paciente = senha.getText();
                String nome_usuario = nome;
                Usuario usuario = new Usuario(email_paciente, senha_paciente);
                try {
                    int id_usuario = usuario.logar();
                    if(id_usuario == -1){
                        JOptionPane.showMessageDialog(JANELA, "Login Inexistente!");
                    }else{
                        TelaHome home = new TelaHome(id_usuario, nome_usuario);
                        dispose();
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnCadastrar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TelaCadastroPaciente form_cadastro = new TelaCadastroPaciente();
                dispose();
            }
        });
    }
    
    public static void main(String args[]){
        TelaLogin app = new TelaLogin("");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}