package ast;

import environment.Environment;

/**
 * This class represents an If statement.
 * 
 * @author Angela Jia
 * @since 4/13/22
 */
public class If extends Statement
{
   private Condition cond;
   private Statement stmts; 

   /**
    * Constructs a new If statement.
    *
    * @param cond the condition to be evaluated 
    * @param block the statements to be executed if the If statement is true
    */
   public If(Condition cond, Statement stmts)
   {
      this.cond = cond;
      this.stmts = stmts;
   }

   /**
    * Executes the If Then statement block.
    *
    * @param env the given environment
    */
   @Override
   public void exec(Environment env)
   {
      if (cond.eval(env) == 1)
      {
         stmts.exec(env);
      }
   }
}
