package com.logic;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class TrieNode {
    char value;
    List<TrieNode> children;

    public TrieNode(char value) {
        this.value = value;
    }

    public void insert(String data) {
        if (data.length() == 0) {
            return;
        }
        if (children == null) {
            children = new ArrayList<>();
        }

        char c = data.charAt(0);
        TrieNode child = findNodeByChar(c);
        if (child == null) {
            child = new TrieNode(c);
            children.add(child);
        }
        child.insert(data.substring(1));
    }

    private TrieNode findNodeByChar(char c) {
        if (children != null) {
            for (TrieNode node : children) {
                if (node.value == c) {
                    return node;
                }
            }
        }
        return null;
    }

    private boolean containString(String str) {
        TrieNode current = this;
        for (int i = 0; i < str.length(); i++) {
            current = current.findNodeByChar(str.charAt(i));
            if (current == null) {
                return false;
            }

        }
        return true;
    }

    public void getAllNumbers(String path, List<String> result) {
        if (value != ' ') {
            path = path + value;
        }
        if (children != null) {
            for (TrieNode node : children) {
                node.getAllNumbers(path, result);
            }
        } else {
            result.add(path);
        }
    }

    public void writeToFile(PrintWriter writer) {
        writer.write(value);

        if (children != null) {
            for (TrieNode node : children) {
                node.writeToFile(writer);
            }
        }
        writer.write(']');
    }

    public void readFromFile(FileReader reader) throws IOException {
        char ch;
        while ((ch = (char) reader.read()) != ']') {
            TrieNode trieNode = new TrieNode(ch);
            trieNode.readFromFile(reader);
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(trieNode);
        }
    }
}
