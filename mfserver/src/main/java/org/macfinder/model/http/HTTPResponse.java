package org.macfinder.model.http;

import java.util.Date;

/**
 * Class to represent outgoing HTTP responses.
 */
public class HTTPResponse {
	private String headers;
	private String body;

	public HTTPResponse(String body) {
		this.body = body;
		headers = "Status: 200 OK\n" +
				"Version: HTTP/1.1\n" +
				"Content-Length: "+ body.getBytes().length + "\n" +
				"Date: " + new Date().toString() + "\n" +
				"Content-Type: application/json;charset=utf-8\n" +
				"Server: mfserver";
	}

	public String getHeaders() {
		return headers;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString() {
		return headers + "\n\n" + body;
	}
}
