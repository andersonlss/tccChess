Ultimas noticias.

  Consegui criar os Controllers para as classes existentes.
  Faço como um loop de conversas entre os controllers.
  Como na Figura abaixo
  
    ![Logica Controllers](andersonlss/tccChess/blob/master/logica.png)
    
  Seguindo as condições:
      
      -> Ctrl_ImgProc espera para tirar as fotos depois que o Ctrl_Robix terminar sua operação;
          Se liberar, é obtida a StrMoveImg
      
      -> Ctrl_ChessIA espera para efetuar a jogada depois que Ctrl_ImgProc obter a jogada do tabuleiro;
          Se liberar, é obtida a StrMoveIA
          
      -> Ctrl_Robix espera para efetuar os movimentos depois que Ctrl_ChessIA obter a jogada da IA;
          Se liberar, é efetuada a jogada e para sua execução
          
  
  No momento efetuei essas operações, se precisar mudar me informe, fiz assim q acho mais prático e 
  simples essa conversa entre os controllers.
  
  Pode executar o projeto diretamente, pq ja mudei a classe Main do projeto e não vai rodar o Robix,
  só tera uma saida de texto informando que o robix jogou.
  
  A classe Ctrl_ImgProc ja esta pronta, comentei seus metodos e indiquei aonde deve-se colocar as linhas de codigos
  que carregam as strings das imagens.
  
  
  Até mais, qualquer coisa me informe.
