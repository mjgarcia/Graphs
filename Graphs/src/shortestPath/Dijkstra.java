package shortestPath;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {

    public static class Vertex implements Comparable<Vertex>{
        final String id;
        Edge[] adjacencies;
        double minDistance = Double.MAX_VALUE;
        Vertex previous;
        Vertex(String id) {
            this.id = id;
        }
        @Override
        public int compareTo(Vertex arg0) {
            return Double.compare(this.minDistance, arg0.minDistance);
        }
    }
    
    public static class Edge {
        Vertex target;
        double weight;
        Edge(Vertex target, double weight) {
            this.target = target;
            this.weight = weight;
        }
    }

    public static void shortestPath(Vertex source, Vertex target) {
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        source.minDistance = 0;
        queue.add(source);
        
        while(!queue.isEmpty()) {
            Vertex u = queue.poll();
            if (u == target) return;
            
            for(Edge e : u.adjacencies) {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if(distanceThroughU < v.minDistance) {
                    queue.remove(v);
                    v.minDistance = distanceThroughU;
                    v.previous = u;
                    queue.add(v);
                }
            }
        }
    }
    
    public static List<Vertex> path(Vertex target) {
        List<Vertex> path = new LinkedList<>();
        for (Vertex current = target; current != null; current = current.previous) {
            path.add(current);
        }
        Collections.reverse(path);
        return path;

    }
    
    public static void main(String[] args) {
        // Add vertices
        int size = 6;
        Vertex[] vertices = new Vertex[size];
        for(int i=0; i<size; i++) {
            vertices[i] = new Vertex("v"+i);
        }
        
        // Add connections
        vertices[0].adjacencies = new Edge[]{ new Edge(vertices[1],7), new Edge(vertices[2],9), new Edge(vertices[5],14)};
        vertices[1].adjacencies = new Edge[]{ new Edge(vertices[0],7), new Edge(vertices[2],10), new Edge(vertices[3],15)};
        vertices[2].adjacencies = new Edge[]{ new Edge(vertices[0],9), new Edge(vertices[1],10), new Edge(vertices[3],11), new Edge(vertices[5],2)};
        vertices[3].adjacencies = new Edge[]{ new Edge(vertices[1],15), new Edge(vertices[2],11), new Edge(vertices[4],6)};
        vertices[4].adjacencies = new Edge[]{ new Edge(vertices[3],6), new Edge(vertices[5],9)};
        vertices[5].adjacencies = new Edge[]{ new Edge(vertices[0],14), new Edge(vertices[2],2), new Edge(vertices[4],9)};
        
        for(Vertex u : vertices) {
            System.out.print(u.id + " ");
            for(Edge e : u.adjacencies) {
                Vertex v = e.target;
                System.out.print(v.id + "," + e.weight + " ");
            }
            System.out.println();
        }
        
        shortestPath(vertices[0], vertices[4]);
        
        List<Vertex> path = path(vertices[4]);
        
        System.out.println("Shortes path");
        for (Vertex v : path) {
            System.out.println(v.id + " "  + v.minDistance);
        }
    }

}
