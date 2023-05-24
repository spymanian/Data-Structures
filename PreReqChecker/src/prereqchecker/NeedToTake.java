package prereqchecker;

import java.util.*;

/**
 * Steps to implement this class main method:
 * 
 * Step 1:
 * AdjListInputFile name is passed through the command line as args[0]
 * Read from AdjListInputFile with the format:
 * 1. a (int): number of courses in the graph
 * 2. a lines, each with 1 course ID
 * 3. b (int): number of edges in the graph
 * 4. b lines, each with a source ID
 * 
 * Step 2:
 * NeedToTakeInputFile name is passed through the command line as args[1]
 * Read from NeedToTakeInputFile with the format:
 * 1. One line, containing a course ID
 * 2. c (int): Number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * NeedToTakeOutputFile name is passed through the command line as args[2]
 * Output to NeedToTakeOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class NeedToTake {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java NeedToTake <adjacency list INput file> <need to take INput file> <need to take OUTput file>");
            return;
        }

        StdIn.setFile(args[1]);
        Stack<String> y = new Stack<String>();
        String val = StdIn.readLine();
        y.push(val);
        int cnum = StdIn.readInt();
        StdIn.readLine();
        Stack<String> c = new Stack<String>();
        for(int i = 0; i < cnum; i++) {
            c.push(StdIn.readLine());
        }
        StdIn.setFile(args[0]);
        cnum = StdIn.readInt();
        StdIn.readLine();
        AdjList adjl = new AdjList(cnum);
        
        for(int i = 0; i < cnum; i++) {
            adjl.addCourse(i, StdIn.readLine());
        }
        String[] string;
        StdIn.readLine();
        while(StdIn.hasNextLine()) {
            string = StdIn.readLine().split(" ");
            adjl.setPrereqs(string[0], string[1]);
        }
        Eligible eligible = new Eligible();
        ArrayList<String> x = eligible.finishedC(adjl.returnCourses(), c);
        ArrayList<String> z = eligible.finishedC(adjl.returnCourses(), y);
        StdOut.setFile(args[2]);
        boolean boo = true;
        for(String tmp : z) {
            if(!tmp.equals(val)){
            for(String t : x) {
                if(tmp.equals(t)) {
                    boo = false;
                }
            }
            if(boo) {
                StdOut.println(tmp);
            }
            boo = true;
            }
        }
    }   
}
