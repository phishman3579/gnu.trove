package gnu.trove.benchmark;

import com.logicartisan.thrifty.benchmark.Benchmark;
import gnu.trove.iterator.TObjectIntIterator;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.procedure.TIntProcedure;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@SuppressWarnings( "UnusedDeclaration" )
public class Object_TroveObjectHashMap extends Benchmark {
	private TObjectIntMap<String> map;

	private ITotaler btotaler = new ITotaler();

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new TObjectIntHashMap<String>( Constants.STRING_OBJECTS.length );
		}
		else {
			map = new TObjectIntHashMap<String>();
			for( int j = 0; j < 2; j++ ) {
				int index = 0;
				for( String i : Constants.STRING_OBJECTS ) {
					map.put( i, index++ );
				}
			}
		}

	}

	@Override
	public void tearDown( Method completed_method ) {
		map = null;
		btotaler.reset();
	}


	public void testGet() {
		for( int j = 0; j < 5; j++ ) {
			for( String i : Constants.STRING_OBJECTS ) {
				map.get( i );
			}
		}
	}


	public void testPut() {
		int index = 0;
		for( String i : Constants.STRING_OBJECTS ) {
			map.put( i, index++ );
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
			TObjectIntIterator<String> iterator = map.iterator();
			while( iterator.hasNext() ) {
				iterator.advance();
				total += iterator.key().length();
			}
		}

		value_slot.set( total );
	}


	public void testForEachKey() {
		for( int i = 0; i < 5; i++ ) {
			map.forEachValue( btotaler );
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
