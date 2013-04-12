package com.mush.simplewebclient.services;

import java.util.HashMap;
import java.util.Map;

public class Test {

	private Map<String, String> headers;
	private Map<String, String> params;
	private String URL;

	public Test() {
		this.headers = new HashMap<String, String>();
		this.params = new HashMap<String, String>();
	}

	public void addParams(String k, String v) {
		this.params.put(k, v);
	}

	public void addHeads(String k, String v) {
		this.headers.put(k, v);
	}

	public void setURL(String url) {
		this.URL = url;
	}

	public void doPost() {
		try {
			HttpRequestUtil.sendPostRequest(URL, params, headers);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	// -p head k v
	// -p param k v
	// -s url url
	// -s encoding encoding
	// -d post
	// -d get
	public void massin(String args[]) {
		String script = args[0];
		String opreate = args[1];

		for (int i = 0; i < args.length; ++i) {

			if (args[i].startsWith("-")) {

				switch (args[i].charAt(1)) {

				case 'h':

					System.out.println(args[++i]);

					break;

				case 'i':

					System.out.println(args[++i]);

					break;

				case 'v':

					System.out.println(args[++i]);

					break;

				default:


					//usage();
				}

			}

		}

		if (script.equals("-put")) {

		} else if (script.equals("-set")) {

		} else if (script.equals("-do")) {

		}

	}
}
