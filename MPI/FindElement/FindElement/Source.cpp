#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

void main(int argc, char* argv[]) {
	int rank, size;
	MPI_Status status;
	MPI_Request request;
	int done, myfound, nvalues;
	int b[100];
	int *sub;
	int i = 0, j, buffer, index;
	FILE *infile = fopen("file.txt", "r");;

	MPI_Init(&argc, &argv);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	myfound = 0;
	if (rank == 0) {
		infile = fopen("file.txt", "r");
		while (fscanf(infile, "%d", &b[i]) != EOF) {
			//printf("%d ", b[i]);
			++i;
		}
		//printf("\n");
	}
	nvalues = 100 / size;
	sub = (int *)malloc(nvalues * sizeof(int));
	MPI_Scatter(b, nvalues, MPI_INT, sub, nvalues, MPI_INT, 0, MPI_COMM_WORLD);
	MPI_Barrier(MPI_COMM_WORLD);  /* Not needed, put in for fun */
	MPI_Irecv(&buffer, 1, MPI_INT, MPI_ANY_SOURCE, 5, MPI_COMM_WORLD, &request);
	MPI_Test(&request, &done, &status);
	i = 0;
	while (!done && i<nvalues) {
		if (sub[i] == 2) {
			buffer = 6;
			for (j = 0; j < size; ++j) {
				MPI_Send(&buffer, 1, MPI_INT, j, 5, MPI_COMM_WORLD);
			}
			printf("Number : %d at index : %d\n", rank, i);
			myfound = 1;
		}
		MPI_Test(&request, &done, &status);
		++i;
	}
	if (!myfound) {
		if (i == 0)
			index = 0;
		else
			index = i - 1;
		printf("Number : %d at index : %d\n", rank, index);
	}
	MPI_Finalize();
}