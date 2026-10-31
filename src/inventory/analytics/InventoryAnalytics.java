package inventory.analytics;

public class InventoryAnalytics {

    private static final int NUM_CATEGORIES = 4;
    private static final int NUM_PRICE_TIERS = 4;

    private final int[][] analyticsMatrix =
             new int[NUM_CATEGORIES][NUM_PRICE_TIERS];

    public void updateMetrics(int categoryId, double price) {
        int tier = (price <= 25.0) ? 0
                : (price <= 75.0) ? 1
                : (price <= 150.0) ? 2
                : 3;

        analyticsMatrix[categoryId][tier]++;
    }

    public void displayDashboard() {
        String[] categories = {
                "Electronics",
                "Apparel    ",
                "Home&Living",
                "Automotive "
        };

        System.out.println(
                "\nCATEGORY      │ BUDGET ($0-25) │ MID ($25-75) │ PREMIUM ($75-150) │ LUXURY ($150+)"
        );

        System.out.println(
                "──────────────┼────────────────┼──────────────┼───────────────────┼───────────────"
        );

        for(int i = 0; i < NUM_CATEGORIES; i++) {

            System.out.printf(
                    "%s        │          %d          │            %d           │              %d              │               %d             %n",
                    categories[i],
                    analyticsMatrix[i][0],
                    analyticsMatrix[i][1],
                    analyticsMatrix[i][2],
                    analyticsMatrix[i][3]
            );
        }
    }

}



