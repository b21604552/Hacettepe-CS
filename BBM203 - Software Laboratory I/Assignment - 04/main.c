#include <stdio.h>
#include <string.h>
#include <stdlib.h>
struct data { 
    struct data *child[26];
    char ch; 
    char password[10];
};
int control,controlThird = 0, controlSecond = 0, labelCount = 0;
char label[200],tempString[200];
struct data* Temp;
/* This function for printing given char array to output.txt*/
void printToFile(char *line){
	FILE *InputFile,*OutputFile;
	OutputFile = fopen("output.txt", "a");
	fprintf(OutputFile, "%s", line);
	fclose(OutputFile);
}
/* This function for printing given char to output.txt*/
void printToFileCh(char line){
	FILE *InputFile,*OutputFile;
	OutputFile = fopen("output.txt", "a");
	fprintf(OutputFile, "%c", line);
	fclose(OutputFile);
}
/* This function for initializing the root element.*/
void init(struct data *Root){
	int i;
	Root->ch = '/';
	strcpy(Root->password,"/");
	for(i=0; i < 26; i++){
		Root->child[i]=NULL;
	}
}
/* This function creates new node and returns a pointer to point it.*/
struct data *createNode(){
	struct data *newCh = (struct data *)malloc(sizeof(struct data));
	int i;
	newCh->ch = '/';
	strcpy(newCh->password,"/");
	for(i=0; i < 26; i++){
		newCh->child[i]=NULL;
	}
	return newCh;
}
/* This function for search given name in the tree.*/
struct data *search(struct data *Root,char *Name){
	int i; 
    int length = strlen(Name); 
    int index; 
    struct data *current = Root;
  	for (i = 0; i < length; i++){ 
        index = Name[i] - 'a'; 
        if (!current->child[index]){ 
            controlSecond = 1;
            return 0;
        }
        current = current->child[index]; 
    }
    if((current != NULL) && (strcmp(current->password, "/")) != 0){
    	return current;
    }else if(current != NULL && (strcmp(current->password, "/")) == 0){
    	control = 1;
    }else{
    	return NULL;
    }
}
/* This function for append new user to tree.*/
int append(struct data *Root,char *Name,char *Password){
	struct data *current = Root;
	int i = 0;
	struct data *Temp = search(Root,Name);
	if(Temp == NULL){
		while(Name[i]){
			if(current->child[Name[i] - 'a'] == NULL){
				current->child[Name[i] - 'a'] = createNode();
			}
			current->child[Name[i] - 'a']->ch = Name[i];
			current = current->child[Name[i] - 'a'];
			i++;
		}
		strcpy(current->password,Password);
		printToFile("\"");
		printToFile(Name);
		printToFile("\" was added\n");
	}else{
		printToFile("\"");
		printToFile(Name);
		printToFile("\" reserved username\n");
	}
}
/* This function for checking login according to username and password.*/
int login(struct data *Root,char *Name,char *Password){
	struct data* Temp = search(Root,Name);
	if(Temp != NULL){
		if(strcmp(Temp->password,Password) == 0){
			printToFile("\"");
			printToFile(Name);
			printToFile("\" successful login\n");
		}else{
			printToFile("\"");
			printToFile(Name);
			printToFile("\" incorrect password\n");
		}
	}else if(Root->child[Name[0] - 'a'] == NULL){
		printToFile("\"");
		printToFile(Name);
		printToFile("\" no record\n");
	}else if(control == 1){
		printToFile("\"");
		printToFile(Name);
		printToFile("\" not enough username\n");
		control = 0;
	}else if(controlSecond == 1){
		printToFile("\"");
		printToFile(Name);
		printToFile("\" incorrect username\n");
		controlSecond = 0;
	}
}
/* This function returns 1 if given node has least one child.*/
int hasChild(struct data *Root){
	int i;
	for (i = 0; i < 26; ++i){
		if(Root->child[i] != NULL)
			return 1;
	}
	return 0;
}
/* This function removes given name if exist.*/
void removeNode(struct data *Root,char *Name){
	int i; 
    int length = (strlen(Name)) - 1;
    struct data *current = Root;
    for (i = 0; i < length; i++){
	    current = current->child[Name[i] - 'a']; 
	}
	length--;
	if(hasChild(current->child[Name[i] - 'a']))
		strcpy(current->child[Name[i] - 'a']->password,"/");
	else
		current->child[Name[i] - 'a'] = NULL;
	while(length >= 0){
		current = Root;
		for (i = 0; i < length; i++){
	    	current = current->child[Name[i] - 'a']; 
		}
		if ((hasChild(current->child[Name[i] - 'a']) == 0) && (strcmp(current->child[Name[i] - 'a']->password,"/")) == 0){
			current->child[Name[i] - 'a'] = NULL;
		}else{
			break;
		}
		length--;
	}
}
/* This function checking deleting condition.*/
int delete(struct data *Root,char *Name){
	struct data* Temp = search(Root,Name);
	if(Temp != NULL && Temp->password){
		removeNode(Root,Name);
		printToFile("\"");
		printToFile(Name);
		printToFile("\" deletion is successful\n");
	}
	else if(Root->child[Name[0] - 'a'] == NULL){
		printToFile("\"");
		printToFile(Name);
		printToFile("\" no record\n");
	}
	else if(control == 1){
		printToFile("\"");
		printToFile(Name);
		printToFile("\" not enough username\n");
		control = 0;
	}else if(controlSecond == 1){
		printToFile("\"");
		printToFile(Name);
		printToFile("\" incorrect username\n");
		controlSecond = 0;
	}
}
/* This function returns the number of child that given node has.*/
int hasTwoChild(struct data* Root){
	int i;
	int j = 0;
	for(i = 0; i < 26; i++){
		if(Root->child[i] != NULL)
			j++;
	}
	return j;
}
/* This function returns the first child of given node.*/
int returnChild(struct data* Root){
	int i;
	for(i = 0; i < 26; i++){
		if(Root->child[i] != NULL)
			return i;
	}
	return -1;
}
/* This function is a rec. function which is prepare the names after the first collison from main root.*/
void tabNamesRec(struct data* Root, int index){
    int i;
    if ((hasChild(Root) == 0) || (strcmp(Root->password,"/") != 0)){
    	label[index]='\0';
        printToFile("\t-");
        printToFile(tempString);
        printToFile(label);
        printToFileCh('\n');
    } 
    for (i = 0; i < 26; i++){
        if (Root->child[i] != NULL){ 
            label[index] = Root->child[i]->ch; 
            tabNamesRec(Root->child[i], 1 + index); 
        } 
    } 
}
/* This function make a direction from given node to other nodes that have password.*/
void tabNames(struct data* Root){
	strcpy(tempString,label);
	if ((strcmp(Root->password, "/") != 0) && (hasChild(Root) == 0)){
		printToFile("\t-");
		printToFile(label);
		printToFile("\n");
	}else if(strcmp(Root->password, "/") == 0){
		tabNamesRec(Root,0);
	}else{
		tabNamesRec(Root,0);
	}
}
/* This function prepare the first printing names comes from main root.*/
void listRec(struct data* Root) { 
    if(hasTwoChild(Root) < 2){
    	label[labelCount++] = Root->ch; 
    	printToFileCh(Root->ch);
    	if(strcmp(Root->password, "/") != 0){
    		controlThird = 1;
    	}
    	if (returnChild(Root) > -1){
    		listRec(Root->child[returnChild(Root)]);
    	}
    }else{
    	label[labelCount++] = Root->ch;
    	printToFileCh(Root->ch);
    	Temp = Root;
    }
}
/* This function prepare the first printing names comes from main root.*/
int list(struct data *Root){
	int i,j,k;
    for(i = 0; i < 26; i++){
    	if(Root->child[i] != NULL){
    		printToFile("-");
    		listRec(Root->child[i]);
    		printToFile("\n");
    		if (controlThird != 1){
    			tabNames(Temp);
    		}
    		else
    			controlThird = 0;
    		k = strlen(label);
    		for (j = 0; j < k; ++j){
    			label[j] = '\0';
    		}
    		labelCount = 0;
    	}
    }
}
int main(int argc, char **argv){
	FILE *InputFile,*OutputFile;
	OutputFile = fopen("output.txt", "w");
	fclose(OutputFile);
	InputFile = fopen(argv[1], "r");
	char line[255];
	char *Operator, *Name, *Password;
	char Names[50];
	struct data* Root;
	Root = (struct data *)malloc(sizeof(struct data));
	init(Root);
	while ( fgets ( line, sizeof line, InputFile ) != NULL ){
		line [strcspn(line, "\r\n")] = 0;
		switch(line[1]){
			case 'a':
				Operator = strtok (line," ");
				Name = strtok(NULL," ");
				Password = strtok(NULL," ");
				append(Root,Name, Password);
			break;
			case 's':
				Operator = strtok (line," ");
				Name = strtok(NULL," ");
				struct data* Temp = search(Root,Name);
				if(Temp != NULL){
					printToFile("\"");
					printToFile(Name);
					printToFile("\" password \"");
					printToFile(Temp->password);
					printToFile("\"\n");
				}
				else if (Root->child[Name[0] - 'a'] == NULL){
					printToFile("\"");
					printToFile(Name);
					printToFile("\" no record\n");
				}
				else if (control == 1){
					printToFile("\"");
					printToFile(Name);
					printToFile("\" not enough username\n");
					control = 0;
				}else if(Root->child[Name[0] - 'a'] != NULL){
					printToFile("\"");
					printToFile(Name);
					printToFile("\" incorrect username\n");
				}
			break;
			case 'q':
				Operator = strtok (line," ");
				Name = strtok(NULL," ");
				Password = strtok(NULL," ");
				login(Root,Name, Password);
			break;
			case 'd':
				Operator = strtok (line," ");
				Name = strtok(NULL," ");
				delete(Root,Name);
			break;
			case 'l':
				list(Root);
			break;
		}	
	}
	fclose(InputFile);
}