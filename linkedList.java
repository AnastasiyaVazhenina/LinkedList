import java.util.NoSuchElementException;

public class linkedList<T> {

    private static class Node<T> {
        T value;
        linkedList.Node<T> next;
        linkedList.Node<T> prev;

        Node(linkedList.Node<T> prev, T element, linkedList.Node<T> next) {
            this.value = element;
            this.next = next;
            this.prev = prev;
        }
    }

    transient int size;
    transient linkedList.Node<T> first;
    transient linkedList.Node<T> last;

    public linkedList() {
        this.size = 0;
    }

    private void linkFirst(T rhs) {
        linkedList.Node<T> f = this.first;
        linkedList.Node<T> newNode = new linkedList.Node<T>(null, rhs, f);
        this.first = newNode;
        if (f == null) {
            this.last = newNode;
        } else {
            f.prev = newNode;
        }
        ++this.size;
    }

    private void linkLast(T rhs) {
        linkedList.Node<T> l = this.last;
        linkedList.Node<T> newNode = new linkedList.Node<T>(l, rhs, null);
        this.last = newNode;
        if (l == null) {
            this.first = newNode;
        } else {
            l.next = newNode;
        }
        ++this.size;
    }

    private void linkBefore(T rhs, linkedList.Node<T> node) {
        linkedList.Node<T> prev = node.prev;
        linkedList.Node<T> newNode = new linkedList.Node<T>(prev, rhs, node);
        node.prev = newNode;
        if (prev == null) {
            this.first = newNode;
        } else {
            prev.next = newNode;
        }
        ++this.size;
    }

    private T unlinkFirst(linkedList.Node<T> f) {
        T element = f.value;
        linkedList.Node<T> nodeNext = f.next;
        f.next = null;
        f.value = null;
        this.first = nodeNext;
        if (nodeNext == null) {
            this.last = null;
        } else {
            nodeNext.prev = null;
        }
        --this.size;
        return element;
    }

    private T unlinkLast(linkedList.Node<T> l) {
        T element = l.value;
        linkedList.Node<T> nodePrev = l.prev;
        l.prev = null;
        l.value = null;
        this.last = nodePrev;
        if (nodePrev == null) {
            this.first = null;
        } else {
            nodePrev.next = null;
        }
        --this.size;
        return element;
    }

    private T unlink(linkedList.Node<T> node) {
        T element = node.value;
        linkedList.Node<T> nodePrev = node.prev;
        linkedList.Node<T> nodeNext = node.next;
        if (nodePrev == null) {
            this.first = null;
        } else {
            nodePrev.next = nodeNext;
            node.prev = null;
        }
        if (nodeNext == null) {
            this.last = null;
        } else {
            nodeNext.prev = nodePrev;
            node.next = null;
        }
        node.value = null;
        --this.size;
        return element;
    }

    public T getFirst() {
        linkedList.Node<T> f = this.first;
        if (f == null) {
            throw new NoSuchElementException();
        } else {
            return f.value;
        }
    }

    public T getLast() {
        linkedList.Node<T> l = this.last;
        if (l == null) {
            throw new NoSuchElementException();
        } else {
            return l.value;
        }
    }

    public T removeFirst() {
        linkedList.Node<T> f = this.first;
        if (f == null) {
            throw new NoSuchElementException();
        } else {
            return this.unlinkFirst(f);
        }
    }

    public T removeLast() {
        linkedList.Node<T> l = this.last;
        if (l == null) {
            throw new NoSuchElementException();
        } else {
            return this.unlinkLast(l);
        }
    }

    public void addFirst(T rhs) {
        this.linkFirst(rhs);
    }

    public void addLast(T rhs) {
        this.linkLast(rhs);
    }

    public int getSize() {
        return this.size;
    }

    public boolean add(T rhs) {
        this.linkFirst(rhs);
        return true;
    }

    public boolean remove(Object object) {
        linkedList.Node<T> node;
        if (object == null) {
            for(node = this.first; node != null; node = this.last) {
                if (node.value == null) {
                    this.unlink(node);
                    return true;
                }
            }
        } else {
            for(node = this.first; node != null; node = this.last) {
                if (object.equals(node.value)) {
                    this.unlink(node);
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        linkedList.Node<T> nodeNext;
        for(linkedList.Node<T> i = this.first; i != null; i = this.last) {
            nodeNext = i.next;
            i.value = null;
            i.prev = null;
            i.next = null;
        }
        this.first = this.last = null;
        this.size = 0;
    }

    private String outOfBoundsMessage(int index) {
        return "Index: " + index + ", Size: " + this.size;
    }

    private boolean isElementIndex(int index) {
        return index >= 0 && index < this.size;
    }

    private void checkElementIndex(int index) {
        if (!this.isElementIndex(index))
            throw new IndexOutOfBoundsException(this.outOfBoundsMessage(index));
    }

    private boolean isPositionIndex(int index) {
        return index >= 0 && index <= this.size;
    }

    private void checkPossition(int index) {
        if (!this.isPositionIndex(index))
            throw new IndexOutOfBoundsException(this.outOfBoundsMessage(index));
    }

    private linkedList.Node<T> node(int index) {
        linkedList.Node<T> node;
        if (index < this.size / 2) {
            node = this.first;
            for (int i = 0; i < index; ++i) {
                node = node.next;
            }
            return node;
        } else {
            node = this.last;
            for (int i = this.size - 1; i > index ; --i) {
                node = node.prev;
            }
            return node;
        }
    }

    public T get(int index) {
        this.checkElementIndex(index);
        return this.node(index).value;
    }

    public T set(int index, T element) {
        this.checkElementIndex(index);
        linkedList.Node<T> node = this.node(index);
        T oldValue = node.value;
        node.value = element;
        return oldValue;
    }

    public void add(int index, T element) {
        this.checkPossition(index);
        if (index == this.size) {
            this.linkLast(element);
        } else {
            this.linkBefore(element, this.node(index));
        }
    }
}
