package gnu.trove.benchmark;

import com.logicartisan.thrifty.benchmark.Benchmark;
import gnu.trove.iterator.TIntIntIterator;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.procedure.TIntProcedure;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@SuppressWarnings( "UnusedDeclaration" )
public class Int_TroveIntHashMap extends Benchmark {
	private TIntIntMap map;

	private ITotaler itotaler = new ITotaler();

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new TIntIntHashMap( Constants.INTS.length );
		}
		else {
			map = new TIntIntHashMap();
			for( int j = 0; j < 2; j++ ) {
				for( int i : Constants.INTS ) {
					map.put( i, i );
				}
			}
		}

	}

	@Override
	public void tearDown( Method completed_method ) {
		map = null;
		itotaler.reset();
	}


	public void testGet() {
		for( int j = 0; j < 5; j++ ) {
			for( int i : Constants.INTS ) {
				map.get( i );
			}
		}
	}


	public void testPut() {
		for( int i : Constants.INTS ) {
			map.put( i, i );
		}
	}

	public void testRemove() {
		boolean remove = true;
		for( int i : Constants.INTS ) {
			if ( remove ) {
				map.remove( i );
			}

			remove = !remove;
		}
	}

	public void testIteration() {
		int total = 0;

		for( int i = 0; i < 5; i++ ) {
			TIntIntIterator iterator = map.iterator();
			while( iterator.hasNext() ) {
				iterator.advance();
				total += iterator.key();
			}
		}

		value_slot.set( total );
	}


	public void testForEachKey() {
		for( int i = 0; i < 5; i++ ) {
			map.forEachValue( itotaler );
		}
	}


	class ITotaler implements TIntProcedure {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( int a ) {
			total += a;
			return true;
		}
	}
}
