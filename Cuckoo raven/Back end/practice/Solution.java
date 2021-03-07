package practice;
import java.util.*;
class Solution{
	static int n;
    static boolean isSafe(int[][] array,int row,int col)
    {
        int i,j;
        for(i=0;i<col;i++)
        {
            if(array[row][i]==1)
            {
                return false;
            }
        }
        for(i=row,j=col;i>=0 && j>=0;i--,j--)
        {
            if(array[i][j]==1)
            {
                return false;
            }
        }
        for(i=row,j=col;i<array[0].length && j>=0;i++,j--)
        {
            if(array[i][j]==1)
            {
                return false;
            }
        }
        return true;
    }
    static boolean solve(int[][] array,int col,int size)
    {
    	if(col==size)
    	{
    		ArrayList<Integer> arr=new ArrayList<Integer>(size);
    		 for(int x=0;x<n;x++)
             {
                 for(int y=0;y<n;y++)
                 {
                     if(array[y][x]==1)
                     {
                         arr.add(y+1);
                     }
                 }
             }
    		 System.out.println(arr);
    		if(arr.get(0)-1<size)
    		{
    			int[][] arra=new int[size][size];
    			//boolean flag=solve(arra,arr.get(0),size);
    		    //System.out.print(arr.get(0));
    		}
    		return true;
    	}
        for(int x=0;x<size;x++)
        {
            if(isSafe(array,x,col)==true)
            {
                array[x][col]=1;
                if(solve(array,col+1,size)==true)
                {
                    return true;
                }
                array[x][col]=0;
            }
        }
        return false;
    }
   public static void main(String args[]) {
        // code here
        Scanner input=new Scanner(System.in);
        n=input.nextInt();
        
         int[][] array=new int[n][n];
            boolean flag=solve(array,0,n);
        //return arr;
    }
}