package gnu.trove.benchmark;

import com.logicartisan.thrifty.benchmark.Benchmark;
import gnu.trove.iterator.TByteByteIterator;
import gnu.trove.map.TByteByteMap;
import gnu.trove.map.hash.TByteByteHashMap;
import gnu.trove.procedure.TByteProcedure;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@SuppressWarnings( "UnusedDeclaration" )
public class Byte_TroveByteHashMap extends Benchmark {
	private TByteByteMap map;

	private BTotaler btotaler = new BTotaler();

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new TByteByteHashMap( Constants.BYTES.length );
		}
		else {
			map = new TByteByteHashMap();
			for( int j = 0; j < 2; j++ ) {
				for( byte i : Constants.BYTES ) {
					map.put( i, i );
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
			for( byte i : Constants.BYTES ) {
				map.get( i );
			}
		}
	}


	public void testPut() {
		for( byte i : Constants.BYTES ) {
			map.put( i, i );
		}
	}

	public void testRemove() {
		boolean remove = true;
		for( byte i : Constants.BYTES ) {
			if ( remove ) {
				map.remove( i );
			}

			remove = !remove;
		}
	}

	public void testIteration() {
		int total = 0;

		for( int i = 0; i < 5; i++ ) {
			TByteByteIterator iterator = map.iterator();
			while( iterator.hasNext() ) {
				iterator.advance();
				total += iterator.key();
			}
		}

		value_slot.set( total );
	}


	public void testForEachKey() {
		for( int i = 0; i < 5; i++ ) {
			map.forEachValue( btotaler );
		}
	}


	class BTotaler implements TByteProcedure {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( byte a ) {
			total += a;
			return true;
		}
	}
}
