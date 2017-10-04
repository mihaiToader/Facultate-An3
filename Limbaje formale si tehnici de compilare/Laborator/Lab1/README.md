# Lab 1

**Cerinta:** http://www.cs.ubbcluj.ro/~motogna/FLandCD.htm

Utils:  
http://matt.might.net/articles  
http://matt.might.net/articles/grammars-bnf-ebnf/  


BNF:

//Daca e <> semnatura a BNF-ului

<program> ::= "pisica" <declist> "caine"
<declist> ::= "hello" <s> "~" | <declist>
<s> ::= "a" | "b"


Ex de program dupa legile de mai sus:    

pisica  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hello a  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;hello b  
caine   

&nbsp;|&nbsp;   
-----|-----
constanta|0  
variabila|1  
pisica|2  
caine|3  
hello|4  
~|5    

!!!  
Variabile globale  
Fara recursivitate  
Fara clase  
Nothing fancy :smiley:     

Tema lab: 1b2a3b
