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
public class Int_JavaHashMap extends Benchmark {
	private Map<Integer,Integer> map;

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new HashMap<Integer,Integer>( Constants.INT_OBJECTS.length );
		}
		else {
			map = new HashMap<Integer,Integer>();
			for( int j = 0; j < 2; j++ ) {
				for( Integer i : Constants.INT_OBJECTS ) {
					map.put( i.intValue(), i );
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
			for( Integer i : Constants.INT_OBJECTS ) {
				map.get( i );
			}
		}
	}

	public void testPut() {
		for( Integer i : Constants.INT_OBJECTS ) {
			map.put( i, i );
		}
	}

	public void testRemove() {
		boolean remove = true;
		for( Integer i : Constants.INT_OBJECTS ) {
			if ( remove ) {
				map.remove( i );
			}

			remove = !remove;
		}
	}

	public void testIteration() {
		int total = 0;

		for( int i = 0; i < 5; i++ ) {
			Iterator<Integer> iterator = map.keySet().iterator();

			while( iterator.hasNext() ) {
				total += iterator.next().intValue();
			}
		}

		value_slot.set( total );
	}
}
