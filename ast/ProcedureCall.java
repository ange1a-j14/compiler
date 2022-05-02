package ast;

import java.util.List;

import environment.Environment;

/**
 * This class represents a Procedure Call.
 * 
 * @author Angela Jia
 * @since 4/19/22
 */
public class ProcedureCall extends Expression
{
    private String name; 
    private List<Expression> params;

    /**
     * Constructs a new Procedure Call that has the given parameter values.
     * 
     * @param name  the name of the procedure
     * @param params the parameter values substituted into the procedure declaration params
     */
    public ProcedureCall(String name, List<Expression> params)
    {
        this.name = name;
        this.params = params;
    }

    /**
     * Evaluates the procedure. 
     * 
     * @param env the given environment
     */
    @Override
    public int eval(Environment env)
    {
        ProcedureDeclaration decl = env.getProcedure(name);
        List<String> formalParams = decl.getFormalParams();
        Environment env2 = new Environment(env);
        env2.declareVariable(name, 0);
        for (int i = 0; i < params.size(); i++)
        {
            env2.declareVariable(formalParams.get(i), params.get(i).eval(env));
        };
        decl.getStatement().exec(env2);
        return env2.getVariable(name);
    }
}
