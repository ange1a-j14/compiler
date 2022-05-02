package environment;

import java.util.HashMap;
import java.util.Map;

import ast.ProcedureCall;
import ast.ProcedureDeclaration;

/**
 * This class is an Environment that stores variable names and their respective
 * values, and procedure names and their respective declarations.
 * 
 * @author Angela Jia
 * @since 4/12/22
 */
public class Environment 
{
    private Map<String, Integer> varMap;
    private Map<String, ProcedureDeclaration> procMap;
    private Environment parent;

    /**
     * Constructs a new instance of Environment.
     */
    public Environment() 
    {
        varMap = new HashMap<String, Integer>();
        procMap = new HashMap<String, ProcedureDeclaration>();
        parent = null;
    }

    /**
     * Constructs a new instance of Environment with a given parent.
     */
    public Environment(Environment parent) 
    {
        varMap = new HashMap<String, Integer>();
        procMap = new HashMap<String, ProcedureDeclaration>();
        this.parent = parent;
    }


    /**
     * Associates the given variable name with the given value. If the variable
     * already exists,
     * replaces the variable value with the given value.
     *
     * @param variable the name of the variable to be stored
     * @param value    the value of the variable
     */
    public void declareVariable(String variable, int value) 
    {
        if (varMap.get(variable) == null) {
            varMap.put(variable, value);
        } else {
            varMap.replace(variable, value);
        }
    }

    /**
     * Associates the given variable name with the given value. If the variable
     * already exists, replaces the variable value with the given value. 
     * If the variable does not exist, searches for it in the parent environment (if a parent exists)
     * and modifies the variable value there if the variable is found.
     * If the variable is not found in the parent environment, declares the variable 
     * in the current environment. 
     * 
     * @param variable the name of the variable to be stored
     * @param value    the value of the variable
     */
    public void setVariable(String variable, int value)
    {
        if (varMap.get(variable) == null)
        {
            if (parent != null)
            {
                if (parent.varMap.get(variable) == null) declareVariable(variable, value);
                else parent.varMap.replace(variable, value);
            }
        }
        else varMap.replace(variable, value);
    }

    /**
     * Returns the value associated with the given variable name. If the variable
     * already exists in this environment, returns the variable value. 
     * If the variable does not exist, searches for it in the parent environment (if a parent exists)
     * and returns the variable from there if the variable is found.
     *
     * @param variable the name of the variable
     * @return the value of the variable
     */
    public int getVariable(String variable) 
    {
        if (varMap.get(variable) == null)
        {
            if (parent != null)
            {
                if (parent.varMap.get(variable) != null) return parent.varMap.get(variable);
            }
        }
        return varMap.get(variable);
    }

    /**
     * Associates the given procedure name with the given procedure declaration. If the procedure
     * already exists,
     * replaces the procedure declaration with the given declaration.
     *   
     * @param procName name of procedure
     * @param decl     procedure declaration
     */
    public void setProcedure(String procName, ProcedureDeclaration decl)
    {
        if (procMap.get(procName) == null) {
            procMap.put(procName, decl);
        } else {
            procMap.replace(procName, decl);
        }
    }

    /**
     * Returns the procedure declaration associated with the given procedure name.
     *
     * @param procName the name of the procedure
     * @return the procedure declaration associated with the procedure name
     */
    public ProcedureDeclaration getProcedure(String procName)
    {
        return procMap.get(procName);
    }
}