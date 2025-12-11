# Warehouse Management System
## Sistem Manajemen Inventaris Gudang

[![Java](https://img.shields.io/badge/Java-17+-orange.svg)](https://www.oracle.com/java/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Status](https://img.shields.io/badge/Status-Active-success.svg)]()

---

##  Informasi Proyek

| Aspek | Detail |
|-------|--------|
| **Mata Kuliah** | Algoritma dan Struktur Data (ASD) |
| **Kelas** | D |
| **Kelompok** | D-11 |
| **Semester** | Gasal 2025/2026 |

---

##  Anggota Kelompok

| Nama | NRP |
|------|-----|
| Satya Azka Aqilla | 5026241199 |
| M. Azwar Alfarezzel Dharmawantara | 5026241079 |
| Adhima Anfasa Bilqi | 5026244167 |

---

##  Deskripsi Proyek

Warehouse Management System adalah aplikasi console-based untuk mengelola inventaris gudang dengan implementasi **4 algoritma dan struktur data**:

1. **Tree Traversal (DFS)** - Recursive Depth-First Search untuk kategori barang
2. **Linear Search** - Sequential search dengan partial matching
3. **Sorting (Bubble Sort)** - Manual nested loop sorting
4. **Priority Queue (Min-Heap)** - Binary heap untuk expired items

---

##  Fitur Utama

###  **Manajemen Inventaris**
-  Tambah barang baru (dengan validasi kategori & duplikasi)
-  Hapus barang berdasarkan ID
-  Auto-detect expired items & pindahkan ke storage terpisah
-  Notifikasi barang yang akan expired (14 hari)
-  Persistensi data ke file (`inventory_data.txt`)

###  **Pencarian & Filtering**
-  **Linear Search** - Cari barang berdasarkan nama (partial match)
-  **Filter** - By kategori (Material/Finished Goods) & fragile status
-  **Tree Traversal** - Navigasi kategori menggunakan Binary Tree

###  **Sorting**
-  Sort by **Nama** (A-Z)
-  Sort by **Stok** (Descending)
-  Sort by **ID**
-  Implementasi manual **Bubble Sort** (O(n²))

###  **Expired Management**
-  **Priority Queue** - Auto-sort expired items by date
-  Item dengan expiration date paling awal di prioritas
-  Binary heap structure (O(log n) insert)

---

##  Struktur Data yang Digunakan

### 1️ **Binary Tree** (CategoryNode.java)
```
Root: "All Categories"
              /                               \
     "Non-Makanan"                       "Makanan"
           |                                   |
           |                                   |
   ----------------                    ---------------------
   |              |                    |        |          |
"Kebutuhan   "Kebersihan &     "Sembako Utama"  "Telur & Produk Susu"
Rumah Tangga"   Kesehatan"            |                     |
   |              |                   |                     |
   |              |            ----------------       -----------------
   |              |            |      |     |         |       |      |
   |              |         "Beras" "Gula" "Minyak" "Telur" "Susu UHT" "Susu Bubuk"
   |              |
   |              |
   |         "Perlengkapan Rumah"
   |              |
   |         "Air Minum / Kemasan"
   |
   |
--------------------------------------------------------------------------------------------------
                                      |
                                      |
                           "Mie & Produk Gandum"
                                      |
                               ----------------
                              |                |
                          "Mie Instan"     "Roti"
                                     
                                      |
                                      |
                               "Bumbu & Penyedap"
                                      |
                     -----------------------------------
                    |           |           |           |
                 "Kecap"     "Saus"     "Bumbu"     "Bumbu Kering"
                                          |             |
                                   (penyedap)   (bawang kering, cabe kering, dll)

                                      |
                                      |
                                  "Minuman"
                                      |
                    ----------------------------------
                    |                |               |
                 "Teh"             "Kopi"        "Sirup / Minuman Botol"

                                      |
                                      |
                               "Makanan Ringan"
                                      |
                              -------------------
                              |                 |
                           "Biskuit"        "Kerupuk"

                                      |
                                      |
                               "Kaleng & Olahan"
                                      |
                              -------------------
                              |                 |
                           "Sarden"          "Olahan Lain"

                                      |
                                      |
                                 "Bahan Mentah"
                                      |
                 -------------------------------------------------
                 |             |              |                 |
             "Jagung"     "Kedelai"   "Kacang Tanah"   "Gula Merah"
```
- **Kegunaan:** Hierarki kategori barang
- **Algoritma:** DFS Pre-order Traversal
- **Kompleksitas:** O(h) - h = tinggi tree

### 2️ **HashMap** (quickAccessMap)
- **Kegunaan:** Quick access item by ID
- **Kompleksitas:** O(1) untuk get/put

### 3️ **Priority Queue** (expiredQueue)
- **Kegunaan:** Auto-sort expired items (min-heap)
- **Implementasi:** Binary heap via `Comparable`
- **Kompleksitas:** O(log n) insert, O(1) peek

### 4️ **ArrayList**
- **Kegunaan:** Dynamic list untuk filtering & sorting
- **Digunakan di:** Bubble Sort, Linear Search

---

##  Algoritma yang Diimplementasikan

### 1. **Tree Traversal (DFS)** - O(h)
```java
// Recursive Pre-order: Root → Left → Right
private boolean addItemToCategoryTreeRecursive(CategoryNode node, InventoryItem item) {
    if (node == null) return false;
    
    if (node.getCategoryName().equals(item.getItemCategory())) {
        node.getStorageItems().add(item);
        return true;
    }
    
    return addItemToCategoryTreeRecursive(node.getLeftChild(), item) ||
           addItemToCategoryTreeRecursive(node.getRightChild(), item);
}
```

### 2. **Linear Search** - O(n)
```java
// Manual loop - iterasi satu per satu
for (InventoryItem item : quickAccessMap.values()) {
    if (item.getItemName().toLowerCase().contains(searchKey)) {
        result.add(item);
    }
}
```

### 3. **Bubble Sort** - O(n²)
```java
// Nested loop - compare & swap adjacent elements
for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
        if (items.get(j) > items.get(j + 1)) {
            swap(items, j, j + 1);
        }
    }
}
```

### 4. **Priority Queue (Heap)** - O(log n)
```java
// Min-heap via Comparable interface
@Override
public int compareTo(InventoryItem other) {
    return this.expirationDate.compareTo(other.expirationDate);
}

// Insert: O(log n) - heapify up
expiredQueue.offer(item);
```

---

##  Struktur File

```
ADS/
│
├── Main.java                    # UI Console & Menu
├── WarehouseManager.java        # Core Logic & 4 Algoritma
├── CategoryNode.java            # Binary Tree Structure
├── InventoryItem.java           # Data Model & Comparable
│
├── inventory_data.txt           # Data persistence (auto-generated)
│
├── README.md                    # Dokumentasi (file ini)
├── ALGORITMA_EXPLANATION.md     # Penjelasan teknis lengkap
└── PRESENTASI_RINGKASAN.md      # Ringkasan untuk presentasi
```

---

##  Cara Menjalankan

### **Prasyarat:**
- Java JDK 11 atau lebih tinggi
- Terminal/Command Prompt

### **Langkah-langkah:**

1. **Clone repository:**
   ```bash
   git clone https://github.com/FasaBil/ASD-D5-Inventory-Management.git
   cd ASD-D5-Inventory-Management
   ```

2. **Compile semua file Java:**
   ```bash
   javac *.java
   ```

3. **Jalankan program:**
   ```bash
   java Main
   ```

4. **Navigasi menu:**
   - Pilih menu dengan input angka (1-4, 0 untuk keluar)
   - Ikuti instruksi di layar

---

##  Contoh Penggunaan

### **1. Tambah Barang Baru**
```
--- Tambah Barang Baru ---
ID Barang (Ex: SB1, BB2): SB001
Nama Barang: Spring Water
Kategori Barang (Material / Finished Goods): Finished Goods
Fragile? (true/false): true
Expiration Date (YYYY-MM-DD / Kosong): 2025-12-31
Lokasi diatur otomatis ke: F-01
Stok Tersedia: 100

[Tree Traversal DFS] Memulai pencarian kategori untuk: Spring Water
  [DFS] Mengunjungi node: All Categories
  [DFS] Mencari di left subtree dari: All Categories
  [DFS] Mengunjungi node: Finished Goods
  [DFS] ✓ Match! Item 'Spring Water' ditambahkan ke node: Finished Goods

Barang berhasil ditambahkan!
```

### **2. Cari Barang (Linear Search)**
```
--- Cari Barang ---
Masukkan Nama Barang: water

[Linear Search] Mencari barang dengan keyword: "water"
[Linear Search] Kompleksitas: O(n) - Iterasi manual melalui semua item
  -> Match ditemukan: Spring Water (Perbandingan ke-5)
[Linear Search] Total perbandingan: 10 item
[Linear Search] Hasil ditemukan: 1 item
```

### **3. Sort Barang (Bubble Sort)**
```
--- Filter & Sort NON-Expired Items ---
Filter berdasarkan Kategori (Material / Finished Goods / ALL): ALL
Filter berdasarkan Fragile (YES / NO / ANY): ANY
Urutkan berdasarkan (ID / NAME / STOCK / PRICE): NAME

[Sorting] Menggunakan Bubble Sort - Kompleksitas O(n²)
[Sorting] Diurutkan berdasarkan NAMA (A-Z)
[Bubble Sort] Total swap: 12 operasi
```

### **4. Check Expired Items (Priority Queue)**
```
[Priority Queue] Mengecek item expired...
  [Heap Insert] Item expired dipindahkan: Milk | Exp: 2025-12-10 | Heap size: 1
[Priority Queue] Total item dipindahkan: 1
[Priority Queue] Item dengan expiration paling awal: Milk
```

---

##  Kompleksitas Algoritma

| Algoritma | Kompleksitas Waktu | Kompleksitas Ruang | Implementasi |
|-----------|-------------------|-------------------|--------------|
| **Tree Traversal (DFS)** | O(h) - O(n) worst | O(h) recursive stack | `addItemToCategoryTreeRecursive()` |
| **Linear Search** | O(n) | O(1) | `searchItemByName()` |
| **Bubble Sort** | O(n²) | O(1) | `bubbleSortByName/Stock/Price()` |
| **Priority Queue Insert** | O(log n) | O(n) heap storage | `expiredQueue.offer()` |
| **HashMap Access** | O(1) average | O(n) | `quickAccessMap.get()` |

---

##  Konsep yang Diterapkan

### **1. Rekursi**
- DFS Tree Traversal menggunakan rekursi
- Base case & recursive case yang jelas

### **2. Nested Loop**
- Bubble Sort dengan 2 loop (i dan j)
- Demonstrasi kompleksitas O(n²)

### **3. Heap (Binary Heap)**
- Priority Queue implementation
- Min-heap dengan `Comparable` interface
- Heapify up/down otomatis

### **4. Tree Structure**
- Binary Tree untuk hierarki
- Parent-child relationship
- Traversal recursive

### **5. Searching**
- Linear search manual (no Stream API)
- Partial matching untuk string

---

##  Perbaikan dari Versi Sebelumnya

### ** Masalah Sebelumnya:**
```java
// Hanya "memanggil" library, bukan implementasi algoritma
return stream().filter(...).collect(Collectors.toList());  // Linear Search
filtered.sort(comparator);  // Sorting (Timsort)
```

### ** Solusi Sekarang:**
```java
// Implementasi manual algoritma dengan kompleksitas terlihat jelas
for (InventoryItem item : items) {  // Linear Search O(n)
    if (condition) result.add(item);
}

for (int i = 0; i < n-1; i++) {  // Bubble Sort O(n²)
    for (int j = 0; j < n-i-1; j++) {
        if (items[j] > items[j+1]) swap();
    }
}
```

**Lihat:** `PRESENTASI_RINGKASAN.md` untuk detail perbaikan

---


##  Testing

### **Skenario Test:**
1.  Add item dengan kategori valid (Finished Goods / Material)
2.  Add item expired → langsung masuk expired queue
3.  Search dengan partial match (case-insensitive)
4.  Sort ascending/descending
5.  Filter by kategori & fragile status
6.  Delete item → hapus dari tree & map
7.  Check expired notification (14 hari)
8.  Persistensi data (save/load file)

---

---

##  Format Data (inventory_data.txt)

```
ID;Nama;Kategori;Fragile;ExpDate;Lokasi;Stok;
SB001;Spring Water;Finished Goods;true;2025-12-31;F-01;100;5000.0
MT001;Steel Plate;Material;false;-;M-A1;50;15000.0
```

**Field:**
- ID: String (unique)
- Nama: String
- Kategori: "Finished Goods" | "Material"
- Fragile: boolean
- ExpDate: YYYY-MM-DD atau "-" (no expiration)
- Lokasi: String (auto-assigned)
- Stok: int

---




