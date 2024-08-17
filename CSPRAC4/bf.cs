using System;
using System.Diagnostics;
using System.Text;

class BruteForce
{
    private readonly string targetPassword;
    private readonly char[] characters;
    private bool found;

    public BruteForce(string targetPassword)
    {
        this.targetPassword = targetPassword;
        this.characters = new char[127];
        for (int i = 0; i < 127; i++)
        {
            characters[i] = (char)i;
        }
        this.found = false;
    }

    public void CrackPassword()
    {
        Stopwatch stopwatch = Stopwatch.StartNew();
        int length = 1;

        while (!found)
        {
            foreach (var guessPassword in GetCombinations(length))
            {
                if (guessPassword == targetPassword)
                {
                    found = true;
                    Console.WriteLine("Password found: " + guessPassword);
                    return;
                }
            }
            length++;
            double elapsedTime = stopwatch.Elapsed.TotalSeconds;
            double estimatedTime = Math.Pow(characters.Length, length) / 1_000_000.0; // Rough estimate
            Console.WriteLine($"Elapsed time: {elapsedTime:F2}s | Estimated time for next length: {estimatedTime:F2}s");
        }

        Console.WriteLine($"Password '{targetPassword}' cracked successfully.");
    }

    private IEnumerable<string> GetCombinations(int length)
    {
        var indices = new int[length];
        while (true)
        {
            var sb = new StringBuilder(length);
            for (int i = 0; i < length; i++)
            {
                sb.Append(characters[indices[i]]);
            }
            yield return sb.ToString();
            int pos = length - 1;
            while (pos >= 0)
            {
                if (indices[pos] < characters.Length - 1)
                {
                    indices[pos]++;
                    break;
                }
                indices[pos] = 0;
                pos--;
            }
            if (pos < 0)
            {
                yield break;
            }
        }
    }

    static void Main()
    {
        Console.Write("Enter the target password: ");
        string target = Console.ReadLine();
        var bruteForce = new BruteForce(target);
        bruteForce.CrackPassword();
    }
}
