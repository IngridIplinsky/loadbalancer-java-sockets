package com.olivelabs.loadbalancer.server;


import java.io.IOException;
import java.net.SocketTimeoutException;

import org.xlightweb.IHttpExchange;
import org.xlightweb.IHttpResponse;
import org.xlightweb.IHttpResponseHandler;
import org.xlightweb.IHttpSocketTimeoutHandler;
import org.xlightweb.InvokeOn;
import org.xsocket.Execution;



public class HttpResponseHandler implements IHttpResponseHandler, IHttpSocketTimeoutHandler{

	   private IHttpExchange exchange = null;

	   public HttpResponseHandler(IHttpExchange exchange) {
	      this.exchange = exchange;
	   }

	   @Execution(Execution.NONTHREADED)
	   @InvokeOn(InvokeOn.HEADER_RECEIVED)
	   public void onResponse(IHttpResponse resp) throws IOException {

	      // handle proxy issues (hop-by-hop headers, ...)
	      // ...

	      // return the response 
	      exchange.send(resp);
	   }

	   @Execution(Execution.NONTHREADED)
	   public void onException(IOException ioe) {
	      exchange.sendError(500, ioe.toString());
	   }

	   @Execution(Execution.NONTHREADED)
	   public void onException(SocketTimeoutException stoe) {
	      exchange.sendError(504, stoe.toString());
	   }
}