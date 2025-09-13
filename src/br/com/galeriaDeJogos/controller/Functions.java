package br.com.galeriaDeJogos.controller;

import br.com.galeriaDeJogos.DAO.JogosDAO;
import br.com.galeriaDeJogos.model.Jogos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * Classe responsável por centralizar as funções principais de cadastro,
 * atualização e exclusão de jogos na galeria.
 *
 * @author Guilherme Santos Nunes
 * @version 1.0
 */
public class Functions {

    /**
     * Cadastra um novo jogo na galeria após validações.
     */
    public static void cadastrarFunction(JFrame frame, JogosDAO jogosDao, JTextField titulo, JComboBox<String> genero,
                                         JComboBox<String> plataformaOpcoes, JTextField anoLancamento, JTextField nota,
                                         JComboBox<String> status, DefaultTableModel modelo, DefaultTableModel modeloPerfil) {
        try {
            // Verifica se campos obrigatórios estão preenchidos
            if (titulo.getText().trim().isEmpty() || anoLancamento.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                return;
            }

            // Verifica se jogo já existe na mesma plataforma
            boolean jogoJaExiste = false;
            for (int i = 0; i < modelo.getRowCount(); i++) {
                String tituloJTable = (String) modelo.getValueAt(i, 1);
                String plataformaJtable = (String) modelo.getValueAt(i, 3);

                if (tituloJTable.equalsIgnoreCase(titulo.getText().trim()) &&
                        plataformaJtable.equalsIgnoreCase((String) plataformaOpcoes.getSelectedItem())) {
                    jogoJaExiste = true;
                    break;
                }
            }
            if (jogoJaExiste) {
                JOptionPane.showMessageDialog(frame, "Este jogo já está cadastrado nesta mesma plataforma.");
                return;
            }

            // Verifica se ano menor/igual 2025
            if (Integer.parseInt(anoLancamento.getText()) > 2025) {
                JOptionPane.showMessageDialog(frame, "Ano de Lançamento Inválido.");
                return;
            }

            // Verifica se nota está entre 0 e 10
            if (Double.parseDouble(nota.getText()) < 0 || Double.parseDouble(nota.getText()) > 10) {
                JOptionPane.showMessageDialog(frame, "Nota deve estar entre 0 e 10.");
                return;
            }

            // Cadastra o jogo após validação
            Jogos jogo = new Jogos();
            jogo.setTitulo(titulo.getText());
            jogo.setGenero((String) genero.getSelectedItem());
            jogo.setPlataforma((String) plataformaOpcoes.getSelectedItem());
            jogo.setAnoLancamento(Integer.parseInt(anoLancamento.getText()));
            jogo.setStatus((String) status.getSelectedItem());
            jogo.setNota(Double.parseDouble(nota.getText()));

            jogosDao.cadastrarJogo(jogo);
            JOptionPane.showMessageDialog(frame, "Jogo Cadastrado!");
            jogosDao.atualizarTabela(modelo, jogosDao, null);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Ano de lançamento e nota devem ser numéricos.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao Cadastrar Jogo: " + ex.getMessage());
        }
    }

    /**
     * Atualiza os dados de um jogo existente na galeria após validações.
     */
    public static void atualizarFunction(JFrame frame, JPanel updateDiv, JogosDAO jogosDao, JTextField tituloUpdate,
                                         JTextField anoLancamentoUpdate, JTextField notaUpdate, JTextField idUpdateTextField,
                                         JComboBox generoOpcoesUpdate, JComboBox plataformaOpcoesUpdate, JComboBox statusOpcoesUpdate,
                                         JComboBox ordenagem, DefaultTableModel modelo, DefaultTableModel modeloPerfil) {
        int option = JOptionPane.showConfirmDialog(
                frame,
                updateDiv,
                "Atualizar Jogo",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                // Verifica se campos obrigatórios estão preenchidos
                if (tituloUpdate.getText().trim().isEmpty() || anoLancamentoUpdate.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
                    return;
                }

                // Verifica se jogo já existe na mesma plataforma
                boolean jogoJaExiste = false;
                for (int i = 0; i < modelo.getRowCount(); i++) {
                    String tituloJTable = (String) modelo.getValueAt(i, 1);
                    String plataformaJtable = (String) modelo.getValueAt(i, 3);

                    if (tituloJTable.equalsIgnoreCase(tituloUpdate.getText().trim()) &&
                            plataformaJtable.equalsIgnoreCase((String) plataformaOpcoesUpdate.getSelectedItem())) {
                        jogoJaExiste = true;
                        break;
                    }
                }
                if (jogoJaExiste) {
                    JOptionPane.showMessageDialog(frame, "Este jogo já está cadastrado nesta mesma plataforma.");
                    return;
                }

                // Verifica se ano menor/igual 2025
                if (Integer.parseInt(anoLancamentoUpdate.getText()) > 2025) {
                    JOptionPane.showMessageDialog(frame, "Ano de Lançamento Inválido.");
                    return;
                }

                // Verifica se nota está entre 0 e 10
                if (Double.parseDouble(notaUpdate.getText()) < 0 || Double.parseDouble(notaUpdate.getText()) > 10) {
                    JOptionPane.showMessageDialog(frame, "Nota deve estar entre 0 e 10.");
                    return;
                }

                // Verifica se ID foi informado
                if (idUpdateTextField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "ID deve ser informado para atualizar.");
                    return;
                }

                // Atualiza o jogo após validação
                Jogos jogo = new Jogos();
                jogo.setTitulo(tituloUpdate.getText());
                jogo.setGenero((String) generoOpcoesUpdate.getSelectedItem());
                jogo.setPlataforma((String) plataformaOpcoesUpdate.getSelectedItem());
                jogo.setAnoLancamento(Integer.parseInt(anoLancamentoUpdate.getText()));
                jogo.setStatus((String) statusOpcoesUpdate.getSelectedItem());
                jogo.setNota(Double.parseDouble(notaUpdate.getText()));
                jogo.setId(Integer.parseInt(idUpdateTextField.getText()));

                boolean sucesso = jogosDao.atualizarJogo(jogo);
                if (sucesso) {
                    JOptionPane.showMessageDialog(frame, "Jogo Atualizado com sucesso!");
                    jogosDao.atualizarTabela(modelo, jogosDao, (String) ordenagem.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(frame, "ID não encontrado!");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ano de lançamento e nota devem ser numéricos.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Erro ao Atualizar: " + ex.getMessage());
            }
        }
    }

    /**
     * Deleta um jogo da galeria a partir do ID informado.
     */
    public static void deleteFunction(JFrame frame, JogosDAO jogosDao, JTextField idDeleteTextField,
                                      JComboBox ordenagem, DefaultTableModel modelo) {
        int option = JOptionPane.showConfirmDialog(frame,
                idDeleteTextField,
                "Digite o ID do Jogo: ",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION) {
            try {
                boolean sucesso = jogosDao.deletarJogoPorID(Integer.parseInt(idDeleteTextField.getText()));
                if (sucesso) {
                    jogosDao.atualizarTabela(modelo, jogosDao, (String) ordenagem.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(frame, "ID não encontrado.");
                }

            } catch (NumberFormatException exception) {
                JOptionPane.showMessageDialog(frame, "Valor digitado não numérico");
            }
        }
    }
}
