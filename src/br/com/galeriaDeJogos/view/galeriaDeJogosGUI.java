package br.com.galeriaDeJogos.view;

import br.com.galeriaDeJogos.DAO.JogosDAO;
import br.com.galeriaDeJogos.controller.Functions;
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
        JLabel notaLabel = new JLabel("Nota(0-10): ");

        // Inputs
        JTextField titulo = new JTextField();
        JTextField anoLancamento = new JTextField();
        JTextField nota = new JTextField();

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
        cadastroLabelPanel.add(notaLabel);

        JPanel cadastroOptionsPanel = new JPanel(new GridLayout(0, 1));
        cadastroOptionsPanel.add(titulo);
        cadastroOptionsPanel.add(generoOpcoes);
        cadastroOptionsPanel.add(plataformaOpcoes);
        cadastroOptionsPanel.add(anoLancamento);
        cadastroOptionsPanel.add(statusOpcoes);
        cadastroOptionsPanel.add(nota);

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
        modelo.addColumn("Nota");

        JTable tabelaDeJogos = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabelaDeJogos);

        for (Jogos jogo : jogosDao.listarJogos("")) {
            modelo.addRow(new Object[]{
                    jogo.getId(),
                    jogo.getTitulo(),
                    jogo.getGenero(),
                    jogo.getPlataforma(),
                    jogo.getAnoLancamento(),
                    jogo.getStatus(),
                    jogo.getNota()
            });
        }

        JPanel btnsPainel = new JPanel();

        JTextField idDeleteTextField = new JTextField();

        // -- ADD BOTÃO PARA AVALIAR --
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

        // Labels
        JLabel tituloLabelUpdate = new JLabel("Titulo: ");
        JLabel generoLabelUpdate = new JLabel("Gênero: ");
        JLabel plataformaLabelUpdate = new JLabel("Plataforma: ");
        JLabel anoLancamentoLabelUpdate = new JLabel("Ano De Lançamento: ");
        JLabel statusLabelUpdate = new JLabel("Status: ");
        JLabel notaLabelUpdate = new JLabel("Nota(0-10)");

        JLabel idUpdateLabel = new JLabel("ID: ");
        JTextField idUpdateTextField = new JTextField();

        // Inputs
        JTextField tituloUpdate = new JTextField();
        JTextField anoLancamentoUpdate = new JTextField();
        JTextField notaUpdate = new JTextField();

        JComboBox<String> generoOpcoesUpdate = new JComboBox<>(generos);
        JComboBox<String> plataformaOpcoesUpdate = new JComboBox<>(plataformas);
        JComboBox<String> statusOpcoesUpdate = new JComboBox<>(status);

        updateLabelPanel.add(tituloLabelUpdate);
        updateLabelPanel.add(generoLabelUpdate);
        updateLabelPanel.add(plataformaLabelUpdate);
        updateLabelPanel.add(anoLancamentoLabelUpdate);
        updateLabelPanel.add(statusLabelUpdate);
        updateLabelPanel.add(notaLabelUpdate);
        updateLabelPanel.add(idUpdateLabel);

        updatePanel.add(tituloUpdate);
        updatePanel.add(generoOpcoesUpdate);
        updatePanel.add(plataformaOpcoesUpdate);
        updatePanel.add(anoLancamentoUpdate);
        updatePanel.add(statusOpcoesUpdate);
        updatePanel.add(notaUpdate);
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
                Functions.cadastrarFunction(frame, jogosDao, titulo, generoOpcoes, plataformaOpcoes, anoLancamento, nota, statusOpcoes, modelo);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Functions.atualizarFunction(frame, updateDiv, jogosDao, tituloUpdate, anoLancamentoUpdate,notaUpdate, idUpdateTextField, generoOpcoesUpdate, plataformaOpcoesUpdate, statusOpcoesUpdate, ordenagem, modelo);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              Functions.deleteFunction(frame, jogosDao, idDeleteTextField, ordenagem, modelo);
            }
        });

        ordenagem.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                jogosDao.atualizarTabela(modelo, jogosDao, (String) ordenagem.getSelectedItem());
            }
        });

        // INSERIR AVALIARBTN ACTION LISTENER

        // ========== FINAL ==========
        tabbedPane.add("CADASTRAR JOGOS", abaCadastro);
        tabbedPane.add("LISTAR JOGOS", abaListagem);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
