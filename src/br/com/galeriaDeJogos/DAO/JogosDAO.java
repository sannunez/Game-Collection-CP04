package br.com.galeriaDeJogos.DAO;

import br.com.galeriaDeJogos.DB.Conexao;
import br.com.galeriaDeJogos.model.Jogos;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JogosDAO {
    /*
        CRUD
     */

    //CREATE
    public void cadastrarJogo(Jogos jogo){
        String sql = "INSERT INTO jogos(titulo, genero, plataforma, anoLancamento, status, nota) VALUES (?, ?, ?, ?, ?, ?)";

        Connection conn = null;

        PreparedStatement pstm = null;

        try {
            //Estabelecendo Conexão com Banco de Dados:
            conn = Conexao.createConnectionToMySQL();

            //PrepareStatement, para executar query:
            pstm = (PreparedStatement) conn.prepareStatement(sql);

            //Passando parametros para INSERT em Banco de Dados:
            pstm.setString(1, jogo.getTitulo());
            pstm.setString(2, jogo.getGenero());
            pstm.setString(3, jogo.getPlataforma());
            pstm.setInt(4, jogo.getAnoLancamento());
            pstm.setString(5, jogo.getStatus());
            pstm.setDouble(6, jogo.getNota());

            //Executar query:
            pstm.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Fechando conexões abertas:
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

    //READ
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

        //Resgatar os dados do banco:
        ResultSet rset = null;

        try {
            conn = Conexao.createConnectionToMySQL();
            pstm = (PreparedStatement) conn.prepareStatement(sql);
            rset = pstm.executeQuery();

            while (rset.next()){
                Jogos jogo = new Jogos();

                //Recderar o id:
                jogo.setId(rset.getInt("id"));

                //Recuperar o titulo:
                jogo.setTitulo(rset.getString("titulo"));

                //Recuperar o genero:
                jogo.setGenero(rset.getString("genero"));

                //Recuperar a plataforma:
                jogo.setPlataforma(rset.getString("plataforma"));

                //Recuperar o ano de lançamento:
                jogo.setAnoLancamento(rset.getInt("anoLancamento"));

                //Recuperar o Status:
                jogo.setStatus(rset.getString("status"));

                //Recuperar a Nota:
                jogo.setNota(rset.getDouble("nota"));

                jogos.add(jogo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rset!=null){
                    rset.close();
                }
                if (pstm!=null){
                    pstm.close();
                }

                if(conn!=null){
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            return jogos;
    }


    //UPDATE
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
            // Retorno em caso de falta de ID, ou seja o comando não executa.
            return false;

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

    //DELETE
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

    //ATUALIZAR LISTA DE INTERFACE DINAMICAMENTE AO CADASTRAR NOVO JOGO:
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

}
