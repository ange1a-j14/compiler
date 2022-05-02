package ast;

import java.util.List;

import environment.Environment;

/**
 * This class represents a Procedure Declaration.
 * 
 * @author Angela Jia
 * @version 4/18/22
 */
public class ProcedureDeclaration extends Statement
{
   private String name;
   private Statement stmt;
   List<String> params;

   /**
    * Constructs a new Procedure Declaration with a given name, body, and parameter list.
    *
    * @param name name of the procedure 
    * @param stmt the statement inside the Procedure 
    * @param params the parameter variables used in the Procedure
    */
   public ProcedureDeclaration(String name, Statement stmt, List<String> params)
   {
        this.name = name;
        this.stmt = stmt;
        this.params = params;
   }

   /**
    * Executes the body of the procedure declaration. 
    *
    * @param env the given environment 
    */
   @Override
   public void exec(Environment env)
   {
      env.setProcedure(name, this);
   }

   /**
    * Retrieves the list of formal parameters.
    *
    * @return list of parameters
    */
   public List<String> getFormalParams()
   {
      return params;
   }

   /**
    * Retrieves the statement in the procedure declaration.
    *
    * @return statement 
    */
   public Statement getStatement()
   {
      return stmt;
   }
}