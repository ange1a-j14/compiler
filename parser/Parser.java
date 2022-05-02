package parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.Assignment;
import ast.BinOp;
import ast.Block;
import ast.Condition;
import ast.Expression;
import ast.If;
import ast.Number;
import ast.ProcedureCall;
import ast.ProcedureDeclaration;
import ast.Program;
import ast.Statement;
import ast.Variable;
import ast.While;
import ast.Writeln;
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
         System.out.println(currToken);
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
   private int parseNumber(String n) throws ScanErrorException
   {
      int num = Integer.parseInt(n);
      return num;
   }

   /**
    * Parses a program by parsing procedure declarations while the current token is PROCEDURE.
    * Then parses a single statement. 
    * Follows the grammar below:
    *
    * program → PROCEDURE id ( maybeparms ) ; stmt program | stmt . maybeparms → parms | ε
    * parms → parms , id | id
    * stmt →WRITELN(expr);|BEGINstmtsEND;|id:=expr;
    * IF cond THEN stmt | WHILE cond DO stmt stmts → stmts stmt | ε
    * expr → expr+term | expr-term | term
    * term → term * factor | term / factor | factor
    * factor →(expr)|-factor |num|id(maybeargs)|id maybeargs → args | ε
    * args → args , expr | expr
    * cond → expr relop expr
    * relop →=|<>|<|>|<=|>=
    *
    * @return a program
    */
   public Program parseProgram() throws ScanErrorException
   {
        List<ProcedureDeclaration> procedures = new ArrayList<ProcedureDeclaration>();
        while (currToken.equals("PROCEDURE"))
        {
            eat("PROCEDURE");
            String name = currToken;
            eat(name);
            eat("(");
            List<String> params = new ArrayList<String>();
            while (!currToken.equals(")"))
            {
                params.add(currToken);
                eat(currToken);
                if (currToken.equals(",")) eat(",");
            }
            eat(")");
            eat(";");
            Statement stmt = parseStatement();
            procedures.add(new ProcedureDeclaration(name, stmt, params));
        }
        Statement progStatement = parseStatement();
        return new Program(procedures, progStatement);
   }
   
   /**
    * Parses a program of statements, containing WRITELN, BEGIN, END, and variable statements, and prints the values to be written. 
    * Follows the grammar below. 
    * stmt -> WRITELN(expr); | BEGIN whilebegin | id := expr; | IF cond THEN stmt | WHILE cond DO stmt
    * whilebegin -> END; | stmt whilebegin
    * stmts -> stmts stmt | epsilon
    * expr -> term whileexpr
    * whileexpr -> + term whileexpr | - term whileexpr | epsilon 
    * where term is defined in parseTerm's description
    * @throws ScanErrorException if the current token does not match the expected token
    *
    */
    public Statement parseStatement() throws ScanErrorException
    {
        Statement stmt;
        if (currToken.equals("BEGIN"))
        {
            List<Statement> stmts = new ArrayList<Statement>();
            eat("BEGIN");
            while (!currToken.equals("END"))
            {
                stmts.add(parseStatement());
            }
            eat("END");
            eat(";");
            stmt = new Block(stmts);
        }
        else if (currToken.equals("WRITELN"))
        {
            eat("WRITELN");
            eat("(");
            Expression expr = parseExpression();
            eat(")");
            eat(";");
            stmt = new Writeln(expr);            
        }
        else if (currToken.equals("IF"))
        {
            eat("IF");
            Condition cond = parseCondition();
            eat("THEN");
            Statement thens = parseStatement();
            stmt = new If(cond, thens);
        }
        else if (currToken.equals("WHILE"))
        {
            eat("WHILE");
            Condition cond = parseCondition();
            eat("DO");
            Statement whiles = parseStatement();
            stmt = new While(cond, whiles);
        }
        else // variable statement 
        {
            String varName = currToken;
            eat(varName);
            eat(":=");
            Expression expr = parseExpression();
            eat(";");
            stmt = new Assignment(varName, expr);
        }
        return stmt;
    }

    /**
     * Parses a condition. 
     * 
     * @return the Condition parsed from the source file
     * @throws ScanErrorException
     */
    private Condition parseCondition() throws ScanErrorException
    {
        Expression exp1 = parseExpression();
        String relop = currToken;
        eat(relop);
        Expression exp2 = parseExpression();
        return new Condition(exp1, exp2, relop);
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
    private Expression parseFactor() throws ScanErrorException
    {
        if (currToken.equals("-"))
        {
            eat("-");
            Expression negone = new Number(-1);
            return new BinOp("*", negone, parseFactor());
        }
        if (currToken.equals("("))
        {
            eat("(");
            Expression expr = parseExpression();
            eat(")");
            return expr;
        }
        try 
        {
            String val = currToken;
            Integer.parseInt(val);
            eat(currToken);
            return new Number(parseNumber(val));
        } 
        catch (Exception e) 
        {
            String varName = currToken;
            eat(currToken);
            if (currToken.equals("("))
            {
                eat("(");
                List<Expression> args = new ArrayList<Expression>();
                while (!currToken.equals(")"))
                {
                    args.add(parseExpression());
                    if (currToken.equals(",")) eat(",");
                }
                eat(")");
                return new ProcedureCall(varName, args);
            }
            return new Variable(varName);
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
    private Expression parseTerm() throws ScanErrorException
    {
        Expression term = parseFactor();
        while (currToken.equals("*") || currToken.equals("/"))
        {
            if (currToken.equals("*"))
            {
                eat("*");
                term = new BinOp("*", term, parseFactor());
            }
            else 
            {
                eat("/");
                term = new BinOp("/", term, parseFactor());
            }
        }
        return term;
    }

    private Expression parseExpression() throws ScanErrorException
    {
        Expression expr = parseTerm();
        while (currToken.equals("+") || currToken.equals("-"))
        {
            if (currToken.equals("+"))
            {
                eat("+");
                expr = new BinOp("+", expr, parseTerm());
            }
            else 
            {
                eat("-");
                expr = new BinOp("-", expr, parseTerm());
            }
        }
        return expr;
    }

    public String getCurrToken()
    {
       return currToken;
    }

}
