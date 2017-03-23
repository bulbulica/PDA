package matrix;

public class MainSimple
{
  public static void main(String[] args)
  {
    Matrix matrix = new Matrix();
    matrix.startTimer();
    matrix.startProgram();
    matrix.stopTimer();
    System.out.println(matrix.getElapsedTime());
  }
}
