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
 * ValidPreReqInputFile name is passed through the command line as args[1]
 * Read from ValidPreReqInputFile with the format:
 * 1. 1 line containing the proposed advanced course
 * 2. 1 line containing the proposed prereq to the advanced course
 * 
 * Step 3:
 * ValidPreReqOutputFile name is passed through the command line as args[2]
 * Output to ValidPreReqOutputFile with the format:
 * 1. 1 line, containing either the word "YES" or "NO"
 */
public class ValidPrereq { 
    public String stack(ArrayList<String>[] c, String[] swap) {
        String string = "";
        Stack<String> done = new Stack<String>();
        Stack<String> goTo = new Stack<String>();
        goTo.push(swap[1]);
        while(!goTo.empty()) {
            string = goTo.pop();
        done.push(string);
        for(int i = 0; i < c.length; i++) {
            if(c[i].get(0).equals(string)) {
                for(String tmp : c[i]) {
                    if(tmp.equals(swap[0]))
                      return "NO";
                    if(!done.contains(tmp))
                    goTo.push(tmp);
            }
        }
    }
}
return "YES";
}
public static void main(String[] args) {

    if ( args.length < 3 ) {
        StdOut.println("Execute: java -cp bin prereqchecker.ValidPrereq <adjacency list INput file> <valid prereq INput file> <valid prereq OUTput file>");
        return;
    }
    StdIn.setFile(args[1]);
    String[] c = new String[2];
    c[0] = StdIn.readLine();
    c[1] = StdIn.readLine();

    StdIn.setFile(args[0]);
    int cnum = StdIn.readInt();
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
    ValidPrereq pr = new ValidPrereq();
    StdOut.setFile(args[2]);
    StdOut.println(pr.stack(adjl.returnCourses(), c));
    }
}
