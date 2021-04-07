package utilities;


public class StdCoord implements Coord {

	private int row;
	private int column;
	
	public StdCoord(int x, int y) {

		
		this.row = x;
		this.column = y;
	}

	@Override
	public int getRow() {
		return this.row;
	}

	@Override
	public int getColumn() {
		return this.column;
	}

	@Override
	public void setRow(int row) {

		
		this.row = row;
	}

	@Override
	public void setColumn(int column) {

		
		this.column = column;
	}
	
	
	@Override
	public boolean equals(Object other) {
			if (this == other) {
	            return true;
	        }
	        if (other == null || getClass() != other.getClass()) {
	            return false;
	        }

	        StdCoord c = (StdCoord) other;

			return this.getRow() == c.getRow() && this.getColumn() == c.getColumn();
	}
	
	@Override
	public int hashCode() {
		 int tmp = ( column +  ((row+1)/2));
         return row +  ( tmp * tmp);
	}
	
	public String toString() {
		
		return "("+getRow()+","+getColumn()+")";
		
		
	}
}
