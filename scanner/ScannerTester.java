package scanner;

import java.io.*;

/**
 * @author Angela Jia
 * @version February 1, 2022
 */
public class ScannerTester 
{
	/**
	 * Runs a test on the Scanner.
	 *
	 * @param args command-line arguments
	 * @throws FileNotFoundException if entered file cannot be found
     * @throws ScanErrorException if there is an issue when encountering a character while scanning 
	 */
    public static void main(String[] args) throws FileNotFoundException, ScanErrorException 
    {
    	FileInputStream inStream = new FileInputStream("ScannerTest.txt");
        Scanner lex = new Scanner(inStream);
        while (lex.hasNext()) 
        {
            System.out.println(lex.nextToken());
        }
    }
}