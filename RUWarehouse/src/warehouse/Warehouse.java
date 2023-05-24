package warehouse;

/*
 *
 * This class implements a warehouse on a Hash Table like structure, 
 * where each entry of the table stores a priority queue. 
 * Due to your limited space, you are unable to simply rehash to get more space. 
 * However, you can use your priority queue structure to delete less popular items 
 * and keep the space constant.
 * 
 * @author Aakash M
 */ 
public class Warehouse {
    private Sector[] sectors;
    
    // Initializes every sector to an empty sector
    public Warehouse() {
        sectors = new Sector[10];

        for (int i = 0; i < 10; i++) {
            sectors[i] = new Sector();
        }
    }
    
    /**
     * Provided method, code the parts to add their behavior
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void addProduct(int id, String name, int stock, int day, int demand) {
        evictIfNeeded(id);
        addToEnd(id, name, stock, day, demand);
        fixHeap(id);
    }

    /**
     * Add a new product to the end of the correct sector
     * Requires proper use of the .add() method in the Sector class
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    private void addToEnd(int id, String name, int stock, int day, int demand) {
        Product product = new Product(id, name, stock, day, demand);
        int tmp = id % 10;
        sectors[tmp].add(product);
    }

    /**
     * Fix the heap structure of the sector, assuming the item was already added
     * Requires proper use of the .swim() and .getSize() methods in the Sector class
     * @param id The id of the item which was added
     */
    private void fixHeap(int id) {
        int tmp = id % 10;
        sectors[tmp].swim(sectors[tmp].getSize());
    }

    /**
     * Delete the least popular item in the correct sector, only if its size is 5 while maintaining heap
     * Requires proper use of the .swap(), .deleteLast(), and .sink() methods in the Sector class
     * @param id The id of the item which is about to be added
     */
    private void evictIfNeeded(int id) {
        int tmp = id % 10;
        if(sectors[tmp].getSize() == 5)
        {
            sectors[tmp].swap(1, sectors[tmp].getSize());
            sectors[tmp].deleteLast();
            sectors[tmp].sink(1);
        }
    }

    /**
     * Update the stock of some item by some amount
     * Requires proper use of the .getSize() and .get() methods in the Sector class
     * Requires proper use of the .updateStock() method in the Product class
     * @param id The id of the item to restock
     * @param amount The amount by which to update the stock
     */
    public void restockProduct(int id, int amount) {
        int tmp = id % 10;
        for(int i = 1; i < sectors[tmp].getSize() + 1; i++){
            if(sectors[tmp].get(i).getId() == id){
                sectors[tmp].get(i).updateStock(amount);
                break;
            }
        }
    }
    
    /**
     * Delete some arbitrary product while maintaining the heap structure in O(logn)
     * Requires proper use of the .getSize(), .get(), .swap(), .deleteLast(), .sink() and/or .swim() methods
     * Requires proper use of the .getId() method from the Product class
     * @param id The id of the product to delete
     */
    public void deleteProduct(int id) {
        int tmp = id % 10;
        for(int i = 1; i < sectors[tmp].getSize() + 1; i++){
            if(sectors[tmp].get(i).getId() == id){
                sectors[tmp].swap(i, sectors[tmp].getSize());
                sectors[tmp].deleteLast();
                sectors[tmp].sink(i);
                break;
             }
         }
    }
    
    /**
     * Simulate a purchase order for some product
     * Requires proper use of the getSize(), sink(), get() methods in the Sector class
     * Requires proper use of the getId(), getStock(), setLastPurchaseDay(), updateStock(), updateDemand() methods
     * @param id The id of the purchased product
     * @param day The current day
     * @param amount The amount purchased
     */
    public void purchaseProduct(int id, int day, int amount) {
        int tmp = id % 10; 
        for(int i = 1; i < sectors[tmp].getSize() + 1; i++){
            if (sectors[tmp].get(i).getId() == id){
                if(sectors[tmp].get(i).getStock() >= amount){
                    sectors[tmp].get(i).setLastPurchaseDay(day);
                    sectors[tmp].get(i).updateStock(-amount);
                    sectors[tmp].get(i).setDemand(sectors[tmp].get(i).getDemand() + amount);
                    sectors[tmp].sink(i);
                    break;
                }else{
                    break;
                }
            }  
        }
    }
    
    /**
     * Construct a better scheme to add a product, where empty spaces are always filled
     * @param id The id of the item to add
     * @param name The name of the item to add
     * @param stock The stock of the item to add
     * @param day The day of the item to add
     * @param demand Initial demand of the item to add
     */
    public void betterAddProduct(int id, String name, int stock, int day, int demand) {
        int ptr = 0;
        int tmp = id % 10;
        while(sectors[(tmp + ptr)%10].getSize()==5 && ptr<10)
        {
            ptr++;
        }
        if(sectors[(tmp + ptr) % 10 ].getSize() == 5)
        {
            addProduct(id, name, stock, day, demand);
        }else{
            sectors[(tmp + ptr)%10].add(new Product(id, name, stock, day, demand));
            sectors[(tmp + ptr) % 10].swim(sectors[(tmp + ptr)%10].getSize());
        }
        
    }

    /*
     * Returns the string representation of the warehouse
     */
    public String toString() {
        String warehouseString = "[\n";

        for (int i = 0; i < 10; i++) {
            warehouseString += "\t" + sectors[i].toString() + "\n";
        }
        
        return warehouseString + "]";
    }

    /*
     * Do not remove this method
     */ 
    public Sector[] getSectors () {
        return sectors;
    }
}
