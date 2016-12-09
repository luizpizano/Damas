public class Logica {

	private Peca tabuleiro[][];
	private int Peca1;
	private int Peca2;
	private int turno;
	
	public Logica(){
		turno = 1;
		tabuleiro = new Peca[8][8];
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				tabuleiro[i][j] = new Peca("Vazio");
			}
		}
		for(int i = 0; i < 8; i++){
			if(i % 2 == 1){
				tabuleiro[0][i].setNome("Peca1");
			}
		}
		for(int i = 0; i < 8; i++){
			if(i % 2 == 0){
				tabuleiro[1][i].setNome("Peca1");
			}
		}
		for(int i = 0; i < 8; i++){
			if(i % 2 == 1){
				tabuleiro[2][i].setNome("Peca1");
			}
		}
		for(int i = 0; i < 8; i++){
			if(i % 2 == 0){
				tabuleiro[5][i].setNome("Peca2");
			}
		}
		for(int i = 0; i < 8; i++){
			if(i % 2 == 1){
				tabuleiro[6][i].setNome("Peca2");
			}
		}
		for(int i = 0; i < 8; i++){
			if(i % 2 == 0){
				tabuleiro[7][i].setNome("Peca2");
			}
		}
		Peca1 = 12;
		Peca2 = 12;
	}
	
	public boolean verificarGanhou(int jogador){
		boolean ganhou = true;
		if(jogador == 1){
			for(int i = 0; i < 7; i++){
				for(int j = 0; j < 7; j++){
					if(tabuleiro[i][j].getNome().equals("Peca2") || tabuleiro[i][j].getNome().equals("Dama2")){
						ganhou = false;
					}
				}
			}
		}
		else{
			if(jogador == 2){
				for(int i = 0; i < 7; i++){
					for(int j = 0; j < 7; j++){
						if(tabuleiro[i][j].getNome().equals("Peca1") || tabuleiro[i][j].getNome().equals("Dama1")){
							ganhou = false;
						}
					}
				}
			}
		}
		return ganhou;
	}
	
	
	
	public synchronized String processarMensagem(String inic){
		String mensagem = new String();
		mensagem = "JI";
		int line; int column; int linefim; int columnfim; int i; int j; boolean damaok = true;
		line = inic.charAt(1) - 48;
		column = inic.charAt(2) - 48;
		linefim = inic.charAt(3) - 48;
		columnfim = inic.charAt(4) - 48;

		if(inic.charAt(0) == '1' && turno == 1){

			if(tabuleiro[line][column].getNome().equals("Peca1") && tabuleiro[linefim][columnfim].getNome().equals("Vazio")){
	

				if(linefim == (line+1)){
					

					if(Math.abs(columnfim - column) == 1){
						
						if(linefim == 7){
							tabuleiro[linefim][columnfim] = new Dama("1");
						} else{
							tabuleiro[linefim][columnfim].setNome("Peca1");
						}
						turno = 2;
						tabuleiro[line][column].setNome("Vazio");
						if(linefim == 7){
							mensagem = "1A"+line+column+"D"+linefim+columnfim;
						}
						else{
							mensagem = "1A"+line+column+"P"+linefim+columnfim;							
						}
						if(verificarGanhou(1)){
							mensagem += "G";
						}
					}
					else{
						mensagem = "JI";
					}
				}
				else if(linefim == (line+2)){
					if(Math.abs(columnfim - column) == 2){
						if(tabuleiro[linefim][columnfim].getNome().equals("Vazio") && (tabuleiro[linefim-1][(columnfim+column)/2].getNome().equals("Peca2") || tabuleiro[linefim-1][(columnfim+column)/2].getNome().equals("Dama2"))){
							tabuleiro[linefim-1][(columnfim+column)/2].setNome("Vazio");
							if(linefim == 7){
								tabuleiro[linefim][columnfim] = new Dama("1");
							} else{
								tabuleiro[linefim][columnfim].setNome("Peca1");
							}
							Peca2--;
							tabuleiro[line][column].setNome("Vazio");
							if(linefim == 7){
								mensagem = "1A"+line+column+"A"+(linefim-1)+((column+columnfim)/2)+"D"+linefim+columnfim+"C";
							} else{
								mensagem = "1A"+line+column+"A"+(linefim-1)+((column+columnfim)/2)+"P"+linefim+columnfim+"C";

							}
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						else{
							mensagem = "JI";
						}
					}
					else{
						mensagem = "JI";
					}
				}
				else{
					mensagem = "JI";
				}
			}
			else if(tabuleiro[line][column].getNome().equals("Dama1") && tabuleiro[linefim][columnfim].getNome().equals("Vazio")){
				if(Math.abs(line - linefim) == Math.abs(column - columnfim)){
					if(linefim > line && columnfim > column){
						for(i = line+1, j = column+1; damaok && i < linefim && j < columnfim; i++, j++){
							if(i != linefim-1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca2") && !tabuleiro[i][j].getNome().equals("Dama2")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 2;
							mensagem = "1A"+line+column+"A"+(linefim-1)+(columnfim-1)+"D"+linefim+columnfim;
							if(tabuleiro[linefim-1][columnfim-1].getNome().equals("Peca2") || tabuleiro[linefim-1][columnfim-1].getNome().equals("Dama2")){
								Peca2--;
								turno = 1;
								mensagem += "C";
								
							}
							tabuleiro[linefim][columnfim] = new Dama("1");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim-1][columnfim-1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
					else if(linefim > line && columnfim < column){
						for(i = line+1, j = column-1; damaok && i < linefim && j > columnfim; i++, j--){
							if(i != linefim-1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca2") && !tabuleiro[i][j].getNome().equals("Dama2")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 2;
							mensagem = "1A"+line+column+"A"+(linefim-1)+(columnfim+1)+"D"+linefim+columnfim;

							if(tabuleiro[linefim-1][columnfim+1].getNome().equals("Peca2") || tabuleiro[linefim-1][columnfim+1].getNome().equals("Dama2")){
								Peca2--;
								turno = 1;
								mensagem += "C";
							}
							tabuleiro[linefim][columnfim] = new Dama("1");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim-1][columnfim+1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
					else if(linefim < line && columnfim < column){
						for(i = line-1, j = column-1; damaok && i > linefim && j > columnfim; i--, j--){
							if(i != linefim+1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca2") && !tabuleiro[i][j].getNome().equals("Dama2")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 2;
							mensagem = "1A"+line+column+"A"+(linefim+1)+(columnfim+1)+"D"+linefim+columnfim;

							if(tabuleiro[linefim+1][columnfim+1].getNome().equals("Peca2") || tabuleiro[linefim+1][columnfim+1].getNome().equals("Dama2")){
								Peca2--;
								turno = 1;
								mensagem += "C";
							}
							tabuleiro[linefim][columnfim] = new Dama("1");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim+1][columnfim+1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
					else if(linefim < line && columnfim > column){
						for(i = line-1, j = column+1; damaok && i < linefim && j > columnfim; i--, j++){
							if(i != linefim+1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca2") && !tabuleiro[i][j].getNome().equals("Dama2")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 2;
							mensagem = "1A"+line+column+"A"+(linefim+1)+(columnfim-1)+"D"+linefim+columnfim;

							if(tabuleiro[linefim+1][columnfim-1].getNome().equals("Peca2") || tabuleiro[linefim+1][columnfim-1].getNome().equals("Dama2")){
								Peca2--;
								turno = 1;
								mensagem += "C";
							}
							tabuleiro[linefim][columnfim] = new Dama("1");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim+1][columnfim-1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
				}
				else{
					mensagem = "JI";
				}
			}
			else{
				mensagem = "JI";
			}


		}
		else if(inic.charAt(0) == '2' && turno == 2){
			if(tabuleiro[line][column].getNome().equals("Peca2") && tabuleiro[linefim][columnfim].getNome().equals("Vazio")){
				if(linefim == (line-1)){
					if(Math.abs(columnfim - column) == 1){
						if(linefim == 0){
							tabuleiro[linefim][columnfim] = new Dama("2");
						} else{
							tabuleiro[linefim][columnfim].setNome("Peca2");
						}
						turno = 1;
						tabuleiro[line][column].setNome("Vazio");
						if(linefim == 0){
							mensagem = "2A"+line+column+"D"+linefim+columnfim;
						}
						else{
							mensagem = "2A"+line+column+"P"+linefim+columnfim;
						}
						if(verificarGanhou(2)){
							mensagem += "G";
						}
					}
					else{
						mensagem = "JI";
					}
				}
				else if(linefim == (line-2)){
					if(Math.abs(columnfim - column) == 2){
						if(tabuleiro[linefim][columnfim].getNome().equals("Vazio") && (tabuleiro[linefim+1][(columnfim+column)/2].getNome().equals("Peca1") || tabuleiro[linefim+1][(columnfim+column)/2].getNome().equals("Dama1"))){
							tabuleiro[linefim+1][(columnfim+column)/2].setNome("Vazio");
							if(linefim == 0){
								tabuleiro[linefim][columnfim] = new Dama("2");
							} else{
								tabuleiro[linefim][columnfim].setNome("Peca2");
							}
							Peca1--;
							tabuleiro[line][column].setNome("Vazio");
							if(linefim == 0){
								mensagem = "2A"+line+column+"A"+(linefim+1)+((column+columnfim)/2)+"D"+linefim+columnfim;
							}
							else{
								mensagem = "2A"+line+column+"A"+(linefim+1)+((column+columnfim)/2)+"P"+linefim+columnfim;

							}
							mensagem += "C";
							if(verificarGanhou(2)){
								mensagem += "G";
							}
						}
						else{
							mensagem = "JI";
						}
					}
					else{
						mensagem = "JI";
					}
				}
				else{
					mensagem = "JI";
				}
			}
			else if(tabuleiro[line][column].getNome().equals("Dama2")){
				if(Math.abs(line - linefim) == Math.abs(column - columnfim)){
					if(linefim > line && columnfim > column){
						for(i = line+1, j = column+1; damaok && i < linefim && j < columnfim; i++, j++){
							if(i != linefim-1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca1") && !tabuleiro[i][j].getNome().equals("Dama1")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 1;
							mensagem = "2A"+line+column+"A"+(linefim-1)+(columnfim-1)+"D"+linefim+columnfim;

							if(tabuleiro[linefim-1][columnfim-1].getNome().equals("Peca1") || tabuleiro[linefim-1][columnfim-1].getNome().equals("Dama1")){
								Peca1--;
								turno = 2;
								mensagem += "C";
							}
							tabuleiro[linefim][columnfim] = new Dama("2");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim-1][columnfim-1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
					else if(linefim > line && columnfim < column){
						for(i = line+1, j = column-1; damaok && i < linefim && j > columnfim; i++, j--){
							if(i != linefim-1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca1") && !tabuleiro[i][j].getNome().equals("Dama1")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 1;
							mensagem = "2A"+line+column+"A"+(linefim-1)+(columnfim+1)+"D"+linefim+columnfim;

							if(tabuleiro[linefim-1][columnfim+1].getNome().equals("Peca1") || tabuleiro[linefim-1][columnfim+1].getNome().equals("Dama1")){
								Peca1--;
								turno = 2;
								mensagem += "C";
							}
							tabuleiro[linefim][columnfim] = new Dama("2");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim-1][columnfim+1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
					else if(linefim < line && columnfim < column){
						for(i = line-1, j = column-1; damaok && i > linefim && j > columnfim; i--, j--){
							if(i != linefim+1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca1") && !tabuleiro[i][j].getNome().equals("Dama1")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 1;
							mensagem = "2A"+line+column+"A"+(linefim+1)+(columnfim+1)+"D"+linefim+columnfim;

							if(tabuleiro[linefim+1][columnfim+1].getNome().equals("Peca1") || tabuleiro[linefim+1][columnfim+1].getNome().equals("Dama1")){
								Peca1--;
								turno = 2;
								mensagem += "C";
							}
							tabuleiro[linefim][columnfim] = new Dama("2");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim+1][columnfim+1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
					else if(linefim < line && columnfim > column){
						for(i = line-1, j = column+1; damaok && i < linefim && j > columnfim; i--, j++){
							if(i != linefim+1){
								if(!tabuleiro[i][j].getNome().equals("Vazio")){
									damaok = false;
									mensagem = "JI";
								}
							} else{
								if(!tabuleiro[i][j].getNome().equals("Vazio") && !tabuleiro[i][j].getNome().equals("Peca1") && !tabuleiro[i][j].getNome().equals("Dama1")){
									damaok = false;
									mensagem = "JI";
								}
							}
						}
						if(damaok){
							turno = 1;
							mensagem = "2A"+line+column+"A"+(linefim+1)+(columnfim-1)+"D"+linefim+columnfim;

							if(tabuleiro[linefim+1][columnfim-1].getNome().equals("Peca1") || tabuleiro[linefim+1][columnfim-1].getNome().equals("Dama1")){
								Peca1--;
								turno = 2;
								mensagem += "C";
							}
							tabuleiro[linefim][columnfim] = new Dama("2");
							tabuleiro[line][column].setNome("Vazio");
							tabuleiro[linefim+1][columnfim-1].setNome("Vazio");
							if(verificarGanhou(1)){
								mensagem += "G";
							}
						}
						
					}
				}
				else{
					mensagem = "JI";
				}
			}
			else{
				mensagem = "JI";
			}


		}
		return mensagem;
	}
	
	
	
}