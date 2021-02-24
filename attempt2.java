import java.util.*;


 class Node<T>
  {
    public T value;
    public Node<T> next, prev;

    public Node(T _value)
    {
      value = _value;
      next = null;
      prev = null;
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
  
public class OrderedList<T>
  {
    public Node<T> head, tail;
    private boolean _ascending;

    public OrderedList(boolean asc)
    {
      head = null;
      tail = null;
      _ascending = asc;
    }

  public int compare(T v1, T v2)
    {
        int var = String.valueOf(v1).trim().compareTo(String.valueOf(v2).trim());

        if (var < 0) {
            return -1;
        }

        if (var > 0) {
            return 1;
        }

        return 0;
    }

    public void add(T value)
    {
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

    public Node<T> find(T val)
    {
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

    public void delete(T val)
    {
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

    public void clear(boolean asc)
    {
        _ascending = asc;
        setHead(null);
        setTail(null);
    }

    public int count()
    {
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
        while(node != null)
        {
            r.add(node);
            node = node.next;
        }
        return r;
    }
  }
