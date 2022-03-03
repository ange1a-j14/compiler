package othello;
/**
* This file defines a simple lexer for the compilers course 2022.
* It is primarily meant to scan a text of Shakespeare's play Othello.
* It parses stage cues, dates, version numbers, acts, scenes, 
* major characters, words, section breaks, and punctuation as lexemes. 
*
* @author Clarice Wang
* @author Angela Jia
* @author Alivia Li 
* @version February 11, 2022
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

/**
 * Pattern definitions for line terminators, white space,
 * punctuation, stage cues (denoted by []), acts, and scenes.
 */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator} | [ \t\f]
Punctuation = [;|:|.|?|\"|!|,|-]
StageCue = "["([a-zA-Z]|[0-9]|{WhiteSpace}|{Punctuation})*"]"
Act = "ACT "[0-9]+
Scene = "Scene "[0-9]+

%%
/**
 * Lexical rules:
 * Parses stage cues, dates, version numbers, acts, scenes, 
 * major characters, words, punctuation, and section breaks.
 * @return the identifier name and lexeme.
 */
{StageCue} {return "stage cue: " + yytext();}
([A-Z][a-z]+" "[0-9]+[,]" "[0-9]+) {return "date: " + yytext();}
[0-9 .]+[0-9] {return "version: " + yytext();}
{Act} {return "act: " + yytext();}
{Scene} {return "scene: " + yytext();}
[A-Z][A-Z]+|([A-Z][A-Z]+" "[A-Z][A-Z]+)  {return "major character: " + yytext();}
("=")+ {return "section break: " + yytext();}
("'")*[a-zA-Z]+("'")*[a-zA-Z]*("'")* {return "word: " + yytext();}
{Punctuation}+ {return "punctuation: " + yytext();}
{WhiteSpace} {/* do nothing */}
.			{ /* do nothing */ }