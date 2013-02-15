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
public class Byte_JavaHashMap extends Benchmark {
	private Map<Byte,Byte> map;

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new HashMap<Byte,Byte>( Constants.BYTE_OBJECTS.length );
		}
		else {
			map = new HashMap<Byte,Byte>();
			for( int j = 0; j < 2; j++ ) {
				for( Byte i : Constants.BYTE_OBJECTS ) {
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
			for( Byte i : Constants.BYTE_OBJECTS ) {
				map.get( i );
			}
		}
	}

	public void testPut() {
		for( Byte i : Constants.BYTE_OBJECTS ) {
			map.put( i, i );
		}
	}

	public void testRemove() {
		boolean remove = true;
		for( Byte i : Constants.BYTE_OBJECTS ) {
			if ( remove ) {
				map.remove( i );
			}

			remove = !remove;
		}
	}

	public void testIteration() {
		int total = 0;

		for( int i = 0; i < 5; i++ ) {
			Iterator<Byte> iterator = map.keySet().iterator();

			//noinspection WhileLoopReplaceableByForEach
			while( iterator.hasNext() ) {
				total += iterator.next().intValue();
			}
		}

		value_slot.set( total );
	}
}
