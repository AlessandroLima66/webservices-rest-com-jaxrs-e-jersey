package br.com.alura.loja.resource;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.thoughtworks.xstream.XStream;

import br.com.alura.loja.dao.CarrinhoDAO;
import br.com.alura.loja.modelo.Carrinho;
import br.com.alura.loja.modelo.Produto;

@Path("carrinhos")
public class CarrinhoResource {
	
	@Path("{id}")
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public String busca(@PathParam("id") long id) {
		CarrinhoDAO carrinhoDao = new CarrinhoDAO();
		return carrinhoDao.busca(id).toJson();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response adiciona(String conteudo) {
		Carrinho carrinho = (Carrinho) new XStream().fromXML(conteudo);
		new CarrinhoDAO().adiciona(carrinho);
		
		URI uri = URI.create("/carrinhos/" + carrinho.getId());
		return Response.created(uri).build();
	}
	
	@Path("{idCarrinho}/produtos/{idProduto}")
	@DELETE
	public Response remove(@PathParam("idCarrinho") long idCarrinho, 
			@PathParam("idProduto") long idProduto ) {
		Carrinho carrinho = new CarrinhoDAO().busca(idCarrinho);
		carrinho.remove(idProduto);
		
		return Response.ok().build();
	}
	
	@Path("{idCarrinho}/produtos/{idProduto}")
	@PUT
	public Response altera(@PathParam("idCarrinho") long idCarrinho, 
			@PathParam("idProduto") long idProduto, String conteudo) {
		Carrinho carrinho = new CarrinhoDAO().busca(idCarrinho);
		Produto produto = (Produto) new XStream().fromXML(conteudo);
		carrinho.trocaQuantidade(produto);
		return Response.ok().build();
	}
	
}
