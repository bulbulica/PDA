package matrix;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class MatrixOMP
{
  private static long startTime = 0;
  private static long stopTime = 0;
  private static boolean running = false;
  private int size = 500;

  public void setSize(int size)
  {
    this.size = size;
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

  public ArrayList<ArrayList<Integer>> multiply(ArrayList<ArrayList<Integer>> a, ArrayList<ArrayList<Integer>> b, ArrayDeque<Integer> sharedQueue)
  {
    ArrayList<ArrayList<Integer>> c = new ArrayList<ArrayList<Integer>>();
    for (int i = 0; i < a.get(0).size(); i++)
    {
      ArrayList<Integer> row = new ArrayList<Integer>();
      for (int j = 0; j < a.get(0).size(); j++)
      {
        row.add(0);
      }
      c.add(row);
    }

    for (int i = 0; i < a.get(0).size(); i++)
    {
      sharedQueue.add(i);
    }
    // omp parallel ThreadNum(4)
    {
      while (!sharedQueue.isEmpty())
      {
        // omp critical
        int x = sharedQueue.removeFirst();

        for (int i = 0; i < b.get(0).size(); i++)
        {
          for (int j = 0; j < a.get(0).size(); j++)
          {
            c.get(x).set(i, c.get(x).get(i) + a.get(x).get(j) * b.get(j).get(i));
          }
        }
      }
    }
    return c;
  }

  private ArrayList<ArrayList<Integer>> generateMatrix()
  {
    ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();

    Random random = new Random();
    for (int i = 0; i < size; ++i)
    {
      ArrayList<Integer> b = new ArrayList<Integer>();
      for (int j = 0; j < size; ++j)
      {
        b.add((98 + random.nextInt() % 2));
      }
      a.add(i, b);
    }
    return a;
  }

  private void writeInFile(ArrayList<ArrayList<Integer>> a) throws IOException
  {
    Writer wr = new FileWriter("output.txt");
    for (ArrayList<?> item : a)
    {
      for (Object item2 : item)
      {
        wr.write(item2.toString() + " ");
      }
      wr.write(" ");
    }
    wr.close();
  }

  public void startProgram()
  {
    ArrayList<ArrayList<Integer>> a = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> b = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> c = new ArrayList<ArrayList<Integer>>();
    ArrayDeque<Integer> sharedQueue = new ArrayDeque<>();
    a = generateMatrix();
    b = generateMatrix();
    c = multiply(a, b, sharedQueue);
    try
    {
      writeInFile(c);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
