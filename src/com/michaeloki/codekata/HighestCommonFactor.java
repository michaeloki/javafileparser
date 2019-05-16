package com.michaeloki.codekata;

public class HighestCommonFactor {
	public static void main(String arg[]) {
	    int numbers[] = {12,18,60,72,96,120,144,168};
	    highestCommonFactor(numbers);
	}
	
	static long hcf(long a,long b) {
	    while (b > 0) {
	        long temp = b;
	        b = a % b; 
	        a = temp;
	    }
	    return a;
	  }
	
    public static int highestCommonFactor(int[] numbers) {
    	int hcf = 1;
    	long result = numbers[0];
    	
	    for(int i =1; i < numbers.length; i++) {
    	  result = hcf(result,numbers[i]);
        }
        System.out.println("Highest Common Factor is "+result);
    	return hcf;
    }
}
