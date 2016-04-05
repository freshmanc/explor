package utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class StringFilterTest {
	
	// ���������ַ�
	public String StringFilter(String str){
		// ֻ������ĸ������
		// String regEx = "[^a-zA-Z0-9]";
		// ��������������ַ�
		String regEx = "[`~!@#$%^&*()+=|{}':;',//[//].<>/?~��@#��%����&*��������+|{}������������������������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}


	public static void main(String[] args){
		String str = "(����)*?%%*(*.�й�}34{45[]12.fd'*&999���������ĵ��ַ�������{}��������������������";
		System.out.println(str);
		System.out.println(new StringFilterTest().StringFilter(str));
	}
}