import java.util.*;
import Tools.*;

class Hill {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);					// Object Initialisation
		MethodClass ob = new MethodClass();
		System.out.println("Hello Comrade ! Let's Play with Hill Cipher");

		while(true) {
			System.out.println("Give your choice Comrade");
			System.out.println("\t1.Encryption\n\t2.Decryption\n\t3.Say GoodBye");
			int cho = sc.nextInt();
			switch(cho) {
				case 1:
					ob.encrypt();
					break;
				case 2:
					ob.decrypt();
					break;
				case 3:
					System.out.println("Ok Comrade ! GoodBye.");
					return;
				default:
					System.out.println("Sorry Comrade , There is no such thing as "+cho);
					break;
			}
		}
		
	}
}