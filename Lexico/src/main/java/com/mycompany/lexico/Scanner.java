/**
 * SUPER IMPORTANTE: configure bien las variables:
 * @archivoTaf y @archivoHTML
 * las carpetas deben existir y el archivo test.taf tambien (este es el código a compilar)
 * el tokens.html se genera en esa carpeta
 * no funciona si la carpeta no existe
 */

package com.mycompany.lexico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private BufferedReader reader;
    private char currentChar;
    private int currentLine = 1;
    private int currentColumn = 0;
    private int errorCount = 0;
    private static String archivoTaf = "C:\\Tafac\\test.taf"; //super importante de asegurarse que exista
    private static String archivoHTML = "C:\\Tafac\\tokens.html"; // esto también, pero el tokens.html no es obligatorio que esté en la carpeta, ya que se genera cuando se compila
    private int tipoCount = 0;
    private int tokensCount = 0;

    public Scanner(String fileName) throws IOException {
        reader = new BufferedReader(new FileReader(fileName));
        currentChar = (char) reader.read();
    }

    // cierra el reader
    public void finalizeScanner() throws IOException {
        reader.close();
    }

    // lee el siguiente caracter del archivo.taf 
    private void demeCaracter() throws IOException {
        currentChar = (char) reader.read();
        currentColumn++;
    }

    // Función auxiliar de saltaEspacio() verifica si el caracter es un espacio en blanco, un tab, un cambio de línea o un carriage (?)
    private boolean esEspacio(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r';
    }

    //salta múltiples espacios de todo tipo
    private void saltaEspacios() throws IOException {
        while (esEspacio(currentChar)) 
        {
            if (currentChar == '\n') 
            {
                currentLine++;
                currentColumn = 0;
            }
            demeCaracter();
        }
    }

    // la función gordita que lee cada token, lo clasifica y los cuenta
    private Token demeToken() throws IOException 
    {
        saltaEspacios();
        StringBuilder lexema = new StringBuilder();
        int line = currentLine;
        int column = currentColumn;

        // Verificar el final del archivo   
        if (currentChar == (char) -1) 
        {
            return new Token(TipoToken.EOF, "", line, column);
        }
        // Reconocimiento de tokens:
        // primero verifica que empiece con caractero o dígito
        if (Character.isLetter(currentChar)) 
        {
            // este ciclo construye el lexema
            while (Character.isLetterOrDigit(currentChar)) 
            {
                lexema.append(currentChar);
                demeCaracter();
            }
            String lexemaStr = lexema.toString();
            // switch que clasifica el lexema en su tipo
            switch (lexemaStr) 
            {
                //Secciones
                case "nhequéquequé","octará","aíca","tilhtilh","chá","purú" ->
                {
                    return new Token(TipoToken.SECCION, lexemaStr, line, column);
                }
                //Tipos de dato
                case "chátené", "ílo", "afá","apúchá","lhalámi","jané","coyé","poréteca" -> 
                {
                    return new Token(TipoToken.TIPO_DATO, lexemaStr, line, column);
                }
                case "tócu","maíca" ->
                {
                    return new Token(TipoToken.LITERAL_BOOLEANA, lexemaStr, line, column);
                }
                case "ta","yú","pucá","emé"->
                {
                    return new Token(TipoToken.OPERADOR_LOGICO, lexemaStr, line, column);
                }
                case "taqué","jené" ->
                {
                    return new Token(TipoToken.INCREMENTO_DECREMENTO, lexemaStr, line, column);
                }
                case "macorróca","óri" ->
                {
                    return new Token(TipoToken.ENCABEZADO, lexemaStr, line, column);
                }
                case "ajá", "lha", "telele", "sóta" -> {
                    return new Token(TipoToken.CICLO, lexemaStr, line, column);
                }
                case "arú", "nocófa", "tiáfa" -> {
                    return new Token(TipoToken.CONDICIONAL, lexemaStr, line, column);
                }
                case "cojcoj", "fuchí" ->
                {
                    return new Token(TipoToken.AGRUPADORES, lexemaStr, line, column);
                }
                case "torré","ári", "mailhíco"-> 
                {
                    return new Token(TipoToken.SWITCH, lexemaStr, line, column);
                }
                case "arrá","putú", "nicó"-> 
                {
                    return new Token(TipoToken.ASIGNACION, lexemaStr, line, column);   
                }
                case "catáqui", "lharátesufá", "joné", "nhalh", "cochéqui" ->
                {
                    return new Token(TipoToken.OPERADOR_STRING, lexemaStr, line, column);
                }
                case "erronh", "quené", "fuinhye", "sulí", "pcalúfa", "culíte" ->
                {
                    return new Token(TipoToken.OPERADOR_CONJUNTO, lexemaStr, line, column);
                }   
                case "quijí", "eloc", "lhioc", "lhaic", "quijérri", "láte" ->
                {
                    return new Token(TipoToken.OPERADOR_ARCHIVO, lexemaStr, line, column);        
                }
                case "jalac" ->
                {
                    return new Token(TipoToken.RETURN, lexemaStr, line, column);
                }
                case "icá" ->
                {
                    return new Token(TipoToken.SIZEOF, lexemaStr, line, column);
                }
                case "taráne" ->
                {
                    return new Token(TipoToken.CONTINUE, lexemaStr, line, column);
                }
                case "tirríque" ->
                {
                    return new Token(TipoToken.COHERCION, lexemaStr, line, column);
                }
                case "tiní" ->
                {
                    return new Token(TipoToken.WITH, lexemaStr, line, column);
                }
                case "lauc" ->
                {
                    return new Token(TipoToken.HALT, lexemaStr, line, column);
                }
                default -> 
                {
                    if (Character.isDigit(lexemaStr.charAt(0))) 
                    {
                        String lineaError = obtenerLineaError(line);
                        reportarError("\nError, se encontro un caracter numerico al inicio de un identificador:", lineaError, column);
                    }
                    return new Token(TipoToken.IDENTIFICADOR, lexemaStr, line, column);
                }
            }
        } 
        else // no es letra
            if (Character.isDigit(currentChar)) // si es dígito
            {
                // Número entero o decimal
                while (Character.isDigit(currentChar)) 
                {
                    lexema.append(currentChar);
                    demeCaracter();
                }
                if (currentChar == '.') // si es un dígito decimal
                {
                    lexema.append(currentChar);
                    demeCaracter();
                    while (Character.isDigit(currentChar)) //toma el resto de dígitos del numero decimal
                    {
                        lexema.append(currentChar);
                        demeCaracter();
                    }
                    return new Token(TipoToken.LITERAL_FLOTANTE, lexema.toString(), line, column);
                } 
                else // encontró un caracter no numérico después de un número en un mismo lexema
                {
                    if (Character.isLetter(currentChar)) // si encontró un número que tiene una letra de por medio Ej: 15.16h6 
                    {
                        String lineaError = obtenerLineaError(line);
                        reportarError("\nError, se encontro un caracter invalido en un numero:", lineaError, column);
                    }
                    return new Token(TipoToken.LITERAL_ENTERO, lexema.toString(), line, column);
                }
            } 
            else // no es ni número ni letra
            {
                // este ciclo construye el lexema mientras no sea un dígito o una letra
                while (currentChar != (char) -1 && !Character.isLetterOrDigit(currentChar) && currentChar != '\n') 
                {
                    if (currentChar == '#') 
                    {
                        lexema.append(currentChar);
                        demeCaracter(); // Consumir el primer '#'

                        if (currentChar == '#') {
                            // Es un comentario, ignorar el resto de la línea
                            while (currentChar != '\n' && currentChar != (char) -1) {
                                demeCaracter();
                            }
                            // Llamar recursivamente para obtener el próximo token
                            return demeToken();
                        } else {
                            // No es un comentario, agregar el '#' al lexema
                            lexema.append('#');
                        }
                    }
                    
                    // Verifica si no es EOF y no es letra ni dígito ni salto de línea
                    lexema.append(currentChar);
                    demeCaracter();     
                }
                if (lexema.charAt(lexema.length() - 2) == ';') 
                {
                // Si el último carácter agregado es un ; entonces elimina el salto de línea siguiente
                     lexema.deleteCharAt(lexema.length() - 1);
                }
                String lexemaStr = lexema.toString();
                // switch que clasifica el lexema en su tipo
                switch (lexemaStr) 
                {
                    // Asignaciones
                    case ":=","+=","/=","-=","*=" ->
                    {
                        return new Token(TipoToken.ASIGNACION, lexemaStr, line, column);
                    }
                    case "<<", ">>", "&?","#?" ->
                    {
                        return new Token(TipoToken.OPERADOR_CHAR, lexemaStr, line, column);
                    }
                    case "|++","++|","|+|","+|+" ->
                    {
                        return new Token(TipoToken.OPERADOR_FLOTANTE, lexemaStr, line, column);
                    }
                    case ">","<","=", ">=", "<=", "><" ->
                    {
                        return new Token(TipoToken.OPERADOR_COMPARACION, lexemaStr, line, column);
                    }
                    case "+", "-", "*", "%", "/" ->
                    {
                         return new Token(TipoToken.OPERADOR_ENTERO, lexemaStr, line, column);
                    }
                    case ";" ->
                    {  
                         return new Token(TipoToken.TERMINADOR, lexemaStr, line, column);
                    }
                    case "##" ->
                    {
                        return new Token(TipoToken.COMENTARIO, lexemaStr, line, column);
                    }
                    default ->
                    {
                        String lineaError = obtenerLineaError(line);
                        reportarError("\nError, caracter monstruo", lineaError, column);
                        return new Token(TipoToken.ERROR, lexemaStr, line, column);
                    }
                }    
            }
                   
    } // demeToken
    
    public void escribirTokensHTML(List<TokenHTML> tokens) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivoHTML))) {
        // Contenido del archivo HTML
        writer.write("<!DOCTYPE html>\n<html>\n<head>\n<style>\ntable {\nfont-family: Arial, sans-serif;\nborder-collapse: collapse;\nwidth: 100%;\n}\n\nth, td {\nborder: 1px solid #dddddd;\ntext-align: left;\npadding: 8px;\n}\n\nth {\nbackground-color: #f2f2f2;\n}\n</style>\n</head>\n<body>\n");
        writer.write("<h2>Tokens</h2>\n<table>\n<tr><th>Lexema</th><th>Fila</th><th>Columna</th><th>Tipo</th></tr>\n");

        // Mapa para contar los tipos de tokens
        Map<String, Integer> contadorTokens = new HashMap<>();
        
        for (TokenHTML token : tokens) {
            // Escribir fila de la tabla para cada token
            writer.write(token.toHTMLTableRow() + "\n");

            // Actualizar el contador para el tipo de token actual
            contadorTokens.put(token.getTipo(), contadorTokens.getOrDefault(token.getTipo(), 0) + 1);
        }

        writer.write("</table>\n");

        // Escribir contador de tokens
        writer.write("<h2>Contador de Tokens</h2>\n<ul>\n");
        for (Map.Entry<String, Integer> entry : contadorTokens.entrySet()) {
            writer.write("<li>" + entry.getKey() + ": " + entry.getValue() + "</li>\n");
        }
        writer.write("</ul>\n");

        writer.write("</body>\n</html>");
    }
}
    
    private String obtenerLineaError(int numeroLinea) throws IOException {
        //Toma el archivo de nuevo para verificar la línea
        BufferedReader fileReader = new BufferedReader(new FileReader(archivoTaf));
        String linea;
        int contadorLineas = 1;
        while ((linea = fileReader.readLine()) != null) 
        {
            if (contadorLineas == numeroLinea) 
            {
                fileReader.close();
                return linea;
            }
            contadorLineas++;
        }
        fileReader.close();
        return "";
}

    private void reportarError(String mensaje, String linea, int columna) {
        errorCount++;
        StringBuilder errorLine = new StringBuilder();
        for (int i = 1; i <= columna; i++) {
            errorLine.append('-');
        }
        errorLine.append('^');
        System.out.println(mensaje);
        System.out.println(linea);
        System.out.println(errorLine.toString());
}

    public Token tomeToken() throws IOException {
        return demeToken();
    }

    public int obtenerNumeroErrores() {
        return errorCount;
    }

    public static void main(String[] args) {
        System.out.print("\n\n\n");
        List<TokenHTML> tokensHTML = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(archivoTaf);
            Token token;
            do {
                token = scanner.tomeToken();
                tokensHTML.add(new TokenHTML(token.getLexema(), token.getLinea(), token.getColumna(), token.getTipo().toString()));
                System.out.println(token);
            } while (token.getTipo() != TipoToken.EOF);
            scanner.finalizeScanner();
            int errores = scanner.obtenerNumeroErrores();
            if (errores == 0) {
                System.out.println("\nCompilacion correcta.");
            } else {
                System.out.println("\nSe encontraron " + errores + " errores durante la compilacion.");
            }
            scanner.escribirTokensHTML(tokensHTML);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
