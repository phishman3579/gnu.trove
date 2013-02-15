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
public class Byte_TroveHashMap extends Benchmark {
	private THashMap<Byte,Byte> map;
	private Totaler totaler = new Totaler();

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new THashMap<Byte,Byte>( Constants.BYTE_OBJECTS.length );
		}
		else {
			map = new THashMap<Byte,Byte>();
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
		totaler.reset();
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


	public void testForEach() {
		for( int i = 0; i < 5; i++ ) {
			map.forEachKey( totaler );
		}
	}



	class Totaler implements TObjectProcedure<Byte> {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( Byte value ) {
			total += value.intValue();
			return true;
		}
	}
}
