package graph;

/**
 * Class to store a vertex and a distance in a single object
 * Allows comparison of the distance of the distances to each vertex
 * when performing search algorithms with comparsion of only a single object
 * @author watso
 *
 */

public class VertexDistancePair implements Comparable<VertexDistancePair>{
		public String vertex;
		public int distance;
		public VertexDistancePair(String vertex, int distance) {
			this.vertex = vertex;
			this.distance= distance;
		}
		
		/**
		 * Returns 1 if passed VDP has smaller tentative distance than this VDP
		 */
		@Override
		public int compareTo(VertexDistancePair vB) {
			int a = this.distance;
			int b =((VertexDistancePair)vB).distance;
			if(a<b) {return -1;}
			if(b<a) {return 1;}
			return 0;
		}
		
		@Override
		public boolean equals(Object vB) {
			if(vB  instanceof VertexDistancePair) {
				return this.equals((VertexDistancePair)vB);
			}
			return false;
		}
		
		public boolean equals(VertexDistancePair vB) {
			if(this.vertex.equals(vB.vertex) 
					&& this.distance == vB.distance) {
				return true;
			}
			return false;
		}
		
}
