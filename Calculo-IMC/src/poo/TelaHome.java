import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TelaHome extends JFrame{
    String nome_select;
        public TelaHome(int id_usuario, String nome_usuario){
            Container JANELA = getContentPane();
            JANELA.setLayout(null);

            setSize(600,400);
            setVisible(true);
            setResizable(false);

            Usuario usuario = new Usuario();
            try {
                nome_select = usuario.buscar_nome(id_usuario);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            JButton btnCalcularImc = new JButton("Calcular IMC");
            JButton btnEstatistica = new JButton("Estatísticas");
            JButton btnPerfil = new JButton("Perfil");
            JButton btnRelatorio = new JButton("Relatório");
            JButton btnSair = new JButton("Sair");
            btnSair.setOpaque(true);
            btnSair.setBackground(Color.RED);
            JLabel titulo = new JLabel("Home");
            JLabel titulo_nome = new JLabel("Paciente: "+nome_select);

            Font fonte_label = new Font( "Arial", Font.PLAIN, 20);
            Font fonte_titulo = new Font("Arial", Font.PLAIN, 24);
            titulo.setFont(fonte_titulo);
            titulo_nome.setFont(fonte_label);

            JANELA.add(titulo);
            JANELA.add(titulo_nome);
            JANELA.add(btnCalcularImc);
            JANELA.add(btnEstatistica);
            JANELA.add(btnPerfil);
            JANELA.add(btnRelatorio);
            JANELA.add(btnSair);

            titulo.setBounds(270, 5, 120, 25);
            titulo_nome.setBounds(230, 45, 500, 25 );

            btnPerfil.setBounds(225, 100, 150, 30);
            btnRelatorio.setBounds(225, 140, 150, 30);
            btnCalcularImc.setBounds(225, 180, 150, 30);
            btnEstatistica.setBounds(225, 220, 150, 30);
            btnSair.setBounds(225, 260, 150, 30);

            Altera banco = new Altera();

            btnCalcularImc.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent evt) {
                    TelaCalculoImc imc = new TelaCalculoImc(id_usuario);
                    dispose();
                }
            });

            btnPerfil.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent evt) {
                    TelaAlterarDados alterar = new TelaAlterarDados(id_usuario);
                    dispose();
                }
            });


            btnSair.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent evt){
                    TelaCadastroPaciente cadastro = new TelaCadastroPaciente();
                    dispose();
                }
            });

            btnEstatistica.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    try {
                        TelaPorcentagemImcs porcentagem = new TelaPorcentagemImcs(id_usuario, nome_usuario);
                        dispose();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });

            btnRelatorio.addMouseListener(new MouseAdapter(){
                public void mouseClicked(MouseEvent evt) {
                    try {
                        try {
                            usuario.gerar_relatorio();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

        }
    }