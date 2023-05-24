package prereqchecker;

import java.util.*;

/**
 * 
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
 * EligibleInputFile name is passed through the command line as args[1]
 * Read from EligibleInputFile with the format:
 * 1. c (int): Number of courses
 * 2. c lines, each with 1 course ID
 * 
 * Step 3:
 * EligibleOutputFile name is passed through the command line as args[2]
 * Output to EligibleOutputFile with the format:
 * 1. Some number of lines, each with one course ID
 */
public class Eligible {
    
    public ArrayList<String> finishedC(ArrayList<String>[] c, Stack<String> tmp) {
        ArrayList<String> classes = new ArrayList<String>();
        String string = "";
        Stack<String> done = new Stack<String>();
        Stack<String> goTo = new Stack<String>();
        while(!tmp.empty()){
            goTo.push(tmp.pop());
        while(!goTo.empty()) {
            string = goTo.pop();
            done.push(string);
        for(int i = 0; i < c.length; i++) {
            if(c[i].get(0).equals(string)) {
                for(String tmp1 : c[i]) {
                    if(!done.contains(tmp1))
                    goTo.push(tmp1);
                }
             break;
            }
        }
    }
}
    while(!done.empty()) {
        classes.add(done.pop());
    }
    return classes;
}


public void torf(ArrayList<String>[] c, Stack<String> tmp, String string) {
    StdOut.setFile(string);
    boolean tf = true;
    ArrayList<String> list = finishedC(c, tmp);
    for(int i = 0; i < c.length; i++) {
        if(!list.contains(c[i].get(0))){
        for(String boo : c[i]) {
            if(!boo.equals(c[i].get(0))) {
                if(!list.contains(boo))
                tf = false;
            }
        }
        if(tf)
            StdOut.println(c[i].get(0));
            tf = true;
        }
    }
}
public static void main(String[] args) {

    if ( args.length < 3 ) {
        StdOut.println("Execute: java -cp bin prereqchecker.Eligible <adjacency list INput file> <eligible INput file> <eligible OUTput file>");
        return;
    }

    StdIn.setFile(args[1]);
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
    String[] pr;
    StdIn.readLine();
    while(StdIn.hasNextLine()) {
        pr = StdIn.readLine().split(" ");
        adjl.setPrereqs(pr[0], pr[1]);
    }
        Eligible eligible = new Eligible();
        eligible.torf(adjl.returnCourses(), c, args[2]);
    }
}
