import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manager untuk mengelola inventaris gudang
 * Implementasi 4 ALGORITMA UTAMA:
 * 1. TREE TRAVERSAL (Recursive DFS) - untuk kategori barang
 * 2. LINEAR SEARCH (Partial Match) - untuk pencarian nama
 * 3. SORTING (Comparator) - untuk sort nama, stok, harga
 * 4. PRIORITY QUEUE (Auto-Sort) - untuk barang expired
 */

public class WarehouseManager {
    private CategoryNode root;  // Binary Tree untuk kategori
    private Map<String, InventoryItem> quickAccessMap;  // HashMap untuk quick access O(1)
    private PriorityQueue<InventoryItem> expiredQueue;  // Priority Queue untuk auto-sort expired items

    private final String DATA_FILE = "inventory_data.txt";

    public WarehouseManager() {
        // Inisialisasi struktur data
        root = new CategoryNode("All Categories");
        quickAccessMap = new HashMap<>();
        expiredQueue = new PriorityQueue<>();  // Auto-sort by expiration date

        // Setup Binary Tree structure
        CategoryNode finishedGoods = new CategoryNode("Finished Goods");
        CategoryNode material = new CategoryNode("Material");
        root.setLeftChild(finishedGoods);   // Binary tree: left child
        root.setRightChild(material);       // Binary tree: right child

        loadDataFromFile();
    }

    // ============================================
    // FILE OPERATIONS
    // ============================================

    public void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (InventoryItem item : quickAccessMap.values()) {
                writer.println(item.toDataString());
            }
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    private void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                InventoryItem item = new InventoryItem(line);
                quickAccessMap.put(item.getItemId(), item);

                // Otomatis pindahkan item ke expiredQueue jika sudah expired
                if (item.isExpired(LocalDate.now())) {
                    expiredQueue.offer(item);
                } else {
                    // Masukkan ke Category Tree menggunakan RECURSIVE TRAVERSAL
                    addItemToCategoryTreeRecursive(root, item);
                }
            }
        } catch (FileNotFoundException e) {
            // File not found - first run, no problem
        } catch (IOException e) {
            System.err.println("Error loading data: " + e.getMessage());
        }
    }

    public boolean addItem(InventoryItem item) {
        // Cek duplikat ID menggunakan HashMap (O(1) complexity)
        if (quickAccessMap.containsKey(item.getItemId())) {
            return false;
        }

        if (item.isExpired(LocalDate.now())) {
            expiredQueue.offer(item);  // Priority Queue auto-sort
            System.out.println("[System] BARANG EXPIRED: " + item.getItemName() + " (Langsung masuk ke Expired Storage)");
        } else {
            // ALGORITMA: RECURSIVE TREE TRAVERSAL (DFS)
            if (!addItemToCategoryTreeRecursive(root, item)) {
                System.err.println("Kategori barang tidak ditemukan: " + item.getItemCategory());
                return false;
            }
        }
        quickAccessMap.put(item.getItemId(), item);
        saveDataToFile();
        return true;
    }

    /**
     * ALGORITMA: RECURSIVE TREE TRAVERSAL (DFS - Depth-First Search)
     */
    private boolean addItemToCategoryTreeRecursive(CategoryNode node, InventoryItem item) {
        if (node == null) {
            return false;
        }

        // Base case: found matching category
        if (node.getCategoryName().equalsIgnoreCase(item.getItemCategory())) {
            node.getStorageItems().add(item);
            System.out.println("[Tree Traversal DFS] Item ditambahkan ke node: " + node.getCategoryName());
            return true;
        }

        // Recursive case: traverse left subtree first (DFS pre-order)
        if (addItemToCategoryTreeRecursive(node.getLeftChild(), item)) {
            return true;
        }

        // Then traverse right subtree
        return addItemToCategoryTreeRecursive(node.getRightChild(), item);
    }

    /**
     * ALGORITMA: RECURSIVE TREE TRAVERSAL untuk mengambil semua items
     * DFS (Depth-First Search) post-order traversal
     */
    private void getAllItemsRecursive(CategoryNode node, List<InventoryItem> result) {
        if (node == null) {
            return;
        }
        
        // Collect items from current node
        result.addAll(node.getStorageItems());
        
        // Traverse left subtree
        getAllItemsRecursive(node.getLeftChild(), result);
        
        // Traverse right subtree
        getAllItemsRecursive(node.getRightChild(), result);
    }

    public List<InventoryItem> getAllNonExpiredItems() {
        List<InventoryItem> all = new ArrayList<>();
        getAllItemsRecursive(root, all);  // RECURSIVE TRAVERSAL
        return all;
    }

    /**
     * ALGORITMA: RECURSIVE DELETE dari tree
     * Traverse seluruh tree dan hapus item yang match
     */
    private void deleteFromTreeRecursive(CategoryNode node, String itemId) {
        if (node == null) {
            return;
        }
        
        // Remove from current node
        node.getStorageItems().removeIf(item -> item.getItemId().equals(itemId));
        
        // Recursive: traverse left and right
        deleteFromTreeRecursive(node.getLeftChild(), itemId);
        deleteFromTreeRecursive(node.getRightChild(), itemId);
    }

    public InventoryItem deleteItem(String itemId) {
        InventoryItem itemToDelete = quickAccessMap.remove(itemId);

        if (itemToDelete != null) {
            // Hapus dari Category Tree menggunakan RECURSIVE TRAVERSAL
            deleteFromTreeRecursive(root, itemId);
            // Hapus dari Expired Queue
            expiredQueue.remove(itemToDelete);
            saveDataToFile();
        }
        return itemToDelete;
    }


    public List<InventoryItem> getExpiredItems() {
        // Return copy of priority queue (already sorted by expiration date)
        return new ArrayList<>(expiredQueue);
    }

    /**
     * ALGORITMA: PRIORITY QUEUE untuk auto-sort expired items
     */
    public int checkAndMoveExpiredItems() {
        LocalDate today = LocalDate.now();
        List<InventoryItem> itemsToCheck = getAllNonExpiredItems();
        int movedCount = 0;

        for (InventoryItem item : itemsToCheck) {
            // Cek apakah item sudah expired (hari ini atau sebelumnya)
            if (item.isExpired(today)) {
                // Hapus dari Tree menggunakan recursive traversal
                deleteFromTreeRecursive(root, item.getItemId());
                // Tambahkan ke Priority Queue (auto-sort by expiration date)
                expiredQueue.offer(item);
                movedCount++;
            }
        }
        if (movedCount > 0) {
            saveDataToFile();
        }
        return movedCount;
    }

    // Near Expiry Notification (14 hari)
    public List<InventoryItem> getNearExpiryItems() {
        int checkDays = 14;
        LocalDate limitDate = LocalDate.now().plusDays(checkDays);
        LocalDate today = LocalDate.now();

        List<InventoryItem> upcoming = getAllNonExpiredItems().stream()
                .filter(i -> i.getExpirationDate() != null)
                .filter(i -> i.getExpirationDate().isAfter(today))
                .filter(i -> i.getExpirationDate().isBefore(limitDate) || i.getExpirationDate().isEqual(limitDate))
                .collect(Collectors.toList());

        upcoming.sort(InventoryItem.NAME_COMPARATOR);
        return upcoming;
    }


    /**
     * ALGORITMA: LINEAR SEARCH dengan partial match
     */
    public List<InventoryItem> searchItemByName(String keyword) {
        String searchKey = keyword.toLowerCase();
        System.out.println("[Linear Search] Mencari barang dengan keyword: \"" + keyword + "\"");
        
        // Linear search through all items in HashMap
        return quickAccessMap.values().stream()
                .filter(item -> item.getItemName().toLowerCase().contains(searchKey))  // Partial match
                .collect(Collectors.toList());
    }

    /**
     * ALGORITMA: SORTING dengan Comparator
     */
    public List<InventoryItem> filterAndSort(String category, String fragileStatus, String sortOrder) {
        List<InventoryItem> filtered = getAllNonExpiredItems();

        // 1. Filter berdasarkan category
        if (!category.equalsIgnoreCase("ALL")) {
            filtered = filtered.stream()
                    .filter(item -> item.getItemCategory().equalsIgnoreCase(category))
                    .collect(Collectors.toList());
        }

        // 2. Filter berdasarkan fragile status
        if (!fragileStatus.equalsIgnoreCase("ANY")) {
            boolean isFragileFilter = fragileStatus.equalsIgnoreCase("YES");
            filtered = filtered.stream()
                    .filter(item -> item.isFragile() == isFragileFilter)
                    .collect(Collectors.toList());
        }

        // 3. ALGORITMA SORTING: Pilih comparator berdasarkan sortOrder
        Comparator<InventoryItem> comparator;
        switch (sortOrder.toUpperCase()) {
            case "NAME":
                comparator = InventoryItem.NAME_COMPARATOR;
                System.out.println("[Sorting] Diurutkan berdasarkan NAMA (A-Z)");
                break;
            case "STOCK":
                comparator = InventoryItem.STOCK_COMPARATOR;
                System.out.println("[Sorting] Diurutkan berdasarkan STOK (Descending)");
                break;
            case "PRICE":
                comparator = InventoryItem.PRICE_COMPARATOR;
                System.out.println("[Sorting] Diurutkan berdasarkan HARGA (Descending)");
                break;
            default: // ID (default)
                comparator = Comparator.comparing(InventoryItem::getItemId);
                System.out.println("[Sorting] Diurutkan berdasarkan ID");
                break;
        }

        // Apply sorting menggunakan Timsort (O(n log n))
        filtered.sort(comparator);
        return filtered;
    }

    // ============================================
    // DISPLAY
    // ============================================

    public static void displayItemList(List<InventoryItem> items, String title) {
        System.out.println("\n--- " + title.toUpperCase() + " ---");
        if (items.isEmpty()) {
            System.out.println("Tidak ada barang ditemukan.");
            return;
        }

        System.out.println("+----------+----------------------+-----------------+----------+-----------------+------------+-------+----------+");
        System.out.printf("| %-8s | %-20s | %-15s | %-8s | %-15s | %-10s | %-5s | %-8s |\n",
                "ID", "Nama", "Kategori", "Fragile", "Exp. Date", "Lokasi", "Stok", "Harga");
        System.out.println("+----------+----------------------+-----------------+----------+-----------------+------------+-------+----------+");

        for (InventoryItem item : items) {
            String date = item.getExpirationDate() == null ? "-" : item.getExpirationDate().toString();
            System.out.printf("| %-8s | %-20s | %-15s | %-8s | %-15s | %-10s | %-5d | Rp%-6.0f |\n",
                    item.getItemId(), item.getItemName(), item.getItemCategory(),
                    item.isFragile() ? "Yes" : "No", date, item.getLocation(), 
                    item.getStockOnHand(), item.getPrice());
        }
        System.out.println("+----------+----------------------+-----------------+----------+-----------------+------------+-------+----------+");
    }
}