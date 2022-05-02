package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import scanner.ScanErrorException;
import scanner.Scanner;

public class ParserTester 
{
   
   public static void main(String[] args) throws FileNotFoundException, ScanErrorException
   {
      FileInputStream inStream = new FileInputStream("parserTest71.txt");
      Scanner scanner = new Scanner(inStream);
      Parser parser = new Parser(scanner);
      while (scanner.hasNext())
      {
         parser.parseProgram();
      }
   }
}