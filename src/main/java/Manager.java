import com.google.gson.Gson;

public class Manager {
    public static class Container {
        public CaratteristicheProdotto caratteristicheProdotto;
        public Categoria categoria;
        public FormaDiPagamento formaDiPagamento;
        public Indirizzo indirizzo;
        public Ordine ordine;
        public Prodotto prodotto;
        public RuoloResponsabile ruoloResponsabile;
        public TesseraFedelta tesseraFedelta;
        public UtenteCliente utenteCliente;
        public UtenteResponsabile utenteResponsabile;

    }

    public String objectToJson(Object obj){
            Gson gson = new Gson();
            return gson.toJson(obj);
    }

    public Container jsonToObject(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, Container.class );
    }
}