package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class TesteDoAvaliador {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	
	
	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		
		 this.joao = new Usuario("João");
		 this.jose = new Usuario("José");
		 this.maria = new Usuario("Maria");
	}
	
	@Test
	public void deveEntenderLanceEmOrdemCrescente() {
		
		//parte 1: cenário
		
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));
		
		// parte 2: ação
		leiloeiro.avalia(leilao);
		
		//parte 3: validacao
		
		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.00001);
		//assertEquals(250.0, leiloeiro.getMenorLance(), 0.00001);
		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
	}
	
	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new Leilao("Playstation 3");
		
		leilao.propoe(new Lance(joao, 1000.0));
		
		leiloeiro.avalia(leilao);
		
		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
		
	}
	
	@Test
	public void deveEncontrarOsTresMaioresLances() {
		
		Leilao leilao2 = new CriadorDeLeilao().para("Playstation 3 Novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(maria, 400.0)
				.controi();
		
		leiloeiro.avalia(leilao2);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertThat(maiores, hasItems(
				new Lance(maria, 400),
				new Lance(joao, 300),
				new Lance(maria, 200)
		));
			
		
	}
	
	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").controi();
		leiloeiro.avalia(leilao);
	}
	
	
}
