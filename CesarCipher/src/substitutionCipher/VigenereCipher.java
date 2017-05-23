package substitutionCipher;

import java.util.Random;

public class VigenereCipher
{
	public static int[] rankOfChar(String s)
	{
		int[] ranks = new int[s.length()];
		for (int i = 0 ; i < s.length() ; i++)
			ranks[i] = Util.rankOfChar(s.charAt(i));
		return ranks;
	}
	
	public static int[] reverseKey(int[] key)
	{
		int[] reverse = new int[key.length];
		for (int i = 0 ; i < key.length ; i++)
			reverse[i] = Util.reverseKey(key[i]);
		return reverse;		
	}
	
	public static String crypt(String s, int[] key)
	{
		String res = "";
		for (int i = 0; i < s.length(); i++)
			res += Util.crypt(s.charAt(i), key[i % key.length]);
		return res;
	}

	public static String crypt(String s, String key)
	{
		return crypt(s, rankOfChar(key));
	}

	public static String decrypt(String s, String key)
	{
		return crypt(s, reverseKey(rankOfChar(key)));
	}

	public static String randomKey(Random r, int length)
	{
		String s = "";
		for (int i = 0 ; i < length ; i++)
			s += Util.charOfRank(r.nextInt(26));
		return s;
	}

	public static int length(String cipher)
	{
		int bestLength = -1;
		double closerIC = -1;
		for (int i = 1 ; i <= cipher.length() ; i++)
		{
			String[] messages = Util.split(cipher, i);
			double ic = 0;
			for (int j = 0 ; j < i ; j++)
				ic += Util.indexOfCoincidence(messages[j]);
			ic /= i;
			/*if (Math.abs(ic - Util.frenchIndexOfCoincidence) 
				< Math.abs(closerIC - Util.frenchIndexOfCoincidence))
			{
				System.out.println("i = " + i + " ic = " + ic);
				bestLength = i;
				closerIC = ic;
			}*/
			if (ic > Util.frenchIndexOfCoincidence)
				return i;
		}		
		return bestLength;
	}
	
	public static int[] bruteForceBreak(String cipher, int cycle)
	{
		String[] messages = Util.split(cipher, cycle);
		int[] key = new int[cycle];
		for (int i = 0 ; i < cycle ; i++)
			key[i] = CesarCipher.bruteForceBreak(messages[i]);
		return key;
	}
	
	public static String bruteForceBreak(String cipher)
	{
		int cycle = length(cipher);
		int[] key = bruteForceBreak(cipher, cycle);
		return Util.charOfRank(key);
	}
	
	public static void main(String[] args)
	{
		Random r = new Random();
		String message = "Le calcul de la fréquence des lettres dans une langue est difficile et soumis à interprétation. On compte la fréquence des lettres d’un texte arbitrairement long, mais un certain nombre de paramètres influencent les résultats.",
			key = randomKey(r, r.nextInt(10) + 1),
			cipher = crypt(message, key);
//		System.out.println("key is " + key);
		System.out.println("Ciphered message is : " + cipher);
		key = bruteForceBreak(cipher);
		System.out.println("key is " + key);
		System.out.println("Clear message is : " + decrypt(cipher, key));
	}
}
