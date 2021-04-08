package ohm.softa.a04;

import java.lang.reflect.InvocationTargetException;

public interface SimpleList<T> extends Iterable<T> {
	/**
	 * Add a given object to the back of the list.
	 */
	void add(T item);

	/**
	 * @return current size of the list
	 */
	int size();

	/**
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 */
	SimpleList<T> filter(SimpleFilter<T> filter);

	/* 4.Add a new method addDefault to the SimpleList interface;
	the purpose is to add a default instance (using the default constructor) to the list
	Hint: this method aims at the instantiation problem of generics.*/
	// Bsp. T inst = new T(); -> geht nicht!!! Man muss die Typinfo zur Laufzeit bekommen
	// addDefault wird später so aufgerufen: Instanz.addDefault(Klasse.class)
	// Klasse.class wegen Eingabeparameter vom Typ Class<T>
	default void addDefault(Class<T> item) {
		try {
			this.add(item.getDeclaredConstructor().newInstance());
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
