package substitutionCipher;

import java.util.Random;
import static substitutionCipher.Util.*; 

public class CesarCipher
{
	public static String crypt(String s, int key)
	{
		String res = "";
		for (int i = 0; i < s.length(); i++)
			res += Util.crypt(s.charAt(i), key);
		return res;
	}

	public static String decrypt(String s, int key)
	{
		return crypt(s, reverseKey(key));
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
//			System.out.println("key = " + key + " , " +
//					"(chi2 = " + squaredChi  + ") :  "
//					+ dec );
			if (minKey == -1 || squaredChi < minSquaredChi)
			{
				minSquaredChi = squaredChi;
				minKey = key;
			}
		}
		return minKey;
	}

	public static void main(String[] args)
	{
		Random r = new Random();
		String message = "Le calcul de la fréquence des lettres dans une langue est difficile et soumis à interprétation. On compte la fréquence des lettres d’un texte arbitrairement long, mais un certain nombre de paramètres influencent les résultats.", 
				cipher = crypt(message, r.nextInt(26)); 
		System.out.println("Ciphered message is : " + cipher);
		int key = bruteForceBreak(cipher);
		System.out.println("Key is : " + key);
		System.out.println("Original message is : " + decrypt(cipher, key));
	}
}
