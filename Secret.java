package HannonHillTest;

import java.util.*;

/**
 * Stand-alone program to find prime numbers less than the user-entered number.
 * 
 * Secret is an additive function secret(x+y) = secret(x) + secret(y) for all 
 * combinations of prime numbers less than N where N is also a given.
 *
 * @author Jim Cobb
 *
 */
public class Secret {

    /**
     * getPrimeNumbers()
     * 
     * Gets all of the prime numbers less than the passed-in value.
     *
     * @param maxValue -- the value the user has entered
     * @return array of Integers that are the prime numbers less than the maxValue
     */
    public static Integer[] getPrimeNumbers( int maxValue ) {
    	
        boolean[] isPrimeNumber = new boolean[maxValue + 1];
        ArrayList<Integer> primeNumbers = new ArrayList<Integer>();
		int primeCount = 0;
		
        // set all flags to true at first
        for (int i = 2; i < maxValue; i++) {
        	isPrimeNumber[i] = true;
        }

        // mark non-primes <= N, using Sieve of Eratosthenes (look it up!)
        for( int i = 2; (i*i) < maxValue; i++ ) {
        	
            // if i is prime, then mark multiples of i as nonprime
            if( isPrimeNumber[i] ) {
            	
                for( int j = i; (i*j) < maxValue; j++ ) {
                	isPrimeNumber[i*j] = false;
                }
            }
        }

    	// load all prime numbers into primeNumbers arraylist
        for (int i = 2; i < maxValue; i++) {
            if( isPrimeNumber[i] ){
				primeNumbers.add(i);
				primeCount ++;
			}
        }
        
        return primeNumbers.toArray( new Integer[primeCount] );
    }
	
    /**
     * getSecret()
     * returns an int based on the passed-in int.
     *
     * @param numIn -- an integer
     * @return a value based on the number passed into the method
     */
    public static int getSecret( int numIn ) {
        return( numIn );
    }
    
    /**
     * isSecretValid()
     * Checks if the function  secret(x+y) = secret(x) + secret(y) is true
     * for all prime numbers in the array of Integers passed-in.  
     * If any of the prime numbers in the array do not pass the test, 
     * the method will return false.
     * 
     * @param primeNumbers -- an array of Integers containing prime numbers
     * @return true if all prime numbers in array pass the test, else return false. 
     */
    private static boolean isSecretValid( Integer[] primeNumbers ) {
    	
    	int sumAdd;
    	int xyAdd;
    	int xSecret;
    	int ySecret;
    	int xplusySecret;
        for( Integer x : primeNumbers ) {
            for( Integer y : primeNumbers ) {
                sumAdd = (x + y);
                xyAdd = getSecret( sumAdd );

                xSecret = getSecret( x );
                ySecret = getSecret( y );
                xplusySecret = ( xSecret + ySecret );

                if( xyAdd != xplusySecret ) {
                    return false;
                }
            }
            
            // successList.add(x);
        }
        return true;
    }
	/**
	 * main class
	 * @param args - none
	 * Enter an integer greater than 1 to find the prime numbers less than that number, 
	 * or enter -1 to end the program.
	 */
	public static void main( String[] args ) {
		
		String userInput = new String( "0" );
		Scanner inputReader = new Scanner( System.in );
		int userInt = 0;
		Integer[] primeNumbers;
		
		while( ! ( userInput.equals( "-1" ))) {
			
			System.out.print( "Enter an integer greater than 1, or enter -1 to exit: " );
			userInput = inputReader.next();			
			try {
				// try to convert the input to an integer, throw an error if it fails
				// or it's a value other than what we want
				userInt = Integer.parseInt( userInput );
				if( userInt == 0 || userInt == 1 || userInt < -1 ) {
					throw( new InputMismatchException() );
				}
				if( userInput.equals( "-1" )) {
					continue;
				}
				
				// get all prime numbers up to the user input number
				primeNumbers = getPrimeNumbers( userInt );
				
				//Print the users input and all of the primes from 1 to n where n is the users input
				System.out.println("Prime numbers below " + userInt + ": " + Arrays.toString( primeNumbers ));

				// test the prime numbers to see if they meet the secret requirements 
				if( isSecretValid( primeNumbers )) {
					System.out.println( "The function getSecret() is true for all the prime numbers less than " + userInt + ".\n" );
				} 
				else {
					System.out.println( "The function getSecret() is NOT true for all the prime numbers less than " + userInt + ".\n" );
				}
			}
			// there's an error if the user enters a value that is not an int > 1 or equal to -1
			catch( NumberFormatException | InputMismatchException e ) {
				System.out.println( userInput + " is not a valid value for this program.");
			} 
			
		}
		
		// clean up and exit
		inputReader.close();
		System.out.println( "Exiting the program." );
		System.exit( 0 );
		
	}
	
} // end class Secret


