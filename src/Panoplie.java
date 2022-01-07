import java.util.ArrayList;
import java.util.List;

public class Panoplie {
    private List<Item> items;
    private final static int NB_MAX_ITEMS = 5;

    public Panoplie() {
        this.items = new ArrayList<>();
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
