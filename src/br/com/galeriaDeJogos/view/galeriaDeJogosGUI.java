package br.com.galeriaDeJogos.view;

import br.com.galeriaDeJogos.DAO.JogosDAO;
import br.com.galeriaDeJogos.model.Jogos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class galeriaDeJogosGUI {
    public static void main(String[] args) {
        // DAO
        JogosDAO jogosDao = new JogosDAO();

        // FRAME PRINCIPAL
        JFrame frame = new JFrame("Coleção de Jogos");
        frame.setSize(500, 350);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // TABS
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel abaCadastro = new JPanel(new BorderLayout());
        abaCadastro.setBorder(new EmptyBorder(20, 40, 40, 20));

        JPanel abaListagem = new JPanel(new BorderLayout());

        // ========== COMPONENTES DA ABA DE CADASTRO ==========
        // Labels
        JLabel tituloLabel = new JLabel("Titulo: ");
        JLabel generoLabel = new JLabel("Gênero: ");
        JLabel plataformaLabel = new JLabel("Plataforma: ");
        JLabel anoLancamentoLabel = new JLabel("Ano De Lançamento: ");
        JLabel statusLabel = new JLabel("Status: ");

        // Inputs
        JTextField titulo = new JTextField();
        JTextField anoLancamento = new JTextField();

        String[] generos = {
                "Ação", "Aventura", "Battle Royale", "Corrida", "Estratégia", "FPS",
                "Horror / Survival Horror", "Luta", "MOBA", "Música / Ritmo", "Plataforma",
                "Puzzle", "RPG", "Sandbox / Mundo Aberto", "Simulação", "TPS", "Visual Novel"
        };

        JComboBox<String> generoOpcoes = new JComboBox<>(generos);

        String[] plataformas = {"PC", "Xbox", "PlayStation", "Switch"};
        JComboBox<String> plataformaOpcoes = new JComboBox<>(plataformas);

        String[] status = {"Jogando", "Concluído", "Wishlist"};
        JComboBox<String> statusOpcoes = new JComboBox<>(status);

        // Painéis de organização
        JPanel cadastroLabelPanel = new JPanel(new GridLayout(0, 1));
        cadastroLabelPanel.add(tituloLabel);
        cadastroLabelPanel.add(generoLabel);
        cadastroLabelPanel.add(plataformaLabel);
        cadastroLabelPanel.add(anoLancamentoLabel);
        cadastroLabelPanel.add(statusLabel);

        JPanel cadastroOptionsPanel = new JPanel(new GridLayout(0, 1));
        cadastroOptionsPanel.add(titulo);
        cadastroOptionsPanel.add(generoOpcoes);
        cadastroOptionsPanel.add(plataformaOpcoes);
        cadastroOptionsPanel.add(anoLancamento);
        cadastroOptionsPanel.add(statusOpcoes);

        JPanel btnCadastroPainel = new JPanel();

        JButton btnCadastro = new JButton("CADASTRAR");
        btnCadastroPainel.add(btnCadastro);

        // Monta aba de cadastro
        abaCadastro.add(cadastroLabelPanel, BorderLayout.WEST);
        abaCadastro.add(cadastroOptionsPanel, BorderLayout.EAST);
        abaCadastro.add(btnCadastroPainel, BorderLayout.SOUTH);

        // ========== COMPONENTES DA ABA DE LISTAGEM ==========
        // Labels
        JLabel abaListagemLabel = new JLabel("Minha Galeria de Jogos");
        JLabel ordenacaoLabel = new JLabel("Ordenar por: ");

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Título");
        modelo.addColumn("Gênero");
        modelo.addColumn("Plataforma");
        modelo.addColumn("Ano");
        modelo.addColumn("Status");

        JTable tabelaDeJogos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabelaDeJogos);

        for (Jogos jogo : jogosDao.listarJogos("")) {
            modelo.addRow(new Object[]{
                    jogo.getId(),
                    jogo.getTitulo(),
                    jogo.getGenero(),
                    jogo.getPlataforma(),
                    jogo.getAnoLancamento(),
                    jogo.getStatus()
            });
        }

        JPanel btnsPainel = new JPanel();

        JTextField idDeleteTextField = new JTextField();

        JButton btnUpdate = new JButton("Atualizar Jogo");
        JButton btnDelete = new JButton("Deletar Jogo");
        btnsPainel.add(btnUpdate);
        btnsPainel.add(btnDelete);
        btnsPainel.add(ordenacaoLabel);

        String[] ordenagemLista = {"","Gênero", "Plataforma", "Status"};

        JComboBox<String> ordenagem = new JComboBox<>(ordenagemLista);
        btnsPainel.add(ordenagem);

        // Tela de update
        JPanel updateLabelPanel = new JPanel(new GridLayout(0,1));
        JPanel updatePanel = new JPanel(new GridLayout(0,1));
        JPanel updateDiv = new JPanel();

        JLabel idUpdateLabel = new JLabel("ID: ");
        JTextField idUpdateTextField = new JTextField();

        // Inputs
        JTextField tituloUpdate = new JTextField();
        JTextField anoLancamentoUpdate = new JTextField();

        JComboBox<String> generoOpcoesUpdate = new JComboBox<>(generos);
        JComboBox<String> plataformaOpcoesUpdate = new JComboBox<>(plataformas);
        JComboBox<String> statusOpcoesUpdate = new JComboBox<>(status);

        updateLabelPanel.add(tituloLabel);
        updateLabelPanel.add(generoLabel);
        updateLabelPanel.add(plataformaLabel);
        updateLabelPanel.add(anoLancamentoLabel);
        updateLabelPanel.add(statusLabel);
        updateLabelPanel.add(idUpdateLabel);

        updatePanel.add(tituloUpdate);
        updatePanel.add(generoOpcoesUpdate);
        updatePanel.add(plataformaOpcoesUpdate);
        updatePanel.add(anoLancamentoUpdate);
        updatePanel.add(statusOpcoesUpdate);
        updatePanel.add(idUpdateTextField);

        updateDiv.setLayout(new BorderLayout());
        updateDiv.add(updateLabelPanel, BorderLayout.WEST);
        updateDiv.add(updatePanel, BorderLayout.EAST);

        // Monta aba de listagem
        abaListagem.add(abaListagemLabel, BorderLayout.NORTH);
        abaListagem.add(scroll, BorderLayout.CENTER);
        abaListagem.add(btnsPainel, BorderLayout.SOUTH);

        // ========== AÇÕES ==========
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (!titulo.getText().trim().isEmpty() && !anoLancamento.getText().trim().isEmpty()) {
                        JogosDAO jogosDAO = new JogosDAO();

                        Jogos jogo = new Jogos();
                        jogo.setTitulo(titulo.getText());
                        jogo.setGenero((String) generoOpcoes.getSelectedItem());
                        jogo.setPlataforma((String) plataformaOpcoes.getSelectedItem());
                        jogo.setAnoLancamento(Integer.parseInt(anoLancamento.getText()));
                        jogo.setStatus((String) statusOpcoes.getSelectedItem());

                        jogosDAO.cadastrarJogo(jogo);
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
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Jogos jogo = new Jogos();
                JOptionPane.showConfirmDialog(
                        frame,
                        updateDiv,
                        "Atualizar Jogo",
                        JOptionPane.OK_CANCEL_OPTION


                        );
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               int option = JOptionPane.showConfirmDialog(frame,
                    idDeleteTextField,
                        "Digite o ID do Jogo: ",
                       JOptionPane.OK_CANCEL_OPTION,
                       JOptionPane.QUESTION_MESSAGE
                );

               if (option == JOptionPane.OK_OPTION){
                   try{
                       jogosDao.deletarJogoPorID(Integer.parseInt(idDeleteTextField.getText()));
                       jogosDao.atualizarTabela(modelo, jogosDao,(String) ordenagem.getSelectedItem());
                   } catch (NumberFormatException exception){
                       System.out.println("Valor digitado não numérico");
                   } catch (IndexOutOfBoundsException exception){
                       System.out.println("Valor digitado não consta em sua lista de jogos.");
                   }

               }

            }
        });

        ordenagem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                jogosDao.atualizarTabela(modelo, jogosDao, (String) ordenagem.getSelectedItem());
            }
        });

        // ========== FINAL ==========
        tabbedPane.add("CADASTRAR JOGOS", abaCadastro);
        tabbedPane.add("LISTAR JOGOS", abaListagem);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
