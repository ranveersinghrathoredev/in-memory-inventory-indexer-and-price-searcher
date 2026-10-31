package inventory.searching;
import inventory.model.Product;
public class RangeSearcher {

    public void rangeSearch(Product[] dbRef, int[] sortedPriceIndex, int totalItems, double minPrice, double maxPrice) {

        long startTime = System.nanoTime();

        int lowerBoundIdx = -1;
        int upperBoundIdx = -1;

        int low = 0;
        int high = totalItems - 1;
        // lowerSearchSteps simply counts how many binary-search iterations we perform.
        int lowerSearchSteps = 0;

        while(low <= high) {

            lowerSearchSteps++;
            int mid = low + (high - low) / 2;
            double midPrice = dbRef[sortedPriceIndex[mid]].getPrice();

            if(midPrice >= minPrice) {
                lowerBoundIdx = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }

        low = 0;
        high = totalItems - 1;
        int upperSearchSteps = 0;

        while(low <= high) {
            upperSearchSteps++;
            int mid = low + (high - low) / 2;
            double midPrice = dbRef[sortedPriceIndex[mid]].getPrice();

            if(midPrice <= maxPrice) {

                upperBoundIdx = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        long endTime = System.nanoTime();

        if(lowerBoundIdx == -1 || upperBoundIdx == -1 || lowerBoundIdx > upperBoundIdx) {
            System.out.println("\n [!] No products found inside specified budget criteria. ");
            return;
        }

        int matchesFound = (upperBoundIdx - lowerBoundIdx) + 1;
        System.out.printf("\n [!] RANGE QUERY RESULTS (Dual Binary Search Bounding) \n");
        System.out.printf(" ──────────────────────────────────────────────────────────\n");

        int printLimit = Math.min(upperBoundIdx, lowerBoundIdx + 9);
        for(int i = lowerBoundIdx; i <= printLimit; i++) {
            Product p = dbRef[sortedPriceIndex[i]];

            System.out.printf(
                    "   %s     | %s     | Price: $%.2f     |   Stock: %d%n",
                    p.getSku(),
                    p.getName(),
                    p.getPrice(),
                    p.getStock()
            );
        }

        if(matchesFound > 10) {
            System.out.printf(
                    " ... and %d more products matching criteria. %n",
                    matchesFound - 10
            );
        }

        System.out.printf("  ──────────────────────────────────────────────────────────%n");

        System.out.printf(
                "  Performance Metric: Iteration Steps (Lower/Upper): %d / %d%n",
                lowerSearchSteps,
                upperSearchSteps
        );

        System.out.printf(
                "  Total Bounding Execution Time: %d nanoseconds (O(log N) Speed).%n",
                (endTime - startTime)
        );

    }
}
