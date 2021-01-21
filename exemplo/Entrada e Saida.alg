algoritmo "I/O - Entrada/Sainda"

var nome    : string
    sexo    : caracter
    idade   : inteiro
    massa   : real

inicio
    escreva("Digite seu nome: ")
    leia(nome)
    
    escreva("Digite seu sexo (M OU F): ")
    leia(sexo)
    
    enquanto sexo <> 'M' e sexo <> 'F' faca
	    escreva("Digite seu sexo (M OU F): ")
	    leia(sexo)
    fimenquanto
    
    escreva("Digite sua idade: ")
    leia(idade)
    
    escreva("Digite sua massa: ")
    leia(massa)
    
    escreval("Seu perfil: =========================")
    escreval("Seu nome: ",nome)
    escreval("Seu sexo: ",sexo)
    escreval("Seu idade: ",idade)
    escreval("Sua massa: ",massa)
    
fimalgoritmo