import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Model class untuk item inventaris
 * Implementasi ALGORITMA:
 * - Comparable interface untuk Priority Queue (auto-sort by expiration date)
 * - Comparators untuk Sorting (NAME, STOCK, PRICE)
 */

public class InventoryItem implements Comparable<InventoryItem> {
    private String itemId;
    private String itemName;
    private String itemCategory;
    private boolean isFragile;
    private LocalDate expirationDate;
    private String location;
    private int stockOnHand;
    private double price;  // Harga barang (untuk sorting)

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    // Constructor lengkap dengan harga
    public InventoryItem(String itemId, String itemName, String itemCategory, boolean isFragile, 
                        LocalDate expirationDate, String location, int stockOnHand, double price) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.isFragile = isFragile;
        this.expirationDate = expirationDate;
        this.location = location;
        this.stockOnHand = stockOnHand;
        this.price = price;
    }

    // Constructor untuk load dari file
    public InventoryItem(String dataString) {
        String[] data = dataString.split(";");
        this.itemId = data[0];
        this.itemName = data[1];
        this.itemCategory = data[2];
        this.isFragile = Boolean.parseBoolean(data[3]);
        if (data[4].equals("-")) {
            this.expirationDate = null;
        } else {
            this.expirationDate = LocalDate.parse(data[4], DATE_FORMATTER);
        }
        this.location = data[5];
        this.stockOnHand = Integer.parseInt(data[6]);
        // Handle backward compatibility: old format tanpa harga
        this.price = data.length > 7 ? Double.parseDouble(data[7]) : 0.0;
    }

    // Metode untuk menyimpan ke file (dengan harga)
    public String toDataString() {
        String dateString = expirationDate == null ? "-" : expirationDate.format(DATE_FORMATTER);
        return String.join(";", itemId, itemName, itemCategory, String.valueOf(isFragile), 
                          dateString, location, String.valueOf(stockOnHand), String.valueOf(price));
    }

    // Pengecekan apakah sudah expired
    public boolean isExpired(LocalDate checkDate) {
        return expirationDate != null && (expirationDate.isBefore(checkDate) || expirationDate.isEqual(checkDate));
    }

    // ALGORITMA: Comparable untuk PriorityQueue (sort by expiration date)
    @Override
    public int compareTo(InventoryItem other) {
        if (this.expirationDate == null && other.expirationDate == null) return 0;
        if (this.expirationDate == null) return 1;  // Null date = paling akhir
        if (other.expirationDate == null) return -1;
        return this.expirationDate.compareTo(other.expirationDate);
    }

    // Getters
    public String getItemId() { return itemId; }
    public String getItemName() { return itemName; }
    public String getItemCategory() { return itemCategory; }
    public boolean isFragile() { return isFragile; }
    public LocalDate getExpirationDate() { return expirationDate; }
    public String getLocation() { return location; }
    public int getStockOnHand() { return stockOnHand; }
    public double getPrice() { return price; }

    // ALGORITMA: Comparators untuk SORTING (NAMA, STOK, HARGA)
    public static Comparator<InventoryItem> NAME_COMPARATOR = Comparator.comparing(InventoryItem::getItemName);
    public static Comparator<InventoryItem> STOCK_COMPARATOR = Comparator.comparingInt(InventoryItem::getStockOnHand).reversed();
    public static Comparator<InventoryItem> PRICE_COMPARATOR = Comparator.comparingDouble(InventoryItem::getPrice).reversed();
}