
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int r[]= {1,2,3};
		int h[]={4,5,6,7};
int j[]=retunto(r,h);
	
for(int p:j)
	System.out.println(p);
	}

	
	
	
	

    private static int[] retunto(int a[],int b[]) {
    	
    	int []res=new int[a.length+b.length];
    	
    	for (int i=0;i<a.length;i++)
    		
    	{res[2*i]=a[i];
    	res[(2*i)+1]=b[i];
    		
    	}
    	return res;
    }

}
