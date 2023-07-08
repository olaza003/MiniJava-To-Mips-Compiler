class Main {
	public static void main(String[] a){
		System.out.println(new A().run());
	}
}

class A {
	int num;
	public int run() { //int -> boolean
		int x;
		boolean z;
		int y;
		x = 1; //1
		y = x + 1;
		return x;
	}
 
	public int too(){
		return 1;
	}/*
}

class B{
	public int yes(int x){
		return 2;
	} */
}

/*
 * just wants: A -> run:int
 * 
 */
