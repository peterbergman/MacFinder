package org.macfinder.model.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Class to represent outgoing HTTP responses.
 */
public class HTTPResponse {

	private final static Map<Integer, String> HTTP_CODES = new HashMap<Integer, String>();

	static {
		HTTP_CODES.put(200, "OK");
		HTTP_CODES.put(201, "Created");
		HTTP_CODES.put(401, "Unauthorized");
		HTTP_CODES.put(500, "Internal Server Error");
		HTTP_CODES.put(501, "Not Implemented");
	}

	private int statusCode;
	private String version;
	private Map<String, String> headers;
	private String body;

	/**
	 * Constructs a new response object with the following default settings:
	 * Status code 200
	 * HTTP version 1.0
	 * Content type application/json
	 * Charset utf-8
	 * Server mfserver
	 */
	public HTTPResponse() {
		headers = new HashMap<String, String>();
		statusCode = 200;
		version = "HTTP/1.0";
		headers.put("Date", new Date().toString());
		headers.put("Content-Type", "application/json");
		headers.put("Charset", "utf-8");
		headers.put("Server", "mfserver");
	}

	/**
	 * Sets the body of the request and the content length associated with that body.
	 *
	 * @param body	the body of the request.
	 */
	public void setBody(String body) {
		this.body = body;
		headers.put("Content-Length", this.body.getBytes().length + "");
	}

	/**
	 * Sets the header fields by splitting the header string on each row.
	 *
	 * @param headers	the HTTP headers as one string.
	 */
	public void setHeaders(String headers) {
		String[] headerArray = headers.split("\\\\r\\\\n");
		for (int i = 1; i < headerArray.length; i++) {
			this.headers.put(headerArray[0], headerArray[1]);
		}
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getBody() {
		return body;
	}

	@Override
	public String toString() {
		StringBuilder data = new StringBuilder();
		data.append(version);
		data.append(" ");
		data.append(statusCode);
		data.append(" ");
		data.append(HTTP_CODES.get(statusCode));
		data.append("\\r\\n");
		for (Map.Entry<String, String> entry : headers.entrySet()) {
			data.append(entry.getKey());
			data.append(": ");
			data.append(entry.getValue());
			data.append(" \\r\\n");
		}
		data.append("\\r\\n");
		data.append(body);
		return data.toString();
	}
}
