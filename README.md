# Projeto1_Desenvolvimento_Colaborativo_Agil
O objetivo deste projeto é implementar um jogo no qual um personagem controlado pelas quatro teclas direcionais deve ser conduzido pelo jogador de um ponto inicial a um ponto final de um labirinto. Esse labirinto deve ser lido de um arquivo no formato descrito anteriormente e exibido também via Swing. Além disso, esse personagem deve competir com um segundo controlado pelo computador, cuja inteligência artificial deve ser baseada em uma busca em profundidade (depth-first search) baseada em pilha. A ideia desse algoritmo é simples: uma pilha é utilizada como “trilha de migalhas de pão”, indicando ao computador o caminho feito desde o ponto inicial. Ao topar com um beco sem saída, o desempilhamento representa um “passo para trás” nessa trilha, que permite tentar outros cami- nhos anteriormente ignorados. Para detalhes de implementação, consulte referências da Internet e compareça aos Horários de Atendimento. Quando um dos dois personagens alcançar a saída, o jogo deve terminar indicando o vence- dor. Note que o gerador de labirintos permite alterar as dimensões através das constantes WIDTH e HEIGHT, portanto seu programa deve estar preparado para labirintos de tamanhos arbitrários.
