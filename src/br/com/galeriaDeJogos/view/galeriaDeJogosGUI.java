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

/**
 * Classe principal da aplicação Galeria de Jogos.
 *
 * @author Guilherme Santos Nunes
 * @version
 *
 * <p>
 * Esta classe cria a interface gráfica (GUI) do sistema, composta por três abas:
 * </p>
 * <ul>
 *     <li><b>CADASTRAR JOGOS:</b> Permite inserir novos jogos na coleção, com informações como título, gênero, plataforma, ano, status e nota.</li>
 *     <li><b>LISTAR JOGOS:</b> Exibe todos os jogos cadastrados em uma tabela, possibilitando atualização, exclusão e ordenação por gênero, plataforma ou status.</li>
 *     <li><b>PERFIL DE USUÁRIO:</b> Mostra um relatório de conquistas, jogos concluídos e distribuição por plataformas.</li>
 * </ul>
 *
 * <p>
 * A classe utiliza {@link JogosDAO} para persistência de dados e {@link Functions} para operações como cadastro, atualização e exclusão de jogos.
 * A interface é construída com componentes do pacote {@link javax.swing}, utilizando {@link JFrame}, {@link JPanel}, {@link JButton}, {@link JTable}, {@link JComboBox} e outros.
 * </p>
 *
 * <p>
 * Observações:
 * <ul>
 *     <li>A classe contém todos os listeners de ação e item listener para interação com o usuário.</li>
 *     <li>Os painéis de atualização e cadastro são separados para melhor organização visual.</li>
 *     <li>As tabelas utilizam {@link DefaultTableModel} para manipulação dinâmica de dados.</li>
 * </ul>
 * </p>
 *
 * @author Guilherme
 * @version 1.0
 */
public class galeriaDeJogosGUI {

    /**
     * Método principal que inicializa a aplicação.
     *
     * <p>
     * Este método cria o {@link JFrame} principal, monta as abas, adiciona todos os componentes
     * gráficos, configura os eventos de clique nos botões e inicializa os dados a partir do {@link JogosDAO}.
     * </p>
     *
     */
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
        updateDiv.setBorder(new EmptyBorder(20, 40, 40, 20));

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

        // ABA PERFIL USUÁRIO (RELATÓRIO)
        //Panels
        JPanel abaPerfil = new JPanel();
        abaPerfil.setBorder(new EmptyBorder(0, 20, 10, 20));

        JPanel conquistasPanel = new JPanel();
        conquistasPanel.setLayout(new GridLayout(2,2));

        JPanel plataformasPanel = new JPanel();
        plataformasPanel.setLayout(new GridLayout(2, 2));

        // Modelo Tabela de Jogos concluidos
        DefaultTableModel modeloPerfil = new DefaultTableModel();
        modeloPerfil.addColumn("Título");
        modeloPerfil.addColumn("Gênero");
        modeloPerfil.addColumn("Plataforma");

        JTable tabelaDePerfil = new JTable(modeloPerfil);
        JScrollPane scrollPerfil = new JScrollPane(tabelaDePerfil);

        // Filtrar jogos concluídos totais:
        int contadorTotal = 0;
        for (Jogos jogo : jogosDao.listarJogos("")) {
            if("Concluído".equalsIgnoreCase(jogo.getStatus())){
                modeloPerfil.addRow(new Object[]{
                        jogo.getTitulo(),
                        jogo.getGenero(),
                        jogo.getPlataforma()
                });
                contadorTotal++;
            }
        }

        // Conquistas Totais
        JLabel conquistasTotaisLabel = new JLabel();
        jogosDao.atualizarPerfil(modeloPerfil, jogosDao, null, conquistasTotaisLabel);

        // Conquistas por plataforma
        JLabel conquistaPcLabel = new JLabel();
        jogosDao.atualizarPerfilPlataformas(jogosDao, "PC", conquistaPcLabel);

        JLabel conquistaPlayStationLabel = new JLabel();
        jogosDao.atualizarPerfilPlataformas(jogosDao, "PlayStation", conquistaPlayStationLabel);

        JLabel conquistaXboxLabel = new JLabel();
        jogosDao.atualizarPerfilPlataformas(jogosDao, "Xbox", conquistaXboxLabel);

        JLabel conquistaSwitchLabel = new JLabel();
        jogosDao.atualizarPerfilPlataformas(jogosDao, "Switch", conquistaSwitchLabel);

        conquistasPanel.add(conquistasTotaisLabel);
        conquistasPanel.add(plataformasPanel);

        plataformasPanel.add(conquistaPcLabel);
        plataformasPanel.add(conquistaPlayStationLabel);
        plataformasPanel.add(conquistaXboxLabel);
        plataformasPanel.add(conquistaSwitchLabel);

        abaPerfil.add(conquistasPanel);
        abaPerfil.add(scrollPerfil);

        // ========== AÇÕES ==========
        btnCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Functions.cadastrarFunction(frame, jogosDao, titulo, generoOpcoes, plataformaOpcoes, anoLancamento, nota, statusOpcoes, modelo, modeloPerfil);
                jogosDao.atualizarPerfil(modeloPerfil, jogosDao, null, conquistasTotaisLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "PC", conquistaPcLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "PlayStation", conquistaPlayStationLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "Xbox", conquistaXboxLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "Switch", conquistaSwitchLabel);
            }
        });

        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Functions.atualizarFunction(frame, updateDiv, jogosDao, tituloUpdate, anoLancamentoUpdate,notaUpdate, idUpdateTextField, generoOpcoesUpdate, plataformaOpcoesUpdate, statusOpcoesUpdate, ordenagem, modelo, modeloPerfil);
                jogosDao.atualizarPerfil(modeloPerfil, jogosDao, null, conquistasTotaisLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "PC", conquistaPcLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "PlayStation", conquistaPlayStationLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "Xbox", conquistaXboxLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "Switch", conquistaSwitchLabel);
            }
        });

        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Functions.deleteFunction(frame, jogosDao, idDeleteTextField, ordenagem, modelo);
                jogosDao.atualizarPerfil(modeloPerfil, jogosDao, null, conquistasTotaisLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "PC", conquistaPcLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "PlayStation", conquistaPlayStationLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "Xbox", conquistaXboxLabel);
                jogosDao.atualizarPerfilPlataformas(jogosDao, "Switch", conquistaSwitchLabel);
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
        tabbedPane.add("PERFIL DE USUÁRIO", abaPerfil);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
