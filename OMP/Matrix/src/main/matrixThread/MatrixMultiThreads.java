package matrixThread;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;

public class MatrixMultiThreads
{
  private static long startTime = 0;
  private static long stopTime = 0;
  private static boolean running = false;
  private static int numberOfThreads = 4;
  private int size;

  public void setSize(int size)
  {
    this.size = size;
  }

  private ArrayList<ArrayList<Double>> generateMatrix()
  {
    ArrayList<ArrayList<Double>> a = new ArrayList<ArrayList<Double>>();

    Random random = new Random();
    for (int i = 0; i < size; ++i)
    {
      ArrayList<Double> b = new ArrayList<Double>();
      for (int j = 0; j < size; ++j)
      {
        b.add((double) ((98 + random.nextInt() % 2) / 100));
      }
      a.add(i, b);
    }
    return a;
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

  private void writeInFile(ArrayList<ArrayList<Double>> a) throws IOException
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
    ArrayList<ArrayList<Double>> a = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> b = new ArrayList<ArrayList<Double>>();
    ArrayList<ArrayList<Double>> c = new ArrayList<ArrayList<Double>>();
    ArrayDeque<Integer> sharedQueue = new ArrayDeque<>();
    a = generateMatrix();
    b = generateMatrix();
    for (int i = 0; i < a.get(0).size(); i++)
    {
      sharedQueue.add(i);
    }
    for (int i = 0; i < a.get(0).size(); i++)
    {
      ArrayList<Double> row = new ArrayList<Double>();
      for (int j = 0; j < a.get(0).size(); j++)
      {
        row.add((double) 0);
      }
      c.add(row);
    }
    MatrixMultiplyingTask[] workers = new MatrixMultiplyingTask[numberOfThreads];
    for (int i = 0; i < workers.length; i++)
    {
      workers[i] = new MatrixMultiplyingTask(sharedQueue, a, b, c);
    }
    for (int i = 0; i < workers.length; i++)
    {
      workers[i].start();
    }
    for (int i = 0; i < workers.length; i++)
    {
      try
      {
        workers[i].join();
      }
      catch (InterruptedException e)
      {
        e.printStackTrace();
      }
    }
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
