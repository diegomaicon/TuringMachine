

Este trabalho consiste em desenvolver um simulador para a Máquina de Turing não determinística,
doravante MT, conforme as especificações que seguem. A sintaxe dos comandos da MT foi inspirada
no formado adotado no simulador disponível em http://morphett.info/turing/turing.html.


prompt> sim tu ring −r palindromo .MT
Simulador de Máquina de Turing com Oráculo ve r 1 . 0
 De senvolvido como t r ab alh o p r á t i c o para a d i s c i p l i n a de Teo ria da Computação
Fulano de Tal , IFMG, 2017.

Forneça o l i m i t e de computações .
Valo r e s g rande s podem demandar muito tempo .
 (max: 1 0 0 0 ) −−> 150
 Forneça o l i m i t e de th r ead s simul tân ea s .
 Valo r e s g rande s podem demandar muito tempo .
 (max: 1 0 0 ) −−> 30

Forneça a palav ra i n i c i a l : bbbab

17 . . . . . . . . . . 0 0 0 1 : ____________________(b )bbab__________________ 
18 . . . . . . . . . . 0 0 0 1 : ____________________b(b )bab__________________
19 . . . . . . . . . . 0 0 0 1 : ____________________bb(b )ab__________________
20 . . . . N . . . . . 0 0 0 1 : ____________________bbb( a )b__________________
21 . . . . . . . . . . 0 0 0 2 : ____________________bbbb(b )__________________
22 . . . . . . . . . . 0 0 0 2 : ____________________bbbbb(_)_________________
23 . . . . . . . . . . 0 0 0 3 : ____________________bbbb(b )__________________
24 . . . . . . . . . . 0 0 0 3 : ____________________bbb(b )b__________________
25 . . . . . . . . . . 0 0 0 3 : ____________________bb(b )bb__________________
26 . . . . . . . . . . 0 0 0 3 : ____________________b(b )bbb__________________
27 . . . . . . . . . . 0 0 0 3 : ____________________(b )bbbb__________________
28 . . . . . . . . . . 0 0 0 3 : ___________________(_)bbbbb__________________
29 . . . . . . . . . . 0 0 0 6 : ____________________(b )bbbb__________________
30 . . . . . . . . a c e i t a


A letra N saída indica onde ocorreu um não-determinismo, onde o oráculo precisou escolher um
caminho para a computação continuar. O oráculo será simulado com o uso de threads, e a saída do
programa mostrará apenas a computação da thread que conseguiu aceitar a entrada. Se nenhuma
thread foi capaz de aceitar a entrada, então o programa interromperá depois da saída da primeira
thread (a linha com a letra N) com a mensagem: “recusada”. Se o número de threads ultrapassar
o limite especificado, o programa interromperá com a mensagem: “estouro do limite de threads”.
Se o limite de computações (transições percorridas) ultrapassar o limite especificado, o programa
interromperá com a mensagem: “estouro do limite de computações”. Note que o limite de computação
é para o programa todo e não para cada thread individualmente
