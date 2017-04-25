

  Como utilizo os Controllers para as classes existentes.
  Faço como um loop de conversas entre os controllers.
  Como na Figura abaixo:
  
    ![Logica Controllers](https://github.com/andersonlss/tccChess/blob/master/logica.png?raw=true)
    
  Seguindo as condições:
      
      -> Ctrl_ImgProc espera para tirar as fotos depois que o Ctrl_Robix terminar sua operação;
          Se liberar, é obtida a StrMoveImg
      
      -> Ctrl_ChessIA espera para efetuar a jogada depois que Ctrl_ImgProc obter a jogada do tabuleiro;
          Se liberar, é obtida a StrMoveIA
          
      -> Ctrl_Robix espera para efetuar os movimentos depois que Ctrl_ChessIA obter a jogada da IA;
          Se liberar, é efetuada a jogada e para sua execução
