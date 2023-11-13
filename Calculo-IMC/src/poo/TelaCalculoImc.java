import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.logging.Level;

public class TelaCalculoImc extends JFrame{
    public TelaCalculoImc(int id_usuario){
        Container JANELA = getContentPane();
        JANELA.setLayout(null);

        setSize(600,400);
        setVisible(true);
        setResizable(false);

        Usuario usuario = new Usuario();

        JTextField altura = new JTextField(20);
        JTextField peso = new JTextField(20);
        JButton btnEnviar = new JButton("Enviar");
        JButton btnCadastrar = new JButton("Cadastre-se");
        JButton btnVoltar = new JButton("Voltar");
        JLabel lblemail = new JLabel("Altura");
        JLabel lblsenha = new JLabel("Peso");
        JLabel titulo = new JLabel("Cálculo IMC");

        Font fonte_label = new Font( "Arial", Font.PLAIN, 14);
        Font fonte_titulo = new Font("Arial", Font.PLAIN, 22);
        lblemail.setFont(fonte_label);
        lblsenha.setFont(fonte_label);
        titulo.setFont(fonte_titulo);

        JLabel lblimc = new JLabel();
        JLabel lblclassificacao = new JLabel();
        
        JANELA.add(titulo);
        JANELA.add(altura);
        JANELA.add(lblemail);
        JANELA.add(peso);
        JANELA.add(lblsenha);
        JANELA.add(btnEnviar);
        JANELA.add(btnCadastrar);
        JANELA.add(btnVoltar);
        JANELA.add(lblimc);
        JANELA.add(lblclassificacao);
        
        titulo.setBounds(240, 5, 150, 25);
        lblemail.setBounds(280, 40, 120, 25);
        lblsenha.setBounds(280, 100, 120, 25);
        altura.setBounds(180, 60, 240, 25);
        peso.setBounds(180, 120, 240, 25);
        btnEnviar.setBounds(225, 180, 150, 25);
        btnVoltar.setBounds(225, 220, 150, 25);

        btnEnviar.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent evt) {
                String altura_paciente = altura.getText();
                String peso_paciente = peso.getText();
//                try {
                    double altura_do_paciente = Double.parseDouble(altura_paciente);
                    double peso_do_paciente = Double.parseDouble(peso_paciente);

                    Usuario usuario = new Usuario(altura_do_paciente, peso_do_paciente);
                    try {
//                    System.out.println("ID DA FUN IMC "+id_usuario);
                        usuario.inserir_peso_altura(id_usuario);
                        usuario.inserir_imc(id_usuario);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);

                    }

                    lblimc.setText(String.format("IMC = %.2f", usuario.getImc_p()));
                    lblclassificacao.setText("Classificação: "+usuario.getClassificacao_p());
                    
                    lblimc.setFont(fonte_label);
                    lblclassificacao.setFont(fonte_label);
                    lblimc.setBounds(225, 260, 150, 25);
                    lblclassificacao.setBounds(225, 280, 300, 25);
            }
        });

        btnVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TelaHome home = new TelaHome(id_usuario, usuario.getNome());
                dispose();
            }
        });
    }
}