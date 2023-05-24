package transit;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered linked
 * list to simulate transit
 * 
 * @author Aakash M
 */
public class Transit {
	private TNode trainZero; // a reference to the zero node in the train layer

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */ 
	public Transit() { trainZero = null; }

	/* 
	 * Default constructor used by the driver and Autolab. 
	 * DO NOT use in your code.
	 * DO NOT remove from this file
	 */
	public Transit(TNode tz) { trainZero = tz; }
	
	/*
	 * Getter method for trainZero
	 *
	 * DO NOT remove from this file.
	 */
	public TNode getTrainZero () {
		return trainZero;
	}

	/**
	 * Makes a layered linked list representing the given arrays of train stations, bus
	 * stops, and walking locations. Each layer begins with a location of 0, even though
	 * the arrays don't contain the value 0. Store the zero node in the train layer in
	 * the instance variable trainZero.
	 * 
	 * @param trainStations Int array listing all the train stations
	 * @param busStops Int array listing all the bus stops
	 * @param locations Int array listing all the walking locations (always increments by 1)
	 */
	public void makeList(int[] trainStations, int[] busStops, int[] locations) {
		trainZero = new TNode(0);
		TNode busHead = new TNode(0);
		TNode walkHead = new TNode(0);
		trainZero.setDown(busHead);
		busHead.setDown(walkHead);
		TNode tmp = null; 
		tmp = trainZero; 
		for(int i =0; i<trainStations.length; i++){
			TNode train = new TNode(trainStations[i]);
			tmp.setNext(train);
			tmp = train;
		}
		tmp = null; 
		tmp = busHead; 
		for(int i =0; i<busStops.length; i++){
			TNode bus = new TNode(busStops[i]);
			tmp.setNext(bus);
			tmp = bus;
		}
		tmp = null; 
		tmp = walkHead; 
		for(int i =0; i<locations.length; i++){
			TNode walk= new TNode(locations[i]);
			tmp.setNext(walk);
			tmp = walk;
		}
		TNode ttmp = trainZero; 
		TNode btmp = busHead; 
		for(int i=0; i<trainStations.length; i++){
			for(int j=0; j<busStops.length; j++){
				if(trainStations[i]==busStops[j]){
					for(int t=0; t<trainStations.length; t++){
						if(ttmp.getLocation() != busStops[j]){
							ttmp = ttmp.getNext(); 
						}
					}
					for(int b =0; b<busStops.length; b++){
						if(btmp.getLocation() != busStops[j]){
							btmp = btmp.getNext(); 
						}
					}
					ttmp.setDown(btmp);
				}
			}
		}
		btmp = busHead;
		TNode wtmp = walkHead;  
		for(int i=0; i<busStops.length; i++){
			for(int j=0; j<locations.length; j++){
				if(busStops[i]==locations[j]){
					for(int b=0; b<busStops.length; b++){
						if(btmp.getLocation() != locations[j]){
							btmp = btmp.getNext(); 
						}
					}
					for(int w =0; w<locations.length; w++){
						if(wtmp.getLocation() != locations[j]){
							wtmp = wtmp.getNext(); 
						}
					}
					btmp.setDown(wtmp);
				}
			}
		}

	}
	
	/**
	 * Modifies the layered list to remove the given train station but NOT its associated
	 * bus stop or walking location. Do nothing if the train station doesn't exist
	 * 
	 * @param station The location of the train station to remove
	 */
	public void removeTrainStation(int station) {
		TNode tmp = trainZero; 

		while(tmp != null){
			if(tmp.getLocation() == station){
				tmp = trainZero; 
				while(tmp.getNext().getLocation() != station){
					tmp = tmp.getNext(); 
				}
				tmp.setNext(tmp.getNext().getNext());
			}
			tmp = tmp.getNext(); 
		}
	}

	/**
	 * Modifies the layered list to add a new bus stop at the specified location. Do nothing
	 * if there is no corresponding walking location.
	 * 
	 * @param busStop The location of the bus stop to add
	 */
	public void addBusStop(int busStop) {
	    TNode tmp = trainZero.getDown().getDown(); 
		TNode next = trainZero.getDown(); 
		TNode prev = trainZero.getDown();
		while(tmp != null){
			if(tmp.getLocation() == busStop){
				while(next.getLocation() < tmp.getLocation()){
					next = next.getNext();
				}
				while(prev.getNext() != next){
					prev = prev.getNext();
				}
				TNode abus = new TNode(busStop);
				abus.setNext(next);
				prev.setNext(abus);
			}
			tmp = tmp.getNext(); 
		}
		TNode wtmp = trainZero.getDown().getDown(); 
		while(prev.getNext().getLocation() != wtmp.getLocation()){
			wtmp = wtmp.getNext();
		}
		prev.getNext().setDown(wtmp);
	}
	
	/**
	 * Determines the optimal path to get to a given destination in the walking layer, and 
	 * collects all the nodes which are visited in this path into an arraylist. 
	 * 
	 * @param destination An int representing the destination
	 * @return
	 */
	public ArrayList<TNode> bestPath(int destination) {
		TNode ttmp = trainZero; 
		ArrayList<TNode> bestPath = new ArrayList<>(); 
		while(ttmp.getNext().getLocation() <= destination ){
			bestPath.add(ttmp);
			ttmp = ttmp.getNext();
		}
		bestPath.add(ttmp.getDown());
		TNode btmp = ttmp.getDown(); 
		while(btmp.getNext().getLocation() <= destination ){
			bestPath.add(btmp);
			btmp = btmp.getNext();
		}
		bestPath.add(btmp.getDown());
		TNode wtmp = btmp.getDown(); 
		while(wtmp.getLocation() != destination){
			bestPath.add(wtmp);
			wtmp = wtmp.getNext();
		}
		bestPath.add(wtmp);

	    return bestPath;
	}

	/**
	 * Returns a deep copy of the given layered list, which contains exactly the same
	 * locations and connections, but every node is a NEW node.
	 * 
	 * @return A reference to the train zero node of a deep copy
	 */
	public TNode duplicate() {
		TNode dupTrain = new TNode(0);
		TNode dupBus = new TNode(0);
		TNode dupWalk = new TNode(0);
		dupTrain.setDown(dupBus);
		dupBus.setDown(dupWalk);
		int tlen =0;
		int blen=0;
		int wlen=0; 
		TNode tmp = trainZero;
		while(tmp!=null){
			tmp = tmp.getNext(); 
			tlen++;
		}
		tmp = trainZero.getDown();
		while(tmp != null){
			tmp = tmp.getNext(); 
			blen++;
		}
		tmp = trainZero.getDown().getDown();
		while(tmp != null){
			tmp = tmp.getNext(); 
			wlen++;
		}
		tmp = dupTrain; 
		TNode ptr = trainZero.getNext(); 
		for(int i=1; i<tlen; i++){
			TNode temp = new TNode(ptr.getLocation());
			tmp.setNext(temp);
			tmp = temp;
			ptr = ptr.getNext();
		}
		tmp = dupBus; 
		ptr = trainZero.getDown().getNext(); 
		for(int i=1; i<blen; i++){
			TNode temp = new TNode(ptr.getLocation());
			tmp.setNext(temp);
			tmp = temp;
			ptr = ptr.getNext();
		}
		tmp = dupWalk; 
		ptr = trainZero.getDown().getDown().getNext(); 
		for(int i=1; i<wlen; i++){
			TNode temp = new TNode(ptr.getLocation());
			tmp.setNext(temp);
			tmp = temp;
			ptr = ptr.getNext();
		}
		Integer[] a = new Integer[tlen];
		tmp = trainZero; 
		for(int i =0; i<tlen; i++){
			a[i] = tmp.getLocation();
			tmp = tmp.getNext();
		}
		Integer[] c = new Integer[blen];
		tmp = trainZero.getDown(); 
		for(int i =0; i<blen; i++){
			c[i] = tmp.getLocation();
			tmp = tmp.getNext();
		}
		Integer[] d = new Integer[wlen];
		tmp = trainZero.getDown().getDown(); 
		for(int i =0; i<wlen; i++){
			d[i] = tmp.getLocation();
			tmp = tmp.getNext();
		}
		TNode ttmp = dupTrain; 
		TNode btmp = dupBus; 
		for(int i=0; i<tlen; i++){
			for(int j=0; j<blen; j++){
				if(a[i]==c[j]){
					for(int t=0; t<tlen; t++){
						if(ttmp.getLocation() != c[j]){
							ttmp = ttmp.getNext(); 
						}
					}
					for(int b =0; b<blen; b++){
						if(btmp.getLocation() != c[j]){
							btmp = btmp.getNext(); 
						}
					}
					ttmp.setDown(btmp);
				}
			}
		}
		btmp = dupBus;
		TNode wtmp = dupWalk;  
		for(int i=0; i<blen; i++){
			for(int j=0; j<wlen; j++){
				if(c[i]==d[j]){
					for(int b=0; b<blen; b++){
						if(btmp.getLocation() != d[j]){
							btmp = btmp.getNext(); 
						}
					}
					for(int w =0; w<wlen; w++){
						if(wtmp.getLocation() != d[j]){
							wtmp = wtmp.getNext(); 
						}
					}
					btmp.setDown(wtmp);
				}
			}
		}
	    return dupTrain;
	}

	/**
	 * Modifies the given layered list to add a scooter layer in between the bus and
	 * walking layer.
	 * 
	 * @param scooterStops An int array representing where the scooter stops are located
	 */
	public void addScooter(int[] scooterStops) {
		TNode scooterHead = new TNode(0);
		scooterHead.setDown(trainZero.getDown().getDown());
		trainZero.getDown().setDown(scooterHead);
		TNode tmp = scooterHead; 
		for(int i =0; i < scooterStops.length; i++){
			TNode scoot = new TNode(scooterStops[i]);
			tmp.setNext(scoot);
			tmp = scoot;
		}
		int blen =0; 
		tmp = trainZero.getDown();
		while(tmp!=null){
			tmp = tmp.getNext(); 
			blen++;
		}
		Integer[] c = new Integer[blen];
		tmp = trainZero.getDown(); 
		for(int i =0; i<blen; i++){
			c[i] = tmp.getLocation();
			tmp = tmp.getNext();
		}
		TNode btmp = trainZero.getDown();
		TNode stmp = scooterHead;  
		for(int i=0; i<blen; i++){
			for(int j=0; j<scooterStops.length; j++){
				if(c[i]==scooterStops[j]){
					for(int k=0; k < blen; k++){
						if(btmp.getLocation() != scooterStops[j]){
							btmp = btmp.getNext(); 
						}
					}
					for(int l = 0; l < scooterStops.length; l++){
						if(stmp.getLocation() != scooterStops[j]){
							stmp = stmp.getNext(); 
						}
					}
					btmp.setDown(stmp);
				}
			}
		}
			int wlen=0; 
			tmp = trainZero.getDown().getDown().getDown();
		while(tmp!=null){
			tmp = tmp.getNext(); 
			wlen++;
		}
		Integer[] d = new Integer[wlen];
		tmp = trainZero.getDown().getDown().getDown(); 
		for(int i =0; i<wlen; i++){
			d[i] = tmp.getLocation();
			tmp = tmp.getNext();
		}
		TNode sptr = scooterHead;
		TNode wptr = trainZero.getDown().getDown().getDown();  
		for(int i=0; i<scooterStops.length; i++){
			for(int j=0; j < wlen; j++){
				if(scooterStops[i]==d[j]){
					for(int k = 0; k < scooterStops.length; k++){
						if(sptr.getLocation() != d[j]){
							sptr = sptr.getNext(); 
						}
					}
					for(int l =0; l < wlen; l++){
						if(wptr.getLocation() != d[j]){
							wptr = wptr.getNext(); 
						}
					}
					sptr.setDown(wptr);
				}
			}
		}
	    
	}

	/**
	 * Used by the driver to display the layered linked list. 
	 * DO NOT edit.
	 */
	public void printList() {
		// Traverse the starts of the layers, then the layers within
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// Output the location, then prepare for the arrow to the next
				StdOut.print(horizPtr.getLocation());
				if (horizPtr.getNext() == null) break;
				
				// Spacing is determined by the numbers in the walking layer
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print("--");
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print("-");
				}
				StdOut.print("->");
			}

			// Prepare for vertical lines
			if (vertPtr.getDown() == null) break;
			StdOut.println();
			
			TNode downPtr = vertPtr.getDown();
			// Reset horizPtr, and output a | under each number
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				while (downPtr.getLocation() < horizPtr.getLocation()) downPtr = downPtr.getNext();
				if (downPtr.getLocation() == horizPtr.getLocation() && horizPtr.getDown() == downPtr) StdOut.print("|");
				else StdOut.print(" ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
	
	/**
	 * Used by the driver to display best path. 
	 * DO NOT edit.
	 */
	public void printBestPath(int destination) {
		ArrayList<TNode> path = bestPath(destination);
		for (TNode vertPtr = trainZero; vertPtr != null; vertPtr = vertPtr.getDown()) {
			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the number if this node is in the path, otherwise spaces
				if (path.contains(horizPtr)) StdOut.print(horizPtr.getLocation());
				else {
					int numLen = String.valueOf(horizPtr.getLocation()).length();
					for (int i = 0; i < numLen; i++) StdOut.print(" ");
				}
				if (horizPtr.getNext() == null) break;
				
				// ONLY print the edge if both ends are in the path, otherwise spaces
				String separator = (path.contains(horizPtr) && path.contains(horizPtr.getNext())) ? ">" : " ";
				for (int i = horizPtr.getLocation()+1; i < horizPtr.getNext().getLocation(); i++) {
					StdOut.print(separator + separator);
					
					int numLen = String.valueOf(i).length();
					for (int j = 0; j < numLen; j++) StdOut.print(separator);
				}

				StdOut.print(separator + separator);
			}
			
			if (vertPtr.getDown() == null) break;
			StdOut.println();

			for (TNode horizPtr = vertPtr; horizPtr != null; horizPtr = horizPtr.getNext()) {
				// ONLY print the vertical edge if both ends are in the path, otherwise space
				StdOut.print((path.contains(horizPtr) && path.contains(horizPtr.getDown())) ? "V" : " ");
				int numLen = String.valueOf(horizPtr.getLocation()).length();
				for (int j = 0; j < numLen-1; j++) StdOut.print(" ");
				
				if (horizPtr.getNext() == null) break;
				
				for (int i = horizPtr.getLocation()+1; i <= horizPtr.getNext().getLocation(); i++) {
					StdOut.print("  ");

					if (i != horizPtr.getNext().getLocation()) {
						numLen = String.valueOf(i).length();
						for (int j = 0; j < numLen; j++) StdOut.print(" ");
					}
				}
			}
			StdOut.println();
		}
		StdOut.println();
	}
}
