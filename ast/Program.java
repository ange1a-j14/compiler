package ast;

import java.util.Date;
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
    private List<String> globalVars;
    private List<ProcedureDeclaration> procedures;
    private Statement stmt;

    /**
     * Constructs a new Program with a given list of procedure declarations and a statement.
     * 
     * @param procedures list of procedure declarations
     * @param stmt statement to be executed
     */
    public Program(List<String> globalVars, List<ProcedureDeclaration> procedures, Statement stmt)
    {
        this.globalVars = globalVars;
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
        for (String var : globalVars)
        {
            env.declareVariable(var, 0);
        }
        for (ProcedureDeclaration procDecl : procedures)
        {
            procDecl.exec(env);
        }
        stmt.exec(env);
    }

    /**
     * Writes code to an output file by using an Emitter.
     * 
     * @param outputFileName name of output file
     */
    public void compile(String outputFileName)
    {
        Emitter e = new Emitter(outputFileName);
        e.emit("# @author Angela Jia");
        e.emit("# @version " + new Date().toString());
        e.emit(".data");
        e.emit("nl: .asciiz \"\\n\"");
        for (String var : globalVars)
        {
            e.emit("var" + var + ": .word 0");
        }
        e.emit(".text");
        e.emit(".globl main");
        e.emit("main:");
        stmt.compile(e);
        e.emit("li $v0 10");
        e.emit("syscall     # terminate");
    }
}
