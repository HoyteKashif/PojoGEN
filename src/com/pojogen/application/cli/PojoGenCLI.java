package com.pojogen.application.cli;

import com.pojogen.application.pojo.component.Pojo;
import com.pojogen.application.request.HelpRequest;
import com.pojogen.application.request.factory.RequestFactory;

/**
 * Command Line Interface version of the Pojo Generator Application.
 * 
 * @author Kashif Hoyte
 */
public final class PojoGenCLI {
	public static void main(String[] args) {
		try {
			Pojo pojo = RequestFactory.createRequest(args).process();

			if (null != pojo) {
				System.out.println(pojo);
			}
		} catch (final Exception e) {
			HelpRequest.throwRequest();
		}
	}
}
