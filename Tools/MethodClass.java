package Tools;
import java.util.*;

public class MethodClass {
	public void encrypt() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the size of Key Matrix");		// Input key matrix Size	
		int n = sc.nextInt();						

		int key[][] = new int[n][n];							// Input Key Matrix
		System.out.println("Enter Key Matrix");
		for(int i=0 ; i<n ; i++) {
			for(int j=0 ; j<n ; j++) {
				key[i][j] = sc.nextInt();
			}
		}
		if(deter(key,n) == 0) {
			System.out.println("Key is Singular , Decryption May not Exist , Try Again");
			return;
		}
		if(checkKeyOk(key,n)) {
			System.out.println("Key is Not suitable , Decryption May not be Possible , Try Again");
			return;	
		}


		System.out.println("Enter you Message");				// Input Message
		String msg = sc.nextLine();
		msg = sc.nextLine();
		msg = msg.replaceAll(" ","").toLowerCase();
		int len = msg.length();

		int xtra = modFun(len,n);							// Find Missing Bits
		xtra = modFun(n-xtra,n);

		int asciArr[] = new int[len+xtra];						// Convert Message to ASCII with Extra Bits
		for(int i=0 ; i<len ; i++) {
			asciArr[i] = (int) msg.charAt(i) - 97;
		}
		while(xtra-- > 0) {
			asciArr[len++] = 23;
		}

		int encryMsg[] = encry(key,asciArr,n,len);			// Encrypt Message

		System.out.println("Message Encrypted by Hill Cipher");	// Output Encrypted Message
		for(int i=0 ; i<len ; i++) {
			encryMsg[i] = modFun(encryMsg[i],26) + 97;
			char temp = (char)encryMsg[i] ;
			System.out.print(temp);
		}
		System.out.println();
	}

	public void decrypt() {
		Scanner sc = new Scanner(System.in);

		System.out.println("Enter the size of Key Matrix");		// Input key matrix Size	
		int n = sc.nextInt();						

		int key[][] = new int[n][n];							// Input Key Matrix
		System.out.println("Enter Key Matrix");
		for(int i=0 ; i<n ; i++) {
			for(int j=0 ; j<n ; j++) {
				key[i][j] = sc.nextInt();
			}
		}
		int det = deter(key,n);
		if(det == 0) {
			System.out.println("Key is Singular , Decryption Not Possible , Try Again");
			return;
		}
		if(checkKeyOk(key,n)) {
			System.out.println("Key is Doesn't Exist , Decryption Not Possible , Try Again");
			return;	
		}

		int inKey[][] =  clearInverse(key,n);
		int finalInKey[][] = clearConvert(inKey,n,det);

		System.out.println("Enter the Encrypted Message");				// Input Message
		String msg = sc.nextLine();
		msg = sc.nextLine();
		msg = msg.replaceAll(" ","").toLowerCase();
		int len = msg.length();

//		int xtra = ob.modFun(len,n);							// Find Missing Bits
//		xtra = ob.modFun(n-xtra,n);

		int asciArr[] = new int[len];						// Convert Message to ASCII with Extra Bits
		for(int i=0 ; i<len ; i++) {
			asciArr[i] = (int) msg.charAt(i) - 97;
		}
//		while(xtra-- > 0) {
//			asciArr[len++] = 23;
//		}

		int decryMsg[] = decry(finalInKey,asciArr,n,len);			// Decrypt Message

		System.out.println("Message Decrypted by Hill Cipher");			// Output Encrypted Message
		for(int i=0 ; i<len ; i++) {
			decryMsg[i] = modFun(decryMsg[i],26) + 97;
			char temp = (char)decryMsg[i] ;
			System.out.print(temp);
		}
		System.out.println();
	}

	public void printMatrix(int key[][],int n) {
		for(int i=0 ; i<n ; i++) {
			for(int j=0 ; j<n ; j++) {
				System.out.print(" "+key[i][j]);
			}
			System.out.println();
		}
	}

	public void printArr(int key[],int n) {
		for(int i=0 ; i<n ; i++) {
			System.out.print(" "+key[i]);
		}
	}

	public int modFun(int a,int n) {
		if(a>0) {
			return a%n;
		} else {
			return ((a%n)+n)%n;
		}
	}

	public int [] encry(int a[][], int b[], int n,int len) {
		int sol[] = new int[len];
		for(int x=0 ; x<len ; x+=n) {
			for(int i=0 ; i<n ; i++) {
				for(int k=0 ; k<n ; k++) {
					sol[x+i] += a[i][k]*b[x+k];
				}
			}
		}
		return sol;
	}

	public int deter(int A[][],int N) {
    	int det=0;
    	if(N == 1) {
    	    det = A[0][0];
    	} else if(N == 2) {
    	   	det = A[0][0]*A[1][1] - A[1][0]*A[0][1];
    	} else {
     	   	det=0;
     	   	for(int j1=0;j1<N;j1++) {
     	       int[][] m = new int[N-1][];
         	   for(int k=0;k<(N-1);k++) {
       	         	m[k] = new int[N-1];
            	}
           	 	for(int i=1;i<N;i++) {
             	   	int j2=0;
               	 	for(int j=0;j<N;j++) {
                    	if(j == j1)
                   	     	continue;
                    	m[i-1][j2] = A[i][j];
                    	j2++;
                	}
           	 	}
            	det += Math.pow(-1.0,1.0+j1+1.0)* A[0][j1] * deter(m,N-1);
        	}
    	}
    	return det;
	}

	public boolean checkKeyOk(int a[][],int n) {
		int check[] = {1,3,5,7,9,11,15,17,19,21,23,25};
		for(int i=0 ; i<12 ; i++) {
			if(modFun(deter(a,n),26) == check[i]) {
				return false;
			}	
		}
		return true;
	}

	public double[][] invert(double a[][]) 
    {
        int n = a.length;
        double x[][] = new double[n][n];
        double b[][] = new double[n][n];
        int index[] = new int[n];
        for (int i=0; i<n; ++i) 
            b[i][i] = 1;
 
        // Transform the matrix into an upper triangle
        gaussian(a, index);
 
        // Update the matrix b[i][j] with the ratios stored
        for (int i=0; i<n-1; ++i)
            for (int j=i+1; j<n; ++j)
                for (int k=0; k<n; ++k)
                    b[index[j]][k]
                    	    -= a[index[j]][i]*b[index[i]][k];
 
        // Perform backward substitutions
        for (int i=0; i<n; ++i) 
        {
            x[n-1][i] = b[index[n-1]][i]/a[index[n-1]][n-1];
            for (int j=n-2; j>=0; --j) 
            {
                x[j][i] = b[index[j]][i];
                for (int k=j+1; k<n; ++k) 
                {
                    x[j][i] -= a[index[j]][k]*x[k][i];
                }
                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }
 
    // Method to carry out the partial-pivoting Gaussian
    // elimination.  Here index[] stores pivoting order.
 
    public void gaussian(double a[][], int index[]) 
    {
        int n = index.length;
        double c[] = new double[n];
 
    // Initialize the index
        for (int i=0; i<n; ++i) 
            index[i] = i;
 
    // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) 
        {
            double c1 = 0;
            for (int j=0; j<n; ++j) 
            {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
 
    // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) 
        {
            double pi1 = 0;
            for (int i=j; i<n; ++i) 
            {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) 
                {
                    pi1 = pi0;
                    k = i;
                }
            }
 
   // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i=j+1; i<n; ++i) 	
            {
                double pj = a[index[i]][j]/a[index[j]][j];
 
    // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;
 
    // Modify other elements accordingly
                for (int l=j+1; l<n; ++l)
                    a[index[i]][l] -= pj*a[index[j]][l];
            }
        }
    }

    public int[][] clearInverse(int key[][],int n) {
    	// Find Inverse
    	int a[][] = new int[n][n];
		double invKey[][] = new double[n][n];
		for(int i=0 ; i<n ; i++) {
			for(int j=0 ; j<n ; j++) {
				invKey[i][j] = (double) key[i][j];
			}
		}
		double det = (double) deter(key,n);
		invKey = invert(invKey);
		for(int i=0 ; i<n ; i++) {
			for(int j=0 ; j<n ; j++) {
				double prt = invKey[i][j]*det;
				double roundOff = Math.round(prt * 100.0) / 100.0;
				long man = (long)roundOff;
				int wow = (int)man;
				a[i][j] = wow;
			}
		}
		return a;
    }

    public int[][] clearConvert(int inKey[][],int n,int det) { 
    	int check[] = {1,3,5,7,9,11,15,17,19,21,23,25};
    	int mulInv = 0;
    	for(int i=0 ; i<12 ; i++) {
    		if(modFun(det*check[i]-1,26) == 0) {
    			mulInv = check[i];
    			break;
    		}
    	}
    	if(mulInv == 0) {
    		return inKey;
    	}
    	for(int i=0 ; i<n ; i++) {
    		for(int j=0 ; j<n ; j++) {
    			inKey[i][j] = modFun((inKey[i][j]*mulInv),26);
    		}
    	}
    	return inKey;
    }

    public int [] decry(int a[][], int b[], int n,int len) {
		int sol[] = new int[len];
		for(int x=0 ; x<len ; x+=n) {
			for(int i=0 ; i<n ; i++) {
				for(int k=0 ; k<n ; k++) {
					sol[x+i] += a[i][k]*b[x+k];
				}
			}
		}
		return sol;
	}
}
