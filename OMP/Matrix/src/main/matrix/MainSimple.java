package matrix;

public class MainSimple
{
  public static void main(String[] args)
  {
    BenchmarkSimpleMatrix.timeMultiplyMatrix500();
    BenchmarkSimpleMatrix.timeMultiplyMatrix1000();
    BenchmarkSimpleMatrix.timeMultiplyMatrix1500();
    BenchmarkSimpleMatrix.timeMultiplyMatrix2000();
    BenchmarkSimpleMatrix.timeMultiplyMatrix2500();
  }
}
