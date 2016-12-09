import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TesteDeUnidade {
	
	private static Logica testeLogica;
	private static String testes;
	
	@BeforeClass
	public static void init(){
		System.out.println("Início dos testes\n");
		testeLogica = new Logica();
		testes = new String();;
	}
	
	@AfterClass
	public static void afterClass(){
		System.out.println("Fim dos testes");
	}
	
	@Test
	public void movimento1 (){
		testes = testeLogica.processarMensagem("10134");
		assertEquals(testes, "JI");
		System.out.println("A tentativa de movimento foi 10134 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento2 (){
		testes = testeLogica.processarMensagem("25041");
		assertEquals(testes, "JI");
		System.out.println("A tentativa de movimento foi 25041 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento3 (){
		testes = testeLogica.processarMensagem("12130");
		assertEquals(testes, "1A21P30");
		System.out.println("A tentativa de movimento foi 12130 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento4 (){
		testes = testeLogica.processarMensagem("13456");
		assertEquals(testes, "JI");
		System.out.println("A tentativa de movimento foi 13456 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento5 (){
		testes = testeLogica.processarMensagem("25041");
		assertEquals(testes, "2A50P41");
		System.out.println("A tentativa de movimento foi 25041 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento6 (){
		testes = testeLogica.processarMensagem("26150");
		assertEquals(testes, "JI");
		System.out.println("A tentativa de movimento foi 26150 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento7 (){
		testes = testeLogica.processarMensagem("13041");
		assertEquals(testes, "JI");
		System.out.println("A tentativa de movimento foi 13041 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento8 (){
		testes = testeLogica.processarMensagem("12332");
		assertEquals(testes, "1A23P32");
		System.out.println("A tentativa de movimento foi 12332 e a resposta da lógica foi " + testes);
	}
	
	
	@Test
	public void movimento9 (){
		testes = testeLogica.processarMensagem("24123");
		assertEquals(testes, "2A41A32P23C");
		System.out.println("A tentativa de movimento foi 24123 e a resposta da lógica foi " + testes);
	}
	
	@Test
	public void movimento10 (){
		testes = testeLogica.processarMensagem("25241");
		assertEquals(testes, "2A52P41");
		System.out.println("A tentativa de movimento foi 25241 e a resposta da lógica foi " + testes);
	}

}
