package parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;

import ast.Emitter;
import ast.Program;
import scanner.ScanErrorException;
import scanner.Scanner;

public class ParserTester 
{
   
   public static void main(String[] args) throws FileNotFoundException, ScanErrorException
   {
      FileInputStream inStream = new FileInputStream("parserTest9.txt");
      Scanner scanner = new Scanner(inStream);
      Parser parser = new Parser(scanner);
      Program p = parser.parseProgram();
      p.compile("out.asm");
   }
}