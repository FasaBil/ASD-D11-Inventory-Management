import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static WarehouseManager manager;
    private static final Scanner scanner = new Scanner(System.in);
    private static final LocalDate TODAY = LocalDate.now();

    public static void main(String[] args) {
        manager = new WarehouseManager();

        // Pengecekan kadaluarsa otomatis
        int movedCount = manager.checkAndMoveExpiredItems();
        if (movedCount > 0) {
            System.out.printf("%d barang telah kedaluwarsa dan dipindahkan ke Expired Storage.\n", movedCount);
        }

        // Pengecekan opsi
        int choice = -1;
        while (choice != 0) {
            displayMainMenu();
            System.out.print("Masukkan pilihan: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleCheckStorageMenu();
                        break;
                    case 2:
                        handleAddItemMenu();
                        break;
                    case 3:
                        handleDeleteItemMenu();
                        break;
                    case 4:
                        displayNearExpiryNotification();
                        break;
                    case 0:
                        System.out.println("Menyimpan data...");
                        manager.saveDataToFile();
                        System.out.println("Terima kasih! Program selesai.");
                        break;
                    default:
                        System.err.println("Pilihan tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Input harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    // UI console
    private static void displayMainMenu() {
        System.out.println("\n=============================================");
        System.out.println("     Console Manajemen Inventaris Gudang");
        System.out.println("            (Tanggal: " + TODAY + ")");
        System.out.println("=============================================");
        System.out.println("1. Cek Storage & Filter Barang");
        System.out.println("2. Tambahkan Barang Baru");
        System.out.println("3. Hapus Barang (Berdasarkan ID)");
        System.out.println("4. Cek Notifikasi Expired");
        System.out.println("0. Keluar");
        System.out.println("=============================================");
    }

    // Menu handling
    private static void handleCheckStorageMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n--- Cek Storage & Filter Barang ---");
            System.out.println("1. Storage NON-Expired (Filter & Sort)");
            System.out.println("2. Storage KHUSUS EXPIRED");
            System.out.println("3. Cari Barang Berdasarkan Nama");
            System.out.println("0. Kembali");
            System.out.print("Pilihan: ");

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        handleFilterAndSortMenu();
                        break;
                    case 2:
                        List<InventoryItem> expired = manager.getExpiredItems();
                        WarehouseManager.displayItemList(expired, "Storage EXPIRED");
                        break;
                    case 3:
                        handleSearchMenu();
                        break;
                    case 0:
                        break;
                    default:
                        System.err.println("Pilihan tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Input harus berupa angka!");
                scanner.nextLine();
            }
        }
    }

    private static void handleFilterAndSortMenu() {
        System.out.println("\n--- Filter & Sort NON-Expired Items ---");

        System.out.print("Filter berdasarkan Kategori (Makanan / Non-Makanan / ALL): ");
        String category = scanner.nextLine();

        System.out.print("Filter berdasarkan Fragile (YES / NO / ANY): ");
        String fragileStatus = scanner.nextLine();

        System.out.print("Urutkan berdasarkan (ID / NAME / STOCK): ");
        String sortOrder = scanner.nextLine();

        List<InventoryItem> results = manager.filterAndSort(category, fragileStatus, sortOrder);
        WarehouseManager.displayItemList(results, "Hasil Filter & Sort");
    }

    private static void handleSearchMenu() {
        System.out.println("\n--- Cari Barang ---");
        System.out.print("Masukkan Nama Barang: ");
        String keyword = scanner.nextLine();

        List<InventoryItem> results = manager.searchItemByName(keyword);
        WarehouseManager.displayItemList(results, "Hasil Pencarian untuk: " + keyword);
    }

    private static void handleAddItemMenu() {
        System.out.println("\n--- Tambah Barang Baru ---");

        System.out.print("ID Barang (Ex: MK01, NM01): ");
        String itemId = scanner.nextLine().toUpperCase();

        System.out.print("Nama Barang: ");
        String itemName = scanner.nextLine();

        System.out.println("Kategori Barang:");
        System.out.println("  Makanan: Beras, Gula, Minyak, Telur, Susu UHT, Susu Bubuk, Mie Instan, Roti,");
        System.out.println("           Kecap, Saus, Bumbu, Teh, Kopi, Biskuit, Kerupuk, Sarden, dll.");
        System.out.println("  Non-Makanan: Perlengkapan Rumah, Air Minum / Kemasan, Kebersihan & Kesehatan");
        System.out.print("Masukkan kategori: ");
        String itemCategory = scanner.nextLine();

        System.out.print("Fragile? (true/false): ");
        boolean isFragile = scanner.nextBoolean();
        scanner.nextLine();

        System.out.print("Expiration Date (YYYY-MM-DD / Kosong): ");
        String dateString = scanner.nextLine();
        LocalDate expirationDate = null;
        if (!dateString.trim().isEmpty()) {
            try {
                expirationDate = LocalDate.parse(dateString);
            } catch (DateTimeParseException e) {
                System.err.println("Format tanggal salah!");
                return;
            }
        }

        // Logika lokasi berdasarkan kategori dan ID
        String location;
        if (itemId.startsWith("MK")) {
            location = "R-" + itemId.substring(2); // Makanan: R-01, R-02, dst
        } else if (itemId.startsWith("NM")) {
            location = "N-" + itemId.substring(2); // Non-Makanan: N-01, N-02, dst
        } else {
            location = isFragile ? "F-01" : "B-01";
        }
        System.out.println("Lokasi diatur otomatis ke: " + location);

        System.out.print("Stok Tersedia: ");
        int stockOnHand = scanner.nextInt();
        scanner.nextLine();

        // Buat item baru
        InventoryItem newItem = new InventoryItem(itemId, itemName, itemCategory, isFragile, 
                                                 expirationDate, location, stockOnHand);
        if (manager.addItem(newItem)) {
            System.out.println("Barang berhasil ditambahkan!");
        } else {
            System.err.println("Gagal menambahkan barang (ID sudah ada atau kategori salah).");
        }
    }

    private static void handleDeleteItemMenu() {
        System.out.println("\n--- Hapus Barang ---");
        System.out.print("Masukkan ID Barang: ");
        String itemId = scanner.nextLine().toUpperCase();

        InventoryItem deleted = manager.deleteItem(itemId);
        if (deleted != null) {
            System.out.println("Barang berhasil dihapus: " + deleted.getItemName());
        } else {
            System.err.println("Barang dengan ID " + itemId + " tidak ditemukan.");
        }
    }

    // Notifikasi barang expired
    private static void displayNearExpiryNotification() {
        List<InventoryItem> nearExpiry = manager.getNearExpiryItems();
        if (nearExpiry.isEmpty()) {
            System.out.println("\nTidak ada barang yang akan expired dalam 14 hari.");
        } else {
            WarehouseManager.displayItemList(nearExpiry, "NOTIFIKASI: Barang akan Expired dalam 14 Hari");
        }
    }
}
