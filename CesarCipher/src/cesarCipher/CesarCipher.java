package cesarCipher;

public class CesarCipher
{
	private static double[] frenchFrequencies =
		{7.636, //a
		0.901, //b
		3.26, //c
		3.669, //d
		14.715, //e
		1.066, //f
		0.866, //g
		0.737, //h
		7.529, //i
		0.545, //j
		0.049, //k
		5.456, //l
		2.968, //m
		7.095, //n
		5.378, //o
		3.021, //p
		1.362, //q
		6.553, //r
		7.948, //s
		7.244, //t
		6.311, //u
		1.628, //v
		0.114, //w
		0.387, //x
		0.308, //y
		0.136 //z
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
	
	
	public static double[] frenchEffectives(String message)
	{
		double[] fe = new double[26];
		double nb = nbLetters(message);
		for (int i = 0 ; i < 26 ; i++)
			fe[i] = frenchFrequencies[i] * nb / 100; 
		return fe;
	}
	
	public static double chiSquared(int[] observed, double[] theorical)
	{
		double sum = 0;
		for (int i = 0 ; i < 26 ; i++)
		{
			double numerator = (observed[i] - theorical[i]);
			numerator *= numerator;
			sum += numerator / theorical[i];
		}
		return sum;
	}
	
	public static void bruteForceBreak(String s)
	{
		double[] fe =  frenchEffectives(s);
		for (int key = 1 ; key <= 25 ; key++)
		{
			String dec = decrypt(s, key);
			System.out.println("key = " + key + " , " +
					"(chi2 = " + chiSquared(effectives(dec), fe) + ") :  "
					+ dec );
			
		}
	}

	public static String stringOfIntTab(int[] tab)
	{
		String s = "";
		for (int i = 0 ; i < tab.length ; i++)
			s += charOfRank(i) + " : " + tab[i] + "\n";
		return s;
	}
	
	public static String stringOfDoubleTab(double[] tab)
	{
		String s = "";
		for (int i = 0 ; i < tab.length ; i++)
			s += charOfRank(i) + " : " + tab[i] + "\n";
		return s;
	}
	
	public static void main(String[] args)
	{
		String message = "Le calcul de la fréquence des lettres dans une langue est difficile et soumis à interprétation. On compte la fréquence des lettres d’un texte arbitrairement long, mais un certain nombre de paramètres influencent les résultats.", 
				cipher = crypt(message, 6), 
				result = decrypt(cipher, 6);
		System.out.println(message);
		System.out.println(cipher);
		System.out.println(result);
		bruteForceBreak(cipher);
	}
}
