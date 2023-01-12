package com.restaurant.configuration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Testing {
//do this by the help of for each loop

	public static void main(String[] args) {

//	List<Integer> arr=Collections.synchronizedList(new ArrayList<>());
		List<Integer> arr = new ArrayList<>();
		int count = 0;
		arr.add(1);
		arr.add(2);
		arr.add(3);
		arr.add(4);
		arr.add(3);
		arr.add(3);
		arr.add(3);
		arr.add(3);
		arr.add(3);

		arr.remove((Object) 2);
//		for(int i:arr) {
//			if(i==3)
//				arr.remove(i);
//		}
		Iterator<Integer> iterator = arr.iterator();
		while (iterator.hasNext()) {
			int a = iterator.next();
			if (a == 3)
				iterator.remove();
		}
//	synchronized (arr) {
//	for (Integer integer : arr) {
//		if(integer==3)
//			arr.remove(integer);
//	}}
//	arr.stream().forEach(o->{
//		if(o==3)
//		count++;//ye smjh mai nahi aaya dhang se ki asa kyu ho raha hai
//	});
//	for(Integer integer:arr) {
//		if(integer==3)
//			arr.remove(integer);
//		System.out.println(arr);
//	}
//		int a=arr.get(2);
//		if(arr.get(0)==1)
		System.out.println(arr);
	}
}
