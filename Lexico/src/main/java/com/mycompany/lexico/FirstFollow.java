package com.mycompany.lexico;

import java.util.*;

public class FirstFollow {
    private Grammar grammar;
    private Map<String, Set<String>> first;
    private Map<String, Set<String>> follow;

    public FirstFollow(Grammar grammar) {
        this.grammar = grammar;
        first = new HashMap<>();
        follow = new HashMap<>();

        for (String nonTerminal : grammar.getNonTerminals()) {
            first.put(nonTerminal, new HashSet<>());
            follow.put(nonTerminal, new HashSet<>());
        }
    }

    public void calculateFirst() {
        boolean changed;
        do {
            changed = false;
            for (String family : grammar.productionFamilies.keySet()) {
                List<Producción> productions = grammar.getProductionsForFamily(family);
                for (Producción production : productions) {
                    String nonTerminal = production.getNoTerminal();
                    List<String> sequence = production.getSecuencia();
                    Set<String> firstSet = first.get(nonTerminal);

                    int originalSize = firstSet.size();
                    firstSet.addAll(calculateFirstForSequence(sequence));
                    if (firstSet.size() > originalSize) {
                        changed = true;
                    }
                }
            }
        } while (changed);
    }

    public Set<String> calculateFirstForSequence(List<String> sequence) {
        Set<String> result = new HashSet<>();
        for (String symbol : sequence) {
            if (grammar.getTerminals().contains(symbol)) {
                result.add(symbol);
                break;
            } else if (grammar.getNonTerminals().contains(symbol)) {
                Set<String> firstSet = first.get(symbol);
                result.addAll(firstSet);
                if (!firstSet.contains("ε")) {
                    break;
                }
            }
        }
        return result;
    }

    public void calculateFollow() {
        follow.get(grammar.getStartSymbol()).add("EOF");
        boolean changed;
        do {
            changed = false;
            for (String family : grammar.productionFamilies.keySet()) {
                List<Producción> productions = grammar.getProductionsForFamily(family);
                for (Producción production : productions) {
                    String nonTerminal = production.getNoTerminal();
                    List<String> sequence = production.getSecuencia();
                    for (int i = 0; i < sequence.size(); i++) {
                        String symbol = sequence.get(i);
                        if (grammar.getNonTerminals().contains(symbol)) {
                            Set<String> followSet = follow.get(symbol);
                            int originalSize = followSet.size();

                            if (i + 1 < sequence.size()) {
                                List<String> remainder = sequence.subList(i + 1, sequence.size());
                                followSet.addAll(calculateFirstForSequence(remainder));
                                followSet.remove("ε");
                            }

                            if (i + 1 == sequence.size() || calculateFirstForSequence(sequence.subList(i + 1, sequence.size())).contains("ε")) {
                                followSet.addAll(follow.get(nonTerminal));
                            }

                            if (followSet.size() > originalSize) {
                                changed = true;
                            }
                        }
                    }
                }
            }
        } while (changed);
    }

    public Set<String> getFirst(String nonTerminal) {
        return first.get(nonTerminal);
    }

    public Set<String> getFollow(String nonTerminal) {
        return follow.get(nonTerminal);
    }
}
