import java.util.*;

/**
 * Binary Tree untuk hierarki kategori barang
 * Implementasi ALGORITMA: Tree Traversal (DFS - Depth First Search)
 * Setiap node memiliki leftChild dan rightChild untuk struktur binary tree
 */

public class CategoryNode {
    private String categoryName;
    private CategoryNode leftChild;   // Left child untuk binary tree
    private CategoryNode rightChild;  // Right child untuk binary tree
    private List<InventoryItem> storageItems;

    public CategoryNode(String categoryName) {
        this.categoryName = categoryName;
        this.leftChild = null;
        this.rightChild = null;
        this.storageItems = new ArrayList<>();
    }

    // Binary tree setters
    public void setLeftChild(CategoryNode left) {
        this.leftChild = left;
    }

    public void setRightChild(CategoryNode right) {
        this.rightChild = right;
    }

    // Getters
    public String getCategoryName() {
        return categoryName;
    }

    public CategoryNode getLeftChild() {
        return leftChild;
    }

    public CategoryNode getRightChild() {
        return rightChild;
    }

    public List<InventoryItem> getStorageItems() {
        return storageItems;
    }
}