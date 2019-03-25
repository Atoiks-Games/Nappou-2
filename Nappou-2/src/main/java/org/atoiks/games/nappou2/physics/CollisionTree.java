/**
 *  Nappou-2
 *  Copyright (C) 2017-2019  Atoiks-Games <atoiks-games@outlook.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.atoiks.games.nappou2.physics;

import java.util.Set;
import java.util.List;
import java.util.HashMap;

public class CollisionTree<K> {

    private class Node {

        private final CollisionBox box;

        private Node parent;
        private Node lhs;
        private Node rhs;

        private K key;

        public Node(final CollisionBox box) {
            this.box = box;
        }

        public boolean isLeaf() {
            return this.lhs == null;
        }
    }

    private final HashMap<K, Node> table = new HashMap<>();

    private Node root;

    public void clear() {
        this.root = null;
        this.table.clear();
    }

    public int size() {
        return table.size();
    }

    public boolean isEmpty() {
        return table.isEmpty();
    }

    public Set<K> keySet() {
        return table.keySet();
    }

    public CollisionBox getCopyOf(K key) {
        final Node n = table.get(key);
        return n == null ? null : new CollisionBox(n.box);
    }

    public void add(final K key, final CollisionBox s) {
        final Node n = table.get(key);
        if (n == null) {
            if (s == null) return;

            final Node item = new Node(s);
            item.key = key;
            table.put(key, item);
            add(item);
        } else if (s == null) {
            table.remove(key);
            remove(n);
        } else {
            update(n, s);
        }
    }

    public void update(final K key, final CollisionBox s) {
        final Node n = table.get(key);
        if (n != null) {
            if (s == null) {
                table.remove(key);
                remove(n);
            } else {
                update(n, s);
            }
        }
    }

    public void remove(final K key) {
        final Node n = table.get(key);
        if (n != null) {
            table.remove(key);
            remove(n);
        }
    }

    public void getCollidingKeys(final List<K> list, final CollisionBox s) {
        if (s == null) return;
        getCollidingKeys(root, list, s);
    }

    private void getCollidingKeys(final Node item, final List<K> list, final CollisionBox s) {
        if (item == null) return;

        if (!item.box.collidesWith(s)) return;
        if (item.key != null) list.add(item.key);
        getCollidingKeys(item.lhs, list, s);
        getCollidingKeys(item.rhs, list, s);
    }

    private void update(final Node item, final CollisionBox s) {
        if (!item.box.contains(s)) {
            this.remove(item);
            item.box.readFrom(s);
            this.add(item);
        }
    }

    private void remove(final Node item) {
        if (root == null) {
            // Nothing to remove
            return;
        }

        if (item == root) {
            root = null;
            return;
        }

        final Node parent = item.parent;
        final Node grandParent = parent.parent;
        final Node sibling = parent.lhs == item ? parent.rhs : parent.lhs;
        if (grandParent == null) {
            // Make sibling root
            root = sibling;
            sibling.parent = null;
        } else {
            // Pull sibling up
            if (grandParent.lhs == parent) {
                grandParent.lhs = sibling;
            } else {
                grandParent.rhs = sibling;
            }
            sibling.parent = grandParent;

            // Reduce bounding box size
            Node node = sibling;
            while (node.parent != null) {
                final Node base = node.parent;
                if (node.lhs != null) {
                    base.box.readFrom(node.lhs.box);
                    base.box.union(node.rhs.box);
                }
                node = base;
            }
        }
    }

    private void add(final Node item) {
        if (root == null) {
            // Insert as root
            root = item;
            return;
        }

        final CollisionBox s = item.box;

        final CollisionBox top = new CollisionBox();
        final CollisionBox lhs = new CollisionBox();
        final CollisionBox rhs = new CollisionBox();

        Node node = root;
        while (!node.isLeaf()) {
            top.readFrom(node.box);
            lhs.readFrom(node.lhs.box);
            rhs.readFrom(node.rhs.box);

            top.union(s);
            lhs.union(s);
            rhs.union(s);

            final float topArea = top.area();
            final float lhsArea = lhs.area();
            final float rhsArea = rhs.area();

            if (topArea < lhsArea && topArea < rhsArea) {
                // Create a new root would result in smallest box
                break;
            }

            if (lhsArea < rhsArea) {
                // Insert on lhs
                node = node.lhs;
            } else {
                // Insert on rhs
                node = node.rhs;
            }
        }

        // Compute new parent node's box size
        final CollisionBox outer = new CollisionBox(s);
        outer.union(node.box);

        // Create a new parent node
        final Node oldParent = node.parent;
        final Node newParent = new Node(outer);
        newParent.parent = oldParent;

        newParent.lhs = node;
        newParent.rhs = item;
        node.parent = newParent;
        item.parent = newParent;

        if (oldParent == null) {
            // node used to be the root node
            this.root = newParent;
        } else if (oldParent.lhs == node) {
            oldParent.lhs = newParent;
        } else {
            oldParent.rhs = newParent;
        }

        // Propagate bounding box size change to parent nodes
        while (node.parent != null) {
            final Node parent = node.parent;
            parent.box.union(node.box);
            node = parent;
        }
    }
}
