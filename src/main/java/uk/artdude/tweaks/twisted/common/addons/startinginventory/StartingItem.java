package uk.artdude.tweaks.twisted.common.addons.startinginventory;

public class StartingItem {

    public int quantity;
    public String modId;
    public String item;
    public int meta;

    public StartingItem(int quantity, String modId, String item, int meta) {
        this.quantity = quantity;
        this.modId = modId;
        this.item = item;
        this.meta = meta;
    }
}
