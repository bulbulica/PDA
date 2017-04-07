#include "mpi.h" 
#include <stdio.h>
#include <math.h>
#define MAXSIZE 100
int myid, numprocs;
int data[MAXSIZE], i, x, low, high, myresult, subDataLenth, exists;
int subData[MAXSIZE], position[MAXSIZE], searchedNumber = 78;
int main(int argc, char **argv)
{
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
	MPI_Scatter(data, x, MPI_INT, subData, x, MPI_INT, 0, MPI_COMM_WORLD);
	for (i = 0; i < x; i++) 
	{
		if (subData[i] != searchedNumber)
		{
			position[subDataLenth] = MAXSIZE;
		}
		else
		{
			position[subDataLenth] = i + low;
		}
		++subDataLenth;
	}
	MPI_Gather(position, x, MPI_INT, data, x, MPI_INT, 0, MPI_COMM_WORLD);
	if (0 == myid) 
	{
		for (i = 0; i < MAXSIZE; ++i)
		{
			if (data[i] != MAXSIZE)
			{
				++exists;
				break;
			}
		}
		if (exists)
		{
			printf("Number %d found at the positions : ", searchedNumber);
			for (i = 0; i < MAXSIZE; ++i)
			{
				if (data[i] != MAXSIZE)
				{
					printf("%d ", data[i]);
				}
			}
		}
		else
		{
			printf("Number not found");
		}
	}
	MPI_Finalize();
}