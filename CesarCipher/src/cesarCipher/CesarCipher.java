package cesarCipher;

import java.util.Random;

public class CesarCipher
{
	private static double[] frenchFrequencies =
		{	0.07636, //a
			0.00901, //b
			0.0326, //c
			0.03669, //d
			0.14715, //e
			0.01066, //f
			0.00866, //g
			0.00737, //h
			0.07529, //i
			0.00545, //j
			0.00049, //k
			0.05456, //l
			0.02968, //m
			0.07095, //n
			0.05378, //o
			0.03021, //p
			0.01362, //q
			0.06553, //r
			0.07948, //s
			0.07244, //t
			0.06311, //u
			0.01628, //v
			0.00114, //w
			0.00387, //x
			0.00308, //y
			0.00136 //z
		};
		
		
	public static char charOfInt(int x)
	{
		return (char) x;
	}

	public static int rankOfChar(char a)
	{
		if (isLowerCase(a))
			return a - 'a';
		return a - 'A';
	}

	public static char charOfRank(int r)
	{
		return charOfInt(r + 'a');
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
			return charOfInt(c - 'A' + 'a');
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

	public static int[] effectives(String s)
	{
		int[] effectifs = new int[26];
		for (int i = 0 ; i < s.length() ; i++)
		{
			char letter = s.charAt(i); 
			if (isALetter(letter))
				effectifs[rankOfChar(letter)]++;
		}
		return effectifs;
	}
	
	public static int nbLetters(String message)
	{
		int nb = 0;
		for (int i = 0 ; i < message.length() ; i++)
			if (isALetter(message.charAt(i)))
				nb++;
		return nb;
	}
	
	
	public static double[] getFrenchEffectives(String message)
	{
		double[] frenchEffectives = new double[26];
		double nb = nbLetters(message);
		for (int i = 0 ; i < 26 ; i++)
			frenchEffectives[i] = frenchFrequencies[i] * nb; 
		return frenchEffectives;
	}
	
	public static double getSquaredChi(int[] observedEffectives, double[] frenchEffectives)
	{
		double sum = 0;
		for (int i = 0 ; i < 26 ; i++)
		{
			double numerator = (observedEffectives[i] - frenchEffectives[i]);
			numerator *= numerator;
			sum += numerator / frenchEffectives[i];
		}
		return sum;
	}
	
	public static int bruteForceBreak(String s)
	{
		double[] frenchEffectives =  getFrenchEffectives(s);
		int minKey = -1;
		double minSquaredChi = -1;
		for (int key = 0 ; key <= 25 ; key++)
		{
			String dec = decrypt(s, key);
			double squaredChi = getSquaredChi(effectives(dec), frenchEffectives);
			System.out.println("key = " + key + " , " +
					"(chi2 = " + squaredChi  + ") :  "
					+ dec );
			if (minKey == -1 || squaredChi < minSquaredChi)
			{
				minSquaredChi = squaredChi;
				minKey = key;
			}
		}
		return minKey;
	}

	public static String stringOfIntTab(int[] tab)
	{
		String s = "";
		for (int i = 0 ; i < tab.length ; i++)
			s += charOfRank(i) + " : " + tab[i] + "\n";
		return s;
	}
		
	public static void main(String[] args)
	{
		Random r = new Random();
		int key = r.nextInt(26);
		String message = "Le calcul de la fréquence des lettres dans une langue est difficile et soumis à interprétation. On compte la fréquence des lettres d’un texte arbitrairement long, mais un certain nombre de paramètres influencent les résultats.", 
				cipher = crypt(message, key); 
		System.out.println("Ciphered message is : " + cipher);
		int minKey = bruteForceBreak(cipher);
		System.out.println("Key is : " + minKey);
		System.out.println("Original message is : " + decrypt(cipher, minKey));
	}
}
