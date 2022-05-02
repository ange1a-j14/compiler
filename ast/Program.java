package ast;

import java.util.List;

import environment.Environment;

/**
 * This class represents a Program, containing any number of procedure declarations
 * and a statement.
 * 
 * @author Angela Jia
 * @since 4/19/22
 */
public class Program extends Statement
{
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;

    /**
     * Constructs a new Program with a given list of procedure declarations and a statement.
     * 
     * @param procedures list of procedure declarations
     * @param stmt statement to be executed
     */
    public Program(List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.procedures = procedures;
        this.stmt = stmt;
    }
    
    /**
     * Executes the program.
     * 
     * @param env the environment used for execution 
     */
    @Override
    public void exec(Environment env)
    {
        for (ProcedureDeclaration procDecl : procedures)
        {
            procDecl.exec(env);
        }
        stmt.exec(env);
    }
}
