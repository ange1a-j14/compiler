package scanner;

import java.io.*;

/**
 * Scanner is a simple scanner for Compilers and Interpreters (2014-2015) lab exercise 1.
 * It is responsible for reading the input string, determining the lexemes according to 
 * a given set of rules, and generating a stream of tokens. 
 * 
 * @author Angela Jia
 * @version January 24, 2022
 */
public class Scanner
{
    private BufferedReader in;
    private char currentChar;
    private boolean eof;
    /**
     * Scanner constructor for construction of a scanner that 
     * uses an InputStream object for input.  
     * Usage: 
     * FileInputStream inStream = new FileInputStream(new File(<file name>);
     * Scanner lex = new Scanner(inStream);
     * 
     * @param inStream the input stream to use
     */
    public Scanner(InputStream inStream)
    {
        in = new BufferedReader(new InputStreamReader(inStream));
        eof = false;
        getNextChar();
    }
    /**
     * Scanner constructor for constructing a scanner that 
     * scans a given input string.  It sets the end-of-file flag an then reads
     * the first character of the input string into the instance field currentChar.
     * Usage: Scanner lex = new Scanner(input_string);
     * 
     * @param inString the string to scan
     */
    public Scanner(String inString)
    {
        in = new BufferedReader(new StringReader(inString));
        eof = false;
        getNextChar();
    }

    /**
     * Sets currentChar to the next character in the input as long as there is another 
     * valid character to be read. If the end of file has been reached 
     * or a period has been reached, set eof to true instead. 
     */
    private void getNextChar()
    {
        try 
        {
            int readVal;
            readVal = in.read();

            if (readVal != -1 && readVal != '.')
            {
                currentChar = (char) readVal;
            }
            else
            {
                eof = true;
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * Retrieves the next character in the file if the current character
     * is valid.
     * 
     * @param expected the expected value of the current character
     * @throws ScanErrorException if the current character is not expected
     */
    
    private void eat(char expected) throws ScanErrorException
    {
        if (expected == currentChar)
        {
            getNextChar();
        }
        else
        {
            throw new ScanErrorException("Illegal character - expected " + 
                                            currentChar + " and found " + expected);
        }
    }
    /**
     * Determines whether there is another character to be read.
     * 
     * @return true if there is more to be read; otherwise, false
     */
    public boolean hasNext()
    {
        return !eof;
    }

    /**
     * Returns the next lexeme as a token. 
     * 
     * @return lexeme found in input stream or "eof" if end of file or period reached
     * @throws ScanErrorException if no lexeme recognized
     */
    public String nextToken() throws ScanErrorException
    {
        while (hasNext() && isWhitespace(currentChar))
        {
            eat(currentChar);
        }
        if (!hasNext())
            return "eof";
        if (isDigit(currentChar))
            return scanNumber();
        else if (isLetter(currentChar))
            return scanIdentifier();
        else 
            return scanOperand();
    }    

    /**
     * Determines whether the given character is a digit or not.
     * 
     * @param inputChar the character to be checked 
     * 
     * @return true if the input character is a digit; otherwise, false
     */
    public static boolean isDigit(char inputChar)
    {
        return inputChar >= '0' && inputChar <= '9';
    }

    /**
     * Determines whether the given character is a letter or not.
     * 
     * @param inputChar the character to be checked
     * 
     * @return true if the input character is a letter; otherwise, false
     */
    public static boolean isLetter(char inputChar)
    {
        return (inputChar >= 'A' && inputChar <= 'Z') || (inputChar >= 'a' && inputChar <= 'z');
    }

    /**
     * Determines whether the given character is whitespace or not.
     * 
     * @param inputChar the character to be checked
     * 
     * @return true if the input character is whitespace; otherwise, false
     */
    public static boolean isWhitespace(char inputChar)
    {
        return inputChar == ' ' || inputChar == '\t' || 
                inputChar == '\r' || inputChar == '\n';
    }

    /**
     * Scans a number defined by the regular expression (digit)(digit)*.
     * 
     * @return lexeme found in input stream
     * @throws ScanErrorException if no lexeme is recognized 
     * @precondition initial currentChar is a number
     */
    private String scanNumber() throws ScanErrorException
    {
        String lexString = String.valueOf(currentChar);
        eat(currentChar);
        while (hasNext() && isDigit(currentChar))
        {
            lexString += String.valueOf(currentChar);
            eat(currentChar);
        }
        return lexString;
    }

    /**
     * Scans an identifier defined by the regular expression letter(letter | digit)*.
     * 
     * @return lexeme found in input stream
     * @throws ScanErrorException if no lexeme is recognized 
     * @precondition initial currentChar is a letter
     */
    private String scanIdentifier() throws ScanErrorException
    {
        String lexString = String.valueOf(currentChar);
        eat(currentChar);
        while (hasNext() && (isLetter(currentChar) || isDigit(currentChar)))
        {
            lexString += String.valueOf(currentChar);
            eat(currentChar);
        }
        return lexString;
    }

    /**
     * Scans an operand defined by the regular expression 
     * ['=' '+' '-' '*' '/' '%' '<' '>' ':' '(' ')' ';' 
     *  '==' '+=' '-=' '*=' '%=' '<=' '>=' ':=' '!=' '<>'].
     * 
     * @return lexeme found in input stream
     * @throws ScanErrorException if no lexeme is recognized 
     * @precondition initial currentChar is not a letter or number
     */
    private String scanOperand() throws ScanErrorException
    {
        if (!(currentChar == '=' || currentChar == '+' || currentChar == '-' || 
                currentChar == '*' || currentChar == '/' || currentChar == '%' || 
                currentChar == '<' || currentChar == '>' || currentChar == ':' || 
                currentChar == '(' || currentChar == ')' || currentChar == ';'))
        {
            throw new ScanErrorException("Unrecognized character encountered: " + currentChar);
        }

        String lexString = "";
        if (hasNext() && currentChar == '/')
        {
            eat(currentChar);
            if (currentChar == '/')
            {
               //recognize a single line comment
                while (currentChar != '\n')
                {
                    eat(currentChar);
                }
                return nextToken();
            }
            else 
            {
                lexString = "/";
            }
        }
        else
        {
            lexString += String.valueOf(currentChar);
            if (hasNext() && (currentChar == '=' || currentChar == '+' || currentChar == '-' || 
                    currentChar == '*' || currentChar == '%' || currentChar == '<' || 
                    currentChar == '>' || currentChar == ':' || currentChar == '!')) 
            {
                eat(currentChar);
                if (currentChar == '=' || (lexString.charAt(0) == '<') && currentChar == '>')
                {
                    lexString += currentChar; 
                    eat(currentChar);
                }
            }
            else
            {
                eat(currentChar);
            }
        }
        return lexString;
    }
}
