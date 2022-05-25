package ast;

import environment.Environment;

/**
 * This class represents a BinOp in the abstract syntax tree.
 * 
 * @author Angela Jia
 * @version March 29, 2022
 */
public class BinOp extends Expression 
{
    private String op;
    private Expression exp1;
    private Expression exp2;

    /**
     * Constructs a new instance of BinOp with a given operation and two
     * expressions.
     *
     * @param op   the operation to connect the two expressions
     * @param exp1 the first expression
     * @param exp2 the second expression
     */
    public BinOp(String op, Expression exp1, Expression exp2) 
    {
        this.op = op;
        this.exp1 = exp1;
        this.exp2 = exp2;
    }

    /**
     * Evaluates the binary operation.
     *
     * @param exp the given environment
     * @return the value of the resulting expression
     */
    @Override
    public int eval(Environment exp) 
    {
        if (op.equals("+")) 
        {
            return exp1.eval(exp) + exp2.eval(exp);
        } 
        else if (op.equals("-")) 
        {
            return exp1.eval(exp) - exp2.eval(exp);
        } 
        else if (op.equals("*")) 
        {
            return exp1.eval(exp) * exp2.eval(exp);
        } 
        else 
        {
            return exp1.eval(exp) / exp2.eval(exp);
        }

    }

    /**
     * Compiles the binary operation in MIPS.
     * 
     * @param e the emitter used to write MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        exp1.compile(e);
        e.emitPush("$v0");
        exp2.compile(e);
        e.emitPop("$t0");
        if (op.equals("+")) e.emit("addu $v0 $t0 $v0");
        else if (op.equals("-")) e.emit("subu $v0 $t0 $v0");
        else if (op.equals("*"))
        {
            e.emit("mult $v0 $t0");
            e.emit("mflo $v0");
        }
        else if (op.equals("/"))
        {
            e.emit("div $v0 $t0");
            e.emit("mflo $v0");
        }

    }
}
