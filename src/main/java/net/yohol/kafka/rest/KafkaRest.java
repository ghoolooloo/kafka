package net.yohol.kafka.rest;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import net.yohol.kafka.producer.MyProducerQualifier;

@Path("/")
public class KafkaRest {
	@Inject
	@MyProducerQualifier
	private Producer<String, String> producer;

	@GET
	@Path("/send")
	@Produces("text/plain")
	public Response doSend() {
		producer.send(new ProducerRecord<String, String>("ttt", "key11111", "value11111"));
		return Response.ok("method doSend invoked").build();
	}

}