import java.sql.*;
//import java.util.concurrent.ExecutionException;
public class Altera {
    private Statement st;
    private ResultSet rs;
    private Connection con;
    
    public void conectar() throws SQLException {
        try{
            String user = "root";
            String password = "";
            String fonte = "jdbc:mysql://localhost/pjtjava";
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(fonte, user, password);
            st = con.createStatement();

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    public Statement getSt() {
        return st;
    }

    public void setSt() {
        this.st = st;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet resultado) {
        this.rs = resultado;
    }

    public Connection getCon() {
        return con;
    }

    public void setCon(Connection con) {
        this.con = con;
    }
}