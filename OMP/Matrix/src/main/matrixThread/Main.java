package matrixThread;

public class Main
{
  public static void main(String[] args)
  {
    BenchmarkThreadsMatrix.timeMultiplyMatrix500();
    BenchmarkThreadsMatrix.timeMultiplyMatrix1000();
    BenchmarkThreadsMatrix.timeMultiplyMatrix1500();
    BenchmarkThreadsMatrix.timeMultiplyMatrix2000();
    BenchmarkThreadsMatrix.timeMultiplyMatrix2500();
  }
}
