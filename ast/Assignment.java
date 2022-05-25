package ast;

import environment.Environment;

/**
 * This class represents an Assignment in the abstract syntax tree.
 * 
 * @author Angela Jia
 * @version March 29, 2022
 */
public class Assignment extends Statement 
{
    private String var;
    private Expression exp;

    /**
     * Constructs a new instance of Assignment with a given variable name and value;
     *
     * @param var the variable to be assigned a value
     * @param exp the expression to be stored in the variable
     */
    public Assignment(String var, Expression exp) 
    {
        this.var = var;
        this.exp = exp;
    }

    /**
     * Executes the assignment by storing the variable in the given environment.
     *
     * @param env the environment that stores the variable assignment
     */
    @Override
    public void exec(Environment env) 
    {
        env.setVariable(var, exp.eval(env));
    }

    /**
     * Compiles the assignment in MIPS.
     * 
     * @param e the emitter used to write MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("sw $v0 " + "var" + var + "      # set variable value");
    }
}
