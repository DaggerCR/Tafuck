datos segment

    nombre db 100 DUP(?) 
    handle dw ?
    buffy  db ?

    msgerror db "error :($"

datos ends

                  
pila segment stack 'stack'
    dw 256 dup (?)
pila ends

codigo segment
    assume  cs:codigo, ds:datos, ss:pila
                                                                             
inicio: 
    mov ax, datos
    mov ds, ax
    mov ax, pila
    mov ss, ax

    mov si, 81h     
    mov di, offset nombre

copy_filename:
    lodsb
    stosb
    cmp al, 0
    jne copy_filename


    mov ah, 3Dh     ; abrir archivo
    lea dx, nombre  
    xor cx, cx      ;solo lectura
    int 21h         
    jc error        ; Manejar error 

    mov handle, ax  ; Guardar el manejador de archivo

   
read_loop:
    mov ah, 3Fh     
    mov bx, handle  
    lea dx, buffy   
    mov cx, 1       
    int 21h         
    jc error_read   

    ; Verificar si llegamos al final del archivo
    cmp ax, 0
    je end_read

    
    mov dl, buffy
    mov ah, 02h     
    int 21h         

    jmp read_loop   

end_read:

    mov ah, 3Eh     ;cerrar archivo
    mov bx, handle  
    int 21h         

    jmp exit        ; Salir del programa

error:
    lea dx, msgerror
    mov ah, 09h     ; Servicio 09h - mostrar cadena de caracteres
    int 21h         ; Llamada al sistema de DOS
    jmp exit

error_read:
    lea dx, msgerror
    mov ah, 09h     ;mostrar cadena de caracteres
    int 21h         
    jmp end_read

exit:
    mov ax, 4C00h   
    int 21h         
     
codigo ends
end inicio
