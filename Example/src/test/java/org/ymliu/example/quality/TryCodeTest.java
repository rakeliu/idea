package org.ymliu.example.quality;

public class TryCodeTest
{
	public static void main(String[] args){
		String str = "-Y";
		String regex = "(\\+|-)?[0-9]+(Y|M|D|y|m|d)";
		if (str.matches(regex)){
			String s = str.substring(0,str.length()-1);

			System.out.printf("YMD: %s\n", s);
		}else{
			System.out.println("no match");
		}
	}
}
