package othello;
/**
* This file defines a simple lexer for the compilers course 2022
* @author Clarice Wang
* @author Alivia Li 
* @author Angela Jia
* @version February 9, 2022
*/
import java.io.*;


%%
/* lexical functions */
/* specify that the class will be called Scanner and the function to get the next
 * token is called nextToken.  
 */
%class Scanner
%unicode
%line
%public
%function nextToken
/*  return String objects - the actual lexemes */
/*  returns the String "END: at end of file */
%type String
%eofval{
return "END";
%eofval}
/* use switch statement to encode DFA */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]

/**
 * Pattern definitions
 */
 
 

%%
/**
 * lexical rules
 */
"handkerchief" {return "Found " + yytext() + " at line " + yyline; } 
[a-z A-Z][a-z]*	{return "word: " + yytext();}
[A-Z]+ {return "character: " + yytext();}
[; | : | . | ? | ! | , | ' | -]+ {return "punctuation: " + yytext();}
[0-9]+ {return "line number: " + yytext();}
{WhiteSpace} {/* do nothing */}
.			{ /* do nothing */ }