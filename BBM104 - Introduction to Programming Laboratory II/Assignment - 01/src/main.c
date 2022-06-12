#include <stdio.h>
#include <string.h>
#include <stdlib.h>
char **Loadmap(char *line,int *row,int *col);
void Put (char **map,char *line);
struct characters{
	char type[50],name[50];
	int hp,damage,xp;
};
void Move (char **map,char *line,char *argv,struct characters character[],int x,int y,int Counter);
void Show (char **map,char *line,char *argv,struct characters character[],int x,int y,int Counter);
void Attack (char **map,char *line,char *argv,int x,int y,int Counter,struct characters character[]);
int Final (struct characters character[],int Counter,char *argv,char **map);
int main(int argc, char **argv){
	/* Construct characters struct with the values given in the input file */
	int number_of_character = 0;
	FILE *fileWrt = fopen(argv[3], "w"); 
	FILE *file_number; /* Our struct must be dynamic for this reason firstly we must open the file for get the number of characters. */
	if ((file_number=fopen(argv[1],"r"))==NULL)
		fprintf(fileWrt,"File could not open, maybe the file does not exist.");
	else{
		char line[900]; /* 900 is represent the maximum characters in a line. */
		while ( fgets ( line, sizeof line, file_number ) != NULL ){ /* We take the file line by line until reach the file end. */
    		number_of_character++;
    	}
	}
    fclose ( file_number );
    /* Now we known the number of characters in chars_<input number>.txt therefore we can define the size of our struct. */
	struct characters *cPtr,*character=(struct characters *)malloc(sizeof(struct characters)*number_of_character);
	cPtr = character;
    /* Purpose of the using a pointer which is correspond the struct is access the struct and change it in a function. */
    /* Now we will send the characters which is from input file to our struct. */
	int Counter=0;
	FILE *filePtr;
	if ((filePtr=fopen(argv[1],"r"))==NULL)
		fprintf(fileWrt,"File could not open, maybe the file does not exist.");
	else{
		char line [ 900 ]; /* 900 is represent the maximum characters in a line. */
      while ( fgets ( line, sizeof line, filePtr ) != NULL )
      {
        char *type = strtok(line, ",");
        char *name = strtok(NULL, ",");
        char *hp = strtok(NULL, ",");
        char *damage = strtok(NULL, ",");
        strcpy(character[Counter].type, type);
        strcpy(character[Counter].name, name);
        character[Counter].hp=atoi(hp);
        character[Counter].damage=atoi(damage);
        character[Counter].xp=0;
        Counter++;
    }
      fclose ( filePtr );
	}
	/*Now our work done on chars_1.txt, after now we will upload our command text file and process the file line by line. */
	FILE *file_twoPtr;
	if ((file_twoPtr=fopen(argv[2],"r"))==NULL)
		fprintf(fileWrt,"File could not open, maybe the file does not exist.");
	else{
		char line [ 900 ]; /* 900 is represent the maximum characters in a line. */
		char **map;
		int x,y,control,for_free;
      while ( fgets ( line, sizeof line, file_twoPtr ) != NULL ){ /* We take the file line by line until reach the file end. */
      	switch(line[0]) {
            /* We use switch in here because every command has differnt start char, there is no diffrent use if and strcmp() and use switch.*/
			case 'L'  :
      			map=Loadmap(line,&x,&y);
      		break;
			case 'P'  :
      			Put(map,line);
      		break;
      		case 'S'  :
      			Show(map,line,argv[3],character,x,y,Counter);
      		break;
      		case 'M'  :
      			Move(map,line,argv[3],character,x,y,Counter);
      		break;
      		case 'A'  :
      			Attack(map,line,argv[3],x,y,Counter,cPtr);
      			control=Final(character,Counter,argv[3],map);
                if (control==0){
                    for (for_free = 0; for_free < x; ++for_free) {
                        free(map[for_free]);
                    }
                    free(map);
                    free(character);
                    fclose ( fileWrt );
                    fclose ( file_twoPtr );
                    exit(0);
                }
      		break;
			}
      	
      }
      for (for_free = 0; for_free < x; ++for_free) {
          free(map[for_free]);
      }
      free(map);
      free(character);
  }
  	  fclose ( fileWrt );
      fclose ( file_twoPtr );
	return 0;
}
char **Loadmap(char *line,int *row,int *col){
    /* Loadmap is a function which is define an array dynamicly. As you known loadmap commands like this "LOADMAP XX XX" therefore we split the line
    which is carry the loadmap command in input file. */
	char *type = strtok(line, " "); /* It holds LOADMAP */
    char *x = strtok(NULL, " "); /* It holds row number of given by in input file. */
    char *y = strtok(NULL, " "); /* It holds column number of given by in input file. */
    *row = atoi(x); *col = atoi(y); /* Now we convert row and col numbers integer from char array. */
    int i,j;
	char **map=(char **) malloc(atoi(x)*sizeof(char *)); /* We define a two dimensional array which will hold the all map. */
	for(i=0;i<atoi(x);i++)
   		map[i]=(char *) malloc(atoi(y)*sizeof(char));
	for (i = 0; i < atoi(x); i++ ) {
		for (j = 0; j < atoi(y); j++ ) {
    		map[i][j]='.'; /* Initialize '.' data which is default. */
    	}
    }
    return map;
}
void Put (char **map,char *line){
    /* Defines the positions of the characters. */
    int i = 0,len = strlen(line);
    char *temp = strtok (line, " ");
    char *words[len];
    /* The command line of put command is "PUT <character_type> <character_name> <character_row> <character_col>..." therefore we can not now
    the number of elements but it can not greather than lenght of line, so we can define an char array and its size is lenght of line. */
	while (temp != NULL){
        words[i++] = temp;
        temp = strtok (NULL, " ");
    }
    len = i; /* Now len holds the tokens number of line. */
    for (i = 2; i < len; i=i+3){
        /* We assign i to 2 because words[0] holds PUT words[1] holds MONSTERS or HEROES.
        We increase i theree because every characters begin every third elements in array.
        Words[i+1] will hold row position of character.
        Words[i+2] will hold col position of character.
        Words[i] will hold the name of character.*/
    	map[atoi(words[i+1])][atoi(words[i+2])]=words[i][0];
    }
}
void Show (char **map,char *line,char *argv,struct characters character[],int x,int y,int Counter){
    /* The purpose of this function is showing all the map or monsters status or heroes status. */
	char *show = strtok(line, " "); /* It holds "SHOW". */
    char *type = strtok(NULL, " "); /* It holds "MAP" or "MONSTERS" or "HEROES"*/
    int i,j;
    FILE *fileWrt = fopen(argv, "a");
    if(strcmp(type,"MAP\n")==0){
    	fprintf(fileWrt,"MAP STATUS\n");
    	for (i = 0; i < x; i++ ) {
			for (j = 0; j < y; j++ ) {
				fprintf(fileWrt,"%c ", map[i][j]);
			}
			fprintf(fileWrt,"\n");
		}
		fprintf(fileWrt,"\n");
    }else if (strcmp(type,"HERO\n")==0){
    	fprintf(fileWrt,"HERO STATUS\n");
    	for (i = 0; i < Counter; ++i)
    	{
    		if (strcmp(character[i].type,"HERO")==0)
    		{
    			fprintf(fileWrt,"%s HP: %d XP: %d\n", character[i].name,character[i].hp,character[i].xp);
    		}
    	}
    	fprintf(fileWrt,"\n");
    }else if (strcmp(type,"MONSTER\n")==0){
    	fprintf(fileWrt,"MONSTER STATUS\n");
    	for (i = 0; i < Counter; ++i)
    	{
    		if (strcmp(character[i].type,"MONSTER")==0)
    		{
    			fprintf(fileWrt,"%s HP: %d\n", character[i].name,character[i].hp);
    		}
    	}
    	fprintf(fileWrt,"\n");
    }
    fclose ( fileWrt );
}
void Move (char **map,char *line,char *argv,struct characters character[],int x,int y,int Counter){
    /* The purpose of this function is move heroes to new position. */
	int i = 0,j,array_x,array_y,len = strlen(line), new_row,new_col;
    char *temp = strtok (line, " ");
    char *words[len];
    FILE *fileWrt = fopen(argv, "a");
	while (temp != NULL){
        words[i++] = temp;
        temp = strtok (NULL, " ");
    }
    len = i;
    fprintf(fileWrt,"HEROES MOVED\n");
    for (i = 2; i < len; i=i+3){
    	for (j = 0; j < Counter; ++j){
    		if (words[i][0]==character[j].name[0]){
    			new_row = atoi(words[i+1]);
    			new_col = atoi(words[i+2]);
    			if (character[j].hp < 1){
    				fprintf(fileWrt,"%s can't move. Dead.\n",character[j].name);
    			}else if((new_row >= x)||(new_col >= y)) {
    				fprintf(fileWrt,"%s can't move. There is a wall.\n",character[j].name);
    			}else if (map[new_row][new_col]=='.'){
                    /* If new position is suitable we must delete old position therefore we must find the position of character.*/
    				for (array_x = 0; array_x < x; ++array_x){
    					for (array_y = 0; array_y < y; ++array_y){
    						if (map[array_x][array_y]==character[j].name[0]){
    							map[array_x][array_y]='.';
    						}
    					}
    				}
    				map[new_row][new_col]=character[j].name[0];
    			}else{
    				fprintf(fileWrt,"%s can't move. Place is occupied.\n",character[j].name);
    			}
    		}
    	}
    }
    fprintf(fileWrt,"\n");
    fclose ( fileWrt );
}
void Attack (char **map,char *line,char *argv,int x,int y,int Counter,struct characters character[]){
    /* The purpose of this function is attack the rival. */
	char *command_type = strtok (line, " ");
	char *character_type = strtok (NULL, " ");
	int j,g,h,i,array_x,array_y; /* i,j,g,h are counter for the loop. */
	FILE *fileWrt = fopen(argv, "a");
	if (character_type[0]=='M'){ /* We check the type of character. */
		for (j = 0; j < Counter; ++j){ /* The purpose of this loop is get the informatiton of character. */
			if ((character[j].type[0]=='M')&&(character[j].hp>0)){
				int x_of_char = 0, y_of_char = 0;;
				for (array_x = 0; array_x < x; ++array_x){
    				for (array_y = 0; array_y < y; ++array_y){
    					if (map[array_x][array_y]==character[j].name[0]){
    						x_of_char = array_x;
    						y_of_char = array_y;
    					}}}
                /* Characters only attack neighboor cells therefor we must check the neighboors of attacking character. */
    			for (g = x_of_char-1; g <= x_of_char+1; ++g){
    				if ((g<0)||(g>=x)){ 
                    /* The purpose of this if is check the row number because there is no row less than 0 and greather or
                    equal to row number which is given in input file*/
    					continue;
					}else{
						for (h = y_of_char-1; h <= y_of_char+1; ++h){
							if ((h<0)||(h>=y)){
                                /* The purpose of this if is check the col number because there is no col less than 0 and greather or
                                equal to col number which is given in input file*/
    							continue;
							}else{
								if (map[g][h]!='.'){ /* We check the cell for it is empty or not. */
									for (i = 0; i < Counter; ++i){
										if ((character[i].name[0]==map[g][h])&&(character[i].type[0]!='M')){
                                            /* There is no friendly fire therefore each character must attack rival we check this. */
											character[i].hp=character[i].hp - character[j].damage;
                                            /* We decrease the character.*/
											if (character[i].hp<1){
                                                /* Now if the character dead we must delete it from map. We check it here. */
												map[g][h]='.';
												character[i].hp=0;
											}}}}}}}}}}
		fprintf(fileWrt,"MONSTERS ATTACKED\n");
	}else{
		for (j = 0; j < Counter; ++j){
			if ((character[j].type[0]=='H')&&(character[j].hp>0)){
				int x_of_char = 0, y_of_char = 0;;
				for (array_x = 0; array_x < x; ++array_x){
    				for (array_y = 0; array_y < y; ++array_y){
    					if (map[array_x][array_y]==character[j].name[0]){
    						x_of_char = array_x;
    						y_of_char = array_y;
    					}}}
    			for (g = x_of_char-1; g <= x_of_char+1; ++g){
    				if ((g<0)||(g>=x)){
    					continue;
					}else{
						for (h = y_of_char-1; h <= y_of_char+1; ++h){
							if ((h<0)||(h>=y)){
    							continue;
							}else{
								if (map[g][h]!='.'){
									for (i = 0; i < Counter; ++i){
										if ((character[i].name[0]==map[g][h])&&(character[i].type[0]!='H')){
											character[i].hp=character[i].hp - character[j].damage;
											if (character[i].hp<1){
												map[g][h]='.';
												character[i].hp=0;
                                                /* The only diffrent Monster and Hero attack is if any hero kill a monster its xp must be increase.*/
												character[j].xp++;
											}}}}}}}}}}
		fprintf(fileWrt,"HEROES ATTACKED\n");
	}
	fprintf(fileWrt,"\n");
	fclose ( fileWrt );
}
int Final (struct characters character [],int Counter,char *argv,char **map){
    /* The purpose of this function is check the all characters dead, and if all of monsters or all of heroes dead terminate the program. */
	int j,number_of_m=0, number_of_h=0;
	FILE *fileWrt = fopen(argv, "a");
	for (j = 0; j < Counter; ++j){
        /* For the known all of characters dead we must now the numbers of each chracters. */
		if (character[j].type[0]=='M'){number_of_m++;}else{number_of_h++;}
	}
	for (j = 0; j < Counter; ++j){
        /* Now we now the numbers of each characters if all of them is dead, the number of characters and the number of dead characters must be equal
        therefore we descrease the number of monsters or number of heroes. */
		if ((character[j].type[0]=='M')&&(character[j].hp==0)){number_of_m--;}else if((character[j].type[0]=='H')&&(character[j].hp==0)){number_of_h--;} 	
	}
	if (number_of_m==0){
        /* If after all those steps number_of_m equal to 0 it is mean is all monsters are dead.*/
		fprintf(fileWrt,"ALL MONSTERS ARE DEAD!\n");
        fclose ( fileWrt );
		return number_of_m;
	}else if (number_of_h==0){
        /* If after all those steps number_of_h equal to 0 it is mean is all heroes are dead.*/
		fprintf(fileWrt,"ALL HEROES ARE DEAD!\n");
        fclose ( fileWrt );
		return number_of_h;
	}
	fclose ( fileWrt );
    return 1;
}