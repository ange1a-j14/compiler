package ast;

import environment.Environment;

/**
 * This class represents a Number in the abstract syntax tree.
 * 
 * @author Angela Jia
 * @version March 29, 2022
 */
public class Number extends Expression 
{
    private int value;

    /**
     * Constructs a new instance of Number with a given value.
     *
     * @param value the value of the number
     */
    public Number(int value) 
    {
        this.value = value;
    }

    /**
     * Returns the value of the number.
     *
     * @param env the environment that stores the variables
     *
     * @return the value of the number
     */
    @Override
    public int eval(Environment env) 
    {
        return value;
    }

    /**
     * Compiles the Number in MIPS.
     * 
     * @param e the emitter used to write MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        e.emit("li $v0 " + value);
    }
}
