package com.mycompany.lexico;


import java.util.*;

public class PredictCalculator {
    private Grammar grammar;
    private FirstFollow firstFollowCalculator;
    private Map<Producción, Set<String>> predictSets;

    public PredictCalculator(Grammar grammar, FirstFollow firstFollowCalculator) {
        this.grammar = grammar;
        this.firstFollowCalculator = firstFollowCalculator;
        this.predictSets = new HashMap<>();

        for (Producción production : grammar.getProductions()) {
            predictSets.put(production, new HashSet<>());
        }
    }

    public void calculatePredict() {
        for (Producción production : grammar.getProductions()) {
            List<String> sequence = production.getSecuencia();
            Set<String> firstSet = firstFollowCalculator.first(sequence);

            if (firstSet.contains("ε")) {
                firstSet.remove("ε");
                firstSet.addAll(firstFollowCalculator.getFollowSets().get(production.getNoTerminal()));
            }

            predictSets.get(production).addAll(firstSet);
        }
    }

    public Map<Producción, Set<String>> getPredictSets() {
        return predictSets;
    }
}
