import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.sql.Statement;

public class Usuario extends JFrame {
    
    private int id;
    private String nome;
    private String email;
    private String senha;
    private double altura;
    private double peso;
    private Altera banco;
    private double imc_p;
    private String classificacao_p;


    Usuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.banco = new Altera();
    }

    Usuario(String email, String senha){
        this.email = email;
        this.senha = senha;
        this.banco = new Altera();
    }

    Usuario(double altura, double peso){
        this.peso = peso;
        this.altura = altura;
        this.banco = new Altera();
    }
    Usuario(){
        this.banco = new Altera();
    }

    int id_usuario;
    double imc;
    String classificacao;
    String nome_select;
    String email_select;
    String senha_select;

    public String cadastrar() throws SQLException {
        this.banco.conectar();
        String sql = "INSERT INTO paciente (nome) VALUE ('"+this.nome+"')";
        this.banco.getSt().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        this.banco.setRs(this.banco.getSt().getGeneratedKeys());

        if (this.banco.getRs().next())
            setId(this.banco.getRs().getInt(1));
        String operacao = log_sql(sql);
        Log log = new Log(operacao, getId());

        String sql2 = "INSERT INTO login (email, senha, id_usuario) VALUE ('"+this.email+"', '"+this.senha+"', '"+getId()+"')";
        this.banco.getSt().executeUpdate(sql2, Statement.RETURN_GENERATED_KEYS);
        System.out.println(this.email);
        setNome(this.nome);

        return this.nome;
    }

    public String log_sql(String sql){
        String[] clasula_sql = sql.split(" ");
        System.out.println(clasula_sql[0]);
        return clasula_sql[0];
    }
    public String buscar_nome(int id_usuario) throws SQLException{
        this.banco.conectar();
        String sql = "SELECT nome FROM paciente WHERE id = '"+id_usuario+"'";
        this.banco.setRs(this.banco.getSt().executeQuery(sql));
        Log log = new Log(log_sql(sql), id_usuario);

        if(this.banco.getRs().next()){
            nome_select = this.banco.getRs().getString("nome");
        }
        System.out.println(nome_select);
        return nome_select;
    }
    public int logar() throws SQLException {
        this.banco.conectar();
        setEmail(email);
        setSenha(senha);
        setNome(nome);
        String sql = "SELECT * FROM login WHERE email = '" + getEmail() + "' AND senha = '" + getSenha() + "'";
        this.banco.setRs(this.banco.getSt().executeQuery(sql));

        if (this.banco.getRs().next()) {
//            System.out.println("SUCESSO");
//            System.out.println(getEmail());
//            System.out.println(getSenha());
            setId(this.banco.getRs().getInt(4));//ID CERTO
            id_usuario = getId();

//            String operacao = log_sql(sql);
            Log log = new Log(log_sql(sql), id_usuario);

            return this.id;
        }
        else {
            return -1;
        }
    }
    public void inserir_peso_altura(int id_usuario) throws SQLException {
        this.banco.conectar();
        String sql = "UPDATE paciente SET peso = '"+this.peso+"', altura = '"+this.altura+"' WHERE id = '"+id_usuario+"'";
        this.banco.getSt().executeUpdate(sql);
        Log log = new Log(log_sql(sql), id_usuario);

        imc = this.peso / (this.altura*this.altura);

        if(imc < 18.5){
            this.classificacao = "Magreza";
        }else if(imc >= 18.5 && imc <= 24.99){
            this.classificacao = "Saudável";
        }else if(imc >= 25 && imc <= 29.9){
            this.classificacao = "Sobrepeso";
        }else if(imc >= 30 && imc <= 34.9){
            this.classificacao = "Obesidade Grau I";
        }else if(imc >= 35 && imc <= 39.9){
            this.classificacao = "Obesidade Grau II";
        }else if(imc >= 40){
            this.classificacao = "Obesidade Grau III";
        }

        setImc_p(imc);
        setClassificacao_p(classificacao);
    }

    public void inserir_imc(int id_usuario) throws SQLException{
        this.banco.conectar();
//        System.out.println("ID NO INSERIR IMC "+id_usuario);
        String sql = "INSERT INTO imc (id_usuario, valor_imc, classificacao) VALUE ('"+id_usuario+"', '"+imc+"', '"+classificacao+"')";
        this.banco.getSt().executeUpdate(sql);
        Log log = new Log(log_sql(sql), id_usuario);
    }

    public String[] restagar_dados(int id_usuario) throws SQLException{
        String[] dados = new String[2];
        this.banco.conectar();
        String sql = "SELECT * FROM login WHERE id_usuario = '"+id_usuario+"'";
        this.banco.setRs(this.banco.getSt().executeQuery(sql));
        Log log = new Log(log_sql(sql), id_usuario);
        if(this.banco.getRs().next()){
            email_select = this.banco.getRs().getString("email");
            senha_select = this.banco.getRs().getString("senha");

            dados[0] = email_select;
            dados[1] = senha_select;
        }
        return dados;
    }
    public void alterar_dados(String nome, String novo_email, String nova_senha, int id_usuario) throws SQLException{
        this.banco.conectar();
        String sql = "UPDATE paciente SET nome = '"+nome+"' WHERE id = '"+id_usuario+"'";
        this.banco.getSt().executeUpdate(sql);

        String sql2 = "UPDATE login SET email = '"+novo_email+"', senha = '"+nova_senha+"' WHERE id_usuario = '"+id_usuario+"'";
        this.banco.getSt().executeUpdate(sql2);
        Log log = new Log(log_sql(sql), id_usuario);
    }

    public void deletar_dados(int id_usuario) throws SQLException{
        this.banco.conectar();
        String sql = "DELETE FROM paciente WHERE id = '"+id_usuario+"'";
        this.banco.getSt().executeUpdate(sql);

        String sql2 = "DELETE FROM login WHERE id_usuario = '"+id_usuario+"'";
        this.banco.getSt().executeUpdate(sql2);
        Log log = new Log(log_sql(sql), id_usuario);
    }

    public float[] porcentagem() throws SQLException{
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
        for(int i = 0; i <= classificacoes.length - 1; i++){
            sql2 = "SELECT COUNT(*) AS quantidade FROM imc WHERE classificacao = '"+classificacoes[i]+"'";
            this.banco.setRs(this.banco.getSt().executeQuery(sql2));
            if(this.banco.getRs().next()){
                qtd_c = this.banco.getRs().getInt("quantidade");
                porcentagem[i] = (qtd_c / qtd) * 100;
            }
        }
        return porcentagem;
    }
    public void gerar_relatorio() throws SQLException, IOException {

        String[] classificacoes = new String[6];
        this.banco.conectar();
        String sql = "SELECT classificacao, COUNT(*) AS qtd FROM imc GROUP BY classificacao";
        this.banco.setRs(this.banco.getSt().executeQuery(sql));

        int i = 0;
        while (this.banco.getRs().next() && i < classificacoes.length) {
            String classificacao = this.banco.getRs().getString("qtd");
            classificacoes[i] = classificacao;
            i++;
        }

        String caminho = "D:/projetos/Poo4bim/relatorio.html";

        String sql2 = "SELECT p.nome, p.peso, p.altura, i.valor_imc, i.classificacao FROM paciente p JOIN imc i ON p.id = i.id_usuario";
        this.banco.setRs(this.banco.getSt().executeQuery(sql2));

        String relatorio = "<!DOCTYPE html>\n" +
                "<html lang='en'>\n" +
                "<head>\n" +
                "    <meta charset='UTF-8'>\n" +
                "    <title>Document</title>\n" +
                "    <script src='https://cdn.plot.ly/plotly-2.27.0.min.js'></script>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <center><div id='myDiv' style='width: 500px; height:500px;'>Pesquisa de Índice de Massa Corporal</div></center>\n" +
                "    <script>\n" +
                "        // Configuração dos dados para o gráfico polar\n" +
                "        var data = [{\n" +
                "            type: 'scatterpolar',\n" +
                "            r: ['" + classificacoes[0] + "', '" + classificacoes[1] + "', '" + classificacoes[2] + "', '" + classificacoes[3] + "', '" + classificacoes[4] + "', '" + classificacoes[5] + "'], // Valores do gráfico\n" +
                "            theta: ['Magreza', 'Saúdavel', 'Sobrepeso', 'OBG-I', 'OBG-II', 'OBG-III'], // Rótulos das categorias\n" +
                "            fill: 'toself' // Preencher a área\n" +
                "        }]\n" +
                "        // Configuração do layout do gráfico\n" +
                "        layout = {\n" +
                "            polar: {\n" +
                "                radialaxis: {\n" +
                "                    visible: true,\n" +
                "                    range: [0, 50] // Faixa dos valores no eixo radial\n" +
                "                }\n" +
                "            },\n" +
                "            showlegend: false // Ocultar a legenda\n" +
                "        }\n" +
                "        // Criação do gráfico usando Plotly\n" +
                "        Plotly.newPlot('myDiv', data, layout)\n" +
                "    </script>\n"+
                "</br>\n";

        String tabelaHTML = "<center><table border='1'>" +
                "<tr>" +
                "<th>Nome</th>" +
                "<th>Peso</th>" +
                "<th>Altura</th>" +
                "<th>IMC</th>" +
                "<th>Classificação</th>" +
                "</tr>";

        while (this.banco.getRs().next()) {
            String nome = this.banco.getRs().getString("nome");
            float peso = this.banco.getRs().getFloat("peso");
            float altura = this.banco.getRs().getFloat("altura");
            float valorImc = this.banco.getRs().getFloat("valor_imc");
            String classificacao = this.banco.getRs().getString("classificacao");

            tabelaHTML += "<tr>" +
                    "<td>" + nome + "</td>" +
                    "<td>" + peso + "</td>" +
                    "<td>" + altura + "</td>" +
                    "<td>" + valorImc + "</td>" +
                    "<td>" + classificacao + "</td>" +
                    "</tr>";
        }
        tabelaHTML += "</table></center>";

        relatorio += tabelaHTML;

                FileWriter fw = new FileWriter(caminho, true);
                fw.write(relatorio+"\n");
                fw.close();

    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getImc_p() {
        return imc_p;
    }

    public void setImc_p(double imc_p) {
        this.imc_p = imc_p;
    }

    public String getClassificacao_p() {
        return classificacao_p;
    }

    public void setClassificacao_p(String classificacao_p) {
        this.classificacao_p = classificacao_p;
    }
}