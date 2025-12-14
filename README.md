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

##  Latar Belakang & Rumusan Masalah

Pengelolaan stok di toko sembako menengah masih banyak dilakukan secara manual, tanpa standar penataan yang jelas. Dalam kondisi nyata, ruang gudang sering memiliki kapasitas terbatas, sehingga pemilik maupun karyawan harus memaksimalkan area penyimpanan yang ada. Situasi ini membuat barang sering ditumpuk atau digabungkan tanpa memperhatikan kategori atau karakteristiknya.

### Permasalahan Umum

**1. Pencarian barang tidak efisien**  
Tanpa sistem kategori yang terstruktur, karyawan membutuhkan waktu lama untuk menemukan barang tertentu. Ada barang yang terselip, tertumpuk, atau dipindahkan ke tempat lain.

**2. Barang kadang dianggap habis padahal masih tersisa**  
Karena penataan gudang tidak rapi, beberapa barang tidak terlihat atau berada di belakang tumpukan lainnya. Karyawan mengira stok sudah habis, padahal sebenarnya masih tersimpan di gudang.

**3. Risiko barang expired meningkat**  
Barang makanan tetap memiliki risiko kedaluwarsa karena kurangnya pemantauan dan pencatatan tanggal expired. Owner sering tidak menyadari barang mana yang paling dekat masa kadaluarsanya.

**4. Kerusakan barang yang bersifat fragile**  
Barang yang mudah pecah seperti botol kaca atau barang kemasan tipis sering rusak karena tidak ada zona khusus atau aturan penumpukan. Ketika gudang penuh, karyawan akan menumpuk barang sembarangan demi menghemat tempat. Selain itu ada resiko kontaminasi bau akibat pencampuran produk tertentu dengan produk sabun, detergen, pembersih.

**5. Keterbatasan kapasitas gudang (Storage Limitation)**  
Karena ruang gudang tidak luas, setiap kesalahan penataan akan memperparah kondisi. Barang yang datang dalam jumlah besar sering dimasukkan ke ruang kosong terdekat tanpa mempertimbangkan kategorinya.

**6. Ketergantungan pada karyawan (Human Error)**  
Pemilik toko sering tidak mengetahui detail lokasi barang di dalam gudang. Pengetahuan tentang penempatan barang biasanya hanya ada pada karyawan yang mengatur gudang.

---

##  Solusi yang Ditawarkan

Dengan adanya berbagai macam masalah tersebut, dibutuhkan sebuah sistem sederhana untuk membantu mengorganisir gudang secara rapi dan mudah untuk dipahami baik oleh owner maupun karyawan dengan fitur sebagai berikut:

- Pengelompokan kategori barang secara hierarkis menggunakan Binary Tree
- Pemisahan zona makanan & non-makanan dengan sub-kategori yang terstruktur
- Pendeteksian barang yang hampir expired menggunakan Priority Queue
- Akses cepat stok barang dengan HashMap (O(1) lookup)
- Penyimpanan data yang fleksibel dengan file persistence
- Pencarian barang dengan Linear Search untuk partial matching
- Sorting barang berdasarkan berbagai kriteria (nama, stok, ID)

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
                 |
     --------------------------------
     |                              |
 "Makanan"                     "Non-Makanan"
     |
     |
-------------------------------------------------------------------------------------
|                 |                |                |                 |             |
"Sembako Utama"   "Bumbu &         "Mie & Produk    "Telur & Produk   "Minuman"    "Makanan
                  Penyedap"         Gandum"          Susu"                           Ringan"
|                 |                |                |                 |             |
|                 |                |                |                 |             |
---------      ----------------   ------------    -----------------   ------------------   ------------------
|   |   |      |   |    |        |         |      |      |        |   |     |        |   |         |
Beras Gula Minyak  Kecap Saus  Bumbu     Mie     Roti    Telur  Susu UHT Susu Bubuk  Teh Kopi Min. Kemasan Biskuit Kerupuk


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
**File/Class Implementasi:** `WarehouseManager.java` - Method `addItemToCategoryTreeRecursive()` (lines 163-186)  
**Cara Kerja:** Algoritma ini melakukan traversal pre-order pada Binary Tree untuk mencari kategori yang sesuai dengan item. Dimulai dari root node, algoritma mengunjungi node saat ini, kemudian rekursif ke left child, dan akhirnya ke right child. Jika kategori cocok, item ditambahkan ke storage list node tersebut.  
**Menunjang Fitur:** Penambahan barang otomatis ke kategori yang tepat dalam struktur hierarki tree.

### 2. **Linear Search** - O(n)
```java
// Manual loop - iterasi satu per satu
for (InventoryItem item : quickAccessMap.values()) {
    if (item.getItemName().toLowerCase().contains(searchKey)) {
        result.add(item);
    }
}
```
**File/Class Implementasi:** `WarehouseManager.java` - Method `searchItemByName()` (lines 279-302)  
**Cara Kerja:** Algoritma ini melakukan pencarian sekuensial melalui semua item dalam HashMap. Setiap item dibandingkan satu per satu dengan keyword pencarian (case-insensitive, partial matching). Jika nama item mengandung keyword, item tersebut ditambahkan ke hasil pencarian.  
**Menunjang Fitur:** Pencarian barang berdasarkan nama dengan partial matching, memungkinkan user menemukan barang tanpa harus mengetik nama lengkap.

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
**File/Class Implementasi:** `WarehouseManager.java` - Methods `bubbleSortByName()` (lines 367-383), `bubbleSortByStockOnHand()` (lines 391-407), `bubbleSortByItemId()` (lines 415-431)  
**Cara Kerja:** Algoritma sorting sederhana yang membandingkan dua elemen bersebelahan dan menukar posisinya jika urutannya salah. Proses ini diulang hingga seluruh list terurut. Menggunakan nested loop dimana loop luar menentukan pass dan loop dalam melakukan perbandingan dan swap.  
**Menunjang Fitur:** Sorting barang berdasarkan berbagai kriteria (nama A-Z, stok descending, ID) untuk memudahkan analisis dan pelaporan.

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
**File/Class Implementasi:**  
- `InventoryItem.java` - Method `compareTo()` (lines 66-72) untuk implementasi Comparable  
- `WarehouseManager.java` - Method `checkAndMoveExpiredItems()` (lines 230-254) untuk penggunaan Priority Queue

**Cara Kerja:** Menggunakan implementasi min-heap melalui Java PriorityQueue dengan custom Comparable. Item dengan expiration date paling awal akan berada di root heap. Saat item expired ditambahkan, heap secara otomatis melakukan heapify up untuk menjaga property heap. Implementasi Comparable yang manual membuat item dapat dibandingkan berdasarkan tanggal expired.  
**Menunjang Fitur:** Deteksi dan manajemen barang expired secara otomatis, dengan prioritas pada barang yang paling dekat expired untuk ditangani terlebih dahulu.

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
├── inventory_data.txt           # Data persistence
│
├── README.md                    # Dokumentasi (file ini)
├── ALGORITMA_EXPLANATION.md     # Penjelasan teknis lengkap
└── PRESENTASI_RINGKASAN.md      # Ringkasan untuk presentasi
```

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
Urutkan berdasarkan (ID / NAME / STOCK): NAME

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
| **Bubble Sort** | O(n²) | O(1) | `bubbleSortByName/Stock/Id()` |
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
MK06;Susu Bubuk 400g;Makanan;true;2026-05-20;R-A6;60
MK07;Tepung Terigu 1kg;Makanan;false;-;R-A7;80
MK08;Sarden Kaleng;Makanan;true;2027-02-01;R-A8;110
MK09;Kopi Sachet;Makanan;false;-;R-A9;250
MK02;Gula Pasir 1kg;Makanan;false;-;R-A2;90
MK03;Minyak Goreng 1L;Makanan;false;-;R-A3;150
MK04;Mie Instan Goreng;Makanan;true;2026-01-10;R-A4;300
MK05;Telur Ayam;Makanan;true;2025-12-28;R-A5;200
MK10;Teh Celup 25pcs;Makanan;false;-;R-A10;140
MK11;Biskuit Kaleng;Makanan;true;2026-04-11;R-B1;70
NM21;Sabun Mandi Batang;Non-Makanan;false;-;N-C1;200
MK12;Kecap Manis 600ml;Makanan;false;-;R-B2;95
NM22;Shampoo Sachet;Non-Makanan;false;-;N-C2;300
MK17;Kentang 1kg;Makanan;true;2025-12-25;R-B7;60
NM27;Plastik Kresek;Non-Makanan;false;-;N-C7;500
MK18;Bawang Merah 1kg;Makanan;true;2025-12-27;R-B8;75
NM28;Gas Elpiji 3kg;Non-Makanan;false;-;N-C8;40
MK19;Bawang Putih 1kg;Makanan;true;2026-01-02;R-B9;80
NM29;Obat Nyamuk Bakar;Non-Makanan;false;-;N-C9;120
MK13;Saus Sambal 135ml;Makanan;false;-;R-B3;100
NM23;Pasta Gigi 100g;Non-Makanan;false;-;N-C3;150
MK14;Susu UHT 1L;Makanan;true;2026-02-18;R-B4;50
NM24;Detergen Bubuk 1kg;Non-Makanan;false;-;N-C4;100
MK15;Air Mineral 600ml;Makanan;false;-;R-B5;300
NM25;Pewangi Pakaian;Non-Makanan;false;-;N-C5;80
MK16;Roti Tawar;Makanan;true;2025-12-22;R-B6;40
NM26;Tisu Wajah;Non-Makanan;false;-;N-C6;90
MK20;Ikan Asin 250g;Makanan;true;2026-03-15;R-B10;40
NM30;Sikat Gigi;Non-Makanan;false;-;N-C10;150
MK01;Beras Premium 5kg;Makanan;false;-;R-A1;120
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

##  Screenshot Program

### Menu Utama
![Menu Utama](https://via.placeholder.com/600x300?text=Menu+Utama+Warehouse+Management)

### Penambahan Barang dengan Tree Traversal
![Tree Traversal](https://via.placeholder.com/600x300?text=DFS+Tree+Traversal)

### Pencarian dengan Linear Search
![Linear Search](https://via.placeholder.com/600x300?text=Linear+Search+Result)

### Sorting dengan Bubble Sort
![Bubble Sort](https://via.placeholder.com/600x300?text=Bubble+Sort+Output)

---

##  Proyek Kelompok Lain

Berikut adalah daftar repositori proyek akhir dari kelompok lain di kelas D:

| Kelompok | Judul Proyek | Link Repository |
|----------|--------------|-----------------|
| D-1 | TBD | TBD |
| D-2 | TBD | TBD |
| D-3 | TBD | TBD |
| D-4 | TBD | TBD |
| D-5 | TBD | TBD |
| D-6 | TBD | TBD |
| D-7 | TBD | TBD |
| D-8 | TBD | TBD |
| D-9 | TBD | TBD |
| D-10 | TBD | TBD |
| D-12 | TBD | TBD |

---

##  Update Log

### Update 1 - [Tanggal akan diisi saat ada update]
**Deskripsi Update:** -  
**File yang Diubah:** -  
**Link Video (jika ada perubahan UI):** -

---

##  Catatan Penting

- Batas waktu penilaian: **Minggu, 14 Desember 2025, pukul 16.00**
- Update program diperbolehkan hingga waktu tersebut
- Setiap update akan didokumentasikan dalam Update Log di atas

---



