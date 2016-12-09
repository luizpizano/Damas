package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.*;

import javax.swing.SwingUtilities;


public class Client
{
	private int deltax = 75;
	private int deltay = 75;

	private boolean ini = false;

	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket client;
	private String Server;
	private String message;

	private View view;

	/* Controle de jogadores */
	private boolean Jogador1;
	private int player;

	//Constructor
	public Client(String serverDNS)
	{
		//this.player = player;
		Server = serverDNS;
		/*
		if(player == 1)
			this.Jogador1 = true;
		else
			this.Jogador1 = false;
		 */

		view = new View(player);
		view.AddIniciarListener(new MudaProJogo());
	}//end Constructor

	/****************** Classes Listeners do Jogo ***************/

	class MudaProJogo implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			view.InitTelaJogo();
			Inicio();
			view.AddNovoJogoListener(new NovoJogo());
			ini = true;
		}
	}


	class NovoJogo implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			view.DeletaTabuleiro();
			view.InitTabuleiro();
			Inicio();
			SendData("novoJogo");
		}
	}

	/* subclasse mouse listener do tabuleiro */
	class TabuleiroClick implements MouseListener

	{
		boolean isIn;
		int Linha;
		int Coluna;
		int click = 0;

		public void mouseClicked(MouseEvent e)

		{
			if(isIn)
			{
				//Jogador1
				if(click==1 && Jogador1)
				{
					String msg;
					msg = Integer.toString(player)+Integer.toString(Linha-1)+Integer.toString(Coluna-1)+Integer.toString(getLinha(e.getY())-1)+Integer.toString(getColuna(e.getX())-1);
					SendData(msg);
					click = 0;
					//view.NovaInformacao("Jogada realizada.");
				}
				else if(click == 0 && Jogador1)
				{
					view.NovaInformacao("Selecionada linha: "+getLinha(e.getY())+" e coluna: "+getColuna(e.getX()));
					click++;
					Linha = getLinha(e.getY());
					Coluna = getColuna(e.getX());
				}		
			}
		}

		public void mouseEntered(MouseEvent arg0) { isIn = true; }

		public void mouseExited(MouseEvent arg0) { isIn = false; }

		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}

	}

	/* Dada uma coordenada no tabuleiro, retorna a coluna */
	private int getColuna(int x)
	{
		int resp = 1;
		while(x > resp*deltax)
			resp++;
		return resp;
	}

	/* Dada uma coordernada no tabuleiro, retorna a linha */
	private int getLinha(int y)
	{
		int resp = 1;
		while(y > resp*deltay)
			resp++;
		return resp;
	}

	/* Ver jogador */
	public void TrocarVez()
	{
		Jogador1 = !Jogador1;
		view.Turno(Jogador1);
		view.NovoJogo.setEnabled(Jogador1);
	}

	/* Posicao inicial das pecas */
	public void Inicio()
	{
		//Jogador 2 (inferior)
		for(int i=1; i<=3; i++)
		{
			if(i%2!=0)
				for(int j=1; j<=7; j = j+2)
					view.AddPeca(9-i, j, 2);
			else
				for(int j=2; j<=8; j = j+2)
					view.AddPeca(9-i, j, 2);
		}

		//Jogador 1(superior)
		for(int i=1; i<=3; i++)
		{
			if(i%2==0)
				for(int j=1; j<=7; j = j+2)
					view.AddPeca(i, j, 1);
			else
				for(int j=2; j<=8; j = j+2)
					view.AddPeca(i, j, 1);
		}
	}

	/****************** Fim das Classes do Jogo ***************/


	//Conexao
	private void connectToServer() throws IOException
	{
		client = new Socket(InetAddress.getByName(Server), 12345);
	}//end method connectToServer


	//Aqui que esta a lógica da comunicacao
	public void processConnection() throws IOException

	{
		do
		{
			try
			{
				message = (String) input.readObject();

				//System.out.println(message);
				if(message.contains("A")){
					int jogador = message.charAt(0)-48;
					for(int i=1; i<message.length(); i++)
					{
						view.NovaInformacao("Jogada realizada.");
						if(message.charAt(i) == 'A'){
							view.DeletePeca(message.charAt(i+1)-47, message.charAt(i+2)-47);
						}
						if(message.charAt(i) == 'P'){
							view.AddPeca(message.charAt(i+1)-47, message.charAt(i+2)-47, jogador);
						}
						if(message.charAt(i) == 'D'){
							view.AddDama(message.charAt(i+1)-47, message.charAt(i+2)-47, jogador);
						}
						if(message.charAt(i) == 'G'){
							view.NovaInformacao("Jogador "+Integer.toString(jogador)+" venceu!!!");
						}
						if(message.charAt(i) == 'C'){
							SoundClipTest fatality = new SoundClipTest();
							TrocarVez();
						}
					}
					TrocarVez();
				}

				else if(message.contains("player"))
				{
					player = message.charAt(6) - 48;
					if(player == 1)
						this.Jogador1 = true;
					else
						this.Jogador1 = false;

					view.SetPlayer(player);
				}
				else if(message.contains("novoJogo"))
				{
					view.DeletaTabuleiro();
					view.InitTabuleiro();
					Inicio();
					if(player==1)
						Jogador1 = true;
					else
						Jogador1 = false;
					view.Turno(Jogador1);
					view.NovoJogo.setEnabled(Jogador1);
				}
				else if(message.equals("xxx"))
				{
					view.addMouseListener(new TabuleiroClick());	
					view.Turno(Jogador1);
					view.NovoJogo.setEnabled(Jogador1);
				}
				else if(message.equals("JI"))
				{
					view.NovaInformacao("Jogada invalida.");
				}

			}//end try
			catch(ClassNotFoundException classNotFoundException)
			{
				System.out.println("Classe desconhecida");
			}//end catch
		} while( !message.equals("closeAll"));
	}//end method processConnection

	//Fechar conexao
	private void closeConnection()
	{
		System.out.println("Closing connection");
		try
		{
			output.close();
			input.close();
			client.close();
		}//end try
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}
	}//end method closeConnection

	//Inicia e estabelece a comunicacao
	public void runClient()
	{
		while(!ini)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try
		{
			connectToServer();
			getStreams();
			processConnection();
		}//end try
		catch(EOFException eofException)
		{
			System.err.println("Client terminated connection");
		}//end catch
		catch(IOException ioException)
		{
			ioException.printStackTrace();
		}//end catch
		finally
		{
			closeConnection();
		}
	}//end method runClient

	//Iniciando input, output
	private void getStreams() throws IOException
	{
		output = new ObjectOutputStream(client.getOutputStream());
		output.flush();
		input = new ObjectInputStream(client.getInputStream());
	}//end method getStreams	

	//Método de envio de dados
	public void SendData(String msg)
	{
		try
		{
			output.writeObject(msg);
			output.flush();
		}//end try
		catch(IOException ioException)
		{
			System.out.println("Erro na escrita");
		}//end catch
	}//end method SendData

}//end class Client
