package example.customer.service.util;

public class StringUtils {

	public static boolean isStringEmpty(String str){
		return ((str == null) || str.equalsIgnoreCase(""));
	}
}
