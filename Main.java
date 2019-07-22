import java.util.*;
import java.io.*;

public class Main
{
  public static void main(String[] args)
  {
        int mover=0;
        while(mover<100000)
        {
          int n = (int)(Math.random()*10)%4;
          switch(n)
        {
          case 0:
          {
            Stick s = new Stick();
            Board.makeboundary();
            Board.printboard();
            s.takeinput();
            break;
          }
          case 1:
          {
            Square sq = new Square();
            Board.makeboundary();
            Board.printboard();
            sq.takeinput();
            break;
          }
          case 2:
          {
            L l = new L();
            Board.makeboundary();
            Board.printboard();
            l.takeinput();
            break;
          }
          case 3:
          {
            Z z = new Z();
            Board.makeboundary();
            Board.printboard();
            z.takeinput();
            break;
          }
          case 4:
          {
            T t = new T();
            Board.makeboundary();
            Board.printboard();
            t.takeinput();
            break;
          }
        }
          mover++;
          Board.delay();
          Board.clearConsole();
          Board.checkfordeleterow();
          Board.EndingCondition();
      }
  }
}


class Board
{
  static char board[][] = new char[20][20];
  char undo[] = new char[1000];
  int top=-1;
  static void makeboundary()
  {
    for(int i=0;i<19;i++)
    {
      board[i][0]='*';
      board[19][i]='*';
      board[i][19]='*';
    }
  }
  static void printboard()
  {
    for(int i=0;i<20;i++)
    {
      for(int j=0;j<20;j++)
      {
        System.out.print(board[i][j]);
      }
      System.out.println();
    }
  }
  public final static void clearConsole()
  {
    try
    {
      new ProcessBuilder("cmd" , "/c" , "cls").inheritIO().start().waitFor();
    }
    catch(Exception e)
    {

    }
  }
  public static void nullboard()
  {
    for(int i=1;i<19;i++)
    {
      for(int j=1;j<19;j++)
      {
        board[i][j]=' ';
      }
    }
  }
  static void delay()
  {
    try
   {
    Thread.sleep(100);
   }
   catch(InterruptedException ex)
   {
    Thread.currentThread().interrupt();
   }
  }
  static void checkfordeleterow()
  {
    int count=1;
    for(int i=18;i>1 && count!=0;i--)
    {
      count=0;
      for(int j=1;j<19;j++)
      {
          if(board[i][j]=='#')
          {
            count++;
          }
      }
      if(count==18)
      {
        for(int k=1;k<19;k++)
        {
          board[i][k]=' ';
        }
        for(int l=i;l>0;l--)
        {
          for(int m=1;m<19;m++)
          {
            board[l][m] = board[l-1][m];
          }
        }
      }
    }
  }
  static void EndingCondition()
  {
    for(int i=0;i<3;i++)
    {
      for(int j=1;j<19;j++)
      {
        if(board[i][j]=='#')
        {
            Board.clearConsole();
            System.out.println("Game Over");
            System.exit(0);
            break;
        }
      }
    }
  }
}


class Stick extends Board
{
  int coordinates1[][] = new int[4][2];
  int curr_version;
  public Stick()
  {
    this.curr_version = 0;
    int y = (int)(Math.random()*100)%15;
    if(y<3)
    {
        while(y<3)
        y = (int)(Math.random()*100)%15;
    }
    coordinates1[0][0]=0;
    coordinates1[0][1]=y;

    coordinates1[1][0]=1;
    coordinates1[1][1]=y;

    coordinates1[2][0]=2;
    coordinates1[2][1]=y;

    coordinates1[3][0]=3;
    coordinates1[3][1]=y;
    putonboard();
  }
  void putonboard()
  {
        for(int i=0;i<4;i++)
        {
          for(int j=0;j<1;j++)
          {
            board[coordinates1[i][j]][coordinates1[i][j+1]] ='#';
          }
        }
    }
  int getversion()
  {
    return curr_version;
  }
  void makestickv2()
  {
        curr_version = 1;

        coordinates1[1][0]=coordinates1[0][0];
        coordinates1[1][1]=coordinates1[0][1]+1;


        coordinates1[2][0]=coordinates1[0][0];
        coordinates1[2][1]=coordinates1[0][1]+2;


        coordinates1[3][0]=coordinates1[0][0];
        coordinates1[3][1]=coordinates1[0][1]+3;
   }
   void takeinput()
   {
     int stop = 1;
     int top=-1;
     Scanner sc = new Scanner(System.in);
     while(board[coordinates1[3][0]+1][coordinates1[3][1]]!='*' && board[coordinates1[2][0]+1][coordinates1[2][1]]!='*' && board[coordinates1[1][0]+1][coordinates1[1][1]]!='*' && board[coordinates1[0][0]+1][coordinates1[0][1]]!='*'
      && board[coordinates1[3][0]+1][coordinates1[3][1]]!='#' && stop==1)
     {
        char ip = sc.next().charAt(0);
        Board.clearConsole();
        if(ip=='s')
        {
            top++;
            undo[top]='s';
            removefromboard();
            movedown();
            putonboard();
            Board.printboard();
            stop = stoppingCondition();
        }
        else if(ip=='d')
        {
          top++;
          undo[top]='d';
          removefromboard();
          moveright();
          putonboard();
          Board.printboard();
        }
        else if(ip=='a')
        {
          top++;
          undo[top]='a';
          removefromboard();
          moveleft();
          putonboard();
          Board.printboard();
        }
        else if(ip=='w')
        {
          top++;
          undo[top]='w';
          removefromboard();
          int v = getversion();
          if(v==1)
          {
            makestickv1();
          }
          else if(v==0 && coordinates1[3][1]<=15)
          {
          makestickv2();
          }
          putonboard();
          Board.printboard();
        }
        else if(ip=='q' && top<0)
        {
          Board.printboard();
        }
        else if(ip=='q' )
        {
          System.out.println(top);
          char op = undo[top];
          top--;
          if(op=='d')
          {
            removefromboard();
            moveleft();
            putonboard();
            Board.printboard();
          }
          else if(op=='s')
          {
                removefromboard();
                moveup();
                putonboard();
                Board.printboard();
          }
          else if(op=='a')
          {
            removefromboard();
            moveright();
            putonboard();
            Board.printboard();
          }
          else if(op=='w')
          {
            removefromboard();
            int v = getversion();
            if(v==1)
            {
              makestickv1();
            }
            else if(v==0 && coordinates1[3][1]<=15)
            {
            makestickv2();
            }
            putonboard();
            Board.printboard();
          }
        }
      }
     }
   void movedown()
   {
     coordinates1[0][0]+=1;
     coordinates1[1][0]+=1;
     coordinates1[2][0]+=1;
     coordinates1[3][0]+=1;
   }
   void moveright()
   {
     if(board[coordinates1[3][0]][coordinates1[3][1]+1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]+1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]+1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]+1]!='*')
     {
     coordinates1[0][1]+=1;
     coordinates1[1][1]+=1;
     coordinates1[2][1]+=1;
     coordinates1[3][1]+=1;
     }
   }
   void moveleft()
   {
     if(board[coordinates1[3][0]][coordinates1[3][1]-1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]-1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]-1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]-1]!='*')
     {
     coordinates1[0][1]-=1;
     coordinates1[1][1]-=1;
     coordinates1[2][1]-=1;
     coordinates1[3][1]-=1;
     }
   }
   void removefromboard()
   {
     for(int i=0;i<4;i++)
     {
       for(int j=0;j<1;j++)
       {
         board[coordinates1[i][j]][coordinates1[i][j+1]] = ' ';
       }
     }
   }
   void makestickv1()
   {
     curr_version = 0;

     coordinates1[1][0]=coordinates1[0][0]+1;
     coordinates1[1][1]=coordinates1[0][1];


     coordinates1[2][0]=coordinates1[0][0]+2;
     coordinates1[2][1]=coordinates1[0][1];


     coordinates1[3][0]=coordinates1[0][0]+3;
     coordinates1[3][1]=coordinates1[0][1];
   }
   void moveup()
   {
     coordinates1[0][0]-=1;
     coordinates1[1][0]-=1;
     coordinates1[2][0]-=1;
     coordinates1[3][0]-=1;
   }
   int stoppingCondition()
   {
     int flag=1;
     for(int i=0;i<4&&flag==1;i++)
     {
       if(board[coordinates1[i][0]+1][coordinates1[i][1]]=='#')
       {
         flag=0;
         int x = coordinates1[i][0]+1;
         int y = coordinates1[i][1];
         // System.out.println(x+" "+y);
         for(int j=0;j<4;j++)
         {
           if(coordinates1[j][0]==x && coordinates1[j][1]==y)
           {
             flag=1;
           }
         }
       }
     }
     return flag;
   }
}

class Square extends Board
{
  int coordinates1[][] = new int [4][2];
  Square()
  {
    int y = (int)(Math.random()*100)%15;
    if(y<3)
    {
        while(y<3)
         y = (int)(Math.random()*100)%15;
    }
    coordinates1[0][0] = 1;
    coordinates1[0][1] = y;

    coordinates1[1][0] = 1;
    coordinates1[1][1] = y+1;

    coordinates1[2][0] = 2;
    coordinates1[2][1] = y;

    coordinates1[3][0] = 2;
    coordinates1[3][1] = y+1;
    putonboard();
  }
  void putonboard()
  {
    for(int i=0;i<4;i++)
    {
      for(int j=0;j<1;j++)
      {
        board[coordinates1[i][j]][coordinates1[i][j+1]] ='#';
      }
    }
  }
  void takeinput()
  {
    int stop=1;
    int top=-1;
    Scanner sc = new Scanner(System.in);
    while(board[coordinates1[3][0]+1][coordinates1[3][1]]!='*' && board[coordinates1[2][0]+1][coordinates1[2][1]]!='*' && board[coordinates1[1][0]+1][coordinates1[1][1]]!='*' && board[coordinates1[0][0]+1][coordinates1[0][1]]!='*'
     && board[coordinates1[3][0]+1][coordinates1[3][1]]!='#' && stop==1)
    {
       char ip = sc.next().charAt(0);
       Board.clearConsole();
       if(ip=='s')
       {
           top++;
           undo[top]='s';
           removefromboard();
           movedown();
           putonboard();
           Board.printboard();
           stop = stoppingCondition();
       }
       else if(ip=='d')
       {
         top++;
         undo[top]='d';
         removefromboard();
         moveright();
         putonboard();
         Board.printboard();
       }
       else if(ip=='a')
       {
         top++;
         undo[top]='a';
         removefromboard();
         moveleft();
         putonboard();
         Board.printboard();
       }
       else if(ip=='w')
       {
         top++;
         undo[top]='w';
         Board.printboard();
       }
       else if(ip=='q' && top<0)
       {
         Board.printboard();
       }
       else if(ip=='q' )
       {
         System.out.println(top);
         char op = undo[top];
         top--;
         if(op=='d')
         {
           removefromboard();
           moveleft();
           putonboard();
           Board.printboard();
         }
         else if(op=='s')
         {
               removefromboard();
               moveup();
               putonboard();
               Board.printboard();
         }
         else if(op=='a')
         {
           removefromboard();
           moveright();
           putonboard();
           Board.printboard();
         }
         else if(op=='w')
         {
           Board.printboard();
         }
       }
    }
  }
  void movedown()
  {
    coordinates1[0][0]+=1;
    coordinates1[1][0]+=1;
    coordinates1[2][0]+=1;
    coordinates1[3][0]+=1;
  }
  void moveright()
  {
    if(board[coordinates1[3][0]][coordinates1[3][1]+1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]+1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]+1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]+1]!='*')
    {
    coordinates1[0][1]+=1;
    coordinates1[1][1]+=1;
    coordinates1[2][1]+=1;
    coordinates1[3][1]+=1;
    }
  }
  void moveup()
  {
    coordinates1[0][0]-=1;
    coordinates1[1][0]-=1;
    coordinates1[2][0]-=1;
    coordinates1[3][0]-=1;
  }
  void moveleft()
  {
    if(board[coordinates1[3][0]][coordinates1[3][1]-1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]-1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]-1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]-1]!='*')
    {
    coordinates1[0][1]-=1;
    coordinates1[1][1]-=1;
    coordinates1[2][1]-=1;
    coordinates1[3][1]-=1;
    }
  }
  void removefromboard()
  {
    for(int i=0;i<4;i++)
    {
      for(int j=0;j<1;j++)
      {
        board[coordinates1[i][j]][coordinates1[i][j+1]] = ' ';
      }
    }
  }
  int stoppingCondition()
  {
    int flag=1;
    for(int i=0;i<4&&flag==1;i++)
    {
      if(board[coordinates1[i][0]+1][coordinates1[i][1]]=='#')
      {
        flag=0;
        int x = coordinates1[i][0]+1;
        int y = coordinates1[i][1];
        // System.out.println(x+" "+y);
        for(int j=0;j<4;j++)
        {
          if(coordinates1[j][0]==x && coordinates1[j][1]==y)
          {
            flag=1;
          }
        }
      }
    }
    return flag;
  }
}

class L extends Board
{
  int coordinates1[][] = new int[4][2];
  int curr_version;
  L()
  {
    curr_version = 1;
    int y = (int)(Math.random()*100)%15;
    if(y<3)
    {
        while(y<3)
         y = (int)(Math.random()*100)%15;
    }
    coordinates1[0][0] = 0;
    coordinates1[0][1] = y;

    coordinates1[1][0] = 1;
    coordinates1[1][1] = y;

    coordinates1[2][0] = 2;
    coordinates1[2][1] = y;

    coordinates1[3][0] = 2;
    coordinates1[3][1] = y+1;
    putonboard();
  }
  void putonboard()
  {
    for(int i=0;i<4;i++)
    {
      for(int j=0;j<1;j++)
      {
        board[coordinates1[i][j]][coordinates1[i][j+1]] ='#';
      }
    }
  }
  int getversion()
  {
    return curr_version;
  }
  void makelv1()
  {
    curr_version = 1;

    coordinates1[0][0] = coordinates1[3][0];
    coordinates1[0][1] = coordinates1[3][1];

    coordinates1[1][0] = coordinates1[3][0]+1;
    coordinates1[1][1] = coordinates1[0][1];

    coordinates1[2][0] = coordinates1[3][0]+2;
    coordinates1[2][1] = coordinates1[0][1];

    coordinates1[3][0] = coordinates1[2][0];
    coordinates1[3][1] = coordinates1[2][1]+1;

  }
  void makeLv2()
  {
    curr_version = 2;

    coordinates1[0][0] = coordinates1[3][0];
    coordinates1[0][1] = coordinates1[2][1];

    coordinates1[1][0] = coordinates1[3][0];
    coordinates1[1][1] = coordinates1[2][1]+1;

    coordinates1[2][0] = coordinates1[3][0];
    coordinates1[2][1] = coordinates1[2][1]+2;

    coordinates1[3][0] = coordinates1[2][0]-1;
    coordinates1[3][1] = coordinates1[2][1];
  }
  void makelv3()
  {
    curr_version = 3;

    coordinates1[0][0] = coordinates1[3][0];
    coordinates1[0][1] = coordinates1[3][1];

    coordinates1[1][0] = coordinates1[0][0]-1;
    coordinates1[1][1] = coordinates1[3][1];

    coordinates1[2][0] = coordinates1[0][0]-2;
    coordinates1[2][1] = coordinates1[3][1];

    coordinates1[3][0] = coordinates1[2][0];
    coordinates1[3][1] = coordinates1[2][1]-1;

  }
  void maakelv4()
  {
      curr_version = 4;

      coordinates1[0][0] = coordinates1[3][0];
      coordinates1[0][1] = coordinates1[3][1];

      coordinates1[1][0] = coordinates1[3][0];
      coordinates1[1][1] = coordinates1[3][1]-1;

      coordinates1[2][0] = coordinates1[3][0];
      coordinates1[2][1] = coordinates1[3][1]-2;

      coordinates1[3][0] = coordinates1[2][0]+1;
      coordinates1[3][1] = coordinates1[2][1];
  }
  void takeinput()
  {
    Scanner sc = new Scanner(System.in);
    int stop=1;
    int top=-1;
    while(board[coordinates1[3][0]+1][coordinates1[3][1]]!='*' && board[coordinates1[2][0]+1][coordinates1[2][1]]!='*' && board[coordinates1[1][0]+1][coordinates1[1][1]]!='*' && board[coordinates1[0][0]+1][coordinates1[0][1]]!='*' && stop==1)
    {
       char ip = sc.next().charAt(0);
       Board.clearConsole();
       if(ip=='s')
       {
           top++;
           undo[top]='s';
           removefromboard();
           movedown();
           putonboard();
           Board.printboard();
           stop = stoppingCondition();
       }
       else if(ip=='d')
       {
         top++;
         undo[top]='d';
         removefromboard();
         moveright();
         putonboard();
         Board.printboard();
       }
       else if(ip=='a')
       {
         top++;
         undo[top]='a';
         removefromboard();
         moveleft();
         putonboard();
         Board.printboard();
       }
       else if(ip=='w')
       {
         top++;
         undo[top]='w';
         removefromboard();
         int v = getversion();
         if(v==1)
         {
           makeLv2();
         }
         else if(v==2)
         {
           makelv3();
         }
         else if(v==3)
         {
           maakelv4();
         }
         else if(v==4)
         {
           makelv1();
         }
         putonboard();
         Board.printboard();
       }
       else if(ip=='q' && top<0)
       {
         Board.printboard();
       }
       else if(ip=='q' )
       {
         System.out.println(top);
         char op = undo[top];
         top--;
         if(op=='d')
         {
           removefromboard();
           moveleft();
           putonboard();
           Board.printboard();
         }
         else if(op=='s')
         {
               removefromboard();
               moveup();
               putonboard();
               Board.printboard();
         }
         else if(op=='a')
         {
           removefromboard();
           moveright();
           putonboard();
           Board.printboard();
         }
         else if(op=='w')
         {
           removefromboard();
           int v = getversion();
           if(v==1)
           {
             maakelv4();
           }
           else if(v==2)
           {
           makelv1();
           }
           else if(v==3)
           {
             makeLv2();
           }
           else if(v==4)
           {
             makelv1();
           }
           putonboard();
           Board.printboard();
         }
       }
    }
  }
  void movedown()
  {
    coordinates1[0][0]+=1;
    coordinates1[1][0]+=1;
    coordinates1[2][0]+=1;
    coordinates1[3][0]+=1;
  }
  void moveright()
  {
    if(board[coordinates1[3][0]][coordinates1[3][1]+1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]+1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]+1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]+1]!='*')
    {
    coordinates1[0][1]+=1;
    coordinates1[1][1]+=1;
    coordinates1[2][1]+=1;
    coordinates1[3][1]+=1;
    }
  }
  void moveup()
  {
    coordinates1[0][0]-=1;
    coordinates1[1][0]-=1;
    coordinates1[2][0]-=1;
    coordinates1[3][0]-=1;
  }
  void moveleft()
  {
    if(board[coordinates1[3][0]][coordinates1[3][1]-1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]-1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]-1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]-1]!='*')
    {
    coordinates1[0][1]-=1;
    coordinates1[1][1]-=1;
    coordinates1[2][1]-=1;
    coordinates1[3][1]-=1;
    }
  }
  void removefromboard()
  {
    for(int i=0;i<4;i++)
    {
      for(int j=0;j<1;j++)
      {
        board[coordinates1[i][j]][coordinates1[i][j+1]] = ' ';
      }
    }
  }
  int stoppingCondition()
  {
    int flag=1;
    for(int i=0;i<4&&flag==1;i++)
    {
      if(board[coordinates1[i][0]+1][coordinates1[i][1]]=='#')
      {
        flag=0;
        int x = coordinates1[i][0]+1;
        int y = coordinates1[i][1];
        // System.out.println(x+" "+y);
        for(int j=0;j<4;j++)
        {
          if(coordinates1[j][0]==x && coordinates1[j][1]==y)
          {
            flag=1;
          }
        }
      }
    }
    return flag;
  }
}

class T extends Board
{
  int coordinates1[][] = new int[4][2];
  int curr_version;
  T()
  {
    curr_version = 1;
    int y = (int)(Math.random()*100)%15;
    if(y==0)
    {
      while(y<3)
      y = (int)(Math.random()*100)%15;
    }
    coordinates1[0][0] = 0;
    coordinates1[0][1] = y;

    coordinates1[1][0] = 0;
    coordinates1[1][1] = y+1;

    coordinates1[2][0] = 0;
    coordinates1[2][1] = y+2;

    coordinates1[3][0] = 1;
    coordinates1[3][1] = y+1;
    putonboard();
  }
  void putonboard()
  {
    for(int i=0;i<4;i++)
    {
      for(int j=0;j<1;j++)
      {
        board[coordinates1[i][j]][coordinates1[i][j+1]] ='#';
      }
    }
  }
  void maketv1()
  {
      curr_version = 1;

      coordinates1[0][0] = coordinates1[3][0];
      coordinates1[0][1] = coordinates1[3][1];

      coordinates1[1][0] = coordinates1[3][0];
      coordinates1[1][1] = coordinates1[3][1]+1;

      coordinates1[2][0] = coordinates1[3][0];
      coordinates1[2][1] = coordinates1[3][1]+2;

      coordinates1[3][0] = coordinates1[1][0]+1;
      coordinates1[3][1] = coordinates1[1][1];
  }
  void maketv2()
  {
    curr_version = 2;

    coordinates1[1][0] = coordinates1[0][0]+1;
    coordinates1[1][1] = coordinates1[0][1];

    coordinates1[2][0] = coordinates1[0][0]+2;
    coordinates1[2][1] = coordinates1[0][1];

    coordinates1[3][0] = coordinates1[1][0];
    coordinates1[3][1] = coordinates1[1][1]+1;
  }
  void maketv3()
  {
    curr_version = 3;

    coordinates1[0][0] = coordinates1[0][0]+1;

    coordinates1[1][1] = coordinates1[1][1]+1;

    coordinates1[2][0] = coordinates1[1][0];
    coordinates1[2][1] = coordinates1[1][1]+1;

    coordinates1[3][0] = coordinates1[1][0]-1;
    coordinates1[3][1] = coordinates1[1][1];
  }
  void maketv4()
  {
    curr_version = 4;

    coordinates1[0][0] = coordinates1[3][0];
    coordinates1[0][1] = coordinates1[3][1];

    coordinates1[1][0] = coordinates1[3][0]+1;
    coordinates1[1][1] = coordinates1[3][1];

    coordinates1[2][0] = coordinates1[3][0]+2;
    coordinates1[2][1] = coordinates1[3][1];

    coordinates1[3][0] = coordinates1[1][0];
    coordinates1[3][1] = coordinates1[1][1]-1;

  }
  void takeinput()
  {
    int stop=1;
    Scanner sc = new Scanner(System.in);
    while(board[coordinates1[3][0]+1][coordinates1[3][1]]!='*' && board[coordinates1[2][0]+1][coordinates1[2][1]]!='*' && board[coordinates1[1][0]+1][coordinates1[1][1]]!='*' && board[coordinates1[0][0]+1][coordinates1[0][1]]!='*' && stop==1)
    {
       char ip = sc.next().charAt(0);
       Board.clearConsole();
       if(ip=='s')
       {
         top++;
         undo[top]='s';
           removefromboard();
           movedown();
           putonboard();
           Board.printboard();
           stop = stoppingCondition();
       }
       else if(ip=='d')
       {
         top++;
         undo[top]='d';
         removefromboard();
         moveright();
         putonboard();
         Board.printboard();
       }
       else if(ip=='a')
       {
         top++;
         undo[top]='a';
         removefromboard();
         moveleft();
         putonboard();
         Board.printboard();
       }
       else if(ip=='w')
       {
         top++;
         undo[top]='w';
         removefromboard();
         int i = getversion();
         if(i==1)
         {
           maketv2();
         }
         else if(i==2)
         {
           maketv3();
         }
         else if(i==3)
         {
           maketv4();
         }
         else if(i==4)
         {
           maketv1();
         }
         putonboard();
         Board.printboard();
       }
       else if(ip=='q' && top<0)
       {
         Board.printboard();
       }
       else if(ip=='q' )
       {
         System.out.println(top);
         char op = undo[top];
         top--;
         if(op=='d')
         {
           removefromboard();
           moveleft();
           putonboard();
           Board.printboard();
         }
         else if(op=='s')
         {
               removefromboard();
               moveup();
               putonboard();
               Board.printboard();
         }
         else if(op=='a')
         {
           removefromboard();
           moveright();
           putonboard();
           Board.printboard();
         }
         else if(op=='w')
         {
           removefromboard();
           int v = getversion();
           if(v==1)
           {
             maketv4();
           }
           else if(v==2)
           {
           maketv1();
           }
           else if(v==3)
           {
             maketv2();
           }
           else if(v==4)
           {
             maketv3();
           }
           putonboard();
           Board.printboard();
         }
       }
    }
  }
  int getversion()
  {
    return curr_version;
  }
  void movedown()
  {
    coordinates1[0][0]+=1;
    coordinates1[1][0]+=1;
    coordinates1[2][0]+=1;
    coordinates1[3][0]+=1;
  }
  void moveright()
  {
    if(board[coordinates1[3][0]][coordinates1[3][1]+1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]+1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]+1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]+1]!='*')
    {
    coordinates1[0][1]+=1;
    coordinates1[1][1]+=1;
    coordinates1[2][1]+=1;
    coordinates1[3][1]+=1;
    }
  }
  void moveup()
  {
    coordinates1[0][0]-=1;
    coordinates1[1][0]-=1;
    coordinates1[2][0]-=1;
    coordinates1[3][0]-=1;
  }
  void moveleft()
  {
    if(board[coordinates1[3][0]][coordinates1[3][1]-1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]-1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]-1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]-1]!='*')
    {
    coordinates1[0][1]-=1;
    coordinates1[1][1]-=1;
    coordinates1[2][1]-=1;
    coordinates1[3][1]-=1;
    }
  }
  void removefromboard()
  {
    for(int i=0;i<4;i++)
    {
      for(int j=0;j<1;j++)
      {
        board[coordinates1[i][j]][coordinates1[i][j+1]] = ' ';
      }
    }
  }
  int stoppingCondition()
  {
    int flag=1;
    for(int i=0;i<4&&flag==1;i++)
    {
      if(board[coordinates1[i][0]+1][coordinates1[i][1]]=='#')
      {
        flag=0;
        int x = coordinates1[i][0]+1;
        int y = coordinates1[i][1];
        // System.out.println(x+" "+y);
        for(int j=0;j<4;j++)
        {
          if(coordinates1[j][0]==x && coordinates1[j][1]==y)
          {
            flag=1;
          }
        }
      }
    }
    return flag;
  }
}

class Z extends Board
{
  int coordinates1[][] = new int[4][2];
  int curr_version;
  Z()
  {
    curr_version = 1;
    int y = (int)(Math.random()*100)%15;
    if(y<3)
    {
        while(y<3)
        y = (int)(Math.random()*100)%15;
    }
    coordinates1[0][0] = 0;
    coordinates1[0][1] = y;

    coordinates1[1][0] = 0;
    coordinates1[1][1] = y+1;

    coordinates1[2][0] = 1;
    coordinates1[2][1] = y+1;

    coordinates1[3][0] = 1;
    coordinates1[3][1] = y+2;
    putonboard();
  }
  void putonboard()
  {
  for(int i=0;i<4;i++)
  {
    for(int j=0;j<1;j++)
    {
      board[coordinates1[i][j]][coordinates1[i][j+1]] ='#';
    }
  }
 }
 void makezv2()
 {
   curr_version = 2;

   coordinates1[0][0] = coordinates1[1][0];
   coordinates1[0][1] = coordinates1[1][1];

   coordinates1[1][0] = coordinates1[2][0];
   coordinates1[1][1] = coordinates1[2][1];

   coordinates1[2][0] = coordinates1[3][0];
   coordinates1[2][1] = coordinates1[3][1];

   coordinates1[3][0] = coordinates1[2][0]+1;
   coordinates1[3][1] = coordinates1[2][1];
 }
 void makezv1()
 {
   curr_version = 1;

   coordinates1[3][0] = coordinates1[2][0];
   coordinates1[3][1] = coordinates1[2][1];

   coordinates1[2][0] = coordinates1[1][0];
   coordinates1[2][1] = coordinates1[1][1];

   coordinates1[1][0]=coordinates1[0][0];
   coordinates1[1][1]=coordinates1[0][1];

   coordinates1[0][0] = coordinates1[0][0];
   coordinates1[0][1] = coordinates1[0][1]-1;
 }
   void takeinput()
   {
     int stop=1;
     Scanner sc = new Scanner(System.in);
     while(board[coordinates1[3][0]+1][coordinates1[3][1]]!='*' && board[coordinates1[2][0]+1][coordinates1[2][1]]!='*' && board[coordinates1[1][0]+1][coordinates1[1][1]]!='*' && board[coordinates1[0][0]+1][coordinates1[0][1]]!='*'
      && board[coordinates1[3][0]+1][coordinates1[3][1]]!='#' && stop==1)
     {
        char ip = sc.next().charAt(0);
        Board.clearConsole();
        if(ip=='s')
        {
          top++;
          undo[top]='s';
            removefromboard();
            movedown();
            putonboard();
            Board.printboard();
            stop = stoppingCondition();
        }
        else if(ip=='d')
        {
          top++;
          undo[top]='d';
          removefromboard();
          moveright();
          putonboard();
          Board.printboard();
        }
        else if(ip=='a')
        {
          top++;
          undo[top]='a';
          removefromboard();
          moveleft();
          putonboard();
          Board.printboard();
        }
        else if(ip=='w')
        {
          top++;
          undo[top]='w';
          removefromboard();
          int i = getversion();
          if(i==1)
          {
            makezv2();
          }
          else if(i==2)
          {
            makezv1();
          }
          putonboard();
          Board.printboard();
        }
        else if(ip=='q' && top<0)
        {
          Board.printboard();
        }
        else if(ip=='q' )
        {
          System.out.println(top);
          char op = undo[top];
          top--;
          if(op=='d')
          {
            removefromboard();
            moveleft();
            putonboard();
            Board.printboard();
          }
          else if(op=='s')
          {
                removefromboard();
                moveup();
                putonboard();
                Board.printboard();
          }
          else if(op=='a')
          {
            removefromboard();
            moveright();
            putonboard();
            Board.printboard();
          }
          else if(op=='w')
          {
            removefromboard();
            int v = getversion();
            if(v==1)
            {
              makezv2();
            }
            else if(v==2)
            {
            makezv1();
            }
            putonboard();
            Board.printboard();
          }
        }
     }
   }
   int getversion()
   {
     return curr_version;
   }
   void movedown()
   {
     coordinates1[0][0]+=1;
     coordinates1[1][0]+=1;
     coordinates1[2][0]+=1;
     coordinates1[3][0]+=1;
   }
   void moveright()
   {
     if(board[coordinates1[3][0]][coordinates1[3][1]+1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]+1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]+1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]+1]!='*')
     {
     coordinates1[0][1]+=1;
     coordinates1[1][1]+=1;
     coordinates1[2][1]+=1;
     coordinates1[3][1]+=1;
     }
   }
   void moveleft()
   {
     if(board[coordinates1[3][0]][coordinates1[3][1]-1]!='*' && board[coordinates1[2][0]][coordinates1[2][1]-1]!='*' &&  board[coordinates1[1][0]][coordinates1[1][1]-1]!='*' &&  board[coordinates1[0][0]][coordinates1[0][1]-1]!='*')
     {
     coordinates1[0][1]-=1;
     coordinates1[1][1]-=1;
     coordinates1[2][1]-=1;
     coordinates1[3][1]-=1;
     }
   }
   void moveup()
   {
     coordinates1[0][0]-=1;
     coordinates1[1][0]-=1;
     coordinates1[2][0]-=1;
     coordinates1[3][0]-=1;
   }
   void removefromboard()
   {
     for(int i=0;i<4;i++)
     {
       for(int j=0;j<1;j++)
       {
         board[coordinates1[i][j]][coordinates1[i][j+1]] = ' ';
       }
     }
   }
   int stoppingCondition()
   {
     int flag=1;
     for(int i=0;i<4&&flag==1;i++)
     {
       if(board[coordinates1[i][0]+1][coordinates1[i][1]]=='#')
       {
         flag=0;
         int x = coordinates1[i][0]+1;
         int y = coordinates1[i][1];
         // System.out.println(x+" "+y);
         for(int j=0;j<4;j++)
         {
           if(coordinates1[j][0]==x && coordinates1[j][1]==y)
           {
             flag=1;
           }
         }
       }
     }
     return flag;
   }
}
