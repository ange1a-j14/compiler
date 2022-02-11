package othello;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ScannerTester {
   
   public static void main(String[] args) throws FileNotFoundException, IOException
    {
    	FileReader inStream = new FileReader("OthelloPlay.txt");
        Scanner lex = new Scanner(inStream);
        while (! lex.yyatEOF()) 
        {
            System.out.println(lex.nextToken());
        }
    }
}
