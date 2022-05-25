package ast;
import java.io.*;

/**
 * This class writes to a new file with a given name.
 * 
 * @author Ms. Datar
 * @author Angela Jia
 * @version 5/11//22
 */
public class Emitter
{
	private PrintWriter out;
	private int idcount;

	/**
	 * Constructs a new Emitter instance that writes to a file
	 * with the given name.
	 * 
	 * @param outputFileName name of output file
	 */
	public Emitter(String outputFileName)
	{
		try
		{
			out = new PrintWriter(new FileWriter(outputFileName), true);
			idcount = 0;
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * Prints one line of code to file (with non-labels indented)
	 * 
	 * @param code line of code to print
	 */
	public void emit(String code)
	{
		if (!code.endsWith(":"))
			code = "\t" + code;
		out.println(code);
	}

	/**
	 * Closes the file. Should be called after all calls to emit.
	 */
	public void close()
	{
		out.close();
	}

	/**
	 * Emits MIPS code to push from the given register onto the stack.
	 *  
	 * @param reg register from which to push
	 */
	public void emitPush(String reg)
	{
		this.emit("subu $sp $sp 4");
		this.emit("sw " + reg + " ($sp)		# push from reg to stack");	
	}

	/**
	 * Emits MIPS code to pop from the stack to the given register 
	 * 
	 * @param reg register to pop to
	 */
	public void emitPop(String reg)
	{
		this.emit("lw " + reg + " ($sp)");
		this.emit("addu $sp $sp 4		# pop from stack to reg");
	}

	/**
	 * Generates the index for the next MIPS label to prevent ambiguous naming.
	 * 
	 * @return the ID number of the next label, starting from 0
	 */
	public int nextLabelID()
	{
		idcount++;
		return idcount;
	}
}