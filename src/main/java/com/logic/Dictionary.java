package com.logic;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class Dictionary {

    public static void main(String[] args) throws URISyntaxException, IOException {

        FileParser parser = new FileParser();
        Validator validator = new Validator();

        final String KNOWN_WORDS = "src/main/resources/words/known.txt";
        final String UNKNOWN_WORDS = "src/main/resources/words/unknown.txt";

        long dictionaryHashMapLoadingTimeStart = System.currentTimeMillis();
        HashMap<String, Long> dictionaryHashMap = (HashMap<String, Long>) parser.parseDictionaryFileHashMap("dictionaries/large");
        long dictionaryHashMapLoadingTimeEnd = System.currentTimeMillis();

        long dictionaryTreeMapLoadingTimeStart = System.currentTimeMillis();
        TreeMap<String, Long> dictionaryTreeMap = parser.parseDictionaryFileTree("src/main/resources/dictionaries/large");
        long dictionaryTreeMapLoadingTimeEnd = System.currentTimeMillis();

        long dictionaryTrieLoadingTimeStart = System.currentTimeMillis();
        TreeMap<String, Long> dictionaryTrie = parser.parseDictionaryFileTree("src/main/resources/dictionaries/large");
        long dictionaryTrieLoadingTimeEnd = System.currentTimeMillis();

        long textsHashMapLoadingTimeStart = System.currentTimeMillis();
        Map<String, Long> aliceFile = parser.parseBookFileHashMap("texts/alice.txt");
        long textsHashMapLoadingTimeEnd = System.currentTimeMillis();

        long textsTreeMapLoadingTimeStart = System.currentTimeMillis();
        Map<String, Long> draculaFile = parser.parseBookFileHashMap("texts/dracula.txt");
        long textsTreeMapLoadingTimeEnd = System.currentTimeMillis();

        long textsTrieLoadingTimeStart = System.currentTimeMillis();
        Map<String, Long> sherlockFile = parser.parseBookFileHashMap("texts/sherlock.txt");
        long textsTrieLoadingTimeEnd = System.currentTimeMillis();

        Map<String, Long> allKnownWords = new HashMap<>();
        Map<String, Long> allUnKnownWords = new HashMap<>();
        Map<String, Long> allInvalidWords = new HashMap<>();

        AtomicLong checkedWordsCount = new AtomicLong(0);

        long aliceForEachTimeStart = System.currentTimeMillis();
        aliceFile.forEach((word, wordCount) -> {
                    if (!validator.validateWord(word)) {
                        allInvalidWords.put(word, wordCount);
                    }
                    if (dictionaryHashMap.containsKey(word)) {
                        allKnownWords.put(word, wordCount);
                    } else {
                        allUnKnownWords.put(word, wordCount);
                    }
                    checkedWordsCount.getAndAdd(wordCount);
                }
        );
        long aliceForEachTimeEnd = System.currentTimeMillis();

        long draculaForEachTimeStart = System.currentTimeMillis();
        draculaFile.forEach((word, wordCount) -> {
                    if (!validator.validateWord(word)) {
                        allInvalidWords.put(word, wordCount);
                    }
                    if (dictionaryTreeMap.containsKey(word)) {
                        allKnownWords.put(word, wordCount);

                    } else {
                        allUnKnownWords.put(word, wordCount);
                    }
                    checkedWordsCount.getAndAdd(wordCount);
                }
        );
        long draculaForEachTimeEnd = System.currentTimeMillis();

        long sherlockForEachTimeStart = System.currentTimeMillis();
        sherlockFile.forEach((word, wordCount) -> {
                    if (!validator.validateWord(word)) {
                        allInvalidWords.put(word, wordCount);
                    }
                    if (dictionaryTrie.containsKey(word)) {
                        allKnownWords.put(word, wordCount);
                    } else {
                        allUnKnownWords.put(word, wordCount);
                    }
                    checkedWordsCount.getAndAdd(wordCount);
                }
        );
        long sherlockForEachTimeEnd = System.currentTimeMillis();

        Files.write(Paths.get(KNOWN_WORDS),
                allKnownWords.keySet().stream().collect(Collectors.toList()),
                StandardCharsets.UTF_8);

        Files.write(Paths.get(UNKNOWN_WORDS),
                allUnKnownWords.keySet().stream().collect(Collectors.toList()),
                StandardCharsets.UTF_8);

        System.out.println("binary_search_tree: "
                + "<dictionary_loading_time> :" + (dictionaryTreeMapLoadingTimeEnd - dictionaryTreeMapLoadingTimeStart) + " (ms) "
                + "<texts_processing_time> :" + (textsTreeMapLoadingTimeEnd - textsTreeMapLoadingTimeStart) + " (ms) "
                + "ForEach Time : " + (draculaForEachTimeEnd - draculaForEachTimeStart)
                + " <number_of_checked_words> :" + (aliceFile.size() + draculaFile.size() + sherlockFile.size())
                + " <number_of_invalid_words> : " + allInvalidWords.size());

        System.out.println("trie: "
                + "<dictionary_loading_time> :" + (dictionaryTrieLoadingTimeEnd - dictionaryTrieLoadingTimeStart) + " (ms) "
                + "<texts_processing_time> :" + (textsTrieLoadingTimeEnd - textsTrieLoadingTimeStart) + " (ms) "
                + "ForEach Time : " + (sherlockForEachTimeEnd - sherlockForEachTimeStart)
                + " <number_of_checked_words> :" + (aliceFile.size() + draculaFile.size() + sherlockFile.size())
                + " <number_of_invalid_words> : " + allInvalidWords.size());

        System.out.println("hash_map: "
                + "<dictionary_loading_time> :" + (dictionaryHashMapLoadingTimeEnd - dictionaryHashMapLoadingTimeStart) + " (ms) "
                + "<texts_processing_time> :" + (textsHashMapLoadingTimeEnd - textsHashMapLoadingTimeStart) + " (ms) "
                + "ForEach Time : " + (aliceForEachTimeEnd - aliceForEachTimeStart)
                + " <number_of_checked_words> :" + (aliceFile.size() + draculaFile.size() + sherlockFile.size())
                + " <number_of_invalid_words> : " + allInvalidWords.size());
    }

}
