package ast;

import environment.Environment;

/**
 * This abstract class represents an expression that can be evaluated.
 * 
 * @author Angela Jia
 * @since 4/12/22
 */
public abstract class Expression 
{
    /**
     * Evaluates the Expression using the given environment.
     * 
     * @param env the given environment 
     * @return the integer value of the expression 
     */
    public abstract int eval(Environment env);

    /**
     * Compiles an expression into MIPS code.
     * 
     * @param e the emitter used to output the code
     */
    public void compile(Emitter e)
    {
        throw new RuntimeException("Implement me!");
    }
}
