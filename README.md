
* Project Title:

in-memory-inventory-indexer-and-price-searcher

* Project Description/Overview:

The In-Memory Inventory Indexer & Price Searcher is a Java-based project that demonstrates how data structures and algorithms can be used to organize, index, and search product inventory efficiently.

The application generates 10,000 mock product records and stores them in an in-memory database. It uses a custom hash table for SKU-based lookups, randomized QuickSort to build a price-sorted index, and binary search to retrieve products within a specified price range. It also maintains an analytics matrix to track product distribution across categories and price tiers.

The project focuses on understanding algorithmic efficiency, implementing fundamental data structures, and exploring the trade-offs between memory usage and search performance.

* Tech stack/Concepts:

Programming Language: Java
Core Libraries: Java Standard Library (java.util.Random)

> Data Structures & Concepts:

Object-Oriented Programming (OOP): Classes, objects, encapsulation, and constructors
Hash Table: Efficient SKU-based product lookup
DJB2 Hashing Algorithm: Custom hash function for mapping product SKUs to hash table buckets
Collision Handling: Separate chaining using linked nodes
Randomized QuickSort: Sorting product price indexes using a randomly selected pivot
Divide and Conquer: Breaking the sorting problem into smaller subproblems using QuickSort
Recursion: Recursively sorting partitions in QuickSort
Binary Search: Finding product price ranges using lower-bound and upper-bound searches
Arrays: Product storage and sorted price index management
2D Arrays: Inventory analytics by product category and price tier
Time Complexity Analysis: Understanding average-case and worst-case performance
Performance Measurement: Execution time tracking using System.nanoTime() and System.currentTimeMillis()

* Real-World Application:
Simulates an inventory management system with fast SKU lookup, price-range searching, and category-wise inventory analytics.

* Challenges Faced & What i learned

> Challenges Faced

* Designing a hash function and calculating bucket indexes to distribute product SKUs across the hash table.
* Handling hash collisions using separate chaining with linked nodes.
* Sorting product prices without rearranging the original product database by maintaining a separate index array.
* Implementing binary search to efficiently find products within a given price range.

> Key Learnings

* Learned how the DJB2 hashing algorithm and modulo operation map SKUs to bucket indexes, and how bucket sizing helps manage collisions.
* Understood how separate chaining handles multiple products mapped to the same bucket.
* Learned to preserve the original database order by sorting an array of indexes and accessing products through "primaryDatabase[sortedPriceIndex[i]]".
* Gained practical experience with Randomized QuickSort, recursion, divide and conquer, and binary search, along with their time complexities.

* Core features/components:

SKU-Based Product Lookup: Custom DJB2 hashing and a hash table with separate chaining for efficient product retrieval.
Price-Based Search & Sorting: Randomized QuickSort and binary search on a separate price index for efficient price-range queries while preserving the original product database.

* Time and Space Complexity: 

> Time Complexity

Overall Initialization: O(N log N) expected, dominated by sorting; worst case O(N²) due to QuickSort and potentially hash-table collisions.
SKU Lookup: O(1) average, O(N) worst case.
Price-Range Search: O(log N) to find range boundaries; O(log N + K) if retrieving K matching products.
Inventory Analytics Update: O(1) per product.

> Space Complexity

Overall Space: O(N), including the primary product database, hash table, sorted price index, and analytics matrix.

* Optimizations and Trade-offs:

> Optimizations:

Optimize Hash Insertion: Insert new nodes at the head of the chain to achieve O(1) insertion instead of traversing the entire chain.
Improve QuickSort: Implement three-way partitioning to handle duplicate product prices more efficiently and reduce the risk of O(N²) performance.
Dynamic Hash Table Resizing: Resize and rehash the table as the inventory grows to maintain efficient SKU lookups.

> Design Trade-offs

Hash Table vs. Linear Search: Faster average SKU lookups at the cost of additional memory and collision-handling complexity.
Separate Price Index vs. Sorting Products Directly: Preserves the original product database order but requires additional O(N) memory.
Randomized QuickSort vs. Simpler Sorting: Offers expected O(N log N) performance but can degrade to O(N²) in the worst case, especially with duplicate prices in the current implementation.
Fixed-Size Hash Table vs. Dynamic Resizing: Simplifies implementation but limits adaptability as the inventory grows.
In-Memory Storage vs. Database Persistence: Provides fast access without database overhead but loses all data when the program terminates.

* Pros and Cons:

Pros
Fast SKU lookup using a hash table and efficient price-range searching using binary search.
Demonstrates core DSA concepts with minimal memory overhead and no external database dependency.

Cons
Data is stored in memory and is lost when the program terminates.
Fixed capacity of 10,000 products; dynamic resizing and product update/delete operations are not implemented.

