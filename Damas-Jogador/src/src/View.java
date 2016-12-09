package src;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class View extends JFrame
{
	/* Imagens */
	private Image bg_preto;
	private Image bg_branco;
	private Image p1;
	private Image p2;
	private Image d1;
	private Image d2;
	private Image e1;
	private Image e2;
	private Image titulo_img;
	private Image ita;
	private Image regras;
	private Image capa;
	private Image creditos;
	
	/* Tela Inicial */
	private JButton Iniciar;
	private JLabel imgInicial;
	private SoundTela musica;
	private JButton Creditos;
	private JButton Regras;
	private JPanel botoes;
	private JButton Voltar;
	
	/* Tabuleiro */
	private JPanel tabuleiro;
	
	/* Local para informações inferior*/
	private JLabel informacoes;
	private int player;
	
	/* Local para Outras informações, conectar, etc */
	private JPanel BarraLateral;
	public JButton NovoJogo;
	private JLabel Vez;
	
	/* Construtora */
	public View(int player)
	{
		super("KOMBAT CHECKERS");
		
		this.player = player;
		
		botoes = new JPanel();
		botoes.setLayout(new FlowLayout());
		
		tabuleiro = new JPanel();
		Iniciar = new JButton("Iniciar");
		Creditos = new JButton("Créditos");
		Regras = new JButton("Regras");
		Voltar = new JButton("Voltar");
		
		/* Carregando as imagens */
		try {
			Initsrc();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		InitTelaInicial();
		
		/* Configurações default */
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(800, 650);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	/* Inicializa a tela inicial */
	private void InitTelaInicial()
	{
		this.setLayout(new BorderLayout());
		imgInicial = new JLabel(new ImageIcon(capa));
		
		musica = new SoundTela();
		musica.Toca();
		
		botoes.add(Iniciar);
		botoes.add(Regras);
		botoes.add(Creditos);
		botoes.add(Voltar);
		
		Voltar.setVisible(false);
		
		Regras.addActionListener(new MostrarRegras());
		Creditos.addActionListener(new MostrarCreditos());
		Voltar.addActionListener(new Volta());
		
		this.add(imgInicial, BorderLayout.WEST);
		this.add(botoes, BorderLayout.SOUTH);
		refresh();
	}
	
	class Volta implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Voltar.setVisible(false);
			imgInicial.setIcon(new ImageIcon(capa));
			refresh();	
		}
	}
	
	class MostrarRegras implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Voltar.setVisible(true);
			imgInicial.setIcon(new ImageIcon(regras));
			refresh();	
		}
	}
	
	class MostrarCreditos implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Voltar.setVisible(true);
			imgInicial.setIcon(new ImageIcon(creditos));
			refresh();	
		}
	}
	
	/* Adicionar Listener no botao Iniciar */
	public void AddIniciarListener(ActionListener listener)
	{
		Iniciar.addActionListener(listener);
	}
	
	/* Iniciar Tela do Jogo */
	public void InitTelaJogo()
	{
		this.remove(botoes);
		this.remove(Iniciar);
		this.remove(Creditos);
		this.remove(Regras);
		this.remove(imgInicial);
		musica.Para();
		
		/* Carregando tabuleiro */
		InitTabuleiro();
		
		/* Carregando a barra de informações */
		InitInformacoes();
		
		/* Carregando a barra lateral */
		InitBarraLateral();
		
		/* Configurando o JFrame */
		this.setLayout(new BorderLayout());
		
		/* Adicionando no JFrame os JComponents */
		this.add(tabuleiro, BorderLayout.WEST);
		this.add(informacoes, BorderLayout.SOUTH);
		this.add(BarraLateral, BorderLayout.CENTER);
		
		refresh();
	}

	
	/* Só pra cada um saber quem é cada um */
	public void SetPlayer(int x)
	{
		informacoes.setText("                                      "
				+ "BEM VINDO AO JOGO DE DAMAS JOGADOR "+Integer.toString(x));
		Vez.setText("                                ");
		this.setTitle("KOMBAT CHECKERS - JOGADOR " + Integer.toString(x));
		refresh();
	}
	
	public void Turno(boolean x)
	{
		if(x)
			Vez.setText("           Sua Vez           ");
		else
			Vez.setText("Vez do adversário");
		refresh();
	}
	
	/* Bota um actionListener no NoboJogo */
	public void AddNovoJogoListener(ActionListener listener)
	{
		NovoJogo.addActionListener(listener);
	}
	
	/* Carrega a barra lateral */
	private void InitBarraLateral()
	{
		BarraLateral = new JPanel();
		BarraLateral.setLayout(new FlowLayout());
		
		Vez = new JLabel("");
		
		NovoJogo = new JButton("   NovoJogo   ");
		NovoJogo.setSize(50, 50);
		
		JLabel italogo = new JLabel(new ImageIcon(ita));
		JLabel titulo = new JLabel(new ImageIcon(titulo_img));
		
		JLabel play1 = new JLabel(new ImageIcon(e1));
		play1.setText("Jogador 1");
		
		JLabel play2 = new JLabel(new ImageIcon(e2));
		play2.setText("Jogador 2");
		
		BarraLateral.add(italogo);
		BarraLateral.add(titulo);
		BarraLateral.add(NovoJogo);
		BarraLateral.add(Vez);
		BarraLateral.add(play1);
		BarraLateral.add(play2);
	}
	
	/* Atualiza a barra de informações */
	public void NovaInformacao(String msg)
	{
		informacoes.setText(msg);
		refresh();
	}
	
	/* Inicializa a barra de informações */
	private void InitInformacoes()
	{
		informacoes = new JLabel("                                      "
				+ "BEM VINDO AO JOGO DE DAMAS JOGADOR "+Integer.toString(player));
	}
	
	/* Metodo auxiliar do tabuleiro */
	public void DeletaTabuleiro()
	{
		tabuleiro.removeAll();
	}
	
	/* Inicializa o tabuleiro */
	public void InitTabuleiro()
	{
		tabuleiro.setLayout(new GridLayout(8, 8));
		tabuleiro.setSize(50, 50);
		
		for(int i=1; i<=8; i++)
			for(int j=1; j<=8; j++)
			{
				JLabel aux;
				if(i%2==0)
				{
					if(j%2==0)
						aux = new JLabel(new ImageIcon(bg_branco));
					else
						aux = new JLabel(new ImageIcon(bg_preto));
				}
				else
				{
					if(j%2==0)
						aux = new JLabel(new ImageIcon(bg_preto));
					else
						aux = new JLabel(new ImageIcon(bg_branco));
				}
				tabuleiro.add(aux);
			}
		refresh();
	}

	/* Reiniciar tabuleiro - NAO TESTADO*/
	private void ReiniciarTabuleiro()
	{
		for(int i=1; i<=8; i++)
			for(int j=1; j<=8; j++)
			{
				if(i%2==0)
				{
					if(j%2!=0)
						DeletePeca(i, j);
				}
				else
				{
					if(j%2==0)
						DeletePeca(i, j);
				}
			}
	}
	
	/* Inicializa Imagens */
	private void Initsrc() throws IOException
	{
		bg_preto = ImageIO.read(getClass().getResource("/images/bg_preto.png"));
		bg_branco = ImageIO.read(getClass().getResource("/images/bg_branco.png"));
		p1 = ImageIO.read(getClass().getResource("/images/p1.png"));
		p2 = ImageIO.read(getClass().getResource("/images/p2.png"));
		titulo_img = ImageIO.read(getClass().getResource("/images/titulo.png"));
		ita = ImageIO.read(getClass().getResource("/images/ita.png"));
		regras = ImageIO.read(getClass().getResource("/images/regras.png"));
		d1 = ImageIO.read(getClass().getResource("/images/d1.png"));
		d2 = ImageIO.read(getClass().getResource("/images/d2.png"));
		e1 = ImageIO.read(getClass().getResource("/images/e1.png"));
		e2 = ImageIO.read(getClass().getResource("/images/e2.png"));
		capa = ImageIO.read(getClass().getResource("/images/Capa.png"));
		creditos = ImageIO.read(getClass().getResource("/images/creditos.png"));
	}

	/* Insere peça em determinada posição */
	public void AddPeca(int row, int column, int Jogador)
	{
		JLabel aux;
		
		if(Jogador == 1)
			aux = new JLabel(new ImageIcon(p1));
		else
			aux = new JLabel(new ImageIcon(p2));
		
		tabuleiro.remove(8*(row-1)+column-1);
		tabuleiro.add(aux, 8*(row-1)+column-1);
		
		refresh();
	}
	
	/* Insere Dama em determinada posição */
	public void AddDama(int row, int column, int Jogador)
	{
		JLabel aux;
		
		if(Jogador == 1)
			aux = new JLabel(new ImageIcon(d1));
		else
			aux = new JLabel(new ImageIcon(d2));
		
		tabuleiro.remove(8*(row-1)+column-1);
		tabuleiro.add(aux, 8*(row-1)+column-1);
		
		refresh();
	}
	
	/* Deleta peca de uma determinada posicao */
	public void DeletePeca(int row, int column)
	{
		JLabel aux = new JLabel(new ImageIcon(bg_preto));
		tabuleiro.remove(8*(row-1)+column-1);
		tabuleiro.add(aux, 8*(row-1)+column-1);
		refresh();
	}
	
	/* Refresh */
	public void refresh()
	{
		this.repaint();
		this.revalidate();
	}

	/* Deletar tabuleiro --> Nunca usada praticamente*/
	public void DeleteTabuleiro()
	{
		this.remove(tabuleiro);
		InitTabuleiro();
		this.add(tabuleiro, BorderLayout.WEST);
	}
	
	/* Tabuleiro Listener */
	public void AddTabuleiroListener(MouseListener listener)
	{
		tabuleiro.addMouseListener(listener);
	}
}


