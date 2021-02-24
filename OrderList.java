import java.util.*;

class Node<T> {
    public T value;
    public Node next;
    public Node prev;

    public Node(T _value) {
        this.value = _value;
        this.next = null;
        this.prev = null;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }
}

public class OrderedList<T> {
    public Node<T> head, tail;
    private boolean _ascending;

    public OrderedList(boolean asc) {
        head = null;
        tail = null;
        _ascending = asc;
    }

    public int compare(T v1, T v2) {

        int var = String.valueOf(v1).trim().compareTo(String.valueOf(v2).trim());

        if (var < 0) {
            return -1;
        }

        if (var > 0) {
            return 1;
        }

        return 0;
    }

    public void add(T... arr) {
        Arrays.stream(arr).forEach(element -> add(element));
    }

    public void add(T value) {

        Node<T> _nodeToInsert = new Node<>(value);

        if (this.getHead() == null && this.getTail() == null) {
            this.setHead(_nodeToInsert);
            this.setTail(_nodeToInsert);
            return;
        }

        int compareValue = 0;

        if (is_ascending()) {
            compareValue = -1;
        } else {
            compareValue = 1;
        }

        if (compare((T) getHead().getValue(), value) != compareValue) {
            _nodeToInsert.setNext(getHead());
            _nodeToInsert.setPrev(null);
            getHead().setPrev(_nodeToInsert);
            this.setHead(_nodeToInsert);
            return;
        }

        Node index = this.getHead().getNext();

        while (index != null) {
            if (compare((T) index.getValue(), value) != compareValue) {

                _nodeToInsert.setNext(index);
                _nodeToInsert.setPrev(index.getPrev());
                index.getPrev().setNext(_nodeToInsert);
                index.setPrev(_nodeToInsert);
                return;
            }
            index = index.next;
        }

        _nodeToInsert.setNext(null);
        _nodeToInsert.setPrev(getTail());
        getTail().setNext(_nodeToInsert);
        this.setTail(_nodeToInsert);
    }

/*    public Node<T> find(T val) {
        Node node = getHead();
        while (node != null) {
            if (this.compare((T) node.getValue(), val) == 0) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }*/

    public Node<T> find(T val) {
        int compareValue = 0;

        if (is_ascending()) {
            compareValue = -1;
        } else {
            compareValue = 1;
        }

        Node node = getHead();

        while (node != null) {
            if (this.compare((T) node.getValue(), val) == 0) {
                return node;
            }
            if (this.compare((T) node.getValue(), val) == -compareValue) {
                return null;
            }
            node = node.getNext();
        }
        return null;
    }

    public void delete(T val) {
        if (this.getHead() == null) {
            return;
        }

        if (this.compare((T) getHead().getValue(), val) == 0) {

            this.setHead(this.getHead().getNext());

            if (this.getHead() == null) {
                this.setTail(null);
            } else {
                this.getHead().setPrev(null);
            }
            return;
        }
        Node<T> nodeDelete = find(val);

        if (nodeDelete == null) {
            return;
        }
        nodeDelete.getPrev().setNext(nodeDelete.getNext());
        if (nodeDelete.getNext() == null) {
            this.setTail(nodeDelete.getPrev());
        } else {
            nodeDelete.getNext().setPrev(nodeDelete.getPrev());
        }
    }

    public void clear(boolean asc) {
        _ascending = asc;
        setHead(null);
        setTail(null);
    }

    public int count() {
        int count = 0;
        Node node = getHead();
        while (node != null) {
            count++;
            node = node.getNext();
        }
        return count;
    }

    ArrayList<Node<T>> getAll() // выдать все элементы упорядоченного
    // списка в виде стандартного списка
    {
        ArrayList<Node<T>> r = new ArrayList<Node<T>>();
        Node<T> node = head;
        while (node != null) {
            r.add(node);
            node = node.next;
        }
        return r;
    }

    public ArrayList<T> getAllTElement()
    {
        ArrayList<T> r = new ArrayList<T>();
        getAll().stream().forEach(node -> r.add((T) node.getValue()));
        return r;
    }

    public Node<T> getHead() {
        return head;
    }

    public void setHead(Node<T> head) {
        this.head = head;
    }

    public Node<T> getTail() {
        return tail;
    }

    public void setTail(Node<T> tail) {
        this.tail = tail;
    }

    public boolean is_ascending() {
        return _ascending;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("OrderList = ");
        Node node = this.getHead();
        while (node != null) {
            builder.append((T) node.getValue())
                    .append("  ");

            node = node.getNext();
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderedList that = (OrderedList) o;

        Node nodeThis = this.getHead();
        Node nodeThat = that.getHead();

        if (nodeThis == null && nodeThat == null) {
            return true;
        }

        if (nodeThis == null ^ nodeThat == null) {
            return false;
        }

        if (compare((T) nodeThat.getValue(), (T) nodeThis.getValue()) != 0
                || compare((T) this.getTail().getValue(), (T) that.getTail().getValue()) != 0) {
            return false;
        }
        while (nodeThat != null && nodeThis != null) {
            if (compare((T) nodeThat.getValue(), (T) nodeThis.getValue()) != 0) {
                return false;
            }
            nodeThat = nodeThat.getNext();
            nodeThis = nodeThis.getNext();
        }

        if (nodeThat == null ^ nodeThis == null) {
            return false;
        }

        nodeThat = that.getTail();
        nodeThis = this.getTail();

        while (nodeThat != null && nodeThis != null) {
            if (compare((T) nodeThat.getValue(), (T) nodeThis.getValue()) != 0) {
                return false;
            }
            nodeThat = nodeThat.getPrev();
            nodeThis = nodeThis.getPrev();
        }
        if (nodeThat == null ^ nodeThis == null) {
            return false;
        }
        return true;
    }
}
