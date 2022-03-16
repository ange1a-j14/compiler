package parser;

import java.util.HashMap;
import java.util.Map;

import scanner.ScanErrorException;
import scanner.Scanner;

/**
 * Parser is a simple parser for Compilers and Interpreters lab exercise 2.
 * It is responsible for parsing the stream of tokens given by the Scanner. 
 * 
 * @author Angela Jia
 * @version March 16, 2022
 */
public class Parser 
{
   
   private Scanner scanner; 
   private String currToken;
   private Map<String, Integer> map;

   /**
    * Constructs a new Parser with a given scanner. Stores the scanner's first token 
    * as the parser's current token. 
    *
    * @param scanner the scanner associated with this Parser instance
    * @throws ScanErrorException
    */
   public Parser(Scanner scanner) throws ScanErrorException
   {
      this.scanner = scanner;
      this.currToken = this.scanner.nextToken();
      this.map = new HashMap<String, Integer>();
   }

   /**
    * Eats a token after checking if the current token matches the expected token.
    *
    * @param expected the expected token 
    * @throws ScanErrorException if the current token does not match the expected token 
    */
   private void eat(String expected) throws ScanErrorException
   {
      if (expected.equals(currToken))
      {
         currToken = scanner.nextToken();
      }
      else
      {
         throw new IllegalArgumentException("Expected " + expected + " but found " + currToken);
      }
   }

   /**
    * Parses a number.  
    * @throws ScanErrorException if the current token does not match the expected token
    *
    * @precondition current token is an integer
    * @postcondition number token has been eaten 
    * @return the value of the parsed integer
    */
   private int parseNumber() throws ScanErrorException
   {
      int num = Integer.parseInt(currToken);
      eat(currToken);
      return num;
   }

   /**
    * Parses a program of statements, containing WRITELN, BEGIN, END, and variable statements, and prints the values to be written. 
    * Follows the grammar below. 
    * stmt -> WRITELN(expr); | BEGIN whilebegin | id := expr;
    * whilebegin -> END; | stmt whilebegin
    * stmts -> stmts stmt | epsilon
    * expr -> term whileexpr
    * whileexpr -> + term whileexpr | - term whileexpr | epsilon 
    * where term is defined in parseTerm's description
    * @throws ScanErrorException if the current token does not match the expected token
    *
    */
    public void parseStatement() throws ScanErrorException
    {
        eat("BEGIN");
        while (!currToken.equals("END"))
        {
            if (currToken.equals("BEGIN"))
            {
                parseStatement();
            }
            else if (currToken.equals("WRITELN"))
            {
                eat("WRITELN");
                eat("(");
                while (!currToken.equals(")"))
                {
                    int expr = parseTerm();
                    while (currToken.equals("+") || currToken.equals("-"))
                    {
                        if (currToken.equals("+"))
                        {
                            eat("+");
                            expr += parseTerm();
                        }
                        else 
                        {
                            eat("-");
                            expr -= parseTerm();
                        }
                    }
                    System.out.println(expr);
                    
                }
                eat(")");
                eat(";");            
            }
            else // variable statement 
            {
                String varName = currToken;
                eat(varName);
                eat(":=");
                int varVal = parseTerm();
                while (currToken.equals("+") || currToken.equals("-"))
                {
                    if (currToken.equals("+"))
                    {
                        eat("+");
                        varVal += parseTerm();
                    }
                    else
                    {
                        eat("-");
                        varVal -= parseTerm();
                    }
                }
                if (map.get(varName) == null)
                {
                    map.put(varName, varVal);
                }
                else
                {
                    map.replace(varName, varVal);
                }
                eat(";");
            }
        }
        eat("END");
        eat(";");
    }

    /**
     * Parses a factor following the below grammar.
     * factor -> (expr) | -factor | num | id
     * expr -> term whileexpr
     * whileexpr -> + term whileexpr | - term whileexpr | epsilon 
     * term -> factor whileterm
     * whileterm -> * factor whileterm | / factor whileterm | epsilon
     * 
     * @precondition current token is part of a factor
     * @postcondition factor tokens have been eaten
     * @return the value of the current factor
     * @throws ScanErrorException
     */
    private int parseFactor() throws ScanErrorException
    {
        if (currToken.equals("-"))
        {
            eat("-");
            return -1*parseFactor();
        }
        if (currToken.equals("("))
        {
            eat("(");
            int num = parseFactor();
            if (currToken.equals("*"))
            {
                eat("*");
                num *= parseFactor();
            }
            if (currToken.equals("/"))
            {
                eat("/");
                num /= parseFactor();
            }
            if (currToken.equals("+"))
            {
                eat("+");
                num += parseFactor();
            }
            if (currToken.equals("-"))
            {
                eat("-");
                num -= parseFactor();
            }
            eat(")");
            return num;
        }
        try 
        {
            Integer.parseInt(currToken);
            return parseNumber();
        } 
        catch (Exception e) 
        {
            String varName = currToken;
            eat(currToken);
            return map.get(varName);
        }
    }

    /**
     * Parses a term, which is any expression that can be added or subtracted,
     * following the below grammar. 
     * term -> factor whileterm
     * whileterm -> * factor whileterm | / factor whileterm | epsilon
     * factor -> (term) | -factor | num | id
     * 
     * @precondition current token is a factor
     * @postcondition term tokens have been eaten
     * @return the value of the term
     * @throws ScanErrorException
     */
    private int parseTerm() throws ScanErrorException
    {
        int term = parseFactor();
        while (currToken.equals("*") || currToken.equals("/"))
        {
            if (currToken.equals("*"))
            {
                eat("*");
                term *= parseFactor();
            }
            else 
            {
                eat("/");
                term /= parseFactor();
            }
        }
        return term;
    }

}
