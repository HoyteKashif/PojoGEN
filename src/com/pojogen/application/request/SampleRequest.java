package com.easytech.request.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.easytech.pojo.component.generator.Pojo;
import com.easytech.staticvalues.PojoStaticValues;

/**
 * Implementation of a Sample Request. 
 * <br>
 * Description: 
 * <br>
 * Gives the user an opportunity to see a sample output.
 * 
 * @author WizardOfOz
 *
 */
public class SampleRequest implements IRequest{
	
	/**
	 * Prints the sample class model to the command line.
	 */
	@Override
	public void process() throws Exception {
		final List<String> sampleMembers = new ArrayList<>();
		sampleMembers.add("fizz_string");
		sampleMembers.add("buzz_string");
		sampleMembers.add("fizz_buzz_bigdecimal");
		
		System.out.println("ex:\n" + PojoStaticValues.TAB + "PojoGen -c FizzBuzz -p fizz_string buzz_string fizz_buzz_bigdecimal\n");
		System.out.println(Pojo.PojoBuilder.getPojo("FizzBuzz", sampleMembers));
	}

	@Override
	public IRequest setArgumentMap(Map<ArgumentPartEnum, List<String>> p_argumentMap) throws Exception {
		return this;
	}
}
