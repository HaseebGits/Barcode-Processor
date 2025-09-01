public class ItemModule implements Module{
    private  String itemCode;
    public ItemModule(String itemCode)
    {
        this.itemCode=itemCode;
    }    @Override
    public boolean isValid() {   //  \\d mean (0-9)
        return itemCode.matches("\\d{5}"); //So "\\d{5}" means it is a string of exactly 5 digits it matches 12345 bcz this is a code requirnment for every product
    }
}
