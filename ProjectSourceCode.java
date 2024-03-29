package project.sourcecode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Ivan Brenes
 * in collaboration with...
 * Guirdelle Blaise
 * Carolina Uribe-Gosselin
 * Juan Maquilon
 * Date: 6/11/19
 * Section: RVCC
 */

public class ProjectSourceCode {

    public static void main (String[] args) throws IOException{
        //Grab the local tuple file
        File file = new File("src/project/sourcecode/tuples.txt");
        
        //Scan the local tuple file so that the data can be used later.
        Scanner scan = new Scanner(file).useDelimiter("[\r\n]+");
        
        //Make a list of tuples from the data.
        ArrayList<Tuple> tupleList = new ArrayList<>();
        
        //Make a list of available attributes.
        ArrayList<String> attributeList = new ArrayList<>();
        
        //Temp variable to help store the data.
        String data = "";
        
        //This while loop scans through the inputted document.
        //First, it get makes an attribute list
        //Then it takes the rest of the data and stores it into their own tuples.
        while(scan.hasNext()){
            data = scan.next();
            String[] tokens = data.split("\\s+");
            if(data.contains("attributes:")){
                attributeList = addAttributesToList(tokens);
            }
            else{
                Tuple tuple = new Tuple(tokens[0], tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                tupleList.add(tuple); 
            }
        }
        
        //The decision tree is made here.
        Node root = decisionTree(attributeList, tupleList);
        
        //for(int i = 0;  i < root.getAttributeList().size(); i++){
        //    System.out.println("Attribute #" + i + ": " + root.getAttributeList().get(i));
        //} 
        
        //for(int i = 0;  i < root.getDataSet().size(); i++){
        //    System.out.println("Data Object #" + i + ": " + root.getDataSet().get(i).getAll());
        //}
        
        //FIXME
        //for(int i = 0;  i < root.nodePointers().size(); i++){
        //    System.out.println("Pointer #" + i + ": " + root.nodePointers().get(i));
        //}
    }
    
    public static String a(String x){
        x = "def";
        return x;
    }
    
    //This method goes through the tokenized attributes and stores them in a list.
    public static ArrayList<String> addAttributesToList(String[] tokens){
        ArrayList<String> attributes = new ArrayList<>();
        for(int i = 1; i < tokens.length; i++){
            attributes.add(tokens[i]);
        }
        return attributes;
    }
    
    //This method makes the root of the decision tree, following the algorithm
    //as explained on page 333 of the textbook.
    public static Node decisionTree(ArrayList<String> attribute, ArrayList<Tuple> set){
        Node root = new Node(attribute, set);
        if(root.checkTupleClass() == true){
            setLabel(root);
            return root;
        }
        if(root.getAttributeListCount() == 0){
            setLabel(root);
            return root;
        }
        
        root.attributeSelectionMethod();
        root.removeSplittingAttribute();
        //remove splitting_attribute from list
        //For each outcome j of splitting, then make sublevels.
        //AKA...
        //10 - for each outcome j of splitting criterion
        //11 - let Dj be the set of data tuples in D satifying outcome j
        //12 - if Dj is empty then
        //13 -  attach a leaf labeled with the majority class in D to node N;
        //14 - else
        //15 -  attach the node returned by the Generate_Decision_Tree(Dj, attribute_list) to node N
        //16 return N
        return root;
    }
    
    //Helps to set the label of the node based on majority.
    //If they are all the same class, then that class is still the majority.
    //This method also helps to cut down code reusage a small bit.
    public static void setLabel(Node node){
        String label = node.classMajority();
        node.setClass(label);
    }
    
}
