package ast;

import environment.Environment;

/**
 * This class represents a Variable in the abstract syntax tree.
 * 
 * @author Angela Jia
 * @version March 29, 2022
 */
public class Variable extends Expression 
{
    private String name;

    /**
     * Constructs a new instance of Variable with a given value.
     *
     * @param value the String value of the Variable
     */
    public Variable(String name) 
    {
        this.name = name;
    }

    /**
     * Returns the value of the variable.
     * 
     * @param env the environment that stores the variables
     *
     * @return the value of the variable as stored in env
     */
    @Override
    public int eval(Environment env) 
    {
        return env.getVariable(name);
    }

    /**
     * Compiles the variable in MIPS, adding 'var' to the variable name
     * to prevent ambiguous naming.
     * 
     * @param e the emitter used to write MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("la $t0 " + "var" + name);
        e.emit("lw $v0 ($t0)        # load variable");
    }
}
