package practice;
import java.util.*;
class Node
{
	char key;
	int value;
	Node left,right;
	Node(char key,int value)
	{
		this.key=key;
		this.value=value;
		left=right=null;
	}
}
public class Huffman {
	
	public static void main(String args[])
	{
		Scanner input=new Scanner(System.in);
		String str=input.next();
		ArrayList<Node> al=new ArrayList<Node>();
		//Map<Integer,Character> map=new HashMap<Integer,Character>();
		TreeMap<Integer,Character> tm=new TreeMap<Integer,Character>();
		for(int i=0;i<str.length();i++)
		{
			int temp=input.nextInt();
			al.add(new Node(str.charAt(i),temp));
			tm.put(temp,str.charAt(i));
		}
		Node node=null;
		while(al.size()>1)
		{
			int i=0,sum=0;
			Node l=null;
			Node r=null;
			for(Map.Entry<Integer,Character> entry:tm.entrySet()){ 
				if(i<2)
				{
                    int c=(int)entry.getKey();
                    sum=sum+c;
                    //tm.remove(c);
                    for(int k=0;k<al.size();k++)
                    {
                    	if(al.get(k).value==c && i==0)
                    	{
                    		//System.out.print("yes");
                    		l=al.get(k);
                    		al.remove(l);
                    		break;
                    	}
                    	else if(al.get(k).value==c && i==1)
                    	{
                    		//System.out.print("no");
                    		r=al.get(k);
                    		al.remove(r);
                    		break;
                    	}
                    }
			       i++;
				}
				else
				{
					break;
				}
				//System.out.println(entry.getKey());
			   } 
			node=new Node('?',sum);
			node.left=l;
			node.right=r;
			al.add(node);
			System.out.print(l.key);
			tm.remove(l.value);
			tm.remove(r.value);
		}
		System.out.print(node.value);
	}
}
