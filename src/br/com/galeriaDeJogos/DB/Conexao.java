package br.com.galeriaDeJogos.DB;

import java.sql.*;

public class Conexao {
    // Nome de usuário mySQL:
    private static final String USERNAME = "root";

    // Senha do Banco de Dados:
    private static final String PASSWORD = "NovaSenha123!";

    // Caminho do banco de dados + porta + nome do banco:
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/galeria_jogos";

    /*
        Conexão com o Banco de Dados
     */
    public static Connection createConnectionToMySQL() throws Exception {
        try{
            //Carregar classe pela JVM
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        // Cria a conexão com o Banco de Dados
        Connection connection = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);

        return connection;
    }

    public static void main(String[] args) throws Exception {

        //Recuperar uma conexão com o Banco de Dados
        Connection conn = createConnectionToMySQL();

        //Testar se a conexão é nula:
        if(conn != null){
            conn.close();
        }
    }
}
