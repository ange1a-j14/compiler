package ast;

import java.util.List;

import environment.Environment;

/**
 * This class represents a Block in the abstract syntax tree.
 * 
 * @author Angela Jia
 * @version March 29, 2022
 */
public class Block extends Statement 
{
    private List<Statement> stmts;

    /**
     * Constructs a new instance of Block with a given list of statements.
     *
     * @param stmts the statements to be executed within the block
     */
    public Block(List stmts) 
    {
        this.stmts = stmts;
    }

    /**
     * Executes the block of statements.
     * 
     * @param env the given environment
     */
    @Override
    public void exec(Environment env)
    {
        for (int i = 0; i < stmts.size(); i++)
        {
            stmts.get(i).exec(env);
        }
    }

    /**
     * Compiles the block of statements in MIPS.
     * 
     * @param e the emitter used to write MIPS code
     */
    @Override
    public void compile(Emitter e)
    {
        for (Statement stmt : stmts)
        {
            stmt.compile(e);
        }
    }

}
