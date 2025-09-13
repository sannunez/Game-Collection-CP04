package br.com.galeriaDeJogos.model;

/**
 * Classe que representa a entidade Jogo.
 * Contém atributos e métodos de acesso (getters e setters).
 *
 * @author Guilherme Santos Nunes
 * @version 1.0
 */
public class Jogos {
    private int id;
    private String titulo;
    private String genero;
    private String plataforma;
    private int anoLancamento;
    private String status;
    private double nota;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(int anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
