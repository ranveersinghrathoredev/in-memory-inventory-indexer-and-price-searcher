package inventory;
import java.util.Scanner;
import java.util.Random;
import inventory.model.Product;
import inventory.hashing.SKUIndexCore;
import inventory.sorting.PriceSorter;
import inventory.searching.RangeSearcher;
import inventory.analytics.InventoryAnalytics;

public class Main {

        private static final int MAX_ITEMS = 10000;
        private static final Product[] primaryDatabase = new Product[MAX_ITEMS];
        private static final SKUIndexCore hashingEngine = new SKUIndexCore();
        private static final PriceSorter sortingEngine = new PriceSorter();
        private static final RangeSearcher searchingEngine = new RangeSearcher();
        private static final InventoryAnalytics analyticsEngine = new InventoryAnalytics();

    public static void main(String[] args) {

        System.out.println("======================================================================");
        System.out.println(" IN-MEMORY INVENTORY INDEXER & PRICE SEARCHER ");
        System.out.println("======================================================================");

        long start = System.currentTimeMillis();

        generateMockData();

        sortingEngine.initializeStorage(primaryDatabase,MAX_ITEMS);
        sortingEngine.sortPriceIndex();
        long end = System.currentTimeMillis();

        System.out.println("[INFO] System ready in " + (end - start) + " ms.");

        runMenuLoop();

    }

    private static void runMenuLoop() {

        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println(
                    "\n==============================================================" +
                            "\n 1. Find Product by SKU Key" +
                            "\n 2. Search Products by Price Range" +
                            "\n 3. View Inventory Analytics" +
                            "\n 4. Exit" +
                    "\n=============================================================="
            );

            System.out.print("Enter your choice: ");

//            int choice = scanner.nextInt();
//            scanner.nextLine();

            String input = scanner.nextLine();
            int choice;

            try {
                choice = Integer.parseInt(input);
            } catch(NumberFormatException e) {
                System.out.println("Please enter a valid number.");
                continue;
            }
            switch (choice) {
                case 1:
                    System.out.print("Enter product SKU (SKU-1000 to SKU-10999): ");
                    String sku = scanner.nextLine().trim();
                    if(sku.trim().isEmpty()) {
                        System.out.println("SKU cannot be empty");
                        break;
                    }
                    if(!sku.matches("SKU-\\d+")) {
                        System.out.println("Invalid SKU format");
                        break;
                    }
                    Product product = hashingEngine.lookupSKU(sku);
                    if (product == null) {
                        System.out.println("Product not found for SKU: " + sku);
                    } else {
                        System.out.println("Product Found");
                        System.out.println("SKU   : " + product.getSku());
                        System.out.println("Name  : " + product.getName());
                        System.out.printf("Price : $%.2f%n", product.getPrice());
                        System.out.println("Stock : " + product.getStock());
                    }
                    break;
                case 2:
                    System.out.print("Enter minimum price: ");
                    double minPrice;
                    try {
                        minPrice = Double.parseDouble(scanner.nextLine());
                    } catch(NumberFormatException e) {
                        System.out.println("Invalid minimum price");
                        break;
                    }
                    System.out.print("Enter maximum price: ");
                    double maxPrice;
                    try {
                        maxPrice = Double.parseDouble(scanner.nextLine());
                    } catch(NumberFormatException e) {
                        System.out.println("Invalid minimum price");
                        break;
                    }
                    if(minPrice < 0 || maxPrice < 0) {
                        System.out.println("Price cannot be negative");
                        break;
                    }
                    if(minPrice > maxPrice) {
                        System.out.println("Minimum price cannot exceed maximum price");
                        break;
                    }
                    searchingEngine.rangeSearch(
                            primaryDatabase,
                            sortingEngine.sortedPriceIndex,
                            MAX_ITEMS,
                            minPrice,
                            maxPrice
                    );
                    break;
                case 3:
                    analyticsEngine.displayDashboard();
                    break;
                case 4:
                    System.out.println("Exiting ...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 4.");
            }
        }
    }

    private static void generateMockData() {

        Random rand = new Random();
        String[] productNameTemplates = {
                "MacBook Pro Laptop",
                "Nike Running Shirt",
                "LED Desk Lamp",
                "Hydraulic Wrench"
        };

        for(int i = 0; i < MAX_ITEMS; i++) {

            String productSku = "SKU-" + (1000 + i);
            int categoryId = rand.nextInt(4);

            String productName = "Mock " + productNameTemplates[categoryId] + "  Var  " + i;

            double price = 5.0 + (rand.nextDouble() * 250.0);
            int stock = rand.nextInt(150);

            Product product = new Product(
                    productSku,
                    productName,
                    price,
                    categoryId,
                    stock
            );
            primaryDatabase[i] = product;
            hashingEngine.insertIntoHashTable(product);
            analyticsEngine.updateMetrics(categoryId,price);

        }

    }
}
