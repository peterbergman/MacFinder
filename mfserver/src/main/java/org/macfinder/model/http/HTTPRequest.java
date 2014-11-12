package org.macfinder.model.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to represent incoming HTTP requests.
 */
public class HTTPRequest {
	private List<String> headers;
	private String body;

	public void setHeaders(String[] headers) {
		this.headers = new ArrayList<String>(Arrays.asList(headers));
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getBody() {
		return body;
	}

	public String getMethod() {
		return headers.get(0);
	}

	public String getPath() {
		return headers.get(1);
	}

	public String getVersion() {
		return headers.get(2);
	}

	public String getHeaderValue(String headerName) {
		String value = null;
		for (int i = 0; i < headers.size(); i++) {
			if (headers.get(i).equals(headerName)) {
				value = headers.get(i+1);
				break;
			}
		}
		return value;
	}
}
