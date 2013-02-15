package gnu.trove.benchmark;

import com.logicartisan.thrifty.benchmark.Benchmark;
import gnu.trove.map.hash.THashMap;
import gnu.trove.procedure.TObjectProcedure;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@SuppressWarnings( "UnusedDeclaration" )
public class Int_TroveHashMap extends Benchmark {
	private THashMap<Integer,Integer> map;
	private Totaler totaler = new Totaler();

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new THashMap<Integer,Integer>( Constants.INT_OBJECTS.length );
		}
		else {
			map = new THashMap<Integer,Integer>();
			for( int j = 0; j < 2; j++ ) {
				for( Integer i : Constants.INT_OBJECTS ) {
					map.put( i, i );
				}
			}
		}

	}

	@Override
	public void tearDown( Method completed_method ) {
		map = null;
		totaler.reset();
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


	public void testForEach() {
		for( int i = 0; i < 5; i++ ) {
			map.forEachKey( totaler );
		}
	}



	class Totaler implements TObjectProcedure<Integer> {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( Integer value ) {
			total += value.intValue();
			return true;
		}
	}
}
