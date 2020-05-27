package objects;

public class Prodotto implements Comparable<Prodotto> {
    private int id;

    private String nome;
    private String marca;
    private CaratteristicheProdotto tag;
    private Categoria categoria;
    private float prezzo;
    private int disponibilita;
    private int quantita;
    private String immagine;

    public Prodotto() {
    }

    public Prodotto(int id, String nome, String marca, CaratteristicheProdotto tag,Categoria categoria, float prezzo, int disponibilita, int quantita, String immagine) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.tag=tag;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.disponibilita = disponibilita;
        this.quantita = quantita;
        this.immagine = immagine;
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
        return tag;
    }

    public void setCaratteristiche(CaratteristicheProdotto caratteristiche) {
        this.tag = caratteristiche;
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
