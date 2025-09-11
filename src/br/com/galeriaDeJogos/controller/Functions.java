package br.com.galeriaDeJogos.controller;

import br.com.galeriaDeJogos.DAO.JogosDAO;
import br.com.galeriaDeJogos.model.Jogos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Functions {

    public static void cadastrarFunction(JFrame frame, JogosDAO jogosDao, JTextField titulo, JComboBox<String> genero, JComboBox<String> plataformaOpcoes, JTextField anoLancamento, JTextField nota, JComboBox<String> status, DefaultTableModel modelo) {
        try {
            //Validação de campos todos preenchidos
            if (!titulo.getText().trim().isEmpty() && !anoLancamento.getText().trim().isEmpty()) {
                //Validação para evitar cadastro de jogos iguais em plataformas iguais.
                boolean jogoJaExiste = false;
                for(int i = 0; i < modelo.getRowCount(); i++){
                    String tituloJTable = (String) modelo.getValueAt(i, 1);      // Coluna 1 = Título
                    String plataformaJtable = (String) modelo.getValueAt(i, 3);

                    if (tituloJTable.equalsIgnoreCase(titulo.getText().trim()) &&
                            plataformaJtable.equalsIgnoreCase((String) plataformaOpcoes.getSelectedItem())){
                        jogoJaExiste = true;
                    }
                }

                Jogos jogo = new Jogos();
                //Validar ano de lançamento
                if (Integer.parseInt(anoLancamento.getText()) > 2025){
                    JOptionPane.showMessageDialog(frame, "Ano de Lançamento Inválido.");
                } else {
                    jogo.setTitulo(titulo.getText());
                    jogo.setGenero((String) genero.getSelectedItem());
                    jogo.setPlataforma((String) plataformaOpcoes.getSelectedItem());
                    jogo.setAnoLancamento(Integer.parseInt(anoLancamento.getText()));
                    jogo.setStatus((String) status.getSelectedItem());
                    jogo.setNota(Double.parseDouble(nota.getText()));
                    jogosDao.cadastrarJogo(jogo);
                    JOptionPane.showMessageDialog(frame, "Jogo Cadastrado!");
                    jogosDao.atualizarTabela(modelo, jogosDao, null);
                }

            } else {
                JOptionPane.showMessageDialog(frame, "Preencha todos os campos.");
            }

            // Validação para ano ser realmente valor numérico
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Ano de lançamento inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Erro ao Cadastrar Jogo: " + ex.getMessage());
        }
    }

    public static void atualizarFunction(JFrame frame, JPanel updateDiv, JogosDAO jogosDao, JTextField tituloUpdate, JTextField anoLancamentoUpdate, JTextField idUpdateTextField, JComboBox generoOpcoesUpdate, JComboBox plataformaOpcoesUpdate, JComboBox statusOpcoesUpdate, JComboBox ordenagem, DefaultTableModel modelo){
        Jogos jogo = new Jogos();
        int option = JOptionPane.showConfirmDialog(
                frame,
                updateDiv,
                "Atualizar Jogo",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if(option == JOptionPane.OK_OPTION){
            try{
                //Validação de campos todos preenchidos
                if (!tituloUpdate.getText().trim().isEmpty() && !anoLancamentoUpdate.getText().trim().isEmpty()){
                    //Validação para evitar cadastro de jogos iguais em plataformas iguais.
                    boolean jogoJaExiste = false;
                    for(int i = 0; i < modelo.getRowCount(); i++){
                        String tituloJTable = (String) modelo.getValueAt(i, 1);      // Coluna 1 = Título
                        String plataformaJtable = (String) modelo.getValueAt(i, 3);

                        if (tituloJTable.equalsIgnoreCase(tituloUpdate.getText().trim()) &&
                            plataformaJtable.equalsIgnoreCase((String) plataformaOpcoesUpdate.getSelectedItem())){
                            jogoJaExiste = true;
                        }
                    }

                    // Validação para evitar anos futuros na atualização.
                    if (Integer.parseInt(anoLancamentoUpdate.getText()) > 2025 ){
                        JOptionPane.showMessageDialog(frame, "Ano de Lançamento Inválido.");
                    } else if(jogoJaExiste){
                        JOptionPane.showMessageDialog(frame, "Este jogo ja está cadastrado nesta mesma plataforma.");
                    } else {
                        jogo.setTitulo(tituloUpdate.getText());
                        jogo.setGenero((String) generoOpcoesUpdate.getSelectedItem());
                        jogo.setPlataforma((String) plataformaOpcoesUpdate.getSelectedItem());
                        jogo.setAnoLancamento(Integer.parseInt(anoLancamentoUpdate.getText()));
                        jogo.setStatus((String) statusOpcoesUpdate.getSelectedItem());
                        jogo.setId(Integer.parseInt(idUpdateTextField.getText()));
                    }

                    // Validação para verificar existencia de ID passado para atualizar jogo.
                    if(!jogoJaExiste){
                        boolean sucesso = jogosDao.atualizarJogo(jogo);
                        if(sucesso){
                            JOptionPane.showMessageDialog(frame,"Jogo Atualizado com sucesso!");
                            jogosDao.atualizarTabela(modelo, jogosDao, (String) ordenagem.getSelectedItem());
                        } else {
                            JOptionPane.showMessageDialog(frame, "ID não encontrado!");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(frame,"Preencha todos os Campos!");
                }
            // Validação para ano ser realmente valor numérico
            } catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(frame, "Ano de lançamento inválido.");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(frame, "Erro ao Atualizar: " + ex.getMessage());
            }
        }
    }


    public static void deleteFunction(JFrame frame, JogosDAO jogosDao, JTextField idDeleteTextField, JComboBox ordenagem, DefaultTableModel modelo){
        int option = JOptionPane.showConfirmDialog(frame,
                idDeleteTextField,
                "Digite o ID do Jogo: ",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION){
            try{
                boolean sucesso = jogosDao.deletarJogoPorID(Integer.parseInt(idDeleteTextField.getText()));
                if (sucesso){
                    jogosDao.atualizarTabela(modelo, jogosDao,(String) ordenagem.getSelectedItem());
                } else {
                    JOptionPane.showMessageDialog(frame, "ID não encontrado.");
                }

            } catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(frame,"Valor digitado não numérico");
            }

        }
    }
}
