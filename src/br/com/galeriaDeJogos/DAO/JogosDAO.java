package br.com.galeriaDeJogos.DAO;

import br.com.galeriaDeJogos.DB.Conexao;
import br.com.galeriaDeJogos.model.Jogos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por realizar operações no banco de dados relacionadas à entidade Jogos.
 * Implementa métodos de CRUD e funções auxiliares para atualizar tabelas e perfis na interface.
 *
 *  @author Guilherme Santos Nunes
 *  @version 1.0
 */
public class JogosDAO {
    /*
        CRUD
     */

    /**
     * Cadastra um novo jogo no banco de dados.
     */
    public void cadastrarJogo(Jogos jogo){
        String sql = "INSERT INTO jogos(titulo, genero, plataforma, anoLancamento, status, nota) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement pstm = null;

        try {
            conn = Conexao.createConnectionToMySQL();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, jogo.getTitulo());
            pstm.setString(2, jogo.getGenero());
            pstm.setString(3, jogo.getPlataforma());
            pstm.setInt(4, jogo.getAnoLancamento());
            pstm.setString(5, jogo.getStatus());
            pstm.setDouble(6, jogo.getNota());

            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstm != null){
                    pstm.close();
                }
                if (conn != null){
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Lista jogos do banco de dados.
     * Pode aplicar ordenação por gênero, plataforma ou status.
     */
    public List<Jogos> listarJogos(String opcao){
        String sql;

        if (opcao == null || opcao.isEmpty()) {
            sql = "SELECT * FROM jogos";
        } else {
            switch (opcao) {
                case "Gênero":
                    sql = "SELECT id, titulo, genero, plataforma, anoLancamento, status, nota FROM jogos ORDER BY genero ASC";
                    break;
                case "Plataforma":
                    sql = "SELECT id, titulo, genero, plataforma, anoLancamento, status, nota FROM jogos ORDER BY plataforma ASC";
                    break;
                case "Status":
                    sql = "SELECT id, titulo, genero, plataforma, anoLancamento, status, nota FROM jogos ORDER BY status ASC";
                    break;
                default:
                    sql = "SELECT id, titulo, genero, plataforma, anoLancamento, status, nota FROM jogos";
                    break;
            }
        }

        List<Jogos> jogos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rset = null;

        try {
            conn = Conexao.createConnectionToMySQL();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()){
                Jogos jogo = new Jogos();
                jogo.setId(rset.getInt("id"));
                jogo.setTitulo(rset.getString("titulo"));
                jogo.setGenero(rset.getString("genero"));
                jogo.setPlataforma(rset.getString("plataforma"));
                jogo.setAnoLancamento(rset.getInt("anoLancamento"));
                jogo.setStatus(rset.getString("status"));
                jogo.setNota(rset.getDouble("nota"));

                jogos.add(jogo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset!=null) rset.close();
                if (pstm!=null) pstm.close();
                if (conn!=null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jogos;
    }

    /**
     * Atualiza os dados de um jogo já existente.
     * Retorna true se o jogo foi atualizado.
     */
    public boolean atualizarJogo(Jogos jogo){
        String sql = "UPDATE jogos SET titulo = ?, genero = ?, plataforma = ?, anoLancamento = ?, status = ? WHERE id = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = Conexao.createConnectionToMySQL();
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            pstm.setString(1, jogo.getTitulo());
            pstm.setString(2, jogo.getGenero());
            pstm.setString(3, jogo.getPlataforma());
            pstm.setInt(4, jogo.getAnoLancamento());
            pstm.setString(5, jogo.getStatus());
            pstm.setInt(6, jogo.getId());

            int linhasAfetadas = pstm.executeUpdate();
            return linhasAfetadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deleta um jogo do banco pelo ID.
     * Retorna true se a exclusão foi realizada.
     */
    public boolean deletarJogoPorID(int id){
        String sql = "DELETE FROM jogos WHERE ID = ?";

        Connection conn = null;
        PreparedStatement pstm = null;

        try{
            conn = Conexao.createConnectionToMySQL();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            pstm.setInt(1, id);

            int linhasAfetadas = pstm.executeUpdate();
            return linhasAfetadas > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            try {
                if (pstm != null) pstm.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Atualiza a tabela de jogos exibida na interface.
     */
    public void atualizarTabela(DefaultTableModel model, JogosDAO jogosDao, String ordem){
        model.setRowCount(0);

        List<Jogos> jogos = jogosDao.listarJogos(ordem);

        for(Jogos jogo : jogos){
            model.addRow(new Object[]{
                    jogo.getId(),
                    jogo.getTitulo(),
                    jogo.getGenero(),
                    jogo.getPlataforma(),
                    jogo.getAnoLancamento(),
                    jogo.getStatus(),
                    jogo.getNota()
            });
        }
    }

    /**
     * Atualiza o perfil do usuário exibindo apenas os jogos concluídos
     * e mostrando a quantidade total deles.
     */
    public void atualizarPerfil(DefaultTableModel modeloPerfil, JogosDAO jogosDao, String ordem, JLabel labelConquistas) {
        modeloPerfil.setRowCount(0);
        int contador = 0;

        for (Jogos jogo : jogosDao.listarJogos("")) {
            if("Concluído".equalsIgnoreCase(jogo.getStatus())){
                modeloPerfil.addRow(new Object[]{
                        jogo.getTitulo(),
                        jogo.getGenero(),
                        jogo.getPlataforma()
                });
                contador++;
            }
        }

        labelConquistas.setText("Você possui " + contador + " jogo(s) concluído(s)");
    }

    /**
     * Atualiza o perfil do usuário exibindo a quantidade de jogos concluídos
     * em uma plataforma específica.
     */
    public void atualizarPerfilPlataformas(JogosDAO jogosDao, String plataforma, JLabel labelPlataformaConcluido){
        int contadorPlataforma = 0;
        for (Jogos jogo : jogosDao.listarJogos("")) {
            if("Concluído".equalsIgnoreCase(jogo.getStatus()) && plataforma.equalsIgnoreCase(jogo.getPlataforma())){
                contadorPlataforma++;
            }
        }
        labelPlataformaConcluido.setText(plataforma + ": " + contadorPlataforma);
    }
}
