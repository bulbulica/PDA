package matrix;

public class BenchmarkSimpleMatrix
{
  private static Matrix matrix = new Matrix();

  public static void timeMultiplyMatrix500()
  {
    matrix.setSize(500);
    matrix.startTimer();
    matrix.startProgram();
    matrix.stopTimer();
    System.out.println("Test for 500 : " + matrix.getElapsedTime());
  }

  public static void timeMultiplyMatrix1000()
  {
    matrix.setSize(1000);
    matrix.startTimer();
    matrix.startProgram();
    matrix.stopTimer();
    System.out.println("Test for 1000 : " + matrix.getElapsedTime());
  }

  public static void timeMultiplyMatrix1500()
  {
    matrix.setSize(1500);
    matrix.startTimer();
    matrix.startProgram();
    matrix.stopTimer();
    System.out.println("Test for 1500 : " + matrix.getElapsedTime());
  }

  public static void timeMultiplyMatrix2000()
  {
    matrix.setSize(2000);
    matrix.startTimer();
    matrix.startProgram();
    matrix.stopTimer();
    System.out.println("Test for 2000 : " + matrix.getElapsedTime());
  }

  public static void timeMultiplyMatrix2500()
  {
    matrix.setSize(2500);
    matrix.startTimer();
    matrix.startProgram();
    matrix.stopTimer();
    System.out.println("Test for 2500 : " + matrix.getElapsedTime());
  }
}
