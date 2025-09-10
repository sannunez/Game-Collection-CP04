package br.com.galeriaDeJogos.controller;

import br.com.galeriaDeJogos.DAO.JogosDAO;
import br.com.galeriaDeJogos.model.Jogos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class CadastroJogo {

    public static void cadastrarJogo(JFrame frame, JogosDAO jogosDao, JTextField titulo, JComboBox<String> genero, JComboBox<String> plataforma, JTextField ano, JComboBox<String> status, DefaultTableModel modelo) {
        try {
            if (!titulo.getText().trim().isEmpty() && !ano.getText().trim().isEmpty()) {
                Jogos jogo = new Jogos();
                jogo.setTitulo(titulo.getText());
                jogo.setGenero((String) genero.getSelectedItem());
                jogo.setPlataforma((String) plataforma.getSelectedItem());
                jogo.setAnoLancamento(Integer.parseInt(ano.getText()));
                jogo.setStatus((String) status.getSelectedItem());

                jogosDao.cadastrarJogo(jogo);
                JOptionPane.showMessageDialog(frame, "Jogo Cadastrado!");
                jogosDao.atualizarTabela(modelo, jogosDao, null);
            } else {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Ano de lançamento inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao Cadastrar Jogo: " + ex.getMessage());
        }
    }

}
