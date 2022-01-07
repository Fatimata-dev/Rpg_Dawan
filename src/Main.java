import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static String recit = "Dans une vallée se trouvait un écuyer. Il avait perdu la trace de son maître, parti\n" +
            "il y a des années combattre un dragon. Ce dernier lui avait dit avant son départ :\n" +
            "\"J'ai une mission de la plus haute importance à accomplir. D'ici mon retour je te confie\n" +
            "la protection du village. Si, dans 3 ans 3 mois et 3 jours tu ne me revois pas, je te\n" +
            "demande de partir à ma recherche.\" L'heure du départ était arrivé et l'écuyer se sentait\n" +
            "investi d'une lourde responsabilité.";

    public static void main(String[] args) {
        System.out.println("Avez vous une sauvegarde ? y | n");
        Scanner sc = new Scanner(System.in);
        Personnage principal = null;
        String choixSave = sc.next();
        if (choixSave.equals("y")) {
            File fIn = new File("Save.txt");
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fIn));
                principal = (Personnage) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println(recit);
            Panoplie pan = new Panoplie();
            int choix = -1;
            Item i = null;
            boolean flag = true;
            System.out.println("Quel est le nom de votre personnage ?");
            String name = sc.next();
            System.out.println("Il informa sa famille et prépara ses affaires. Il trouve sur la table" +
                    "un certain nombre d'équipements. Choisir : Epée (sword) - 1, Bouclier (shield) - 2, Casque (helmet) - 3," +
                    " Marteau (hammer) - 4, Arc (bow) - 5, Cotte de Maille (chainmail) - 6, Ceinture (belt) - 7, Collier (collar) - 8.");
            int cpt = 0;
            do {
                try {
                    choix = sc.nextInt();
                } catch (Exception e) {
                    System.out.println("Le choix n'est pas valide !");
                }

                switch (choix) {
                    case 1:
                        i = new Item("Sword", TypeStat.FORCE, 25);
                        break;
                    case 2:
                        i = new Item("Shield", TypeStat.DEFENSE, 15);
                        break;
                    case 3:
                        i = new Item("Helmet", TypeStat.HP, 20);
                        break;
                    case 4:
                        i = new Item("Hammer", TypeStat.FORCE, 17);
                        break;
                    case 5:
                        i = new Item("Bow", TypeStat.FORCE, 21);
                        break;
                    case 6:
                        i = new Item("Chainmail", TypeStat.DEFENSE, 13);
                        break;
                    case 7:
                        i = new Item("Belt", TypeStat.DEFENSE, 7);
                        break;
                    case 8:
                        i = new Item("Collar", TypeStat.HP, 12);
                        break;
                    case 0:
                        flag = false;
                        break;
                }
                pan.addToPanoplie(i);
                cpt++;
            } while (flag || cpt < 0);
            principal = new Personnage(name, 100, pan);
        }


        System.out.println("Sur la route qui mène à l'antre du dragon, notre héros rencontre un paladin qui veut en découdre." +
                "Le combat commence !");
        Panoplie panEnnemi = new Panoplie();
        panEnnemi.addToPanoplie(new Item("Sword", TypeStat.FORCE, 22));
        panEnnemi.addToPanoplie(new Item("Helmet", TypeStat.DEFENSE, 17));
        Personnage ennemi1 = new Personnage("Paladin Sauvage", 100, panEnnemi);
        String choixCombat = null;
        do {
            System.out.println("Veuillez choisir une action : Attaquer (a) ou fuir (f) !");
            choixCombat = sc.next();
            if (choixCombat.equals("a")) {
                if (principal.attaquer(principal.getPanoplie().getArme(), ennemi1)) {
                    break;
                }
                System.out.println("Les HP de " + ennemi1.getNom() + " sont de " + ennemi1.getHpRestants());
            } else if (choixCombat.equals("f")) {
                if (principal.fuir()) {
                    System.out.println(principal.getNom() + " a pris la fuite !");
                    break;
                } else {
                    System.out.println("Fuite impossible !");
                }
            } else {
                System.out.println("Le choix n'est pas valide !");
                continue;
            }

            if (ennemi1.attaquer(ennemi1.getPanoplie().getArme(), principal)) {
                break;
            }
            System.out.println("Les HP de " + principal.getNom() + " sont de " + principal.getHpRestants());
        } while (true);

        System.out.println("Voulez-vous sauvegarder ? y | n");
        String choixToSave = sc.next();
        if (choixToSave.equals("y")) {
            ObjectOutputStream oos = null;

            try {
                File f = new File("Save.txt");
                oos = new ObjectOutputStream(new FileOutputStream(f));
                oos.writeObject(principal);
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        sc.close();

    }
}
