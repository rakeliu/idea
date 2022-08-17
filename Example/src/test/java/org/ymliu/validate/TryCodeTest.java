package org.ymliu.validate;

public class TryCodeTest
{
	public static void main(String[] args){
		String str = "-02Y";
		String regex = "([+\\-])?[1-9][0-9]+([YMDymd])";
		if (str.matches(regex)){
			String s = str.substring(0,str.length()-1);

			System.out.printf("YMD: %s\n", s);
		}else{
			System.out.println("no match");
		}
	}
}
