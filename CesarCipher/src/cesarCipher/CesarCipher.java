package cesarCipher;

public class CesarCipher
{
	public static int intOfChar(char a)
	{
		return (int) a;
	}

	public static char charOfInt(int x)
	{
		return (char) x;
	}

	public static int rankOfChar(char a)
	{
		return intOfChar(a) - intOfChar('a');
	}

	public static char charOfRank(int r)
	{
		return charOfInt(r + intOfChar('a'));
	}

	public static int reverseKey(int k)
	{
		return 26 - k;
	}

	public static boolean isLowerCase(char c)
	{
		return c >= 'a' && c <= 'z';
	}

	public static char lowerCase(char c)
	{
		if (isUpperCase(c))
			return charOfInt(intOfChar(c) - intOfChar('A') + intOfChar('a'));
		return c;
	}

	public static boolean isUpperCase(char c)
	{
		return c >= 'A' && c <= 'Z';
	}

	public static boolean isALetter(char c)
	{
		return isLowerCase(c) || isUpperCase(c);
	}

	public static int crypt(int l, int key)
	{
		return (l + key) % 26;
	}

	public static char crypt(char a, int key)
	{
		if (isALetter(a))
		{	
			a = lowerCase(a);
			return charOfRank(crypt(rankOfChar(a), key));
		}
		return a;
	}

	public static String crypt(String s, int key)
	{
		String res = "";
		for (int i = 0; i < s.length(); i++)
			res += crypt(s.charAt(i), key);
		return res;
	}

	public static String decrypt(String s, int key)
	{
		return crypt(s, reverseKey(key));
	}

	public static void bruteForceBreak(String s)
	{
		for (int key = 1 ; key < 25 ; key++)
			System.out.println("key = " + key + " : " + decrypt(s, key));
	}

	public static void main(String[] args)
	{
		String message = "J'aime la programmation.", 
				cipher = crypt(message, 6), 
				result = decrypt(cipher, 6);
		System.out.println(message);
		System.out.println(cipher);
		System.out.println(result);
		bruteForceBreak(cipher);
	}
}
