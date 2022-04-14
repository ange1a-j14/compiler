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
}
