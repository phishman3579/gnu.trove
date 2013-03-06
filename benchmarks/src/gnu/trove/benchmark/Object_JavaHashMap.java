package gnu.trove.benchmark;

import com.logicartisan.thrifty.benchmark.Benchmark;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@SuppressWarnings( "UnusedDeclaration" )
public class Object_JavaHashMap extends Benchmark {
	private Map<String,String> map;

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new HashMap<String,String>( Constants.BYTE_OBJECTS.length );
		}
		else {
			map = new HashMap<String,String>();
			for( int j = 0; j < 2; j++ ) {
				for( String i : Constants.STRING_OBJECTS ) {
					map.put( i, i );
				}
			}
		}

	}

	@Override
	public void tearDown( Method completed_method ) {
		map = null;
	}


	public void testGet() {
		for( int j = 0; j < 5; j++ ) {
			for( String i : Constants.STRING_OBJECTS ) {
				map.get( i );
			}
		}
	}

	public void testPut() {
		for( String i : Constants.STRING_OBJECTS ) {
			map.put( i, i );
		}
	}

	public void testRemove() {
		boolean remove = true;
		for( String i : Constants.STRING_OBJECTS ) {
			if ( remove ) {
				map.remove( i );
			}

			remove = !remove;
		}
	}

	public void testIteration() {
		int total = 0;

		for( int i = 0; i < 5; i++ ) {
			Iterator<String> iterator = map.keySet().iterator();

			//noinspection WhileLoopReplaceableByForEach
			while( iterator.hasNext() ) {
				total += iterator.next().length();
			}
		}

		value_slot.set( total );
	}
}
