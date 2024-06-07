package com.mycompany.lexico;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author danie
 */
public class Tafuck extends javax.swing.JFrame {

    public Tafuck() {
        initComponents();
        this.setTitle("Tafac Compiler");
    }

    private File currentFile;
    List<Token> tokensAux;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Scanner = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Editor = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        Salida = new javax.swing.JTextArea();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        Gaurdar = new javax.swing.JMenuItem();
        Abrir = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Scanner.setText("Scanner");
        Scanner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScannerActionPerformed(evt);
            }
        });

        jButton2.setText("Parser");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Semántico");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Generar código");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        Editor.setColumns(20);
        Editor.setRows(5);
        Editor.setTabSize(2);
        jScrollPane2.setViewportView(Editor);

        Salida.setEditable(false);
        Salida.setColumns(20);
        Salida.setRows(5);
        Salida.setWrapStyleWord(true);
        jScrollPane4.setViewportView(Salida);

        jMenu3.setText("Archivo");

        Gaurdar.setText("Guardar");
        Gaurdar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GaurdarActionPerformed(evt);
            }
        });
        jMenu3.add(Gaurdar);

        Abrir.setText("Abrir");
        Abrir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirActionPerformed(evt);
            }
        });
        jMenu3.add(Abrir);

        jMenuBar2.add(jMenu3);

        jMenu4.setText("Manual");

        jMenuItem1.setText("Léeme");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem1);

        jMenuBar2.add(jMenu4);

        setJMenuBar(jMenuBar2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 541, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(Scanner)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Scanner)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(79, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        this.Salida.setText("");
        String portada;
        String inicio;
        String codigo;
        String datos1;
        String datos2;
        String pila;
        String halt;
        String fin;
        String llamadaImprimir = "";
        String variables = "";
        String asignaciones = "\n\n";
        String salidas = "\n";
        String sumas = "";
        String restas = "";
        
        String userHome = System.getProperty("user.home");
        String directoryPath = userHome + "/Documents/Salida_Tafac";
        String filePath = directoryPath + "/output.asm";
        
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); 
        }

        portada = "; El proyecto " + this.currentFile + " es hecho por Daniel Sequeira y kenneth Palacios\n\n";

        datos1 = "datos segment\n    buffer db 6 dup (?) ; Buffer para imprimir numeros \n";
        datos2 = "datos ends\n";

        pila = """
               \n
               pila segment stack 'stack'
                   dw 256 dup (?) ; Definición de la pila
               pila ends
               """;

        codigo = """
                 \n
                 codigo segment
                     assume cs:codigo, ds:datos, ss:pila
                 """;

        inicio = """
                \n
                inicio:
                    mov ax, ds    ; ponemos al ES a apuntar al inicio del PSP
                    mov es, ax
                
                    mov ax, datos  ; El DS que apuntaba al PSP se debe poner a apuntar al segmento de datos para 
                    mov ds, ax     ; poder usar las variables.
                
                    mov ax, pila
                    mov ss, ax""";

        halt = """ 
              \n
                  mov ax, 4C00h  
                  int 21h  
              """;
        fin = """
              
              codigo ends
              end inicio
              """;

        List<String> variablesUsadas;
        variablesUsadas = new ArrayList<>();
        
        List<String> variablesIndices;
        variablesIndices = new ArrayList<>();
        
        // ciclo para crear variables
        for (int i = 0; i < tokensAux.size(); i++) {
            Token token = tokensAux.get(i);

            switch (token.getTipoString()) {
                case "IDENTIFICADOR":
                    String newVariable = token.getLexema();
                    if(!variablesUsadas.contains(newVariable))
                    {
                        variables = variables + "    " + newVariable + "_var" + i +" dw ? \n";
                        variablesUsadas.add(newVariable);
                        variablesIndices.add(""+i);
                    }
                case "ASIGNACION":
                    if (token.getLexema().equals("nicó") || token.getLexema().equals(":= ")) {
                        Token tokenPre = tokensAux.get(i - 1);
                        Token tokenPost = tokensAux.get(i + 1);
                        asignaciones = asignaciones + "   mov word ptr " + tokenPre.getLexema() + "_var" + (i - 1) + ", " + tokenPost.getLexema() + "\n";
                    }
                    if (token.getLexema().equals("arrá")) {
                        Token tokenPost = tokensAux.get(i + 1);
                        Token tokenPostPost = tokensAux.get(i + 2);
                        asignaciones = asignaciones + "   mov word ptr " + tokenPost.getLexema() + "_var" + (i + 1) + ", " + tokenPostPost.getLexema() + "\n";
                    }
                    if (token.getLexema().equals("+= ")) {
                        Token tokenPre = tokensAux.get(i - 1);
                        Token tokenPost = tokensAux.get(i + 1);
                        
                        int index = variablesUsadas.indexOf(tokenPre.getLexema());
                        sumas = sumas + "\n   add " + tokenPre.getLexema() + "_var" + (variablesIndices.get(index))+"," +tokenPost.getLexema() +"\n";
                    }
                    if (token.getLexema().equals("-= ")) {
                         Token tokenPre = tokensAux.get(i - 1);
                        Token tokenPost = tokensAux.get(i + 1);
                        
                        int index = variablesUsadas.indexOf(tokenPre.getLexema());
                        sumas = sumas + "\n   sub " + tokenPre.getLexema() + "_var" + (variablesIndices.get(index))+"," +tokenPost.getLexema() +"\n";
                    }
                case "SALIDA":
                    if (token.getLexema().equals("lacchátené")) {
                        Token tokenPost = tokensAux.get(i + 1);
                        int index = variablesUsadas.indexOf(tokenPost.getLexema());
                        System.out.println(""+tokenPost.getLexema() + " " + variablesIndices.get(index));
                        
                        salidas = salidas + "imprime_" + tokenPost.getLexema() + "_var" + (variablesIndices.get(index))+":\n";
                        salidas = salidas + "    mov ax,"+ tokenPost.getLexema() + "_var" + (variablesIndices.get(index)) +"       \n" +
                            "    mov bx, 10         \n" +
                            "    lea si, buffer     \n" +
                            "    mov cx, 0 ";
                        salidas = salidas + "\nconv_loop:\n" +
                            "    xor dx, dx         \n" +
                            "    div bx             \n" +
                            "    add dl, '0'        \n" +
                            "    mov [si], dl       \n" +
                            "    inc si             \n" +
                            "    inc cx             \n" +
                            "    or ax, ax          \n" +
                            "    jnz conv_loop      \n" +
                            "\n" +
                            "    lea si, buffer     \n" +
                            "    add si, cx         \n" +
                            "    dec si             \n" +
                            "\n" +
                            "print_loop:\n" +
                            "    mov dl, [si]       ; Cargar el siguiente dígito en DL\n" +
                            "    mov ah, 02h        ; Función DOS para imprimir un carácter\n" +
                            "    int 21h            ; Llamar a la interrupción DOS\n" +
                            "    dec si             ; Retroceder un byte\n" +
                            "    loop print_loop    ; Repetir hasta que se impriman todos los dígitos\n" +
                            "\n" +
                            "    ret                ; Volver al programa principal";
                        
                        llamadaImprimir = llamadaImprimir + "\n   call imprime_" +tokenPost.getLexema() + "_var" + (variablesIndices.get(index));

                    }

            }
        }
        this.Salida.setText(portada
                + datos1 + variables + datos2 + pila + codigo
                + inicio + asignaciones + sumas +llamadaImprimir + halt +salidas + fin);

        String content = this.Salida.getText();

        // Especificar la ruta y el nombre del archivo

        try (FileWriter writer = new FileWriter(new File(filePath))) {
            // Escribir el contenido en el archivo
            writer.write(content);
            // Mostrar un mensaje de éxito
            JOptionPane.showMessageDialog(this, "Archivo guardado exitosamente en " + filePath);
        } catch (IOException e) {
            // Mostrar un mensaje de error en caso de fallo
            JOptionPane.showMessageDialog(this, "Error al guardar el archivo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void AbrirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            File selectedFile = fileChooser.getSelectedFile();
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                this.Editor.read(reader, null);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + ex.getMessage());
            }
        }
    }//GEN-LAST:event_AbrirActionPerformed

    private void guardar() {
        if (currentFile != null) {
            try (FileWriter writer = new FileWriter(currentFile)) {
                Editor.write(writer);
                JOptionPane.showMessageDialog(null, "Archivo guardado exitosamente.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al guardar el archivo: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay ningún archivo abierto.");
        }
    }

    private void GaurdarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GaurdarActionPerformed
        this.guardar();

    }//GEN-LAST:event_GaurdarActionPerformed

    private void ScannerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScannerActionPerformed

        if (currentFile != null) {
            this.guardar();
            this.Salida.setText("");
            try {
                // Crear una instancia de Scanner y escanear el archivo
                Scanner scanner = new Scanner(currentFile.toString()); // Asegúrate de que el constructor sea correcto
                List<Token> tokens = scanner.scan2(currentFile.toString());

                // Imprimir los tokens en la consola
                for (int i = 0; i < tokens.size(); i++) {
                    Token token = tokens.get(i);
                    if(token.getTipoString().equals("ERROR"))
                         this.Salida.setText(this.Salida.getText()+"\n- - - - - - ->");
                    System.out.println("- Tipo: " + token.getTipoString()
                            + "  Lexema: " + token.getLexema() + "   Fila: " + token.getLinea() + "   Columna: " + token.getColumna());
                    this.Salida.setText(this.Salida.getText() + "\n"
                            + "Tipo: " + token.getTipoString()
                            + "  Lexema: " + token.getLexema() + "   Fila: " + token.getLinea() + "   Columna: " + token.getColumna() + "\n");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al escanear el archivo: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(null, "No hay ningún archivo abierto.");
        }
    }//GEN-LAST:event_ScannerActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showMessageDialog(null, """
                                            NOTA: siempre ponga un salto de línea al final del código
                                            NOTA 2: cargue siempre un archivo antes de empezar a programar
                                            NOTA 3: antes de ejecutar una etapa siempre se guardarán los cambios hechos
                                            NOTA 4: Antes de ejecutar el Semántico o la generación de código debe ejecutar el Parser y el Scanner
                                            NOTA 5: Cuando se genera código, este guarda el archivo .asm en una carpeta nueva en documentos llamada Salida_Tafac
                                            
                                            Para cargar un archivo en el editor presione Archivo → Abrir
                                            Para Guardar el texto, presione Archivo → Guardar
                                            Para probar una etapa, cargue el código y seleccione el botón que desea""", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        this.guardar();
        this.Salida.setText("");
        Grammar grammar = new Grammar();
        grammar.setStartSymbol("<S>");

        grammar.addNonTerminal("<S>");
        grammar.addNonTerminal("<loop-constantes>");
        grammar.addNonTerminal("<literal>");
        grammar.addTerminal("octará");
        grammar.addTerminal("arrá");
        grammar.addTerminal("IDENTIFICADOR");
        grammar.addTerminal("LITERAL_FLOTANTE");
        grammar.addTerminal("LITERAL_BOOLEANA");
        grammar.addTerminal("LITERAL_ENTERO");
        grammar.addTerminal("COMA");
        grammar.addTerminal("TERMINADOR");
        grammar.addTerminal("TIPO_DATO");

        grammar.addNonTerminal("<loop-definicion>");
        grammar.addTerminal("aíca");
        grammar.addTerminal("putú");

        grammar.addTerminal("tilhtilh");
        grammar.addTerminal("nicó");
        grammar.addNonTerminal("<seccion-variables>");

        grammar.addTerminal("nhequéquequé");

        //grammar.addNonTerminal("<>");
        grammar.addTerminal(":= ");
        grammar.addTerminal("+= ");
        grammar.addTerminal("/= ");
        grammar.addTerminal("*= ");
        grammar.addTerminal("-= ");
        grammar.addTerminal("ASIGNACION");

        grammar.addTerminal("% ");
        grammar.addTerminal("+ ");
        grammar.addTerminal("/ ");
        grammar.addTerminal("* ");
        grammar.addTerminal("- ");
        grammar.addTerminal("OPERADOR_ENTERO");

        //nombre del programa
        grammar.addProduction("<S>", Arrays.asList("nhequéquequé", "IDENTIFICADOR", "TERMINADOR"));
        // Sección de constantes
        grammar.addProduction("<S>", Arrays.asList("octará", "arrá", "<loop-constantes>"));
        //grammar.addProduction("<loop-constantes>", Arrays.asList("IDENTIFICADOR", "<literal>", "COMA", "<loop-constantes>"));
        grammar.addProduction("<loop-constantes>", Arrays.asList("IDENTIFICADOR", "<literal>", "TERMINADOR"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_FLOTANTE"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_BOOLEANA"));
        grammar.addProduction("<literal>", Arrays.asList("LITERAL_ENTERO"));
        
        grammar.addProduction("<S>", Arrays.asList("arrá", "<loop-constantes>"));

        //sección de tipos
        grammar.addProduction("<S>", Arrays.asList("aíca", "<loop-definicion>"));
        grammar.addProduction("<loop-definicion>", Arrays.asList("putú", "IDENTIFICADOR", "TIPO_DATO", "TERMINADOR"));

        //sección de variables
        grammar.addProduction("<S>", Arrays.asList("tilhtilh", "<seccion-variables>"));
        grammar.addProduction("<seccion-variables>", Arrays.asList("TIPO_DATO", "IDENTIFICADOR", "nicó", "<literal>", "TERMINADOR"));
        grammar.addProduction("<S>", Arrays.asList("TIPO_DATO", "IDENTIFICADOR", "nicó", "<literal>", "TERMINADOR"));

        //asiganaciones
        grammar.addProduction("<S>", Arrays.asList("IDENTIFICADOR", "ASIGNACION", "<literal>", "TERMINADOR"));

        //operador entero
        grammar.addProduction("<S>", Arrays.asList("IDENTIFICADOR", "OPERADOR_ENTERO", "IDENTIFICADOR", "TERMINADOR"));

        grammar.addNonTerminal("<salidaTipo>");
        grammar.addTerminal("lacchátené");
        grammar.addTerminal("SALIDA");

        grammar.addProduction("<S>", Arrays.asList("SALIDA", "IDENTIFICADOR", "TERMINADOR"));
        grammar.addProduction("<salidaTipo>", Arrays.asList("lacchátené"));

//----------------------------------------------------------
        // Calcular First y Follow
        FirstFollow firstFollowCalculator = new FirstFollow(grammar);
        firstFollowCalculator.calculateFirst();
        firstFollowCalculator.calculateFollow();

        // Calcular Predict
        PredictCalculator predictCalculator = new PredictCalculator(grammar, firstFollowCalculator);
        predictCalculator.calculatePredict();

        // Construir la tabla de parsing
        ParsingTableBuilder parsingTableBuilder = new ParsingTableBuilder(grammar, predictCalculator);
        ParsingTable parsingTable = parsingTableBuilder.getParsingTable();

        //obtiene los tokens del Scanner
        Scanner scanner;
        try {
            scanner = new Scanner(currentFile.toString()); // Asegúrate de que el constructor sea correcto
            List<Token> tokens = scanner.scan2(currentFile.toString());
            tokensAux = tokens;
            Parser2 parser = new Parser2(tokens, grammar.getProductions(), parsingTable, grammar);
            parser.parse();
            this.Salida.setText(parser.mensaje());
        } catch (IOException ex) {
            Logger.getLogger(Tafuck.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Crear el parser y ejecutar el análisis sintáctico

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        SymbolTable tabla = new SymbolTable();
        this.guardar();
        try {
            tabla.fillTable(tokensAux);
        } catch (SymbolTableCollisionException e) {
            System.out.println(e.toString());
        }
        System.out.println("---------------Tabla de simbolos---------------");
        tabla.printTable();
        this.Salida.setText("");
        this.Salida.setText(tabla.getTableAsString());
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Tafuck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Tafuck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Tafuck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Tafuck.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Tafuck().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Abrir;
    private javax.swing.JTextArea Editor;
    private javax.swing.JMenuItem Gaurdar;
    private javax.swing.JTextArea Salida;
    private javax.swing.JButton Scanner;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables

}
