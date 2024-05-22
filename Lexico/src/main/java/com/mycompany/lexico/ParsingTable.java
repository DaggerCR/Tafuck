package com.mycompany.lexico;

import java.util.*;
import java.util.HashMap;
import java.util.Map;

public class ParsingTable {
    private Map<String, Map<String, Integer>> table;

    public ParsingTable() {
        table = new HashMap<>();
    }

    public void addEntry(String nonTerminal, String terminal, int productionNumber) {
        table.computeIfAbsent(nonTerminal, k -> new HashMap<>()).put(terminal, productionNumber);
    }

    public int getEntry(String nonTerminal, String terminal) {
        return table.getOrDefault(nonTerminal, new HashMap<>()).getOrDefault(terminal, -1);
    }

    public void printTable() {
        for (Map.Entry<String, Map<String, Integer>> row : table.entrySet()) {
            System.out.print(row.getKey() + ": ");
            for (Map.Entry<String, Integer> col : row.getValue().entrySet()) {
                System.out.print(col.getKey() + "->" + col.getValue() + " ");
            }
            System.out.println();
        }
    }
}

