package gson.offer;

public class Item {
    int amount;
    int id;
    gson.offer.itm itm;
    double price;

    public Item(int amount, int id, itm itm, double price){
        this.amount = amount;
        this.id = id;
        this.itm = itm;
        this.price = price;
    }

}
