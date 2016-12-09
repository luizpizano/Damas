package src;

public class JogadorIniciar 
{
	public static void main(String args[])
	{	
		Client client;		
		client = new Client("ec2-54-214-129-42.us-west-2.compute.amazonaws.com");
		//client = new Client("127.0.0.1");
		client.runClient();
	}
}
