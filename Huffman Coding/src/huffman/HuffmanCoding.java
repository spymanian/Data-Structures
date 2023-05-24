package huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class contains methods which, when used together, perform the
 * entire Huffman Coding encoding and decoding process
 * 
 * @author Aakash M
 */
public class HuffmanCoding {
    private String fileName;
    private ArrayList<CharFreq> sortedCharFreqList;
    private TreeNode huffmanRoot;
    private String[] encodings;

    /**
     * Constructor used by the driver, sets filename
     * DO NOT EDIT
     * @param f The file we want to encode
     */
    public HuffmanCoding(String f) { 
        fileName = f; 
    }

    /**
     * Reads from filename character by character, and sets sortedCharFreqList
     * to a new ArrayList of CharFreq objects with frequency > 0, sorted by frequency
     * @return 
     */
    public void makeSortedList() {
        StdIn.setFile(fileName);
        double c = 0;
        boolean a = false;
        StdIn.setFile(fileName);
        sortedCharFreqList = new ArrayList<CharFreq>();
        while(StdIn.hasNextChar()) {
            char character = StdIn.readChar();
            c++;
            for(int i = 0; i < sortedCharFreqList.size(); i++) {
                if(sortedCharFreqList.get(i).getCharacter() == character){
                    sortedCharFreqList.get(i).setProbOcc(sortedCharFreqList.get(i).getProbOcc()+1);
                    a = true;
                }
            }
            if(a ==false) {
                sortedCharFreqList.add(new CharFreq(character, 1));
            }
            a =false;
        }
        for(int i = 0; i < sortedCharFreqList.size(); i++) {
            sortedCharFreqList.get(i).setProbOcc(sortedCharFreqList.get(i).getProbOcc()/c);
        }
        if(sortedCharFreqList.size()==1) {
            CharFreq o = new CharFreq();
            int d = ((int)sortedCharFreqList.get(0).getCharacter());
            if(d == 127) {
                o.setCharacter((char)0);
            }
            o.setCharacter((char)(d+1));
            sortedCharFreqList.add(o);
        }
        Collections.sort(sortedCharFreqList);
    }

    /**
     * Uses sortedCharFreqList to build a huffman coding tree, and stores its root
     * in huffmanRoot
     * @return 
     */
    public void makeTree() {
        double tmp = 0;
        Queue<TreeNode> a  = new Queue<TreeNode>();
        Queue<TreeNode> b = new Queue<TreeNode>();
        huffmanRoot = new TreeNode();
        
        for(int i =0; i<sortedCharFreqList.size(); i++){
            TreeNode x = new TreeNode(sortedCharFreqList.get(i),null,null);
            a.enqueue(x);   
        }
        TreeNode node1 = null;
        TreeNode node2 = null;

        while(!(a.isEmpty()==true && b.size() ==1)){
            if(!a.isEmpty() && (b.isEmpty() || (a.peek().getData().getProbOcc() <= b.peek().getData().getProbOcc()))){
                node1=a.dequeue();
            }else{
                node1 = b.dequeue();
            }if( !b.isEmpty() && !a.isEmpty() && (a.peek().getData().getProbOcc() <= b.peek().getData().getProbOcc())){
                node2=a.dequeue();
            }else if(!b.isEmpty() && !a.isEmpty() && a.peek().getData().getProbOcc() > b.peek().getData().getProbOcc()){
                node2 = b.dequeue();
            }else if(a.isEmpty()){
                node2 = b.dequeue();
            }else if(b.isEmpty()){
                node2 = a.dequeue();
            }
                tmp = node1.getData().getProbOcc() + node2.getData().getProbOcc();
                CharFreq prb = new CharFreq(null,tmp);
                TreeNode  occ = new TreeNode(prb, node1, node2);
                b.enqueue(occ);
                if(a.isEmpty()==true && b.size() == 1){
                    huffmanRoot = occ;
                    break;
                }   
        }

    }

    /**
     * Uses huffmanRoot to create a string array of size 128, where each
     * index in the array contains that ASCII character's bitstring encoding. Characters not
     * present in the huffman coding tree should have their spots in the array left null.
     * Set encodings to this array.
     */
    public void makeEncodings() {
        encodings = new String[128];

        encodes(huffmanRoot, "", encodings);
    }

    private static void encodes(TreeNode R, String tmp, String[] e) {
        if(R.getLeft() == null && R.getRight() == null) {
            e[(int)R.getData().getCharacter()] = tmp;
            return;
        }
        encodes(R.getLeft(), tmp+"0", e);
        encodes(R.getRight(), tmp+"1", e);    
    }


    /**
     * Using encodings and filename, this method makes use of the writeBitString method
     * to write the final encoding of 1's and 0's to the encoded file.
     * 
     * @param encodedFile The file name into which the text file is to be encoded
     */
    public void encode(String encodedFile) {
        StdIn.setFile(fileName);
        char y;
        String x = "";
        while(StdIn.hasNextChar()) {
            y = StdIn.readChar();
            for(int i = 0; i < encodings.length; i++) {
                if(i==(int)y) {
                    x+=encodings[i];
                }
            }
        }
        writeBitString(encodedFile, x);

    }
    
    /**
     * Writes a given string of 1's and 0's to the given file byte by byte
     * and NOT as characters of 1 and 0 which take up 8 bits each
     * DO NOT EDIT
     * 
     * @param filename The file to write to (doesn't need to exist yet)
     * @param bitString The string of 1's and 0's to write to the file in bits
     */
    public static void writeBitString(String filename, String bitString) {
        byte[] bytes = new byte[bitString.length() / 8 + 1];
        int bytesIndex = 0, byteIndex = 0, currentByte = 0;

        // Pad the string with initial zeroes and then a one in order to bring
        // its length to a multiple of 8. When reading, the 1 signifies the
        // end of padding.
        int padding = 8 - (bitString.length() % 8);
        String pad = "";
        for (int i = 0; i < padding-1; i++) pad = pad + "0";
        pad = pad + "1";
        bitString = pad + bitString;

        // For every bit, add it to the right spot in the corresponding byte,
        // and store bytes in the array when finished
        for (char c : bitString.toCharArray()) {
            if (c != '1' && c != '0') {
                System.out.println("Invalid characters in bitstring");
                return;
            }

            if (c == '1') currentByte += 1 << (7-byteIndex);
            byteIndex++;
            
            if (byteIndex == 8) {
                bytes[bytesIndex] = (byte) currentByte;
                bytesIndex++;
                currentByte = 0;
                byteIndex = 0;
            }
        }
        
        // Write the array of bytes to the provided file
        try {
            FileOutputStream out = new FileOutputStream(filename);
            out.write(bytes);
            out.close();
        }
        catch(Exception e) {
            System.err.println("Error when writing to file!");
        }
    }

    /**
     * Using a given encoded file name, this method makes use of the readBitString method 
     * to convert the file into a bit string, then decodes the bit string using the 
     * tree, and writes it to a decoded file. 
     * 
     * @param encodedFile The file which has already been encoded by encode()
     * @param decodedFile The name of the new file we want to decode into
     */
    public void decode(String encodedFile, String decodedFile) {
        StdOut.setFile(decodedFile);
        TreeNode temp = huffmanRoot;
        String b = readBitString(encodedFile);
        String a = "";
        while(b.length()!=0){
        while(temp.getLeft() != null && temp.getRight() != null) {
            if(b.substring(0, 1).equals("0")){
                b = b.substring(1);
                temp = temp.getLeft();
            }
            if(temp.getLeft() == null && temp.getRight() == null) {
                break;
            }
            if(b.substring(0, 1).equals("1")){
                b = b.substring(1);
                temp = temp.getRight();
            }
        }
            a+=Character.toString(temp.getData().getCharacter());
            temp = huffmanRoot;
        }
            StdOut.print(a);
    }

    /**
     * Reads a given file byte by byte, and returns a string of 1's and 0's
     * representing the bits in the file
     * DO NOT EDIT
     * 
     * @param filename The encoded file to read from
     * @return String of 1's and 0's representing the bits in the file
     */
    public static String readBitString(String filename) {
        String bitString = "";
        
        try {
            FileInputStream in = new FileInputStream(filename);
            File file = new File(filename);

            byte bytes[] = new byte[(int) file.length()];
            in.read(bytes);
            in.close();
            
            // For each byte read, convert it to a binary string of length 8 and add it
            // to the bit string
            for (byte b : bytes) {
                bitString = bitString + 
                String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            }

            // Detect the first 1 signifying the end of padding, then remove the first few
            // characters, including the 1
            for (int i = 0; i < 8; i++) {
                if (bitString.charAt(i) == '1') return bitString.substring(i+1);
            }
            
            return bitString.substring(8);
        }
        catch(Exception e) {
            System.out.println("Error while reading file!");
            return "";
        }
    }

    /*
     * Getters used by the driver. 
     * DO NOT EDIT or REMOVE
     */

    public String getFileName() { 
        return fileName; 
    }

    public ArrayList<CharFreq> getSortedCharFreqList() { 
        return sortedCharFreqList; 
    }

    public TreeNode getHuffmanRoot() { 
        return huffmanRoot; 
    }

    public String[] getEncodings() { 
        return encodings; 
    }
}
