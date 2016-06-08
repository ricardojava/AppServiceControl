package com.avianca.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET; 
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;


@Path("avianca/test") // o @path define a URI do recurso que nesse caso será /helloworld
public class RecursoHelloWorld {

	@GET 
	@Produces("text/plain") 
	public String exibir(){
		return "Hello World";
	}
	
	@GET
    @Produces("text/plain")
    @Path("consumidor/{username}")
    public void setIt(@PathParam("username") String userName) {
         
        System.out.println("Hi " + userName + " !");
    }
	 
} 