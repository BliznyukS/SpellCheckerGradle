package com.logic;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileParser {
    public FileParser() {
    }

    public Map<String, Long> parseDictionaryFileHashMap(String filePath) throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());

        Map<String, Long> collect = Files.lines(path).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Files.lines(path).close();

        return collect;
    }

    public TreeMap<String, Long> parseDictionaryFileTree(String filePath) throws URISyntaxException, IOException {

        TreeMap<String, Long> treeMap = new TreeMap<>();

        Scanner scanner = new Scanner(new File(filePath));
        while (scanner.hasNext()) {
            String word = scanner.next();
            Long count = treeMap.get(word);
            count = (count == null ? 1 : count + 1);
            treeMap.put(word, count);
        }
        scanner.close();

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
        Path path = Paths.get(getClass().getClassLoader().getResource(filePath).toURI());

        Map<String, Long> collect = Files.lines(path)
                .map(String::trim)
                .map(a -> a.split(" "))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        Files.lines(path).close();

        return collect;
    }

//    public TreeMap<String, Long> parseBookFileTreeMap(String filePath) throws IOException {
//
//        TreeMap<String, Long> treeMap = new TreeMap<>();
//
//        Scanner scanner = new Scanner(new File(filePath));
//        while (scanner.hasNext()) {
//            String word = scanner.next();
//            Long count = treeMap.get(word);
//            count = (count == null ? 1 : count + 1);
//            treeMap.put(word, count);
//        }
//        scanner.close();
//
//        return treeMap;
//    }
}