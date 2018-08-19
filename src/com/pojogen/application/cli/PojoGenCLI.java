package com.pojogen.application.cli;

import java.util.Collections;

import com.pojogen.application.request.IRequest.ArgumentPartEnum;
import com.pojogen.application.request.factory.RequestFactory;

public class PojoGenCLI {
	public static void main(String[] args) {
		try {
			RequestFactory.createRequest(args).process();
		} catch (final Exception e) {
			try {
				RequestFactory
						.createRequest(Collections.singletonMap(ArgumentPartEnum.HELP_PART, Collections.emptyList()))
						.process();
			} catch (Exception _exception) {
				System.out.println("Unable to process the Pojo Request");
				_exception.printStackTrace();
			}
		}
	}
}
