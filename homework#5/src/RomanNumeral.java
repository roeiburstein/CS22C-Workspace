/**
 * Program Name: RomanNumeral.java
 * 
 * @author Roei Burstein 
 * Current Date: Wednesday, March 11, 2018 
 * Computer Operating System: Mac OS High Sierra 
 * Version 10.13.2 
 * Compiler: Eclipse Oxygen 4.7.0
 * <p>
 * Description: This program is the RomanNumeral object and its methods.
 * <p>
 */

public class RomanNumeral {
	public static final int[] romanValues = { 1000, 500, 100, 50, 10, 5, 1 };

	public static final RomanNumeral[] romanArray = { new RomanNumeral("M"), new RomanNumeral("CM"),
			new RomanNumeral("D"), new RomanNumeral("CD"), new RomanNumeral("C"), new RomanNumeral("XC"),
			new RomanNumeral("L"), new RomanNumeral("XL"), new RomanNumeral("X"), new RomanNumeral("IX"),
			new RomanNumeral("V"), new RomanNumeral("IV"), new RomanNumeral("I") };

	private String romNumStr = "";
	private int value; // default 0

	public RomanNumeral(String str) throws IllegalArgumentException {
		setRomNumStr(str);
	}// end constructor

	public RomanNumeral(int val) {
		setValue(val);
	}

	public RomanNumeral() {
	}

	public boolean setRomNumStr(String str) {
		if (str != null) {
			romNumStr = str.trim();
			romNumStr = romNumStr.toUpperCase();
			if (calcValue())
				return true;
			else
				throw new IllegalArgumentException();
		} else
			throw new IllegalArgumentException();
	}// end setRomNumStr

	public boolean setValue(int val) {
		if (val > 0) {
			value = val;
			makeRomNumStr();
			return true;
		} else
			return false;
	}// end set Value

	private boolean calcValue() {
		if (romNumStr.length() == 0)
			return false;
		int[] digitValues = new int[romNumStr.length()];
		int i;

		// get int values of each roman numeral digit
		for (i = 0; i < digitValues.length; ++i) {
			int index = "MDCLXVI".indexOf(romNumStr.charAt(i));
			if (index >= 0) {
				digitValues[i] = romanValues[index];
			} else {
				romNumStr = "";
				value = 0;
				return false;
			}
		} // end for

		// convert to int value
		value = 0;
		for (i = 0; i < digitValues.length - 1; ++i) {
			if (digitValues[i] >= digitValues[i + 1]) {
				value += digitValues[i];
			} else
				value -= digitValues[i];
		} // end for
		if (digitValues.length > 0)
			value += digitValues[i];
		return true;
	}// end calcValue

	public String getRomNumStr() {
		return romNumStr;
	}// end getRomNumStr

	public int getValue() {
		return value;
	}// end getValue

	public void makeRomNumStr() {
		StringBuffer sb = new StringBuffer();
		int i = 0;

		int val = value;
		for (i = 0; i < romanArray.length; ++i) {
			while ((val - romanArray[i].value) >= 0) {
				sb.append(romanArray[i].romNumStr);
				val -= romanArray[i].value;
			} // end while
		} // end for
		romNumStr = sb.toString();
	}// end makeRomanNumeral

	public String toString() {
		return romNumStr + " = " + value;
	} // end toString

} // end class RomanNumeral
