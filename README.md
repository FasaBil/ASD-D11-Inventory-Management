## Sistem Manajemen Inventaris Gudang

##  Informasi Proyek

| Aspek | Detail |
|-------|--------|
| **Mata Kuliah** | Algoritma dan Struktur Data (ASD) |
| **Topik** | Warehouse Management System |
| **Dosen Pengampu** | Renny Pradina K. |
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

**Perbedaan Bubble Sort dengan Algoritma Sorting Lain:**

| Aspek | Bubble Sort | Selection Sort | Insertion Sort |
|-------|-------------|----------------|----------------|
| **Perbandingan** | Adjacent elements (j, j+1) | Cari index minimum di seluruh unsorted part | Bandingkan dengan sorted part |
| **Operasi Utama** | **SWAP adjacent** setiap kali tidak urut | SWAP sekali per pass | **SHIFT** elemen, lalu INSERT |
| **Loop Dalam** | `j < n - i - 1` (kurangi dari belakang) | `j = i + 1` (mulai dari i+1) | `while (j >= 0)` (loop mundur) |
| **Jumlah Swap** | **Banyak** (setiap elemen tidak urut) | Sedikit (n-1 kali saja) | Tidak ada swap (pakai shift) |
| **Karakteristik** | Elemen besar "menggelembung" ke akhir | Pilih minimum, taruh di awal | Insert elemen ke posisi tepat di sorted part |
| **Variable Tambahan** | Hanya `temp` untuk swap | `minIndex` untuk track minimum | `key` untuk simpan elemen yang diinsert |
| **Best Case** | O(n) jika sudah terurut (dengan optimasi) | O(n²) tetap | O(n) jika sudah terurut |

**Ciri Khas Bubble Sort yang Membedakan:**
1. **Compare adjacent elements** - Selalu membandingkan elemen bersebelahan (j dengan j+1)
2. **Multiple swaps** - Melakukan swap berkali-kali dalam satu pass
3. **Bubble up pattern** - Elemen terbesar "naik" ke posisi akhir setiap iterasi
4. **Loop reduction** - Loop dalam berkurang (`n - i - 1`) karena elemen terbesar sudah di akhir

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


Priority Queue dalam proyek ini **BUKAN hanya import library**, tapi **implementasi algoritma heap sorting manual** melalui `Comparable` interface. Berikut alasannya:

**1. Implementasi Manual Algoritma Comparison (Comparable Interface)**
```java
// InventoryItem.java - Manual implementation
@Override
public int compareTo(InventoryItem other) {
    // Algoritma perbandingan manual dengan null handling
    if (this.expirationDate == null && other.expirationDate == null) return 0;
    if (this.expirationDate == null) return 1;  // Null = prioritas rendah
    if (other.expirationDate == null) return -1;
    
    // Comparison logic: tanggal lebih awal = prioritas lebih tinggi
    return this.expirationDate.compareTo(other.expirationDate);
}
```

**2. Algoritma yang Diimplementasikan:**
- **Comparison Algorithm** - Logic untuk menentukan urutan prioritas (lines 66-72)
- **Null Handling** - Edge case handling untuk tanggal null
- **Min-Heap Property** - Item dengan tanggal paling awal di root

**3. Perbedaan dengan "Sekedar Import Library":**

| Aspek | Sekedar Import Library | Implementasi Kami |
|-------|----------------------|-------------------|
| **Code** | `queue.add(item)` tanpa logic | **Implement `compareTo()`** dengan logic manual |
| **Sorting Logic** | Default sorting (tidak terlihat) | **Custom sorting berdasarkan expiration date** |
| **Algorithm Visibility** | Tersembunyi di library | **Algoritma comparison terlihat jelas di code** |
| **Edge Cases** | Default handling | **Manual null handling dan conditional logic** |
| **Complexity** | Tidak terlihat | **O(log n) heapify terlihat dari implementasi** |

**4. Algoritma Heap yang Bekerja di Balik PriorityQueue:**

Saat `compareTo()` dipanggil, PriorityQueue melakukan:
- **Heapify Up** (saat insert) - O(log n): Bandingkan dengan parent, swap jika perlu
- **Heapify Down** (saat poll) - O(log n): Reorder heap setelah remove root
- **Comparison** menggunakan **ALGORITMA MANUAL** yang kita tulis di `compareTo()`

**5. Bukti dalam kode**

```java
// Tanpa implementasi compareTo(), code ini ERROR!
PriorityQueue<InventoryItem> queue = new PriorityQueue<>();  // ❌ Compile error

// Karena InventoryItem HARUS implement Comparable dengan ALGORITMA manual
public class InventoryItem implements Comparable<InventoryItem> {
    @Override
    public int compareTo(InventoryItem other) {
        // ALGORITMA INI YANG MENENTUKAN CARA SORTING!
        return this.expirationDate.compareTo(other.expirationDate);
    }
}
```

**Kesimpulan:**
Priority Queue di proyek ini ***implementasi algoritma comparison manual** yang:
1. Ditulis sendiri di method `compareTo()`
2. Menentukan logic prioritas (tanggal lebih awal = prioritas tinggi)
3. Handle edge cases (null dates)
4. Menggunakan konsep heap/tree structure dengan kompleksitas O(log n)
5. Algoritma terlihat jelas dan bisa dijelaskan step-by-step

**Menunjang Fitur:** Deteksi dan manajemen barang expired secara otomatis, dengan prioritas pada barang yang paling dekat expired untuk ditangani terlebih dahulu.

---

##  Struktur File

```
ASD-D5-Inventory-Management/
│
├── kode/                        # Folder kode 
│   ├── Main.java                # UI Console & Menu
│   ├── WarehouseManager.java    # Core Logic & 4 Algoritma
│   ├── CategoryNode.java        # Binary Tree Structure
│   └── InventoryItem.java       # Data Model & Comparable
│
├── data/
│   └── inventory_data.txt       # Data persistence
│
├── presentasi/                  # Folder presentasi 
│   └── Final Project ASD Kelompok 11.pdf
├── README.md                    # Dokumentasi utama (file ini)
├── .gitignore                   # Git ignore rules
```

---

##  Contoh Penggunaan

### **1. Tambah Barang Baru**
```
--- Tambah Barang Baru ---
ID Barang (Ex: MK01, NM01): MK01
Nama Barang: Spring Water
Kategori Barang:
  Makanan: Beras, Gula, Minyak, Telur, Susu UHT, Susu Bubuk, Mie Instan, Roti,
           Kecap, Saus, Bumbu, Teh, Kopi, Biskuit, Kerupuk, Sarden, dll.
  Non-Makanan: Perlengkapan Rumah, Air Minum / Kemasan, Kebersihan & Kesehatan
Masukkan kategori: Non-Makanan
Fragile? (true/false): true
Expiration Date (YYYY-MM-DD / Kosong): 2025-12-31
Lokasi diatur otomatis ke: R-01
Stok Tersedia: 100

[Tree Traversal DFS] Memulai pencarian kategori untuk: Spring Water
  [DFS] Mengunjungi node: All Categories
  [DFS] Mencari di left subtree dari: All Categories
  [DFS] Mengunjungi node: Non-Makanan
  [DFS] ✓ Match! Item 'Spring Water' ditambahkan ke node: Non-Makanan

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
[Bubble Sort] Total swap: 4 operasi

--- NOTIFIKASI: BARANG AKAN EXPIRED DALAM 14 HARI ---
+----------+----------------------+-----------------+----------+-----------------+------------+-------+
| ID       | Nama                 | Kategori        | Fragile  | Exp. Date       | Lokasi     | Stok  |
+----------+----------------------+-----------------+----------+-----------------+------------+-------+
| MK18     | Bawang Merah 1kg     | Makanan         | Yes      | 2025-12-27      | R-B8       | 75    |
| MK17     | Kentang 1kg          | Makanan         | Yes      | 2025-12-25      | R-B7       | 60    |
| MK16     | Roti Tawar           | Makanan         | Yes      | 2025-12-22      | R-B6       | 40    |
| MK05     | Telur Ayam           | Makanan         | Yes      | 2025-12-28      | R-A5       | 200   |
+----------+----------------------+-----------------+----------+-----------------+------------+-------+
```
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
<img width="543" height="308" alt="image" src="https://github.com/user-attachments/assets/89132b98-0e93-4979-ad73-2b8ceeb80a26" />

### Penambahan Barang dengan Tree Traversal
<img width="826" height="406" alt="image" src="https://github.com/user-attachments/assets/3f25195a-e70e-4dda-aca9-53543de98c03" />
<img width="664" height="525" alt="image" src="https://github.com/user-attachments/assets/862d8430-a8f5-4675-97a4-2f9a58a83fda" />

### Pencarian dengan Linear Search
<img width="1046" height="501" alt="image" src="https://github.com/user-attachments/assets/c45a32b3-16e1-4f02-92fd-6fc836c93406" />

### Sorting dengan Bubble Sort
<img width="1048" height="527" alt="image" src="https://github.com/user-attachments/assets/dc810903-b1fb-4e7c-b1d2-cb7dd2c09559" />

## Check Expired Items
<img width="1037" height="262" alt="image" src="https://github.com/user-attachments/assets/4ba92d7b-ad71-45aa-8d48-1ecb5d95261f" />


---
## Daftar Kelompok Lain

| No | Nama | GitHub |
|----|------|--------|
| 1  | D-1  | https://github.com/NashiwaInsan/asdfinalproject |
| 2  | D-2  | https://github.com/dedyirama-id/kael-recommendation-system |
| 3  | D-3  | https://github.com/Sudukk/FP_ASD_Traffic_Light_Simulation_FINAL |
| 4  | D-4  | https://github.com/dreadf/hotelseek |
| 5  | D-5  |  |
| 6  | D-6  | https://github.com/anggraitapr/ASDFPTODOLIST |
| 7  | D-7  | https://github.com/WilliamHanantha/FP-ASD |
| 8  | D-8  | https://github.com/tyr3x74/GymPlan |
| 9  | D-9  | https://github.com/mariaelvina/FinalProjectD9 |
| 10 | D-10 | https://github.com/Aida41104/FPASD |
| 11 | D-11 | https://github.com/FasaBil/ASD-D11-Inventory-Management |
| 12 | D-12 | https://github.com/Dziky05/FP-ASD-KEL-13 |
| 13 | D-13 | https://github.com/FashaAsshofa/Final-Project-ASD-D-Kelompok-13 |
| 14 | D-14 | https://github.com/neutralcheeze/final-project-asd.git |
---



