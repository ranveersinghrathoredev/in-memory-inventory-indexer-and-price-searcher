package inventory.sorting;
import inventory.model.Product;
import java.util.Random;

public class PriceSorter {

    public int[] sortedPriceIndex;
    private Product[] dbRef;
    private int totalItems;

    public void initializeStorage(Product[] primaryDatabase, int currentSize) {
        this.dbRef = primaryDatabase;
        this.totalItems = currentSize;
        this.sortedPriceIndex = new int[currentSize];

         for(int i = 0; i < currentSize; i++) {
            sortedPriceIndex[i] = i;
        }
    }

    public void sortPriceIndex() {
        randomizedQuickSort(0, totalItems - 1);
    }

    private void randomizedQuickSort(int low, int high) {
        if(low < high) {

            int pivotIndex = partition(low, high);

            randomizedQuickSort(low, pivotIndex - 1);
            randomizedQuickSort(pivotIndex + 1, high);
        }

    }

    private int partition(int low, int high) {

        Random rand = new Random();
        int pivotIndex = rand.nextInt(high - low + 1) + low;

        int tempIdx = sortedPriceIndex[pivotIndex];
        sortedPriceIndex[pivotIndex] = sortedPriceIndex[high];
        sortedPriceIndex[high] = tempIdx;

        double pivotPrice = dbRef[sortedPriceIndex[high]].getPrice();

        int i = low - 1;

        for(int j = low; j < high; j++) {
            if(dbRef[sortedPriceIndex[j]].getPrice() <= pivotPrice) {
                i++;
                int temp = sortedPriceIndex[i];
                sortedPriceIndex[i] = sortedPriceIndex[j];
                sortedPriceIndex[j] = temp;
            }
        }

        int temp = sortedPriceIndex[i + 1];
        sortedPriceIndex[i + 1] = sortedPriceIndex[high];
        sortedPriceIndex[high] = temp;

        return i + 1;
    }
}
