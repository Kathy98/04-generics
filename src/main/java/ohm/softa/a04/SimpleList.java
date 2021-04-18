package ohm.softa.a04;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

// Iterable verwendet das generische Objekt von SimpleList (bindet das generische Obj. T)
public interface SimpleList<T> extends Iterable<T> {
	/**
	 * Add a given object to the back of the list.
	 */
	void add(T item);

	/**
	 * @return current size of the list
	 */
	int size();

	/* 1.4 Add a new method addDefault to the SimpleList interface;
	the purpose is to add a default instance (using the default constructor) to the list
	Hint: this method aims at the instantiation problem of generics.*/
	// Bsp. T inst = new T(); -> geht nicht!!! Man muss die Typinfo zur Laufzeit bekommen
	// addDefault wird später so aufgerufen: Instanz.addDefault(Klasse.class)
	// Klasse.class (Integer.class) wegen Eingabeparameter vom Typ Class<T>
	// Bekomme mit Class<T> die Klasseninfo. Klasseninfo T ist zur Laufzeit  nicht verfügbar, deswegen muss ich die hier reinschmeißen
	@SuppressWarnings("unchecked")
	default void addDefault(Class<T> item) {
		try {
			this.add(item.getDeclaredConstructor().newInstance());
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 2.1
	 * Generate a new list using the given filter instance.
	 * @return a new, filtered list
	 * SimpleList<T> filter(SimpleFilter<T> filter);
	 */
	@SuppressWarnings("unchecked")
	default SimpleList<T> filter(SimpleFilter<T> filter){
		SimpleList<T> result = new SimpleListImpl<>();
		/*SimpleList<T> result;
		try {
			result = (SimpleList<T>) getClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			result = new SimpleListImpl<>();
		}*/

		for(T o : this){
			// Include-Methode: Ist Wert enthalten? Ja - true / Nein - false
			if(filter.include(o)){
				result.add(o);
			}
		}
		return result;
	}

	/**
	 * 2.2
	 */
	// Siehe auch: https://mkyong.com/java8/java-8-function-examples (4. List -> List)
	@SuppressWarnings("unchecked")
	default <R> SimpleList<R> map(Function<T,R> transform){
		SimpleList<R> result;
		try {
			result = (SimpleList<R>) getClass().getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
			result = new SimpleListImpl<>();
		}

		for (T t : this) {
			// method "apply" of the functional interface Function applies this function to the given argument.
			result.add(transform.apply(t));
		}
		return result;
	}
}
