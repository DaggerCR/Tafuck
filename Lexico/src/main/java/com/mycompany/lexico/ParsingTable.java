package com.mycompany.lexico;

import java.util.*;
import java.util.HashMap;
import java.util.Map;


public class ParsingTable {
    private Map<String, Map<String, Producción>> table;

    public ParsingTable() {
        this.table = new HashMap<>();
    }

    public void initialize(List<String> nonTerminals, List<String> terminals) {
        for (String nonTerminal : nonTerminals) {
            table.put(nonTerminal, new HashMap<>());
        }
    }

    public void addEntry(String nonTerminal, String terminal, Producción production) {
        if (!table.containsKey(nonTerminal)) {
            throw new IllegalArgumentException("Non-terminal not found: " + nonTerminal);
        }
        table.get(nonTerminal).put(terminal, production);
    }

    public Producción getEntry(String nonTerminal, String terminal) {
        return table.get(nonTerminal).get(terminal);
    }

    public Map<String, Map<String, Producción>> getTable() {
        return table;
    }
}


