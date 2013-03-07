package gnu.trove.benchmark;

/**
 * Tests get performance.
 */
@SuppressWarnings( "UnusedDeclaration" )
public class ForEachBenchmark extends AbstractComparisonBase {
	public ForEachBenchmark() {
		super( true );      // we want filled maps
	}


	// Bytes...

	@Override
	public void testByte_JavaHashMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void testByte_TPrimitiveHashMap() {
		for( int i = 0; i < 5; i++ ) {
			byte_t_primitive_map.forEachValue( byte_primitive_totaler );
		}
	}

	@Override
	public void testByte_THashMap() {
		for( int i = 0; i < 5; i++ ) {
			byte_t_map.forEachKey( byte_totaler );
		}
	}


	// Ints...

	@Override
	public void testInt_JavaHashMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void testInt_TPrimitiveHashMap() {
		for( int i = 0; i < 5; i++ ) {
			int_t_primitive_map.forEachKey( int_primitive_totaler );
		}
	}

	@Override
	public void testInt_THashMap() {
		for( int i = 0; i < 5; i++ ) {
			int_t_map.forEachKey( int_totaler );
		}
	}


	// Objects...

	@Override
	public void testString_JavaHashMap() {
		throw new UnsupportedOperationException();
	}

	@Override
	public void testString_TPrimitiveHashMap() {
		for( int i = 0; i < 5; i++ ) {
			string_t_primitive_map.forEachKey( string_totaler );
		}
	}

	@Override
	public void testString_THashMap() {
		for( int i = 0; i < 5; i++ ) {
			string_t_map.forEachKey( string_totaler );
		}
	}
}
