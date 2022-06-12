#include <stdio.h>
#include <string.h>
#include <stdlib.h>
void search(int maprow,int mapcol,int keyrc,int **map, int keymap[keyrc][keyrc], int row, int col, char* file);
void prt(int midrow,int midcol,int result, char* file);
int main(int argc, char **argv){
	FILE *mapFILE, *keyFILE, *output; /* I define our file variables.*/
	output = fopen(argv[5], "w"); /* I create the output file, all the outputs will be append this file.*/
	fclose(output); /* I close the output file.*/
	char *temp,*rci[3]; /* I define a char array temp and a char 2D array. temp for split the row and col information on argv array.*/
	rci[2] = argv[2]; /* I assign third argument on command which is col and row number of keymap.*/
	int i,j; /* I define i and j for using as a counter.*/
	i=0;
	temp = strtok (argv[1],"x");
	while (temp!= NULL){
		rci[i] = temp;
		temp = strtok(NULL,"x");
		i++;
	}
	/* I split the second argument on command.
	After that rci[0] will store the row number of map rci[1] will store the col number of map and rci[2] will store keymap col number.*/
	int **map=(int **) malloc(atoi(rci[0])*sizeof(int *));
	for(i=0;i<atoi(rci[0]);i++){
   		map[i]=(int *) malloc(atoi(rci[1])*sizeof(int));
	}
	/* I alloced an area for storing map.*/
	int (*keymap)[atoi(rci[2])] = malloc(sizeof(int) * atoi(rci[2]) * atoi(rci[2]));
	/* I alloced an area for storing keymap.*/
	char line[(atoi(rci[1])*10)];
	/* I define a variable line which is hold the lines of files, it is max lenght is row product 10.*/
	i = 0;
	mapFILE = fopen(argv[3], "r");
	/* I open the map file for reading.*/
	while ( fgets ( line, sizeof line, mapFILE ) != NULL ){	
    	if((strlen(line))>=(atoi(rci[0]))){
    		char *temp = strtok(line, " ");
    		while (temp != NULL){
       			map[i][j++] = atoi(temp);
       			temp = strtok (NULL, " ");
    		}
    		i++;
    		j=0;
    	}
	}
	/* I take the map file line by line and split it space by space then convert it to integer and assing it to alloced area.*/
	fclose(mapFILE);
	i = 0;
	keyFILE = fopen(argv[4], "r");
	/* I open the keymap file for reading.*/
	while ( fgets ( line, sizeof line, keyFILE ) != NULL ){
		line [strcspn(line, "\r\n")] = 0;
		if((strlen(line))>=(atoi(rci[2]))){
			char *temp = strtok(line, " ");
			j = 0;
    		while (temp != NULL){
       			keymap[i][j++] = atoi(temp);
       			temp = strtok (NULL, " ");
    		}
    		i++;
		}
	}
	/* I take the keymap file line by line and split it space by space then convert it to integer and assing it to alloced area.*/
	fclose(keyFILE);
	search((atoi(rci[0])),(atoi(rci[1])),(atoi(rci[2])),map,keymap,0,0,argv[5]);
	/* I send the informations for searching treasure.
	The format of it is;
	search(Total Row,Total Col,Keymap Row,Address of Map,Address of Keymap,Starting posion of searching on row,Starting posion of searching on col,Output File)*/
	for (i = 0; i < (atoi(rci[0])); ++i){
		free(map[i]);
	}
	free(map);
	free(keymap);
	/* At the end of program I made free all the areas I alloced.*/
}
void search(int maprow,int mapcol,int keyrc,int **map, int keymap[keyrc][keyrc], int row, int col, char* file){
	int result = 0, mod = 0;
	int i,j;
	for(i = (0); i < keyrc; ++i){
		for (j = (0); j < keyrc; ++j){
			result = result + (map[i+row][j+col] * keymap[i][j]);
		}
	}
	/* I make the dot product of the matching cells, and sum up them to result variable.*/
	int oprt = ((keyrc-1)/2);
	/* I make a formula for print the middle cell of the map.*/
	mod = result%5;
	/* I make the mod operation.*/
	/* I make a switch-case block for checking mod variable.*/
	switch (mod){
		case 0:
			prt((row+oprt),(col+oprt),result,file);
			/* I send the middle point on the matching area and result to prt function which is print it to output file.*/
		break;
		case 1:
			prt((row+oprt),(col+oprt),result,file);
			/* I send the middle point on the matching area and result to prt function which is print it to output file.*/
			if ((row-keyrc)>=0){
				search(maprow,mapcol,keyrc,map,keymap,row-keyrc,col,file);
			}else{
				search(maprow,mapcol,keyrc,map,keymap,row+keyrc,col,file);
			}
			/* I check the next position is on the map or out of the map, if out of the map, I send the reverse direction.*/
		break;
		case 2:
			prt((row+oprt),(col+oprt),result,file);
			/* I send the middle point on the matching area and result to prt function which is print it to output file.*/
			if ((row+keyrc)>=maprow){
				search(maprow,mapcol,keyrc,map,keymap,row-keyrc,col,file);
			}else{
				search(maprow,mapcol,keyrc,map,keymap,row+keyrc,col,file);
			}
			/* I check the next position is on the map or out of the map, if out of the map, I send the reverse direction.*/
		break;
		case 3:
			prt((row+oprt),(col+oprt),result,file);
			/* I send the middle point on the matching area and result to prt function which is print it to output file.*/
			if ((col+keyrc)>=mapcol){
				search(maprow,mapcol,keyrc,map,keymap,row,col-keyrc,file);
			}else{
				search(maprow,mapcol,keyrc,map,keymap,row,col+keyrc,file);
			}
			/* I check the next position is on the map or out of the map, if out of the map, I send the reverse direction.*/
		break;
		case 4:
			prt((row+oprt),(col+oprt),result,file);
			/* I send the middle point on the matching area and result to prt function which is print it to output file.*/
			if ((col-keyrc)>=0){
				search(maprow,mapcol,keyrc,map,keymap,row,col-keyrc,file);
			}else{
				search(maprow,mapcol,keyrc,map,keymap,row,col+keyrc,file);
			}
			/* I check the next position is on the map or out of the map, if out of the map, I send the reverse direction.*/
		break;
	}
}
void prt(int midrow,int midcol,int result, char* file){
	FILE *output;
	output = fopen(file, "a");
	fprintf(output, "%d,%d:%d\n", midrow,midcol,result);
	fclose(output);
	/* I open the output file for append, then I write the line then I close the file.*/
}