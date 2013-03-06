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
public class Object_TroveHashMap extends Benchmark {
	private THashMap<String,String> map;
	private Totaler totaler = new Totaler();

	private AtomicInteger value_slot = new AtomicInteger();


	@Override
	public void setUp( Method upcoming_method ) {
		if ( upcoming_method.getName().endsWith( "Put" ) ) {
			map = new THashMap<String,String>( Constants.STRING_OBJECTS.length );
		}
		else {
			map = new THashMap<String,String>();
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
		totaler.reset();
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


	public void testForEach() {
		for( int i = 0; i < 5; i++ ) {
			map.forEachKey( totaler );
		}
	}



	class Totaler implements TObjectProcedure<String> {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( String value ) {
			total += value.length();
			return true;
		}
	}
}
