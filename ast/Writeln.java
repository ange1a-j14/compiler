package ast;

import environment.Environment;

/**
 * This class represents a Writeln in the abstract syntax tree.
 * 
 * @author Angela Jia
 * @version March 29, 2022
 */
public class Writeln extends Statement 
{
    private Expression exp;

    /**
     * Constructs a new instance of Writeln with a given expression.
     *
     * @param exp the expression to be written
     */
    public Writeln(Expression exp) 
    {
        this.exp = exp;
    }

    /**
     * Executes the Writeln statement with the given environment.
     *
     * @param env the given environment
     */
    @Override
    public void exec(Environment env) 
    {
        System.out.println(exp.eval(env));
    }

    /**
     * Compiles the Writeln instruction in MIPS.
     * 
     * @param e the emitter used to write MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        exp.compile(e);
        e.emit("move $a0 $v0");
        e.emit("li $v0 1");
        e.emit("syscall");
        e.emit("la $a0 nl");
        e.emit("li $v0 4");
        e.emit("syscall     # print expression and move to new line");
    }
}
