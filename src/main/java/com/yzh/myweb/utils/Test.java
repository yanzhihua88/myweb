package com.yzh.myweb.utils;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {

		String str = "33333zzhhhhaaa44455pppin";
		List<String> list = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		List<String> list3 = new ArrayList<>();
		int length = str.length();
		for (int i = 0; i < length; i++) {
			list.add(str.charAt(i) + "");
			list2.add(str.charAt(i) + "");
			list3.add(str.charAt(i) + "");
		}

		String start = "";
		label1: for (int i = 0; i < list.size(); i++) {
			start = list.get(i);
			Object o;
			try {
				o = Integer.parseInt(start);
			} catch (Exception e) {
				continue;
			}
			if (o instanceof Integer) {
				String s1 = "";
				String s2 = "";
				boolean flag = false;
				for (int j = i + 1; j < list2.size(); j++) {
					if (j == i + 1) {
						s1 = list2.get(j);
					} else {
						s2 = list2.get(j);
					}

					if (s2.equals(s1)) {
						flag = true;
						s1 = s2;
					}
					if (flag && !s2.equals(s1)) {
						list3.remove(i + 1);
						list3.add(i + 1, "#");
						i = j - 2;
						continue label1;
					}

				}
			}
		}
		StringBuffer sb = new StringBuffer();
		for (String s : list3) {
			if (!"#".equals(s)) {
				sb.append(s);
			}
		}

		System.out.println(sb.toString());
	}
}
