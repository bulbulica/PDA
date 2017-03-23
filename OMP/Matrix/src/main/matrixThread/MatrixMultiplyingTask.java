package matrixThread;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class MatrixMultiplyingTask extends Thread
{
  private ArrayDeque<Integer> sharedQueue;
  private ArrayList<ArrayList<Double>> a;
  private ArrayList<ArrayList<Double>> b;
  private ArrayList<ArrayList<Double>> c;
  private int x = 0;

  public MatrixMultiplyingTask(ArrayDeque<Integer> sharedQueue, ArrayList<ArrayList<Double>> a, ArrayList<ArrayList<Double>> b,
      ArrayList<ArrayList<Double>> c)
  {
    this.sharedQueue = sharedQueue;
    this.a = a;
    this.b = b;
    this.c = c;
  }

  @Override
  public void run()
  {
    while (!sharedQueue.isEmpty())
    {
      synchronized (sharedQueue)
      {
        x = sharedQueue.removeFirst();
      }
      for (int i = 0; i < b.get(0).size(); i++)
      {
        for (int j = 0; j < a.get(0).size(); j++)
        {
          c.get(x).set(i, c.get(x).get(i) + a.get(x).get(j) * b.get(j).get(i));
        }
      }
    }
  }
}
