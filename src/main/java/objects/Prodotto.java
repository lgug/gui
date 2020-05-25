package objects;

import objects.CaratteristicheProdotto;
import objects.Categoria;

public class Prodotto implements Comparable<Prodotto> {
    private int id;

    private String nome;
    private String marca;
    private CaratteristicheProdotto caratteristiche;
    private Categoria categoria;
    private float prezzo;
    private int disponibilita;
    private int quantita;
    private String immagine;

    public Prodotto(int id,String nome, String marca, CaratteristicheProdotto caratteristiche, Categoria categoria, float prezzo,int quantita,int disponibilita,  String immagine) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.caratteristiche = caratteristiche;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.quantita=quantita;
        this.disponibilita = disponibilita;
        this.immagine = immagine;
    }
    public Prodotto(){

    }
    public int getId() {return id;}

    public void setId(int id) { this.id = id; }

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

    public int getQuantita() { return quantita; }

    public void setQuantita(int quantita) { this.quantita = quantita; }

    public int compareTo(Prodotto o) {
        return id - o.id;
    }
}