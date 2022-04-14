package ast;

import environment.Environment;

/**
 * This class represents a boolean Condition. 
 * 
 * @author Angela Jia
 * @since 4/13/22
 */
public class Condition extends Expression
{
   private Expression exp1;
   private Expression exp2;
   private String relop;

   /**
    * Constructs a new Condition. 
    *
    * @param exp1   the first Expression
    * @param exp2   the second Expression
    * @param relop  the comparison operator 
    */
   public Condition(Expression exp1, Expression exp2, String relop)
   {
       this.exp1 = exp1;
       this.exp2 = exp2;
       this.relop = relop;
   }

   /**
    * Evaluates the condition.
    *
    * @param env the given environment
    *
    * @return 1 if the condition evaluates as true; otherwise, 0
    */
   @Override
   public int eval(Environment env)
   {
        if (relop.equals("="))
        {
            if (exp1.eval(env) == exp2.eval(env)) return 1;
            else return 0;
        }
        else if (relop.equals("<>"))
        {
            if (exp1.eval(env) != exp2.eval(env)) return 1;
            else return 0;
        }
        else if (relop.equals("<"))
        {
            if (exp1.eval(env) < exp2.eval(env)) return 1;
            else return 0;
        }
        else if (relop.equals(">"))
        {
            if (exp1.eval(env) > exp2.eval(env)) return 1;
            else return 0;
        }
        else if (relop.equals("<="))
        {
            if (exp1.eval(env) <= exp2.eval(env)) return 1;
            else return 0;
        }
        else // >=
        {
            if (exp1.eval(env) >= exp2.eval(env)) return 1;
            else return 0;
        }
   }

}