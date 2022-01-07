import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Panoplie {
    private List<Item> items;
    private final static int NB_MAX_ITEMS = 5;

    public Panoplie() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItem(String name) {
        Optional<Item> item = items.stream().filter(i -> i.getNom().equals(name)).findFirst();
        return item.orElse(null);
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Item getArme() {
        Optional<Item> item = items.stream().filter(i -> i.getType() == TypeStat.FORCE).findFirst();
        return item.orElse(null);
    }

    public void addToPanoplie(Item item) {
        if (items.size() < NB_MAX_ITEMS)
            this.items.add(item);
    }

    public void removeFromPanoplie(Item item) {
        if (items.size() > 1)
            this.items.remove(item);
    }
}
