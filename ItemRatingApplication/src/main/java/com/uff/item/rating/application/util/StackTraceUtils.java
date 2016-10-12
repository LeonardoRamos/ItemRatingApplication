package com.uff.item.rating.application.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceUtils {

	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();

		e.printStackTrace(new PrintWriter(stringWriter));
		
		return stringWriter.toString();
	}
	
}