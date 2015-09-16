#include<stdlib.h>
#include<bios.h>
#include<dos.h>
#include<graphics.h>
#include<math.h>
#define MAGIC					/*�����ݣ�ģʽ2*/
#define MAXX 640            /*��Ϸ���ֱ���*/
#define MAXY 480
#define EASY    1500            /*��ʾ��Ϸ�Ѷȼ��ٶ�*/
#define NOR    900
#define DIF   300
#define HELL   90
#define LEN   1             /*��ʼʱ�߳���*/
#define FIR   10            /* ����һ������ʳ���� */
#define SEC   20            /* ���ڶ�������ʳ���� */
#define THR   30            /* ������������ʳ���� */
#define MOVE 10            /*��ʾ���ƶ�һ��������仯��*/
/*˫����Ϸ*/
#define DUOMOVE 10			/*��ʾ˫��ģʽ�����ƶ�һ���ľ���*/
#define PAI		3.141592654
#define CGANGLE		PAI/6      /*��һ�μ���ͷ�ı�ĽǶ�*/
#define RADIUS    5           /*����뾶*/
#define DUOLEN   3			/*��ʼ�߳�*/
#define NOBODY 15		/*�޵�ʱ��*/
/*˫����Ϸ*/
#define LEFT  0x4B00        /*2p��ͷת��*/
#define RIGHT 0x4D00
#define UP    0x4800
#define DOWN  0x5000
#define ESC   0x011B			/*�˳���Ϸ*/
#define ENTER 0x1C0D        /*��ͣ��Ϸ*/

typedef struct snake{
    int x;
    int y;
    struct snake *previous;
    struct snake *next;
}SNAKE;
typedef struct wall{
    int x1;          /*�� �Խ������� ��ʾǽ�ķ�Χ*/
    int y1;
    int x2;
    int y2;
    struct wall *next;
}WALL;
typedef struct food{
    int x;
    int y;
    int exist;      /* 1��ʳ����ڣ�0������ */
}FOOD;
SNAKE *head=NULL,*tail=NULL;
WALL *wallhead=NULL;
FOOD f[3]={0,0,0,0,0,0,0,0,0};
struct {
    int direx;        /*(direx,direy)����ʸ���˶�����*/
    int direy;   
    unsigned int screeni:2;   
    unsigned int mode:2;        /*modeΪ��Ϸģʽ��1Ϊ���䣬0Ϊ���أ�2Ϊ������*/
    int restart;  /*��¼��Ϸ״̬��-1��Ϸʧ�ܡ�0������Ϸ��1��Ϸʤ����������һ�ء�2��Ϸͨȫ��*/
}gamemsg={MOVE,0,1,0,0};
int speed=NOR;       /*���ƶ��ٶ�*/
/*˫����Ϸ*/
#ifdef MAGIC
struct {
	double angle1;						/*1P�˶������*/
	double angle2;
	unsigned int move1:6;			/*1P move һ�εľ���*/
	unsigned int move2:6;
	unsigned int time1:6;			/*1P�޵�ʱ��*/
	unsigned int time2:6;
	unsigned int dead1p:1;	 /*��¼��״̬��1��������0������*/
	unsigned int dead2p:1;
    unsigned int out:1;			 /*��¼��Ϸ״̬��1���˳���Ϸ��0������Ϸ*/
	int score1p;						/*1P�÷�*/
	int score2p;	
}duogame={0,0,DUOMOVE,DUOMOVE,0,0,1,1,0,0,0};
SNAKE *head1=NULL,*head2=NULL;
SNAKE *tail1=NULL,*tail2=NULL;
FOOD duofood[4]={0,0,0,0,0,0,0,0,0,0,0,0};
#endif
/*˫����Ϸ*/
int checkin(SNAKE scope,int x,int y)                /*�����Ƿ�����ͷ�ڣ����Ƿ���1�����򷵻�0*/
{                                         
    if(scope.x-5<=x&&scope.x+5>=x&&scope.y-5<=y&&scope.y+5>=y)return 1;
    else return 0;
}
int checkwall(WALL scope,int x,int y)               /*�����Ƿ���ǽ�ڣ����Ƿ���1�����򷵻�0*/
{
    if(scope.x1<=x&&scope.x2>=x&&scope.y1<=y&&scope.y2>=y)return 1;
    else return 0;
}
void graphbegin(void)
{                                       /* ��ʼ��ͼ������ */
    int gdriver=DETECT,gmode;
    initgraph(&gdriver,&gmode,"bgi");
}
void newgame(void)                      /* ��ʼ������������Ϣ */
{
    int i;      
    SNAKE *ps=NULL;
    gamemsg.restart=0;                          /* ��ʼ����Ϸ��� */
    gamemsg.direx=MOVE,gamemsg.direy=0;                /* ��ʼ���˶����� */
	for(ps=tail;ps!=NULL;ps=ps->previous)   /*�ͷŴ洢�ռ�*/
			free(ps);
    head=(SNAKE *)malloc(sizeof(SNAKE));
    ps=head; 
    head->previous=NULL;                /* ������ͷ */
    head->x=MAXX/2;
    head->y=MAXY/2;
    for(i=1;i<=LEN;i++)                 /* �������� */
    {
        ps->next=(SNAKE *)malloc(sizeof(SNAKE));
        ps->next->previous=ps;
        ps=ps->next;
        ps->x=ps->previous->x-MOVE;
        ps->y=ps->previous->y;
    }
    tail=ps;
    ps->next=NULL;
    cleardevice();                      /* ��һ�صĿ�ʼǰ�������ͼ�� */
    setbkcolor(0);                      /* ��ʼ������ */
    setcolor(GREEN);              /* ������ */
    for(ps=head->next;ps!=NULL;ps=ps->next)
        rectangle(ps->x-5,ps->y-5,ps->x+5,ps->y+5);
    setcolor(BLUE);               /* ��ͷ������ */
    rectangle(head->x-5,head->y-5,head->x+5,head->y+5);
}
int length(SNAKE *head)                       /*�����߳���*/
{
    SNAKE *ps;
    int n=0;
    for(ps=head;ps!=NULL;ps=ps->next)n++;
    return n;
}
int speedup(const int speed)
{
    int cgspeed=(length(head)-LEN-1);
    switch(speed)
    {
        case EASY:if(cgspeed<=10)return 50*cgspeed;
        else if(cgspeed<=20)return (500+cgspeed*20);
        else return 900;
        case NOR:if(cgspeed<=10)return 30*cgspeed;
        else if(cgspeed<=20)return (300+cgspeed*20);
        else return 700;
        case DIF:if(cgspeed<=10)return 10*cgspeed;
        else if(cgspeed<=22)return (100+5*cgspeed);
        else return 210;
        default :return 0;
    }
}
void direction(int speed)              /*��Ϸ����--������Ӧ�����ƶ�����Ϸ����*/
{
    int i;
    SNAKE *ps=NULL;    
    for(i=1;i<=speed;i++)        /* speed*delay(1000)=�޿��������ƶ�һ������ʱ�� */
    {
        if(kbhit())                 /* ������Ϣ */
        {
            while(bioskey(1)==0);
            switch(bioskey(0))
            {
                case ESC:
                {
                    gamemsg.restart=-1;     /*������Ϸ����������game over*/
                    closegraph();
                    break;
                }
                case ENTER:         /*��ͣ��Ϸ*/
                {
                    while(ENTER!=bioskey(0));
                    break;
                }
                case LEFT:
                if(MOVE==gamemsg.direx)break; /* ifʹ��ͷ���ܷ��� */
                else
                {
                    gamemsg.direx=-MOVE;
                    gamemsg.direy=0;
                    i=speed;
                    break;          /* left gamemsg.direx=-MOVE gamemsg.direy=0 */
                }
                case RIGHT:
                if(-MOVE==gamemsg.direx)break;
                else
                {
                    gamemsg.direx=MOVE;
                    gamemsg.direy=0;
                    i=speed;
                    break;          /* right gamemsg.direx=MOVE gamemsg.direy=0 */
                }
                case UP:
                if(MOVE==gamemsg.direy)break;
                else
                {
                    gamemsg.direx=0;
                    gamemsg.direy=-MOVE;
                    i=speed;
                    break;          /* up gamemsg.direx=0 gamemsg.direy=MOVE */
                }
                case DOWN:
                if(-MOVE==gamemsg.direy)break;
                else
                {
                    gamemsg.direx=0;
                    gamemsg.direy=MOVE;
                    i=speed;
                    break;          /* down gamemsg.direx=0 gamemsg.direy=-MOVE */
                }
                default:break;
            }
        }
    delay(100);
    }
    setcolor(0);                        /* ������β�� */
    rectangle(tail->x-5,tail->y-5,tail->x+5,tail->y+5);
	setcolor(GREEN);
    for(ps=tail;ps->previous!=NULL;ps=ps->previous)   /* ������������ */
    {
        ps->x=ps->previous->x;
        ps->y=ps->previous->y;
		rectangle(ps->x-5,ps->y-5,ps->x+5,ps->y+5);
    }
    head->x+=gamemsg.direx;                                   /* ��ͷ�������� */
    head->y+=gamemsg.direy;
    setcolor(BLUE);                          
    rectangle(head->x-5,head->y-5,head->x+5,head->y+5);
}
void profood(void)                  
{
    int i;
    struct{
                unsigned int food:2;   /*���ʳ��������������� 1����ȷ 0 */
                unsigned int wall:2;
                unsigned int snake:2;
                unsigned int info:2;
            }flag={0,0,0,0};
    for(i=0;i<3;i++)                   /* ����Ƿ�ͬʱ����3��ʳ������������� */
    {
        if(0==f[i].exist)              /* ���ʳ�ﲻ���ڣ����� */
       {
            int j;
            SNAKE *ps;
            WALL *wallp=NULL;
            do
            {
                f[i].x=random(MAXX);       /* ����ʳ����Ϣ */
                f[i].y=random(MAXY);
                flag.food=flag.snake=flag.info=flag.wall=0;
                for(wallp=wallhead;wallp!=NULL;wallp=wallp->next)
                    if(wallp->x2>=f[i].x-6&&wallp->x1<=f[i].x+6&&wallp->y2>=f[i].y-6&&wallp->y1<=f[i].y+6)
                    {
                        flag.wall=1;
                        setfillstyle(1,RED);
                        bar(wallp->x1,wallp->y1,wallp->x2,wallp->y2);
                    }
                for(ps=head;ps!=NULL;ps=ps->next)
                    if(f[i].x-11<=ps->x&&f[i].x+11>=ps->x&&f[i].y-11<=ps->y&&f[i].y+11>=ps->y)
                        {
                            flag.snake=1;
                            setcolor(GREEN);
                            rectangle(ps->x-5,ps->y-5,ps->x+5,ps->y+5);
                        }
                if(f[i].x-5<=2||f[i].x+5>MAXX-2||f[i].y-5<25||f[i].y+5>MAXY-11)
                    flag.info=1;
                for(j=0;j<3;j++)
                    if((j!=i)&&(f[j].x-11<=f[i].x&&f[j].x+11>=f[i].x&&f[j].y-11<=f[i].y&&f[j].y+11>=f[i].y))
                        {
                            flag.food=1;
                            /*����ص���ʳ��ͼ��*/
                            setfillstyle(0,0);
                            bar(f[i].x-5,f[i].y-5,f[i].x+5,f[i].y+5);
                            bar(f[j].x-5,f[j].y-5,f[j].x+5,f[j].y+5);
                            setfillstyle(1,YELLOW);       /*�ػ�Ӧ�ô��ڵ�ʳ��*/
                            bar(f[j].x-5,f[j].y-5,f[j].x+5,f[j].y+5);
                        }
            }while(1==flag.food||1==flag.snake||1==flag.info||1==flag.wall);
            setfillstyle(1,YELLOW);
            bar(f[i].x-5,f[i].y-5,f[i].x+5,f[i].y+5);
            f[i].exist=1;
        }
    }
}
void printwall(int i)        /* ǽ����Ϣ */
{
    WALL *wallp=NULL;
	for(wallp=wallhead;wallp!=NULL;wallp=wallp->next)   /*�ͷŴ洢�ռ�*/
			free(wallp);
    wallhead=(WALL *)malloc(sizeof(WALL));
    wallhead->x1=0;           /* ����ǽ */
    wallhead->y1=20;
    wallhead->x2=MAXX;
    wallhead->y2=24;
    wallp=(WALL *)malloc(sizeof(WALL));
    wallhead->next=wallp;
    wallp->x1=0;
    wallp->y1=20;
    wallp->x2=1;
    wallp->y2=MAXY;
    wallp->next=(WALL *)malloc(sizeof(WALL));
    wallp=wallp->next;
    wallp->x1=MAXX-1;
    wallp->y1=20;
    wallp->x2=MAXX;
    wallp->y2=MAXY;
    wallp->next=(WALL *)malloc(sizeof(WALL));
    wallp=wallp->next;
    wallp->x1=0;
    wallp->y1=MAXY-10;
    wallp->x2=MAXX;
    wallp->y2=MAXY;
    switch(i)                 /* �ؿ�ǽ */
    {
        case 1:
        {
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=100;
            wallp->y1=100;
            wallp->x2=110;
            wallp->y2=MAXY-100;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX-110;
            wallp->y1=100;
            wallp->x2=MAXX-100;
            wallp->y2=MAXY-100;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2-70;
            wallp->y1=120;
            wallp->x2=MAXX/2+70;
            wallp->y2=130;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2-70;
            wallp->y1=MAXY-130;
            wallp->x2=MAXX/2+70;
            wallp->y2=MAXY-120;
            wallp->next=NULL;            
            break;
        }
        case 2:
        {
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=60;
            wallp->y1=70;
            wallp->x2=70;
            wallp->y2=MAXY-70;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX-70;
            wallp->y1=70;
            wallp->x2=MAXX-60;
            wallp->y2=MAXY-70;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=135;
            wallp->y1=100;
            wallp->x2=MAXX-135;
            wallp->y2=110;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=135;
            wallp->y1=MAXY-110;
            wallp->x2=MAXX-135;
            wallp->y2=MAXY-100;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX-217;
            wallp->y1=60;
            wallp->x2=MAXX-210;
            wallp->y2=195;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=210;
            wallp->y1=MAXY-195;
            wallp->x2=217;
            wallp->y2=MAXY-60;
            wallp->next=NULL;
            break;
        }
        case 3:
        {
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=88;
            wallp->y1=150;
            wallp->x2=95;
            wallp->y2=MAXY-150;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX-95;
            wallp->y1=150;
            wallp->x2=MAXX-88;
            wallp->y2=MAXY-150;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=165;
            wallp->y1=90;
            wallp->x2=MAXX-165;
            wallp->y2=100;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=165;
            wallp->y1=MAXY-100;
            wallp->x2=MAXX-165;
            wallp->y2=MAXY-90;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2-60;
            wallp->y1=55;
            wallp->x2=MAXX/2-55;
            wallp->y2=195;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2+55;
            wallp->y1=MAXY-195;
            wallp->x2=MAXX/2+60;
            wallp->y2=MAXY-55;
            wallp->next=NULL;
            break;
        }
        default :break;
    }
#ifdef MAGIC    /*˫����Ϸʱ��ǽ*/
    if(2==gamemsg.mode)
        {				/*��ǿǽ*/
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2-5;
            wallp->y1=24;
            wallp->x2=MAXX/2+5;
            wallp->y2=MAXY/2-150;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2-5;
            wallp->y1=MAXY/2+150;
            wallp->x2=MAXX/2+5;
            wallp->y2=MAXY;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2-5;
            wallp->y1=MAXY/2-100;
            wallp->x2=MAXX/2+5;
            wallp->y2=MAXY/2-30;
            wallp->next=(WALL *)malloc(sizeof(WALL));
            wallp=wallp->next;
            wallp->x1=MAXX/2-5;
            wallp->y1=MAXY/2+30;
            wallp->x2=MAXX/2+5;
            wallp->y2=MAXY/2+100;
			wallp->next=(WALL *)malloc(sizeof(WALL));     /*��������Ķ�ǽ*/
            wallp=wallp->next;
			do     
			{			/*���ǽһ�������*/
            wallp->x1=random(MAXX/2-20)+10;
            wallp->y1=random(MAXY/2-60)+40;
			}while(wallp->y1>=MAXY/2-120&&wallp->x1>=MAXX/2-120);
			 /*�������ڴ˴������������ǿǽ�����ص������*/
			if(wallp->x1>=MAXX/2-120&&wallp->y1<=MAXY/2-120)
            {			/*���ǽ���Ȳ���*/
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
			}
			else if(wallp->y1>=MAXY/2-120&&wallp->x1<=MAXX/2-120)
			{
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
			}	
			else switch(random(1))      /*���ǽ�ķ���Ҳ�ǲ�����*/
			{
				case 0:
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
				break;
				case 1:
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
				break;
				default :break;
			}
			wallp->next=(WALL *)malloc(sizeof(WALL));    /*�ڶ���*/ 
            wallp=wallp->next;
			do     
			{
            wallp->x1=random(MAXX/2-20)+10;
            wallp->y1=random(MAXY/2-50)+MAXY/2+20;
			}while(wallp->y1>=MAXY-140&&wallp->x1>=MAXX/2-120);
			if(wallp->x1>=MAXX/2-120&&wallp->y1<=MAXY-140)
            {
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
			}
			else if(wallp->y1>=MAXY-140&&wallp->x1<=MAXX/2-120)
			{
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
			}	
			else switch(random(1))
			{
				case 0:
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
				break;
				case 1:
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
				break;
				default :break;
			}
			wallp->next=(WALL *)malloc(sizeof(WALL));     /*������ǽ*/
            wallp=wallp->next;
			do     
			{
            wallp->x1=random(MAXX/2-40)+MAXX/2+20;
            wallp->y1=random(MAXY/2-60)+40;
			}while(wallp->y1>=MAXY/2-120&&wallp->x1>=MAXX-120);
			if(wallp->x1>=MAXX-120&&wallp->y1<=MAXY/2-120)
            {
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
			}
			else if(wallp->y1>=MAXY/2-120&&wallp->x1<=MAXX-120)
			{
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
			}	
			else switch(random(1))
			{
				case 0:
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
				break;
				case 1:
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
				break;
				default :break;
			}
			wallp->next=(WALL *)malloc(sizeof(WALL));     /*���Ķ�ǽ*/
            wallp=wallp->next;
			do     
			{
            wallp->x1=random(MAXX/2-40)+MAXX/2+20;
            wallp->y1=random(MAXY/2-50)+MAXY/2+20;
			}while(wallp->y1>=MAXY-140&&wallp->x1>=MAXX-120);
			if(wallp->x1>=MAXX-120&&wallp->y1<=MAXY-140)
            {
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
			}
			else if(wallp->y1>=MAXY-140&&wallp->x1<=MAXX-120)
			{
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
			}	
			else switch(random(1))
			{
				case 0:
				wallp->x2=wallp->x1+random(5)+5;
				wallp->y2=wallp->y1+random(100)+50;
				break;
				case 1:
				wallp->x2=wallp->x1+random(100)+50;
				wallp->y2=wallp->y1+random(5)+5;
				break;
				default :break;
			}
            wallp->next=NULL;            
        }
#endif
    setfillstyle(1,RED);    /*��ǽ*/
    for(wallp=wallhead;wallp!=NULL;wallp=wallp->next)
        bar(wallp->x1,wallp->y1,wallp->x2,wallp->y2);
}
void eat(void)
{
    int i;
    WALL *wallp=wallhead;
    SNAKE *ps=NULL;
    for(i=0;i<3;i++)        /* ���ÿ��ʳ�����Ե�ʳ�β�ڵ�+1 */
    {
        if(checkin(*head,f[i].x,f[i].y)||checkin(*head,f[i].x-5,f[i].y-5)||checkin(*head,f[i].x-5,f[i].y+5)||checkin(*head,f[i].x+5,f[i].y-5)||checkin(*head,f[i].x+5,f[i].y+5))
        {            
            setfillstyle(0,0);
            bar(f[i].x-5,f[i].y-5,f[i].x+5,f[i].y+5);
            f[i].exist=0;
            setcolor(BLUE);
            rectangle(head->x-5,head->y-5,head->x+5,head->y+5);
            ps=(SNAKE *)malloc(sizeof(SNAKE));
            ps->x=tail->x*2-tail->previous->x;
            ps->y=tail->y*2-tail->previous->y;
            tail->next=ps;
            ps->previous=tail;
            ps->next=NULL;
            tail=ps;
        }
    }
    for(wallp=wallhead;wallp!=NULL;wallp=wallp->next)     /* ���ײǽ��gameover */
        if(checkwall(*wallp,head->x,head->y)||checkwall(*wallp,head->x-5,head->y-5)||checkwall(*wallp,head->x+5,head->y-5)||checkwall(*wallp,head->x-5,head->y+5)||checkwall(*wallp,head->x+5,head->y+5))gamemsg.restart=-1;
    for(ps=head->next->next->next;ps!=NULL;ps=ps->next) /* ���ҧ�Լ���gameover */
        if(checkin(*head,ps->x,ps->y))gamemsg.restart=-1;
}
int advance(void)                        /* ����0 ��Ϸ������1 ��һ��orͨȫ�� */
{         
    SNAKE *ps=NULL;/* ���� */
    WALL *wallp=NULL;
    if(1==gamemsg.screeni&&(length(head)-LEN-1)>=FIR)
        {
            gamemsg.restart=1;                    /* ˵����Ϸʤ�� */
            gamemsg.screeni++;                    /* ��һ�� */
             /* ����������Ϣ */
            for(ps=head;head!=NULL;free(ps))   /* �������Ϣ */
            {
                ps=head;
                head=ps->next;
            }
            for(wallp=wallhead;wallhead!=NULL;free(wallp))
            {                                  /* ���ǽ��Ϣ */
                wallp=wallhead;
                wallhead=wallp->next;
            }
            f[0].exist=0;
            f[1].exist=0;
            f[2].exist=0;
            return 1;
        }
    else if(2==gamemsg.screeni&&(length(head)-LEN-1)>=SEC)
        {
            gamemsg.restart=1;                       
            gamemsg.screeni++;                      
            for(ps=head;head!=NULL;free(ps)) 
            {
                ps=head;
                head=ps->next;
            }
            for(wallp=wallhead;wallhead!=NULL;free(wallp)) 
            {
                wallp=wallhead;
                wallhead=wallp->next;
            }
            f[0].exist=0;
            f[1].exist=0;
            f[2].exist=0;
            return 1;  
        }
    else if(3==gamemsg.screeni&&(length(head)-LEN-1)>=THR)
        {                                    /* ͨȫ�أ��ÿ� */
            gamemsg.restart=2;                       /* ͨȫ���˳� */
            for(ps=head;head!=NULL;free(ps)) 
            {
                ps=head;
                head=ps->next;
            }
            for(wallp=wallhead;wallhead!=NULL;free(wallp)) 
            {
                wallp=wallhead;
                wallhead=wallp->next;
            }
            f[0].exist=0;
            f[1].exist=0;
            f[2].exist=0;
            return 1;  
        }
    else return 0;
}
void information(int mode)      /*��Ϸ��Ϣ��*/
{
    int flag;
    char score[7];
    settextstyle(0,0,2);
    setcolor(8);
    outtextxy(460,0,"Speed:");    /*��Ϸ�ٶ�*/
    switch(speed)
    {
        case EASY:outtextxy(560,0,"EASY");break;
        case NOR:outtextxy(560,0,"NOR");break;
        case DIF:outtextxy(560,0,"DIF");break;
        case HELL:outtextxy(560,0,"HELL");break;
    }
    if(2==mode)
    {
        outtextxy(40,0,"1P:2P = ");     /*˫����Ϸʱ�ıȷ����*/
        if(duogame.score1p<10)
        {
            flag=1;
            score[0]=duogame.score1p+'0';
            score[1]='\0';
        }    
        else if(duogame.score1p<100)
        {
            flag=2;
            score[0]=duogame.score1p/10+'0';
            score[1]=duogame.score1p%10+'0';
            score[2]='\0';
        }
        else 
        {
            flag=3;
            score[0]=duogame.score1p/100+'0';
            score[1]=duogame.score1p/10-duogame.score1p/100*10+'0';
            score[2]=duogame.score1p%10+'0';
            score[3]='\0';
        }
        setfillstyle(0,0);
        switch(flag)
        {
            case 1:bar(160,0,210,15);outtextxy(190,0,score);break;
            case 2:bar(160,0,210,15);outtextxy(180,0,score);break;
            case 3:bar(160,0,210,15);outtextxy(165,0,score);break;
        }
        outtextxy(230,0,":");
        if(duogame.score2p<10)
        {
            flag=1;
            score[0]=duogame.score2p+'0';
            score[1]='\0';
        }    
        else if(duogame.score2p<100)
        {
            flag=2;
            score[0]=duogame.score2p/10+'0';
            score[1]=duogame.score2p%10+'0';
            score[2]='\0';
        }
        else 
        {
            flag=3;
            score[0]=duogame.score2p/100+'0';
            score[1]=duogame.score2p/10-duogame.score2p/100*10+'0';
            score[2]=duogame.score2p%10+'0';
            score[3]='\0';
        }
        switch(flag)
        {
            case 1:bar(250,0,300,15);outtextxy(280,0,score);break;
            case 2:bar(250,0,300,15);outtextxy(270,0,score);break;
            case 3:bar(250,0,300,15);outtextxy(255,0,score);break;
        }
        return;
    }
    outtextxy(5,0,"Score:");     /*�÷����*/
    if((length(head)-LEN-1)<10)
    {
        flag=1;
        score[0]=(length(head)-LEN-1)+'0';
        score[1]='\0';
    }
    else if((length(head)-LEN-1)<100)
    {
        flag=2;
        score[0]=(length(head)-LEN-1)/10+'0';
        score[1]=(length(head)-LEN-1)%10+'0';
        score[2]='\0';
    }
    else 
    {
        flag=3;
        score[0]=(length(head)-LEN-1)/100+'0';
        score[1]=(length(head)-LEN-1)/10-(length(head)-LEN-1)/100*10+'0';
        score[2]=(length(head)-LEN-1)%10+'0';
        score[3]='\0';
    }
    setfillstyle(0,0);
    switch(flag)
    {
        case 1:bar(110,0,185,15);outtextxy(110,0,score);break;
        case 2:bar(110,0,185,15);outtextxy(110,0,score);break;
        case 3:bar(110,0,185,15);outtextxy(110,0,score);break;
    }
    if(0==mode)
    {    
        outtextxy(170,0,"Aim:");
        outtextxy(300,0,"Screen:");
        switch(gamemsg.screeni)
        {
            case 1:outtextxy(235,0,"10");outtextxy(400,0," 1 ");break;
            case 2:outtextxy(235,0,"20");outtextxy(400,0," 2 ");break;
            case 3:outtextxy(235,0,"30");outtextxy(400,0," 3 ");break;
        }
    }
    else if(1==mode)
    {
        outtextxy(190,0,"Try your best!");
    }
}
void beforegame(void)               /* ѡ����Ϸģʽ����Ϸ�Ѷ� */
{
	f[0].exist=f[1].exist=f[2].exist=0;
	duofood[0].exist=duofood[1].exist=duofood[2].exist=duofood[3].exist=0;
    settextstyle(0,0,2);
    setcolor(8);
    outtextxy(20,160,"Please choose the game mode:");
    outtextxy(200,200,"1.Classic");
    outtextxy(200,230,"2.Stage");
#ifdef MAGIC
    outtextxy(200,260,"3.Magic");
#endif
    switch(bioskey(0))
    {
        case 0x231:
		case 0x4f31:gamemsg.mode=1;break;     /*�����ּ���1��ʱѡ����Ϸģʽ1*/
        case 0x332:
		case 0x5032:gamemsg.mode=0;break;
#ifdef MAGIC
        case 0x433:
		case 0x5133:
			{
				gamemsg.mode=2;
				speed=EASY;
				duogame.dead1p=duogame.dead2p=1;
				duogame.score1p=duogame.score2p=0;
				duogame.out=0;
				cleardevice();
				return;
			}
#endif
		case ESC:closegraph();break;
        default :gamemsg.mode=1;break;
    }
    cleardevice();
    outtextxy(20,160,"Please choose the gamedifficulty:");
    outtextxy(200,200,"1.Easy");
    outtextxy(200,230,"2.Normal");
    outtextxy(200,260,"3.Expert");
    outtextxy(200,290,"4.Hell");
    switch(bioskey(0))
    {
        case 0x231:
		case 0x4f31:speed=EASY;break;  /*�����ּ���1��ʱѡ����Ϸ�Ѷ� Easy*/
        case 0x332:
		case 0x5032:speed=NOR;break;
        case 0x433:
		case 0x5133:speed=DIF;break;
        case 0x534:
		case 0x4b34:speed=HELL;break;
		case ESC:closegraph();break;
        default :speed=EASY;break;
    }
    cleardevice();
}
/*˫����Ϸ*/
#ifdef MAGIC
int distance(int x1,int y1,int x2,int y2)  /*������룬����*/
{			/*long intΪ�˷�ֹ���*/
	return (int)sqrt((double)((long int)(x1-x2)*(long int)(x1-x2)+(long int)(y1-y2)*(long int)(y1-y2)));
}
void duonew(void)                      /* ��ʼ������������Ϣ */
{
    int i;      
    SNAKE *ps=NULL;
	void clearsnake(SNAKE *head);
	if(1==duogame.dead1p)    /*���1P����*/
	{
		clearsnake(head1);			/*���ʬ��*/
		duogame.time1=NOBODY;       /*��ʼ���޵�ʱ��*/
		duogame.angle1=0;					/*��ʼ���˶�����*/
		duogame.move1=DUOMOVE;                /* ��ʼ���˶��ٶ� */
		for(ps=tail1;ps!=NULL;ps=ps->previous)   /*�ͷŴ洢�ռ�*/
			free(ps);
		head1=(SNAKE *)malloc(sizeof(SNAKE));
		head1->previous=NULL;                /* ������ͷ */
		head1->x=MAXX/4;
		head1->y=MAXY/2;
		setcolor(GREEN);
		for(ps=head1,i=1;i<=DUOLEN;i++)                 /* �������� */
		{
			ps->next=(SNAKE *)malloc(sizeof(SNAKE));
			ps->next->previous=ps;
			ps=ps->next;
			ps->x=ps->previous->x-DUOMOVE;
			ps->y=ps->previous->y;
			circle(ps->x,ps->y,RADIUS);
		}
		tail1=ps;
		tail1->next=NULL;
		setfillstyle(1,BLUE);                          /* ����ͷ */
		fillellipse(head1->x,head1->y,RADIUS,RADIUS);
		duogame.dead1p=0;
	}
	if(1==duogame.dead2p)
	{
		clearsnake(head2);
		duogame.time2=NOBODY; 
		duogame.angle2=0;		
		duogame.move2=DUOMOVE; 
		for(ps=tail2;ps!=NULL;ps=ps->previous)   /*�ͷŴ洢�ռ�*/
			free(ps);
		head2=(SNAKE *)malloc(sizeof(SNAKE));
		head2->previous=NULL;                /* ������ͷ */
		head2->x=MAXX/4*3;
		head2->y=MAXY/2;
		setcolor(RED);
		for(ps=head2,i=1;i<=DUOLEN;i++)                 /* �������� */
		{
			ps->next=(SNAKE *)malloc(sizeof(SNAKE));
			ps->next->previous=ps;
			ps=ps->next;
			ps->x=ps->previous->x+DUOMOVE;
			ps->y=ps->previous->y;
			circle(ps->x,ps->y,RADIUS);
		}
		tail2=ps;
		tail2->next=NULL;
		setfillstyle(1,WHITE);                          
		fillellipse(head2->x,head2->y,RADIUS,RADIUS);
		duogame.dead2p=0;
	}
    setbkcolor(0);                      /* ��ʼ������ */	
}
void duomove(int speed)              
{
	long int i;
	SNAKE *ps=NULL;
	duogame.move1=duogame.move2=DUOMOVE;    /*��ʼ��ÿ���ƶ�����*/
    for(i=1;i<=150*speed;i++)       
    {
        if(kbhit())                
        {
            while(bioskey(1)==0);
            switch(bioskey(0))
            {
                case ESC:
                {
                    duogame.out=-1;     /*������Ϸ*/
                    break;
                }
                case ENTER:					/*��ͣ��Ϸ*/
                {
                    while(ENTER!=bioskey(0));
                    break;
                }
				case UP:
                {					/*����*/
					if(duogame.move2<4*RADIUS)  /*���������*/
						duogame.move2+=RADIUS;
                    break;         
                }   
				case 0x1157:
				case 0x1177:          /*����W*/
				{
					if(duogame.move1<4*RADIUS)
						duogame.move1+=RADIUS;
                    break;  
				}
				case 0x1e41:
				case 0x1e61:       /*����A*/
				{
					 duogame.angle1-=CGANGLE;					
					 break;
				}
                case LEFT:
                {
                     duogame.angle2-=CGANGLE;
					 break;        
                }
				case 0x2044:
				case 0x2064:       /*����D*/
				{
					 duogame.angle1+=CGANGLE;
					 break;
				}
                case RIGHT:
                {
                     duogame.angle2+=CGANGLE;
					 break;        
                }                      
                default:break;
            }
        }
    delay(1);
    }
    setfillstyle(0,0);                        /*ֻ������������Ȼ�����ɾ�*/
    bar(tail1->x-RADIUS,tail1->y-RADIUS,tail1->x+RADIUS,tail1->y+RADIUS);
    bar(tail2->x-RADIUS,tail2->y-RADIUS,tail2->x+RADIUS,tail2->y+RADIUS);
    setcolor(GREEN);
	if(duogame.time1!=0)duogame.time1--; 
    for(ps=tail1;ps->previous!=NULL;ps=ps->previous) 			/* ������������ */
	{
		ps->x=ps->previous->x;
        ps->y=ps->previous->y;
		if(duogame.time1!=0)setcolor(random(14)+1);		/*����޵У������ɫ*/
		fillellipse(ps->x,ps->y,RADIUS,RADIUS);   
	}
	setcolor(RED);
	if(duogame.time2!=0)duogame.time2--; 
	for(ps=tail2;ps->previous!=NULL;ps=ps->previous)  
    {
        ps->x=ps->previous->x;
        ps->y=ps->previous->y;
		if(duogame.time2!=0)setcolor(random(14)+1);
		fillellipse(ps->x,ps->y,RADIUS,RADIUS);
    }
	head1->x+=(int)duogame.move1*cos(duogame.angle1); /* ��ͷ�������� */
    head1->y+=(int)duogame.move1*sin(duogame.angle1);
	head2->x+=(int)duogame.move2*cos(PAI+duogame.angle2);
    head2->y+=(int)duogame.move2*sin(PAI+duogame.angle2);
    setfillstyle(1,BLUE);                          /* ����ͷ */
	fillellipse(head1->x,head1->y,RADIUS,RADIUS); 
	setfillstyle(1,WHITE);                          
	fillellipse(head2->x,head2->y,RADIUS,RADIUS);
}
void duoprofood(void)                  
{
    int i;
    struct{
                unsigned int food:2;   /*���ʳ��������������� 1����ȷ 0 */
                unsigned int wall:2;
                unsigned int snake:2;
                unsigned int info:2;
            }flag={0,0,0,0};
    for(i=0;i<4;i++)                   /* ����Ƿ�ͬʱ����4��ʳ������������� */
    {
        if(0==duofood[i].exist)              /* ���ʳ�ﲻ���ڣ����� */
       {
            int j;
            SNAKE *ps1=NULL,*ps2=NULL;
            WALL *wallp=NULL;
            do
            {
                duofood[i].x=random(MAXX);       /* ����ʳ����Ϣ */
                duofood[i].y=random(MAXY);
                flag.food=flag.snake=flag.info=flag.wall=0;
                for(wallp=wallhead;wallp!=NULL;wallp=wallp->next)
                    if(wallp->x2>=duofood[i].x-6&&wallp->x1<=duofood[i].x+6&&wallp->y2>=duofood[i].y-6&&wallp->y1<=duofood[i].y+6)
                    {
                        flag.wall=1;
                        setfillstyle(1,RED);
                        bar(wallp->x1,wallp->y1,wallp->x2,wallp->y2);
                    }
                for(ps1=head1;ps1!=NULL;ps1=ps1->next)
                    if((int)distance(duofood[i].x,duofood[i].y,ps1->x,ps1->y)<RADIUS+7)
                        {
                            flag.snake=1;
                            setcolor(GREEN);
                            fillellipse(ps1->x,ps1->y,RADIUS,RADIUS);
                        }
				for(ps2=head2;ps2!=NULL;ps2=ps2->next)
                    if((int)distance(duofood[i].x,duofood[i].y,ps2->x,ps2->y)<RADIUS+7)
                        {
                            flag.snake=1;
                            setcolor(RED);
                            fillellipse(ps2->x,ps2->y,RADIUS,RADIUS);
                        }
                if(duofood[i].x-5<=2||duofood[i].x+5>MAXX-2||duofood[i].y-5<25||duofood[i].y+5>MAXY-11)
                    flag.info=1;
                for(j=0;j<4;j++)
                    if((j!=i)&&(duofood[j].x-11<=duofood[i].x&&duofood[j].x+11>=duofood[i].x&&duofood[j].y-11<=duofood[i].y&&duofood[j].y+11>=duofood[i].y))
                        {
                            flag.food=1;
                            /*����ص���ʳ��ͼ��*/
                            setfillstyle(0,0);
                            bar(duofood[i].x-5,duofood[i].y-5,duofood[i].x+5,duofood[i].y+5);
                            bar(duofood[j].x-5,duofood[j].y-5,duofood[j].x+5,duofood[j].y+5);
                            setfillstyle(1,YELLOW);       /*�ػ�Ӧ�ô��ڵ�ʳ��*/
                            bar(duofood[j].x-5,duofood[j].y-5,duofood[j].x+5,duofood[j].y+5);
                        }
            }while(1==flag.food||1==flag.snake||1==flag.info||1==flag.wall);
            setfillstyle(1,YELLOW);
            bar(duofood[i].x-5,duofood[i].y-5,duofood[i].x+5,duofood[i].y+5);
            duofood[i].exist=1;
        }
    }
}
void duoeat(void)
{
    int i;
    WALL *wallp=wallhead;
    SNAKE *ps=NULL;
    for(i=0;i<4;i++)        /* ���ÿ��ʳ�����Ե�ʳ�β�ڵ�+1 */
    {
        if(distance(head1->x,head1->y,duofood[i].x,duofood[i].y)<RADIUS+5)
        {            
            setfillstyle(0,0);
            bar(duofood[i].x-5,duofood[i].y-5,duofood[i].x+5,duofood[i].y+5);
            duofood[i].exist=0;
            setfillstyle(1,BLUE);                          /* ����ͷ */
			fillellipse(head1->x,head1->y,RADIUS,RADIUS); 
            ps=(SNAKE *)malloc(sizeof(SNAKE));
            ps->x=ps->y=0;           
            tail1->next=ps;
            ps->previous=tail1;
            ps->next=NULL;
            tail1=ps;
			duogame.score1p++;     /*�÷ּ�1*/
        }
		else if(distance(head2->x,head2->y,duofood[i].x,duofood[i].y)<RADIUS+5)
        {            
            setfillstyle(0,0);
            bar(duofood[i].x-5,duofood[i].y-5,duofood[i].x+5,duofood[i].y+5);
            duofood[i].exist=0;
            setfillstyle(1,BLUE);                          /* ����ͷ */
			fillellipse(head2->x,head2->y,RADIUS,RADIUS); 
            ps=(SNAKE *)malloc(sizeof(SNAKE));
            ps->x=ps->y=0;           
            tail2->next=ps;
            ps->previous=tail2;
            ps->next=NULL;
            tail2=ps;
			duogame.score2p++;
        }
    }
	if(head1->x-RADIUS<1||head1->y-RADIUS<24||head1->x+RADIUS>MAXX-1||head1->y+RADIUS>MAXY-10)
	{					/*���1P����Ϣ����1Pdead&&2Pscore++*/
		duogame.score2p++;
		duogame.dead1p=1;
	}
	if(head2->x-RADIUS<1||head2->y-RADIUS<24||head2->x+RADIUS>MAXX-1||head2->y+RADIUS>MAXY-10)
	{
		duogame.score1p++;
		duogame.dead2p=1;
	}
    for(wallp=wallhead;wallp!=NULL;wallp=wallp->next)     /* ���1Pײǽ��2P��һ��*/
	{
        if(checkwall(*wallp,head1->x-5,head1->y)||checkwall(*wallp,head1->x+5,head1->y)||checkwall(*wallp,head1->x,head1->y+5)||checkwall(*wallp,head1->x,head1->y-5))
			{
				duogame.score2p++;
				duogame.dead1p=1;
			}
		if(checkwall(*wallp,head2->x-5,head2->y)||checkwall(*wallp,head2->x+5,head2->y)||checkwall(*wallp,head2->x,head2->y+5)||checkwall(*wallp,head2->x,head2->y-5))
			{
				duogame.score1p++;
				duogame.dead2p=1;
			}
	}
	if(distance(head1->x,head1->y,head2->x,head2->y)<RADIUS*2)	   /*�����ͷ��ײ������*/
			{
				duogame.dead1p=1;
				duogame.dead2p=1;
				return;
			}
	ps=head1->next;								/*2P���ҧ��1P�ڶ���*/
	if(distance(head2->x,head2->y,ps->x,ps->y)<RADIUS*2)	
			{
				if(0==duogame.time1)
				{
					duogame.score2p+=length(head1);
					duogame.dead1p=1;
				}
				else								/*��1P�޵�ʱ��ҧ��2P����1P��һ��*/
				{
					duogame.dead2p=1;
					duogame.score1p++;
				}
			}
    for(ps=head1->next->next;ps!=NULL;ps=ps->next) 
		if(distance(head1->x,head1->y,ps->x,ps->y)<RADIUS*2)	/* ���1Pҧ�Լ���2P��һ�� */
			{
				duogame.score2p++;
				duogame.dead1p=1;
			}
		else if(distance(head2->x,head2->y,ps->x,ps->y)<RADIUS*2)/* ���1P��2Pҧ��2P��length��*/
			{
				if(0==duogame.time1)
				{
					duogame.score2p+=length(head1);
					duogame.dead1p=1;
				}
				else								/*���޵�ʱ��ҧ���Է����ҵ�һ��*/
				{
					duogame.dead2p=1;
					duogame.score1p++;
				}
			}
	ps=head2->next;
	if(distance(head1->x,head1->y,ps->x,ps->y)<RADIUS*2)	
			{
				if(0==duogame.time1)
				{
					duogame.score1p+=length(head1);
					duogame.dead2p=1;
				}
				else								/*���޵�ʱ��ҧ���Է����ҵ�һ��*/
				{
					duogame.dead1p=1;
					duogame.score2p++;
				}
			}
	for(ps=head2->next->next;ps!=NULL;ps=ps->next) 
		if(distance(head2->x,head2->y,ps->x,ps->y)<RADIUS*2)	
			{
				duogame.score1p++;
				duogame.dead2p=1;
			}
		else if(distance(head1->x,head1->y,ps->x,ps->y)<RADIUS*2)	
			{
				if(0==duogame.time1)
				{
					duogame.score1p+=length(head1);
					duogame.dead2p=1;
				}
				else								/*���޵�ʱ��ҧ���Է����ҵ�һ��*/
				{
					duogame.dead1p=1;
					duogame.score2p++;
				}
			}
}
void clearsnake(SNAKE *head)
{
	WALL *wallp=NULL;
	SNAKE *ps=NULL;
	int i;
	setfillstyle(0,0); 
	for(ps=head;ps!=NULL;ps=ps->next)    /*������*/
		bar(ps->x-RADIUS,ps->y-RADIUS,ps->x+RADIUS,ps->y+RADIUS);
	for(i=0;i<4;i++)			/*���food���ڣ��ػ�*/
		if(1==duofood[i].exist)
		{
			setfillstyle(1,YELLOW);
            bar(duofood[i].x-5,duofood[i].y-5,duofood[i].x+5,duofood[i].y+5);
		}
	setfillstyle(1,RED);    /*��ǽ*/
    for(wallp=wallhead;wallp!=NULL;wallp=wallp->next)
        bar(wallp->x1,wallp->y1,wallp->x2,wallp->y2);
}
#endif
/*˫����Ϸ*/
void playgame(void)
{
    switch(gamemsg.mode)
    {
        case 0:
        {
            do
            {    
                newgame();
                printwall(gamemsg.screeni);
                information(gamemsg.mode);
                bioskey(0);               /*���������ʼ��Ϸ*/
                do
                {
                    information(gamemsg.mode);
                    direction(speed-speedup(speed));   /* ����speed���ٶȳ���gamemsg.direx��gamemsg.direy��ʸ���˶� */
                    eat();              /* �Ե�ʳ�ҧ���Լ���ײǽ�� */
                    profood();          /* ʳ�ﲻ��3���������� */
                }while(0==advance()&&0==gamemsg.restart);/* advance()������Ϸ���,1�����,0������*/
            }while(gamemsg.restart!=-1&&gamemsg.restart!=2);
            return;             /* gamemsg.restart=0 ��Ϸ����,-1 ��Ϸʧ��or�˳�,1 ��һ��,2 ͨȫ�� */
        }
        case 1:
        {
            newgame();
            printwall(0);
            information(gamemsg.mode);
            bioskey(0);
            do
            {
                information(gamemsg.mode);
                direction(speed-speedup(speed));
                eat();
                profood();
            }while(0==gamemsg.restart);
            return;
        }
#ifdef MAGIC
        case 2:
        {	
            printwall(0);
            information(gamemsg.mode);
			duonew();
			bioskey(0);
            do
            {
				duonew();
				duoprofood();
                information(gamemsg.mode);
                duomove(speed);
				duoeat();			
            }while(0==duogame.out);    /*��ESC���˳���*/
            return;
        }
#endif
    }
}
int closegame(void)         /*��Ϸ�������ӣ�ʧ�ܽ���orͨȫ�ؽ���������ESC��ֱ������*/
{                                        /*0��ر���Ϸ��1��R��������Ϸ*/
    int i;
    cleardevice();
    settextstyle(0,0,8);
    setcolor(RED);
    if(2==gamemsg.mode)
    {
        if(duogame.score1p>duogame.score2p)
            outtextxy(90,180,"1P WIN !");
        else if(duogame.score2p>duogame.score1p)
            outtextxy(90,180,"2P WIN !");
        else    outtextxy(130,180,"Draw !");
        goto loop;
    }
    if(2==gamemsg.restart)outtextxy(50,180,"YOU WIN !");
    else outtextxy(50,180,"GAME OVER");
loop:
    delay(30000);
    for(i=0;i<10000;i++)
         {
             if(kbhit())
                switch(bioskey(0))    /*�����R��*/
                    {
                        case 0x1352:
                        case 0x1372:
                            cleardevice();
                            return 1;
                        default :break ;
                    }
             delay(50);
         }
    closegraph();
    return 0;
}
int main()
{
    graphbegin();     /*��ʼ����*/
    randomize();      /*��������������������main��*/
    do
    {
		beforegame();     /* ѡ����Ϸģʽ����Ϸ�Ѷ� */
		playgame();   /*����Ϸ*/
    }while(closegame());      /*������Ϸ*/
    return 0;
}








