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
 * SchedulePlanInputFile name is passed through the command line as args[1]
 * Read from SchedulePlanInputFile with the format:
 * 1. One line containing a course ID
 * 2. c (int): number of courses
 * 3. c lines, each with one course ID
 * 
 * Step 3:
 * SchedulePlanOutputFile name is passed through the command line as args[2]
 * Output to SchedulePlanOutputFile with the format:
 * 1. One line containing an int c, the number of semesters required to take the course
 * 2. c lines, each with space separated course ID's
 */
public class SchedulePlan {
    public static void main(String[] args) {

        if ( args.length < 3 ) {
            StdOut.println("Execute: java -cp bin prereqchecker.SchedulePlan <adjacency list INput file> <schedule plan INput file> <schedule plan OUTput file>");
            return;
        }

        //stdin here
        StdIn.setFile(args[1]);
        Stack<String> y = new Stack<String>();
        String string = StdIn.readLine();
        y.push(string);
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

        String[] list;
        StdIn.readLine();

        while(StdIn.hasNextLine()) {
            list = StdIn.readLine().split(" ");
            adjl.setPrereqs(list[0], list[1]);
        }

        Eligible eligible = new Eligible();
        ArrayList<String> a = eligible.finishedC(adjl.returnCourses(), c);
        ArrayList<String> t = eligible.finishedC(adjl.returnCourses(), y);
        ArrayList<String> v = new ArrayList<String>();
        boolean boo = true;
        for(String z : t) {
            if(!z.equals(string)){
            for(String x : a) {
                if(z.equals(x)) {
                    boo = false;
                }
            }
            if(boo) {
                v.add(z);
            }
            boo = true;
            }
        }
        int count = 0;
        boolean boo1 = true;
        ArrayList<String> al = new ArrayList<String>();
        ArrayList<String> delete = new ArrayList<String>();
        ArrayList<ArrayList<String>> s = new ArrayList<ArrayList<String>>();
        ArrayList<String>[] courseList = adjl.returnCourses();

        while(!v.isEmpty()) {
        for(int i = 0; i < courseList.length; i++) {
            for(String e : v) {
            if(courseList[i].get(0).equals(e)) {
                for(int j = 1; j < courseList[i].size(); j++) {
                    if(!a.contains(courseList[i].get(j))) {
                        boo1 = false;
                    }
                }
                if(boo1) {
                    System.out.println();
                    if(s.size() == count)
                    s.add(new ArrayList<>());
                    s.get(count).add(e);
                    al.add(e);
                    delete.add(e);
                }

                    boo1 = true;
                }
            }
        }
        if(delete.size() == 0) {
            if(s.size() == count)
            s.add(new ArrayList<>());
            s.get(count).addAll(v);
            al.addAll(v);
            delete.addAll(v);
        }
        v.removeAll(delete);
        delete.clear();
        a.addAll(al);
        al.clear();
        count++;
        }
        StdOut.setFile(args[2]);
        StdOut.println(s.size());
        for(ArrayList<String> f : s) {
            for(String n : f) {
                StdOut.print(n + " ");
            }
            StdOut.println();
        }  

    }
}
