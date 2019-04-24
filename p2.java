import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

interface Tree {
    void build_expression_tree(char str[]);
    void evaluate_expression_tree(ArrayList list);
}

class TreeNode {
    char data;
    TreeNode left, right;

    TreeNode(char item) {
        data = item;
        left = right = null;
    }
}

public class p2 implements Tree {
    TreeNode n;
    ArrayList<Character> list = new ArrayList<>();

   // Checks if it's an operator
    boolean isOperator(char c) {
        return c == '+' || c == '-'
                || c == '*' || c == '/';
    }

    // Converts it into inorder
    void inorder(TreeNode n) {
        if (n != null) {
            inorder(n.left);
            System.out.print(n.data + " ");
            list.add(n.data);
            inorder(n.right);
        }
    }

    // Clears the list for next input
    public void clearList() {
        list.clear();
    }

    // Returns the list
    public ArrayList getList() {
        return list;
    }

    @Override
    public void build_expression_tree(char str[]) {
        Stack<TreeNode> stack = new Stack();
        TreeNode node, leftChild, rightChild;

        // This loop will traverse through every character of the input expression
        for (int i = 0; i < str.length; i++) {

            // If it is an operand, then push it onto the stack
            if (!isOperator(str[i])) {
                node = new TreeNode(str[i]);
                stack.push(node);
            }
            // Otherwise,
            else {
                // Create a new node with the operator
                node = new TreeNode(str[i]);

                // Pop the last two operands
                leftChild = stack.pop();
                rightChild = stack.pop();

                // Make the two operands its children
                node.right = leftChild;
                node.left = rightChild;

                // And then push that operator onto the stack.
                stack.push(node);
            }
        }

        // Get the root operator of the tree
        n = stack.peek();
    }

    // Returns the root node
    public TreeNode getNode() {
        return n;
    }

    @Override
    public void evaluate_expression_tree(ArrayList list) {
        // Gets the first operand of the string
        int sum = ((char) list.get(0).toString().charAt(0)) - '0';
        // For all the other items in the string: conduct operation with operator and next operand
        for (int i = 0; i < list.size(); i++) {
            // If the index contains an operator: conduct the infix operation
            if (i%2 == 1) {
                if ((char) list.get(i).toString().charAt(0) == '+') {
                    sum = sum + ((char) list.get(i+1).toString().charAt(0) - '0');
                }
                else if ((char) list.get(i).toString().charAt(0) == '-') {
                    sum = sum - ((char) list.get(i+1).toString().charAt(0) - '0');
                }
                else if ((char) list.get(i).toString().charAt(0) == '*') {
                    sum = sum * ((char) list.get(i+1).toString().charAt(0) - '0');
                }
                else if ((char) list.get(i).toString().charAt(0) == '/') {
                    sum = sum / ((char) list.get(i+1).toString().charAt(0) - '0');
                }
            }
        }
        System.out.println("FINAL SUM = " + sum);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        p2 et = new p2();
        System.out.println("Type 'End' to end the program");
        String input = reader.readLine();
        while(input != null) {
            if (input.toLowerCase().equals("end")) {
                break;
            }
            input = input.replaceAll(" ", "");
            char array[] = input.toCharArray();
            System.out.println("The input is: " + input);
            et.build_expression_tree(array);
            et.inorder(et.getNode());
            et.evaluate_expression_tree(et.getList());
            et.clearList();
            input = reader.readLine();
        }
    }
}
