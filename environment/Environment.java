package environment;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is an Environment that stores variable names and their respective
 * values.
 * 
 * @author Angela Jia
 * @since 4/12/22
 */
public class Environment 
{
    private Map<String, Integer> map;

    /**
     * Constructs a new instance of Environment.
     */
    public Environment() 
    {
        map = new HashMap<String, Integer>();
    }

    /**
     * Associates the given variable name with the given value. If the variable
     * already exists,
     * replaces the variable value with the given value.
     *
     * @param variable the name of the variable to be stored
     * @param value    the value of the variable
     */
    public void setVariable(String variable, int value) 
    {
        if (map.get(variable) == null) {
            map.put(variable, value);
        } else {
            map.replace(variable, value);
        }
    }

    /**
     * Returns the value associated with the given variable name.
     *
     * @param variable the name of the variable
     * @return the value of the variable
     */
    public int getVariable(String variable) 
    {
        return map.get(variable);
    }

}