package com.mycompany.lexico;

import java.util.*;

public class FirstFollow {
    private Grammar grammar;
    private Map<String, Set<String>> firstSets;
    private Map<String, Set<String>> followSets;

    public FirstFollow(Grammar grammar) {
        this.grammar = grammar;
        firstSets = new HashMap<>();
        followSets = new HashMap<>();

        for (String nonTerminal : grammar.getNonTerminals()) {
            firstSets.put(nonTerminal, new HashSet<>());
            followSets.put(nonTerminal, new HashSet<>());
        }
    }

    public void calculateFirst() {
        boolean changed;
        do {
            changed = false;
            for (Producción production : grammar.getProductions()) 
            {
                System.out.print("X");
                String nonTerminal = production.getNoTerminal();
                List<String> sequence = production.getSecuencia();

                int initialSize = firstSets.get(nonTerminal).size();
                firstSets.get(nonTerminal).addAll(first(sequence));
                if (firstSets.get(nonTerminal).size() > initialSize) 
                {
                    changed = true;
                }
            }
        } while (changed);
    }

    public Set<String> first(List<String> sequence) {
        Set<String> result = new HashSet<>();
        if (sequence.isEmpty()) {
            result.add("ε");
            return result;
        }

        String firstSymbol = sequence.get(0);
        if (grammar.getTerminals().contains(firstSymbol)) {
            result.add(firstSymbol);
        } else {
            result.addAll(firstSets.get(firstSymbol));
            for (int i = 1; i < sequence.size() && firstSets.get(sequence.get(i - 1)).contains("ε"); i++) {
                result.addAll(firstSets.get(sequence.get(i)));
                result.remove("ε");
            }
            if (sequence.stream().allMatch(symbol -> firstSets.get(symbol).contains("ε"))) {
                result.add("ε");
            }
        }
        return result;
    }

    public void calculateFollow() {
        followSets.get(grammar.getStartSymbol()).add("$");
        boolean changed;
        do {
            changed = false;
            for (Producción production : grammar.getProductions()) {
                String nonTerminal = production.getNoTerminal();
                List<String> sequence = production.getSecuencia();

                for (int i = 0; i < sequence.size(); i++) {
                    String symbol = sequence.get(i);
                    if (grammar.getNonTerminals().contains(symbol)) {
                        Set<String> followSet = followSets.get(symbol);
                        int initialSize = followSet.size();

                        if (i + 1 < sequence.size()) {
                            List<String> restSequence = sequence.subList(i + 1, sequence.size());
                            Set<String> firstOfRest = first(restSequence);
                            followSet.addAll(firstOfRest);
                            followSet.remove("ε");
                            if (firstOfRest.contains("ε")) {
                                followSet.addAll(followSets.get(nonTerminal));
                            }
                        } else {
                            followSet.addAll(followSets.get(nonTerminal));
                        }

                        if (followSet.size() > initialSize) {
                            changed = true;
                        }
                    }
                }
            }
        } while (changed);
    }

    public Map<String, Set<String>> getFirstSets() {
        return firstSets;
    }

    public Map<String, Set<String>> getFollowSets() {
        return followSets;
    }
}
