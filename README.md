# Spell Checker
In this task you will be implementing a spell checker using one of the most popular data structures.

## Description
What needs to be done:
1. Load the dictionary from the `dictionaries` folder into memory. Use different data structures for storing the known words:
   1. Binary Search Tree
   2. Trie
   3. Hash Map  
   _First three data structures are required, but you can additionally implement/use some custom data structures for faster search._
2. Process texts provided in the `texts` folder:
   1. For each word detect whether it is valid, known or unknown
    1. Word is considered invalid if:
        - its length exceeds 45 characters or
        - it contains non-alphanumeric characters or begins with apostrophe
    2. Known are words that exist in the dictionary
    3. Valid words absent from the dictionary are considered unknown
3. Save the processing results:
   1. Create `words` folder
   2. Save known words to the `known.txt` file inside the `words` folder
   3. Save unknown words to the `unknown.txt` file inside the `words` folder
4. As a result of the execution, your program must print data in the following format:
   ```
   binary_search_tree: <dictionary_loading_time (ms)> <texts_processing_time (ms)> <number_of_checked_words> <number_of_invalid_words>
   trie: <dictionary_loading_time (ms)> <texts_processing_time (ms)> <number_of_checked_words> <number_of_invalid_words>
   hash_map: <dictionary_loading_time (ms)> <texts_processing_time (ms)> <number_of_checked_words> <number_of_invalid_words>
   ``` 

## com.com.logic.Dictionary Restrictions
 - com.com.logic.Dictionary is assumed to be a file containing a list of lowercase words, one per line
 - Each word will contain only lowercase alphabetical characters and possibly apostrophes
 - From top to bottom, the file is sorted lexicographically, with only one word per line
 - No word is longer than 45 characters
 - No word appears more than once

## Environment
- Java 11
- Gradle
- No third-party libraries allowed
