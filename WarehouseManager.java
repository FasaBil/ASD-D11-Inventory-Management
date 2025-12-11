import java.io.*;
import java.time.LocalDate;
import java.util.*;

/**
 * Manager untuk mengelola inventaris gudang
 * 
 * ===================================================================
 * Implementasi 4 ALGORITMA UTAMA dengan KOMPLEKSITAS SEBENARNYA:
 * ===================================================================
 * 
 * 1. TREE TRAVERSAL (Recursive DFS - Depth First Search)
 *    - Menggunakan rekursi untuk traverse binary tree kategori
 *    - Pre-order traversal: Root -> Left -> Right
 *    - Kompleksitas: O(h) dimana h = tinggi tree, worst case O(n)
 *    - Implementasi: addItemToCategoryTreeRecursive(), getAllItemsRecursive()
 * 
 * 2. LINEAR SEARCH (Sequential Search dengan Partial Match)
 *    - Iterasi manual satu per satu menggunakan loop
 *    - Kompleksitas: O(n) - harus cek semua elemen
 *    - TIDAK menggunakan Stream API atau filter bawaan
 *    - Implementasi: searchItemByName()
 * 
 * 3. SORTING (Bubble Sort Manual)
 *    - Implementasi manual nested loop untuk compare dan swap
 *    - Kompleksitas: O(n²) - worst case quadratic time
 *    - TIDAK menggunakan Collections.sort() atau Comparator
 *    - Implementasi: bubbleSortByName(), bubbleSortByStock(), bubbleSortById()
 * 
 * 4. PRIORITY QUEUE (Min-Heap untuk Expired Items)
 *    - Binary heap structure untuk maintain sorted order
 *    - Kompleksitas: O(log n) untuk insert (heapify up)
 *    - Auto-sort berdasarkan expiration date (earliest first)
 *    - Implementasi: checkAndMoveExpiredItems() + PriorityQueue
 * 
 * ===================================================================
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

        // Setup Binary Tree structure dengan hierarki lengkap
        buildCategoryTree();
        
        loadDataFromFile();
    }
    
    /**
     * Membangun Binary Tree dengan struktur kategori lengkap
     * Struktur: All Categories -> Makanan / Non-Makanan -> Sub-kategori
     */
    private void buildCategoryTree() {
        // Level 1: Main categories
        CategoryNode makanan = new CategoryNode("Makanan");
        CategoryNode nonMakanan = new CategoryNode("Non-Makanan");
        
        root.setLeftChild(nonMakanan);
        root.setRightChild(makanan);
        
        // Level 2 Makanan: Sub-kategori makanan
        CategoryNode sembakoUtama = new CategoryNode("Sembako Utama");
        CategoryNode telurSusu = new CategoryNode("Telur & Produk Susu");
        CategoryNode mieProdukGandum = new CategoryNode("Mie & Produk Gandum");
        CategoryNode bumbuPenyedap = new CategoryNode("Bumbu & Penyedap");
        CategoryNode minuman = new CategoryNode("Minuman");
        CategoryNode makananRingan = new CategoryNode("Makanan Ringan");
        CategoryNode kalengOlahan = new CategoryNode("Kaleng & Olahan");
        CategoryNode bahanMentah = new CategoryNode("Bahan Mentah");
        
        makanan.setLeftChild(sembakoUtama);
        makanan.setRightChild(telurSusu);
        sembakoUtama.setLeftChild(mieProdukGandum);
        sembakoUtama.setRightChild(bumbuPenyedap);
        telurSusu.setLeftChild(minuman);
        telurSusu.setRightChild(makananRingan);
        minuman.setLeftChild(kalengOlahan);
        minuman.setRightChild(bahanMentah);
        
        // Level 2 Non-Makanan: Sub-kategori non-makanan
        CategoryNode kebutuhanRT = new CategoryNode("Kebutuhan Rumah Tangga");
        CategoryNode kebersihanKesehatan = new CategoryNode("Kebersihan & Kesehatan");
        
        nonMakanan.setLeftChild(kebutuhanRT);
        nonMakanan.setRightChild(kebersihanKesehatan);
        
        // Level 3 Sembako Utama
        CategoryNode beras = new CategoryNode("Beras");
        CategoryNode gula = new CategoryNode("Gula");
        CategoryNode minyak = new CategoryNode("Minyak");
        CategoryNode tepung = new CategoryNode("Tepung");
        sembakoUtama.setLeftChild(beras);
        sembakoUtama.setRightChild(gula);
        beras.setLeftChild(minyak);
        beras.setRightChild(tepung);
        
        // Level 3 Telur & Produk Susu
        CategoryNode telur = new CategoryNode("Telur");
        CategoryNode susuUHT = new CategoryNode("Susu UHT");
        CategoryNode susuBubuk = new CategoryNode("Susu Bubuk");
        telurSusu.setLeftChild(telur);
        telurSusu.setRightChild(susuUHT);
        susuUHT.setLeftChild(susuBubuk);
        
        // Level 3 Mie & Produk Gandum
        CategoryNode mieInstan = new CategoryNode("Mie Instan");
        CategoryNode roti = new CategoryNode("Roti");
        mieProdukGandum.setLeftChild(mieInstan);
        mieProdukGandum.setRightChild(roti);
        
        // Level 3 Bumbu & Penyedap
        CategoryNode kecap = new CategoryNode("Kecap");
        CategoryNode saus = new CategoryNode("Saus");
        CategoryNode bumbu = new CategoryNode("Bumbu");
        CategoryNode bumbuKering = new CategoryNode("Bumbu Kering");
        bumbuPenyedap.setLeftChild(kecap);
        bumbuPenyedap.setRightChild(saus);
        saus.setLeftChild(bumbu);
        saus.setRightChild(bumbuKering);
        
        // Level 3 Minuman
        CategoryNode teh = new CategoryNode("Teh");
        CategoryNode kopi = new CategoryNode("Kopi");
        minuman.setLeftChild(teh);
        minuman.setRightChild(kopi);
        
        // Level 3 Makanan Ringan
        CategoryNode biskuit = new CategoryNode("Biskuit");
        CategoryNode kerupuk = new CategoryNode("Kerupuk");
        makananRingan.setLeftChild(biskuit);
        makananRingan.setRightChild(kerupuk);
        
        // Level 3 Kaleng & Olahan
        CategoryNode sarden = new CategoryNode("Sarden");
        CategoryNode olahanLain = new CategoryNode("Olahan Lain");
        kalengOlahan.setLeftChild(sarden);
        kalengOlahan.setRightChild(olahanLain);
        
        // Level 3 Bahan Mentah
        CategoryNode jagung = new CategoryNode("Jagung");
        CategoryNode kedelai = new CategoryNode("Kedelai");
        CategoryNode kacangTanah = new CategoryNode("Kacang Tanah");
        CategoryNode gulaMerah = new CategoryNode("Gula Merah");
        bahanMentah.setLeftChild(jagung);
        bahanMentah.setRightChild(kedelai);
        kedelai.setLeftChild(kacangTanah);
        kedelai.setRightChild(gulaMerah);
        
        // Level 3 Non-Makanan
        CategoryNode perlengkapanRumah = new CategoryNode("Perlengkapan Rumah");
        CategoryNode airMinumKemasan = new CategoryNode("Air Minum / Kemasan");
        kebutuhanRT.setLeftChild(perlengkapanRumah);
        kebutuhanRT.setRightChild(airMinumKemasan);
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
            // PRIORITY QUEUE: Insert ke heap (O(log n) - heapify up operation)
            expiredQueue.offer(item);  
            System.out.println("[Priority Queue - Heap Insert] BARANG EXPIRED: " + item.getItemName() + 
                             " (Langsung masuk ke Expired Storage | Heap size: " + expiredQueue.size() + ")");
        } else {
            // ALGORITMA: RECURSIVE TREE TRAVERSAL (DFS)
            System.out.println("[Tree Traversal DFS] Memulai pencarian kategori untuk: " + item.getItemName());
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
     * Kompleksitas: O(h) dimana h adalah tinggi tree (worst case O(n) untuk skewed tree)
     * Menggunakan pre-order traversal: Root -> Left -> Right
     */
    private boolean addItemToCategoryTreeRecursive(CategoryNode node, InventoryItem item) {
        if (node == null) {
            System.out.println("  [DFS] Null node - backtrack");
            return false;
        }

        System.out.println("  [DFS] Mengunjungi node: " + node.getCategoryName());

        // Base case: found matching category
        if (node.getCategoryName().equalsIgnoreCase(item.getItemCategory())) {
            node.getStorageItems().add(item);
            System.out.println("  [DFS] ✓ Match! Item '" + item.getItemName() + "' ditambahkan ke node: " + node.getCategoryName());
            return true;
        }

        // Recursive case: traverse left subtree first (DFS pre-order)
        System.out.println("  [DFS] Mencari di left subtree dari: " + node.getCategoryName());
        if (addItemToCategoryTreeRecursive(node.getLeftChild(), item)) {
            return true;
        }

        // Then traverse right subtree
        System.out.println("  [DFS] Mencari di right subtree dari: " + node.getCategoryName());
        return addItemToCategoryTreeRecursive(node.getRightChild(), item);
    }

    /**
     * ALGORITMA: RECURSIVE TREE TRAVERSAL untuk mengambil semua items
     * DFS (Depth-First Search) post-order traversal
     * Kompleksitas: O(n) dimana n adalah jumlah node dalam tree
     */
    private void getAllItemsRecursive(CategoryNode node, List<InventoryItem> result) {
        if (node == null) {
            return;
        }
        
        // Collect items from current node (process root)
        if (!node.getStorageItems().isEmpty()) {
            result.addAll(node.getStorageItems());
        }
        
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
     * ALGORITMA: PRIORITY QUEUE (Min-Heap) untuk auto-sort expired items
     * Kompleksitas: O(log n) untuk setiap insert (offer operation)
     * Priority Queue menggunakan binary heap structure untuk maintain order
     * Item dengan expiration date paling awal akan berada di root (poll operation O(log n))
     */
    public int checkAndMoveExpiredItems() {
        LocalDate today = LocalDate.now();
        List<InventoryItem> itemsToCheck = getAllNonExpiredItems();
        int movedCount = 0;

        System.out.println("[Priority Queue] Mengecek item expired...");
        
        for (InventoryItem item : itemsToCheck) {
            // Cek apakah item sudah expired (hari ini atau sebelumnya)
            if (item.isExpired(today)) {
                // Hapus dari Tree menggunakan recursive traversal
                deleteFromTreeRecursive(root, item.getItemId());
                
                // PRIORITY QUEUE: Tambahkan ke heap (O(log n) - heapify up)
                expiredQueue.offer(item);
                System.out.println("  [Heap Insert] Item expired dipindahkan: " + item.getItemName() + 
                                 " | Exp: " + item.getExpirationDate() + " | Heap size: " + expiredQueue.size());
                movedCount++;
            }
        }
        
        if (movedCount > 0) {
            System.out.println("[Priority Queue] Total item dipindahkan: " + movedCount);
            System.out.println("[Priority Queue] Item dengan expiration paling awal: " + 
                             (expiredQueue.peek() != null ? expiredQueue.peek().getItemName() : "N/A"));
            saveDataToFile();
        }
        return movedCount;
    }

    // Near Expiry Notification (14 hari)
    // Menggunakan manual filtering dengan loop O(n)
    public List<InventoryItem> getNearExpiryItems() {
        int checkDays = 14;
        LocalDate limitDate = LocalDate.now().plusDays(checkDays);
        LocalDate today = LocalDate.now();

        List<InventoryItem> allItems = getAllNonExpiredItems();
        List<InventoryItem> upcoming = new ArrayList<>();
        
        // Manual filtering O(n)
        for (InventoryItem item : allItems) {
            if (item.getExpirationDate() != null) {
                if (item.getExpirationDate().isAfter(today) && 
                   (item.getExpirationDate().isBefore(limitDate) || item.getExpirationDate().isEqual(limitDate))) {
                    upcoming.add(item);
                }
            }
        }

        // Sort menggunakan Bubble Sort
        bubbleSortByName(upcoming);
        return upcoming;
    }


    /**
     * ALGORITMA: LINEAR SEARCH dengan partial match
     * Kompleksitas: O(n) - dimana n adalah jumlah total item
     * Menggunakan iterasi manual untuk mencari item satu per satu
     */
    public List<InventoryItem> searchItemByName(String keyword) {
        String searchKey = keyword.toLowerCase();
        System.out.println("[Linear Search] Mencari barang dengan keyword: \"" + keyword + "\"");
        System.out.println("[Linear Search] Kompleksitas: O(n) - Iterasi manual melalui semua item");
        
        List<InventoryItem> result = new ArrayList<>();
        int comparisonCount = 0;
        
        // MANUAL LINEAR SEARCH: Iterasi satu per satu (O(n))
        for (InventoryItem item : quickAccessMap.values()) {
            comparisonCount++;
            // Partial match: cek apakah nama item mengandung keyword
            if (item.getItemName().toLowerCase().contains(searchKey)) {
                result.add(item);
                System.out.println("  -> Match ditemukan: " + item.getItemName() + " (Perbandingan ke-" + comparisonCount + ")");
            }
        }
        
        System.out.println("[Linear Search] Total perbandingan: " + comparisonCount + " item");
        System.out.println("[Linear Search] Hasil ditemukan: " + result.size() + " item");
        return result;
    }

    /**
     * ALGORITMA: SORTING dengan implementasi manual Bubble Sort
     * Kompleksitas: O(n²) - nested loop untuk membandingkan dan menukar elemen
     */
    public List<InventoryItem> filterAndSort(String category, String fragileStatus, String sortOrder) {
        List<InventoryItem> filtered = getAllNonExpiredItems();

        // 1. Filter berdasarkan category (Manual loop - O(n))
        if (!category.equalsIgnoreCase("ALL")) {
            List<InventoryItem> temp = new ArrayList<>();
            for (InventoryItem item : filtered) {
                if (item.getItemCategory().equalsIgnoreCase(category)) {
                    temp.add(item);
                }
            }
            filtered = temp;
        }

        // 2. Filter berdasarkan fragile status (Manual loop - O(n))
        if (!fragileStatus.equalsIgnoreCase("ANY")) {
            boolean isFragileFilter = fragileStatus.equalsIgnoreCase("YES");
            List<InventoryItem> temp = new ArrayList<>();
            for (InventoryItem item : filtered) {
                if (item.isFragile() == isFragileFilter) {
                    temp.add(item);
                }
            }
            filtered = temp;
        }

        // 3. ALGORITMA SORTING: Bubble Sort Manual (O(n²))
        System.out.println("[Sorting] Menggunakan Bubble Sort - Kompleksitas O(n²)");
        
        switch (sortOrder.toUpperCase()) {
            case "NAME":
                System.out.println("[Sorting] Diurutkan berdasarkan NAMA (A-Z)");
                bubbleSortByName(filtered);
                break;
            case "STOCK":
                System.out.println("[Sorting] Diurutkan berdasarkan STOK (Descending)");
                bubbleSortByStock(filtered);
                break;
            default: // ID (default)
                System.out.println("[Sorting] Diurutkan berdasarkan ID");
                bubbleSortById(filtered);
                break;
        }

        return filtered;
    }

    /**
     * BUBBLE SORT BY NAME - Ascending (A-Z)
     * Kompleksitas: O(n²) dengan nested loop
     */
    private void bubbleSortByName(List<InventoryItem> items) {
        int n = items.size();
        int swapCount = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Bandingkan nama item[j] dengan item[j+1]
                if (items.get(j).getItemName().compareToIgnoreCase(items.get(j + 1).getItemName()) > 0) {
                    // Swap jika order salah
                    InventoryItem temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                    swapCount++;
                }
            }
        }
        System.out.println("[Bubble Sort] Total swap: " + swapCount + " operasi");
    }

    /**
     * BUBBLE SORT BY STOCK - Descending (Tertinggi ke Terendah)
     * Kompleksitas: O(n²) dengan nested loop
     */
    private void bubbleSortByStock(List<InventoryItem> items) {
        int n = items.size();
        int swapCount = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                // Descending: item dengan stok lebih tinggi di depan
                if (items.get(j).getStockOnHand() < items.get(j + 1).getStockOnHand()) {
                    InventoryItem temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                    swapCount++;
                }
            }
        }
        System.out.println("[Bubble Sort] Total swap: " + swapCount + " operasi");
    }

    /**
     * BUBBLE SORT BY ID - Ascending
     * Kompleksitas: O(n²) dengan nested loop
     */
    private void bubbleSortById(List<InventoryItem> items) {
        int n = items.size();
        int swapCount = 0;
        
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (items.get(j).getItemId().compareToIgnoreCase(items.get(j + 1).getItemId()) > 0) {
                    InventoryItem temp = items.get(j);
                    items.set(j, items.get(j + 1));
                    items.set(j + 1, temp);
                    swapCount++;
                }
            }
        }
        System.out.println("[Bubble Sort] Total swap: " + swapCount + " operasi");
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

        System.out.println("+----------+----------------------+-----------------+----------+-----------------+------------+-------+");
        System.out.printf("| %-8s | %-20s | %-15s | %-8s | %-15s | %-10s | %-5s |\n",
                "ID", "Nama", "Kategori", "Fragile", "Exp. Date", "Lokasi", "Stok");
        System.out.println("+----------+----------------------+-----------------+----------+-----------------+------------+-------+");

        for (InventoryItem item : items) {
            String date = item.getExpirationDate() == null ? "-" : item.getExpirationDate().toString();
            System.out.printf("| %-8s | %-20s | %-15s | %-8s | %-15s | %-10s | %-5d |\n",
                    item.getItemId(), item.getItemName(), item.getItemCategory(),
                    item.isFragile() ? "Yes" : "No", date, item.getLocation(), 
                    item.getStockOnHand());
        }
        System.out.println("+----------+----------------------+-----------------+----------+-----------------+------------+-------+");
    }
}