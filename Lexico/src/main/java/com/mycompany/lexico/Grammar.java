package com.mycompany.lexico;

import java.util.*;

public class Grammar {
    private List<String> nonTerminals;
    private List<String> terminals;
    public Map<String, List<Producción>> productionFamilies; // Mapa para las familias de producciones
    private String startSymbol;

    public Grammar() {
        nonTerminals = new ArrayList<>();
        terminals = new ArrayList<>();
        productionFamilies = new HashMap<>(); // Inicializar el mapa
    }

    public void addNonTerminal(String nonTerminal) {
        nonTerminals.add(nonTerminal);
    }

    public void addTerminal(String terminal) {
        terminals.add(terminal);
    }

    // Nuevo método para agregar producciones a familias
    public void addProduction(String family, String nonTerminal, List<String> sequence) {
        productionFamilies.computeIfAbsent(family, k -> new ArrayList<>())
                          .add(new Producción(nonTerminal, sequence));
    }

    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }

    public List<String> getNonTerminals() {
        return nonTerminals;
    }

    public List<String> getTerminals() {
        return terminals;
    }

    // Método para obtener todas las producciones de una familia
    public List<Producción> getProductionsForFamilyOld(String family) {
        return productionFamilies.getOrDefault(family, new ArrayList<>());
    }
    
    public List<Producción> getProductionsForFamily(String family) {
        return productionFamilies.get(family);
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public List<Producción> getProductionsForNonTerminal(String nonTerminal) {
        List<Producción> result = new ArrayList<>();
        for (List<Producción> family : productionFamilies.values()) {
            for (Producción production : family) {
                if (production.getNoTerminal().equals(nonTerminal)) {
                    result.add(production);
                }
            }
        }
        return result;
    }
}
