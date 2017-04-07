#include "mpi.h" 
#include <stdio.h>
#include <math.h>
#define MAXSIZE 100
int main(int argc, char **argv)
{
	int myid, numprocs;
	int data[MAXSIZE], i, x, low, high, myresult = 0, lastPosition = MAXSIZE + 1, position = MAXSIZE + 1, searchedNumber = 78;
	FILE *fp;
	MPI_Init(&argc, &argv);
	MPI_Comm_size(MPI_COMM_WORLD, &numprocs);
	MPI_Comm_rank(MPI_COMM_WORLD, &myid);
	if (0 == myid) 
	{
		/* open input file and intialize data */
		if (NULL == (fp = fopen("file.txt", "r"))) 
		{
			printf("Can't open the input file.");
			return 0;
		}
		for (i = 0; i < MAXSIZE; ++i) 
		{
			fscanf(fp, "%d", &data[i]);
		}
	}
	/* broadcast data */
	MPI_Bcast(data, MAXSIZE, MPI_INT, 0, MPI_COMM_WORLD);
	/* add portion of data */
	x = MAXSIZE / numprocs;   /* must be an integer */
	low = myid * x;
	high = low + x;
	for (i = low; i < high; i++) 
	{
		if (data[i] == searchedNumber)
		{
			position = i;
			printf("Process %d found it at %d\n", myid, position + 1);
		}
	}
	/* compute global sum */
	MPI_Reduce(&position, &lastPosition, 1, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);
	if (0 == myid) 
	{
		if (lastPosition != 101)
		{
			printf("Number %d found at the position %d", searchedNumber, lastPosition);
		}
		else
		{
			printf("Number not found");
		}
	}
	MPI_Finalize();
}