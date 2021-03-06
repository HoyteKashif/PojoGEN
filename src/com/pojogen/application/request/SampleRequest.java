package com.pojogen.application.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pojogen.application.pojo.component.Pojo;
import com.pojogen.application.shared.util.StaticValues;

/**
 * Implementation of a Sample Request. <br>
 * Description: <br>
 * Gives the user an opportunity to see a sample output.
 * 
 * @author Kashif Hoyte
 *
 */
public class SampleRequest implements IRequest {

	/**
	 * Prints the sample class model to the command line.
	 */
	@Override
	public Pojo process() throws Exception {
		final List<String> sampleMembers = new ArrayList<>();
		sampleMembers.add("fizz_string");
		sampleMembers.add("buzz_string");
		sampleMembers.add("fizz_buzz_bigdecimal");

		System.out.println("ex:\n" + StaticValues.TAB
				+ String.format("PojoGen %s FizzBuzz %s fizz_string buzz_string fizz_buzz_bigdecimal\n",
						CommandLineArgument.CLASS.getValue(), CommandLineArgument.MEMBER.getValue()));

		return new Pojo.Builder("FizzBuzz", sampleMembers).build();
	}

	@Override
	public IRequest setArgumentMap(Map<CommandLineArgument, List<String>> p_argumentMap) throws Exception {
		return this;
	}
}
