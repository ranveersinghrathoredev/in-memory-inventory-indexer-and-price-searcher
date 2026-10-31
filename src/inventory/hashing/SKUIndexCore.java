package inventory.hashing;
import inventory.model.Product;

public class SKUIndexCore {

    private static final int HASH_BUCKETS = 20011;
    private final HashNode[] hashTable = new HashNode[HASH_BUCKETS];


    private static class HashNode {

        Product product;
        HashNode next;

        HashNode(Product product) {

            this.product = product;
            this.next = null;

        }
    }

    private int calculateDJB2Hash(String str) {
        long hash = 5381;

        for(int i = 0; i < str.length(); i++) {
            hash = ((hash << 5) + hash) + str.charAt(i);
        }
        int index = (int) (hash % HASH_BUCKETS);
        return index < 0 ? index + HASH_BUCKETS : index;
    }

    public void insertIntoHashTable(Product p) {
        int bucket = calculateDJB2Hash(p.getSku());
        HashNode newNode = new HashNode(p);

        if(hashTable[bucket] == null) {
            hashTable[bucket] = newNode;
        }
        else {
            HashNode current = hashTable[bucket];
            while(current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public Product lookupSKU(String sku) {
        long startTime = System.nanoTime();
        int bucket = calculateDJB2Hash(sku);
        HashNode current = hashTable[bucket];
        int collisionsChecked = 0;

        while(current != null) {
            if(current.product.getSku().equals(sku)) {
                long endTime = System.nanoTime();
                System.out.printf("\n [!] SEARCH SUCCESSFUL (Custom Hash Table Lookup)\n");
                System.out.printf(" 📊 Performance Metric: Checked %d collision node(s).\n", collisionsChecked);
                System.out.printf(" Total Execution Time: %d nanoseconds (O(1) Speed).\n",
                        (endTime - startTime));
                return current.product;
            }
            collisionsChecked++;
            current = current.next;
        }
        long endTime = System.nanoTime();
        System.out.printf(" [!] Search completed in %d nanoseconds. No match.\n",
                (endTime - startTime));
        return null;
    }

}
