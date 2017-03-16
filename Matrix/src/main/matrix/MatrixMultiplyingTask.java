package matrix;

public class MatrixMultiplyingTask implements Runnable
{

  private double[][] A;
  private double[][] B;
  private double[][] C;
  private final Concurrency context;

  public MatrixMultiplyingTask(Concurrency context, double[][] A, double[][] B, double[][] C)
  {
    if (context == null)
    {
      throw new IllegalArgumentException("error");
    }
    this.context = context;
    this.A = A;
    this.B = B;
    this.C = C;
  }

  @Override
  public void run()
  {
    while (true)
    {
      int row;
      synchronized (context)
      {
        if (context.isFullyProcessed())
        {
          break;
        }
        row = context.nextRowNum();
      }
      // System.out.println(Thread.currentThread().getName() + " for row " + row);
      for (int j = 0; j < B[0].length; j++)
      {
        for (int k = 0; k < A[0].length; k++)
        {
          C[row][j] += A[row][k] * B[k][j];
        }
      }
    }
  }

  public static class Concurrency
  {
    private final int rowCount;
    private int nextRow = 0;

    public Concurrency(int rowCount)
    {
      this.rowCount = rowCount;
    }

    public synchronized int nextRowNum()
    {
      if (isFullyProcessed())
      {
        throw new IllegalStateException("Raw processed");
      }
      return nextRow++;
    }

    public synchronized boolean isFullyProcessed()
    {
      return nextRow == rowCount;
    }
  }
}