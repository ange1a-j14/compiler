package ast;

import environment.Environment;

/**
 * This abstract class is a Statement that can be evaluated or executed with a
 * given environment.
 * 
 * @author Angela Jia
 * @version 4/12/22
 */
public abstract class Statement 
{
    /**
     * Executes the Statement with the given environment.
     * 
     * @param env the given environment
     */
    public abstract void exec(Environment env);
}
