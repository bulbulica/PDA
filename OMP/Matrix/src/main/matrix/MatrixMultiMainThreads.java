package matrix;

import java.util.Random;

public class MatrixMultiMainThreads
{

  private static int size = 500;
  private static int numberOfThreads = 10;
  private static double[][] matrixAs = new double[size][size];
  private static double[][] matrixBs = new double[size][size];

  public static void main(String[] args)
  {
    Matrix matrix = new Matrix();
    matrix.startTimer();
    matrix.startTimer();
    startProgram();
    matrix.stopTimer();
    System.out.println(matrix.getElapsedTime());
  }

  private static double[][] multMatrixWithThreadsSync(double[][] matrixA, double[][] matrixB)
  {
    double[][] matrixProduct = new double[matrixA.length][matrixB[0].length];
    MatrixMultiplyingTask.Concurrency context = new MatrixMultiplyingTask.Concurrency(matrixProduct.length);
    Runnable task = new MatrixMultiplyingTask(context, matrixA, matrixB, matrixProduct);
    Thread[] workers = new Thread[numberOfThreads];
    for (int i = 0; i < workers.length; i++)
    {
      workers[i] = new Thread(task, "Worker - " + i);
    }
    for (int i = 0; i < workers.length; i++)
    {
      Thread worker = workers[i];
      worker.start();
    }
    for (int i = 0; i < workers.length; i++)
    {
      Thread worker = workers[i];
      try
      {
        worker.join();
      }
      catch (InterruptedException ex)
      {
      }
    }
    return matrixProduct;
  }

  private static void generateMatrix(double[][] matrixA)
  {
    Random rand = new Random();
    for (int i = 0; i < matrixA.length; i++)
    {
      for (int j = 0; j < matrixA[0].length; j++)
      {
        matrixA[i][j] = (double) (98 + rand.nextInt() % 2) / 100;
      }
    }
  }

  private static void startProgram()
  {
    generateMatrix(matrixAs);
    generateMatrix(matrixBs);
    double[][] matrixProduct3 = multMatrixWithThreadsSync(matrixAs, matrixBs);
//    for (int i = 0; i < size; i++)
//    {
//      for (int j = 0; j < size; j++)
//      {
//        System.out.print(matrixProduct3[i][j] + " ");
//      }
//      System.out.println();
//    }
  }
}