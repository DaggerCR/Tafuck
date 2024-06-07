
package com.mycompany.lexico;
import java.util.Hashtable;
import java.util.List;

/**
 *
 * @author kpala
 * Tabla de simbolos para el compilador de Tafac
 */
public class SymbolTable {
    Hashtable<ScopeIdentifier, SymbolInfo> globalSymbolTable;
    private String response = "";
    
    SymbolTable() {
            this.globalSymbolTable = new Hashtable<>();
        }

        public void addSymbol(String scope, String identifier, SymbolInfo info) throws SymbolTableCollisionException {
            ScopeIdentifier key = new ScopeIdentifier(scope, identifier);
            if (this.globalSymbolTable.containsKey(key)) {
                throw new SymbolTableCollisionException("Collision detected for key: " + key);
            }
            globalSymbolTable.put(key, info);
        }
        
        public void addSymbol(ScopeIdentifier key, SymbolInfo info) throws SymbolTableCollisionException {
            /*if (this.globalSymbolTable.containsKey(key)) {
                throw new SymbolTableCollisionException("Collision detected for key: " + key);
            }*/
            globalSymbolTable.put(key, info);
        }

        public boolean isDeclared(String scope, String identifier) {
            ScopeIdentifier key = new ScopeIdentifier(scope, identifier);
            return globalSymbolTable.containsKey(key);
        }

        // Método para obtener la información de un símbolo
        public SymbolInfo getSymbolInfo(String scope, String identifier) {
            ScopeIdentifier key = new ScopeIdentifier(scope, identifier);
            return globalSymbolTable.get(key);
        }

        // Método para actualizar la información de un símbolo
        public void updateSymbolInfo(String scope, String identifier, SymbolInfo newInfo) {
            ScopeIdentifier key = new ScopeIdentifier(scope, identifier);
            globalSymbolTable.put(key, newInfo);
        }

        // Método para imprimir la tabla de símbolos
        public void printTable() {
            globalSymbolTable.forEach((k, v) -> System.out.println(k + " -> " + v));
        }
        
        public String mensaje () 
        {
            // me rendí y no pude hacer que funcionara (porque hay que llamar al método fillTable primero)
            StringBuilder responseLocal = new StringBuilder();
            globalSymbolTable.forEach((k, v) -> responseLocal.append(k).append(" -> ").append(v).append("\n"));
            return response = responseLocal.toString();
            
        }
        
        public String getTableAsString() 
        {
            StringBuilder tableString = new StringBuilder();
            globalSymbolTable.forEach((k, v) -> tableString.append(k).append(" -> ").append(v).append("\n"));
            return tableString.toString();
        }
        
        public Hashtable<ScopeIdentifier, SymbolInfo> fillTable(List<Token> tokensTrue) throws SymbolTableCollisionException{
            boolean programName = false;
            boolean constSeccion = false;
            boolean typesSeccion = false;
            boolean variablesSeccion = false;
            boolean prototypesSeccion = false;
            boolean rutinesSeccion = false;
            String programNameStr = "";
            int tokensAmount = tokensTrue.size();
            
            for (int i = 0; i < tokensAmount; i++) {
                Token tmpToken = tokensTrue.get(i);
                //System.out.println(tokensTrue.get(i).toString2());
                if (tmpToken.getTipo() == TipoToken.SECCION) {
                    if (!programName) { //definicion de nombre del programa
                        programName = true;
                        /*programNameStr = tokensTrue.get(i+1).getLexema();
                        System.out.println("Nombre del programa: "+ programNameStr);
                        ScopeIdentifier tmpIdentifier = new ScopeIdentifier("Global",programNameStr);
                        SymbolInfo tmpInfo = new SymbolInfo("SECCION","",tmpToken.getLinea(), true);
                        this.globalSymbolTable.put(tmpIdentifier, tmpInfo);*/
                        i = i + 2;
                    } else if (tmpToken.getLexema().equals("octará")) { //seccion de constantes
                        constSeccion = true;
                        typesSeccion = false;
                        variablesSeccion = false;
                        prototypesSeccion = false;
                        rutinesSeccion = false;
                    } else if (tmpToken.getLexema().equals("aíca")) { //seccion de tipos
                        constSeccion = false;
                        typesSeccion = true;
                        variablesSeccion = false;
                        prototypesSeccion = false;
                        rutinesSeccion = false;
                    } else if (tmpToken.getLexema().equals("tilhtilh")) { //seccion de variables
                        constSeccion = false;
                        typesSeccion = false;
                        variablesSeccion = true;
                        prototypesSeccion = false;
                        rutinesSeccion = false;
                    } else if (tmpToken.getLexema().equals("chá")) { //seccion de prototipos (.h) en C
                        constSeccion = false;
                        typesSeccion = false;
                        variablesSeccion = false;
                        prototypesSeccion = true;
                        rutinesSeccion = false;
                    } else if (tmpToken.getLexema().equals("purú")) { //seccion de rutinas
                        constSeccion = false;
                        typesSeccion = false;
                        variablesSeccion = false;
                        prototypesSeccion = false;
                        rutinesSeccion = true;
                    }
                } else if (constSeccion && tmpToken.getTipo() == TipoToken.ASIGNACION) {
                    Token constNameToken = tokensTrue.get(i+1);
                    Token constValueToken = tokensTrue.get(i+2);
                    String value = constValueToken.getLexema();
                    String dataType;
                    if (value.startsWith("\"")) {
                        dataType = "ílo";
                    } else if (value.startsWith("\'")) {
                        dataType = "malhióca";
                    } else if (value.equals("tócu") || value.equals("maíca")) {
                        dataType = "afá";
                    } else if (isInteger(value)) {
                        dataType = "chátené";
                    } else if (isFloat(value)) {
                        dataType = "jané";
                    } else {
                        dataType = "undefined";
                    }
                    ScopeIdentifier tmpIdentifier = new ScopeIdentifier("Global",constNameToken.getLexema());
                    SymbolInfo tmpInfo = new SymbolInfo(dataType,value,constValueToken.getLinea(), true);
                    this.addSymbol(tmpIdentifier, tmpInfo);
                    //this.globalSymbolTable.put(tmpIdentifier,tmpInfo);
                } else if (typesSeccion && tmpToken.getTipo() == TipoToken.ASIGNACION) {
                    Token constNameToken = tokensTrue.get(i+1);
                    Token constValueToken = tokensTrue.get(i+2);
                    String value = constValueToken.getLexema();
                    ScopeIdentifier tmpIdentifier = new ScopeIdentifier("Global",constNameToken.getLexema());
                    SymbolInfo tmpInfo = new SymbolInfo(value,"",constValueToken.getLinea(), true);
                    this.addSymbol(tmpIdentifier, tmpInfo);
                    //this.globalSymbolTable.put(tmpIdentifier,tmpInfo);
                } else if (variablesSeccion && tmpToken.getTipo() == TipoToken.TIPO_DATO) {
                    String dataType = tmpToken.getLexema();
                    String identifier = tokensTrue.get(i+1).getLexema();
                    String value = tokensTrue.get(i+3).getLexema();
                    ScopeIdentifier tmpIdentifier = new ScopeIdentifier("Global",identifier);
                    SymbolInfo tmpInfo = new SymbolInfo(dataType,value,tmpToken.getLinea(),false);
                    this.addSymbol(tmpIdentifier, tmpInfo);
                    //this.globalSymbolTable.put(tmpIdentifier,tmpInfo);
                }
            }
            return this.globalSymbolTable;
        }
        
        public static boolean isInteger(String value) {
            try {
                Integer.parseInt(value);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        
        public static boolean isFloat(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}