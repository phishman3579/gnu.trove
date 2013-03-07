package gnu.trove.benchmark;

import com.logicartisan.thrifty.benchmark.Benchmark;
import gnu.trove.map.TByteByteMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.hash.TByteByteHashMap;
import gnu.trove.map.hash.THashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.procedure.TByteProcedure;
import gnu.trove.procedure.TIntProcedure;
import gnu.trove.procedure.TObjectProcedure;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 */
@SuppressWarnings( "UnusedDeclaration" )
abstract class AbstractComparisonBase extends Benchmark {
	protected Map<Byte,Byte> byte_java_map;
	protected TByteByteMap byte_t_primitive_map;
	protected THashMap<Byte,Byte> byte_t_map;

	protected Map<Integer,Integer> int_java_map;
	protected TIntIntMap int_t_primitive_map;
	protected THashMap<Integer,Integer> int_t_map;

	protected Map<String,String> string_java_map;
	protected TObjectIntMap<String> string_t_primitive_map;
	protected THashMap<String,String> string_t_map;


	protected final AtomicInteger int_slot = new AtomicInteger();

	protected final BytePrimitiveTotaler byte_primitive_totaler =
		new BytePrimitiveTotaler();
	protected final ByteTotaler byte_totaler = new ByteTotaler();

	protected final IntPrimitiveTotaler int_primitive_totaler =
		new IntPrimitiveTotaler();
	protected final IntTotaler int_totaler = new IntTotaler();

	protected final StringTotaler string_totaler = new StringTotaler();


	private final boolean fill_maps;

	AbstractComparisonBase( boolean fill_maps ) {
		this.fill_maps = fill_maps;
	}

	@Override
	public void setUp( Method upcoming_method ) {
		String method_name = upcoming_method.getName();

		switch( method_name ) {
			// Bytes...
			case "testByte_JavaHashMap":
				byte_java_map = new HashMap<>();
				if ( fill_maps ) {
					for( Byte i : Constants.BYTE_OBJECTS ) {
						byte_java_map.put( i, i );
					}
				}
				break;
			case "testByte_TPrimitiveHashMap":
				byte_t_primitive_map = new TByteByteHashMap( Constants.BYTES.length );
				if ( fill_maps ) {
					for( byte i : Constants.BYTES ) {
						byte_t_primitive_map.put( i, i );
					}
				}
				break;
			case "testByte_THashMap":
				byte_t_map = new THashMap<>( Constants.BYTE_OBJECTS.length );
				if ( fill_maps ) {
					for( Byte i : Constants.BYTE_OBJECTS ) {
						byte_t_map.put( i, i );
					}
				}
				break;

			// Ints...
			case "testInt_JavaHashMap":
				int_java_map = new HashMap<>();
				if ( fill_maps ) {
					for( Integer i : Constants.INT_OBJECTS ) {
						int_java_map.put( i, i );
					}
				}
				break;
			case "testInt_TPrimitiveHashMap":
				int_t_primitive_map = new TIntIntHashMap( Constants.INTS.length );
				if ( fill_maps ) {
					for( int i : Constants.INTS ) {
						int_t_primitive_map.put( i, i );
					}
				}
				break;
			case "testInt_THashMap":
				int_t_map = new THashMap<>( Constants.INT_OBJECTS.length );
				if ( fill_maps ) {
					for( Integer i : Constants.INT_OBJECTS ) {
						int_t_map.put( i, i );
					}
				}
				break;

			// Objects...
			case "testString_JavaHashMap":
				string_java_map = new HashMap<>();
				if ( fill_maps ) {
					for( String i : Constants.STRING_OBJECTS ) {
						string_java_map.put( i, i );
					}
				}
				break;
			case "testString_TPrimitiveHashMap":
				string_t_primitive_map =
					new TObjectIntHashMap<>( Constants.STRING_OBJECTS.length );
				if ( fill_maps ) {
					for( String i : Constants.STRING_OBJECTS ) {
						string_t_primitive_map.put( i, i.length() );
					}
				}
				break;
			case "testString_THashMap":
				string_t_map = new THashMap<>( Constants.STRING_OBJECTS.length );
				if ( fill_maps ) {
					for( String i : Constants.STRING_OBJECTS ) {
						string_t_map.put( i, i );
					}
				}
				break;
		}
	}

	@Override
	public void tearDown( Method completed_method ) {
		byte_java_map = null;
		byte_t_primitive_map = null;
		byte_t_map = null;

		int_java_map = null;
		int_t_primitive_map = null;
		int_t_map = null;

		string_java_map = null;
		string_t_primitive_map = null;
		string_t_map = null;
	}



	// Bytes...
	public abstract void testByte_JavaHashMap();
	public abstract void testByte_TPrimitiveHashMap();
	public abstract void testByte_THashMap();

	// Ints...
	public abstract void testInt_JavaHashMap();
	public abstract void testInt_TPrimitiveHashMap();
	public abstract void testInt_THashMap();

	// Objects...
	public abstract void testString_JavaHashMap();
	public abstract void testString_TPrimitiveHashMap();
	public abstract void testString_THashMap();



	protected static class BytePrimitiveTotaler implements TByteProcedure {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( byte a ) {
			total += a;
			return true;
		}
	}

	protected static class ByteTotaler implements TObjectProcedure<Byte> {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( Byte value ) {
			total += value.intValue();
			return true;
		}
	}

	protected static class IntPrimitiveTotaler implements TIntProcedure {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( int a ) {
			total += a;
			return true;
		}
	}

	@SuppressWarnings( "UnnecessaryUnboxing" )
	protected static class IntTotaler implements TObjectProcedure<Integer> {
		int total = 0;

		void reset() {
			total = 0;
		}

		public boolean execute( Integer value ) {
			total += value.intValue();
			return true;
		}
	}

	protected static class StringTotaler implements TObjectProcedure<String> {
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
