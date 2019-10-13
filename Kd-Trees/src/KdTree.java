import edu.princeton.cs.algs4.*;

public class KdTree {
    private Node root;
    private int size;

    /**
     * Construct an empty set of points.
     */
    public KdTree() {
        size = 0;
    }

    public static void main(String[] args) {
        KdTree kdtree = new KdTree();
        Point2D p = new Point2D(0.2, 0.3);
        kdtree.insert(p);
        StdOut.println(kdtree.contains(p));
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D point2D) {
        if (point2D == null) {
            throw new IllegalArgumentException();
        }
        root = insert(root, point2D, true, new double[]{0, 0, 1, 1});
    }

    private Node insert(Node node, Point2D p, boolean evenLevel, double[] coords) {
        if (node == null) {
            size++;
            return new Node(p, coords);
        }

        double cmp = comparePoints(p, node, evenLevel);

        /**
         * Traverse down the BST.
         *
         * In subsequent levels, the orientation is orthogonal
         * to the current orientation.
         *
         * Place the point in the left or right nodes accordingly.
         *
         * If the comparison is not affirmatively left or right, then it could
         * be that we're considering literally the same point, in which case
         * the size shouldn't increase, or that we're considering a point
         * which lies on the same partition line, which would need to be added
         * to the BST and increase the size accordingly.
         */

        // Handle Nodes which should be inserted to the left
        if (cmp < 0 && evenLevel) {
            coords[2] = node.getPoint2D().x(); // lessen x_max
            node.setLeftNode(insert(node.getLeftNode(), p, !evenLevel, coords));
        }

        // Handle Nodes which should be inserted to the bottom
        else if (cmp < 0 && !evenLevel) {
            coords[3] = node.getPoint2D().y(); // lessen y_max
            node.setLeftNode(insert(node.getLeftNode(), p, !evenLevel, coords));
        }

        // Handle Nodes which should be inserted to the right
        else if (cmp > 0 && evenLevel) {
            coords[0] = node.getPoint2D().x(); // increase x_min
            node.setRightNode(insert(node.getRightNode(), p, !evenLevel, coords));
        }

        // Handle Nodes which should be inserted to the top
        else if (cmp > 0 && !evenLevel) {
            coords[1] = node.getPoint2D().y(); // increase y_min
            node.setRightNode(insert(node.getRightNode(), p, !evenLevel, coords));
        }

        /**
         * Handle Nodes which lie on the same partition line,
         * but aren't the same point.
         *
         * As per the checklist, these "ties" are resolved in favor of the
         * right subtree.
         *
         * It is assumed that the RectHV to be created cannot be shrunk
         * at all, and so none of coords[] values are updated here.
         */
        else if (!node.getPoint2D().equals(p))
            node.setRightNode(insert(node.getRightNode(), p, !evenLevel, coords));

        /**
         * Do nothing for a point which is already in the BST.
         * This is because the BST contains a "set" of points.
         * Hence, duplicates are silently dropped, rather than
         * being added.
         */

        return node;
    }

    // does the set contain point point2D?
    public boolean contains(Point2D point2D) {
        if (point2D == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, point2D, true);
    }

    private boolean contains(Node node, Point2D p, boolean evenLevel) {

        // Handle reaching the end of the search
        if (node == null) return false;

        // Check whether the search point matches the current Node's point
        if (node.getPoint2D().equals(p)) return true;

        double cmp = comparePoints(p, node, evenLevel);

        // Traverse the left path when necessary
        if (cmp < 0) return contains(node.getLeftNode(), p, !evenLevel);

            // Traverse the right path when necessary, and as tie-breaker
        else return contains(node.getRightNode(), p, !evenLevel);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, true);
    }

    private void draw(Node node, boolean evenLevel) {
        if (node == null) return;

        // Traverse the left Nodes
        draw(node.getLeftNode(), !evenLevel);

        // Draw the current Node
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.getPoint2D().draw();

        // Draw the partition line
        StdDraw.setPenRadius();
        if (evenLevel) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.getPoint2D().x(), node.getRectHV().ymin(), node.getPoint2D().x(), node.getRectHV().ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.getRectHV().xmin(), node.getPoint2D().y(), node.getRectHV().xmax(), node.getPoint2D().y());
        }

        // Traverse the right Nodes
        draw(node.getRightNode(), !evenLevel);
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rectHV) {
        if (rectHV == null) {
            throw new IllegalArgumentException();
        }
        Stack<Point2D> points = new Stack<>();

        // Handle KdTree without a root node yet
        if (root == null) return points;

        Stack<Node> nodes = new Stack<>();
        nodes.push(root);
        while (!nodes.isEmpty()) {

            // Examine the next Node
            Node tmp = nodes.pop();

            // Add contained points to our points stack
            if (rectHV.contains(tmp.getPoint2D())) points.push(tmp.getPoint2D());

            /**
             * Add Nodes containing promising rectangles to our nodes stack.
             *
             * Note that, since we don't push Nodes onto the stack unless
             * their rectangles intersect with the given RectHV, we achieve
             * pruning as we traverse the BST.
             */
            if (tmp.getLeftNode() != null && rectHV.intersects(tmp.getLeftNode().getRectHV())) {
                nodes.push(tmp.getLeftNode());
            }
            if (tmp.getRightNode() != null && rectHV.intersects(tmp.getRightNode().getRectHV())) {
                nodes.push(tmp.getRightNode());
            }
        }
        return points;
    }

    // a nearest neighbor in the set to point point2D; null if the set is empty
    public Point2D nearest(Point2D point2D) {
        if (point2D == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            return null;
        }
        return nearest(root, point2D, root.getPoint2D(), true);
    }

    private Point2D nearest(Node node, Point2D p, Point2D champion,
                            boolean evenLevel) {

        // Handle reaching the end of the tree
        if (node == null) return champion;

        // Handle the given point exactly overlapping a point in the BST
        if (node.getPoint2D().equals(p)) return p;

        // Determine if the current Node's point beats the existing champion
        if (node.getPoint2D().distanceSquaredTo(p) < champion.distanceSquaredTo(p))
            champion = node.getPoint2D();

        /**
         * Calculate the distance from the search point to the current
         * Node's partition line.
         *
         * Primarily, the sign of this calculation is useful in determining
         * which side of the Node to traverse next.
         *
         * Additionally, the magnitude to toPartitionLine is useful for pruning.
         *
         * Specifically, if we find a champion whose distance is shorter than
         * to a previous partition line, then we know we don't have to check any
         * of the points on the other side of that partition line, because none
         * can be closer.
         */
        double toPartitionLine = comparePoints(p, node, evenLevel);

        /**
         * Handle the search point being to the left of or below
         * the current Node's point.
         */
        if (toPartitionLine < 0) {
            champion = nearest(node.getLeftNode(), p, champion, !evenLevel);

            // Since champion may have changed, recalculate distance
            if (champion.distanceSquaredTo(p) >=
                    toPartitionLine * toPartitionLine) {
                champion = nearest(node.getRightNode(), p, champion, !evenLevel);
            }
        }

        /**
         * Handle the search point being to the right of or above
         * the current Node's point.
         *
         * Note that, since insert() above breaks point comparison ties
         * by placing the inserted point on the right branch of the current
         * Node, traversal must also break ties by going to the right branch
         * of the current Node (i.e. to the right or top, depending on
         * the level of the current Node).
         */
        else {
            champion = nearest(node.getRightNode(), p, champion, !evenLevel);

            // Since champion may have changed, recalculate distance
            if (champion.distanceSquaredTo(p) >=
                    toPartitionLine * toPartitionLine) {
                champion = nearest(node.getLeftNode(), p, champion, !evenLevel);
            }
        }

        return champion;
    }

    private double comparePoints(Point2D point2D, Node node, boolean evenLevel) {
        if (evenLevel) {
            return point2D.x() - node.getPoint2D().x();
        } else return point2D.y() - node.getPoint2D().y();
    }

    /**
     * The data structure from which a KdTree is created.
     */
    private class Node {

        // the point
        private Point2D point2D;

        // the axis-aligned rectangle corresponding to this node
        private RectHV rectHV;

        // the left/bottom subtree
        private Node leftNode;

        // the right/top subtree
        private Node rightNode;

        public Node(Point2D point2D, double[] coords) {
            this.point2D = point2D;
            rectHV = new RectHV(coords[0], coords[1], coords[2], coords[3]);
        }

        public Point2D getPoint2D() {
            return point2D;
        }

        public RectHV getRectHV() {
            return rectHV;
        }

        public Node getLeftNode() {
            return leftNode;
        }

        public void setLeftNode(Node leftNode) {
            this.leftNode = leftNode;
        }

        public Node getRightNode() {
            return rightNode;
        }

        public void setRightNode(Node rightNode) {
            this.rightNode = rightNode;
        }
    }
}
