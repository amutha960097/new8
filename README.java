# new8
#include<stdio.h>
#include<stdlib.h>
#include <string.h>

#define STACK_SIZE 10
typedef struct job{
    int start_time;
    int end_time;
    int job_no;
}job;


typedef struct stack{
    int top;
    job items[STACK_SIZE];
}stack;

void push(stack *ms, job element){
    if(ms->top < STACK_SIZE-1){
        ms->items[++(ms->top)] = element;
    }
    else {
        printf("Stack is full\n");
    }
}

job pop (stack *ms){

    if(ms->top >= 0){
        return ms->items[(ms->top)--];
    }
    else{
        printf("Stack is empty\n");
    }
}

int is_empty(stack ms){
    if(ms.top <0) return 1;
    else return 0;
}
void swap(job jobs[], int i, int j){
    int temp_start = jobs[i].start_time;
    int temp_end = jobs[i].end_time;
    int job_no = jobs[i].job_no;

    jobs[i].start_time  = jobs[j].start_time ;
    jobs[i].end_time  = jobs[j].end_time ;
    jobs[i].job_no  = jobs[j].job_no;

    jobs[j].start_time  = temp_start;
    jobs[j].end_time  = temp_end ;
    jobs[j].job_no  = job_no;
}
int partition(job jobs[], int start, int njobs){
    int i,j;

    int pivot =  jobs[start].end_time;
    i = start+1;
    j = njobs;
    while(i<=j){
        while(i<=njobs && jobs[i].start_time < pivot)
            i++;
        while(j>start && jobs[j].start_time >= pivot)
            j--;
        if(i<j){
            swap(jobs, i, j);
        }
    }
    if(j>start)
        swap(jobs,start, j);
    
    return j;
}

void quicksort(job jobs[], int start, int njobs){

    if(start >=njobs) return;
    int pivot = partition(jobs, start, njobs);

    quicksort(jobs, start, pivot);
    quicksort(jobs, pivot+1, njobs);
}

void merge_intervals(job jobs[], int njobs){

	int current_job =0;
    stack s;
    s.top =-1;

    quicksort(jobs, 0, njobs-1);
	job last_job;

    while(current_job < njobs){
        if(!is_empty(s)){
            last_job =  pop(&s);
            if(jobs[current_job].start_time < last_job.end_time
                && jobs[current_job].end_time > last_job.end_time){
                    last_job.end_time = jobs[current_job].end_time;
                    push(&s, last_job);
            }
            else{
                push(&s, last_job);
                push(&s, jobs[current_job]);
            }
        }
        else{
            push(&s, jobs[current_job]);
        }
        current_job++;
    }
    while(!is_empty(s)){
    	last_job = pop(&s);
    	printf("(%d,%d)", last_job.start_time,last_job.end_time);
    	printf("\n");
    }
}
int main(){
	 job jobs[5];

        jobs[0].start_time = 0;
        jobs[0].end_time = 2;
        jobs[0].job_no =1;

        jobs[1].start_time = 2;
        jobs[1].end_time = 3;
        jobs[1].job_no =2;

        jobs[2].start_time = 1;
        jobs[2].end_time = 5;
        jobs[2].job_no =3;

        jobs[3].start_time = 3;
        jobs[3].end_time = 7;
        jobs[3].job_no =4;

        jobs[4].start_time = 8;
        jobs[4].end_time = 9;
        jobs[4].job_no =5;
		merge_intervals(jobs, 5);
        printf("\n");
        return 0;
}
