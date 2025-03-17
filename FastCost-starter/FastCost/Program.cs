using System;
using System.Diagnostics;
using System.IO;

class Program
{
    static void Main(string[] args)
    {
        string fileArgument = args.Length > 0 ? $" \"{args[0].Replace("\\", "/")}\"" : "";
        string command = $"java -jar FastCost.jar{fileArgument}";

        var startInfo = new ProcessStartInfo
        {
            FileName = "cmd.exe",
            Arguments = $"/c {command}",
            UseShellExecute = false,
            CreateNoWindow = true,
            WorkingDirectory = AppDomain.CurrentDomain.BaseDirectory
        };

        Process.Start(startInfo);
    }
}
