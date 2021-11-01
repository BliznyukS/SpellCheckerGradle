package com.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileParser {
    public FileParser() {
    }

    public Map<String, Long> parseDictionaryFileHashMap(String filePath) throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = Class.class.getClassLoader();
        }

        Map<String, Long> hashMap;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filePath)))) {
            hashMap = reader.lines().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        }
        return hashMap;
    }

    public TreeMap<String, Long> parseDictionaryFileTree(String filePath) throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = Class.class.getClassLoader();
        }

        TreeMap<String, Long> treeMap = new TreeMap<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filePath)))) {
            reader.lines().forEach(word -> {
                Long count = treeMap.get(word);
                count = (count == null ? 1 : count + 1);
                treeMap.put(word, count);
            });
        }
        return treeMap;
    }

    public TrieNode parseDictionaryFileTrie(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        TrieNode root = new TrieNode(' ');
        for (String line : lines) {
            root.insert(line);
            System.out.println(root);
        }
        return root;
    }

    public Map<String, Long> parseBookFileHashMap(String filePath) throws URISyntaxException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader == null) {
            classLoader = Class.class.getClassLoader();
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(classLoader.getResourceAsStream(filePath)));
        return reader.lines()
                .map(String::trim)
                .map(a -> a.split(" "))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}