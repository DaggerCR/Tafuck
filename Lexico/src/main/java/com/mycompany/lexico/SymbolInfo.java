
package com.mycompany.lexico;

/**
 *
 * @author kpala
 * Campo de la tabla de simbolos con los metadatos necesarios para el análisis
 */
public class SymbolInfo {
    String dataType;
    String additionalInfo;
    int line;
    boolean isConstant;

    @Override
    public String toString() {
        return "SymbolInfo{" + "dataType=" + dataType + ", additionalInfo=" + additionalInfo + ", line=" + line + '}';
    }

    public SymbolInfo() {
    }

    public SymbolInfo(String dataType, String additionalInfo, int line, boolean isConstant) {
        this.dataType = dataType;
        this.additionalInfo = additionalInfo;
        this.line = line;
        this.isConstant = isConstant;
    }

    public String getDataType() {
        return dataType;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public int getLine() {
        return line;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public boolean isIsConstant() {
        return isConstant;
    }

    public void setIsConstant(boolean isConstant) {
        this.isConstant = isConstant;
    }
    
    
}
