package br.edu.utfpr;

public class Dependente {

    public static final int ENSINOINFANTIL    = 1;
    public static final int ENSINOFUNDAMENTAL1    = 2;
    public static final int ENSINOFUNDAMENTAL2 = 3;
    public static final int ENSINOMEDIO = 4;

    private String nome;
    private String escola;
    private int idade;
    //private int serie;
    private int serie;

    private int imagem;

    public Dependente (String nome, String escola, int idade, int serie){
        this.nome = nome;
        this.idade = idade;
        this.escola = escola;
        this.serie = serie;

    }

    public int getImagem() {
        return imagem;
    }

    public void setImagem(int imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEscola() {
        return escola;
    }

    public void setEscola(String escola) {
        this.escola = escola;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public int getIdade() {
        return idade;
    }

    public int getSerie() {
        return serie;
    }

    public void setSerie(int serie) {
        this.serie = serie;
    }


}
