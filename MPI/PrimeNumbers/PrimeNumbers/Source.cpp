#include "mpi.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define NMAX 1000     

bool isPrime(int number) 
{
	if (number < 2) 
		return 0;
	for (int i = 2; i * i <= number; ++i)
		if (number % i == 0)
			return 0;
	return 1;
}

int main(int argc, char *argv[])
{
	int	tasks, rank, start, step;               
	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &tasks);
	start = (rank * 2) + 1;       
	step = tasks * 2;          
	if (rank == 0) 
		printf("2 ");
	for (int i = start; i <= NMAX; i += step) 
		if (isPrime(i)) 
			printf("%d ", i);
	MPI_Finalize();
}