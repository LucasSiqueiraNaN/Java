import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaAlterarDados extends JFrame{
        public TelaAlterarDados(int id_usuario){
            Container JANELA = getContentPane();
            JANELA.setLayout(null);

            Usuario usuario = new Usuario();

            setSize(600, 400);
            setVisible(true);
            setResizable(false);

            String email_select;
            String senha_select;
            String nome_select;
            try {
                String[] dados = usuario.restagar_dados(id_usuario);
                email_select = dados[0];
                senha_select = dados[1];
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            try {
                nome_select = usuario.buscar_nome(id_usuario);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            JTextField nome = new JTextField(nome_select);
            JTextField email = new JTextField(email_select);
            JTextField senha = new JTextField(senha_select);
            JButton btnEnviar = new JButton("Enviar");
            JButton btnDeletar = new JButton("Deletar");
            JButton btnVoltar = new JButton("Voltar");
            JLabel titulo = new JLabel("Alterar dados");

            Font fonte_label = new Font("Arial", Font.PLAIN, 14);
            Font fonte_titulo = new Font("Arial", Font.PLAIN, 22);
            titulo.setFont(fonte_titulo);
            JANELA.add(titulo);
            JANELA.add(nome);
            JANELA.add(email);
            JANELA.add(senha);
            JANELA.add(btnEnviar);
            JANELA.add(btnDeletar);
            JANELA.add(btnVoltar);

            titulo.setBounds(250, 5, 250, 25);

            nome.setBounds(180, 60, 240, 25);
            email.setBounds(180, 120, 240, 25);
            senha.setBounds(180, 180, 240, 25);
            btnEnviar.setBounds(225, 240, 150, 25);
            btnDeletar.setBounds(225, 280, 150, 25);
            btnVoltar.setBounds(225, 310, 150, 25);

            Altera banco = new Altera();

            btnEnviar.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt) {
                    String nome_paciente = nome.getText();
                    String email_paciente = email.getText();
                    String senha_paciente = senha.getText();

                    try {
                        usuario.alterar_dados(nome_paciente, email_paciente, senha_paciente, id_usuario);
                        TelaHome home = new TelaHome(id_usuario, nome_paciente);
                        dispose();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            btnDeletar.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    try {
                        usuario.deletar_dados(id_usuario);
                        JOptionPane.showMessageDialog(JANELA, "Deletado com sucesso!","Deletando", JOptionPane.ERROR_MESSAGE);
                        TelaCadastroPaciente cadastro = new TelaCadastroPaciente();
                        dispose();

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });

            btnVoltar.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt){
                    TelaHome home = new TelaHome(id_usuario, nome_select);
                    dispose();
                }
            });
        }
    }