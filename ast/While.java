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

    /**
     * Compiles the while loop in MIPS and creates an empty endWhile block.
     * 
     * @param e the emitter used to write MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        String loop = "loop" + e.nextLabelID();
        e.emit(loop + ":");
        String endWhile = "endw" + e.nextLabelID();
        cond.compile(e, endWhile);
        stmts.compile(e);
        e.emit("j " + loop);
        e.emit(endWhile + ":");
    }
}
