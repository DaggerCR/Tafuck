datos segment
    num1 db ?
datos ends

pila segment stack 'stack'
    dw 256 dup (?) ; Definición de la pila
pila ends

codigo segment
    assume cs:codigo, ds:datos, ss:pila

inicio:
    mov ax, ds    ; ponemos al ES a apuntar al inicio del PSP
    mov es, ax

    mov ax, datos  ; El DS que apuntaba al PSP se debe poner a apuntar al segmento de datos para 
    mov ds, ax     ; poder usar las variables.

    mov ax, pila
    mov ss, ax

    mov al, byte ptr [es:82h]  ; entrada
    sub al, 30h                ; Convertir de ASCII
    mov num1, al               ;asiganr a num1


    mov dl, num1   
    add dl, 30h    ; Convertir a ASCII
    mov ah, 02h    ; Función para imprimir un caracter en la salida estándar
    int 21h       

  
    mov ax, 4C00h  
    int 21h        

codigo ends
end inicio
