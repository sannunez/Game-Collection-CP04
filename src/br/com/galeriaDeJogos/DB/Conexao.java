package br.com.galeriaDeJogos.DB;

import java.sql.*;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL.
 *
 *  @author Guilherme Santos Nunes
 *  @version 1.0
 */
public class Conexao {
    // Nome de usuário do MySQL
    private static final String USERNAME = "root";

    // Senha do Banco de Dados
    private static final String PASSWORD = "NovaSenha123!";

    // Caminho do banco de dados + porta + nome do banco
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/galeria_jogos";

    /**
     * Cria e retorna uma conexão com o banco de dados MySQL.
     */
    public static Connection createConnectionToMySQL() throws Exception {
        try {
            // Carrega o driver do MySQL
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("Error loading driver: " + cnfe);
        }

        return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
    }

    /**
     * Testa a conexão com o banco de dados.
     */
    public static void main(String[] args) throws Exception {
        Connection conn = createConnectionToMySQL();

        if (conn != null) {
            conn.close();
        }
    }
}

