package diceRoller;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceRollParser {

	/* Hinzufügen einer Unterscheidung zwischen Schadenstypen in einem Statement und bei der Ausgabe
	 * wären ein optimales nächstes Upgrade.
	 * Ein Term könnte wie folgt aussehen:
	 * "4d4 + 3 bludg + 2d6 acid"
	 * Als Ergebnis nach einem solchen Aufruf erwartet man einen Gesamtschadenswert sowie Schadenswerte
	 * für jeden angegebenen Typ hier also Bludgeoning und Acid.
	 * 
	 * Um solche Aussagen zu beachten trennen wir zu Begin das Statement nach Schadenstyp.
	 * Werten jedes Statement einzeln aus und schreiben die Ergebnisse an zugehörige Stellen in ein Array.
	 * Dann wird noch der Gesamtschaden an die 0-te Stelle des Arrays geschrieben, und wir haben eine Aufteilung
	 * nach Schadenstypen. Das sollte es erleichtern den Schaden den man erhält transparent zu halten und Resistenzen
	 * wirkungsvoll und mit weniger rechenaufwand hinzuzufügen.
	 * Vom Gesamtschaden muss ja nur der reduzierte Schaden abgezogen werden.
	 * 
	 */
	public static void main(String[] args)
	{
		DiceRollParser parser = new DiceRollParser();
		String input = "1d20 acid + 2d4";
		parser.evaluateDmgTypes(input);
	}
	
	private WRandom random = new WRandom();
	
	
	/**
	 * Check Syntax and return false if a none expected character is in the string
	 * @param input
	 * @return
	 */
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
	
	/**
	 * Returns a HashMap that has as keys the dmg types that were dealt and the totalDmg dealt
	 *
	 * @param input
	 * @return A HashMap String->Integer that maps from dmgType to dmgAmount.
	 */
	public HashMap<String, Integer> evaluateDmgTypes(String input)
	{
		HashMap<String, Integer> dmgMapping = new HashMap<String, Integer>();
		
		//split the String at the dmg types so at specific words defined above but in lowerCase
		input.trim();
		
		input.indexOf("[a-zA-Z]{2,}");
		
		
		return dmgMapping;
	}
	
	/**
	 * Parse an input string and evaluates the rolls given.
	 * 
	 * @param input - String, a statement after these rules:
	 * X := YdY | 
	 * @return Evaluated Integer of the statement
	 */
	public int parseStatement(String input)
	{
		input = input.trim();
		for (int i = 0; i < input.length(); i++)
		{
			switch(input.charAt(i))
			{
				case('+'): return parseStatement(input.substring(0, i)) + parseStatement(input.substring(i+1));
				case('-'): return parseStatement(input.substring(0, i)) - parseStatement(input.substring(i+1));
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
