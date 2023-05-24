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
 * AdjListOutputFile name is passed through the command line as args[1]
 * Output to AdjListOutputFile with the format:
 * 1. c lines, each starting with a different course ID, then 
 *    listing all of that course's prerequisites (space separated)
 */
public class AdjList {
    private ArrayList<String>[] c;

    public AdjList(int length) {
       c = (ArrayList<String>[]) new ArrayList[length];
    }
    public void addCourse(int id, String a) {
        c[id] = new ArrayList<String>();
        c[id].add(a); 
    }
    public void setPrereqs(String a, String pr) {
        for(int i = 0; i < c.length; i++) {
            if(c[i].get(0).equals(a))
                c[i].add(pr);
        }
    }
    public void printer(String tmp) {
        StdOut.setFile(tmp);
        for(int i = 0; i < c.length; i++) {
            for(String string : c[i]) {
                StdOut.print(string + " ");
            }
            StdOut.println();
        }
    }
    public ArrayList<String>[] returnCourses() {
        return c;
    }
    public static void main(String[] args) {

        if ( args.length < 2 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.AdjList <adjacency list INput file> <adjacency list OUTput file>");
            return;
        }
        StdIn.setFile(args[0]);
        int cnum = StdIn.readInt();
        StdIn.readLine();
        AdjList courseList = new AdjList(cnum);
        
        for(int i = 0; i < cnum; i++) {
            courseList.addCourse(i, StdIn.readLine());
        }
        String[] stringEntry;
        StdIn.readLine();
        while(StdIn.hasNextLine()) {
            stringEntry = StdIn.readLine().split(" ");
            courseList.setPrereqs(stringEntry[0], stringEntry[1]);
        }
        courseList.printer(args[1]);
    }
}
