import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server extends Thread {

	//atributos para estabelecimento de rede
	private ServerSocket server; // server socket
	private Socket connection; // connection to client
	private int counter = -1; // counter of number of connections
	private MultiThreadClient[] jogador = new MultiThreadClient[10];

	//atributos referente à lógica do jogo
	private Logica[] damaLogica = new Logica[5];

	public void run() 
	{
		try // set up server to receive connections; process connections
		{
			while ( true ) 
			{
				System.out.println("aguardando conexao");
				connection = server.accept();
				System.out.println("conectou");
				
				counter++;
				counter=counter%10;
				
				jogador[counter] = new MultiThreadClient(connection,counter/2); 

				jogador[counter].getOutput().writeObject("player"+Integer.toString( (counter+1)%2+1 ) );
				jogador[counter].getOutput().flush();

				if(counter%2==1)
				{
					jogador[counter-1].getOutput().writeObject("xxx");
					jogador[counter-1].getOutput().flush();
					jogador[counter].getOutput().writeObject("xxx");
					jogador[counter].getOutput().flush();
					this.novoJogo();
				}
				jogador[counter].start();
			} // end while   
		} // end try
		catch ( IOException ioException ) 
		{
			ioException.printStackTrace();
			System.out.println("LOLOLOLOLO");
			this.run();
		} // end catch
	} // end method runServer

	public void initialize(){
		System.out.println("Servidor Iniciado");
		try {
			server = new ServerSocket( 12345, 10 );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // create ServerSocket
	}
	
	public void novoJogo(){
		damaLogica[ counter/2 ] = new Logica();
	}

	//classe privada de MultiThreadClient que estabeleca a conexão com cada um dos clientes

	private class MultiThreadClient extends Thread {

		private Socket clientSocket;
		private ObjectInputStream input;
		private ObjectOutputStream output;
		private int logicaIndice; 

		public MultiThreadClient ( Socket newClientSocket, int logicaIndice ){
			this.logicaIndice = logicaIndice;
			clientSocket = newClientSocket;
			try {
				input = new ObjectInputStream ( clientSocket.getInputStream() );
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				output = new ObjectOutputStream (clientSocket.getOutputStream() );
				output.flush();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void run(){
			String jogada = new String("");
			String resposta = new String("");

			while (true) // processar jogadas enviadas pelos clientes
			{			         
				try {
					try {
						try{
						jogada = ( String ) input.readObject();
						}
						catch(SocketException e){
							break;
						}
						if(!jogada.equals("novoJogo")){
							resposta = damaLogica[logicaIndice].processarMensagem(jogada);
						}
						for(int i=logicaIndice*2; i<logicaIndice*2+2; i++)
							if(jogador[i] != null)
							{
								jogador[i].getOutput().writeObject(resposta);
								jogador[i].getOutput().flush();
							}
						if (jogada.equals("novoJogo")){
							novoJogo();
							for(int i=logicaIndice*2; i<logicaIndice*2+2; i++)
								if(jogador[i] != null)
								{
									jogador[i].getOutput().writeObject("novoJogo");
									jogador[i].getOutput().flush();
								}
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // read new message 
			} 
		}//fim do método run

		public ObjectOutputStream getOutput(){
			return output;
		}
	}//fim da classe ClientMultiThread




}