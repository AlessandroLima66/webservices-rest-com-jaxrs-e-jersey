package br.com.alura.loja;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.modelo.Projeto;
import junit.framework.Assert;

public class ProjetoTest {
private HttpServer server;
	
	@Before
	public void startaServidor() {
	    this.server = Servidor.startServer();
	}
	
	@After
    public void mataServidor() {
		Servidor.stopServer(server);
    }
	
	@Test
	public void testaQueBuscarUmProjetoTrazOProjetoEsperado() {
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080");
		Projeto projeto = target.path("/projetos").request().get(Projeto.class);
		
		Assert.assertEquals("Minha loja", projeto.getNome());
	}

}
