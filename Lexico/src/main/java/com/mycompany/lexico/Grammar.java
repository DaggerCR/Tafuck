package com.mycompany.lexico;

import java.util.*;

public class Grammar {
    private List<String> nonTerminals;
    private List<String> terminals;
    private List<Producción> productions;
    private String startSymbol;

    public Grammar() {
        nonTerminals = new ArrayList<>();
        terminals = new ArrayList<>();
        productions = new ArrayList<>();
    }

    public void addNonTerminal(String nonTerminal) {
        nonTerminals.add(nonTerminal);
    }

    public void addTerminal(String terminal) {
        terminals.add(terminal);
    }

    public void addProduction(String nonTerminal, List<String> sequence) {
        productions.add(new Producción(nonTerminal, sequence));
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

    public List<Producción> getProductions() {
        return productions;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public List<Producción> getProductionsForNonTerminal(String nonTerminal) {
        List<Producción> result = new ArrayList<>();
        for (Producción production : productions) {
            if (production.getNoTerminal().equals(nonTerminal)) {
                result.add(production);
            }
        }
        return result;
    }
}
