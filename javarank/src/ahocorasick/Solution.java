package ahocorasick;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.BiConsumer;



public class Solution {
    public static void main(String[] args) {
        String[] genes = {"a", "b", "c", "aa", "d", "b"};
        int[] healths = {1, 2, 3, 4, 5, 6};
        Node root = createTrieWithLinks(genes, healths);
        System.out.println(computeHealth(root, 1, 5, "caaab"));
        System.out.println(computeHealth(root, 0, 4 , "xyz"));
        System.out.println(computeHealth(root, 2, 4, "bcdybc"));

    }

    @Test
    public void processFile() throws FileNotFoundException {
        Scanner in = new Scanner(new InputStreamReader(new FileInputStream("/home/anton/projects/hackerrank/javarank/src/ahocorasick/input30")));
        int n = in.nextInt();
        String[] genes = new String[n];
        for(int genes_i=0; genes_i < n; genes_i++){
            genes[genes_i] = in.next();
        }
        int[] health = new int[n];
        for(int health_i=0; health_i < n; health_i++){
            health[health_i] = in.nextInt();
        }
        int s = in.nextInt();
        long max = 0;
        long min = Long.MAX_VALUE;
        Node root = createTrieWithLinks(genes, health);
        for(int a0 = 0; a0 < s; a0++){
            int first = in.nextInt();
            int last = in.nextInt();
            String d = in.next();
            long health1 = computeHealth(root, first, last, d);
            if (health1 > max) {
                max = health1;
            }
            if (health1 < min) {
                min = health1;
            }
        }
        System.out.println(min + " " + max);
    }

    static long computeHealth(Node root, int first, int last, String d) {
        class Accum implements BiConsumer<Integer, Integer> {
            long accum = 0;

            @Override
            public void accept(Integer idx, Integer health) {
                if (idx >= first && idx <= last) {
                    accum += health;
                }
            }
        }
        Accum accum = new Accum();
        findPatterns(d, root, accum);
        return accum.accum;
    }

    public static Node createTrie(String[] patterns, int[] weights) {
        Node root = new Node();

        for (int i = 0; i < patterns.length; i++) {
            Node start = root;
            for (Character ch: patterns[i].toCharArray()) {
                start.leafs.putIfAbsent(ch, new Node());
                start = start.leafs.get(ch);
            }
            start.foundTemplates.put(i, patterns[i]);
            start.weights.put(i, start.weights.getOrDefault(i, 0) + weights[i]);
        }
        return root;
    }

    public static Node createTrieWithLinks(String[] patterns, int[] weights) {
        Node root = createTrie(patterns, weights);
        Deque<Node> queue = new ArrayDeque<>();
        for (Node leaf : root.leafs.values()) {
            queue.add(leaf);
            leaf.fail = root;
        }

        while (!queue.isEmpty()) {
            Node rnode = queue.pop();

            for (Map.Entry<Character, Node> entry : rnode.leafs.entrySet()) {
                queue.add(entry.getValue());
                Node failNode = rnode.fail;
                while (failNode != null && !failNode.leafs.containsKey(entry.getKey())) {
                    failNode = failNode.fail;
                }
                entry.getValue().fail = failNode == null ? root : failNode.leafs.get(entry.getKey());
                entry.getValue().foundTemplates.putAll(entry.getValue().fail.foundTemplates);
                entry.getValue().weights.putAll(entry.getValue().fail.weights);
            }
        }
        return root;
    }

    public static void findPatterns(String wholeSequence, Node root, BiConsumer<Integer, Integer> callback) {
        Node current = root;

        for (Character nextChar : wholeSequence.toCharArray()) {
            while (current != null && !current.leafs.containsKey(nextChar)) {
                current = current.fail;
            }
            if (current == null) {
                current = root;
                continue;
            }
            current = current.leafs.get(nextChar);
            for (Map.Entry<Integer, String> foundPattern : current.foundTemplates.entrySet()) {
                callback.accept(foundPattern.getKey(), current.weights.get(foundPattern.getKey()));
            }
        }
    }
}


class Node {
    public Map<Character, Node> leafs = new HashMap<>();
    public Map<Integer, String> foundTemplates = new HashMap<>();
    public Node fail;
    public Map<Integer, Integer> weights = new HashMap<>();
}
