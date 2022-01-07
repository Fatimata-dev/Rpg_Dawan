public class Item {
    private String nom;
    private TypeStat type;
    private double capacite;

    public Item(String nom, TypeStat type, double capacite) {
        this.nom = nom;
        this.type = type;
        this.capacite = capacite;
    }

    public double getCapacite() {
        return capacite;
    }

    public String getNom() {
        return nom;
    }

    public TypeStat getType() {
        return type;
    }
}
