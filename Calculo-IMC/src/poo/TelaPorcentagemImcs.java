import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class TelaPorcentagemImcs extends JFrame {
    private Altera banco;
    TelaPorcentagemImcs(int id_usuario, String nome_usuario) throws SQLException {
        this.banco = new Altera();
        Container JANELA = getContentPane();
        JANELA.setLayout(null);

        setSize(600,400);
        setVisible(true);
        setResizable(false);
        Font fonte_label = new Font( "Arial", Font.PLAIN, 14);
        Font font_titulo = new Font("Arial", Font.PLAIN, 22);
        JLabel titulo = new JLabel("Porcentagem dos IMC'S ");
        JButton btnVoltar = new JButton("Voltar");

        float qtd = 0;
        float qtd_c = 0;
        float[] porcentagem = new float[6];
        this.banco.conectar();
        String sql = "SELECT COUNT(*) AS qtd FROM imc";
        this.banco.setRs(this.banco.getSt().executeQuery(sql));
        if(this.banco.getRs().next()){
            qtd = this.banco.getRs().getInt("qtd");
        }

        String[] classificacoes = {"Magreza", "Saudavel", "Sobrepeso", "Obesidade Grau I", "Obesidade Grau II", "Obesidade Grau III"};
        String sql2;
        int x = 70;
        JANELA.add(titulo);
        JANELA.add(btnVoltar);
        titulo.setBounds(180, 20, 300, 25);
        titulo.setFont(font_titulo);
        btnVoltar.setBounds(225, 220, 150, 25);

        for(int i = 0; i <= classificacoes.length - 1; i++){
            sql2 = "SELECT COUNT(*) AS quantidade FROM imc WHERE classificacao = '"+classificacoes[i]+"'";
            this.banco.setRs(this.banco.getSt().executeQuery(sql2));
            if(this.banco.getRs().next()){
                qtd_c = this.banco.getRs().getInt("quantidade");
                porcentagem[i] = (qtd_c / qtd) * 100;

                JLabel lblimc = new JLabel();
                lblimc.setText(classificacoes[i] +" = "+porcentagem[i]+"%");
                JANELA.add(lblimc);
                lblimc.setFont(fonte_label);
                lblimc.setBounds(225, x, 300, 25);
                x += 20;
            }
        }

        btnVoltar.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                TelaHome home = new TelaHome(id_usuario, nome_usuario);
                dispose();
            }
        });

    }

    public Altera getBanco() {
        return banco;
    }

    public void setBanco(Altera banco) {
        this.banco = banco;
    }

}