package com.restaurant.controller;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;
//import java.util.*;
import java.util.stream.Stream;
public class test {
    public static void main(String[] args) {
        
    	List<Integer> list=Arrays.asList(1,2,3,4,5);
    	
    	int s=list.stream().mapToInt(Integer::intValue).sum();
    	System.out.println(s);
    	
    	long count = countStringStartWith_A();
    	System.out.println(count);
    	
    	Double countAverage = countAverageOfAllEvenNumbers();
    	System.out.println(countAverage);
    	
    	int findMaximum = findMaximumValueInList();
    	System.out.println(findMaximum);
    	
    	List<String> UpperCaseList = convertListOfStringsToUpperCase();
    	System.out.println(UpperCaseList);
    	
    	List<String> filteredList = filterListOfStringWhoseLengthGreaterThenFive();
    	System.out.println(filteredList);
    	
    	Integer productOfIntegers = productOfIntegers();
    	System.out.println(productOfIntegers);
    	
    	List<String> sortListOfStrings = sortListOfStringg();
    	System.out.println(sortListOfStrings);
    	
//    	List<Integer> list1=Arrays.asList(2,2,2,2,2);
//    	double d1=0;
//    	for(int i:list1) {
//    		
//    		if(i%2==0)
//    			d1+=i;
//    	}
//		double avg=d1/list1.size();
//		System.out.println(avg);
    	
//    	int sum=0;
    	
//    	for(int i=0;i<list.size();i++) {
//    		sum=sum+list.get(i);
//    	System.out.println(sum);}
    	
//    	for(int i:list) {
//    		sum=sum+i;
//    	System.out.println(sum);}
    	
    }
    
    public static long countStringStartWith_A() {
    	
    	List<String> strings=Arrays.asList("keshav","ankit","nikhil","aneesh","saurabh");
    	
    	long count=strings.stream().filter(s->s.startsWith("a")).count();
    	
    	return count;
    }
    
    public static Double countAverageOfAllEvenNumbers() {
    	
    	List<Integer> list=Arrays.asList(2,2,2,2,2);
    	
    	Double average = list.stream().filter(n->n%2==0).mapToDouble(n->n).average().orElse(0.0);
    	 
    	 return average;
    }
    
    public static int findMaximumValueInList() {
    	
    	List<Integer> list=Arrays.asList(1,2,4,5,231,5,2,5,21);
    	
//    	int max = list.stream().max((o1, o2) -> o1-o2).orElse(0);
//    	int max=list.stream().max(Integer::compare).orElse(0);
    	int max=list.stream().max(Comparator.naturalOrder()).orElse(0);
    	
    	return max;
    }
    
    public static List<String> convertListOfStringsToUpperCase() {
    	
    	List<String> list=Arrays.asList("keshav","sahil","manu");
    	
    	List<String> collect = list.stream().map(String::toUpperCase).collect(Collectors.toList());
    	
    	return collect;
    	
    }
    
    public static List<String> filterListOfStringWhoseLengthGreaterThenFive() {
    	
    	List<String> list=Arrays.asList("keshav","suman","nisha","jyoti","abhi","fin");
    	
    	List<String> collect = list.stream().filter(s->s.length()>5).collect(Collectors.toList());
    	
    	return collect;
    }
    
    public static Integer productOfIntegers(){
    	List<Integer> list=Arrays.asList(1,2,3);
    	
    	int reduce = list.stream().reduce(1,(a,b)->a*b);
    	
    	return reduce;
    }
    
    public static List<String> sortListOfStringg(){
    	
    	List<String> list=Arrays.asList("keshav","suman","nisha","jyoti","abhi","fin");
    	
    	List<String> collect = list.stream().sorted().collect(Collectors.toList());
    	
    	return collect;
    }
    
    
}