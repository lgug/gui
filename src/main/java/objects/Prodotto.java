package objects;

import objects.CaratteristicheProdotto;
import objects.Categoria;

public class Prodotto {

    private String nome;
    private String marca;
    private CaratteristicheProdotto caratteristiche;
    private Categoria categoria;
    private float prezzo;
    private int disponibilita;
    private String immagine;

    public Prodotto() {
    }

    public Prodotto(String nome, String marca, CaratteristicheProdotto caratteristiche, Categoria categoria, float prezzo, int disponibilita, String immagine) {
        this.nome = nome;
        this.marca = marca;
        this.caratteristiche = caratteristiche;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.disponibilita = disponibilita;
        this.immagine = immagine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public CaratteristicheProdotto getCaratteristiche() {
        return caratteristiche;
    }

    public void setCaratteristiche(CaratteristicheProdotto caratteristiche) {
        this.caratteristiche = caratteristiche;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    public int getDisponibilita() {
        return disponibilita;
    }

    public void setDisponibilita(int disponibilita) {
        this.disponibilita = disponibilita;
    }

    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }
}
