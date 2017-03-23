package matrix;

import java.util.Random;

public class Matrix
{
  private static long startTime = 0;
  private static long stopTime = 0;
  private static boolean running = false;
  private static int size = 500;

  public static double[][] multiply(double[][] a, double[][] b)
  {
    double[][] c = new double[a.length][b[0].length];
    for (int i = 0; i < a.length; i++)
    {
      for (int j = 0; j < b[0].length; j++)
      {
        for (int k = 0; k < a[0].length; k++)
        {
          c[i][j] += a[i][k] * b[k][j];
        }
      }
    }
    return c;
  }

  public void startTimer()
  {
    startTime = System.currentTimeMillis();
    running = true;
  }

  public void stopTimer()
  {
    stopTime = System.currentTimeMillis();
    running = false;
  }

  public long getElapsedTime()
  {
    long elapsed;
    if (running)
    {
      elapsed = (System.currentTimeMillis() - startTime);
    }
    else
    {
      elapsed = (stopTime - startTime);
    }
    return elapsed;
  }

  public static double[][] generateMatrix(int size)
  {
    double[][] a = new double[size][size];
    Random random = new Random();
    for (int i = 0; i < size; ++i)
    {
      for (int j = 0; j < size; ++j)
      {
        a[i][j] = (double) (98 + random.nextInt() % 2) / 100;
      }
    }
    return a;
  }

  public void startProgram()
  {
    double[][] a = generateMatrix(size);
    double[][] b = generateMatrix(size);
    double[][] result = multiply(a, b);
//    for (int i = 0; i < size; i++)
//    {
//      for (int j = 0; j < size; j++)
//      {
//        System.out.print(result[i][j] + " ");
//      }
//      System.out.println();
//    }
  }
}