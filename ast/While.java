package ast;

import environment.Environment;

/**
 * This class represents a While loop.
 * 
 * @author Angela Jia
 * @since 4/14/22
 */
public class While extends Statement
{
    private Condition cond;
    private Statement stmts;

    /**
     * Constructs a new While instance. 
     * 
     * @param cond the condition for entering the While loop
     * @param stmts the statement(s) within the While loop
     */
    public While(Condition cond, Statement stmts) 
    {
        this.cond = cond;
        this.stmts = stmts;
    }

    /**
     * Executes a While loop.
     * 
     * @param env the given environment
     */
    public void exec(Environment env)
    {
        while (cond.eval(env) == 1)
        {
            stmts.exec(env);
        }
    }
}
