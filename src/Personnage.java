import java.util.Random;
import java.util.Scanner;

public class Personnage {
    private String nom;
    private double xp;
    private double partXp;
    private double hp;
    private double hpRestants;
    private double force;
    private double defense;
    private int timeToDefeat;
    private Panoplie panoplie;
    private int Niveau;
    private final static double XP_GAGNES_A_CHAQUE_VICTOIRE = 30.0;
    private final static double XP_A_AJOUTER_A_CHAQUE_NIVEAU = 5.0;
    private final static double FORCE_A_AJOUTER_A_CHAQUE_NIVEAU = 3.0;
    private final static double DEFENSE_A_AJOUTER_A_CHAQUE_NIVEAU = 7.0;
    private final static double HP_A_AJOUTER_A_CHAQUE_NIVEAU = 20.0;

    public Personnage(String nom, double hp, Panoplie panoplie) {
        this.xp = 0.0;
        this.partXp = 0.0;
        this.timeToDefeat = 0;
        this.nom = nom;
        this.hp = hp;
        this.hpRestants = hp;
        this.panoplie = panoplie;
        for (Item i : panoplie.getItems()) {
            if (i.getType() == TypeStat.DEFENSE) {
                this.defense += i.getCapacite();
            }
            else if (i.getType() == TypeStat.FORCE) {
                this.force += i.getCapacite();
            }
            else {
                this.hp += i.getCapacite();
            }
        }
        this.Niveau = 1;
    }

    public Panoplie getPanoplie() {
        return panoplie;
    }

    public void setPanoplie(Panoplie panoplie) {
        this.panoplie = panoplie;
    }

    public void augmenterNiveau(TypeStat stat) {
        this.Niveau++;
        this.xp += XP_A_AJOUTER_A_CHAQUE_NIVEAU*this.Niveau;
        switch (stat) {
            case FORCE:
                this.force += FORCE_A_AJOUTER_A_CHAQUE_NIVEAU*this.Niveau;
                break;
            case DEFENSE:
                this.defense += DEFENSE_A_AJOUTER_A_CHAQUE_NIVEAU*this.Niveau;
                break;
            case HP:
                this.hp += HP_A_AJOUTER_A_CHAQUE_NIVEAU*this.Niveau;
                break;
        }
    }

    public void diminuerNiveau() {
        this.Niveau--;
        this.xp -= XP_A_AJOUTER_A_CHAQUE_NIVEAU*this.Niveau;
        TypeStat stat = TypeStat.values()[new Random().nextInt(3)];
        switch (stat) {
            case FORCE:
                this.force -= FORCE_A_AJOUTER_A_CHAQUE_NIVEAU * this.Niveau;
                break;
            case DEFENSE:
                this.defense -= DEFENSE_A_AJOUTER_A_CHAQUE_NIVEAU * this.Niveau;
                break;
            case HP:
                this.hp -= HP_A_AJOUTER_A_CHAQUE_NIVEAU * this.Niveau;
                break;
        }
    }

    public boolean attaquer(Item item, Personnage p) {
        this.timeToDefeat++;
        double coupCritique = Math.random() + 1;
        p.hpRestants = p.defense - item.getCapacite()*coupCritique;
        System.out.println(p.hpRestants);
        if (p.hpRestants <= 0) {
            System.out.println("Le personnage " + this.nom + " a gagné le combat !");
            System.out.println("Veuillez choisir une stat à faire évoluer (0: FORCE, 1: DEFENSE, 2: HP)");
            Scanner sc = new Scanner(System.in);
            TypeStat stat = TypeStat.values()[sc.nextInt()];
            addStatistiques(stat);
            reduceStatistiques(p);
            return true;
        }
        return false;
    }

    private void reduceStatistiques(Personnage p) {
        p.partXp -= XP_GAGNES_A_CHAQUE_VICTOIRE/timeToDefeat;
        if (p.partXp < 0) {
            diminuerNiveau();
        }
    }

    private void addStatistiques(TypeStat stat) {
        this.partXp += XP_GAGNES_A_CHAQUE_VICTOIRE/timeToDefeat;
        if (this.partXp > this.xp) {
            augmenterNiveau(stat);
        }
    }

    public void restore() {
        this.hpRestants = this.hp;
        this.timeToDefeat = 0;
    }

    public boolean fuir() {
        double chanceToFlee = Math.random()*timeToDefeat;
        if (chanceToFlee > 1) {
            System.out.println("Le personnage s'est enfuit !");
            restore();
            return true;
        }
        return false;
    }
}
