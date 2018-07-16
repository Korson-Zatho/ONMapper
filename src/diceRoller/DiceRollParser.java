package diceRoller;


public class DiceRollParser {

	public static void main(String[] args)
	{
		DiceRollParser parser = new DiceRollParser();
		String input = "1d20 + 20";
		System.out.println(parser.checkSyntax(input));
		System.out.println(parser.parseStatement(input));
	}
	
	private WRandom random = new WRandom();
	
	public boolean checkSyntax(String input)
	{
		input = input.trim();
		for (int i = 0; i < input.length(); i++)
		{
			char c = input.charAt(i);
			if (!(c == 'd' || (c >= '0' && c <= '9') || c == '+' || c == '-'))
			{
				return false;
			}
		}
		return true;
	}
	
	public int parseStatement(String input)
	{
		input = input.trim();
		System.out.println(input);
		for (int i = 0; i < input.length(); i++)
		{
			switch(input.charAt(i))
			{
				case('+'): return parseStatement(input.substring(0, i-1)) + parseStatement(input.substring(i+1));
				case('-'): return parseStatement(input.substring(0, i-1)) - parseStatement(input.substring(i+1));
			}
		}
		if(input.contains("d".subSequence(0, 1)))
		{
			String[] split = input.split("d");
			return rollDice(Integer.parseInt(split[0]), Integer.parseInt(split[1]));			
		} 	else {
			return Integer.parseInt(input);
		}
	}
	
	public int rollDice(int amount, int dice)
	{
		return random.rollXDX(amount, dice);
	}
}
