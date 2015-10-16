package cesarCipher;

public class CesarCipher
{
	public static int intOfChar(char a){return (int)a;}

	public static char charOfInt(int x){return (char)x;}

	public static int rankOfChar(char a){
		if (isLowerCase(a))
			return intOfChar(a) - intOfChar('a');
		return intOfChar(a) - intOfChar('A');
	}

	public static char charOfRank(int r){return charOfInt(r + intOfChar('a'));}

	public static int reverseKey(int k){return 26 - k;}

	public static boolean isLowerCase(char c){return c >= 'a' && c <= 'z';}

	public static boolean isUpperCase(char c){return c >= 'A' && c <= 'Z';}

	public static boolean isALetter(char c){return isLowerCase(c) || isUpperCase(c);}

	public static int crypt(int l, int key){return (l + key) % 26;}

	public static char crypt(char a, int key){
		if (isALetter(a))
			return charOfRank(crypt(rankOfChar(a), key));
		return a;}

	public static String crypt(String s, int key){
		String res = "";
		for (int i = 0  ; i < s.length() ; i++)
			res += crypt(s.charAt(i), key);
		return res;
	}

	public static String decrypt(String s, int key){return crypt(s, reverseKey(key));}

	public static void bruteForceBreak(String s){

	}

	public static void main(String[] args)
	{
		String message = "J'aime la programmation.",
				cipher = crypt(message, 6),
				result = decrypt(cipher, 6);
		System.out.println(message);
		System.out.println(cipher);
		System.out.println(result);	
	}
}
