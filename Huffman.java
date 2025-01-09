import tester.*;
import java.util.ArrayList;

// This represents a comparator object, used to compare two generic types;
// compare the second one with respect to the first one.  
interface IComparator<T> {
  // This method compares the two given generic types, comparing the second one relative to the 
  // first one. 
  int compare(T one, T two);
}

// This comparator object is used to compare two ATree objects. 
class TreeCompare implements IComparator<ATree> {
  
  /* TEMPLATE
   * METHODS:
   * ... compare(ATree one, ATree two) ...   -- int
   */
  
  // This method compares the two provided ATrees in terms of their values of frequency. 
  public int compare(ATree one, ATree two) {
    /* TEMPLATE
     * PARAMETERS:
     * ... one ...        -- ATree
     * ... two ...        -- ATree
     * FIELDS ON PARAMETERS:
     * ... one.freq ...   -- int
     * ... two.freq ...   -- int
     */
    return one.freq - two.freq;
  }
}

// This represents a Huffman Tree, constructed by an ArrayList of letters and their respective
// frequencies that are subsequently encoded into a structure Forest, that represents the entire
// Huffman Tree. 
class Huffman {
  ArrayList<String> letters;
  ArrayList<Integer> numbers;
  Forest f;
  
  // Constructor takes in an ArrayList of symbols to encode and their respective frequencies in 
  // an ArrayList of integers. It uses these parameters to create a Huffman Tree. 
  Huffman(ArrayList<String> letters, ArrayList<Integer> numbers) {
    // Exception thrown if two inputted arraylists are not of the same length or, if the 
    // arraylist of letters is of size less than two (because then it would be illogical to make
    // use of a Huffman Tree). 
    if ((letters.size() != numbers.size()) || letters.size() < 2) {
      throw new IllegalArgumentException("Lists don't meet input criteria.");
    }
    this.letters = letters;
    this.numbers = numbers;
    Forest encodedTree = new Forest(new ArrayList<ATree>());
    encodedTree.populateTree(letters, numbers);
    new Utils().sort(encodedTree.orchard, new TreeCompare());
    encodedTree.makeHuffmanTree();
    this.f = encodedTree;
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.letters ...                                    -- ArrayList<String>
   * ... this.numbers ...                                    -- ArrayList<Integer>
   * ... this.f ...                                          -- Forest
   * METHODS:
   * ... encode(String input) ...                            -- ArrayList<Boolean>
   * ... decode(ArrayList<Boolean> input) ...                -- String
   * METHODS ON FIELDS:
   * ... this.f.toBoolean(String input) ...                  -- ArrayList<Boolean>
   * ... this.f.decodingTree(ArrayList<Boolean> input) ...   -- String
   */
  
  // This method takes in an input in the form of a string to then "encode" the given string, based
  // on this Huffman tree. The method traverses this Huffman Tree to perform this encoding, saving
  // its steps in an arraylist of instructions and returning it, depicting the encoded string 
  // in an arraylist of booleans. 
  ArrayList<Boolean> encode(String input) {
    /* TEMPLATE
     * PARAMETERS:
     * ... input ...   -- String
     */
    return this.f.toBoolean(input);
  }
  
  // This method is quite the inverse of the encode method. This method uses the provided arraylist
  // of booleans and the given arraylist serves as instructions to traverse this Huffman Tree. 
  // After decoding process, where it has mapped the list of boolean values to their respective 
  // character values, a collation of these character values are returned as a string. 
  String decode(ArrayList<Boolean> input) {
    /* TEMPLATE
     * ... input ...   -- ArrayList<Boolean>
     */
    ArrayList<Boolean> version2 = new ArrayList<Boolean>();
    
    for (boolean item : input) {
      version2.add(item);
    }
    
    return this.f.decodingTree(version2);
  }
}


// This interface represents an ITree, which is one of:
// - Node
// - Leaf
// An ITree repesents either an encoded value available in the Huffman Tree (leaf) or a collection 
// of such encoded values(subtree). 
interface ITree {
  
}

// The class serves as an abstracted representation for an ITree, containing the value associated
// with any ITree. 
abstract class ATree implements ITree {
  int freq;
  
  // Constructor
  ATree(int freq) {
    this.freq = freq;
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.freq ...                                                      -- int
   * METHODS:
   * ... isSmallerFreq(ATree other) ...                                     -- boolean
   * ... find(String c) ...                                                 -- boolean
   * ... updateConverted(ArrayList<Boolean> converted, String letter) ...   -- void
   * ... decodeThisTree(ArrayList<Boolean> input, Forest f) ...             -- String
   */ 
  
  // This method checks whether the frequency of this ATree is smaller than the frequency of the 
  // given ATree. 
  boolean isSmallerFreq(ATree other) {
    /* TEMPLATE
     * PARAMETERS:
     * ... other ...        -- ATree
     * FIELDS OF PARAMETERS:
     * ... other.freq ...   -- int
     */
    return (this.freq < other.freq);
  }
  
  // This method is meant to check whether the given string exists in this particular ATree or not.
  abstract boolean find(String c);
  
  // This method is meant to update the arraylist of booleans based on where the encoded letter is
  // located in the Huffman Tree. 
  abstract void updateConverted(ArrayList<Boolean> converted, String letter);
  
  // This method is meant to use the given arraylist of booleans as instructions to locate what
  // is meant to be decoded. This method also keeps track of the current Forest that is being 
  // navigated. 
  abstract String decodeThisTree(ArrayList<Boolean> input, Forest f);
}

// This represents a Node in an ATree, that is a combination of two ITrees. 
class Node extends ATree {
  ATree left;
  ATree right;
  
  // Constructor
  Node(int total, ATree left, ATree right) {
    super(total);
    this.left = left;
    this.right = right;
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.freq ...                                                                 -- int
   * ... this.left ...                                                                 -- ATree
   * ... this.right ...                                                                -- ATree
   * METHODS:
   * ... isSmallerFreq(ATree other) ...                                                -- boolean
   * ... find(String c) ...                                                            -- boolean
   * ... updateConverted(ArrayList<Boolean> converted, String letter) ...              -- void
   * ... decodeThisTree(ArrayList<Boolean> input, Forest f) ...                        -- String
   * METHODS ON FIELDS:
   * ... this.left.find(String c) ...                                                  -- boolean
   * ... this.right.find(String c) ...                                                 -- boolean
   * ... this.left.updateConverted(ArrayList<Boolean> converted, String letter) ...    -- void
   * ... this.right.updateConverted(ArrayList<Boolean> converted, String letter) ...   -- void
   * ... this.right.decodeThisTree(ArrayList<Boolean> input, Forest f) ...             -- String
   * ... this.left.decodeThisTree(ArrayList<Boolean> input, Forest f) ...              -- String
   */ 
  
  // This method checks whether or not the given string is present in this Node or not. More
  // specifically, it checks whether or not the given string is present in the two ITrees that
  // constitute the Node. 
  public boolean find(String c) {
    /* TEMPLATE
     * PARAMETERS:
     * ... c ...   -- String
     */
    return this.left.find(c) || this.right.find(c);
    
  }
  
  // EFFECT: Uses this Node to modify the given arraylist of booleans in the process of encoding 
  // and forming a resultant arraylist of booleans that consists the encoded values. 
  public void updateConverted(ArrayList<Boolean> converted, String letter) {
    /* TEMPLATE
     * PARAMETERS:
     * ... converted ...                 -- ArrayList<Boolean>
     * ... letter ...                    -- String
     * METHODS ON PARAMETERS:
     * ... converted.add(Object o) ...   -- boolean
     */
    if (this.left.find(letter)) {
      converted.add(false);
      this.left.updateConverted(converted, letter);
    } else {
      converted.add(true);
      this.right.updateConverted(converted, letter);
    }
  } 
  
  // This method uses the user-inputted arraylist of booleans to traverse through this Node, 
  // in order to reach the characters at the leaves of the Huffman Tree, during the process of
  // decoding. 
  public String decodeThisTree(ArrayList<Boolean> input, Forest f) {
    /* TEMPLATE
     * PARAMETERS:
     * ... input ...                     -- ArrayList<Boolean>
     * ... f ...                         -- Forest
     * METHODS ON PARAMETERS:
     * ... input.size() ...              -- int
     * ... input.remove(int index) ...   -- Object o
     */
    if (input.size() == 0) {
      return "?";
    }
    boolean direction = input.remove(0);
    if (direction) {
      return this.right.decodeThisTree(input, f);
    } else {
      return this.left.decodeThisTree(input, f);
    }
  }
}

// This method represents a Leaf in an ATree, that holds an encoded character and its respective
// frequency. 
class Leaf extends ATree {
  String val;
  
  // Constructor
  Leaf(String val, int freq) {
    super(freq);
    this.val = val;
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.freq ...                                                      -- int
   * ... this.val ...                                                       -- String
   * METHODS:
   * ... isSmallerFreq(ATree other) ...                                     -- boolean
   * ... find(String c) ...                                                 -- boolean
   * ... updateConverted(ArrayList<Boolean> converted, String letter) ...   -- void
   * ... decodeThisTree(ArrayList<Boolean> input, Forest f) ...             -- String
   * METHODS ON FIELDS:
   * ... this.val.equals(String str) ...                                    -- boolean
   */ 
  
  // This method checks whether the given string is the same as this Leaf's encoded symbol. If 
  // yes, then the given string has been found in this Huffman Tree!
  public boolean find(String c) {
    /* TEMPLATE
     * PARAMETERS:
     * ... c ...   -- String
     */
    return this.val.equals(c);
  }
  
  // EFFECT: This simply marks the end of traversal during the process of encoding one letter.
  public void updateConverted(ArrayList<Boolean> converted, String letter) {
    /* TEMPLATE
     * PARAMETERS:
     * ... converted ...   -- ArrayList<Boolean>
     * ... letter ...      -- String
     */
    return;
  }
  
  // This method adds the value of this Leaf's encoded symbol to the string output of decoding in
  // the decoding process and simply resets the process to start again at the top of the Huffman
  // Tree with the remaining arraylist of booleans(instructions). 
  public String decodeThisTree(ArrayList<Boolean> input, Forest f) {
    /* TEMPLATE
     * PARAMETERS:
     * ... input ...                                      -- ArrayList<Boolean>
     * ... f ...                                          -- Forest
     * METHODS ON PARAMETERS:
     * ... f.decodingTree(ArrayList<Boolean> input) ...   -- String
     */
    return this.val + f.decodingTree(input);
  }
}

// This structurally represents the Huffman Tree. 
class Forest {
  ArrayList<ATree> orchard;
  
  // Constructor
  Forest(ArrayList<ATree> orchard) {
    this.orchard = orchard;
  }
  
  /* TEMPLATE
   * FIELDS:
   * ... this.orchard ...                                -- ArrayList<ATree>
   * METHODS:
   * ... populateTree(ArrayList<String> letters, 
   *                  ArrayList<Integer> numbers) ...    -- void
   * ... makeHuffmanTree() ...                           -- void
   * ... insert(ATree element) ...                       -- void
   * ... toBoolean(String input) ...                     -- ArrayList<Boolean>
   * ... decodingTree(ArrayList<Boolean> input) ...      -- String
   * METHODS ON FIELDS:
   * ... this.orchard.add(Atree object) ...              -- boolean
   * ... this.orchard.size() ...                         -- int
   * ... this.orchard.remove(int index) ...              -- ATree
   * ... this.orchard.get(int index) ...                 -- ATree
   * ... this.orchard.add(int index, ATree object) ...   -- void
   */
  
  // EFFECT: Modifies this Forest to be filled up with the given letters/symbols 
  // and their respective frequencies. 
  void populateTree(ArrayList<String> letters, ArrayList<Integer> numbers) {
    /* TEMPLATE
     * PARAMETERS:
     * ... letters ...                  -- ArrayList<String>
     * ... numbers ...                  -- ArrayList<Integer>
     * METHODS ON PARAMETERS:
     * ... letters.size() ...           -- int
     * ... letters.get(int index) ...   -- String
     * ... numbers.get(int index) ...   -- Integer
     */
    for (int i = 0 ; i < letters.size(); i += 1) {
      ATree leaf = new Leaf(letters.get(i), numbers.get(i));
      this.orchard.add(leaf);
    }
  }
  
  // EFFECT: Modifies this Forest to construct itself into a Huffman Tree-like structure from 
  // its constituent character and frequency values. 
  void makeHuffmanTree() {
    /* TEMPLATE
     * Template: Same as class template.
     */
    while (this.orchard.size() > 1) {
      ATree left = this.orchard.remove(0);
      ATree right = this.orchard.remove(0);
      
      ATree combined = new Node(left.freq + right.freq, left, right);
      
      this.insert(combined);
    }
  }
  
  // EFFECT: Modifies this Forest by adding an ATree into this Forest's arraylist of ATrees, 
  // without disturbing the sorted arraylist of ATrees by adding in the correct place - the 
  // deepest point that retains ascending relation. 
  void insert(ATree element) {
    /* TEMPLATE
     * PARAMETERS:
     * ... element ...                              -- ATree
     * METHODS ON PARAMETERS:
     * ... element.isSmallerFreq(ATree other) ...   -- boolean
     */
    for (int i = 0; i <= this.orchard.size(); i += 1) {
      if (i == this.orchard.size()) {
        this.orchard.add(element);
        i += this.orchard.size();
      } else if (element.isSmallerFreq(this.orchard.get(i))) {
        this.orchard.add(i, element);
        i += this.orchard.size();
      }
    }
  }
  
  // This method takes the given string input and executes on this Forest, converting the input 
  // string into an arraylist of booleans, depicting the encoded version of the given input. 
  ArrayList<Boolean> toBoolean(String input) {
    /* TEMPLATE
     * PARAMETERS:
     * ... input ...   -- String
     */
    ArrayList<Boolean> converted = new ArrayList<Boolean>();
    String str = input;
    while (!str.equals("")) {
      String letter = str.substring(0,1);
      str = str.substring(1);
      if (!this.orchard.get(0).find(letter)) {
        throw new IllegalArgumentException(
            "Tried to encode " + letter + " but that is not part of the language.");
      } else {
        this.orchard.get(0).updateConverted(converted, letter);
      }     
    }
    return converted;
  }
  
  // This method uses the given arraylist of booleans and decodes it, in order to return a 
  // string that matches the given steps in the boolean arraylist. 
  String decodingTree(ArrayList<Boolean> input) {
    /* TEMPLATE
     * PARAMETERS:
     * ... input ...          -- ArrayList<Boolean>
     * METHODS ON PARAMETERS:
     * ... input.size() ...   -- int
     */
    if (input.size() == 0) {
      return "";
    }
    return this.orchard.get(0).decodeThisTree(input, this);
  }
  
}

// A Utility Class. 
class Utils {
  
  /* TEMPLATE
   * METHODS:
   * ... sort(ArrayList<ATree> array, IComparator<ATree> comp) ...   -- ArrayList<ATree>
   * ... swap(ArrayList<ATree> array, int one, int two) ...          -- ArrayList<ATree>
   * ... indexOfMin(ArrayList<ATree> array, 
   *                int start, 
   *                IComparator<ATree> comp) ...                     -- int
   */
  
  // EFFECT: This method implements the selectionSort algorithm on the given arrayList, 
  // using the given comparator. 
  ArrayList<ATree> sort(ArrayList<ATree> array, IComparator<ATree> comp) {
    /* TEMPLATE
     * PARAMETERS:
     * ... array ...          -- ArrayList<ATree>
     * ... comp ...           -- IComparator<ATree> comp
     * METHODS ON PARAMETERS:
     * ... array.size() ...   -- int
     */
    for (int i = 0; i < array.size(); i += 1) {
      int indexOfMin = new Utils().indexOfMin(array, i, comp);
      array = new Utils().swap(array, i, indexOfMin);
    }
    return array;
  }
  
  // EFFECT: This method implements a swap between two elements located at the two given indices, 
  // within the given arraylist. 
  ArrayList<ATree> swap(ArrayList<ATree> array, int one, int two) {
    /* TEMPLATE
     * PARAMETERS:
     * ... array ...                  -- ArrayList<ATree>
     * ... one ...                    -- int
     * ... two ...                    -- int
     * METHODS ON PARAMETERS:
     * ... array.get(int index) ...                 -- ATree
     * ... array.set(int index, ATree object) ...   -- ATree
     */
    ATree temp = array.get(one);
    array.set(one, array.get(two));
    array.set(two,  temp);
    return array;
  }
  
  // EFFECT: This method finds the minimum value into the given arraylist, in accordance with the 
  // given comparator. 
  int indexOfMin(ArrayList<ATree> array, int start, IComparator<ATree> comp) {
    /* TEMPLATE
     * ... array ...                        -- ArrayList<ATree>
     * ... start ...                        -- int
     * ... comp ...                         -- IComparator<ATree>
     * METHODS ON PARAMETERS:
     * ... array.get(int index) ...         -- ATree
     * ... array.size() ...                 -- int
     * ... comp.compare(T one, T two) ...   -- boolean
     */
    ATree min = array.get(start);
    int minVal = start;
    for (int i = start + 1; i < array.size(); i += 1) {
      if (comp.compare(min, array.get(i)) > 0) {
        min = array.get(i);
        minVal = i;
      }
    }
    return minVal;
  }
}

class ExamplesHuffman {
  
  //tests that the exceptions are thrown when expected
  boolean testExceptions(Tester t) {
    ArrayList<String> letters = new ArrayList<String>();
    letters.add("a");
    letters.add("b");
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    numbers.add(1);
    
    ArrayList<String> letters2 = new ArrayList<String>();
    letters2.add("a");
    ArrayList<Integer> numbers2 = new ArrayList<Integer>();
    numbers2.add(1);
    
    ArrayList<String> letters3 = new ArrayList<String>();
    letters3.add("a");
    letters3.add("b");
    ArrayList<Integer> numbers3 = new ArrayList<Integer>();
    numbers3.add(1);
    numbers3.add(1);
    Huffman example = new Huffman(letters3, numbers3);
    
    return t.checkConstructorExceptionType(
        Exception.class, "Huffman", letters, numbers)
        && t.checkConstructorExceptionType(
            Exception.class, "Huffman", letters2, numbers2)
        && t.checkException( 
        new IllegalArgumentException(
            "Tried to encode ! but that is not part of the language."), 
        example, "encode", "aba!baba");
  }
  
  //tests that the compare method works as expected
  boolean testCompare(Tester t) {
    return t.checkExpect(new TreeCompare().compare(new Leaf("c", 7), 
        new Node(5, new Leaf("a", 2), new Leaf("b", 5))), 2)
        && t.checkExpect(new TreeCompare().compare(new Leaf("c", 2), 
            new Node(9, new Leaf("a", 2), new Leaf("b", 5))), -7)
        && t.checkExpect(new TreeCompare().compare(new Node(9, new Leaf("c", 2), new Leaf("d", 1)), 
            new Node(5, new Leaf("a", 2), new Leaf("b", 5))), 4);
  }
  
  //tests that the encode method works as expected
  void testEncode(Tester t) {
    ArrayList<String> letters = new ArrayList<String>();
    letters.add("a");
    letters.add("b");
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    numbers.add(1);
    numbers.add(1);
    ArrayList<Boolean> result = new ArrayList<Boolean>();
    result.add(false);
    result.add(true);
    t.checkExpect(new Huffman(letters, numbers).encode("ab"), result);
      
    ArrayList<String> letters2 = new ArrayList<String>();
    letters2.add("a");
    letters2.add("b");
    letters2.add("c");
    letters2.add("d");
    letters2.add("e");
    letters2.add("f");
    ArrayList<Integer> numbers2 = new ArrayList<Integer>();
    numbers2.add(12);
    numbers2.add(45);
    numbers2.add(5);
    numbers2.add(13);
    numbers2.add(9);
    numbers2.add(16);
    ArrayList<Boolean> result2 = new ArrayList<Boolean>();
    result2.add(true);
    result2.add(false);
    result2.add(false);
    
    result2.add(false);
    
    result2.add(true);
    result2.add(true);
    result2.add(false);
    result2.add(false);
    t.checkExpect(new Huffman(letters2, numbers2).encode("abc"), result2);
    
    ArrayList<String> letters3 = new ArrayList<String>();
    letters3.add("a");
    letters3.add("b");
    letters3.add("c");
    ArrayList<Integer> numbers3 = new ArrayList<Integer>();
    numbers3.add(1);
    numbers3.add(2);
    numbers3.add(3);
    ArrayList<Boolean> result3 = new ArrayList<Boolean>();
    result3.add(true);
    result3.add(false);
    result3.add(true);
    result3.add(true);
    result3.add(false);
    t.checkExpect(new Huffman(letters3, numbers3).encode("abc"), result3);
  }

  
  //tests that the decode method works as expected
  void testDecode(Tester t) {
    ArrayList<String> letters = new ArrayList<String>();
    letters.add("a");
    letters.add("b");
    letters.add("c");
    letters.add("d");
    letters.add("e");
    letters.add("f");
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    numbers.add(12);
    numbers.add(45);
    numbers.add(5);
    numbers.add(13);
    numbers.add(9);
    numbers.add(16);
    ArrayList<Boolean> result = new ArrayList<Boolean>();
    result.add(true);
    result.add(false);
    result.add(false);
    
    result.add(false);
    
    result.add(true);
    result.add(true);
    result.add(false);
    result.add(false);
    result.add(true);
    t.checkExpect(new Huffman(letters, numbers).decode(result), "abc?");
    
    ArrayList<String> letters2 = new ArrayList<String>();
    letters2.add("a");
    letters2.add("b");
    ArrayList<Integer> numbers2 = new ArrayList<Integer>();
    numbers2.add(1);
    numbers2.add(1);
    ArrayList<Boolean> result2 = new ArrayList<Boolean>();
    result2.add(false);
    result2.add(true);
    t.checkExpect(new Huffman(letters2, numbers2).decode(result2), "ab");
    
    ArrayList<String> letters3 = new ArrayList<String>();
    letters3.add("a");
    letters3.add("b");
    letters3.add("c");
    ArrayList<Integer> numbers3 = new ArrayList<Integer>();
    numbers3.add(1);
    numbers3.add(2);
    numbers3.add(3);
    ArrayList<Boolean> result3 = new ArrayList<Boolean>();
    result3.add(true);
    result3.add(true);
    result3.add(false);
    result3.add(true);
    t.checkExpect(new Huffman(letters3, numbers3).decode(result3), "bc?");
  }
  
  //tests that the isSmallerFreq method works as expected
  boolean testIsSmallerFreq(Tester t) {
    Leaf leaf = new Leaf("ab", 1);
    Leaf leaf2 = new Leaf("c", 9);
    Leaf leaf3 = new Leaf("d", 1);
    Node node = new Node(4, leaf, leaf);
    Node bigger = new Node(10, node, leaf);
    return t.checkExpect(leaf.isSmallerFreq(leaf3), false)
        && t.checkExpect(leaf.isSmallerFreq(leaf2), true)
        && t.checkExpect(leaf2.isSmallerFreq(node), false)
        && t.checkExpect(node.isSmallerFreq(bigger), true);
  }
  
  //tests that the find method works as expected
  boolean testFind(Tester t) {
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    return t.checkExpect(leaf.find("a"), true)
        && t.checkExpect(node.find("c"), false)
        && t.checkExpect(bigger.find("b"), true);
  }
  
  //tests that the updateConverted method works as expected
  void testUpdateConverted(Tester t) {
    ArrayList<Boolean> converted = new ArrayList<Boolean>();
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    leaf.updateConverted(converted, "a");
    ArrayList<Boolean> converted2 = new ArrayList<Boolean>();
    t.checkExpect(converted, converted2);
    
    ArrayList<Boolean> c = new ArrayList<Boolean>();
    node.updateConverted(c, "b");
    ArrayList<Boolean> c2 = new ArrayList<Boolean>();
    c2.add(true);
    t.checkExpect(c, c2);
    
    ArrayList<Boolean> c4 = new ArrayList<Boolean>();
    bigger.updateConverted(c4, "b");
    ArrayList<Boolean> c5 = new ArrayList<Boolean>();
    c5.add(false);
    c5.add(true);
    t.checkExpect(c4, c5);
  }
  
  //tests that the decodeThisTree method works as expected
  void testDecodeThisTree(Tester t) {
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    ArrayList<Boolean> converted = new ArrayList<Boolean>();
    converted.add(true);
    converted.add(false);
    ArrayList<ATree> tree = new ArrayList<ATree>();
    tree.add(bigger);
    t.checkExpect(bigger.decodeThisTree(converted, new Forest(tree)), "a?");
    
    ArrayList<ATree> tr = new ArrayList<ATree>();
    tr.add(leaf);
    tr.add(node);
    tr.add(leaf2);
    tr.add(bigger);
    Forest f = new Forest(tr);
    f.makeHuffmanTree();
    
    ArrayList<ATree> treev2 = new ArrayList<ATree>();
    Node firstC = new Node(5, leaf, node);
    Node secondC = new Node(10, leaf2, firstC);
    Node thirdC = new Node(20, bigger, secondC);
    
    ArrayList<Boolean> converted2 = new ArrayList<Boolean>();
    converted2.add(true);
    converted2.add(false);
    converted2.add(false);
    converted2.add(false);
    converted2.add(true);
    converted2.add(true);
    
    t.checkExpect(thirdC.decodeThisTree(converted2, new Forest(tr)), "bb?");
  }
  
  //tests that the populateTree method works as expected
  void testPopulateTree(Tester t) {
    ArrayList<String> letters = new ArrayList<String>();
    letters.add("a");
    letters.add("b");
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    numbers.add(1);
    numbers.add(1);
    ArrayList<ATree> tree = new ArrayList<ATree>();
    Forest f = new Forest(tree);
    f.populateTree(letters, numbers);
    
    ArrayList<ATree> tree2 = new ArrayList<ATree>();
    tree2.add(new Leaf("a", 1));
    tree2.add(new Leaf("b", 1));
    Forest f2 = new Forest(tree2);
    t.checkExpect(f, f2);
    
    ArrayList<String> letters2 = new ArrayList<String>();
    letters2.add("a");
    letters2.add("b");
    letters2.add("c");
    letters2.add("d");
    letters2.add("e");
    letters2.add("f");
    ArrayList<Integer> numbers2 = new ArrayList<Integer>();
    numbers2.add(12);
    numbers2.add(45);
    numbers2.add(5);
    numbers2.add(13);
    numbers2.add(9);
    numbers2.add(16);
    ArrayList<ATree> treev2 = new ArrayList<ATree>();
    Forest fore = new Forest(treev2);
    fore.populateTree(letters2, numbers2);
    
    ArrayList<ATree> tree2v2 = new ArrayList<ATree>();
    tree2v2.add(new Leaf("a", 12));
    tree2v2.add(new Leaf("b", 45));
    tree2v2.add(new Leaf("c", 5));
    tree2v2.add(new Leaf("d", 13));
    tree2v2.add(new Leaf("e", 9));
    tree2v2.add(new Leaf("f", 16));
    Forest fore2 = new Forest(tree2v2);
    t.checkExpect(fore, fore2);
  }
  
  //tests that the makeHuffmanTree method works as expected
  void testMakeHuffmanTree(Tester t) {
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    ArrayList<ATree> tree = new ArrayList<ATree>();
    tree.add(leaf);
    tree.add(node);
    tree.add(leaf2);
    tree.add(bigger);
    Forest f = new Forest(tree);
    f.makeHuffmanTree();
    
    ArrayList<ATree> treev2 = new ArrayList<ATree>();
    Node firstC = new Node(5, leaf, node);
    Node secondC = new Node(10, leaf2, firstC);
    Node thirdC = new Node(20, bigger, secondC);
    treev2.add(thirdC);
    Forest fv2 = new Forest(treev2);
    t.checkExpect(f, fv2);
    
    Leaf l = new Leaf("a", 1);
    Node n = new Node(4, l, l);
    Node fC = new Node(5, l, n);
    ArrayList<ATree> tree2 = new ArrayList<ATree>();
    tree2.add(l);
    tree2.add(n);
    Forest f2 = new Forest(tree2);
    f2.makeHuffmanTree();
    
    ArrayList<ATree> tree2v2 = new ArrayList<ATree>();
    tree2v2.add(fC);
    Forest f2v2 = new Forest(tree2v2);
    t.checkExpect(f2,  f2v2);
  }
  
  //tests that the insert method works as expected
  void testInsert(Tester t) {
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    ArrayList<ATree> tree = new ArrayList<ATree>();
    tree.add(leaf);
    tree.add(node);
    tree.add(leaf2);
    tree.add(bigger);
    Forest f = new Forest(tree);
    f.insert(new Leaf("c", 3));
    
    ArrayList<ATree> tree2 = new ArrayList<ATree>();
    tree2.add(leaf);
    tree2.add(new Leaf("c", 3));
    tree2.add(node);
    tree2.add(leaf2);
    tree2.add(bigger);
    Forest f2 = new Forest(tree2);
    t.checkExpect(f, f2);
    
    Leaf l = new Leaf("a", 1);
    Leaf l2 = new Leaf("b", 5);
    Node n = new Node(4, l, l2);
    Node biggerN = new Node(10, n, l);
    ArrayList<ATree> tr = new ArrayList<ATree>();
    tr.add(leaf);
    tr.add(node);
    tr.add(leaf2);
    tr.add(bigger);
    Forest fore = new Forest(tr);
    fore.insert(new Node(10, l, l));
    
    ArrayList<ATree> t2 = new ArrayList<ATree>();
    t2.add(l);
    t2.add(n);
    t2.add(l2);
    t2.add(biggerN);
    t2.add(new Node(10, l, l));
    Forest fore2 = new Forest(t2);
    t.checkExpect(fore, fore2);

  }
  
  //tests that the toBoolean method works as expected
  void testToBoolean(Tester t) {
    ArrayList<String> letters = new ArrayList<String>();
    letters.add("a");
    letters.add("b");
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    numbers.add(1);
    numbers.add(1);
    ArrayList<Boolean> result = new ArrayList<Boolean>();
    result.add(false);
    result.add(true);
    t.checkExpect(new Huffman(letters, numbers).f.toBoolean("ab"), result);
      
    ArrayList<String> letters2 = new ArrayList<String>();
    letters2.add("a");
    letters2.add("b");
    letters2.add("c");
    letters2.add("d");
    letters2.add("e");
    letters2.add("f");
    ArrayList<Integer> numbers2 = new ArrayList<Integer>();
    numbers2.add(12);
    numbers2.add(45);
    numbers2.add(5);
    numbers2.add(13);
    numbers2.add(9);
    numbers2.add(16);
    ArrayList<Boolean> result2 = new ArrayList<Boolean>();
    result2.add(true);
    result2.add(false);
    result2.add(false);
    
    result2.add(false);
    
    result2.add(true);
    result2.add(true);
    result2.add(false);
    result2.add(false);
    t.checkExpect(new Huffman(letters2, numbers2).f.toBoolean("abc"), result2);
    
    ArrayList<String> letters3 = new ArrayList<String>();
    letters3.add("a");
    letters3.add("b");
    letters3.add("c");
    ArrayList<Integer> numbers3 = new ArrayList<Integer>();
    numbers3.add(1);
    numbers3.add(2);
    numbers3.add(3);
    ArrayList<Boolean> result3 = new ArrayList<Boolean>();
    result3.add(true);
    result3.add(false);
    result3.add(true);
    result3.add(true);
    result3.add(false);
    t.checkExpect(new Huffman(letters3, numbers3).f.toBoolean("abc"), result3);
  }
  
  //tests that the decodingTree method works as expected
  void testDecodingTree(Tester t) {
    ArrayList<String> letters = new ArrayList<String>();
    letters.add("a");
    letters.add("b");
    letters.add("c");
    letters.add("d");
    letters.add("e");
    letters.add("f");
    ArrayList<Integer> numbers = new ArrayList<Integer>();
    numbers.add(12);
    numbers.add(45);
    numbers.add(5);
    numbers.add(13);
    numbers.add(9);
    numbers.add(16);
    ArrayList<Boolean> result = new ArrayList<Boolean>();
    result.add(true);
    result.add(false);
    result.add(false);
    
    result.add(false);
    
    result.add(true);
    result.add(true);
    result.add(false);
    result.add(false);
    result.add(true);
    t.checkExpect(new Huffman(letters, numbers).f.decodingTree(result), "abc?");
    
    ArrayList<String> letters2 = new ArrayList<String>();
    letters2.add("a");
    letters2.add("b");
    ArrayList<Integer> numbers2 = new ArrayList<Integer>();
    numbers2.add(1);
    numbers2.add(1);
    ArrayList<Boolean> result2 = new ArrayList<Boolean>();
    result2.add(false);
    result2.add(true);
    t.checkExpect(new Huffman(letters2, numbers2).f.decodingTree(result2), "ab");
    
    ArrayList<String> letters3 = new ArrayList<String>();
    letters3.add("a");
    letters3.add("b");
    letters3.add("c");
    ArrayList<Integer> numbers3 = new ArrayList<Integer>();
    numbers3.add(1);
    numbers3.add(2);
    numbers3.add(3);
    ArrayList<Boolean> result3 = new ArrayList<Boolean>();
    result3.add(true);
    result3.add(true);
    result3.add(false);
    result3.add(true);
    t.checkExpect(new Huffman(letters3, numbers3).f.decodingTree(result3), "bc?");
  }

  //tests that the sort method works as expected
  void testSort(Tester t) {
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    ArrayList<ATree> tree = new ArrayList<ATree>();
    tree.add(leaf);
    tree.add(leaf2);
    tree.add(node);
    tree.add(bigger);
    ArrayList<ATree> treev2 = new ArrayList<ATree>();
    treev2.add(leaf);
    treev2.add(node);
    treev2.add(leaf2);
    treev2.add(bigger);
    t.checkExpect(new Utils().sort(tree, new TreeCompare()), treev2);
    
    Leaf l = new Leaf("a", 10);
    Leaf l2 = new Leaf("b", 5);
    Node n = new Node(4, leaf, leaf2);
    Node biggerN = new Node(10, node, leaf);
    ArrayList<ATree> tree2 = new ArrayList<ATree>();
    tree2.add(l);
    tree2.add(l2);
    tree2.add(n);
    tree2.add(biggerN);
    ArrayList<ATree> tree2v2 = new ArrayList<ATree>();
    tree2v2.add(n);
    tree2v2.add(l2);
    tree2v2.add(l);
    tree2v2.add(biggerN);
    t.checkExpect(new Utils().sort(tree2, new TreeCompare()), tree2v2);
  }
  
  //tests that the swap mathod works as expected
  void testSwap(Tester t) {
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    ArrayList<ATree> tree = new ArrayList<ATree>();
    tree.add(leaf);
    tree.add(leaf2);
    tree.add(node);
    tree.add(bigger);
    ArrayList<ATree> treev2 = new ArrayList<ATree>();
    treev2.add(leaf2);
    treev2.add(leaf);
    treev2.add(node);
    treev2.add(bigger);
    t.checkExpect(new Utils().swap(tree, 0, 1), treev2);
    
    Leaf l = new Leaf("a", 10);
    Leaf l2 = new Leaf("b", 5);
    Node n = new Node(4, leaf, leaf2);
    Node biggerN = new Node(10, node, leaf);
    ArrayList<ATree> tree2 = new ArrayList<ATree>();
    tree2.add(l);
    tree2.add(l2);
    tree2.add(n);
    tree2.add(biggerN);
    ArrayList<ATree> tree2v2 = new ArrayList<ATree>();
    tree2v2.add(l);
    tree2v2.add(biggerN);
    tree2v2.add(n);
    tree2v2.add(l2);
    t.checkExpect(new Utils().swap(tree2, 1, 3), tree2v2);
  }
  
  //tests that the indexOfMin method works as expected
  boolean testIndexOfMin(Tester t) {
    Leaf leaf = new Leaf("a", 1);
    Leaf leaf2 = new Leaf("b", 5);
    Node node = new Node(4, leaf, leaf2);
    Node bigger = new Node(10, node, leaf);
    ArrayList<ATree> tree = new ArrayList<ATree>();
    tree.add(leaf);
    tree.add(leaf2);
    tree.add(node);
    tree.add(bigger);
    
    Leaf l = new Leaf("a", 10);
    Leaf l2 = new Leaf("b", 5);
    Node n = new Node(4, leaf, leaf2);
    Node biggerN = new Node(10, node, leaf);
    ArrayList<ATree> tree2 = new ArrayList<ATree>();
    tree2.add(l);
    tree2.add(l2);
    tree2.add(n);
    tree2.add(biggerN);
    return t.checkExpect(new Utils().indexOfMin(tree, 0, new TreeCompare()), 0)
        && t.checkExpect(new Utils().indexOfMin(tree2, 0, new TreeCompare()), 2);
  }
}