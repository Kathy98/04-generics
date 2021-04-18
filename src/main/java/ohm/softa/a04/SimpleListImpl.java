package ohm.softa.a04;

import java.util.Iterator;

/**
 * @author Peter Kurfer
 * Created on 10/6/17.
 */
public class SimpleListImpl<T> implements SimpleList<T> {

	private ListElement<T> head;
	private int size;

	public SimpleListImpl() {
		head = null;
	}

	/**
	 * Add an object to the end of the list
	 * @param item item to add
	 */
	public void add(T item){
		/* special case empty list */
		if(head == null){
			head = new ListElement<T>(item);
		}else {
			/* any other list length */
			ListElement<T> current = head;
			while (current.getNext() != null){
				current = current.getNext();
			}
			current.setNext(new ListElement<T>(item));
		}
		size++;
	}

	/**
	 * @return size of the list
	 */
	public int size() {
		return size;
	}

	/**
	 * Get a new SimpleList instance with all items of this list which match the given filter
	 * @param filter SimpleFilter instance
	 * @return new SimpleList instance
	 */
	/* SimpleList<T> filter(SimpleFilter<T> filter){
		SimpleList<T> result = new SimpleListImpl<T>();

		for(T o : this){
			if(filter.include(o)){
				result.add(o);
			}
		}
		return result;
	}*/

	/**
	 * @inheritDoc
	 */
	// Wenn Iterator nicht <T>, dann könnts nen Fehler zur Laufzeit geben.
	// Funzt zwar aber müsste vilt casten -> Vorteil von generics geht verloren
	@Override
	public Iterator<T> iterator() {

		return new SimpleIterator();
	}

	/**
	 * Helper class which implements the Iterator interface
	 * Has to be non static because otherwise it could not access the head of the list
	 */
	// nicht static, weil man sonst nicht auf den Listenanfang (head) zugreifen könnte
	// wenn nicht statisch, würde ich das T von oben kriegen, würde ichs statisch machen, würde T nicht von oben kommen
	// hier würde es zur verschattung kommen nsollte ich SimpleIterator auch generisch machen. Könnte SimpleIterator<P> schreiben
	// Es wär nicht klar, welche Typisierung (sollte ich Iterator nicht generisch sein)

	private class SimpleIterator implements Iterator<T> {

		private ListElement<T> current = head;

		/**
		 * @inheritDoc
		 */
		@Override
		public boolean hasNext() {
			return current != null;
		}

		/**
		 * @inheritDoc
		 */
		@Override
		public T next() {
			// Wo bin ich gerade in der ddatenstruktur? man soll immer davor sein
			// Konzeptionell: return current.getItem();
			// current = current.next;
			// Will zuerst das element zurück geben vor dem ich steh und dann nen schritt weiter gehen
			T tmp = current.getItem();
			current = current.getNext();
			return tmp;
		}
	}

	/**
	 * Helper class for the linked list
	 * can be static because the ListElement does not need to access the SimpleList instance
	 */
	private static class ListElement<T> {
		// dieses (private T item) T hat nix mit dem SimpleListImpl T zu tun.
		// Wenn ListElement<P> wäre, müsste ich "private P item;" schreiben
		private T item;
		private ListElement<T> next;

		ListElement(T item) {
			this.item = item;
			this.next = null;
		}

		/**
		 * @return get object in the element
		 */
		public T getItem() {
			return item;
		}

		/**
		 * @return successor of the ListElement - may be NULL
		 */
		public ListElement<T> getNext() {
			return next;
		}

		/**
		 * Sets the successor of the ListElement
		 * @param next ListElement
		 */
		public void setNext(ListElement<T> next) {
			this.next = next;
		}
	}
}
