package com.mycompany.lexico;


import java.util.*;

public class PredictCalculator {
    private Grammar grammar;
    private FirstFollow firstFollow;
    private Map<Producción, Set<String>> predict;

    public PredictCalculator(Grammar grammar, FirstFollow firstFollow) {
        this.grammar = grammar;
        this.firstFollow = firstFollow;
        predict = new HashMap<>();
    }

    public void calculatePredict() {
        for (String family : grammar.productionFamilies.keySet()) {
            List<Producción> productions = grammar.getProductionsForFamily(family);
            for (Producción production : productions) {
                Set<String> predictSet = new HashSet<>();
                List<String> sequence = production.getSecuencia();
                predictSet.addAll(firstFollow.calculateFirstForSequence(sequence));
                if (predictSet.contains("ε")) {
                    predictSet.remove("ε");
                    predictSet.addAll(firstFollow.getFollow(production.getNoTerminal()));
                }
                predict.put(production, predictSet);
            }
        }
    }

    public Set<String> getPredict(Producción production) {
        return predict.get(production);
    }
}
