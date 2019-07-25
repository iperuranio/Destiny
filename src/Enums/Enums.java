package Enums;

public class Enums 
{
	public static enum ExecutionType
	{
		COMMAND("COMMAND"),  ITEM("ITEM"), MONEY("FULL_GAME");
	    
	    String value;
	    
	    private ExecutionType(String string)
	    {
	    	this.value = string;
	    }
	    
	    public String toString()
	    {
	    	return this.value;
	    }
	}
	
	public static enum LuckyBlockType
	{
		NORMAL("NORMAL"),  VIP("VIP");
	    
	    String value;
	    
	    private LuckyBlockType(String string)
	    {
	    	this.value = string;
	    }
	    
	    public String toString()
	    {
	    	return this.value;
	    }
	}
}
