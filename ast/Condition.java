package ast;

import environment.Environment;

/**
 * This class represents a boolean Condition.
 * 
 * @author Angela Jia
 * @since 4/13/22
 */
public class Condition extends Expression 
{
    private Expression exp1;
    private Expression exp2;
    private String relop;

    /**
     * Constructs a new Condition.
     *
     * @param exp1  the first Expression
     * @param exp2  the second Expression
     * @param relop the comparison operator
     */
    public Condition(Expression exp1, Expression exp2, String relop) 
    {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.relop = relop;
    }

    /**
     * Evaluates the condition.
     *
     * @param env the given environment
     *
     * @return 1 if the condition evaluates as true; otherwise, 0
     */
    @Override
    public int eval(Environment env) 
    {
        if (relop.equals("=")) 
        {
            if (exp1.eval(env) == exp2.eval(env))
                return 1;
            else
                return 0;
        } else if (relop.equals("<>")) 
        {
            if (exp1.eval(env) != exp2.eval(env))
                return 1;
            else
                return 0;
        } else if (relop.equals("<")) 
        {
            if (exp1.eval(env) < exp2.eval(env))
                return 1;
            else
                return 0;
        } else if (relop.equals(">")) 
        {
            if (exp1.eval(env) > exp2.eval(env))
                return 1;
            else
                return 0;
        } else if (relop.equals("<=")) 
        {
            if (exp1.eval(env) <= exp2.eval(env))
                return 1;
            else
                return 0;
        } else // >=
        {
            if (exp1.eval(env) >= exp2.eval(env))
                return 1;
            else
                return 0;
        }
    }

    /**
     * Compiles the condition in MIPS.
     * 
     * @param e           the emitter used to write MIPS code
     * @param targetLabel the name of the MIPS label to branch to
     *                    if condition false
     */
    public void compile(Emitter e, String targetlabel) 
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        if (relop.equals("="))
            e.emit("bne $t0 $v0 " + targetlabel);
        else if (relop.equals("<>"))
            e.emit("beq $t0 $v0 " + targetlabel);
        else if (relop.equals("<"))
            e.emit("bge $t0 $v0 " + targetlabel);
        else if (relop.equals(">"))
            e.emit("ble $t0 $v0 " + targetlabel);
        else if (relop.equals("<="))
            e.emit("bgt $t0 $v0 " + targetlabel);
        else
            e.emit("blt $t0 $v0 " + targetlabel);
    }
}