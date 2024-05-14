package com.mycompany.lexico;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/* TEORÍA PARA SABER QUE TENGO QUE HACER
 El Parser  solo se preocupa de revisar que los tokens se encuentren en un orden válido.

 El parser del proyecto se clasifica en LL1 y este tipo de parser tiene una clasificación 
 adicional dependiendo de su tipo de construcción (implementación)(parsers dirigidos por tabla 
 o parsers por descenso recursivo).

 LL1: Un parser izquierdo siempre procesa las formas sentenciales de trabajo de izquierda a derecha. El 1 es el token lookahead

 La Tabla de Parsing es la IA del parser.  
 Ayuda a tomar la decisión de cuál es la mejor regla de la gramática para poder compilar.

 La pila de Parsing almacena la forma sentencial actual de trabajo.

Siempre en el proceso de generación se toman dos decisiones:
  - ¿Cuál no terminal se cambia?
  - Usando ¿qué regla?

  Como es un LL1 siempre cambiaremos el no terminal de más a la izquierda.
  Como la forma sentencial de trabajo se guarde en una pila, será el que esté en el tope de la pila.

---------Conjunto First:------------  
First es una función matemática que recibe una forma sentencial en el contexto de una gramática 
y retorna un conjunto de terminales.
 
Intuitivamente es el grupo de terminales con los que puede comenzar todo string derivado 
a partir de la forma sentencial recibida.

First es un conjunto finito ya que se basa en el conjunto de terminales que son un 
reflejo del alfabeto que es finito.

Matemáticamente:
Si la forma sentencial comienza con un terminal el conjunto first solo incluye a ese 
terminal.  Si la forma sentencial comienza con un no terminal, se debe analizar todas 
las reglas que producen ese no terminal y agregar al conjunto el first de los lados 
derechos de dichas reglas.  

Marca Derecha ($).  Es un símbolo que usaremos para indicar la finalización de la 
entrada.  En un archivo de texto es el EOF (por esta razón es normal tener un token de EOF o Marca derecha).

--------------Conjunto Follow:---------------- 
Follow es una función matemática que    recibe un no terminal y genera un conjunto de 
terminales, como siempre en el contexto de una gramática.

Intuitivamente el follow es la union de todos los first de las formas sentenciales 
que están a la derecha de todas las apariciones del no terminal que se recibe en 
todos los lados derechos de la gramática.  Si el first incluye la marca derecha 
hay que agregar recursivamente el follow del lado izquierdo. 

El follow del no terminal inicial siempre debe incluir a la marca derecha.

-------------Conjunto Predict:-----------------

Predict es una función matemática que recibe una producción en el contexto de 
una gramática y regresa un conjunto de terminales.  Ese conjunto son los terminales 
que predicen que debo utilizar esa regla para trabajar:
Intuitivamente el predict de una regla es el first del lado derecho.  
Siempre que ese lado derecho no sea épsilon.  Si es épsilon el predict es el follow del lado izquierdo.

------------Tabla de Parsing:------------------

La tabla de Parsing se construye a partir de el predict de cada regla de la gramática.
La TP tiene tantas filas como no terminales tiene la gramática y tiene tantas 
columnas como terminales + 1 tiene la gramática  (el +1 representa a la marca derecha).
La TP contiene en cada celda números de producción o -1. 

Se toma el predict de cada regla y se coloca en la fila de su lado izquierdo ese 
número de regla en todas las columnas del conjunto predict de esa regla.  
Todas las celdas de esa fila que no son parte del predict se rellenan con -1.

Una Doble Predicción se produce cuando en la misma celda hay que colocar uno o más números. 
 Hay dos posibles culpables de la doble predicción:
1. La gramática se escribió de forma ambigua.
2. El lenguaje ES ambiguo, por lo que toda gramática que lo represente correctamente será ambigua siempre.

-----------Driver de Parsing:------------------

El Driver de Parsing es el corazón del Parser.  Es tan conocido que suele estar en los libros de compiladores.
  Nosotros veremos nuestra versión. Que suponemos es la que usarán en el proyecto.
  El problema entonces de hacer el parser es tener una gramática bien depurada,
  que no tenga errores y que genere el lenguaje que exactamente ocupamos compilar.

La idea es analizar el corazón del parser en C-udocódigo.

Elementos que necesitamos:
+ Biblioteca de análisis léxico (listo)
+ Tabla de Parsing (TP)
+ La Tabla de lados derechos (representa la gramática) (TLD)
+ La pila de parsing (representa la forma sentencial de trabajo)
+ Una variable que almacena el token actual (TA)
+ Una variable que almacena el elemento actual en proceso (EAP)

 */
public class Parser {

    class Production {

        String leftHandSide;
        List<String> rightHandSide;

        public Production(String left, List<String> right) {
            this.leftHandSide = left;
            this.rightHandSide = right;
        }
    }

    public static class PredictiveParsingTable {

        private static PredictiveParsingTable instance; // Instancia única de la tabla

        private Map<String, Map<String, Integer>> table;

        private PredictiveParsingTable() {
            table = new HashMap<>();
        }

        public static PredictiveParsingTable getInstance() {
            if (instance == null) {
                instance = new PredictiveParsingTable();
            }
            return instance;
        }

        public void fillTable(Map<String, List<List<String>>> grammar) {
            for (Map.Entry<String, List<List<String>>> entry : grammar.entrySet()) {
                String nonTerminal = entry.getKey();
                List<List<String>> productions = entry.getValue();
                for (int i = 0; i < productions.size(); i++) {
                    List<String> production = productions.get(i);
                    Set<String> predictSet = SyntaxAnalyzer.calculatePredict(grammar, this, nonTerminal, production);
                    for (String terminal : predictSet) {
                        addEntry(nonTerminal, terminal, i + 1); // Incrementamos en 1 para evitar el 0 como valor de producción
                    }
                }
            }
        }

        public void addEntry(String nonTerminal, String terminal, int productionNumber) {
            table.putIfAbsent(nonTerminal, new HashMap<>());
            table.get(nonTerminal).put(terminal, productionNumber);
        }

            public int getProductionNumber(String nonTerminal, String terminal) {
            return table.getOrDefault(nonTerminal, Collections.emptyMap()).getOrDefault(terminal, -1);
        }
    }

    class SyntaxAnalyzer {

        public static Set<String> calculateFirst(Map<String, List<List<String>>> grammar, String symbol) {
            Set<String> firstSet = new HashSet<>();
            if (!grammar.containsKey(symbol)) {
                // Si el símbolo no es un no terminal, devolvemos solo ese símbolo como FIRST
                firstSet.add(symbol);
                return firstSet;
            }
            // Si el símbolo es un no terminal, necesitamos calcular FIRST recursivamente
            for (List<String> production : grammar.get(symbol)) {
                for (String symbolInProduction : production) {
                    // Manejar los no terminales
                    if (grammar.containsKey(symbolInProduction)) {
                        // Calcular FIRST del no terminal y agregarlo al conjunto
                        Set<String> firstOfSymbol = calculateFirst(grammar, symbolInProduction);
                        firstSet.addAll(firstOfSymbol);
                        // Si el no terminal no puede derivar epsilon, detener la iteración
                        if (!firstOfSymbol.contains("epsilon")) {
                            break;
                        }
                    } else {
                        // Manejar los terminales
                        firstSet.add(symbolInProduction);
                        break;
                    }
                }
            }
            return firstSet;
        }

        public static Set<String> calculateFollow(Map<String, List<List<String>>> grammar, PredictiveParsingTable table, String symbol) {
            Set<String> followSet = new HashSet<>();
            if (symbol.equals("<S>")) {
                // Agregar la marca de fin de cadena ($) al conjunto FOLLOW del símbolo inicial
                followSet.add("$");
            }
            // Iterar sobre todas las producciones para encontrar el símbolo y calcular FOLLOW
            for (Map.Entry<String, List<List<String>>> entry : grammar.entrySet()) {
                String nonTerminal = entry.getKey();
                for (List<String> production : entry.getValue()) {
                    for (int i = 0; i < production.size(); i++) {
                        if (production.get(i).equals(symbol)) {
                            // Calcular FOLLOW para el símbolo actual en la producción
                            if (i == production.size() - 1) {
                                // Si el símbolo es el último en la producción, calcular FOLLOW del no terminal izquierdo
                                if (!nonTerminal.equals(symbol)) {
                                    // Evitar la recursión infinita
                                    followSet.addAll(calculateFollow(grammar, table, nonTerminal));
                                }
                            } else {
                                // Si el símbolo no es el último, calcular FIRST del siguiente símbolo en la producción
                                Set<String> firstOfNext = calculateFirst(grammar, production.get(i + 1));
                                followSet.addAll(firstOfNext);
                                // Si el siguiente símbolo puede derivar epsilon, calcular FOLLOW del no terminal izquierdo
                                if (firstOfNext.contains("epsilon")) {
                                    if (!nonTerminal.equals(symbol)) {
                                        // Evitar la recursión infinita
                                        followSet.addAll(calculateFollow(grammar, table, nonTerminal));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return followSet;
        }

        public static Set<String> calculatePredict(Map<String, List<List<String>>> grammar, PredictiveParsingTable table, String nonTerminal, List<String> production) {
            Set<String> predictSet = new HashSet<>();
            boolean allDeriveEpsilon = true; // Variable para verificar si todos los símbolos derivan epsilon

            // Calcular FIRST del lado derecho de la producción
            for (String symbol : production) {
                Set<String> firstOfSymbol = calculateFirst(grammar, symbol);
                predictSet.addAll(firstOfSymbol);

                // Verificar si el símbolo deriva epsilon
                if (!firstOfSymbol.contains("epsilon")) {
                    allDeriveEpsilon = false;
                    break;
                }
            }

            // Si todos los símbolos del lado derecho pueden derivar epsilon, agregar FOLLOW del no terminal
            if (allDeriveEpsilon) {
                predictSet.addAll(calculateFollow(grammar, table, nonTerminal));
            }

            // Remover epsilon si está presente
            predictSet.remove("epsilon");

            return predictSet;
        }
    }

    public static void parse(Token[] tokens, PredictiveParsingTable table, Map<String, List<List<String>>> grammar) 
    {
        Stack<String> parsingStack = new Stack<>();
        parsingStack.push("<S>"); // Símbolo inicial en la pila

        int tokenIndex = 0; // Índice para recorrer los tokens

        while (!parsingStack.isEmpty() && tokenIndex < tokens.length) {
            String stackTop = parsingStack.pop(); // Obtener el elemento superior de la pila
            System.out.println("Analizando token: " + tokens[tokenIndex].getTipoString() + "con Lexema: "+ tokens[tokenIndex].getLexema()  + ". Simbolo actual en la pila: " + stackTop);

            if (grammar.containsKey(stackTop)) 
            {
                System.out.println("Se encontro la llave: " + stackTop);
                System.out.println("Tipo Token a analizar: " + tokens[tokenIndex].getTipoString());
                
                // Si el elemento es un no terminal, buscar en la tabla de análisis predictivo
                int productionNumber = table.getProductionNumber(stackTop, tokens[tokenIndex].getTipoString());
                if (productionNumber == -1) 
                {
                    // Error: no se encontró una producción para el no terminal y el token actual
                    System.out.println("Error sintactico en fila " + tokens[tokenIndex].getLinea() +
                            ", columna " + tokens[tokenIndex].getColumna() +
                            ": No se encontro una producción para " + stackTop + " y " +
                            tokens[tokenIndex].getTipoString());
                    return;
                } else {
                    // Obtener la producción correspondiente y agregar los símbolos a la pila
                    List<String> production = grammar.get(stackTop).get(productionNumber - 1); // Restamos 1 porque la numeración de las producciones comienza desde 1
                    for (int i = production.size() - 1; i >= 0; i--) {
                        if (!production.get(i).equals("epsilon")) {
                            parsingStack.push(production.get(i));
                        }
                    }
                }
            } else {
                // Si el elemento es un terminal, compararlo con el tipo de token actual
                String terminal = tokens[tokenIndex].getTipoString(); // Cambio aquí a getTipoString()
                if (stackTop.equals(terminal) || (stackTop.equals("IDENTIFICADOR") && terminal.equals("IDENTIFICADOR"))) {
                    // Coincidencia: avanzar al siguiente token
                    tokenIndex++;
                } else {
                    // Error: el terminal en la pila no coincide con el tipo de token actual
                    System.out.println("Error sintáctico en fila " + tokens[tokenIndex].getLinea() +
                            ", columna " + tokens[tokenIndex].getColumna() +
                            ": Se esperaba " + stackTop + " y se recibio " + terminal);
                    return;
                }
            }
        }

        // Verificar si se llegó al final de la cadena de tokens
        if (tokenIndex != tokens.length) {
            System.out.println("Error sintactico: fin de archivo inesperado");
        } else {
            System.out.println("Analisis sintactico exitoso");
        }
    }
    
    
    public static void main(String[] args) {
        System.out.print("\n\n\n\n");
        // Obtener los tokens
        Token[] tokens = Scanner.scan();

        // Definimos el mapa que tendrá toda la gramática
        Map<String, List<List<String>>> grammar = new HashMap<>();

        // tabla de predicciones
        PredictiveParsingTable table = new PredictiveParsingTable();

        // Aquí van todas las reglas gramaticales
        // Reglas gramaticales para la asignación de constantes
        grammar.put("<S>", Arrays.asList(
                Arrays.asList("arrá", "<loop-constantes>")
        ));
        grammar.put("<loop-constantes>", Arrays.asList(
                Arrays.asList("IDENTIFICADOR", "<literal>", "COMA", "<loop-constantes>"),
                Arrays.asList("IDENTIFICADOR", "<literal>", "TERMINADOR")
        ));
        grammar.put("<literal>", Arrays.asList(
                Arrays.asList("LITERAL_FLOTANTE"),
                Arrays.asList("LITERAL_BOOLEANA"),
                Arrays.asList("LITERAL_ENTERA")
        ));

        
        // Llenar la tabla de parsing
        table.fillTable(grammar);

        // Llamar al parser
        parse(tokens, table, grammar);
    }
}
