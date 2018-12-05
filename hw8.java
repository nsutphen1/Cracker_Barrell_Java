import java.util.*;
//Hello World
class Puzzle {
	//All of them are public. It's not the most secure, but it will get the job done easier
	public int[] curPuzzle;
	public int[][] lessMoves; //I'm going to move this into an arrayList soon to generate all possible moves. But not yet
	public ArrayList<int[]> moveSet; //The previous moved made in the puzzle 
	public int size; //Number of pegs remaining in the puzzle
	public ArrayList<int[]> moves; //Will contain all moves 
	public Puzzle(){
		curPuzzle = new int[15];
		Random random = new Random();
		init(random.nextInt(15)%15);
		init(0);
		moveSet = new ArrayList<int[]>();
		moves = new ArrayList<int[]>();
		size = 14; //15 holes, 14 pegs
		//From, Over, To 
		lessMoves = new int[][] {
				{0,1,3},
				{0,2,5},
				{1,3,6},
				{1,4,8},
				{2,4,7},
				{2,5,9},
				{3,6,10},
				{3,7,12},
				{4,7,11},
				{4,8,13},
				{5,8,12},
				{5,9,14},
				{3,4,5},
				{6,7,8},
				{7,8,9},
				{10,11,12},
				{11,12,13},
				{12,13,14}
				};
		allMoves();		
	}
	public void setMoveset(ArrayList<int[]> toSet){
		moveSet = toSet;
	}
	public void setPuzzle(int[] puz){
		curPuzzle=puz;
	}
	//This method will initialize the puzzle
	public void init(int start){
		for(int i=0; i<15; i++){
			if(i!=start){
				curPuzzle[i]=1;
			}
			else{
				curPuzzle[i]=0;
			}
		}
	}
	//returns copy of arrayList 
	public ArrayList getMoves(){
		ArrayList<int[]> toReturn = new ArrayList<int[]>(moves);
		return toReturn;
	}
	//Returns copy of moveset 
	public ArrayList getMoveSet(){
		ArrayList<int[]> toReturn = new ArrayList<int[]>(moveSet);
		return toReturn;
	}
	//Returns copy of puzzle 
	public int[] getPuzzle(){
		int[] toReturn = new int[15];
		toReturn = Arrays.copyOf(curPuzzle,curPuzzle.length);
		return toReturn;
	}
	public int size(){
		return size;
	}
	//This method will generate all possible moves (The given moveset and the reverse) 
	public void allMoves(){
		for(int i=0; i<lessMoves.length; i++){
			int[] tmp1 = lessMoves[i];
			int[] tmp2 = new int[] {tmp1[2],tmp1[1],tmp1[0]};
			moves.add(tmp1);
			moves.add(tmp2);
		}
	}
	//Moves peg given the int array move 
	public boolean move(int[] toMove){
		if(!(curPuzzle[toMove[0]]==1 && curPuzzle[toMove[1]]==1 && curPuzzle[toMove[2]]==0)){
			return false;
		}
		int[] tmp = new int[15];
		tmp = Arrays.copyOf(curPuzzle,curPuzzle.length);
		moveSet.add(tmp);
		curPuzzle[toMove[0]]=0;
		curPuzzle[toMove[1]]=0;
		curPuzzle[toMove[2]]=1;
		size--;
		return true;
	}
	//Shows all states of the puzzle 
	public void showAll(){
		for(int i=0; i<moveSet.size(); i++){
			show(moveSet.get(i));
		}
	}
	//Shows the puzzle, same logic as python function
	public void showNow(){
		int[][] lines = new int[][]{{4,0,0},{3,1,2},{2,3,5},{1,6,9},{0,10,14}};
		for(int[] l: lines){
			int t=l[0];
			int a=l[1];
			int b=l[2];
			for(int i=0; i<t; i++){
				System.out.print(" ");
			}
			for(int i=a; i<=b; i++){
				if(curPuzzle[i]==0){
					System.out.print(". ");
				}
				else{
					System.out.print("x ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
	//Shows the puzzle, same logic as python function
	public void show(int[] puzzle){
		int[][] lines = new int[][]{{4,0,0},{3,1,2},{2,3,5},{1,6,9},{0,10,14}};
		for(int[] l: lines){
			int t=l[0];
			int a=l[1];
			int b=l[2];
			for(int i=0; i<t; i++){
				System.out.print(" ");
			}
			for(int i=a; i<=b; i++){
				if(puzzle[i]==0){
					System.out.print(". ");
				}
				else{
					System.out.print("x ");
				}
			}
			System.out.println();
		}
		System.out.println();
	}
}
//This class will save one of the solved puzzle states and print it out.
class Solver{
	public Puzzle Final; //Solved puzzle 
	public boolean found;
	public static void main(String args[]){
		Solver solver = new Solver();
		solver.run();
	}
	//This function runs through all possible itterations of the puzzle 
	public void run(){
		for(int i=0; i<15; i++){
			found=false;
			System.out.println("===="+i+"====");
			Puzzle puzzle = new Puzzle();
			puzzle.init(i);
			solve(puzzle);
			Final.showAll();
			Final.showNow();
			Final = new Puzzle();
			System.out.println();
			System.out.println();
			System.out.println();
		}
	}
	public Solver(){
		Final = new Puzzle();
		found=false;
	}
	public void solve(Puzzle x){
		ArrayList<int[]> moves = x.getMoves();
		if(x.size()>1&&!found){
			for(int i=0; i<moves.size(); i++){
				Puzzle tmp = new Puzzle();
				tmp.setPuzzle(x.getPuzzle());
				tmp.setMoveset(x.getMoveSet());
				tmp.size=x.size();
				if(tmp.move(moves.get(i))){
					solve(tmp);
				}
			}
		}
		if(x.size()==1){
			Final=x;
			found=true;
		}
	}
	public Puzzle getFinal(){
		return Final;
	}
}

/*Usage:
>java -cp . Solver
====0====
    . 
   x x 
  x x x 
 x x x x 
x x x x x 

    x 
   . x 
  . x x 
 x x x x 
x x x x x 

    x 
   x x 
  . . x 
 x x . x 
x x x x x 

    x 
   x x 
  x . x 
 . x . x 
. x x x x 

    x 
   . x 
  . . x 
 x x . x 
. x x x x 

    x 
   . x 
  . x x 
 x . . x 
. . x x x 

    x 
   . . 
  . . x 
 x x . x 
. . x x x 

    x 
   . x 
  . . . 
 x x . . 
. . x x x 

    . 
   . . 
  . . x 
 x x . . 
. . x x x 

    . 
   . . 
  . . x 
 . . x . 
. . x x x 

    . 
   . . 
  . . x 
 . . x . 
. x . . x 

    . 
   . . 
  . . . 
 . . . . 
. x x . x 

    . 
   . . 
  . . . 
 . . . . 
. . . x x 

    . 
   . . 
  . . . 
 . . . . 
. . x . . 




====1====
    x 
   . x 
  x x x 
 x x x x 
x x x x x 

    x 
   x x 
  . x x 
 . x x x 
x x x x x 

    . 
   . x 
  x x x 
 . x x x 
x x x x x 

    . 
   x x 
  x . x 
 . x . x 
x x x x x 

    . 
   . x 
  . . x 
 x x . x 
x x x x x 

    . 
   . x 
  x . x 
 . x . x 
. x x x x 

    . 
   . x 
  x x x 
 . . . x 
. . x x x 

    . 
   . . 
  x . x 
 . x . x 
. . x x x 

    . 
   . x 
  x . . 
 . x . . 
. . x x x 

    . 
   . x 
  x . . 
 . x . . 
. x . . x 

    . 
   . x 
  x x . 
 . . . . 
. . . . x 

    . 
   . x 
  . . x 
 . . . . 
. . . . x 

    . 
   . . 
  . . . 
 . . . x 
. . . . x 

    . 
   . . 
  . . x 
 . . . . 
. . . . . 



...
*/
